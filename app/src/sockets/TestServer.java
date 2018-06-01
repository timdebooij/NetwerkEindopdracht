package sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

public class TestServer {


    private static final int PORT = 9898;
    private static HashSet<String> names = new HashSet<String>();
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
    private static HashSet<Boolean> readies = new HashSet<Boolean>();

    private ArrayList<ObjectOutputStream> outputStreams = new ArrayList<>();
    private ArrayList<ObjectInputStream> inputStreams = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        System.out.println("The game server is running...");
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }

    private static class Handler extends Thread {
        private String name;
        private Socket socket;
        private Boolean ready;
        private BufferedReader in;
        private PrintWriter out;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public static boolean areAllTrue(boolean[] array) {
            for (boolean b : array) if (!b) return false;
            return true;
        }

        public void run() {
            try {

                // Create character streams for the socket.
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Request a name from this client.
                while (true) {
                    out.println("SUBMITNAME");
                    name = in.readLine();
                    if (name == null) {
                        return;
                    }
                    synchronized (names) {
                        if (!names.contains(name)) {
                            names.add(name);
                            readies.add(ready);
                            break;
                        }
                    }
                }

                // Now that a successful name has been chosen, add the
                // socket's print writer to the set of all writers so
                // this client can receive broadcast messages.
                out.println("NAMEACCEPTED");
                writers.add(out);

                String timeStamp = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());

                for (PrintWriter writer : writers) {
                    writer.println("MESSAGE " + timeStamp + " > " + name + " just joined! ");
                }

                while (true) {
                    out.println("READY");
                    String input = in.readLine();
//                    if (input == null) {
//                        return;
//                    }
//                    if (input == "ready" && !readies.contains(ready)){
//                        ready = true;
//                        for (PrintWriter writer : writers){
//                            writer.println("MESSAGE " + timeStamp + " > " + name + " is ready! " );
//                        }
//
//                    }
                    for (PrintWriter writer : writers) {
                        writer.println("MESSAGE " + timeStamp + " - " + name + ": " + input);
                    }
                    //}
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
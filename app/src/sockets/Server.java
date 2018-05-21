package sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 4444;
    public static void main(String[] args) throws IOException {
        new Server().runServer();
    }
    public void runServer() throws IOException{
        //Maak server socket, en luister voor connections. Meteen accepten.
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Ready for connection...");
        //Zorgt ervoor dat de server meerdere en verschillende clients kan accepten.
        while (true){
            Socket socket = serverSocket.accept();
            new ServerThread(socket).start();
        }
    }
}

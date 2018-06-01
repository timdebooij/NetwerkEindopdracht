package sockets;

import drankspel.game.Card;
import drankspel.game.Game;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;

/**
 * A server program which accepts requests from clients to
 * capitalize strings.  When clients connect, a new thread is
 * started to handle an interactive dialog in which the client
 * sends in a string and the server thread sends back the
 * capitalized version of the string.
 *
 * The program is runs in an infinite loop, so shutdown in platform
 * dependent.  If you ran it from a console window with the "java"
 * interpreter, Ctrl+C generally will shut it down.
 */

public class Server {

    private static ArrayList<PrintWriter> writers = new ArrayList<>();
    private static ArrayList<Capitalizer> caps = new ArrayList<>();

    private static ArrayList<ObjectOutputStream> outputStreams = new ArrayList<>();
    private static ArrayList<ObjectInputStream> inputStreams = new ArrayList<>();
    private static int playersJoined = 0;
    private static int selectedPlayer = 0;
    private static Game game;
    private static ArrayList<Card> currentCards = new ArrayList<>();
    /**
     * Application method to run the server runs in an infinite loop
     * listening on port 9898.  When a connection is requested, it
     * spawns a new thread to do the servicing and immediately returns
     * to listening.  The server keeps a unique client number for each
     * client that connects just to show interesting logging
     * messages.  It is certainly not necessary to do this.
     */
    public static void main(String[] args) throws Exception {
        System.out.println("The capitalization server is running.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(9898);
        try {
            while (true) {
                Capitalizer cap = new Capitalizer(listener.accept(), clientNumber++);
                cap.start();
                //caps.add(cap);
                System.out.println(caps.size());
                if(playersJoined == 2){
                    break;
                }
            }
        } finally {
            listener.close();
        }
        game = new Game();
        System.out.println(outputStreams.size());
        outputStreams.get(selectedPlayer).writeObject(getCards());
        nextPlayer();
        System.out.println(selectedPlayer);
        System.out.println(game.gameStillGoing());
    }


    public static void nextPlayer(){
        if(selectedPlayer <= 1)
            selectedPlayer++;
        else
            selectedPlayer = 0;
    }

    public static ArrayList<Card> getCards(){
        System.out.println(game.getCards().size());
        ArrayList<Card> newCards = game.selectFiveCards();
        currentCards = newCards;
        return newCards;
    }

    public static void sendNewCards() throws IOException {
        if(game.gameStillGoing()) {
            outputStreams.get(selectedPlayer).writeObject(getCards());
        }
        else{
            System.out.println("game finished");
        }
    }

    public static void sendMessages(String message) throws IOException {
        for(int i = 0; i <= outputStreams.size()-1; i++){
            if(!(i==selectedPlayer))
                System.out.println("message send");
            outputStreams.get(i).writeObject(message);
        }
    }


    /**
     * A private thread to handle capitalization requests on a particular
     * socket.  The client terminates the dialogue by sending a single line
     * containing only a period.
     */
    private static class Capitalizer extends Thread {
        private Socket socket;
        private int clientNumber;
        private PrintWriter out;

        public Capitalizer(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client# " + clientNumber + " at " + socket);
        }

        /**
         * Services this thread's client by first sending the
         * client a welcome message then repeatedly reading strings
         * and sending back the capitalized version of the string.
         */
        public void run() {
            try {

                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                outputStreams.add(out);
                inputStreams.add(in);

                out.writeObject("you joined");

                // Get messages from the client, line by line; return them
                // capitalized

                while (true) {
                    synchronized (this) {
                        Object input = in.readObject();
                        if (input.getClass().equals(String.class)) {
                            if (input.equals("ready")) {
                                System.out.println("player ready");
                                playersJoined++;
                            } else {
                                sendMessages(input.toString());
                            }
                        } else {
                            //System.out.println("cards entered");
                            ArrayList<Card> card = (ArrayList<Card>) input;
                            //System.out.println("rule " + card.get(0).getRule());
                            //System.out.println("amount: " + card.size());
                            sendMessages(card.get(0).getRule());
                            int number = card.get(0).getNumber();
                            String type = card.get(0).getType();
                            Iterator it = currentCards.iterator();
                            while(it.hasNext()){
                                Card c = (Card) it.next();
                                if(c.getNumber() == number){
                                    if(c.getType().equals(type)){
                                        it.remove();
                                        //System.out.println("removed card");
                                    }
                                }
                            }
                            game.returnFourCards(currentCards);
                            //System.out.println("returned cards");
                            nextPlayer();
                            sendNewCards();
                            System.out.println("new cards send");
                        }

                        if (input == null || input.equals(".")) {
                            break;
                        }

                    }
                    }
                } catch(IOException e){
                    log("Error handling client# " + clientNumber + ": " + e);
                } catch(ClassNotFoundException e){
                    e.printStackTrace();
                } finally{
                    try {
                        socket.close();
                    } catch (IOException e) {
                        log("Couldn't close a socket, what's going on?");
                    }
                    log("Connection with client# " + clientNumber + " closed");
                }

        }

        /**
         * Logs a simple message.  In this case we just write the
         * message to the server applications standard output.
         */
        private void log(String message) {
            System.out.println(message);
        }

        private void print(String message){
            out.println(message);
        }
    }
}
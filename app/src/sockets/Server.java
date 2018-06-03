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
import java.util.HashSet;
import java.util.Iterator;

public class Server {

    private static ArrayList<ObjectOutputStream> outputStreams = new ArrayList<>();
    private static ArrayList<ObjectInputStream> inputStreams = new ArrayList<>();
    private static int playersJoined = 0;
    private static int selectedPlayer = 0;
    private static Game game;
    private static ArrayList<Card> currentCards = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        System.out.println("The Game server is running.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(9898);
        try {
            while (true) {
                Player player = new Player(listener.accept(), clientNumber++);
                player.start();

                if(playersJoined == 2){
                    break;
                }
            }
        } finally {
            listener.close();
        }
        game = new Game(1);
        System.out.println(outputStreams.size());
        outputStreams.get(selectedPlayer).writeObject(getCards());
        //nextPlayer();
        System.out.println(selectedPlayer);
        System.out.println(game.gameStillGoing());
    }


    private static void nextPlayer(){
        if(selectedPlayer <= 1)
            selectedPlayer++;
        else
            selectedPlayer = 0;
    }

    private static ArrayList<Card> getCards(){
        game.shuffleCards();
        System.out.println(game.getCards().size());
        ArrayList<Card> newCards = game.selectFiveCards();
        currentCards = newCards;
        return newCards;
    }

    private static void sendNewCards() throws IOException {
        if(game.gameStillGoing()) {
            outputStreams.get(selectedPlayer).writeObject(getCards());
            System.out.println("new cards send");
        }
        else{
            System.out.println("game finished");
        }
    }

    private static void sendMessages(String message) throws IOException {
        for(int i = 0; i <= outputStreams.size()-1; i++){
            if(!(i==selectedPlayer))
                //System.out.println("message send");
            outputStreams.get(i).writeObject(message);
        }
    }

    private static class Player extends Thread {
        private Socket socket;
        private int clientNumber;
        private PrintWriter out;

        private Player(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client# " + clientNumber + " at " + socket);
        }

        public void run() {
            try {

                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                outputStreams.add(out);
                inputStreams.add(in);

                out.writeObject("you joined");

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
                            ArrayList<Card> card = (ArrayList<Card>) input;
                            sendMessages(card.get(0).getRule());
                            int number = card.get(0).getNumber();
                            String type = card.get(0).getType();
                            Iterator it = currentCards.iterator();
                            while(it.hasNext()){
                                Card c = (Card) it.next();
                                if(c.getNumber() == number){
                                    if(c.getType().equals(type)){
                                        it.remove();
                                    }
                                }
                            }
                            game.returnFourCards(currentCards);
                            nextPlayer();
                            sendNewCards();
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

        private void log(String message) {
            System.out.println(message);
        }

    }
}
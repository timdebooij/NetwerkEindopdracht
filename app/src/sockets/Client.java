package sockets;

import drankspel.game.Card;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

    private static int cardSelected;
    private JFrame frame = new JFrame("Game Client");
    private JPanel panel = new JPanel();
    private static JTextArea messageArea = new JTextArea(8, 60);
    private static ArrayList<Card> cards = new ArrayList<>();
    private static ArrayList<JButton> buttons = new ArrayList<>();
    private static JButton button1 = new JButton();
    private static JButton button2 = new JButton();
    private static JButton button3 = new JButton();
    private static JButton button4 = new JButton();
    private static JButton button5 = new JButton();
    private static ObjectOutputStream out;

    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Enter in the
     * listener sends the textfield contents to the server.
     */
    public Client(){

        // Layout GUI
        frame.setSize(700 , 250);
        panel.setSize(700 , 30);
        messageArea.setEditable(false);
        frame.getContentPane().add(new JScrollPane(messageArea), "North");
        frame.getContentPane().add(panel, "South");
        panel.repaint();
        frame.repaint();

        button1.addActionListener(e -> {int index = 0;
            try {
                playCard(index);
                removeCards(buttons, cards);
                //dealCards(buttons, cards);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println("klik!");});
        buttons.add(button1);

        button2.addActionListener(e -> {int index = 1;  try {
            playCard(index);
            removeCards(buttons, cards);
            //dealCards(buttons, cards);
        } catch (IOException e1) {
            e1.printStackTrace();
        }System.out.println("klik!");});
        buttons.add(button2);

        button3.addActionListener(e -> {int index = 2;  try {
            playCard(index);
            removeCards(buttons, cards);
            //dealCards(buttons, cards);
        } catch (IOException e1) {
            e1.printStackTrace();
        }System.out.println("klik!");});
        buttons.add(button3);

        button4.addActionListener(e -> {int index = 3;  try {
            playCard(index);
            removeCards(buttons, cards);
            //dealCards(buttons, cards);
        } catch (IOException e1) {
            e1.printStackTrace();
        }System.out.println("klik!");});
        buttons.add(button4);

        button5.addActionListener(e -> {int index = 4;  try {
            playCard(index);
            removeCards(buttons, cards);
            //dealCards(buttons, cards);
        } catch (IOException e1) {
            e1.printStackTrace();
        }System.out.println("klik!");});
        buttons.add(button5);
    }

    public void dealCards(ArrayList<JButton> buttons, ArrayList<Card> cards){

        //adds amount of buttons equal to the amount of cards dealt to the players hand
        for(int index = 0; index < cards.size(); index++){
            buttons.get(index).setText(cards.get(index).getType() + cards.get(index).getNumber());
            cardSelected = index;
            buttons.get(index).setPreferredSize(new Dimension(100, 25));
            buttons.get(index).setToolTipText(cards.get(index).getRule());
            panel.add(buttons.get(index));
        }
        frame.repaint();
        panel.repaint();
        frame.repaint();
        frame.validate();
        frame.revalidate();
        panel.repaint();
        panel.validate();
        panel.revalidate();
    }

    public void playCard(int index) throws IOException {
        ArrayList<Card> card = new ArrayList<>();
        card.add(cards.get(index));
        messageArea.append("\n");
        messageArea.append("You have to complete the challenge: \n");
        messageArea.append(cards.get(index).getRule() + "\n");
        messageArea.setCaretPosition(messageArea.getDocument().getLength());
        System.out.println(card.size());
        out.writeObject(card);
        System.out.println("card send");
    }

    public void removeCards(ArrayList<JButton> buttons, ArrayList<Card> cards){

        //removes amount of buttons equal to the amount of cards dealt to the players hand
        for(int index = 0; index < cards.size(); index++){
            panel.remove(buttons.get(index));
        }
        panel.repaint();
        //out.println();

    }

    public void connectToServer() throws IOException, ClassNotFoundException {

        // Get the server address from a dialog box.
        String serverAddress = JOptionPane.showInputDialog(
                frame,
                "Enter IP Address of the Server:",
                "Welcome to the Capitalization Program",
                JOptionPane.QUESTION_MESSAGE);

        // Get the user to input username from a dialog box.
        String name = JOptionPane.showInputDialog(
                frame,
                "Choose a screen name:",
                "Screen name selection",
                JOptionPane.PLAIN_MESSAGE);
        frame.setTitle(name);
        // Make connection and initialize streams
        Socket socket = new Socket(serverAddress, 9898);

        out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        while(true) {

            try {
                synchronized (this) {
                    Object input = (Object) in.readObject();

                    if (input.getClass().equals(String.class)) {
                        if (input.equals("you joined")) {
                            messageArea.append(input + " the game, please wait for the game to start" + "\n");
                            out.writeObject("ready");
                        } else {
                            messageArea.append("\n");
                            messageArea.append("Someone else has a challenge to complete:  \n");
                            messageArea.append(input.toString() + "\n");
                            messageArea.setCaretPosition(messageArea.getDocument().getLength());
                        }
                    } else {
                        System.out.println("cards received");
                        cards = (ArrayList<Card>) input;
                        dealCards(buttons, cards);
                        messageArea.append("You can select a new card" + "\n");
                        messageArea.setCaretPosition(messageArea.getDocument().getLength());

                        frame.repaint();
                        frame.validate();
                        frame.revalidate();
                        panel.repaint();
                        panel.validate();
                        panel.revalidate();

                    }
                }
            } catch (EOFException e) {

            }
        }

    }

    /**
     * Runs the client application.
     */
    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception ignored){}
        Client client = new Client();
        //client.setButtonPressed(false);
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.frame.setResizable(false);
        //client.frame.pack();
        client.connectToServer();
        System.out.println("received cards");
    }
}
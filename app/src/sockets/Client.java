package sockets;

import drankspel.game.Card;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {


    private static BufferedReader in;
    private PrintWriter out;
    private JFrame frame = new JFrame("Game Client");
    private JPanel panel = new JPanel();
    private JTextField dataField = new JTextField(40);
    private static JTextArea messageArea = new JTextArea(8, 60);
    private ArrayList<Card> cards = new ArrayList<>();
    private static JButton button;

    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Enter in the
     * listener sends the textfield contents to the server.
     */
    public Client(){

        // Layout GUI
        messageArea.setEditable(false);
        frame.getContentPane().add(dataField, "Center");
        frame.getContentPane().add(new JScrollPane(messageArea), "North");
        frame.getContentPane().add(panel, "South");

        // Add Buttons to panel
        for(int index = 0; index < 5; index++){
            panel.add(button = new JButton());
        }

        // Add Listeners
        dataField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println(dataField.getText());
                dataField.setText("");
            }
        });
    }

    public void connectToServer() throws IOException {

        // Get the server address from a dialog box.
        String serverAddress = JOptionPane.showInputDialog(
                frame,
                "Enter IP Address of the Server:",
                "Welcome to the Capitalization Program",
                JOptionPane.QUESTION_MESSAGE);

        // Get the user to input username from a dialog box.
        String  name = JOptionPane.showInputDialog(
                frame,
                "Choose a screen name:",
                "Screen name selection",
                JOptionPane.PLAIN_MESSAGE);

        // Make connection and initialize streams
        Socket socket = new Socket(serverAddress, 9898);
//        in = new BufferedReader(
//                new InputStreamReader(socket.getInputStream()));
//        out = new PrintWriter(socket.getOutputStream(), true);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        while(true) {

            try {
                synchronized (this) {
                    Object input = (Object) in.readObject();

                    if (input.getClass().equals(String.class)) {
                        if (input.equals("you joined")) {
                            messageArea.append(input + " the game" + "\n");
                            out.writeObject("ready");
                        } else {
                            messageArea.append(input.toString() + "\n");
                        }
                    } else {
                        System.out.println("cards received");
                        cards = (ArrayList<Card>) input;
                        //Collections.sort(cards);
                        //Collections.sort(cards, Card.cardComparator);
                        System.out.println(cards.toString());

                        messageArea.append(cards.get(0).toString() + "\n");
                        ArrayList<Card> card = new ArrayList<>();
                        card.add(cards.get(0));
                        System.out.println(card.size());
                        out.writeObject(card);
                        System.out.println("card send");

                    }
                }
            } catch (EOFException e) {

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    //public static void update() throws IOException {
    //    String message = in.readLine();
    //    messageArea.append(message + "\n");
    //}

    /**
     * Runs the client application.
     */
    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.pack();
        client.frame.setVisible(true);
        client.connectToServer();
    }
}
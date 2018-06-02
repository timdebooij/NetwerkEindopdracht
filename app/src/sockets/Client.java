package sockets;

import drankspel.game.Card;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

    private static int cardSelected;
    private static BufferedReader in;
    private PrintWriter out;
    private JFrame frame = new JFrame("Game Client");
    private JPanel panel = new JPanel();
    private JTextField dataField = new JTextField(40);
    private static JTextArea messageArea = new JTextArea(8, 60);
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<JButton> buttons = new ArrayList<>();
    private static boolean active = false;
    private static JButton test;
    private static JButton button1 = new JButton();
    private static JButton button2 = new JButton();
    private static JButton button3 = new JButton();
    private static JButton button4 = new JButton();
    private static JButton button5 = new JButton();
    private static int index1;

    public static boolean isButtonPressed() {
        return buttonPressed;
    }

    public static void setButtonPressed(boolean buttonPressed) {
        Client.buttonPressed = buttonPressed;
    }

    private static boolean buttonPressed;

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
        //frame.getContentPane().add(dataField, "Center");
        frame.getContentPane().add(new JScrollPane(messageArea), "North");
        frame.getContentPane().add(panel, "South");
        panel.repaint();
        frame.repaint();
        button1.addActionListener(e -> {index1 = 0; setButtonPressed(true); System.out.println("klik!");});
        buttons.add(button1);
        button2.addActionListener(e -> {index1 = 1; setButtonPressed(true); System.out.println("klik!");});
        buttons.add(button2);
        button3.addActionListener(e -> {index1 = 2; setButtonPressed(true); System.out.println("klik!");});
        buttons.add(button3);
        button4.addActionListener(e -> {index1 = 3; setButtonPressed(true); System.out.println("klik!");});
        buttons.add(button4);
        button5.addActionListener(e -> {index1 = 4; setButtonPressed(true); System.out.println("klik!");});
        buttons.add(button5);
        buttonPressed = false;

        //panel.add(button1);
        //button1.setPreferredSize(new Dimension(100,25));
        //panel.add(button2);
        //button2.setPreferredSize(new Dimension(100,25));
        //panel.add(button3);
        //button3.setPreferredSize(new Dimension(100,25));
        //panel.add(button4);
        //button4.setPreferredSize(new Dimension(100,25));
        //panel.add(button5);
        //button5.setPreferredSize(new Dimension(100,25));

        // Add Listeners
        dataField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println(dataField.getText());
                dataField.setText("");
            }
        });
    }

    public void dealCards(ArrayList<JButton> buttons, ArrayList<Card> cards){

        //adds amount of buttons equal to the amount of cards dealt to the players hand
        for(int index = 0; index < cards.size(); index++){
            buttons.get(index).setText(cards.get(index).getType() + cards.get(index).getNumber());
            cardSelected = index;
            buttons.get(index).setPreferredSize(new Dimension(100, 25));
            panel.add(buttons.get(index));
        }
        frame.repaint();
        panel.repaint();
    }

    public void playCard(ArrayList<Card> cards){

    }

    public void removeCards(ArrayList<JButton> buttons, ArrayList<Card> cards){

        //removes amount of buttons equal to the amount of cards dealt to the players hand
        for(int index = 0; index < cards.size(); index++){
            panel.remove(buttons.get(index));
        }
        panel.repaint();
    }

    public void connectToServer() throws IOException, ClassNotFoundException {

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
                        //System.out.println(cards.toString());
                        dealCards(buttons, cards);
                        //messageArea.append(cards.get(0).toString() + "\n");
                        ArrayList<Card> card = new ArrayList<>();

                        if (buttonPressed){
                            card.add(cards.get(index1));
                            System.out.println(card.size());
                            out.writeObject(card);
                            System.out.println("card send");
                        }


                        //card.add(cards.get(0));

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

    //public static void update() throws IOException {
    //    String message = in.readLine();
    //    messageArea.append(message + "\n");
    //}

    /**
     * Runs the client application.
     */
    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.setButtonPressed(false);
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.frame.setResizable(false);
        //client.frame.pack();
        client.connectToServer();
    }
}
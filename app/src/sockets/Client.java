package sockets;

import drankspel.game.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * A simple Swing-based client for the capitalization server.
 * It has a main frame window with a text field for entering
 * strings and a textarea to see the results of capitalizing
 * them.
 */
public class Client {


    private static BufferedReader in;
    private PrintWriter out;
    private Timer timer;
    private JFrame frame = new JFrame("Capitalize Client");
    private String ready;
    private JTextField dataField = new JTextField(40);
    private static JTextArea messageArea = new JTextArea(8, 60);
    private ArrayList<Card> cards = new ArrayList<>();

    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Enter in the
     * listener sends the textfield contents to the server.
     */
    public Client(){



        // Layout GUI
        messageArea.setEditable(false);
        frame.getContentPane().add(dataField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.getContentPane().add(new UpdatePanel(), BorderLayout.SOUTH);


        // Add Listeners
        dataField.addActionListener(new ActionListener() {
            /**
             * Responds to pressing the enter key in the textfield
             * by sending the contents of the text field to the
             * server and displaying the response from the server
             * in the text area.  If the response is "." we exit
             * the whole application, which closes all sockets,
             * streams and windows.
             */
            public void actionPerformed(ActionEvent e) {
                out.println(dataField.getText());
                dataField.setText("");
                //String response;
                //try {
                //    response = in.readLine();
                //    if (response == null || response.equals("")) {
                //        System.exit(0);
                //    }
                //} catch (IOException ex) {
                //    response = "Error: " + ex;
                //}
                //messageArea.append(response + "\n");
                //dataField.selectAll();
            }
        });
    }

    /**
     * Implements the connection logic by prompting the end user for
     * the server's IP address, connecting, setting up streams, and
     * consuming the welcome messages from the server.  The Capitalizer
     * protocol says that the server sends three lines of text to the
     * client immediately after establishing a connection.
     */

    public class UpdatePanel extends JPanel {

        public UpdatePanel(){

            JButton readyButton = new JButton();
            add(readyButton);
            readyButton.addActionListener(e -> {
                ready = "ready";
                remove(readyButton);
            });

            //for (int  index = 0; index < 5; index++){
            //    add(new JButton("Button"));
            //}
        }
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

            }
        }
    }

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
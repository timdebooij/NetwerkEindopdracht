package sockets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
    private Boolean ready;
    private JTextField dataField = new JTextField(40);
    private static JTextArea messageArea = new JTextArea(8, 60);

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
                ready = true;
                remove(readyButton);
            });

            for (int  index = 0; index < 5; index++){
                add(new JButton("Button"));
            }
        }
    }

    public void connectToServer() throws IOException {

        // Get the server address from a dialog box.
        String serverAddress = JOptionPane.showInputDialog(
                frame,
                "Enter IP Address of the Server:",
                "Welcome to the Capitalization Program",
                JOptionPane.QUESTION_MESSAGE);

        // Get the user to input username from a dialog box.
        String  Name = JOptionPane.showInputDialog(
                frame,
                "Choose a screen name:",
                "Screen name selection",
                JOptionPane.PLAIN_MESSAGE);

        // Make connection and initialize streams
        Socket socket = new Socket(serverAddress, 9898);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        while(true){
            String line = in.readLine();
            if (line.startsWith("SUBMITNAME")) {
                out.println(Name);
            } else if (line.startsWith("NAMEACCEPTED")) {
                dataField.setEditable(true);
            } else if (line.startsWith("READY")){

            } else if (line.startsWith("MESSAGE")) {
                messageArea.append(line.substring(8) + "\n");
            }
        }

        // Consume the initial welcoming messages from the server
        //for (int i = 0; i < 3; i++) {
        //    messageArea.append(in.readLine() + "\n");
        //}
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
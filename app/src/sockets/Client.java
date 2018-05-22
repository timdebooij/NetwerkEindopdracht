package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException {
        //String name = args[0];
        Socket socket = new  Socket("localhost", 4444);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader bufferedReader = new java.io.BufferedReader(new InputStreamReader(System.in));
        while(true){
            String readerInput = bufferedReader.readLine();
            printWriter.println(": "+readerInput);
        }
    }
}

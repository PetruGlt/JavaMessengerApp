package com.example;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private String serverAddress = "127.0.0.1"; // The server's IP address
    private int PORT = 8100; // The server's port


    public Client() throws IOException {
        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader terminalIn = new BufferedReader(new InputStreamReader (System.in));
                BufferedReader socketIN = new BufferedReader(new InputStreamReader(socket.getInputStream()));){
                    System.out.print("Please enter your username: ");
                    String username = terminalIn.readLine();
                    System.out.print("Please enter your password: ");
                    String password = terminalIn.readLine();
                    socketOut.println(username + ":" + password);
                    socketOut.flush();
                    String response = socketIN.readLine();
                    System.out.println(response);
        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        }
    }
}
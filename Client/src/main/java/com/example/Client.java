package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private String serverAddress = "127.0.0.1"; // The server's IP address
    private int PORT = 8100; // The server's port


    public Client() throws IOException {
        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())) ) {
// Send a request to the server
            String request = "Wor";
            out.println(request);
// Wait the response from the server ("Hello World!")
            String response = in.readLine ();
            System.out.println(response);
        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        }
    }
}
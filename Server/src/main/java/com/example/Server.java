package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 8100;

    public Server() throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                System.out.println("Waiting for connection...");
                Socket socket = serverSocket.accept();

                new ClientThread(socket).start();
            }
        } catch (IOException e){
            System.err.println("Error: " + e.getMessage());
        }
        finally {
            serverSocket.close();
        }
    }
}

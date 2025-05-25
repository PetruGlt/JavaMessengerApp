package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
    public static final int PORT = 8100;
    public static int users = 0;

    public Server() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                System.out.println("Waiting for connection...");

                Socket socket = serverSocket.accept();
                boolean ok = true;
                if(ok){
                    users++;
                    System.out.println("Connection established. Online users: " + users);
                    new ClientThread(socket, Database.getConnection()).start();
                } else {
                    ok = false;
                }


            }
        } catch (IOException e){
            System.err.println("Error: " + e.getMessage());
        }
        catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        finally {
            serverSocket.close();
        }
    }
}

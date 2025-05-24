package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket = null ;
    public ClientThread (Socket socket) { this.socket = socket ; }
    public void run () {

        try {
            // Get the request from the input stream: client → server
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            /// Read login request ( username:pass )

            String request = in.readLine();
            String[] credentials = request.split(":");
            if(credentials.length != 2){
                out.println("Invalid request format. Usage: username:password");
            }

            String username = credentials[0];
            String password = credentials[1];

            if(checkLoginCredentials(username, password)) {
                out.println("Success login!");
            }
            else{
                out.println("Invalid credentials.");
            }


        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close(); // or use try-with-resources
            } catch (IOException e) { System.err.println (e); }
        }
    }

    /// TODO
    ///
    public boolean checkLoginCredentials(String user, String pass){
        return false;
    }
}

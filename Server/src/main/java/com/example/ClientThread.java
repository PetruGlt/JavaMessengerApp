package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.util.Date;
//TODO Possible problem , util.DATE si sql.DATE , s-ar putea sa trebuiascasa ne mutam pe slq.DATE
public class ClientThread extends Thread {
    private Socket socket = null ;
    private Connection connection = null;
    private Integer currentGroupID = null;
    private Date lastRecievedMessageTime=null;
    public ClientThread (Socket socket , Connection connection) { this.socket = socket ;this.connection=connection;}
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
                Thread reader = new Thread( new SocketReciever(in, connection, this));
                Thread writer = new Thread(new SocketSender(out, connection, this));
                reader.start();
                writer.start();
                reader.join();
                writer.join();
            }
            else{
                out.println("Invalid credentials.");
            }


        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        }
        catch (InterruptedException e) {
            System.err.println("Thread interrupted... " + e);
        }
            finally {
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
    public synchronized Integer getCurrentGroupID() {
        return currentGroupID;
    }
    public synchronized void setCurrentGroupID(Integer currentGroupID) {
        this.currentGroupID = currentGroupID;
    }
    public synchronized Date getLastRecievedMessageTime() {
        return lastRecievedMessageTime;
    }
    public synchronized void setLastRecievedMessageTime(Date lastRecievedMessageTime) {
        this.lastRecievedMessageTime=lastRecievedMessageTime;
    }
}

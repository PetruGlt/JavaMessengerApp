package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class SocketReciever implements Runnable{
    private BufferedReader reader;
    private Connection connection;
    private ClientThread fatherProcess;
    public SocketReciever(BufferedReader reader, Connection connection, ClientThread clientThread) {
        this.reader = reader;
        this.connection = connection;
        this.fatherProcess = clientThread;
    }
    @Override
    public void run() {
        while(true){
            try {
                if (reader.ready()) {
                    Message message = Message.fromJson(reader.readLine());
                    MessageDao msgDao= new MessageDao();
                    msgDao.create(connection, message);
                }
            }
            catch(IOException e) {//TODO Trebuie facuta mai specifica
                System.err.println("Error: " + e.getMessage());
//                Database.rollback(connection);
//                break;???
            }
            catch (SQLException e){
                System.err.println("Error: " + e.getMessage());//TODO Trebuie facuta mai specifica
            }
            }
        }
    }

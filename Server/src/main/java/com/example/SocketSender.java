package com.example;

import java.io.PrintWriter;
import java.sql.Connection;

public class SocketSender implements Runnable{
    private PrintWriter writer;
    private Connection connection;
    private ClientThread fatherProcess;
    public SocketSender(PrintWriter writer, Connection connection, ClientThread clientThread) {
        this.writer = writer;
        this.connection = connection;
        this.fatherProcess = clientThread;
    }
    @Override
    public void run() {}
}

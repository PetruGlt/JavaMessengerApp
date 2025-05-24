package com.example;

import java.io.PrintWriter;
import java.net.Socket;

public class SocketSender implements Runnable{
    private PrintWriter writer;
    public SocketSender(PrintWriter writer) {
        this.writer = writer;
    }
    @Override
    public void run() {}
}

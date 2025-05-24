package com.example;

import java.io.BufferedReader;

public class SocketReciever implements Runnable{
    BufferedReader reader;
    public SocketReciever(BufferedReader reader) {
        this.reader = reader;
    }
    @Override
    public void run() {}
}

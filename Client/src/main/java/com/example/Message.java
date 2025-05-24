package com.example;

import java.io.Serializable;
import java.util.*;

import com.google.gson.Gson;

public class Message {
    private String senderID;
    private ArrayList<Integer> recipientID = new ArrayList<>();
    private String content;
    private Date time;

    public Message(String senderID, String message, ArrayList<Integer> recipientID) {
        this.senderID = senderID;
        this.content = message;
        this.recipientID = recipientID;
        this.time = new Date();
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public ArrayList<Integer> getRecipientID() {
        return recipientID;
    }

    public void setRecipientID(ArrayList<Integer> recipientID) {
        this.recipientID = recipientID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    // Optional: Custom method to convert time to String format
    public String getTimeAsString() {
        return time.toString();  // This could be customized
    }

    // Method to serialize the message object to JSON
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // Static method to deserialize from JSON to Message object
    public static Message fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Message.class);
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderID='" + senderID + '\'' +
                ", recipientID=" + recipientID +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }
}

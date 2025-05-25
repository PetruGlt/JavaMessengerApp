package com.example;

import java.io.Serializable;
import java.util.*;

import com.google.gson.Gson;

public class Message {
    private Integer senderID;
    private Integer groupID;
    private String content;
    private Date time;

    public Message(Integer senderID, Integer groupID, String message, Date time) {
        this.senderID = senderID;
        this.content = message;
        this.groupID = groupID;
        this.time = time;
    }
    public Message(Integer senderID, String message, Integer groupID) {
        this.senderID = senderID;
        this.content = message;
        this.groupID = groupID;
        this.time = new Date();
    }

    public Integer getSenderID() {
        return senderID;
    }

    public void setSenderID(Integer senderID) {
        this.senderID = senderID;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
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
                ", groupID=" + groupID +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }
}

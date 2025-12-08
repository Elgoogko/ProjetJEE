package com.actors;

import org.json.JSONObject;

public class MessageDTO {
    public String senderId;
    public String receiverId;
    public JSONObject message;

    public MessageDTO(String senderId, String receiverId, JSONObject message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
    }

    public MessageDTO() {
    }
}

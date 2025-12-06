package com.msfilm.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)  // Important !
public class MessageDTO {
    private String content;
    private String sender;
    private String recipient;
    
    // Constructeur par défaut OBLIGATOIRE
    public MessageDTO() {}
    
    public MessageDTO(String content, String sender, String recipient) {
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
    }
    
    // Getters et setters OBLIGATOIRES
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    
    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    
    @Override
    public String toString() {
        return String.format("Message{content='%s', sender='%s', recipient='%s'}", 
            content, sender, recipient);
    }
}
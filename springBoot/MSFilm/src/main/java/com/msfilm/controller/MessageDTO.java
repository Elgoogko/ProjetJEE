package com.msfilm.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDTO {
    
    private String content;
    private String sender;
    private String recipient;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;
    
    private MessageType type;
    private Map<String, Object> metadata = new HashMap<>();
    
    // Enum pour le type de message
    public enum MessageType {
        INFO,
        WARNING,
        ERROR,
        REQUEST,
        RESPONSE,
        NOTIFICATION
    }
    
    // Constructeurs
    public MessageDTO() {
        this.timestamp = new Date();
        this.type = MessageType.INFO;
    }
    
    public MessageDTO(String content) {
        this();
        this.content = content;
    }
    
    public MessageDTO(String content, String sender) {
        this(content);
        this.sender = sender;
    }
    
    public MessageDTO(String content, String sender, String recipient) {
        this(content, sender);
        this.recipient = recipient;
    }
    
    public MessageDTO(String content, String sender, String recipient, MessageType type) {
        this(content, sender, recipient);
        this.type = type;
    }
    
    // Getters et Setters
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getSender() {
        return sender;
    }
    
    public void setSender(String sender) {
        this.sender = sender;
    }
    
    public String getRecipient() {
        return recipient;
    }
    
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    public MessageType getType() {
        return type;
    }
    
    public void setType(MessageType type) {
        this.type = type;
    }
    
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
    
    // Méthodes utilitaires
    public void addMetadata(String key, Object value) {
        this.metadata.put(key, value);
    }
    
    public Object getMetadata(String key) {
        return this.metadata.get(key);
    }
    
    // Builder pattern (optionnel)
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private MessageDTO messageDTO;
        
        private Builder() {
            messageDTO = new MessageDTO();
        }
        
        public Builder content(String content) {
            messageDTO.content = content;
            return this;
        }
        
        public Builder sender(String sender) {
            messageDTO.sender = sender;
            return this;
        }
        
        public Builder recipient(String recipient) {
            messageDTO.recipient = recipient;
            return this;
        }
        
        public Builder type(MessageType type) {
            messageDTO.type = type;
            return this;
        }
        
        public Builder metadata(String key, Object value) {
            messageDTO.addMetadata(key, value);
            return this;
        }
        
        public MessageDTO build() {
            return messageDTO;
        }
    }
    
    @Override
    public String toString() {
        return "MessageDTO{" +
                "content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", timestamp=" + timestamp +
                ", type=" + type +
                ", metadata=" + metadata +
                '}';
    }
}
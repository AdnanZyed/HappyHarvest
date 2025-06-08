package com.example.happyharvest;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Message")
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int messageId;

    @NonNull
    private String senderUsername;

    @NonNull
    private String receiverUsername;

    @NonNull
    private String messageContent;

    @NonNull
    private long timestamp;

    public Message(@NonNull String senderUsername, @NonNull String receiverUsername, @NonNull String messageContent, long timestamp) {
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    @NonNull
    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(@NonNull String senderUsername) {
        this.senderUsername = senderUsername;
    }

    @NonNull
    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(@NonNull String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    @NonNull
    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(@NonNull String messageContent) {
        this.messageContent = messageContent;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

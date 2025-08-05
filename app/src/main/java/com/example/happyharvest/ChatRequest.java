package com.example.happyharvest;

public class ChatRequest {
    private String model;
    private Message[] messages;

    public ChatRequest(String model, Message[] messages) {
        this.model = model;
        this.messages = messages;
    }

    public static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
package com.example.happyharvest;

public class ChatResponse {
    private Choice[] choices;

    public String getMessage() {
        if (choices != null && choices.length > 0) {
            return choices[0].message.content;
        }
        return null;
    }

    public static class Choice {
        private Message message;
    }

    public static class Message {
        private String content;
    }
}
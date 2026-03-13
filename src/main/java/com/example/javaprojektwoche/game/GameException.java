package com.example.javaprojektwoche.game;

public class GameException extends Exception{
    private final String userMessage;
    public GameException(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
}

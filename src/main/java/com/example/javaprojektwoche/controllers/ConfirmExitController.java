package com.example.javaprojektwoche.controllers;

import com.example.javaprojektwoche.Navigator;
import com.example.javaprojektwoche.game.Level;
import javafx.fxml.FXML;
import javafx.scene.Scene;

public class ConfirmExitController extends NavigatingController {
    private final Level level;
    private final Scene gameState;

    public ConfirmExitController(Navigator navigator, Level level, Scene gameState) {
        super(navigator);
        this.level = level;
        this.gameState = gameState;
    }

    @FXML
    public void quit() {
        navigator.showLevelScene(level.getDifficulty());
    }

    @FXML
    public void cancel() {
        navigator.reconstructGame(gameState);
    }

    @Override
    public void back() {
        cancel();
    }
}

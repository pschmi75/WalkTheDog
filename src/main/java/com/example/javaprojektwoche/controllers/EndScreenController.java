package com.example.javaprojektwoche.controllers;

import com.example.javaprojektwoche.Navigator;
import com.example.javaprojektwoche.game.Level;
import javafx.fxml.FXML;

import java.util.List;

public class EndScreenController extends NavigatingController {
    private final Level previousLevel;

    public EndScreenController(Navigator navigator, Level previousLevel) {
        super(navigator);
        this.previousLevel = previousLevel;
    }

    @FXML
    public void selectNextLevel() {
        List<Level> levels = Level.getAllLevels();
        int nextIndex = levels.indexOf(previousLevel) + 1;
        navigator.showGameScene(nextIndex < levels.size() ? levels.get(nextIndex) : levels.getFirst());
    }

    @FXML
    public void selectReplay() {
        navigator.showGameScene(previousLevel);
    }

    @FXML
    public void selectHome() {
        navigator.showHomeScene();
    }

    @Override
    public void back() {
        selectReplay();
    }
}

package com.example.javaprojektwoche.controllers;

import com.example.javaprojektwoche.Navigator;
import com.example.javaprojektwoche.game.Level;
import javafx.fxml.FXML;

public class SelectDifficultyController extends NavigatingController {

    public SelectDifficultyController(Navigator navigator) {
        super(navigator);
    }

    @FXML
    public void selectStarter() {
        navigator.showLevelScene(Level.Difficulty.STARTER);
    }

    @FXML
    public void selectJunior() {
        navigator.showLevelScene(Level.Difficulty.JUNIOR);
    }

    @FXML
    public void selectExpert() {
        navigator.showLevelScene(Level.Difficulty.EXPERT);
    }

    @FXML
    public void selectMaster() {
        navigator.showLevelScene(Level.Difficulty.MASTER);
    }

    @FXML
    @Override
    public void back() {
        selectHome();
    }
}

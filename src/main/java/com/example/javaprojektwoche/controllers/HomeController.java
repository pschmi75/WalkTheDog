package com.example.javaprojektwoche.controllers;

import com.example.javaprojektwoche.Navigator;
import javafx.fxml.FXML;

public class HomeController extends NavigatingController{
    public HomeController(Navigator navigator) {
        super(navigator);
    }

    @FXML
    public void startGame() {
        navigator.showDifficultyScene();
    }

    @FXML
    public void showOptions() {
        navigator.showOptionsScene();
    }

    @FXML
    public void exit() {
        System.exit(0);
    }

    @Override
    public void back() {
        selectHome();
    }
}

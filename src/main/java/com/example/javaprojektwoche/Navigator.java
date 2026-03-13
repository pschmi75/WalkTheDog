package com.example.javaprojektwoche;

import com.example.javaprojektwoche.controllers.*;
import com.example.javaprojektwoche.game.Level;
import com.example.javaprojektwoche.game.Level.Difficulty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigator {

    private final Stage stage;

    public Navigator(Stage stage) throws IOException {
        this.stage = stage;
    }

    public void showHomeScene() {
        try {
            stage.setScene(loadScene("start_screen.fxml", new HomeController(this)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showOptionsScene() {
        try {
            stage.setScene(loadScene("settings_screen.fxml", new OptionsController(this)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showDifficultyScene() {
        try {
            stage.setScene(loadScene("difficulty_screen.fxml", new SelectDifficultyController(this)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showLevelScene(Difficulty difficulty) {
        try {
            stage.setScene(loadScene("levelselect_screen.fxml", new SelectLevelController(this, difficulty)));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showGameScene(Level level) {
        try {
            stage.setScene(loadScene("game_screen.fxml", new GameController(this, level)));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showEndScene(Level completedLevel) {
        try {
            Scene endScene = loadScene("win_screen.fxml", new EndScreenController(this, completedLevel));
            stage.setScene(endScene);

        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showConfirm(Level level, Scene gameState) {
        try {
            Scene endScene = loadScene("confirm_exit.fxml", new ConfirmExitController(this, level, gameState));
            stage.setScene(endScene);

        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void reconstructGame(Scene state) {
        stage.setScene(state);
    }

    private<T extends NavigatingController> Scene loadScene(String path, NavigatingController controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        // Set it in the FXMLLoader
        loader.setController(controller);


        Scene scene =  new Scene(loader.load(), 600, 400);
        controller.setScene(scene);
        return scene;
    }
}

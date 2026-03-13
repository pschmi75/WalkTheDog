package com.example.javaprojektwoche;

import com.example.javaprojektwoche.game.Level;
import com.example.javaprojektwoche.util.MusicManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import java.io.File;
import java.io.IOException;

public class WalkTheDogApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setResizable(false);
        MusicManager.startMusic();
        Navigator navigator = new Navigator(stage);
        navigator.showHomeScene();
        Platform.runLater(Level::getAllLevels);
        stage.show();
    }
}

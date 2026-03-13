package com.example.javaprojektwoche.controllers;

import com.example.javaprojektwoche.Navigator;
import com.example.javaprojektwoche.util.Mode;
import com.example.javaprojektwoche.util.MusicManager;

import com.example.javaprojektwoche.util.Options;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;

import java.util.prefs.Preferences;

public class OptionsController extends NavigatingController {
    public OptionsController(Navigator navigator) {
        super(navigator);
    }

    @Override
    public void back() {
        navigator.showHomeScene();
    }

    @FXML
    private Slider volumeSlider;
    @FXML
    private ComboBox<Mode> skinChoice;

    @FXML
    public void initialize() {
        MediaPlayer mediaPlayer = MusicManager.getMediaPlayer();
        volumeSlider.setMin(0);
        volumeSlider.setMax(100);
        volumeSlider.setValue(mediaPlayer.getVolume() * 100);
        
        mediaPlayer.volumeProperty().bind(volumeSlider.valueProperty().divide(100));
        skinChoice.setValue(Options.getInstance().getMode());
        skinChoice.setItems(FXCollections.observableArrayList(Mode.values()));

        skinChoice.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue == newValue) return;
            Options.getInstance().setMode(newValue);
            MusicManager.getMediaPlayer().volumeProperty().unbind();
            MusicManager.stopMediaPlayer();
            MusicManager.startMusic();
            MusicManager.getMediaPlayer().volumeProperty().bind(volumeSlider.valueProperty().divide(100));

            setScene(scene);
        });

        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if(oldVal.equals(newVal)) return;
            Options.getInstance().setVolume(newVal.intValue());

        });
    }


    public static final Preferences prefs = Preferences.userNodeForPackage(OptionsController.class);
}

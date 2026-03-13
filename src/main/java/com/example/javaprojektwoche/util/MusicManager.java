package com.example.javaprojektwoche.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicManager {

    private static MediaPlayer mediaPlayer;

    public static void startMusic() {
        if (mediaPlayer == null) {
            String musicFile = "/com/example/javaprojektwoche/sound/track.mp3";
            if(Options.getInstance().getMode() == Mode.BINGUS) {
                musicFile = "/com/example/javaprojektwoche/sound/bingus-track.mp3";
            }
            Media music = new Media(MusicManager.class.getResource(musicFile).toExternalForm());

            mediaPlayer = new MediaPlayer(music);
            mediaPlayer.setVolume(Options.getInstance().getVolume() / 100.0);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
    }

    public static void stopMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}

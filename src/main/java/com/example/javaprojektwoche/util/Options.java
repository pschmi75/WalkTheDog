package com.example.javaprojektwoche.util;

import java.util.prefs.Preferences;

public class Options {
    private final Preferences preferenceNode;
    private Mode mode = Mode.NORMAL;
    private int volumePercent = 100;

    private static final String VOLUME_KEY = "VOLUME";
    private static final String MODE_KEY = "MODE";

    private static Options instance = null;

    private Options() {
        preferenceNode = Preferences.userNodeForPackage(getClass());
        mode = Mode.fromString(preferenceNode.get(MODE_KEY, "default"));
        volumePercent = preferenceNode.getInt(VOLUME_KEY, 100);
    }

    public void setVolume(int volumePercent) {
        this.volumePercent = volumePercent;
        preferenceNode.putInt(VOLUME_KEY, volumePercent);
    }

    public int getVolume() {
        return volumePercent;
    };

    public void setMode(Mode mode) {
        if(mode == this.mode) return;
        preferenceNode.put(MODE_KEY, mode.toString());
        this.mode = mode;

    }

    public Mode getMode() {
        return mode;
    }

    public static Options getInstance() {
        if(instance == null) {
            instance = new Options();
        }
        return instance;
    }
}

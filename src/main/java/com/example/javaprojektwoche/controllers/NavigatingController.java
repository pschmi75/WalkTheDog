package com.example.javaprojektwoche.controllers;

import com.example.javaprojektwoche.Navigator;
import com.example.javaprojektwoche.util.Mode;
import com.example.javaprojektwoche.util.Options;
import com.example.javaprojektwoche.util.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;

public abstract class NavigatingController {
    protected final Navigator navigator;
    protected Scene scene;

    private static final String DEFAULT_MODE_CSS = Utils.getResourcePath("/com/example/javaprojektwoche/css/default-mode.css");
    private static final String BINGUS_MODE_CSS = Utils.getResourcePath("/com/example/javaprojektwoche/css/bingus-mode.css");

    protected NavigatingController(Navigator navigator) {
        this.navigator = navigator;
    }

    @FXML
    public void selectHome() {
        navigator.showHomeScene();
    }

    @FXML
    public abstract void back();

    public void setScene(final Scene scene) {
        this.scene = scene;
        Platform.runLater(() -> {
            scene.getStylesheets().removeAll(BINGUS_MODE_CSS, DEFAULT_MODE_CSS);
            scene.getStylesheets().add(Utils.getResourcePath("/com/example/javaprojektwoche/css/global.css"));
            scene.getStylesheets().add(Options.getInstance().getMode() == Mode.BINGUS ? BINGUS_MODE_CSS : DEFAULT_MODE_CSS);
        });
    }
}

package com.example.javaprojektwoche.controllers;

import com.example.javaprojektwoche.Launcher;
import com.example.javaprojektwoche.Navigator;
import com.example.javaprojektwoche.game.Level;
import com.example.javaprojektwoche.game.Level.Difficulty;
import com.example.javaprojektwoche.util.GridManager;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class SelectLevelController extends NavigatingController {
    private final Level.Difficulty difficulty;
    private List<Level> levelList;
    @FXML
    private GridPane levelGrid;
    @FXML
    private Label difficultyLabel;

    @FXML
    private AnchorPane anchorpane;

    public SelectLevelController(Navigator navigator, Level.Difficulty difficulty) {
        super(navigator);

        this.difficulty = difficulty;

        levelList = new ArrayList<>();
    }

    @FXML
    public void initialize() {
        difficultyLabel.setText(
            switch (this.difficulty) {
                case Difficulty.STARTER -> "Starter Levels";
                case Difficulty.JUNIOR -> "Junior Levels";
                case Difficulty.EXPERT -> "Expert Levels";
                case Difficulty.MASTER -> "Master Levels";
            }
        );

        List<Level> levels = Level.getAllLevels();

        int row = 0;
        int column = 0;
        for(int i = 0; i < levels.size(); i++) {
            if(levels.get(i).getDifficulty() == difficulty) {
                levelList.add(levels.get(i));

                if (column > 1) {
                    row++;
                    column = 0;

                    if (row > 1) {
                        levelGrid.setPrefHeight(levelGrid.getPrefHeight()+260);
                        levelGrid.getRowConstraints().clear();

                        for (int j = 0; j < row + 1; j++) {
                            RowConstraints rc = new RowConstraints();
                            rc.setPercentHeight(100.0 / (row + 1));
                            levelGrid.getRowConstraints().add(rc);
                        }
                    }
                }

                final Label levelLabel = new Label();
                final Pane hoverOverlay = new Pane();
                levelLabel.setText("Level " + levels.get(i).getNumber());
                levelLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24; -fx-font-family: 'Monospace'; -fx-font-weight: bold");
                final GridPane layer1 = new GridPane();
                final GridPane layer2 = new GridPane();
                hoverOverlay.getStyleClass().add("level-button");
                int index = levelList.size() - 1;
                hoverOverlay.setOnMouseClicked((e) -> selectLevel(index));
                layer1.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-style: dashed;");
                layer2.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-style: dashed;");
                for (int j = 0; j < 4; j++) {
                    ColumnConstraints cc = new ColumnConstraints();
                    RowConstraints rc = new RowConstraints();
                    cc.setPercentWidth(25);
                    rc.setPercentHeight(25);
                    layer1.getRowConstraints().add(rc);
                    layer2.getRowConstraints().add(rc);
                    layer1.getColumnConstraints().add(cc);
                    layer2.getColumnConstraints().add(cc);
                }


                for(int r = 0; r < 4; r++) {
                    for(int  c = 0; c < 4; c++) {
                        Button b1 = new Button();
                        Button b2 = new Button();
                        b1.setMaxHeight(Double.MAX_VALUE);
                        b1.setMaxWidth(Double.MAX_VALUE);
                        b2.setMaxHeight(Double.MAX_VALUE);
                        b2.setMaxWidth(Double.MAX_VALUE);
                        b1.getStyleClass().add("button");
                        b2.getStyleClass().add("button");
                        layer1.add(b1, c, r);
                        layer2.add(b2, c, r);
                    }
                }

                hoverOverlay.setMaxHeight(Double.MAX_VALUE);
                hoverOverlay.setMaxWidth(Double.MAX_VALUE);
                levelGrid.add(layer1, column, row);
                levelGrid.add(layer2, column, row);
                levelGrid.add(levelLabel, column, row);
                levelGrid.add(hoverOverlay, column, row);
                GridPane.setHalignment(levelLabel, HPos.CENTER);
                GridPane.setValignment(levelLabel, VPos.TOP);
                GridPane.setMargin(hoverOverlay, new Insets(50, 50, 50, 50));
                GridPane.setMargin(levelLabel, new Insets(15, 20, 20, 20));
                GridPane.setMargin(layer1, new Insets(50, 50, 50, 50));
                GridPane.setMargin(layer2, new Insets(50, 50, 50, 50));
                final Level l = levels.get(i);
                
                Platform.runLater(() -> {
                    new GridManager(layer1, layer2).updateGrid(l.getInitial());
                });

                column++;
                layer1.setId("grid");
                layer2.setId("leash-grid");
            }
        }


    }

    private void selectLevel(int id) {
        Level level = levelList.get(id);
        navigator.showGameScene(level);
    }

    @Override
    public void back() {
        navigator.showDifficultyScene();
    }
}

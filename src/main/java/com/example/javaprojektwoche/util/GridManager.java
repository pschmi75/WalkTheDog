package com.example.javaprojektwoche.util;

import com.example.javaprojektwoche.controllers.OptionsController;
import com.example.javaprojektwoche.game.Board;
import com.example.javaprojektwoche.game.Leash;
import com.example.javaprojektwoche.game.figures.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.Map.Entry;
import java.util.Objects;


public class GridManager {
    private static final String[] BOARD_CLASSES = {
            "person-blue", "person-yellow", "person-red", "person-white",
            "dog-blue", "dog-yellow", "dog-red", "dog-white",
            "cat", "tree", "title",
            "leash-h", "leash-v",
            "leash-se", "leash-s", "leash-sw",
            "leash-ne", "leash-n", "leash-nw",
            "leash-w", "leash-e",
            "bingus-person-blue", "bingus-person-yellow", "bingus-person-red", "bingus-person-white",
            "bingus-dog-blue", "bingus-dog-yellow", "bingus-dog-red", "bingus-dog-white",
            "bingus-cat", "bingus-tree", "bingus-background", "bingus-title"
    };
    private final GridPane figurePane;
    private final GridPane overlayPane;

    public GridManager( GridPane figurePane, GridPane overlayPane) {
        this.figurePane = figurePane;
        this.overlayPane = overlayPane;
    }

    public void updateGrid(Board board) {
        for(Entry<Node, Node> nodePair: Utils.zip(figurePane.getChildren(), overlayPane.getChildren())) {
            Button button = (Button)nodePair.getKey();
            Button overlay = (Button)nodePair.getValue();
            Point position = Utils.getPosition(button);
            button.getStyleClass().removeAll(BOARD_CLASSES);
            overlay.getStyleClass().removeAll(BOARD_CLASSES);
            Figure containedFigure = board.get(position);



            if(containedFigure instanceof Dog dog) {
                button.getStyleClass().add("dog-" + switch(dog.getOwnerColor()) {
                    case Person.Color.WHITE -> "white";
                    case Person.Color.BLUE -> "blue";
                    case Person.Color.RED -> "red";
                    case Person.Color.YELLOW -> "yellow";
                });
            } else if(containedFigure instanceof Cat) {
                button.getStyleClass().add("cat");
            } else if (containedFigure instanceof Tree) {
                button.getStyleClass().add("tree");
            } else if (containedFigure instanceof Person person) {
                button.getStyleClass().add("person-" + switch(person.getColor()) {
                    case Person.Color.WHITE -> "white";
                    case Person.Color.BLUE -> "blue";
                    case Person.Color.RED -> "red";
                    case Person.Color.YELLOW -> "yellow";
                });
            }

            String leashClass = getLeashClass(board, position);
            if(leashClass != null) {
                overlay.getStyleClass().add(leashClass);
            }

        }


    }

    private static String[][] START_LOOKUP = {
            {null, "leash-w", null},
            {"leash-n", null, "leash-s"},
            {null, "leash-e", null}
    };

    private static String[][] TREE_LOOKUP = {
            {"leash-se", null, "leash-ne"},
            null,
            {"leash-sw", null, "leash-nw"}
    };

    private String getLeashClass(Board board, Point position) {
        for(Leash leash : board.getAllLeashes()) {
            if (!leash.getAllPoints().contains(position)) {
                continue;
            }
            Point personPos = leash.getPerson().getPosition();
            Point dogPos = leash.getDog().getPosition();

            if(leash.getTree() == null) {
                if(personPos.equals(position)) {
                    int sx = Integer.compare(dogPos.getX(), personPos.getX());
                    int sy = Integer.compare(dogPos.getY(), personPos.getY());
                    return START_LOOKUP[1 + sx][1 + sy];
                } else if(dogPos.equals(position)) {
                    int sx = Integer.compare(personPos.getX(), dogPos.getX());
                    int sy = Integer.compare(personPos.getY(), dogPos.getY());
                    return START_LOOKUP[1 + sx][1 + sy];
                } else {
                    return personPos.getX() == dogPos.getX() ? "leash-v" : "leash-h";
                }
            } else {
                Point treePos = leash.getTree().getPosition();
                if(personPos.equals(position)) {
                    int sx = Integer.compare(treePos.getX(), personPos.getX());
                    int sy = Integer.compare(treePos.getY(), personPos.getY());
                    return START_LOOKUP[1 + sx][1 + sy];
                } else if(dogPos.equals(position)) {
                    int sx = Integer.compare(treePos.getX(), dogPos.getX());
                    int sy = Integer.compare(treePos.getY(), dogPos.getY());
                    return START_LOOKUP[1 + sx][1 + sy];
                } else if (treePos.equals(position) && !Utils.threeWayEquals(position.getX(), personPos.getX(), dogPos.getX()) && !Utils.threeWayEquals(position.getY(), personPos.getY(), dogPos.getY())) {
                    // Selbe Y Wert -> Vergleiche x und andersrum
                    Point xPoint = position.getY() == personPos.getY() ? personPos : dogPos;
                    Point yPoint = xPoint == personPos ? dogPos : personPos;
                    int sx = Integer.compare(position.getX(), xPoint.getX());
                    int sy = Integer.compare(position.getY(), yPoint.getY());

                    return TREE_LOOKUP[1 + sx][1 + sy];

                } else {
                    return position.getX() == treePos.getX() ? "leash-v" : "leash-h";
                }
            }

        }
        return null;
    }
};


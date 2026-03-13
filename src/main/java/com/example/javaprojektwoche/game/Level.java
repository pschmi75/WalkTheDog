package com.example.javaprojektwoche.game;

import com.example.javaprojektwoche.game.figures.*;
import com.example.javaprojektwoche.game.figures.Person.Color;
import com.example.javaprojektwoche.util.Point;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


public class Level {
    private final int number;
    private final Board board;
    private final Difficulty difficulty;

    public Level(int number, Board board, Difficulty difficulty) {
        this.number = number;
        this.board = board;
        this.difficulty = difficulty;
    }

    private static List<Level> levels;

    public static List<Level> getAllLevels() {
        if(levels != null) return levels;
        ObjectMapper mapper = new ObjectMapper();
        levels = new ArrayList<>();
        String levelsDir = Objects.requireNonNull(Level.class.getResource("/com/example/javaprojektwoche/levels")).getPath();
        File folder = new File(levelsDir);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            try {
                JsonNode root = mapper.readTree(file);
                JsonNode figuresRoot = root.get("figures");
                JsonNode leashesRoot = root.get("leashes");

                List<Figure> figureList = new ArrayList<>();
                List<Leash> leashList = new ArrayList<>();

                int levelNumber = Integer.parseInt(file.getName().split("\\.")[0].substring(5));

                if (figuresRoot.isArray()) {
                    for (JsonNode figureRoot : figuresRoot) {
                        Figure figure = new Person();

                        switch (figureRoot.get("type").asText()) {
                            case "person" ->
                                    figure = new Person(new Point(figureRoot.get("x").asInt(), figureRoot.get("y").asInt()), switch (figureRoot.get("color").asText()) {
                                        case "blue" -> Color.BLUE;
                                        case "yellow" -> Color.YELLOW;
                                        case "red" -> Color.RED;
                                        default -> Color.WHITE;
                                    });
                            case "dog" ->
                                    figure = new Dog(new Point(figureRoot.get("x").asInt(), figureRoot.get("y").asInt()));
                            case "cat" ->
                                    figure = new Cat(new Point(figureRoot.get("x").asInt(), figureRoot.get("y").asInt()));
                            case "tree" ->
                                    figure = new Tree(new Point(figureRoot.get("x").asInt(), figureRoot.get("y").asInt()));
                        }
                        figureList.add(figure);
                    }
                }

                if (leashesRoot != null) {
                    for (JsonNode leashRoot : leashesRoot) {
                        Person master = null;
                        Dog dog = null;
                        Tree tree = null;
                        Point treePosition = null;
                        Leash leash;

                        String nodePosition = leashRoot.get("master").asText();
                        String[] coordinates = nodePosition.split(":");
                        Point masterPosition = new Point(Integer.parseInt(coordinates[0]) , Integer.parseInt(coordinates[1]));
                       
                        if(leashRoot.has("tree")){
                            nodePosition = leashRoot.get("tree").asText();
                            coordinates = nodePosition.split(":");
                            treePosition = new Point(Integer.parseInt(coordinates[0]) , Integer.parseInt(coordinates[1]));
                        } 

                        nodePosition = leashRoot.get("dog").asText();
                        coordinates = nodePosition.split(":");
                        Point dogPosition = new Point(Integer.parseInt(coordinates[0]) , Integer.parseInt(coordinates[1]));

                        for(Figure figure: figureList) {
                            if(figure.getPosition().equals(dogPosition)) {
                                dog = (Dog) figure;
                            } else if(figure.getPosition().equals(masterPosition)) {
                                master = (Person) figure;
                            } else if(figure.getPosition().equals(treePosition)) {
                                tree = (Tree) figure;
                            }
                        }
                        Color mastercolor = master.getColor();
                        dog.setOwnerColor(mastercolor);

                        leash = new Leash(master, tree, dog, mastercolor.ordinal());
                        leashList.add(leash);
                    }
                }
                Level readLevel = new Level(levelNumber, new Board(figureList, leashList), switch (root.get("difficulty").asText()) {
                    case "starter" -> Difficulty.STARTER;
                    case "junior" -> Difficulty.JUNIOR;
                    case "expert" -> Difficulty.EXPERT;
                    case "master" -> Difficulty.MASTER;
                    default -> Difficulty.JUNIOR;
                });

                levels.add(readLevel);

            } catch (IOException e) {
                System.out.print(e);
            }
        }

        levels.sort(Comparator.comparingInt(Level::getNumber));
        return levels;
    }



    public int getNumber() {
        return number;
    }

    public Board getInitial() {
        return new Board(board);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Board getBoard(){
        return board;
    }

    public enum Difficulty {
        STARTER, JUNIOR, EXPERT, MASTER
    }

    @Override
    public boolean equals(Object other) {
        if(other == this) return true;
        else if(other instanceof Level level) return level.getNumber() == getNumber();
        else return false;
    }
}

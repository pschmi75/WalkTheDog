package com.example.javaprojektwoche.controllers;

import com.example.javaprojektwoche.Navigator;
import com.example.javaprojektwoche.edit.EditManager;
import com.example.javaprojektwoche.edit.LeashEdit;
import com.example.javaprojektwoche.edit.PlaceFigureEdit;
import com.example.javaprojektwoche.edit.RemoveFigureEdit;
import com.example.javaprojektwoche.game.Board;
import com.example.javaprojektwoche.game.GameException;
import com.example.javaprojektwoche.game.Leash;
import com.example.javaprojektwoche.game.Level;
import com.example.javaprojektwoche.game.figures.Dog;
import com.example.javaprojektwoche.game.figures.Figure;
import com.example.javaprojektwoche.game.figures.Person;
import com.example.javaprojektwoche.game.figures.Tree;
import com.example.javaprojektwoche.util.GridManager;
import com.example.javaprojektwoche.util.HotbarMode;
import com.example.javaprojektwoche.util.Point;
import com.example.javaprojektwoche.util.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.List;
import java.util.stream.Collectors;


public class GameController extends NavigatingController {


    private final Level level;
    private final EditManager edits = new EditManager();
    private Board board;
    private HotbarMode mode = HotbarMode.NONE;
    private Leash.LeashBuilder currentLeash = null;
    private GridManager gridManager;

    @FXML
    private Label errorLabel;
    @FXML
    private Label title;
    @FXML
    private GridPane boardContainer;
    @FXML
    private GridPane boardContainer1;
    @FXML
    private Label dog_selector;
    @FXML
    private Label person_selector;
    @FXML
    private Label leash_selector;
    @FXML
    private Label trash_selector;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button dog_button;
    @FXML
    private Button person_button;

    public GameController(Navigator navigator, Level level) {
        super(navigator);
        this.level = level;
    }

    @FXML
    public void initialize() {
        gridManager = new GridManager(boardContainer, boardContainer1);
        reset();
        title.setText("Level " + level.getNumber());

        dog_button.getStyleClass().add("dog-white");
        person_button.getStyleClass().add("person-white");
    }

    @FXML
    public void tileClick(MouseEvent event) {
        Button button = (Button) event.getTarget();

        Point position = Utils.getPosition(button);
        try {
            switch (mode) {
                case DOG -> {
                    Dog dog = new Dog(position);
                    board.place(dog);
                    selectMode(HotbarMode.NONE);
                    edits.registerEdit(new PlaceFigureEdit(dog));
                }
                case PERSON -> {
                    Person person = new Person(position);
                    board.place(person);
                    selectMode(HotbarMode.NONE);
                    edits.registerEdit(new PlaceFigureEdit(person));
                }

                case TRASH -> {
                    Pair<Figure, Leash> deleted = board.delete(position);
                    edits.registerEdit(new RemoveFigureEdit(deleted.getKey(), deleted.getValue()));
                }
                case LEASH -> {
                    if (currentLeash == null) {
                        currentLeash = new Leash.LeashBuilder(board);
                    }
                    Figure figure = board.get(position);
                    if (figure instanceof Person person) {
                        currentLeash.addPerson(person);
                    } else if (figure instanceof Tree tree) {
                        currentLeash.addTree(tree);
                    } else if (figure instanceof Dog dog) {
                        currentLeash.addDog(dog);
                    }

                    if (currentLeash.isComplete()) {
                        Leash leash = currentLeash.build();
                        board.connect(leash);
                        currentLeash = null;
                        selectMode(HotbarMode.NONE);
                        edits.registerEdit(new LeashEdit(leash));
                    }
                }
                default -> {
                    return;
                }
            }
        } catch (GameException error) {
            showErrorMessage(error);
        }
        gridManager.updateGrid(board);
        checkWin();
    }

    @FXML
    public void reset() {
        board = level.getInitial();
        gridManager.updateGrid(board);
    }

    @FXML
    public void redo() {
        edits.redoLast(board);
        gridManager.updateGrid(board);
    }

    @FXML
    public void undo() {
        edits.undoLast(board);
        gridManager.updateGrid(board);
    }

    @FXML
    public void selectDog() {
        if (mode != HotbarMode.DOG) {
            selectMode(HotbarMode.DOG);
        } else {
            selectMode(HotbarMode.NONE);
        }
    }

    @FXML
    public void selectPerson() {
        if (mode != HotbarMode.PERSON) {
            selectMode(HotbarMode.PERSON);
        } else {
            selectMode(HotbarMode.NONE);
        }
    }

    @FXML
    public void selectLeash() {
        if (mode != HotbarMode.LEASH) {
            selectMode(HotbarMode.LEASH);
        } else {
            selectMode(HotbarMode.NONE);
        }
    }

    @FXML
    public void selectTrash() {
        if (mode != HotbarMode.TRASH) {
            selectMode(HotbarMode.TRASH);
        } else {
            selectMode(HotbarMode.NONE);
        }
    }

    @Override
    public void back() {
        navigator.showConfirm(level, scene);
    }

    private void showErrorMessage(GameException ex) {
        errorLabel.setText(ex.getUserMessage());
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> errorLabel.setText(""));
        }).start();
    }

    private void checkWin() {
        List<Leash> leashes = board.getAllLeashes();
        if (leashes.size() != 3) return;
        List<Person> persons = leashes.stream().map(Leash::getPerson).collect(Collectors.toList());
        List<Dog> dogs = leashes.stream().map(Leash::getDog).collect(Collectors.toList());
        int unleashedDogs = 0;
        int totalDogs = 0;
        for (Figure figure : board.getAllFigures()) {
            if (figure instanceof Person person) {
                if (persons.contains(person)) {
                    persons.remove(person); // Person wasn't holding a leash
                } else {
                    return;
                }
            } else if (figure instanceof Dog dog) {
                totalDogs++;
                if (!dogs.remove(dog)) {
                    unleashedDogs++;
                }
            }
        }

        if (totalDogs == 4 && unleashedDogs == 1 && persons.isEmpty() && dogs.isEmpty()) {
            navigator.showEndScene(level);
        }

    }

    private void selectMode(HotbarMode mode) {
        leash_selector.setId(null);
        dog_selector.setId(null);
        person_selector.setId(null);
        trash_selector.setId(null);

        this.mode = mode;
        Cursor modeCursor = mode.getCursor();
        anchorPane.setCursor(modeCursor);
        switch (mode) {
            case DOG -> {
                dog_selector.setId("selected-tool");
                person_selector.setCursor(modeCursor);
            }
            case PERSON -> {
                person_selector.setId("selected-tool");
                person_selector.setCursor(modeCursor);
            }
            case LEASH -> {
                leash_selector.setId("selected-tool");
                person_selector.setCursor(modeCursor);
            }
            case TRASH -> {
                trash_selector.setId("selected-tool");
                person_selector.setCursor(modeCursor);
            }
        }
    }
}

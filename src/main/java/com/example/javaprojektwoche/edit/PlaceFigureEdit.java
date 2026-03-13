package com.example.javaprojektwoche.edit;

import com.example.javaprojektwoche.game.Board;
import com.example.javaprojektwoche.game.GameException;
import com.example.javaprojektwoche.game.figures.Figure;

public class PlaceFigureEdit extends Edit {
    private final Figure figure;
    public PlaceFigureEdit(Figure figure) {
        this.figure = figure;
    }

    @Override
    public void undo(Board board) {
        board.delete(figure.getPosition());
    }

    @Override
    public void redo(Board board) {
        try {
            board.place(figure);
        } catch (GameException ignored) {

        }
    }
}

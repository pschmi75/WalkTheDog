package com.example.javaprojektwoche.edit;

import com.example.javaprojektwoche.game.Board;
import com.example.javaprojektwoche.game.GameException;
import com.example.javaprojektwoche.game.Leash;
import com.example.javaprojektwoche.game.figures.Figure;

public class RemoveFigureEdit extends Edit {
    private final Figure figure;
    private final Leash leash;
    public RemoveFigureEdit(Figure figure, Leash leash) {
        this.figure = figure;
        this.leash = leash;
    }

    @Override
    public void undo(Board board) {
        try {
            if(figure != null) board.place(figure);
            if(leash != null) board.connect(leash);
        } catch (GameException ignored) {

        }
    }

    @Override
    public void redo(Board board) {
        if(figure != null) board.delete(figure.getPosition());
        if(leash != null) board.undoLeash(leash);
    }
}

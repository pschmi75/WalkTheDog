package com.example.javaprojektwoche.edit;

import com.example.javaprojektwoche.game.Board;
import com.example.javaprojektwoche.game.GameException;
import com.example.javaprojektwoche.game.Leash;

public class LeashEdit extends Edit {
    private final Leash leash;
    public LeashEdit(Leash leash) {
        this.leash = leash;
    }

    @Override
    void undo(Board board) {
        board.undoLeash(leash);
    }

    @Override
    void redo(Board board) {
        try {
            board.connect(leash);
        } catch (GameException ignored) {

        }
    }
}

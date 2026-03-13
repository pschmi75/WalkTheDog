package com.example.javaprojektwoche.edit;

import com.example.javaprojektwoche.game.Board;

public abstract class Edit {
    abstract void undo(Board board);
    abstract void redo(Board board);
}

package com.example.javaprojektwoche.edit;

import com.example.javaprojektwoche.game.Board;

import java.util.LinkedList;

public class EditManager {
    private final LinkedList<Edit> done = new LinkedList<>();
    private final LinkedList<Edit> undone = new LinkedList<>();

    public void registerEdit(Edit edit) {
        done.addFirst(edit);
        undone.clear();
    }

    public void undoLast(Board board) {
        if(done.isEmpty()) return;
        Edit toUndo = done.pop();

        toUndo.undo(board);
        undone.addFirst(toUndo);
    }

    public void redoLast(Board board) {
        if(undone.isEmpty()) return;
        Edit toRedo = undone.pop();
        toRedo.redo(board);
        done.addFirst(toRedo);
    }
}

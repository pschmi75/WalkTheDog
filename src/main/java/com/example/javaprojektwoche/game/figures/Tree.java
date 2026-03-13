package com.example.javaprojektwoche.game.figures;

import com.example.javaprojektwoche.game.Board;
import com.example.javaprojektwoche.util.Point;

public class Tree extends Figure {

    public Tree(Point position) {
        super(position);
    }

    @Override
    public Figure clone() {
        return new Tree(position);
    }
}

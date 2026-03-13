package com.example.javaprojektwoche.game.figures;
import com.example.javaprojektwoche.game.Board;
import com.example.javaprojektwoche.game.GameException;
import com.example.javaprojektwoche.util.*;

public abstract class Figure {
    Point position;

    public Figure() {
        this.position = new Point(0, 0);
    }

    public Figure(Point position){
        this.position = position;
    }

    public Point getPosition(){
        return this.position;
    }

    public void checkValid(Board board) throws GameException{
       if (board.get(position) != null){
           throw new GameException("There is already a figure in this tile");
       }
    }

    public abstract Figure clone();

    @Override
    public boolean equals(Object object) {
        if(this == object) return true;
        else if(object instanceof Figure figure) return figure.getPosition().equals(position);
        else return false;
    }
}

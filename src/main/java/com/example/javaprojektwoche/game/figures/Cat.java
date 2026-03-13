package com.example.javaprojektwoche.game.figures;

import com.example.javaprojektwoche.game.Board;
import com.example.javaprojektwoche.game.GameException;
import com.example.javaprojektwoche.util.Point;

public class Cat extends Figure {

    public Cat(Point position){
        super(position);
    }

    public void checkValid(Board board) throws GameException {
        if(isDogNearby(board, "N") || isDogNearby(board, "S") || isDogNearby(board, "E") || isDogNearby(board, "W")){
            throw new GameException("");
        }
    }

    public boolean isDogNearby(Board board, String direction){
        return switch (direction) {
            case "N" ->
                    position.getX() + 1 <= 3 && board.get(new Point(position.getX() + 1, position.getY())) instanceof Dog;
            case "E" ->
                    position.getY() + 1 <= 3 && board.get(new Point(position.getX(), position.getY() + 1)) instanceof Dog;
            case "S" ->
                    position.getX() - 1 >= 0 && board.get(new Point(position.getX() - 1, position.getY())) instanceof Dog;
            case "W" ->
                    position.getY() - 1 >= 0 && board.get(new Point(position.getX(), position.getY() - 1)) instanceof Dog;
            default -> false;
        };
    }

    @Override
    public Figure clone() {
        return new Cat(position);
    }
}

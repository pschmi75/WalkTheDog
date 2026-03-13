package com.example.javaprojektwoche.game.figures;

import com.example.javaprojektwoche.game.Board;
import com.example.javaprojektwoche.game.GameException;
import com.example.javaprojektwoche.util.Point;

public class Dog extends Figure{
    private Person.Color ownerColor = Person.Color.WHITE;
    public Dog(Point position) {
        super(position);
    }

    public void checkValid(Board board) throws GameException{
        if (isCatNearby(board, "N") || isCatNearby(board, "E") || isCatNearby(board, "S") || isCatNearby(board, "W")){
            throw new GameException("A dog cannot be placed next to a cat");
        }
        if (isDogNearby(board, "N") || isDogNearby(board, "E") || isDogNearby(board, "S") || isDogNearby(board, "W")){
            throw new GameException("A dog cannot be placed next to another dog");
        }
        if(getAmountOfDogs(board) == 4){
            throw new GameException("There can only be 4 dogs on the grid");
        }
    }

    public void setOwnerColor(Person.Color color) {
        ownerColor = color;
    }

    public Person.Color getOwnerColor() {
        return ownerColor;
    }

    public boolean isCatNearby(Board board, String direction){
        return switch (direction) {
            case "N" ->
                    position.getX() + 1 <= 3 && board.get(new Point(position.getX() + 1, position.getY())) instanceof Cat;
            case "E" ->
                    position.getY() + 1 <= 3 && board.get(new Point(position.getX(), position.getY() + 1)) instanceof Cat;
            case "S" ->
                    position.getX() - 1 >= 0 && board.get(new Point(position.getX() - 1, position.getY())) instanceof Cat;
            case "W" ->
                    position.getY() - 1 >= 0 && board.get(new Point(position.getX(), position.getY() - 1)) instanceof Cat;
            default -> false;
        };
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

    public int getAmountOfDogs(Board board){
        int amount = 0;
        for (Figure figure: board.getAllFigures()){
            if(figure instanceof Dog){
                amount++;
            }
        }
        return amount;
    }

    @Override
    public Figure clone() {
        Dog dog = new Dog(position);
        dog.setOwnerColor(ownerColor);
        return dog;
    }
}

package com.example.javaprojektwoche.game.figures;


import com.example.javaprojektwoche.game.Board;
import com.example.javaprojektwoche.game.GameException;
import com.example.javaprojektwoche.util.Point;

public class Person extends Figure {

    private Color color;

    public Person() {
        super();
        this.color = Color.WHITE;
    }

    public Person(Point position) {
        super(position);
        this.color = Color.WHITE;
    }

    public Person(Point position, Color color) {
        super(position);
        this.color = color;
    }

    public void checkValid(Board board) throws GameException {
        if(getAmountOfPerson(board) == 3){
            throw new GameException("There can only be 3 people on the grid");
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getAmountOfPerson(Board board){
        int amount = 0;
        for (Figure figure: board.getAllFigures()){
            if(figure instanceof Person){
                amount++;
            }
        }
        return amount;
    }

    public enum Color {
        WHITE,
        BLUE,
        YELLOW,
        RED
    }

    @Override
    public Figure clone() {
        return new Person(position, color);
    }

    @Override
    public boolean equals(Object object) {
        if(!super.equals(object)) return false;
        else if(object instanceof Person person) return person.getColor() == color;
        else return false;
    }
}

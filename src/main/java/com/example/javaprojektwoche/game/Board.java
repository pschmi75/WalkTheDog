package com.example.javaprojektwoche.game;

import com.example.javaprojektwoche.game.figures.Figure;
import com.example.javaprojektwoche.game.figures.Person;
import com.example.javaprojektwoche.util.Point;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<Figure> initialFigureList;
    private final List<Leash> initialLeashList;
    List<Figure> figureList = new ArrayList<>();
    List<Leash> leashList = new ArrayList<>();

    public Board(List<Figure> figureList, List<Leash> leashList) {
        this.initialFigureList = figureList.stream().map(Figure::clone).toList();
        this.initialLeashList = leashList.stream().map(Leash::new).toList();
        this.figureList = figureList;
        this.leashList = leashList;
    }

    public Board(Board other) {
       this(new ArrayList<>(other.figureList.stream().map(Figure::clone).toList()), new ArrayList<>(other.leashList.stream().map(Leash::new).toList()));
    }

    public void place(Figure figure) throws GameException {
        figure.checkValid(this);
        figureList.add(figure);
    }

    public void connect(Leash leash) throws GameException {
        for (Leash existingLeash :leashList){
            if(existingLeash.getLeashLength() == leash.getLeashLength()){
                throw new GameException("Leash with that length already exists");
            }
        }
        Person.Color color = Person.Color.values()[leash.getLeashLength()];
        if (leash.getPerson().getColor() == Person.Color.WHITE) {
            leash.getPerson().setColor(color);
        } else if(leash.getPerson().getColor() != color){
            throw new GameException("Incorrect leash length");
        }
        leash.getDog().setOwnerColor(color);


        leashList.add(leash); // Muss nach der Schleife sein weil bei der allerersten leash ist die liste leer
        
    }

    public Figure get(Point position) {
        for (Figure figure: figureList){
            if(figure.getPosition().equals(position)){
                return figure;
            }
        }
        return null;
    }

    public Leash getLeash(Point position){
        for(Leash leash: leashList){
            if(leash.getAllPoints().contains(position)){
                return leash;
            }
        }
        return null;
    }

    public Pair<Figure, Leash> delete(Point position) {
        Leash deleteLeash = null;
        for (Leash leash: leashList){
            if(leash.getAllPoints().contains(position) && !initialLeashList.contains(leash)){
                deleteLeash = leash;
            }
        }
        if(deleteLeash != null) {
            undoLeash(deleteLeash);
        }
        Figure toRemove = null;
        for(Figure figure: figureList) {
            if(!initialFigureList.contains(figure) && figure.getPosition().equals(position)) {
                toRemove = figure;
            }
        }
        if(toRemove != null) {
            figureList.remove(toRemove);
        }
        return new Pair<>(toRemove, deleteLeash);
    }

    public void undoLeash(Leash leash) {
        leashList.remove(leash);
        if(!initialFigureList.contains(leash.getPerson())) {
            leash.getPerson().setColor(Person.Color.WHITE);
        }
        leash.getDog().setOwnerColor(Person.Color.WHITE);
    }

    public List<Leash> getAllLeashes() {
        return leashList;
    }

    public List<Figure> getAllFigures() {
        return figureList;
    }
}

package com.example.javaprojektwoche.game;

import com.example.javaprojektwoche.game.figures.Dog;
import com.example.javaprojektwoche.game.figures.Figure;
import com.example.javaprojektwoche.game.figures.Person;
import com.example.javaprojektwoche.game.figures.Tree;
import com.example.javaprojektwoche.util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Leash {
    private int length;
    private Person person;
    private Tree tree;
    private Dog dog;

    public Leash (Person person, Tree tree, Dog dog, int length){
        this.length = length;
        this.person = person;
        this.tree = tree;
        this.dog = dog;
    }

    public Leash(Leash other) {
        this((Person)other.getPerson().clone(), other.getTree() == null ? null: (Tree)other.getTree().clone(), (Dog)other.dog.clone(), other.getLeashLength());
    }

    public static class LeashBuilder {
        private Person person;
        private Tree tree;
        private Dog dog;
        Board board;

        public LeashBuilder(Board board)  {
            this.board = board;
        }

        public void addPerson(Person person) throws GameException {
            for(Leash leashes: board.getAllLeashes()){
                if(leashes.getPerson().equals(person)){
                    throw new GameException("This person already has a leash");
                }
            }
            if(this.person != null){
                this.tree = null;
                this.dog = null;
            }
            this.person = person;
        }

        public void addTree(Tree tree) throws GameException {
            for(Leash leashes: board.getAllLeashes()){
                if(Objects.equals(leashes.getTree(), tree)){
                    throw new GameException("This tree already has a leash");
                }
            }
            if(this.tree != null){
                this.person = null;
                this.dog = null;
            }
            this.tree = tree;
        }

        public void addDog(Dog dog) throws GameException {
            for(Leash leashes: board.getAllLeashes()){
                if(leashes.getDog().equals(dog)){
                    throw new GameException("This dog already has a leash");
                }
            }
            if(this.dog != null){
                this.person = null;
                this.tree = null;
            }
            this.dog = dog;
        }

        public boolean isComplete() throws GameException {
            if(person == null || dog == null) return false;
            if(tree == null){
                return isStraight(person, dog);
            }else{
                return isStraight(person, tree) && isStraight(tree, dog);
            }
        }

        public boolean isStraight(Figure figure1, Figure figure2) throws GameException{
            if((figure1.getPosition().getX() == figure2.getPosition().getX()) || (figure1.getPosition().getY() == figure2.getPosition().getY())){
                return true;
            }else{
                throw new GameException("Leashes have to be straight");
            }
        }

        public int getWholeLength() throws GameException{
            int leashLength = 0;
            if(this.tree == null){
                if(isStraight(this.person, this.dog)){
                    leashLength = Math.abs((person.getPosition().getX() - dog.getPosition().getX()) + (person.getPosition().getY() - dog.getPosition().getY()));
                }else{
                    throw new GameException("Leine zu lang!");
                }
            }else{
                if(isStraight(this.person, this.tree) && isStraight(this.tree, this.dog)){
                    int personToTree = Math.abs((person.getPosition().getX() - tree.getPosition().getX()) + (person.getPosition().getY() - tree.getPosition().getY()));
                    int treeToDog = Math.abs((tree.getPosition().getX() - dog.getPosition().getX()) + (tree.getPosition().getY() - dog.getPosition().getY()));
                    leashLength = personToTree + treeToDog;
                }else{
                    throw new GameException("Leine darf nicht diagonal sein!");
                }
            }
            return leashLength;
        }

        private int getLengthOfTwo(Figure figure1, Figure figure2){
            return Math.abs((figure1.getPosition().getX() - figure2.getPosition().getX()) + (figure1.getPosition().getY() - figure2.getPosition().getY()));
        }

        public boolean intersects(){
            if(tree == null){
                if(getLengthOfTwo(person, dog) >= 2){
                    if(person.getPosition().getX() == dog.getPosition().getX()){
                        if(person.getPosition().getY() < dog.getPosition().getY()){
                            for (int row = person.getPosition().getY() + 1; row < dog.getPosition().getY(); row++){
                                if(board.get(new Point(person.getPosition().getX(), row)) instanceof Tree){
                                    return  board.getLeash(new Point(person.getPosition().getX(), row)) != null;
                                }else {
                                    if (board.get(new Point(person.getPosition().getX(), row)) != null || board.getLeash(new Point(person.getPosition().getX(), row)) != null) {
                                        return true;
                                    }
                                }
                            }
                        }else if(person.getPosition().getY() > dog.getPosition().getY()){
                            for (int row = dog.getPosition().getY() + 1; row < person.getPosition().getY(); row++){
                                if(board.get(new Point(person.getPosition().getX(), row)) instanceof Tree){
                                    return board.getLeash(new Point(person.getPosition().getX(), row)) != null;
                                }else {
                                    if (board.get(new Point(person.getPosition().getX(), row)) != null || board.getLeash(new Point(person.getPosition().getX(), row)) != null) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }else{
                        if(person.getPosition().getX() < dog.getPosition().getX()){
                            for (int column = person.getPosition().getX() + 1; column < dog.getPosition().getX() ; column++){
                                if(board.get(new Point(column, person.getPosition().getY())) instanceof Tree){
                                    return board.getLeash(new Point(column, person.getPosition().getY())) != null;
                                }else {
                                    if (board.get(new Point(column, person.getPosition().getY())) != null || board.getLeash(new Point(column, person.getPosition().getY())) != null) {
                                        return true;
                                    }
                                }
                            }
                        }else if(person.getPosition().getX() > dog.getPosition().getX()){
                            for (int column = dog.getPosition().getX() + 1; column < person.getPosition().getX(); column++){
                                if(board.get(new Point(column, person.getPosition().getY())) instanceof Tree){
                                    return board.getLeash(new Point(column, person.getPosition().getY())) != null;
                                }else {
                                    if (board.get(new Point(column, person.getPosition().getY())) != null || board.getLeash(new Point(column, person.getPosition().getY())) != null) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }else{
                    return false;
                }
            }else{
                if(getLengthOfTwo(person, tree) == 2){
                    if(person.getPosition().getX() == tree.getPosition().getX()){
                        if(person.getPosition().getY() < tree.getPosition().getY()){
                            return board.get(new Point(person.getPosition().getX(), person.getPosition().getY() + 1)) != null || board.getLeash(new Point(person.getPosition().getX(), person.getPosition().getY() + 1)) != null;
                        }else if(person.getPosition().getY() > tree.getPosition().getY()){
                            return board.get(new Point(person.getPosition().getX() + 1, person.getPosition().getY())) != null || board.getLeash(new Point(person.getPosition().getX() + 1, person.getPosition().getY())) != null;
                        }
                    }else{
                        if(person.getPosition().getX() < tree.getPosition().getX()){
                            return board.get(new Point(person.getPosition().getX(), person.getPosition().getY() - 1)) != null || board.getLeash(new Point(person.getPosition().getX(), person.getPosition().getY() - 1)) != null;
                        }else if(person.getPosition().getX() > tree.getPosition().getX()){
                            return board.get(new Point(person.getPosition().getX() - 1, person.getPosition().getY())) != null || board.getLeash(new Point(person.getPosition().getX() - 1, person.getPosition().getY())) != null;
                        }
                    }
                }else if(getLengthOfTwo(tree, dog) == 2){
                    if(tree.getPosition().getX() == dog.getPosition().getX()){
                        if(tree.getPosition().getY() < dog.getPosition().getY()){
                            return board.get(new Point(tree.getPosition().getX(), tree.getPosition().getY() + 1)) != null || board.getLeash(new Point(tree.getPosition().getX(), tree.getPosition().getY() + 1)) != null;
                        }else if(tree.getPosition().getY() > dog.getPosition().getY()){
                            return board.get(new Point(tree.getPosition().getX() + 1, tree.getPosition().getY())) != null || board.getLeash(new Point(tree.getPosition().getX() + 1, tree.getPosition().getY())) != null;
                        }
                    }else{
                        if(tree.getPosition().getX() < dog.getPosition().getX()){
                            return board.get(new Point(tree.getPosition().getX(), tree.getPosition().getY() - 1)) != null || board.getLeash(new Point(tree.getPosition().getX(), tree.getPosition().getY() - 1)) != null;
                        }else if(tree.getPosition().getX() > dog.getPosition().getX()){
                            return board.get(new Point(tree.getPosition().getX() - 1, tree.getPosition().getY())) != null || board.getLeash(new Point(tree.getPosition().getX() - 1, tree.getPosition().getY())) != null;
                        }
                    }
                }else{
                    return false;
                }
            }
            return false;
        }

        public Leash build() throws GameException {
            if(intersects()) {
                throw new GameException("A leash cant go through another figure");
            }
            if (getWholeLength() > 3) {
                throw new GameException("The leash cant be longer than 3 tiles");
            }

            return new Leash(person, tree, dog, getWholeLength());
        }
    }

    public List<Point> getAllPoints(){
        List<Point> allPoints = new ArrayList<>();
        int personX = person.getPosition().getX();
        int personY = person.getPosition().getY();
        int dogX = dog.getPosition().getX();
        int dogY = dog.getPosition().getY();

        if(tree == null){
            if(personX == dogX){
                if(personY < dogY){
                    for(int i = personY; i <= dogY; i++){
                        allPoints.add(new Point(personX, i));
                    }
                }else{
                    for(int i = dogY; i <= personY; i++){
                        allPoints.add(new Point(dogX, i));
                    }
                }
            }else if(personX < dogX){
                for(int i = personX; i <= dogX; i++){
                    allPoints.add(new Point(i, personY));
                }
            }else{
                for(int i = dogX; i <= personX; i++){
                    allPoints.add(new Point(i, dogY));
                }
            }
        }else{
            int treeX = tree.getPosition().getX();
            int treeY = tree.getPosition().getY();
            if(personX == treeX){
                if(personY < treeY){
                    for(int i = personY; i <= treeY; i++){
                        allPoints.add(new Point(personX, i));
                    }
                }else{
                    for(int j = treeY; j <= personY; j++){
                        allPoints.add(new Point(treeX, j));
                    }
                }
            }else if(personX < treeX){
                for(int k = personX; k <= treeX; k++){
                    allPoints.add(new Point(k, personY));
                }
            }else{
                for(int l = treeX; l <= personX; l++){
                    allPoints.add(new Point(l, treeY));
                }
            }

            if(dogX == treeX){
                if(dogY < treeY){
                    for(int i = dogY; i <= treeY; i++){
                        allPoints.add(new Point(dogX, i));
                    }
                }else{
                    for(int j = treeY; j <= dogY; j++){
                        allPoints.add(new Point(treeX, j));
                    }
                }
            }else if(dogX < treeX){
                for(int k = dogX; k <= treeX; k++){
                    allPoints.add(new Point(k, dogY));
                }
            }else{
                for(int l = treeX; l <= dogX; l++){
                    allPoints.add(new Point(l, treeY));
                }
            }
        }
        return allPoints;
    }
    public int getLeashLength() { return this.length; }
    public Person getPerson() { return this.person; }
    public Tree getTree() { return this.tree;}
    public Dog getDog() { return this.dog;}
}
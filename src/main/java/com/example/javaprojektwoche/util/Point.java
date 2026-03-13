package com.example.javaprojektwoche.util;

public class Point {
    int x;
    int y;

    public Point(int x, int y) {
        if(x < 0 || x > 3 || y < 0 || y > 3){
            throw new IllegalArgumentException();
        }else{
            this.x = x;
            this.y = y;
        }
    }

    public Point() {

    }

    public int getX() { return x;}
    public int getY() { return y; }

    public boolean equals(Object other) {
        if(other == this) return true;
        else if (other instanceof Point point) {
            return point.getX() == getX() && point.getY() == getY();
        }
        return false;
    }
}

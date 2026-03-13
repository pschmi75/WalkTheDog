package com.example.javaprojektwoche.util;

public enum Mode {
    NORMAL,
    BINGUS;

    public static Mode fromString(String string) {
        if("bingus".equalsIgnoreCase(string)) return BINGUS;
        else return NORMAL;
    }

    @Override
    public String toString() {
        return this == NORMAL ? "Normal": "Bingus";
    }
}

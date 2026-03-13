package com.example.javaprojektwoche.util;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;

public enum HotbarMode {
    NONE,
    DOG,
    PERSON,
    LEASH,
    TRASH;

    private static String DOG_CURSOR_NORMAL = "/com/example/javaprojektwoche/sprites/dog_white.png";
    private static String DOG_CURSOR_BINGUS = "/com/example/javaprojektwoche/sprites/bingus_dog_white.png";

    private static String PERSON_CURSOR_NORMAL = "/com/example/javaprojektwoche/sprites/person_white.png";
    private static String PERSON_CURSOR_BINGUS = "/com/example/javaprojektwoche/sprites/bingus_person_white.png";

    private static String LEASH_CURSOR = "/com/example/javaprojektwoche/sprites/leash_icon.png";
    private static String TRASH_CURSOR = "/com/example/javaprojektwoche/sprites/x.png";

    public Cursor getCursor() {
        if(this == NONE) return Cursor.DEFAULT;

        return new ImageCursor(new Image(getClass().getResource(switch (this){
            case DOG -> Options.getInstance().getMode() == Mode.BINGUS ? DOG_CURSOR_BINGUS : DOG_CURSOR_NORMAL;
            case PERSON -> Options.getInstance().getMode() == Mode.BINGUS ? PERSON_CURSOR_BINGUS : PERSON_CURSOR_NORMAL;
            case LEASH -> LEASH_CURSOR;
            default -> TRASH_CURSOR;
        }).toExternalForm()), 0, 0);
    }
}

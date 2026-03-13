module com.example.javaprojektwoche {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires java.prefs;


    opens com.example.javaprojektwoche.controllers to javafx.fxml;
    exports com.example.javaprojektwoche;
}
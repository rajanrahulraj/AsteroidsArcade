module com.example.asteroidsarcade {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.asteroidsarcade.main to javafx.fxml;
    exports com.asteroidsarcade.main;
    exports com.asteroidsarcade.controllers;
    opens com.asteroidsarcade.controllers to javafx.fxml;
    opens com.asteroidsarcade.entities.base to javafx.fxml;
    exports com.asteroidsarcade.entities.base;
    exports com.asteroidsarcade.entities;
    exports com.asteroidsarcade.config;
    exports com.asteroidsarcade.components;
    exports com.asteroidsarcade.interfaces;
}
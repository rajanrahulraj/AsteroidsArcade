module com.example.asteroidsarcade {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.asteroidsarcade.main to javafx.fxml;
    exports com.asteroidsarcade.main;
}
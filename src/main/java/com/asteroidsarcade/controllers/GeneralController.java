package com.asteroidsarcade.controllers;

import com.asteroidsarcade.components.GeneralButton;
import com.asteroidsarcade.main.AsteroidsGame;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GeneralController {
    public Pane pane;
    public Pane homePane;
    public Stage stage;
    public Stage homeStage;
    public Scene scene;


    public void displayMenuButton(){
        GeneralButton mainMenuButton = new GeneralButton("Main Menu");
        mainMenuButton.setLayoutX(AsteroidsGame.WIDTH - 170);
        mainMenuButton.setLayoutY(20);
        this.pane.getChildren().add(mainMenuButton);
        mainMenuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                TODO: save score when user navigates to main menu.
                AsteroidsGame.sceneController.toggleStageView(stage, homeStage);
//                stage.close();
            }
        });
    }
}
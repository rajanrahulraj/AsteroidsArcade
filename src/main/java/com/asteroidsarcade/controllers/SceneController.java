package com.asteroidsarcade.controllers;

import com.asteroidsarcade.entities.base.GameEntity;
import com.asteroidsarcade.main.AsteroidsGame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import com.asteroidsarcade.components.GameButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SceneController{
    private Pane pane;
    private Stage stage;
    private Scene scene;

    List<GameButton> menuButtons;

    private int MENU_BUTTON_START_X = AsteroidsGame.WIDTH/2;
    private int MENU_BUTTON_START_Y = AsteroidsGame.HEIGHT/3;

    public SceneController(Pane pane, Stage stage) {
        this.pane = pane;
        this.stage = stage;
    }

    public void toggleStageView(Stage hideStage, Stage showStage){
        hideStage.hide();
        showStage.show();
    }

    public Scene setHomePageScene(){
        this.pane.setBackground(new Background(new BackgroundFill(Color.GREY, null, null)));
        this.scene = new Scene(this.pane);
        this.stage.setTitle("Asteroids - Home");
        this.stage.setScene(this.scene);
        this.scene.setFill(Color.GREY);
        return this.scene;
    }


    private void AddMenuButtons(GameButton button) {
        button.setLayoutX(MENU_BUTTON_START_X);
        if(menuButtons != null && menuButtons.size() > 0){
            button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 100);
        } else{
            button.setLayoutY(MENU_BUTTON_START_Y);
        }
        menuButtons.add(button);
        this.pane.getChildren().add(button);
    }

    public void showHomePage(){
        menuButtons = new ArrayList<>();
        createButtons();
        this.stage.show();
    }
    private void createButtons() {
        createStartButton();
        createScoresButton();
//        createCreditsButton();
        createExitButton();
    }

    private void createStartButton(){
        GameButton startButton = new GameButton("PLAY");
        AddMenuButtons(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                GameController gameController = new GameController(pane, stage);
                gameController.startGame();
            }
        });
    }

    private void createScoresButton(){
        GameButton scoreButton = new GameButton("HIGH SCORES");
        AddMenuButtons(scoreButton);
    }

    private void createExitButton(){
        GameButton exitButton = new GameButton("EXIT");
        AddMenuButtons(exitButton);
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
    }

    public void removeEntity(GameEntity entity){

    }
}

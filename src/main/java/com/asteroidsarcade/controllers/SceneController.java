package com.asteroidsarcade.controllers;

import com.asteroidsarcade.entities.base.GameEntity;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SceneController{
    private Pane pane;
    private Stage stage;
    private Scene scene;

    public SceneController(Pane pane, Stage stage) {
        this.pane = pane;
        this.stage = stage;
    }

    public Scene setScene(){
        this.scene = new Scene(this.pane);
        stage.setTitle("Group 4: Asteroids Game!");
        stage.setScene(this.scene);
        this.scene.setFill(Color.GREY);
        stage.show();
        return this.scene;
    }

    public void removeEntity(GameEntity entity){

    }
}

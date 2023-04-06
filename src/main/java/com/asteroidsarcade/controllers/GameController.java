package com.asteroidsarcade.controllers;

import com.asteroidsarcade.entities.Player;
import com.asteroidsarcade.entities.Alien;
import com.asteroidsarcade.entities.Bullet;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.asteroidsarcade.entities.SmallAsteroids;
import com.asteroidsarcade.entities.MediumAsteroids;
import com.asteroidsarcade.entities.LargeAsteroids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {

    private Pane pane;
    private Stage stage;
    private Scene scene;
    private Player player;
    private Alien alien;
    List<Bullet> bullets = new ArrayList<>();

    public GameController(Pane pane, Stage stage) {
        this.pane = pane;
        this.stage = stage;
    }

    public Scene setScene(){
        Scene scene = new Scene(this.pane);
        stage.setTitle("Group 4: Asteroids Game!");
        stage.setScene(scene);
        scene.setFill(Color.GREY);
        stage.show();
        return scene;
    }

    public void handleKyPressAction(Map<KeyCode, Boolean> pressedKeys){
        // press left arrow
        if(pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
            this.player.turnLeft();
        }

        // press right arrow
        if(pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
            this.player.turnRight();
        }

        // press up arrow, the player move faster.
        if(pressedKeys.getOrDefault(KeyCode.UP, false)) {
            this.player.applyThrust();
        }

     // press space to shoot
        if (pressedKeys.getOrDefault(KeyCode.SPACE, false)){
        	
            // When shooting the bullet in the same direction as the ship
        	Bullet bullet = new Bullet((int) player.getEntityShape().getTranslateX(),
                    (int) player.getEntityShape().getTranslateY());

        	bullet.getEntityShape().setRotate(player.getEntityShape().getRotate());
        	bullets.add(bullet);
        	bullet.move();
            pane.getChildren().add(bullet.getEntityShape());
            pressedKeys.clear();
        }

        this.player.move();
     // shooting
        bullets.forEach(bullet -> bullet.move());
    }
    
    public void addAsteroids(){
        // TO-DO: Add condition to add asteroids based on level

        SmallAsteroids smallAsteroid = new SmallAsteroids();
        MediumAsteroids mediumAsteroid = new MediumAsteroids();
        LargeAsteroids largeAsteroid = new LargeAsteroids();
        this.pane.getChildren().add(smallAsteroid.getEntityShape());
        this.pane.getChildren().add(mediumAsteroid.getEntityShape());
        this.pane.getChildren().add(largeAsteroid.getEntityShape());
    }


    public Player addPlayer() {
        this.player = new Player();
        this.pane.getChildren().add(player.getEntityShape());
        return this.player;
    }
    
    public Alien addAlien() {
        this.alien = new Alien();
        this.pane.getChildren().add(alien.getEntityShape());
        return this.alien;
    }
    
}
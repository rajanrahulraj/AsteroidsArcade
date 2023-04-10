package com.asteroidsarcade.controllers;

import com.asteroidsarcade.entities.*;
import com.asteroidsarcade.main.AsteroidsGame;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Math;

public class GameController {

    private Pane pane;
    private Pane homePane;
    private Stage stage;
    private Stage homeStage;
    private Scene scene;
    private Player player;
    private Alien alien;
    List<Bullet> bullets = new ArrayList<>();
    List<Asteroids> asteroids = new ArrayList<>();

    public GameController(Pane pane, Stage stage) {
        this.homePane = pane;
        this.pane = new Pane();
        this.pane.setPrefSize(AsteroidsGame.WIDTH, AsteroidsGame.HEIGHT);
        this.homeStage = stage;
        this.homeStage.hide();
        this.stage = new Stage();
        this.scene = setGameScene();
        this.stage.show();
    }
    public Scene setGameScene(){
        this.pane.setBackground(new Background(new BackgroundFill(Color.GREY, null, null)));
        this.scene = new Scene(this.pane);
        this.stage.setTitle("Asteroids - Game");
        this.stage.setScene(this.scene);
        this.scene.setFill(Color.GREY);
        return this.scene;
    }

    public void startGame(){
        // add the player entity into the pane
        Player player = addPlayer();

        //adding test asteroids of all shapes (A)
        addAsteroids(2, 2, 2);

        //adding an alien
        addAlien();


//	      when press the keyboard, make the player move smoothly.
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

        this.scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        this.scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });
        // control the keyboard
        new AnimationTimer() {

            @Override
            public void handle(long nanosec) {
                handleKeyPressAction(pressedKeys);

            }
        }.start();
    }

    public Scene getScene(){
        return this.scene;
    }


    public void handleKeyPressAction(Map<KeyCode, Boolean> pressedKeys){
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
        	if (player.getIsUncollisionable()) {
                return; // do nothing if player is invisible
            }
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

        // Move each asteroid
        asteroids.forEach(asteroid -> asteroid.move());

        // Check collisions for each asteroid
        asteroids.forEach(asteroid -> asteroid.handleCollision(bullets, asteroids, player, pane));
    }

    public void handleCollision() {
        this.bullets.forEach(bullet -> {
            if (bullet.hasCollided(this.player)) {
                player.decreaseLife();
            }
        });

        this.asteroids.forEach(asteroid -> {
            if (asteroid.hasCollided(this.player)) {
                player.decreaseLife();
            }
        });
    }
    
public void addAsteroids(int numSmallAsteroids, int numMediumAsteroids, int numLargeAsteroids) {
    // TO-DO: Add condition to add asteroids based on level

    for (int i = 0; i < numSmallAsteroids; i++) {
        SmallAsteroids smallAsteroid = new SmallAsteroids();
        asteroids.add(smallAsteroid);
    }

    for (int i = 0; i < numMediumAsteroids; i++) {
        MediumAsteroids mediumAsteroid = new MediumAsteroids();
        asteroids.add(mediumAsteroid);
    }

    for (int i = 0; i < numLargeAsteroids; i++) {
        LargeAsteroids largeAsteroid = new LargeAsteroids();
        asteroids.add(largeAsteroid);
    }

   // Generate a random angle between 0 and 360 degrees
    // Move each asteroid at the random angle
    this.asteroids.forEach(asteroid -> {
        asteroid.setAngle(Math.random() * 360);
        this.pane.getChildren().add(asteroid.getEntityShape());
});
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
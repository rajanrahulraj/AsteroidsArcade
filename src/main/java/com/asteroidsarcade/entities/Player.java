package com.asteroidsarcade.entities;

import java.util.List;
import java.util.Random;

import com.asteroidsarcade.entities.base.GameEntity;
import com.asteroidsarcade.main.AsteroidsGame;

import javafx.scene.shape.Polygon;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.PauseTransition;
import javafx.animation.FadeTransition;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;


// code below write by liaoliao

public class Player extends SpaceShip{
	
	// use to reduce the speed after applyThrust
	private double dragCoefficient = 0.01;
    private int remainingLives;
    public static int MAX_LIVES = 3;

    public int getRemainingLives() {
        return remainingLives;
    }

    public void setRemainingLives(int remainingLives) {
        this.remainingLives = remainingLives;
    }

    private int remainHyperspaceJump;

	private  boolean isUncollisionable = false;
	
	public Player(){
        super(new Polygon(-5, -5, 10, 0, -5, 5), 380,270);
        this.remainingLives = MAX_LIVES;
        this.remainHyperspaceJump = 3;
    }
	
    
    @Override
    public void move() {
        this.entityShape.setTranslateX(this.entityShape.getTranslateX() + this.movement.getX());
        this.entityShape.setTranslateY(this.entityShape.getTranslateY() + this.movement.getY());
        
     // use to reduce the speed after applyThrust
        movement = movement.multiply(1 - dragCoefficient);
        
        // code below are used to set all entities stay within the screen.
        if (this.entityShape.getTranslateX() < 0) {
            this.entityShape.setTranslateX(this.entityShape.getTranslateX() + AsteroidsGame.WIDTH);
        }

        if (this.entityShape.getTranslateX() > AsteroidsGame.WIDTH) {
            this.entityShape.setTranslateX(this.entityShape.getTranslateX() % AsteroidsGame.WIDTH);
        }

        if (this.entityShape.getTranslateY() < 0) {
            this.entityShape.setTranslateY(this.entityShape.getTranslateY() + AsteroidsGame.HEIGHT);
        }

        if (this.entityShape.getTranslateY() > AsteroidsGame.HEIGHT) {
            this.entityShape.setTranslateY(this.entityShape.getTranslateY() % AsteroidsGame.HEIGHT);
        }
        
    }
    
    public void applyThrust() {
        double changeX = Math.cos(Math.toRadians(this.entityShape.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.entityShape.getRotate()));

        changeX *= 0.05;
        changeY *= 0.05;

        this.movement = this.movement.add(changeX, changeY);
    }
  

   
    
    public void hyperspaceJump(List<GameEntity> enemies) {
        if (this.remainHyperspaceJump > 0) {
            boolean collided;
            do {
                setNewLocation();
                collided = false;
                // detect the new position didn't collied with enemies.
                for (GameEntity enemy : enemies) {
                    if (enemy.hasCollided(this)) {
                        collided = true;
                        break;
                    }
                }
            } while (collided);

            invisibility(Duration.seconds(3), Color.WHITE);
            this.remainHyperspaceJump -= 1;
        }
    }

    
    // set a random location
    public void setNewLocation() {
		Random r = new Random();
		int hyperSpaceX = r.nextInt(AsteroidsGame.WIDTH - 50) + 50;
		int hyperSpaceY = r.nextInt(AsteroidsGame.HEIGHT - 50) + 50;
		
		this.entityShape.setTranslateX(hyperSpaceX);
		this.entityShape.setTranslateY(hyperSpaceY);
	}
    

    public void decreaseLife() {
        if (!isUncollisionable) {
            this.remainingLives--;
            dyingAnimation();
            // make player transparent for 3 seconds
            invisibility(Duration.seconds(3), Color.TRANSPARENT);
            // pause for 3 seconds
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> {
                // move player to center of screen
                entityShape.setTranslateX(AsteroidsGame.WIDTH / 2);
                entityShape.setTranslateY(AsteroidsGame.HEIGHT / 2);
                // make player white for 3 seconds
                invisibility(Duration.seconds(3), Color.WHITE);
            });
            pause.play();
        }
    }

    public boolean getIsUncollisionable() {
        return isUncollisionable;
    }

    public void setIsUncollisionable(boolean value) {
        isUncollisionable = value;
    }

    //written by Anastasiia
    private void invisibility(Duration duration, Color color) {
        entityShape.setFill(color); // set fill color 
        isUncollisionable = true;
        Timeline timeline = new Timeline(new KeyFrame(duration, e -> {
            isUncollisionable = false;
            entityShape.setFill(Color.BLACK); // restore original fill color
        }));

    timeline.play();
}

    public void dyingAnimation() { 
    // create sticks and dots
    List<Shape> shapes = new ArrayList<>();
    Pane parent = (Pane) entityShape.getParent();
    double playerX = entityShape.getTranslateX();
    double playerY = entityShape.getTranslateY();
    double stickLength = 20;
    for (int i = 0; i < 4; i++) {
        double angle = Math.random() * 2 * Math.PI;
        double xOffset = stickLength * Math.cos(angle);
        double yOffset = stickLength * Math.sin(angle);
        Line stick = new Line(playerX, playerY, playerX + xOffset, playerY + yOffset);
        stick.setStroke(Color.BLUE);
        stick.setStrokeWidth(2);
        shapes.add(stick);

        // create animation for stick
        Path path = new Path();
        path.getElements().add(new MoveTo(playerX, playerY));
        path.getElements().add(new LineTo(playerX + xOffset, playerY + yOffset));
        PathTransition pathTransition = new PathTransition(Duration.seconds(2.5), path, stick);

        pathTransition.play();
    }
    for (int i = 0; i < 5; i++) {
        Circle dot = new Circle(playerX, playerY, 2);
        dot.setFill(Color.BLUE);
        shapes.add(dot);

        // create animation for dot
        double angle = Math.random() * 2 * Math.PI;
        double distance = Math.random() * 30;
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2.5), dot);
        translateTransition.setByX(distance * Math.cos(angle));
        translateTransition.setByY(distance * Math.sin(angle));

        translateTransition.play();
    }

    // add shapes to entity shape's parent
    shapes.forEach(shape -> parent.getChildren().add(shape));

    // create timeline to remove shapes after duration
    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.5), e -> {
        shapes.forEach(shape -> {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), shape);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.setOnFinished(event2 -> {
                parent.getChildren().remove(shape);
            });
            fadeTransition.play();
            });
        }));

        timeline.play();
    }
}

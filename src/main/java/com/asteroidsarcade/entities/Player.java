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

            invisibility(Duration.seconds(3));
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
        isUncollisionable = true;

        if (this.remainingLives <= 0) {
            // handle game over
        } else {
            // move player to center of screen
            entityShape.setTranslateX(AsteroidsGame.WIDTH / 2);
            entityShape.setTranslateY(AsteroidsGame.HEIGHT / 2);

            // make player invisible for 3 seconds
            invisibility(Duration.seconds(3));
        }
    } else {
        entityShape.setFill(Color.WHITE); // set fill color to white
    }
}

    public boolean getIsUncollisionable() {
        return isUncollisionable;
    }

    public void setIsUncollisionable(boolean value) {
        isUncollisionable = value;
    }
    //written by Anastasiia
    private void invisibility(Duration duration) {
        entityShape.setFill(Color.WHITE); // set fill color to white
        Timeline timeline = new Timeline(new KeyFrame(duration, e -> {
            isUncollisionable = false;
            entityShape.setFill(Color.BLACK); // restore original fill color
        }));
        timeline.play();
    }

    public void increaseScore() {
        //
    }
}

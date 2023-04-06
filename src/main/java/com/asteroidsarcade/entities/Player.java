package com.asteroidsarcade.entities;

import javafx.geometry.Point2D;
import com.asteroidsarcade.main.AsteroidsGame;


import javafx.scene.shape.Polygon;
import com.asteroidsarcade.interfaces.Moveable;
import com.asteroidsarcade.interfaces.Turnable;
import com.asteroidsarcade.entities.base.GameEntity;

// code below write by liaoliao

public class Player extends SpaceShip implements Moveable, Turnable {
	
	// use to reduce the speed after applyThrust
	private double dragCoefficient = 0.01;
	
	
	public Player(){
        super(new Polygon(-5, -5, 10, 0, -5, 5), 380,270);
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
  
    
    
	
    public void hyperspaceJump() {
    	
    }
    
}

package com.asteroidsarcade.entities;

import javafx.geometry.Point2D;
import com.asteroidsarcade.main.HelloApplication;


import com.asteroidsarcade.entities.base.TurnableEntity;
import com.asteroidsarcade.entities.base.GameEntity;

import javafx.scene.shape.Polygon;
import com.asteroidsarcade.interfaces.Moveable;
import com.asteroidsarcade.interfaces.Turnable;
import com.asteroidsarcade.entities.SpaceShip;

// code below write by liaoliao

public class Player extends SpaceShip implements Moveable, Turnable {
	private Point2D movement;
	
	
	public Player(){
        super(new Polygon(-5, -5, 10, 0, -5, 5), 380,270);
        this.movement = new Point2D(0, 0);
    }
	
    
    @Override
    public void move() {
        this.entityShape.setTranslateX(this.entityShape.getTranslateX() + this.movement.getX());
        this.entityShape.setTranslateY(this.entityShape.getTranslateY() + this.movement.getY());
        
        // code below are used to set all entities stay within the screen.
        if (this.entityShape.getTranslateX() < 0) {
            this.entityShape.setTranslateX(this.entityShape.getTranslateX() + HelloApplication.WIDTH);
        }

        if (this.entityShape.getTranslateX() > HelloApplication.WIDTH) {
            this.entityShape.setTranslateX(this.entityShape.getTranslateX() % HelloApplication.WIDTH);
        }

        if (this.entityShape.getTranslateY() < 0) {
            this.entityShape.setTranslateY(this.entityShape.getTranslateY() + HelloApplication.HEIGHT);
        }

        if (this.entityShape.getTranslateY() > HelloApplication.HEIGHT) {
            this.entityShape.setTranslateY(this.entityShape.getTranslateY() % HelloApplication.HEIGHT);
        }
        
    }
    
    public void applyThrust() {
        double changeX = Math.cos(Math.toRadians(this.entityShape.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.entityShape.getRotate()));

        changeX *= 0.05;
        changeY *= 0.05;

        this.movement = this.movement.add(changeX, changeY);
    }
  
    // Add by Marc - fire calls bullet right, also needs life, I think this needs direction variable 
    public Bullet fire() {
    	return new Bullet(this.entityShape.getTranslateX(), this.entityShape.getTranslateY(), velocityX, velocityY);
    }
	
    public void hyperspaceJump() {
    	
    }
    
}

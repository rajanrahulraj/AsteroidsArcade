package com.asteroidsarcade.entities;

import javafx.geometry.Point2D;
import com.asteroidsarcade.main.HelloApplication;


import com.asteroidsarcade.entities.base.GameEntity;

import javafx.scene.shape.Polygon;



// code below write by liaoliao

public class Player extends GameEntity {
	
	private Polygon entityShape;
    private Point2D movement;
	

    public Player(int x, int y) {
        super(new Polygon(-5, -5, 10, 0, -5, 5), x, y);
    }
    
    public Polygon getEntityShape() {
        return entityShape;
    }
    
    public void turnLeft() {
        this.entityShape.setRotate(this.entityShape.getRotate() - 5);
    }
    
    public void turnRight() {
        this.entityShape.setRotate(this.entityShape.getRotate() + 5);
    }
	
    
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
    
    @Override
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

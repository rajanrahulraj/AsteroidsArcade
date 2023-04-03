package com.asteroidsarcade.entities.base;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;



public abstract class GameEntity {
	private Point2D movement;
    private Polygon entityShape;

    public GameEntity(Polygon polygon, int x, int y) {
        this.entityShape = polygon;
        this.entityShape.setTranslateX(x);
        this.entityShape.setTranslateY(y);
        
        this.movement = new Point2D(0, 0);
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
    }
    
    public void applyThrust() {
    	double changeX = Math.cos(Math.toRadians(this.entityShape.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.entityShape.getRotate()));

        changeX *= 0.05;
        changeY *= 0.05;

        this.movement = this.movement.add(changeX, changeY);
    }
    
    // Setter
    public void setMovement(Point2D x) {
      this.movement = x;
    }
    
    // getter
    public Point2D getMovement() {
    	return movement;
    }
    
 

}

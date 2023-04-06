package com.asteroidsarcade.entities.base;

import com.asteroidsarcade.interfaces.Moveable;
import com.asteroidsarcade.main.AsteroidsGame;

import javafx.scene.shape.Polygon;
import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;


/**
 * An abstract class that represents a game entity with a polygon shape that can move on the screen.
 * Implements the Moveable interface for moving functionality.
 */
public abstract class GameEntity implements Moveable {

    /**
     * The polygon shape of the game entity.
     */
    public Polygon entityShape;
    
    public Point2D movement;

    /**
     * The velocity in the x direction.
     */
    public double velocityX;

    /**
     * The velocity in the y direction.
     */
    public double velocityY;

    /**
     * Constructor that creates a game entity with a specified polygon shape and starting position.
     * @param polygon the polygon shape of the game entity
     * @param x the starting x position of the game entity
     * @param y the starting y position of the game entity
     */
    public GameEntity(Polygon polygon, int x, int y) {
        this.entityShape = polygon;
        this.entityShape.setTranslateX(x);
        this.entityShape.setTranslateY(y);
        
        this.movement = new Point2D(0, 0);
    }

    /**
     * Returns the polygon shape of the game entity.
     * @return the polygon shape of the game entity
     */
    public Polygon getEntityShape() {
        return entityShape;
    }

    /**
     * Sets the velocity in the x and y directions.
     * @param x the velocity in the x direction
     * @param y the velocity in the y direction
     */
    public void setVelocity(double x, double y) {
        this.velocityX = x;
        this.velocityY = y;
    }

    /**
     * Moves the game entity based on its velocity and rotation.
     * Also ensures that the game entity stays within the screen boundaries.
     */
    public void move() {
        double changeX = Math.cos(Math.toRadians(this.entityShape.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.entityShape.getRotate()));
        entityShape.setTranslateX(entityShape.getTranslateX() + changeX);
        entityShape.setTranslateY(entityShape.getTranslateY() + changeY);

        // Code below is used to ensure that all entities stay within the screen.
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

    public boolean hasCollided(GameEntity other) {
        Shape collisionArea = Shape.intersect(this.entityShape, other.getEntityShape());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

}

package com.asteroidsarcade.entities;
import com.asteroidsarcade.main.AsteroidsGame;
import com.asteroidsarcade.entities.base.GameEntity;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Asteroids extends GameEntity {

    public Point2D location;
    public int numSplit;
    private double angle;
    public double velocity;

    public Asteroids(Polygon polygon, int x, int y, double velocity) {
        super(polygon, x, y);
        this.velocity = velocity;
        location = new Point2D(x, y);
    }
    
    // Logic to move and rotate asteroids here.

     public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void move() {
        double radians = Math.toRadians(angle);
        double changeX = Math.cos(radians) * velocity;
        double changeY = Math.sin(radians) * velocity;
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

    // Logic when asteroid "dies"
}

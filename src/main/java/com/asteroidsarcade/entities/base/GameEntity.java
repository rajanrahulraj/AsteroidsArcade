package com.asteroidsarcade.entities.base;

import com.asteroidsarcade.interfaces.Moveable;
import com.asteroidsarcade.main.HelloApplication;
import com.asteroidsarcade.main.HelloApplication;

import javafx.scene.shape.Polygon;

public abstract class GameEntity implements Moveable {

    public Polygon entityShape;
    public double velocityX;
    public double velocityY;

    public GameEntity(Polygon polygon, int x, int y) {
        this.entityShape = polygon;
        this.entityShape.setTranslateX(x);
        this.entityShape.setTranslateY(y);
    }

    public Polygon getEntityShape() {
        return entityShape;
    }

    public void setVelocity(double x, double y) {
        this.velocityX = x;
        this.velocityY = y;
    }

    public void move() {
        double changeX = Math.cos(Math.toRadians(this.entityShape.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.entityShape.getRotate()));
        entityShape.setTranslateX(entityShape.getTranslateX() + changeX);
        entityShape.setTranslateY(entityShape.getTranslateY() + changeY);

        // code below added by liaoliao.
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
        // code above added by liaoliao.

    }
}

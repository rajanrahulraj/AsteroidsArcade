package com.asteroidsarcade.entities;

import com.asteroidsarcade.interfaces.Moveable;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Alien extends SpaceShip {

    public Point2D position;
    public double moveAngle;
    public double velocity = 10;// pixels per second, maybe need to change

    public Alien() {
        super(new Polygon(0.0, 80.0, 20.0, 0.0, 60.0, 0.0, 80.0, 80.0, 60.0, 160.0, 20.0, 160.0), 0, 0);
        // dont know what the last two parameters are
        this.position = new Point2D(Math.random(), Math.random());
        
        this.moveAngle = Math.random() * 2 * Math.PI;
        this.velocityX = Math.cos(this.moveAngle) * velocity;
        this.velocityY = Math.sin(this.moveAngle) * velocity;

        move();// Alien starts moving immediately after creation
    }

    @Override
    public void move() {
        this.entityShape.setTranslateX(this.entityShape.getTranslateX() + Math.cos(this.moveAngle));
        this.entityShape.setTranslateY(this.entityShape.getTranslateY() + Math.sin(this.moveAngle));
    }

    public Bullet fire() {
        return new Bullet(this.entityShape.getTranslateX(), this.entityShape.getTranslateY(), velocityX, velocityY);
    }

    public void destoryed() {
        entityShape=null;

    }
}

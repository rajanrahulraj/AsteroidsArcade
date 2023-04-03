package com.asteroidsarcade.entities;

import com.asteroidsarcade.interfaces.Moveable;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;


public class Alien extends SpaceShip {

    public Point2D position;
    public double moveAngle;
    public double velocity = 10;// pixels per second, maybe need to change

    public Alien() {
        super(new Polygon(0.0, 70.0, 20.0, 0.0, 60.0, 0.0, 80.0, 80.0, 60.0, 160.0, 20.0, 160.0), 0, 0);
        // dont know what the last two parameters are
        this.position = new Point2D(Math.random(), Math.random());

        this.moveAngle = Math.random() * 2 * Math.PI;
        this.velocityX = Math.cos(this.moveAngle) * velocity;
        this.velocityY = Math.sin(this.moveAngle) * velocity;

        move();// Alien starts moving immediately after creation
    }

    // not sure if this is correct
    @Override
    public void move() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.0167), event -> {
            double newX = this.getTranslateX() + this.velocityX;
            double newY = this.getTranslateY() + this.velocityY;

            this.setTranslateX(newX);
            this.setTranslateY(newY);
        }));
        
        timeline.setCycleCount(Animation.INDEFINITE); 

    }

    public Bullet fire() {
        return new Bullet(this.entityShape.getTranslateX(), this.entityShape.getTranslateY(), velocityX, velocityY);
    }

    public void destoryed() {
        entityShape = null;

    }
}

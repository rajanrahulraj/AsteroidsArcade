package com.asteroidsarcade.entities;

import com.asteroidsarcade.interfaces.Moveable;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Alien extends SpaceShip {

    public Point2D position;
    public double moveAngle;

    public Alien(int x, int y) {
        super(new Polygon(0.0, 80.0, 20.0, 0.0, 60.0, 0.0, 80.0, 80.0, 60.0, 160.0, 20.0, 160.0), 0, 0);
        // this.position = new Point2D(Math.random(), Math.random());
        this.moveAngle=Math.random()*2*Math.PI;
    }

    public Alien() {
    }

    @Override
    public void move() {
        this.entityShape.setTranslateX(this.entityShape.getTranslateX() + Math.cos(this.moveAngle));
        this.entityShape.setTranslateY(this.entityShape.getTranslateY() + Math.sin(this.moveAngle));
    }

    public Bullet fire() {
        return new Bullet(this.entityShape.getTranslateX(), this.entityShape.getTranslateY(), velocityX, velocityY);
    }
}

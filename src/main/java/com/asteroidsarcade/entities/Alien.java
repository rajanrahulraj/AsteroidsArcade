package com.asteroidsarcade.entities;

import com.asteroidsarcade.interfaces.Moveable;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Alien extends SpaceShip {

    public Point2D position;

    public Alien(int x, int y) {
        super(new Polygon(0.0, 80.0, 20.0, 0.0, 60.0, 0.0, 80.0, 80.0, 60.0, 160.0, 20.0, 160.0), x, y);
        this.position = new Point2D(0, 0);
    }

    public Alien() {
    }

    @Override
    public void move() {
        this.entityShape.setTranslateX(this.entityShape.getTranslateX() + this.position.getX());
        this.entityShape.setTranslateY(this.entityShape.getTranslateY() + this.position.getY());
    }

    public Bullet fire() {
        return new Bullet(this.entityShape.getTranslateX(), this.entityShape.getTranslateY(), velocityX, velocityY);
    }
}

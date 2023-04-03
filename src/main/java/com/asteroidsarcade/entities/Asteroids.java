package com.asteroidsarcade.entities;

import com.asteroidsarcade.entities.base.GameEntity;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Asteroids extends GameEntity {

    public Point2D location;
    public int numSplit;

    public int velocity;
    public Asteroids(Polygon polygon, int x, int y) {
        super(polygon, x, y);
    }

    public void setVelocity(double velocity){
        super.setVelocity(velocity, velocity);
    }
    // Logic to move and rotate asteroids here.
    // Logic when asteroid "dies"
}

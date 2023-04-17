package com.asteroidsarcade.entities;
import javafx.scene.shape.Polygon;

public class LargeAsteroids extends Asteroids {

    public LargeAsteroids(double x, double y, double velocity) {
        //coordinates for the large asteroid
        super(new Polygon(22, 8, 41, 0, 57, 16, 39, 26, 59, 42, 39, 67, 17, 59, 7, 68, -9, 51, -1, 32, -9, 18, 12, 0), x, y, velocity);
    }

    public LargeAsteroids() {
    super(new Polygon(22, 8, 41, 0, 57, 16, 39, 26, 59, 42, 39, 67, 17, 59, 7, 68, -9, 51, -1, 32, -9, 18, 12, 0), 900, 600, 0.5);
    }
}


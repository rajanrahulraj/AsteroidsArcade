package com.asteroidsarcade.entities;
import javafx.scene.shape.Polygon;

public class MediumAsteroids extends Asteroids {

    public MediumAsteroids(double x, double y, double velocity) {
        //coordinates for the medium asteroid
        super(new Polygon(12, 4, 20.5, 0, 28.5, 8, 19.5, 13, 29.5, 21, 19.5, 33.5, 8.5, 29.5, 3.5, 34, -4.5, 25.5, -0.5, 16, -4.5, 9, 5.5, 0), x, y, velocity); 
    }

    public MediumAsteroids() {
    super(new Polygon(12, 4, 20.5, 0, 28.5, 8, 19.5, 13, 29.5, 21, 19.5, 33.5, 8.5, 29.5, 3.5, 34, -4.5, 25.5, -0.5, 16, -4.5, 9, 5.5, 0), 900, 600, 1.0); 
    }
}

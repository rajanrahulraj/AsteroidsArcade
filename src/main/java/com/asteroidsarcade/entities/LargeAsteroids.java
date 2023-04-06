package com.asteroidsarcade.entities;
import com.asteroidsarcade.entities.base.GameEntity;
import com.asteroidsarcade.interfaces.Moveable;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class LargeAsteroids extends Asteroids {

    public LargeAsteroids() {
        //coordinates for the large asteroid
        super(new Polygon(22, 8, 41, 0, 57, 16, 39, 26, 59, 42, 39, 67, 17, 59, 7, 68, -9, 51, -1, 32, -9, 18, 12, 0), 900, 600, 0.5);

        this.location = new Point2D(0, 0);

        //number of splits => 2, because if it gets killed, it divides into two medium asteroids
        this.numSplit = 2;
    }
}


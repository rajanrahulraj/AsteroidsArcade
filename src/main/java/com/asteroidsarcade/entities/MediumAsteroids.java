package com.asteroidsarcade.entities;
import com.asteroidsarcade.entities.base.GameEntity;
import com.asteroidsarcade.interfaces.Moveable;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class MediumAsteroids extends GameEntity implements Moveable {
    private Point2D location;
    private int numSplit; 

    public MediumAsteroids() {
        //coordinates for the medium asteroid
        super(new Polygon(12, 4, 20.5, 0, 28.5, 8, 19.5, 13, 29.5, 21, 19.5, 33.5, 8.5, 29.5, 3.5, 34, -4.5, 25.5, -0.5, 16, -4.5, 9, 5.5, 0), 500, 500); 

        this.location = new Point2D(0, 0);

        //number of splits => 2, because if it gets killed, it divides into two small asteroids
        this.numSplit = 2;
    }
}

package com.asteroidsarcade.entities;
import com.asteroidsarcade.entities.base.GameEntity;
import com.asteroidsarcade.interfaces.Moveable;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class SmallAsteroids extends GameEntity implements Moveable {
    private Point2D location;
    private int numSplit; 

    public SmallAsteroids() {
        //coordinates for the small asteroid
        super(new Polygon(6, 2, 10.25, 0, 14.25, 4, 9.75, 6.5, 14.75, 10.5, 9.75, 16.75, 4.25, 14.75, 1.75, 17, -2.25, 12.75, -0.25, 8, -2.25, 4.5, 2.75, 0), 380, 270);

        this.location = new Point2D(0, 0);

        //number of splits => 0, because if it gets killed, it disappears
        this.numSplit = 0;
        this.setVelocity(4,4);
    }
}

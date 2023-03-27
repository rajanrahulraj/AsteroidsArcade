package com.asteroidsarcade.entities;
import com.asteroidsarcade.interfaces.Moveable;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Alien extends SpaceShip{

    public Point2D position;

    public Alien(int x, int y) {
        super(new Polygon(-5, -5, 10, 0, -5, 5), x, y);
        this.position=new Point2D(0, 0);
    }

    public Alien() {
    }

    public void move(){

    }
}

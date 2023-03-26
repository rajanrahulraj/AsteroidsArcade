package com.asteroidsarcade.entities;

import com.asteroidsarcade.interfaces.Moveable;

import javafx.scene.shape.Polygon;

public class Alien extends SpaceShip{

    public Alien(Polygon polygon, int x, int y) {
        super(polygon, x, y);
    }

    public Alien() {
    }

}

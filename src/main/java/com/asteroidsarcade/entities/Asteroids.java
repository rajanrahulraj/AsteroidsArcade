package com.asteroidsarcade.entities;

import com.asteroidsarcade.entities.base.GameEntity;
import javafx.scene.shape.Polygon;

public class Asteroids extends GameEntity {
    public Asteroids(Polygon polygon, int x, int y) {
        super(polygon, x, y);
    }
}

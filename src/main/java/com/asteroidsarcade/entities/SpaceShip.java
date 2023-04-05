package com.asteroidsarcade.entities;


import javafx.geometry.Point2D;

import com.asteroidsarcade.entities.base.TurnableEntity;
import javafx.scene.shape.Polygon;

public class SpaceShip extends TurnableEntity {

	public SpaceShip(Polygon polygon, int x, int y) {
        super(polygon, x, y);
    }
    public SpaceShip(){
        super(new Polygon(-5,-5,10,5,-5,5), 150,100);
    }

    @Override
    public void setVelocity() {

    }
}

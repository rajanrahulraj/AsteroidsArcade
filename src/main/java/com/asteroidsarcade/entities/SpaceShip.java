package com.asteroidsarcade.entities;


import javafx.geometry.Point2D;

import com.asteroidsarcade.entities.base.TurnableEntity;
import javafx.scene.shape.Polygon;

public class SpaceShip extends TurnableEntity {
	
	// liaoliao add code below, if not, can't build a player entities.
	public SpaceShip(Polygon polygon, int x, int y) {
        super(polygon, x, y);
    }
	// liaoliao add code above.
	
    public SpaceShip(){
        super(new Polygon(-5,-5,10,5,-5,5), 150,100);
    }
    

    @Override
    public void setVelocity() {

    }
}

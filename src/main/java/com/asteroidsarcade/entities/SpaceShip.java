package com.asteroidsarcade.entities;


import com.asteroidsarcade.entities.base.TurnableEntity;
import javafx.scene.shape.Polygon;

public class SpaceShip extends TurnableEntity {
    public SpaceShip(){
        super(new Polygon(-5,-5,10,0,-5,5), 150,100);
    }

    @Override
    public void setVelocity() {

    }
}

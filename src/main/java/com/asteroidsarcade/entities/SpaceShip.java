package com.asteroidsarcade.entities;


import javafx.scene.shape.Polygon;

public class SpaceShip extends GameEntity{
    public SpaceShip(){
        super(new Polygon(-5,-5,10,0,-5,5), 150,100);
    }
}

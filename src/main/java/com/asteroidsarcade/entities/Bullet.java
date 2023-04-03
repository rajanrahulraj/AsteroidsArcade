package com.asteroidsarcade.entities;

import com.asteroidsarcade.entities.base.GameEntity;

import javafx.scene.shape.Polygon;


public class Bullet extends GameEntity {

	
	    public Bullet(int x, int y) {
	        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
	    }

	    
	
}

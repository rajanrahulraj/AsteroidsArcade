package com.asteroidsarcade.entities;

import javafx.scene.shape.Polygon;
import com.asteroidsarcade.entities.base.GameEntity;


import javafx.geometry.Point2D;

public class Bullet extends GameEntity {
	

    public Bullet(int x, int y) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
       
    }
    
    public void move() {
        double changeX = Math.cos(Math.toRadians(this.getEntityShape().getRotate()));
        double changeY = Math.sin(Math.toRadians(this.getEntityShape().getRotate()));

        this.getEntityShape().setTranslateX(this.getEntityShape().getTranslateX() + changeX * 5);
        this.getEntityShape().setTranslateY(this.getEntityShape().getTranslateY() + changeY * 5);


    }


}
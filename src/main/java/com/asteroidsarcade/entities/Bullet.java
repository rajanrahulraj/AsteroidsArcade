package com.asteroidsarcade.entities;

import javafx.scene.shape.Polygon;
import com.asteroidsarcade.entities.base.GameEntity;


import javafx.geometry.Point2D;

import java.util.List;

public class Bullet extends GameEntity {
	private double origin_x;
    private double origin_y;
    private long createdTime = 0;

    public double getOrigin_x() {
        return origin_x;
    }

    public void setOrigin_x(double origin_x) {
        this.origin_x = origin_x;
    }

    public double getOrigin_y() {
        return origin_y;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public void setOrigin_y(double origin_y) {
        this.origin_y = origin_y;
    }

    public Bullet(int origin_x, int origin_y) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), origin_x, origin_y);
        setOrigin_x(origin_x);
        setOrigin_y(origin_y);
        setCreatedTime(System.currentTimeMillis());
    }
    
    public void move() {
        double changeX = Math.cos(Math.toRadians(this.getEntityShape().getRotate()));
        double changeY = Math.sin(Math.toRadians(this.getEntityShape().getRotate()));

        this.getEntityShape().setTranslateX(this.getEntityShape().getTranslateX() + changeX * 5);
        this.getEntityShape().setTranslateY(this.getEntityShape().getTranslateY() + changeY * 5);
    }

}
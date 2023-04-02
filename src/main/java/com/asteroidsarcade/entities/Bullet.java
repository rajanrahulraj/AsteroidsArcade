package com.asteroidsarcade.entities;

import com.asteroidsarcade.interfaces.Moveable;


import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Bullet extends SpaceShip implements Moveable {
	// private fixed variables 
	// shot velocity stays same
	// existance (if needs to stop if doesnt hit anything, how does it know this? Time? 

	
	final double shotVelocity = 9.0;
	
	double x; 
	double y;
	double velocityX;
	double velocityY; 
	
	
	// bullet doesn't exist by itself right? so must contain variables from ship/player/ufo 
	// variable direction is equal to direction of spaceship is facing, we need put in spaceship class
  // need direction too but not sure how to get this sin + cos
  // need to add life existence here too) 
	public Bullet (double x, double y, double shipVelocityX, double shipVelocityY) {
		this.x=x;
		this.y=y;
		velocityX = shotVelocity*Math.cos(this.entityShape.getTranslateX()) + shipVelocityX;
		velocityY = shotVelocity*Math.sin(this.entityShape.getTranslateY()) + shipVelocityY;
	}
	
	
	// I don't override because it is it's own entity right?
	public void move() {
		x += velocityX;
		y += velocityY;
		
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
}

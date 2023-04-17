package com.asteroidsarcade.entities;

import com.asteroidsarcade.main.AsteroidsGame;
import com.asteroidsarcade.interfaces.Moveable;
import com.asteroidsarcade.interfaces.Turnable;
import com.asteroidsarcade.entities.base.GameEntity;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import javafx.animation.AnimationTimer;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

// Alien requiments:
//1. Alien must be in the shape of a saucer.
//2. Alien must be generated randomly from the edge of screen.
//3. The initial direction of each Alien is fixed, depends on the screen sides it generated, but Alien will change direction radomly from time to time.
//4. The speed of Alien is fixed.
//5. Alien must be able to fire a bullet toward the player.
//6. Alien must be able to be destroyed.
//7. Alien will disappear permanently when it exits the screen.
public class Alien extends SpaceShip {

    private int shootDelay;// milliseconds
    private int shootCounter;
    private int size;
    private int velocity;
    private double angle;

    public Alien() {

        super();

        this.entityShape = new Polygon(0, 0, 0, 7, -5, 15, 15, 15, 10, 7, 10, 0);

        this.entityShape.setTranslateX(0);
        this.entityShape.setTranslateY(600 * Math.random());

        // this.velocityX = 1;//Temporary parameters
        // this.velocityY = 0;//Temporary parameters
        this.velocity = 1;
        this.angle = Math.random() * 2 * Math.PI;

        this.size = 30;
        this.shootDelay = 50;
        this.shootCounter = 0;

    }

    @Override
    public void move() {

        this.entityShape.setTranslateX(this.entityShape.getTranslateX() + this.velocity * Math.cos(angle));
        this.entityShape.setTranslateY(this.entityShape.getTranslateY() + this.velocity * Math.sin(angle));

    }

}

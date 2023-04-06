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

import java.util.Random;



// Alien requiments:
//1. Alien must be in the shape of a saucer.
//2. Alien must be generated randomly from the edge of screen.
//3. The initial direction of each Alien is fixed, depends on the screen sides it generated, but Alien will change direction radomly from time to time.
//4. The speed of Alien is fixed.
//5. Alien must be able to fire a bullet.
//6. Alien must be able to be destroyed.
//7. Alien will disappear permanently when it exits the screen.
public class Alien extends SpaceShip {
    

    public Alien() {

        super(new Polygon(0,0, 0,7, -5,15, 15,15, 10,7, 10,0), 100,100);

        
    }
    

    // not sure if this is correct
//    @Override
//    public void move() {
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.0167), event -> {
//            double newX = this.getTranslateX() + this.velocityX;
//            double newY = this.getTranslateY() + this.velocityY;
//
//            this.setTranslateX(newX);
//            this.setTranslateY(newY);
//        }));
//
//        timeline.setCycleCount(Animation.INDEFINITE);
//
//    }

    public Bullet fire() {
    
    }

    public void destoryed() {

    }
}

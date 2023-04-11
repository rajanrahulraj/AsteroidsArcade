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
    List<Bullet> bullets = new ArrayList<>();

    public Alien() {

        super();

        this.entityShape = new Polygon(0, 0, 0, 7, -5, 15, 15, 15, 10, 7, 10, 0);

        this.entityShape.setTranslateX(0);
        this.entityShape.setTranslateY(600 * Math.random());

        this.velocityX = 2 * (Math.random() - 0.5);
        this.velocityY = 2 * (Math.random() - 0.5);

        this.size = 30;
        this.shootDelay = 50;
        this.shootCounter = 0;

    }

    @Override
    public void move() {

        if (!isAlive()) {
            return;
        }

        this.entityShape.setTranslateX(this.entityShape.getTranslateX() + this.velocityX);
        this.entityShape.setTranslateY(this.entityShape.getTranslateY() + this.velocityY);

        shoot();
    }

    void shoot(){
        new AnimationTimer() {
        public void handle(long now) {
        if (shootCounter == shootDelay) {
            Bullet bullet = new Bullet((int) this.getEntityShape().getTranslateX(),(int) this.getEntityShape().getTranslateY()));
            bullet.setVelocity(this.velocityX,this.velocityY);
            bullet.setAlive(true);

            bullet.getEntityShape().setRotate(Math.sinh(playrt.getEntityShape().getTranslateX()/player.getEntityShape().getTranslateX()));
        	bullets.add(bullet);
        	bullet.move();

            pane.getChildren().add(bullet.getEntityShape());
            bullets.forEach(bullet -> bullet.move());
            shootCounter = 0;
        }
        shootCounter++;}
        }.start();
    }

}

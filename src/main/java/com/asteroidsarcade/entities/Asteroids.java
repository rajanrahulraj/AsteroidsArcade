package com.asteroidsarcade.entities;
import com.asteroidsarcade.main.AsteroidsGame;
import com.asteroidsarcade.entities.base.GameEntity;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Asteroids extends GameEntity {

    public Point2D location;
    public int numSplit;
    private double angle;
    public double velocity;
    public double maxVelocity = 2.0;

    public Asteroids(Polygon polygon, double x, double y, double velocity) {
        super(polygon, x, y);
        this.velocity = velocity;
        location = new Point2D(x, y);
    }
    
    // Logic to move and rotate asteroids here.
     public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void move() {
        double radians = Math.toRadians(angle);
        double changeX = Math.cos(radians) * velocity;
        double changeY = Math.sin(radians) * velocity;
        location = location.add(changeX, changeY);
        entityShape.setTranslateX(location.getX());
        entityShape.setTranslateY(location.getY());


        // Code below is used to ensure that all entities stay within the screen.
        if (this.entityShape.getTranslateX() < 0) {
            this.entityShape.setTranslateX(this.entityShape.getTranslateX() + AsteroidsGame.WIDTH);
        }

        if (this.entityShape.getTranslateX() > AsteroidsGame.WIDTH) {
            this.entityShape.setTranslateX(this.entityShape.getTranslateX() % AsteroidsGame.WIDTH);
        }

        if (this.entityShape.getTranslateY() < 0) {
            this.entityShape.setTranslateY(this.entityShape.getTranslateY() + AsteroidsGame.HEIGHT);
        }

        if (this.entityShape.getTranslateY() > AsteroidsGame.HEIGHT) {
            this.entityShape.setTranslateY(this.entityShape.getTranslateY() % AsteroidsGame.HEIGHT);
        }
    }

    public void handleCollision(List<Bullet> bullets, List<Asteroids> asteroids, Player player, Pane pane) {
    // Remove bullets and asteroids that have collided
    bullets.removeIf(bullet -> {
        for (Asteroids asteroid : asteroids) {
            if (bullet.hasCollided(asteroid)) {
                pane.getChildren().remove(bullet.getEntityShape());
                // Divide large and medium asteroids into smaller ones
                if (asteroid instanceof LargeAsteroids) {
                    
                    MediumAsteroids newAsteroid1 = new MediumAsteroids(asteroid.location.getX(), asteroid.location.getY(), velocity);
                    MediumAsteroids newAsteroid2 = new MediumAsteroids(asteroid.location.getX(), asteroid.location.getY(), velocity);
                    asteroids.add(newAsteroid1);
                    asteroids.add(newAsteroid2);
                    newAsteroid1.getEntityShape().setFill(Color.BLUE); // Set fill color of new asteroid 1 to blue
                    newAsteroid2.getEntityShape().setFill(Color.BLUE); // Set fill color of new asteroid 2 to blue
                    newAsteroid1.setAngle(Math.random() * 360);
                    newAsteroid2.setAngle(Math.random() * 360);
                    pane.getChildren().add(newAsteroid1.getEntityShape());
                    pane.getChildren().add(newAsteroid2.getEntityShape());

                } else if (asteroid instanceof MediumAsteroids) {

                    SmallAsteroids newAsteroid1 = new SmallAsteroids(asteroid.location.getX(), asteroid.location.getY(), velocity);
                    SmallAsteroids newAsteroid2 = new SmallAsteroids(asteroid.location.getX(), asteroid.location.getY(), velocity);
                    asteroids.add(newAsteroid1);
                    asteroids.add(newAsteroid2);
                    newAsteroid1.getEntityShape().setFill(Color.BLUE); // Set fill color of new asteroid 1 to blue
                    newAsteroid2.getEntityShape().setFill(Color.BLUE); // Set fill color of new asteroid 2 to blue
                    newAsteroid1.setAngle(Math.random() * 360);
                    newAsteroid2.setAngle(Math.random() * 360);
                    pane.getChildren().add(newAsteroid1.getEntityShape());
                    pane.getChildren().add(newAsteroid2.getEntityShape());
                } 

                pane.getChildren().remove(asteroid.getEntityShape());
                asteroids.remove(asteroid);
                return true;
            }
        }
        return false;
    });

    // Check collisions for player
    for (Asteroids asteroid : asteroids) {
        if (asteroid.hasCollided(player)) {
            
            System.out.println(player.getRemainingLives());
            player.decreaseLife();
        }
    }
}

}

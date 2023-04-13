package com.asteroidsarcade.controllers;

import com.asteroidsarcade.entities.*;
import com.asteroidsarcade.entities.base.GameEntity;
import com.asteroidsarcade.main.AsteroidsGame;

// for reading writing highscore
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import javafx.scene.control.*;
import java.io.File;
import java.io.FileWriter;





import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Math;
import java.util.Timer;
import java.util.TimerTask;


public class GameController {

    private Pane pane;
    private Pane homePane;
    private Stage stage;
    private Stage homeStage;
    private Scene scene;
    private Player player;
    private Alien alien;
    // variables for scoring systems
    private int score = 0;
    private String highscore = "";
    List<Bullet> bullets = new ArrayList<>();
    List<Bullet> alienBullets = new ArrayList<>();

    List<Asteroids> asteroids = new ArrayList<>();


    public GameController(Pane pane, Stage stage) {
        this.homePane = pane;
        this.pane = new Pane();
        this.pane.setPrefSize(AsteroidsGame.WIDTH, AsteroidsGame.HEIGHT);
        this.homeStage = stage;
        this.homeStage.hide();
        this.stage = new Stage();
        this.scene = setGameScene();
        this.stage.show();
    }


    public Scene setGameScene(){
        this.pane.setBackground(new Background(new BackgroundFill(Color.GREY, null, null)));
        this.scene = new Scene(this.pane);
        this.stage.setTitle("Asteroids - Game");
        this.stage.setScene(this.scene);
        this.scene.setFill(Color.GREY);
        return this.scene;
    }


    public void startGame(){
        // add the player entity into the pane
        Player player = addPlayer();

        //adding test asteroids of all shapes (A)
        addAsteroids(2, 2, 2);


        //when press the keyboard, make the player move smoothly.
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

        this.scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        this.scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });
        // control the keyboard
        new AnimationTimer() {

            @Override
            public void handle(long nanosec) {
                handleKeyPressAction(pressedKeys);// Rick suggests that codes about astroids should be removed from this function and relocated to a new AnimationTimer cuz astroids do not relate to the keyboard.
            }
        }.start();


        AnimationTimer alienAnimation = new AnimationTimer() {
            
            private long lastTime = 0;
            private long shootingInterval = 1_000_000_000L; 
            Alien alien= addAlien();
            
            @Override
            public void handle(long now) {
                
                if (now - lastTime >= shootingInterval){
                    lastTime = now;
                    Bullet bullet = new Bullet((int) alien.getEntityShape().getTranslateX(),(int) alien.getEntityShape().getTranslateY());

                    
                    if (player.getEntityShape().getTranslateX() == alien.getEntityShape().getTranslateX()){
                        bullet.getEntityShape().setRotate(90);
                    }
                    else if(player.getEntityShape().getTranslateX() > alien.getEntityShape().getTranslateX()){
                        bullet.getEntityShape().setRotate(Math.atan((player.getEntityShape().getTranslateY() - alien.getEntityShape().getTranslateY()) / (player.getEntityShape().getTranslateX() - alien.getEntityShape().getTranslateX())) * 180 / Math.PI);
                    }
                    else if(player.getEntityShape().getTranslateX() < alien.getEntityShape().getTranslateX()){
                        bullet.getEntityShape().setRotate(Math.atan((player.getEntityShape().getTranslateY() - alien.getEntityShape().getTranslateY()) / (player.getEntityShape().getTranslateX() - alien.getEntityShape().getTranslateX())) * 180 / Math.PI + 180);
                    }
                    
                    
                    bullets.add(bullet);
                    pane.getChildren().add(bullet.getEntityShape());

                }
                bullets.forEach(bullet -> bullet.move());
                alien.move();
                if (alien.getEntityShape().getTranslateX()>500){
                    removeEntity(alien);
                    // removeEntity(bullet);
                }
            }
        };
        alienAnimation.start();
    }


    public Scene getScene(){
        return this.scene;
    }

    // liao add the hyperspaceKeyPressed variable here, to set the H key press just once a time.
    private boolean hyperspaceKeyPressed = false;
    public void handleKeyPressAction(Map<KeyCode, Boolean> pressedKeys){
        // press left arrow
        if(pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
            this.player.turnLeft();
        }

        // press right arrow
        if(pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
            this.player.turnRight();
        }

        // press up arrow, the player move faster.
        if(pressedKeys.getOrDefault(KeyCode.UP, false)) {
            this.player.applyThrust();
        }
        
        if (pressedKeys.getOrDefault(KeyCode.H, false)) {
            if (!hyperspaceKeyPressed) {
                this.player.hyperspaceJump(groupEnemies());
                hyperspaceKeyPressed = true;
            }
        } else {
            hyperspaceKeyPressed = false;
        }

        // press space to shoot
        if (pressedKeys.getOrDefault(KeyCode.SPACE, false)){
        	if (player.getIsUncollisionable()) {
                return; // do nothing if player is invisible
            }
            // When shooting the bullet in the same direction as the ship
        	Bullet bullet = new Bullet((int) player.getEntityShape().getTranslateX(),
                    (int) player.getEntityShape().getTranslateY());

        	bullet.getEntityShape().setRotate(player.getEntityShape().getRotate());
        	bullets.add(bullet);
        	bullet.move();
            pane.getChildren().add(bullet.getEntityShape());
            pressedKeys.clear();
        }

        this.player.move();
        // shooting
        bullets.forEach(bullet -> bullet.move());

        // Move each asteroid
        asteroids.forEach(asteroid -> asteroid.move());

        // Check collisions for each asteroid
        asteroids.forEach(asteroid -> asteroid.handleCollision(bullets, asteroids, player, pane));
    }


    public void handleCollision() {
        this.bullets.forEach(bullet -> {
            if (bullet.hasCollided(this.player)) {
                player.decreaseLife();
            }
        });

        this.asteroids.forEach(asteroid -> {
            if (asteroid.hasCollided(this.player)) {
                player.decreaseLife();
            }
        });
    }
    

    public void addAsteroids(int numSmallAsteroids, int numMediumAsteroids, int numLargeAsteroids) {
        // TO-DO: Add condition to add asteroids based on level

        for (int i = 0; i < numSmallAsteroids; i++) {
            SmallAsteroids smallAsteroid = new SmallAsteroids();
            asteroids.add(smallAsteroid);
        }

        for (int i = 0; i < numMediumAsteroids; i++) {
            MediumAsteroids mediumAsteroid = new MediumAsteroids();
            asteroids.add(mediumAsteroid);
        }

        for (int i = 0; i < numLargeAsteroids; i++) {
            LargeAsteroids largeAsteroid = new LargeAsteroids();
            asteroids.add(largeAsteroid);
        }

        // Generate a random angle between 0 and 360 degrees
        // Move each asteroid at the random angle
        this.asteroids.forEach(asteroid -> {
            asteroid.setAngle(Math.random() * 360);
            this.pane.getChildren().add(asteroid.getEntityShape());
        });
    }


    public Player addPlayer() {
        this.player = new Player();
        this.pane.getChildren().add(player.getEntityShape());
        return this.player;
    }
    

    public Alien addAlien() {
        this.alien = new Alien();
        this.pane.getChildren().add(alien.getEntityShape());
        return this.alien;
    }
    
    public List<GameEntity> groupEnemies() {
    	List<GameEntity> enemies = new ArrayList<GameEntity>();
    	enemies.addAll(asteroids);
    	enemies.addAll(bullets);
    	enemies.add(alien); 
    	return enemies;
    }


    public void removeEntity(GameEntity entity) {
        this.pane.getChildren().remove(entity.getEntityShape());
    }
    
    // someone needs to put this method when the player dies as in if player life less than 0 then call this method
    public void CheckScore() {
    	
    	System.out.println(highscore);
    	// if highscore is set
    	if (score > Integer.parseInt((highscore.split(":")[1]))) {
    		// need javafx box that comes up to input in name
    		// and takes the input as variable into name
    		String name = ///need box java fx to input
    		highscore = name + ":" + score;
    		
    		File scoreFile = new File("highscore.dat");
    		if (!scoreFile.exists()) {
    			try {
    				scoreFile.createNewFile();
    			}	catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    		FileWriter writeFile = null;
    		BufferedWriter writer = null;
    		try {
    			writeFile = new FileWriter(scoreFile);
    			writer = new BufferedWriter(writeFile);
    			writer.write(this.highscore);
    		}
    		catch (Exception e) {
    			//errors if file isnt found
    		}
    		finally {
    			try {
    				if (writer != null) 
        				writer.close();
    			}
    				catch (Exception e) {}
    			}
    		}
    }
    
    // highscore 
    public String GetHighScore(){
    	// format: Marc:100
    	// same directory as class because no slash directory under
    	FileReader readFile = null;
    	BufferedReader reader = null;
    	try {
    		readFile = new FileReader("highscore.dat");
    		reader = new BufferedReader(readFile);
    		return reader.readLine();
    	}
    	catch (Exception e) {
    		return "Anon:0";
    	}
    	finally {
    		try {
    			if (reader != null)
    				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    
    
    
}

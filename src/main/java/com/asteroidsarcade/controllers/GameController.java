package com.asteroidsarcade.controllers;

import com.asteroidsarcade.entities.*;
import com.asteroidsarcade.entities.base.GameEntity;
import com.asteroidsarcade.main.AsteroidsGame;

// for reading writing highscore
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
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


public class GameController {

    private Pane pane;
    private Pane homePane;
    private Stage stage;
    private Stage homeStage;
    private Scene scene;
    private Player player;
    // variables for scoring systems
    private int score = 0;
    private String highscore = "";
    // variable to shoot every 0.5 seconds
    private Timer timer;
    List<Bullet> bullets = new ArrayList<>();
    List<Bullet> alienBullets = new ArrayList<>();

    List<Asteroids> asteroids = new ArrayList<>();
    List<Alien> aliens = new ArrayList<>();


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
<<<<<<< HEAD
        // add the player entity into the pane
        Player player = addPlayer();

        // adding test asteroids of all shapes (A)
        addAsteroids(2, 2, 2);
=======
        addCharacters();
>>>>>>> 4e2cdcbe87bce38aa024e700732bbec031a400bc

        // adding alien entity to the pane
        Alien alien = addAlien();

        // initialize a timer
        timer = new Timer();

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
<<<<<<< HEAD
                handleKeyPressAction(pressedKeys);// Rick suggests that codes about astroids should be removed from this function and relocated to a new AnimationTimer cuz astroids do not relate to the keyboard. 
                //👍
=======
                handleKeyPressAction(pressedKeys);
>>>>>>> 4e2cdcbe87bce38aa024e700732bbec031a400bc
            }
        }.start();
        
        // alien shooting
        new AnimationTimer() {
            
            private long lastTime = 0;
<<<<<<< HEAD
            private long shootingInterval = 1_000_000_000L; 

            @Override
            public void handle(long now) {
                alienMovement(lastTime, shootingInterval, now);
=======
            private long shootingInterval = 1_000_000_000L;
            
            @Override
            public void handle(long now) {
                aliens.forEach(alien -> {
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
                });
>>>>>>> 4e2cdcbe87bce38aa024e700732bbec031a400bc
            }
        }.start();
}

    // method that checks if the entity is on the screen (A)
    private boolean isOnScreen(GameEntity ge) {
        System.out.println("Alien position: (" + alien.getEntityShape().getTranslateX() + ", " + alien.getEntityShape().getTranslateY() + ")");
        if (ge.getEntityShape().getTranslateX() < AsteroidsGame.WIDTH && ge.getEntityShape().getTranslateY() < AsteroidsGame.HEIGHT) {
            return true;
        } else {
            return false;
        }
    }


    public Scene getScene(){
        return this.scene;
    }

    public void addCharacters(){
        // add the player entity into the pane
        Player player = addPlayer();

        //adding test asteroids of all shapes (A)
        LevelController levelController = new LevelController();
        List<GameEntity> enemies =  levelController.addEnemiesBasedOnLevel();

        enemies.forEach(enemy ->{
            if (enemy instanceof Asteroids){
                this.asteroids.add((Asteroids) enemy);
            } else if (enemy instanceof  Alien) {
                this.aliens.add((Alien)enemy);
            }
            this.pane.getChildren().add(enemy.getEntityShape());
        });
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
            pressedKeys.remove(KeyCode.SPACE);
        }

        this.player.move();

        // shooting
        bullets.forEach(bullet -> bullet.move());

        // Move each asteroid
        asteroids.forEach(asteroid -> asteroid.move());

        // Check collisions for each asteroid
        asteroids.forEach(asteroid -> asteroid.handleCollision(bullets, asteroids, player, pane));
    }

    public void alienMovement(long lastTime, long shootingInterval, long now) {

        this.alien.move();

        if (isOnScreen(this.alien) && (now - lastTime >= shootingInterval)) {
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

            bullet.move();

            if (!isOnScreen(bullet)) {
                pane.getChildren().remove(bullet.getEntityShape());
                bullets.remove(bullet);
            }

        } else {
            removeEntity(alien);
        }
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


    public Player addPlayer() {
        this.player = new Player();
        this.pane.getChildren().add(player.getEntityShape());
        return this.player;
    }

    
    public List<GameEntity> groupEnemies() {
    	List<GameEntity> enemies = new ArrayList<GameEntity>();
    	enemies.addAll(asteroids);
    	enemies.addAll(bullets);
    	enemies.addAll(aliens);
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
    		String name = "";
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

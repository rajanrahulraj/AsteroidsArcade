package com.asteroidsarcade.controllers;

import com.asteroidsarcade.components.GeneralButton;
import com.asteroidsarcade.entities.*;
import com.asteroidsarcade.entities.base.GameEntity;
import com.asteroidsarcade.entities.base.GameScore;
import com.asteroidsarcade.main.AsteroidsGame;
import static com.asteroidsarcade.config.AppConstants.*;

// for reading writing highscore
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;





import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;
import java.lang.Math;
import java.util.concurrent.atomic.AtomicInteger;


public class GameController extends GeneralController{


    private Player player;
    // variables for scoring systems
    private AtomicInteger score = new AtomicInteger(0);
    private String highscore = "";
    List<Bullet> bullets = new ArrayList<>();
    List<Bullet> alienBullets = new ArrayList<>();

    List<Asteroids> asteroids = new ArrayList<>();
    List<Alien> aliens = new ArrayList<>();
    // variable to shoot every 0.5 seconds
    private Timer timer;
    private AnimationTimer animationTimer;


    private LevelController levelController;
    Label scoreLabel;
    Label levelLabel;
    Label lifeLabel;


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
        levelController = new LevelController();
        // add the player entity into the pane
        addPlayer();
        addCharacters();
        displayGameStats();
        displayMenuButton();

        //when press the keyboard, make the player move smoothly.
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();
        this.scene.getRoot().requestFocus();
        this.scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        this.scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });
        
        
        // liaoliao change the AnimationTimer code here
//         control the keyboard
        this.animationTimer = new AnimationTimer() {
            @Override
            public void handle(long nanosec) {
                handleKeyPressAction(pressedKeys);
                updateCharactersOnScreen();
            }
        };
        this.animationTimer.start();



        AnimationTimer alienAnimation = new AnimationTimer() {
            
            private long lastTime = 0;
            private long shootingInterval = 1_000_000_000L;// shoot every 1 second

            // @Override
            // public void start() {
            //     lastTime = System.nanoTime();
            //     super.start();
            // }
            // @Override
            public void handle(long now) {
                // long elapsedTime = now - lastTime;
                alienMovement(now,lastTime);
                

            }
        };
        alienAnimation.start();
    }
    
    // liaoliao add the code here
    public void stopGame() {
        this.animationTimer.stop();
        
        Label gameOverLabel = new Label("Game Over!");
        gameOverLabel.setFont(new Font(100));
        gameOverLabel.setLayoutX(AsteroidsGame.WIDTH/2 - 250); 
        gameOverLabel.setLayoutY(AsteroidsGame.HEIGHT/2 - 200); 
        gameOverLabel.setTextFill(Color.WHITE);
        
        
       


        this.pane.getChildren().addAll(gameOverLabel);

    }

    public Scene getScene(){
        return this.scene;
    }

    public void addCharacters(){
        //adding test asteroids of all shapes (A)
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

    public void clearCharacters(){
        ObservableList<Node> children = this.pane.getChildren();
        children.removeIf(child -> child instanceof Polygon && child != this.player.getEntityShape());
//        for (Node child : children){
//            if (){
//                this.pane.getChildren().remove(child);
//            }
//        }
        this.asteroids.clear();
        this.aliens.clear();
        this.bullets.clear();
    }


    private void updateCharactersOnScreen() {
        int[] enemyCounts = levelController.getEnemyCounts();
        if (this.asteroids.stream().filter(asteroid->asteroid instanceof SmallAsteroids).count() < enemyCounts[0]){

        }
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
        	if (player.getIsUncollisionable() || this.bullets.size() >= MAX_BULLET_FREQUENCY) {
                pressedKeys.remove(KeyCode.SPACE);
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

        // Calculate score before asteroids disappear;
        updateScore();
        // Check collisions for each asteroid
        asteroids.forEach(asteroid -> asteroid.handleCollision(bullets, asteroids, player, pane));

        bullets.forEach(bullet -> {
            if (System.currentTimeMillis() - bullet.getCreatedTime() > BULLET_LIFE_TIME){
                this.pane.getChildren().remove(bullet.getEntityShape());
                this.bullets.remove(bullet);
            }
        });
    }

    public void alienMovement(long now, long lastTime) {
        for (Alien alien : this.aliens){
            if (isOnScreen(alien) && (now-lastTime >= 1_000_000_000L)) {

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

                alienBullets.add(bullet);
                // bullet.move();
                this.pane.getChildren().add(bullet.getEntityShape());
            }
        }
        alienBullets.forEach(shot -> shot.move());
        alienBullets.removeIf(shot->!isOnScreen(shot));
        aliens.forEach(obj -> obj.move());
    }

    public void updateScore() {
        this.bullets.forEach(bullet -> {
            for (Asteroids asteroid : asteroids){
                if (asteroid.hasCollided(bullet)) {
                    if (asteroid instanceof LargeAsteroids){
                        this.scoreLabel.setText("Score: " + score.addAndGet(Points.LARGE_ASTEROID.getPoint()));
                    } else if (asteroid instanceof MediumAsteroids) {
                        this.scoreLabel.setText("Score: " + score.addAndGet(Points.MEDIUM_ASTEROID.getPoint()));
                    } else {
                        this.scoreLabel.setText("Score: " + score.addAndGet(Points.SMALL_ASTEROID.getPoint()));
                    }
                    // Update the level if score goes above a value. Required only when the score gets updated
                    updateLevel();
                }
            }
            for (Alien alien : aliens){
                if (bullet.hasCollided(alien)) {
                    this.scoreLabel.setText("Score: " + score.getAndAdd(Points.ALIEN.getPoint()));
                    // Update the level if score goes above a value. Required only when the score gets updated
                    updateLevel();
                }
            }
        });

        this.asteroids.forEach(asteroid -> {
            if (asteroid.hasCollided(this.player)) {
                player.decreaseLife();
                this.lifeLabel.setText("Life:" + this.player.getRemainingLives());
             // liaoliao add the code below
                if (this.player.getRemainingLives() <= 0) {
                	CheckScore();
                }
            }
        });
    }

    public void updateLevel(){
        int newLevel = levelController.newLevel(score.get());
        if (newLevel != levelController.getLevel()){
             levelController.setLevel(newLevel);
            this.levelLabel.setText("Level:" + levelController.getLevel());
            // clear all characters on screen before proceeding to next level
            clearCharacters();
            addCharacters();
        }
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
    	highscore = "Anon:0";
    	// if highscore is set
    	String[] highscoreParts = highscore.split(":");
    	if (highscoreParts.length >= 2 && score.get() > Integer.parseInt(highscoreParts[1])) {
    		
    		
    		// need javafx box that comes up to input in name
    		Label gameOverLabel = new Label("Game Over!");
            gameOverLabel.setFont(new Font(100));
            gameOverLabel.setLayoutX(AsteroidsGame.WIDTH/2 - 250); 
            gameOverLabel.setLayoutY(AsteroidsGame.HEIGHT/2 - 200); 
            gameOverLabel.setTextFill(Color.WHITE);
            
            
            
            Label PromptText = new Label("Please Enter your name: ");
            PromptText.setFont(new Font(30));
            PromptText.setLayoutX(AsteroidsGame.WIDTH/2 - 150); 
            PromptText.setLayoutY(AsteroidsGame.HEIGHT/2); 
            PromptText.setTextFill(Color.WHITE);
            
            TextField nameInput = new TextField();
            nameInput.setFont(new Font(20));
            nameInput.setPromptText("Please Enter your name");
            nameInput.setLayoutX(AsteroidsGame.WIDTH/2 - 200);
            nameInput.setLayoutY(AsteroidsGame.HEIGHT/2 + 60); 

            GeneralButton submitButton = new GeneralButton("Save");
            
            submitButton.setLayoutX(AsteroidsGame.WIDTH/2 + 40);
            submitButton.setLayoutY(AsteroidsGame.HEIGHT/2 + 60); 
            
            submitButton.setOnAction(e -> {
                String playerName = nameInput.getText();
                String newHighScore = playerName + ":" + score.get(); // concatenate player name and score
                highscore = newHighScore; // update the current highscore
                System.out.println("Player name: " + playerName);
                saveHighScore();
                AsteroidsGame.sceneController.toggleStageView(stage, homeStage);
            });


            this.pane.getChildren().addAll(gameOverLabel, PromptText, nameInput, submitButton);
            
    	}
    	else {
    		stopGame();
    	}
    }
    
    
    //saving highscore
    private void saveHighScore() {
        //new box
        File scoreFile = new File("highscore.dat");
        if (!scoreFile.exists()) {
            try {
                scoreFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter writeFile = null;
        BufferedWriter writer = null;
        try {
            writeFile = new FileWriter(scoreFile, true);
            writer = new BufferedWriter(writeFile);
            writer.write(highscore);
            writer.write(System.lineSeparator());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


//    public void displayHighScore() {
//        Stage highScoreStage = new Stage();
//        highScoreStage.setTitle("High Scores");
//
//        // Create a label to display the high score
//        Label highScoreLabel = new Label("High Score: " + GetHighScore());
//        highScoreLabel.setFont(new Font(20));
//        highScoreLabel.setLayoutX(10);
//        highScoreLabel.setLayoutY(10);
//
//        // Add the label to a new scene
//        Scene highScoreScene = new Scene(new VBox(highScoreLabel), 300, 100);
//
//        // Set the scene for the new stage
//        highScoreStage.setScene(highScoreScene);
//
//        // Show the new stage
//        highScoreStage.show();
//    }
    
    public void resetHighScore() {
        try {
            FileWriter writer = new FileWriter("highscore.dat");
            writer.write("Anon:0");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean isOnScreen(GameEntity ge) {
//        System.out.println("Character position: (" + ge.getEntityShape().getTranslateX() + ", " + ge.getEntityShape().getTranslateY() + ")");
        if (ge.getEntityShape().getTranslateX() < AsteroidsGame.WIDTH && ge.getEntityShape().getTranslateY() < AsteroidsGame.HEIGHT) {
            return true;
        } else {
            return false;
        }
    }

    private void displayGameStats(){
        int gameStatBegin_X = 20;
        int gameStatBegin_Y = 20;
        scoreLabel = displayScore(gameStatBegin_X, gameStatBegin_Y);
        levelLabel = displayLevel(gameStatBegin_X, scoreLabel.getTranslateY() + 40);
        lifeLabel = displayLife(gameStatBegin_X, levelLabel.getTranslateY() + 40);
        List<Label> labels = new ArrayList<>();
        labels.add(scoreLabel);
        labels.add(levelLabel);
        labels.add(lifeLabel);
        this.pane.getChildren().addAll(labels);
    }
    private Label displayScore(double position_x, double position_y) {
        Label scoreLabel = new Label("Score: " + score);
        scoreLabel.setTranslateX(position_x);
        scoreLabel.setTranslateY(position_y);
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setFont(Font.font(30));
        scoreLabel.setVisible(true);
        return scoreLabel;
    }
    private Label displayLevel(double position_x, double position_y) {
        Label levelLabel = new Label("Level: " + this.levelController.getLevel());
        levelLabel.setTranslateX(position_x);
        levelLabel.setTranslateY(position_y);
        levelLabel.setTextFill(Color.WHITE);
        levelLabel.setFont(Font.font(30));
        levelLabel.setVisible(true);
        return levelLabel;
    }
    private Label displayLife(double position_x, double position_y){
        Label lifeLabel = new Label("Life: " + this.player.getRemainingLives());
        lifeLabel.setTranslateX(position_x);
        lifeLabel.setTranslateY(position_y);
        lifeLabel.setTextFill(Color.WHITE);
        lifeLabel.setFont(Font.font(30));
        lifeLabel.setVisible(true);
        return lifeLabel;
    }

}

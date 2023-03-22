package com.asteroidsarcade.main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import com.asteroidsarcade.entities.SpaceShip;
import com.asteroidsarcade.entities.Player;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class HelloApplication extends Application {
	

//    private Parent createContent() {
//        root = new Pane();
//        root.setPrefSize(600, 600);
//
//        player = new Player();
//        player.setVelocity(new Point2D(1, 0));
//        addGameObject(player, 300, 300);
//
//        AnimationTimer timer = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                onUpdate();
//            }
//        };
//        timer.start();
//
//        return root;
//    }
	
	
//	   @Override
//	    public void start(Stage stage) throws IOException {
//	        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//	        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
//
//	        stage.setTitle("Asteroids");
//	        stage.setScene(scene);
//	        stage.show();
//	    }
//	    
//	    
	 // the below code added by liaoliao.
		
		// this two variable are used to set all entities move on the screen.
		public static int WIDTH = 900;
	    public static int HEIGHT = 600;
		
		@Override
	    public void start(Stage stage) throws Exception {
			// create the pane, all entities should in the pane
	        Pane pane = new Pane();
	        // set the size of the screen.
	        pane.setPrefSize(WIDTH, HEIGHT); 
	        // Background color
	        pane.setStyle("-fx-background-color: grey");
	        
	        Player player = new Player();


	        // add the player entity into the pane
	        pane.getChildren().add(player.getEntityShape());
	        

	        Scene scene = new Scene(pane);
	        stage.setTitle("Group 4: Asteroids Game!");
	        stage.setScene(scene);
	        stage.show();
	        
//	      when press the keyboard, make the player move smoothly.
	        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

	        scene.setOnKeyPressed(event -> {
	            pressedKeys.put(event.getCode(), Boolean.TRUE);
	        });

	        scene.setOnKeyReleased(event -> {
	            pressedKeys.put(event.getCode(), Boolean.FALSE);
	        });
	        
	        // control the keyboard
	        new AnimationTimer() {

	            @Override
	            public void handle(long now) {
	            	// press left arrow
	                if(pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
	                	player.turnLeft();
	                }
	                
	                // press right arrow
	                if(pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
	                	player.turnRight();
	                }
	                
	                // press up arrow, the player move faster.
	                if(pressedKeys.getOrDefault(KeyCode.UP, false)) {
	                	player.applyThrust();
	                }

	                player.move();
	            }
	        }.start();

		}
	    
	 // the the above code added by liaoliao.

    
    
    public static void main(String[] args) {
        try{
            launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
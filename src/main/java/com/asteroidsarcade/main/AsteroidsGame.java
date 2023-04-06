package com.asteroidsarcade.main;

import com.asteroidsarcade.controllers.GameController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import com.asteroidsarcade.entities.Player;


import java.util.HashMap;
import java.util.Map;


public class AsteroidsGame extends Application {
		// this two variable are used to set all entities move on the screen.
		public static int WIDTH = 900;
	    public static int HEIGHT = 600;
		
		@Override
	    public void start(Stage stage) throws Exception {
			// create the pane, all entities should in the pane
	        Pane pane = new Pane();
	        // set the size of the screen.
	        pane.setPrefSize(WIDTH, HEIGHT);

			GameController gameController = new GameController(pane, stage);

	        // add the player entity into the pane
			Player player = gameController.addPlayer();
	        
			//adding test asteroids of all shapes (A)
			gameController.addAsteroids();

			//adding an alien
			gameController.addAlien();

	        Scene scene = gameController.getScene();
	        stage.setTitle("Group 4: Asteroids Game!");
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
	            public void handle(long nanosec) {
					gameController.handleKyPressAction(pressedKeys);

	            }
	        }.start();

		}

    public static void main(String[] args) {
        try{
            launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

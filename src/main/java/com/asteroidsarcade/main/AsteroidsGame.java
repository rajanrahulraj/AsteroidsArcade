package com.asteroidsarcade.main;

import com.asteroidsarcade.controllers.GameController;
import com.asteroidsarcade.controllers.SceneController;
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
		public static SceneController sceneController;
		
		@Override
	    public void start(Stage stage) throws Exception {
			// create the pane, all entities should in the pane
	        Pane pane = new Pane();
	        // set the size of the screen.
	        pane.setPrefSize(WIDTH, HEIGHT);

			sceneController = new SceneController(pane, stage);
			sceneController.setHomePageScene();
			sceneController.showHomePage();

		}

    public static void main(String[] args) {
        try{
            launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

package com.asteroidsarcade.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        stage.setTitle("Asteroids");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try{
            launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
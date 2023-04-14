package com.asteroidsarcade.controllers;

import com.asteroidsarcade.entities.*;
import com.asteroidsarcade.entities.base.GameEntity;

import java.util.ArrayList;
import java.util.List;

public class LevelController {
    private int level = 1;

//    Base level characters denotes the count of characters on screen for the base level.
//    Order of characters in array: Small Asteroid, Medium asteroid, Large asteroid, alien.
    private int[] enemyCounts = new int[]{3,1,1,1};
    private final int MAX_ASTEROID_COUNT = 20;
    private final int MAX_ALIEN_COUNT = 3;

    public int[] getEnemyCounts() {
        return enemyCounts;
    }

    public void setEnemyCounts(int[] enemyCounts) {
        this.enemyCounts = enemyCounts;
    }

    public int getLevel(){
        return this.level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public List<GameEntity> addEnemiesBasedOnLevel(){
        List<GameEntity> gameCharacters = new ArrayList<>();
        if (this.level == 1){
            gameCharacters.addAll(addAsteroids(enemyCounts[0], enemyCounts[1], enemyCounts[2]));
        }
        else{
            int asteroidsCount = enemyCounts[0] + enemyCounts[1] + enemyCounts[2];
            int smallAsteroidsCount = asteroidsCount < MAX_ASTEROID_COUNT ? enemyCounts[0] * this.level : enemyCounts[0];
            int mediumAsteroidsCount = enemyCounts[1] != 0 ? (asteroidsCount < MAX_ASTEROID_COUNT ? enemyCounts[1] * this.level : enemyCounts[1]) : 1;
            int largeAsteroidsCount = enemyCounts[2] != 0 ? (asteroidsCount < MAX_ASTEROID_COUNT ? enemyCounts[2] * this.level : enemyCounts[2]) : 1;
            int alienCount = enemyCounts[3] < MAX_ALIEN_COUNT ? enemyCounts[3] + 1 : MAX_ALIEN_COUNT;
            setEnemyCounts(new int[] {largeAsteroidsCount, mediumAsteroidsCount, smallAsteroidsCount, alienCount});
            gameCharacters.addAll(addAsteroids(smallAsteroidsCount, mediumAsteroidsCount, largeAsteroidsCount));
            gameCharacters.addAll(addAlien(alienCount));
        }
        return gameCharacters;
    }

    public List<Asteroids> addAsteroids(int numSmallAsteroids, int numMediumAsteroids, int numLargeAsteroids) {
        List<Asteroids> asteroidsList = new ArrayList<>();
        for (int i = 0; i < numSmallAsteroids; i++) {
            SmallAsteroids smallAsteroid = new SmallAsteroids();
            smallAsteroid.setAngle(Math.random() * 360);
            asteroidsList.add(smallAsteroid);
        }
        for (int i = 0; i < numMediumAsteroids; i++) {
            MediumAsteroids mediumAsteroid = new MediumAsteroids();
            mediumAsteroid.setAngle(Math.random() * 360);
            asteroidsList.add(mediumAsteroid);
        }
        for (int i = 0; i < numLargeAsteroids; i++) {
            LargeAsteroids largeAsteroid = new LargeAsteroids();
            largeAsteroid.setAngle(Math.random() * 360);
            asteroidsList.add(largeAsteroid);
        }
        return asteroidsList;
    }

    public List<Alien> addAlien(int alienCount) {
        List<Alien> aliensList = new ArrayList<>();
        for (int i = 0; i < alienCount; i++){
            Alien alien = new Alien();
            aliensList.add(alien);
        }
        return aliensList;
    }
}

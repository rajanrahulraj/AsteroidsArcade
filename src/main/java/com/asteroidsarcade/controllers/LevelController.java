package com.asteroidsarcade.controllers;

import com.asteroidsarcade.entities.*;
import com.asteroidsarcade.entities.base.GameEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.asteroidsarcade.config.AppConstants.*;

public class LevelController {
    private AtomicInteger level = new AtomicInteger(1);

//    Base level characters denotes the count of characters on screen for the base level.
//    Order of characters in array: Small Asteroid, Medium asteroid, Large asteroid, alien.
    private int[] enemyCounts = new int[]{DEFAULT_SMALL_ASTEROIDS_COUNT, DEFAULT_MEDIUM_ASTEROIDS_COUNT, DEFAULT_LARGE_ASTEROIDS_COUNT, DEFAULT_ALIEN_COUNT};


    public int[] getEnemyCounts() {
        return enemyCounts;
    }

    public void setEnemyCounts(int[] enemyCounts) {
        this.enemyCounts = enemyCounts;
    }

    public int getLevel(){
        return this.level.get();
    }
    public void setLevel(int level) {
        this.level.set(level);
    }

    public List<GameEntity> addEnemiesBasedOnLevel(){
        List<GameEntity> gameCharacters = new ArrayList<>();
        if (this.getLevel() == 1){
            gameCharacters.addAll(addAsteroids(enemyCounts[0], enemyCounts[1], enemyCounts[2]));
        }
        else{
            int asteroidsCount = enemyCounts[0] + enemyCounts[1] + enemyCounts[2];
            int smallAsteroidsCount = asteroidsCount < MAX_ASTEROID_COUNT ? enemyCounts[0] * this.getLevel() : enemyCounts[0];
            int mediumAsteroidsCount = enemyCounts[1] != 0 ? (asteroidsCount < MAX_ASTEROID_COUNT ? enemyCounts[1] * this.getLevel() : enemyCounts[1]) : 1;
            int largeAsteroidsCount = enemyCounts[2] != 0 ? (asteroidsCount < MAX_ASTEROID_COUNT ? enemyCounts[2] * this.getLevel() : enemyCounts[2]) : 1;
            int alienCount = enemyCounts[3] < MAX_ALIEN_COUNT ? enemyCounts[3] + 1 : MAX_ALIEN_COUNT;
            setEnemyCounts(new int[] {smallAsteroidsCount, mediumAsteroidsCount, largeAsteroidsCount, alienCount});
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

    public int newLevel(int score){
        return score/LEVEL_THRESHOLD + 1;
    }
}

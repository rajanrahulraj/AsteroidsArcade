package com.asteroidsarcade.config;

public class AppConstants {
        public static final int DEFAULT_LARGE_ASTEROIDS_COUNT = 3;
        public static final int DEFAULT_MEDIUM_ASTEROIDS_COUNT = 1;
        public static final int DEFAULT_SMALL_ASTEROIDS_COUNT = 0;
        public static final int DEFAULT_ALIEN_COUNT = 0;
        public static final int MAX_ASTEROID_COUNT = 20;
        public static final int MAX_ALIEN_COUNT = 3;

        public enum Points {

                LARGE_ASTEROID(5),
                MEDIUM_ASTEROID(10),
                SMALL_ASTEROID(15),
                ALIEN(20);

                private int point = 0;
                public int getPoint(){
                        return this.point;
                }

                private Points(int point){
                        this.point = point;
                }

        }
        public static final int LEVEL_THRESHOLD = 50;
}

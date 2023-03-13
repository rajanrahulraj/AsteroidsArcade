package com.asteroidsarcade.entities.base;
import com.asteroidsarcade.interfaces.Moveable;
import javafx.scene.shape.Polygon;

public abstract class GameEntity implements Moveable {

        private Polygon entityShape;

        public GameEntity(Polygon polygon, int x, int y) {
            this.entityShape = polygon;
            this.entityShape.setTranslateX(x);
            this.entityShape.setTranslateY(y);
        }

        public Polygon getEntityShape() {
            return entityShape;
        }

        public void setVelocity(){
            // To-Do
        }

        public void move() {
            double changeX = Math.cos(Math.toRadians(this.entityShape.getRotate()));
            double changeY = Math.sin(Math.toRadians(this.entityShape.getRotate()));
            entityShape.setTranslateX(entityShape.getTranslateX() + changeX);
            entityShape.setTranslateY(entityShape.getTranslateY() + changeY);
        }
}

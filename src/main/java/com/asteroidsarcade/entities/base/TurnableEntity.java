package com.asteroidsarcade.entities.base;

import com.asteroidsarcade.entities.base.GameEntity;
import com.asteroidsarcade.interfaces.Turnable;
import javafx.scene.shape.Polygon;

public class TurnableEntity extends GameEntity implements Turnable {
    public TurnableEntity(Polygon polygon, int x, int y) {
        super(polygon, x, y);
    }

    public void setVelocity(){
        super.setVelocity(velocityX, velocityX);
    }

    public void turnLeft() {
        this.getEntityShape().setRotate(this.getEntityShape().getRotate() - 5);
    }

    public void turnRight() {
        this.getEntityShape().setRotate(this.getEntityShape().getRotate() + 5);
    }
}

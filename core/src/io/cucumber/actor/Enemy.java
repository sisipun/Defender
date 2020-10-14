package io.cucumber.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.cucumber.actor.road.RoadType;
import io.cucumber.base.model.base.DynamicActor;
import io.cucumber.base.model.bound.RectangleBound;

public class Enemy extends DynamicActor<Rectangle> {

    private float power;
    private float health;
    private boolean passed;

    private Vector2 initialVelocity;

    public Enemy(float x, float y, float size, float horizontalVelocity, float verticalVelocity,
                 float power, float health, TextureRegion texture) {
        super(new RectangleBound(x, y, size, size), horizontalVelocity, verticalVelocity, texture);
        this.power = power;
        this.health = health;
        this.passed = false;
        this.initialVelocity = new Vector2(horizontalVelocity, verticalVelocity);
    }

    public Enemy init(float x, float y, float size, float horizontalVelocity, float verticalVelocity,
                      float power, float health, TextureRegion texture) {
        super.init(new RectangleBound(x, y, size, size), horizontalVelocity, verticalVelocity, texture);
        this.power = power;
        this.health = health;
        this.passed = false;
        this.initialVelocity = new Vector2(horizontalVelocity, verticalVelocity);
        return this;
    }

    @Override
    public void act(float delta) {
        if (passed) {
            return;
        }

        setX(getX() + velocity.x * delta);
        setY(getY() + velocity.y * delta);
        super.act(delta);
    }

    public void changeDirection(RoadType roadType) {
        if (passed) {
            return;
        }

        if (io.cucumber.actor.road.RoadType.NONE.equals(roadType)) {
            health = 0;
        } else if (io.cucumber.actor.road.RoadType.UP.equals(roadType)) {
            velocity.y = initialVelocity.y;
            velocity.x = 0;
        } else if (io.cucumber.actor.road.RoadType.DOWN.equals(roadType)) {
            velocity.y = -initialVelocity.y;
            velocity.x = 0;
        } else if (io.cucumber.actor.road.RoadType.LEFT.equals(roadType)) {
            velocity.y = 0;
            velocity.x = -initialVelocity.x;
        } else if (io.cucumber.actor.road.RoadType.RIGHT.equals(roadType)) {
            velocity.y = 0;
            velocity.x = initialVelocity.x;
        } else if (RoadType.END.equals(roadType)) {
            passed = true;
        }
    }

    public void hit(float power) {
        if (passed) {
            return;
        }

        health -= power;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public boolean isPassed() {
        return passed;
    }

    public float getPower() {
        return power;
    }
}

package io.cucumber.model.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.cucumber.base.model.base.DynamicActor;
import io.cucumber.base.model.bound.RectangleBound;
import io.cucumber.model.actor.road.RoadType;

public class Enemy extends DynamicActor<Rectangle> {

    private float power;
    private float health;
    private boolean finished;
    private Vector2 initialVelocity;

    public Enemy(float x, float y, float size, float horizontalVelocity, float verticalVelocity,
                 float power, float health, TextureRegion region) {
        super(new RectangleBound(x, y, size, size), horizontalVelocity, verticalVelocity, region);
        this.power = power;
        this.health = health;
        this.finished = false;
        this.initialVelocity = new Vector2(horizontalVelocity, verticalVelocity);
    }

    public Enemy init(float x, float y, float size, float horizontalVelocity, float verticalVelocity,
                      float power, float health, TextureRegion region) {
        super.init(new RectangleBound(x, y, size, size), horizontalVelocity, verticalVelocity, region);
        this.power = power;
        this.health = health;
        this.finished = false;
        this.initialVelocity = new Vector2(horizontalVelocity, verticalVelocity);
        return this;
    }

    @Override
    public void act(float delta) {
        if (finished) {
            return;
        }

        setX(getX() + velocity.x * delta);
        setY(getY() + velocity.y * delta);
        super.act(delta);
    }

    public void changeDirection(RoadType roadType) {
        if (RoadType.NONE.equals(roadType)) {
            health = 0;
        } else if (RoadType.UP.equals(roadType)) {
            velocity.y = initialVelocity.y;
            velocity.x = 0;
        } else if (RoadType.DOWN.equals(roadType)) {
            velocity.y = -initialVelocity.y;
            velocity.x = 0;
        } else if (RoadType.LEFT.equals(roadType)) {
            velocity.y = 0;
            velocity.x = -initialVelocity.x;
        } else if (RoadType.RIGHT.equals(roadType)) {
            velocity.y = 0;
            velocity.x = initialVelocity.x;
        } else if (RoadType.END.equals(roadType)) {
            finished = true;
        }
    }

    public void hit(float power) {
        if (finished) {
            return;
        }
        health -= power;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public boolean isFinished() {
        return finished;
    }

    public float getPower() {
        return power;
    }
}

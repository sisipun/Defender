package io.cucumber.model.character;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.cucumber.base.model.base.DynamicActor;
import io.cucumber.base.model.bound.RectangleBound;
import io.cucumber.model.road.RoadType;

public class Enemy extends DynamicActor<Rectangle> {

    private int health;
    private Vector2 initialVelocity;

    public Enemy(float x, float y, float size, float horizontalVelocity, float verticalVelocity,
                 TextureRegion region, int health) {
        super(new RectangleBound(x, y, size, size), horizontalVelocity, verticalVelocity, region);
        this.health = health;
        this.initialVelocity = new Vector2(horizontalVelocity, verticalVelocity);
    }

    public Enemy init(float x, float y, float size, float horizontalVelocity, float verticalVelocity,
                                    TextureRegion region, int health) {
        super.init(new RectangleBound(x, y, size, size), horizontalVelocity, verticalVelocity, region);
        this.health = health;
        this.initialVelocity = new Vector2(horizontalVelocity, verticalVelocity);
        return this;
    }

    @Override
    public void act(float delta) {
        setX(getX() + velocity.x * delta);
        setY(getY() + velocity.y * delta);
        super.act(delta);
    }

    public void changeDirection(RoadType roadType) {
        if (RoadType.NONE.equals(roadType) || RoadType.END.equals(roadType)) {
            health = 0;
        } else if (RoadType.UP.equals(roadType)) {
            this.velocity.y = initialVelocity.y;
            this.velocity.x = 0;
        } else if (RoadType.DOWN.equals(roadType)) {
            this.velocity.y = -initialVelocity.y;
            this.velocity.x = 0;
        } else if (RoadType.LEFT.equals(roadType)) {
            this.velocity.y = 0;
            this.velocity.x = -initialVelocity.x;
        } else if (RoadType.RIGHT.equals(roadType)) {
            this.velocity.y = 0;
            this.velocity.x = initialVelocity.x;
        }
    }

    public void hit(int power) {
        health -= power;
    }

    public boolean isDead() {
        return health <= 0;
    }
}

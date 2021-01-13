package io.cucumber.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import io.cucumber.generator.LevelBlockType;
import io.cucumber.base.actor.base.DynamicActor;
import io.cucumber.base.actor.bound.RectangleBound;

public class Enemy extends DynamicActor<Rectangle> {

    private float power;
    private float totalHealth;
    private float health;
    private boolean passed;
    private float speed;
    private TextureRegion healthTexture;
    private TextureRegion healthBackTexture;

    private Random random;

    public Enemy() {
        super(new RectangleBound(0, 0, 0, 0), 0, 0, null);
        this.power = 0;
        this.health = 0;
        this.totalHealth = 0;
        this.passed = false;
        this.speed = 0;
        this.healthTexture = null;
        this.healthBackTexture = null;
        this.random = null;
    }

    public Enemy init(float x, float y, float size, float speed, float power, float health,
                      TextureRegion texture, TextureRegion healthTexture,
                      TextureRegion healthBackTexture) {
        super.init(new RectangleBound(x, y, size, size), 0f, 0f, texture);
        this.power = power;
        this.totalHealth = health;
        this.health = health;
        this.passed = false;
        this.speed = speed;
        this.healthTexture = healthTexture;
        this.healthBackTexture = healthBackTexture;
        this.random = new Random();

        return this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(
                healthBackTexture.getTexture(),
                getX(),
                getY() + 4 * getHeight() / 3,
                getOriginX(),
                getOriginY(),
                getWidth(),
                getHeight() / 3,
                getScaleX(),
                getScaleY(),
                getRotation(),
                healthBackTexture.getRegionX(),
                healthBackTexture.getRegionY(),
                healthBackTexture.getRegionWidth(),
                healthBackTexture.getRegionHeight(),
                false,
                false
        );
        batch.draw(
                healthTexture.getTexture(),
                getX(),
                getY() + 4 * getHeight() / 3,
                getOriginX(),
                getOriginY(),
                (health / totalHealth) * getWidth(),
                getHeight() / 3,
                getScaleX(),
                getScaleY(),
                getRotation(),
                healthTexture.getRegionX(),
                healthTexture.getRegionY(),
                healthTexture.getRegionWidth(),
                healthTexture.getRegionHeight(),
                false,
                false
        );
    }

    public void changeDirection(LevelBlockType levelBlockType) {
        if (passed) {
            return;
        }

        if (LevelBlockType.ROAD_UP.equals(levelBlockType) && velocity.y == 0) {
            velocity.y = speed;
            velocity.x = 0;
        } else if (LevelBlockType.ROAD_DOWN.equals(levelBlockType) && velocity.y == 0) {
            velocity.y = -speed;
            velocity.x = 0;
        } else if (LevelBlockType.ROAD_LEFT.equals(levelBlockType) && velocity.x == 0) {
            velocity.y = 0;
            velocity.x = -speed;
        } else if (LevelBlockType.ROAD_RIGHT.equals(levelBlockType) && velocity.x == 0) {
            velocity.y = 0;
            velocity.x = speed;
        } else if (LevelBlockType.ROAD_HORIZONTAL_RAND.equals(levelBlockType) && velocity.x == 0) {
            velocity.y = 0;
            int direction = random.nextInt(2);
            if (direction == 0) {
                velocity.x = -speed;
            } else {
                velocity.x = speed;
            }
        } else if (LevelBlockType.ROAD_END.equals(levelBlockType)) {
            velocity.y = 0;
            velocity.x = 0;
            passed = true;
        }
    }

    public void hit(float power) {
        if (passed) {
            return;
        }

        health -= power;
    }

    public float getPower() {
        return power;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public boolean isPassed() {
        return passed;
    }
}

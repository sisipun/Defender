package io.cucumber.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.cucumber.actor.area.AreaType;
import io.cucumber.base.model.base.DynamicActor;
import io.cucumber.base.model.bound.RectangleBound;

public class Enemy extends DynamicActor<Rectangle> {

    private float power;
    private float totalHealth;
    private float health;
    private int cost;
    private boolean passed;

    private Vector2 initialVelocity;

    private TextureRegion healthTexture;
    private TextureRegion healthBackTexture;

    public Enemy(float x, float y, float width, float height, float horizontalVelocity,
                 float verticalVelocity, float power, float health, int cost, TextureRegion texture,
                 TextureRegion healthTexture, TextureRegion healthBackTexture) {
        super(new RectangleBound(x, y, width, height), horizontalVelocity, verticalVelocity, texture);
        this.power = power;
        this.health = health;
        this.totalHealth = health;
        this.cost = cost;
        this.passed = false;
        this.initialVelocity = new Vector2(horizontalVelocity, verticalVelocity);
        this.healthTexture = healthTexture;
        this.healthBackTexture = healthBackTexture;
    }

    public Enemy init(float x, float y, float size, float horizontalVelocity, float verticalVelocity,
                      float power, float health, int cost, TextureRegion texture,
                      TextureRegion healthTexture, TextureRegion healthBackTexture) {
        super.init(new RectangleBound(x, y, size, size), horizontalVelocity, verticalVelocity, texture);
        this.power = power;
        this.health = health;
        this.cost = cost;
        this.passed = false;
        this.initialVelocity = new Vector2(horizontalVelocity, verticalVelocity);
        this.healthTexture = healthTexture;
        this.healthBackTexture = healthBackTexture;
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

    @Override
    public void act(float delta) {
        if (passed) {
            return;
        }

        setX(getX() + velocity.x * delta);
        setY(getY() + velocity.y * delta);
        super.act(delta);
    }

    public void changeDirection(AreaType areaType) {
        if (passed) {
            return;
        }

        if (AreaType.NONE.equals(areaType)) {
            health = 0;
        } else if (AreaType.UP.equals(areaType)) {
            velocity.y = initialVelocity.y;
            velocity.x = 0;
        } else if (AreaType.DOWN.equals(areaType)) {
            velocity.y = -initialVelocity.y;
            velocity.x = 0;
        } else if (AreaType.LEFT.equals(areaType)) {
            velocity.y = 0;
            velocity.x = -initialVelocity.x;
        } else if (AreaType.RIGHT.equals(areaType)) {
            velocity.y = 0;
            velocity.x = initialVelocity.x;
        } else if (AreaType.END.equals(areaType)) {
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

    public int getCost() {
        return cost;
    }

    public boolean isPassed() {
        return passed;
    }
}

package io.cucumber.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.actor.area.AreaType;
import io.cucumber.base.actor.base.DynamicActor;
import io.cucumber.base.actor.bound.RectangleBound;

public class Enemy extends DynamicActor<Rectangle> {

    private float power;
    private float totalHealth;
    private float health;
    private int cost;
    private boolean passed;

    private float speed;

    private TextureRegion healthTexture;
    private TextureRegion healthBackTexture;

    public Enemy() {
        super(new RectangleBound(0, 0, 0, 0), 0, 0, null);
        this.power = 0;
        this.health = 0;
        this.totalHealth = 0;
        this.cost = 0;
        this.passed = false;
        this.speed = 0;
        this.healthTexture = null;
        this.healthBackTexture = null;
    }

    public Enemy init(float x, float y, float size, float speed, float power, float health,
                      int cost, TextureRegion texture, TextureRegion healthTexture,
                      TextureRegion healthBackTexture) {
        super.init(new RectangleBound(x, y, size, size), speed, speed, texture);
        this.power = power;
        this.totalHealth = health;
        this.health = health;
        this.cost = cost;
        this.passed = false;
        this.speed = speed;
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

    public void changeDirection(AreaType areaType) {
        if (passed) {
            return;
        }

        if (AreaType.ROAD_UP.equals(areaType)) {
            velocity.y = speed;
            velocity.x = 0;
        } else if (AreaType.ROAD_DOWN.equals(areaType)) {
            velocity.y = -speed;
            velocity.x = 0;
        } else if (AreaType.ROAD_LEFT.equals(areaType)) {
            velocity.y = 0;
            velocity.x = -speed;
        } else if (AreaType.ROAD_RIGHT.equals(areaType)) {
            velocity.y = 0;
            velocity.x = speed;
        } else if (AreaType.ROAD_END.equals(areaType)) {
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

    public int getCost() {
        return cost;
    }

    public boolean isPassed() {
        return passed;
    }
}

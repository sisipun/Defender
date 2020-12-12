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

    public Enemy(float x, float y, float width, float height, float speed, float power,
                 float health, int cost, TextureRegion texture, TextureRegion healthTexture,
                 TextureRegion healthBackTexture) {
        super(new RectangleBound(x, y, width, height), speed, speed, texture);
        this.power = power;
        this.health = health;
        this.totalHealth = health;
        this.cost = cost;
        this.passed = false;
        this.speed = speed;
        this.healthTexture = healthTexture;
        this.healthBackTexture = healthBackTexture;
    }

    public Enemy init(float x, float y, float size, float speed, float power, float health,
                      int cost, TextureRegion texture, TextureRegion healthTexture,
                      TextureRegion healthBackTexture) {
        super.init(new RectangleBound(x, y, size, size), speed, speed, texture);
        this.power = power;
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

        if (AreaType.NONE.equals(areaType)) {
            health = 0;
        } else if (AreaType.UP.equals(areaType)) {
            velocity.y = speed;
            velocity.x = 0;
        } else if (AreaType.DOWN.equals(areaType)) {
            velocity.y = -speed;
            velocity.x = 0;
        } else if (AreaType.LEFT.equals(areaType)) {
            velocity.y = 0;
            velocity.x = -speed;
        } else if (AreaType.RIGHT.equals(areaType)) {
            velocity.y = 0;
            velocity.x = speed;
        } else if (AreaType.END.equals(areaType)) {
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

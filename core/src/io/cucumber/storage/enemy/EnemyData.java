package io.cucumber.storage.enemy;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyData {

    private final float size;
    private final float power;
    private final float health;
    private final float velocity;
    private final int cost;

    private final TextureRegion texture;
    private TextureRegion healthTexture;
    private TextureRegion healthBackTexture;

    public EnemyData(float size, float power, float health, float velocity,
                     int cost, TextureRegion texture, TextureRegion healthTexture,
                     TextureRegion healthBackTexture) {
        this.size = size;
        this.power = power;
        this.health = health;
        this.velocity = velocity;
        this.cost = cost;
        this.texture = texture;
        this.healthTexture = healthTexture;
        this.healthBackTexture = healthBackTexture;
    }

    public float getSize() {
        return size;
    }

    public float getPower() {
        return power;
    }

    public float getHealth() {
        return health;
    }

    public float getVelocity() {
        return velocity;
    }

    public int getCost() {
        return cost;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public TextureRegion getHealthTexture() {
        return healthTexture;
    }

    public TextureRegion getHealthBackTexture() {
        return healthBackTexture;
    }
}

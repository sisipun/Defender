package io.cucumber.utils.storage.enemy;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyData {

    private final float size;
    private final float power;
    private final float health;
    private final float speed;

    private final TextureRegion texture;
    private TextureRegion healthTexture;
    private TextureRegion healthBackTexture;

    public EnemyData(float size, float power, float health, float velocity,
                     TextureRegion texture, TextureRegion healthTexture,
                     TextureRegion healthBackTexture) {
        this.size = size;
        this.power = power;
        this.health = health;
        this.speed = velocity;
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

    public float getSpeed() {
        return speed;
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

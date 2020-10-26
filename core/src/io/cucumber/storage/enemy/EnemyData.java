package io.cucumber.storage.enemy;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyData {

    private final float size;
    private final float power;
    private final float health;
    private final float velocity;
    private final int cost;

    private final TextureRegion texture;

    public EnemyData(TextureAtlas atlas, float size, float power, float health, float velocity,
                     int cost, String texture) {
        this.size = size;
        this.power = power;
        this.health = health;
        this.velocity = velocity;
        this.cost = cost;
        this.texture = atlas.findRegion(texture);
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
}

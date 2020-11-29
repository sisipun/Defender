package io.cucumber.storage.defender;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DefenderData {

    private final float size;
    private final float power;
    private final float zoneSize;
    private final int cost;

    private final TextureRegion availableTexture;
    private final TextureRegion unavailableTexture;

    public DefenderData(float size, float power, int cost, float zoneSize,
                        TextureRegion availableTexture, TextureRegion unavailableTexture) {
        this.size = size;
        this.power = power;
        this.cost = cost;
        this.zoneSize = zoneSize;
        this.availableTexture = availableTexture;
        this.unavailableTexture = unavailableTexture;
    }

    public float getSize() {
        return size;
    }

    public float getPower() {
        return power;
    }

    public int getCost() {
        return cost;
    }

    public float getZoneSize() {
        return zoneSize;
    }

    public TextureRegion getAvailableTexture() {
        return availableTexture;
    }

    public TextureRegion getUnavailableTexture() {
        return unavailableTexture;
    }
}

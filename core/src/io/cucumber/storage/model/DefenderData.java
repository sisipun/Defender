package io.cucumber.storage.model;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DefenderData {

    private final float size;
    private final float power;
    private final float zoneSize;

    private final TextureRegion availableTexture;
    private final TextureRegion unavailableTexture;

    public DefenderData(TextureAtlas atlas, float size, float power, float zoneSize,
                        String availableTexture, String unavailableTexture) {
        this.size = size;
        this.power = power;
        this.zoneSize = zoneSize;
        this.availableTexture = atlas.findRegion(availableTexture);
        this.unavailableTexture = atlas.findRegion(unavailableTexture);
    }

    public float getSize() {
        return size;
    }

    public float getPower() {
        return power;
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

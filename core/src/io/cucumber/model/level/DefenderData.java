package io.cucumber.model.level;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DefenderData {

    private final float size;
    private final float power;
    private final float zoneSize;
    private final TextureRegion availableRegion;
    private final TextureRegion unavailableRegion;

    public DefenderData(TextureAtlas atlas, float size, float power, float zoneSize,
                        String availableRegion, String unavailableRegion) {
        this.size = size;
        this.power = power;
        this.zoneSize = zoneSize;
        this.availableRegion = atlas.findRegion(availableRegion);
        this.unavailableRegion = atlas.findRegion(unavailableRegion);
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

    public TextureRegion getAvailableRegion() {
        return availableRegion;
    }

    public TextureRegion getUnavailableRegion() {
        return unavailableRegion;
    }
}

package io.cucumber.model.level;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DefenderSample {

    private final float size;
    private final float power;
    private final float zoneSize;
    private final float zoneAlpha;
    private final TextureRegion availableRegion;
    private final TextureRegion unavailableRegion;
    private final TextureRegion zoneRegion;

    public DefenderSample(float size, float power, float zoneSize, float zoneAlpha,
                          TextureRegion availableRegion, TextureRegion unavailableRegion,
                          TextureRegion zoneRegion) {
        this.size = size;
        this.power = power;
        this.zoneSize = zoneSize;
        this.zoneAlpha = zoneAlpha;
        this.availableRegion = availableRegion;
        this.unavailableRegion = unavailableRegion;
        this.zoneRegion = zoneRegion;
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

    public float getZoneAlpha() {
        return zoneAlpha;
    }

    public TextureRegion getAvailableRegion() {
        return availableRegion;
    }

    public TextureRegion getUnavailableRegion() {
        return unavailableRegion;
    }

    public TextureRegion getZoneRegion() {
        return zoneRegion;
    }
}

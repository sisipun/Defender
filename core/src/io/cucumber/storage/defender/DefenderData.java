package io.cucumber.storage.defender;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DefenderData {

    private final float size;
    private final int cost;
    private final TextureRegion availableTexture;
    private final TextureRegion unavailableTexture;

    private final float zoneSize;
    private final TextureRegion zoneTexture;

    private final float bulletSize;
    private final float bulletSpeed;
    private final float bulletPower;
    private final TextureRegion bulletTexture;

    public DefenderData(float size, int cost, TextureRegion availableTexture,
                        TextureRegion unavailableTexture, float zoneSize, TextureRegion zoneTexture,
                        float bulletSize, float bulletSpeed, float bulletPower,
                        TextureRegion bulletTexture) {
        this.size = size;
        this.cost = cost;
        this.availableTexture = availableTexture;
        this.unavailableTexture = unavailableTexture;

        this.zoneSize = zoneSize;
        this.zoneTexture = zoneTexture;

        this.bulletSize = bulletSize;
        this.bulletSpeed = bulletSpeed;
        this.bulletPower = bulletPower;
        this.bulletTexture = bulletTexture;
    }

    public float getSize() {
        return size;
    }

    public int getCost() {
        return cost;
    }

    public TextureRegion getAvailableTexture() {
        return availableTexture;
    }

    public TextureRegion getUnavailableTexture() {
        return unavailableTexture;
    }

    public float getZoneSize() {
        return zoneSize;
    }

    public TextureRegion getZoneTexture() {
        return zoneTexture;
    }

    public float getBulletSize() {
        return bulletSize;
    }

    public float getBulletSpeed() {
        return bulletSpeed;
    }

    public float getBulletPower() {
        return bulletPower;
    }

    public TextureRegion getBulletTexture() {
        return bulletTexture;
    }
}

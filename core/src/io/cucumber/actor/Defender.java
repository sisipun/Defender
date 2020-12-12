package io.cucumber.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.actor.base.StaticActor;
import io.cucumber.base.actor.bound.RectangleBound;

public class Defender extends StaticActor<Rectangle> {

    private int cost;
    private boolean highlighted;

    private Zone zone;
    private Bullet bullet;

    public Defender(float x, float y, float size, int cost, TextureRegion texture, float zoneSize,
                    TextureRegion zoneTexture, float bulletSize, float bulletSpeed,
                    float bulletPower, TextureRegion bulletTexture) {
        super(new RectangleBound(x, y, size, size), texture);

        this.cost = cost;
        this.highlighted = false;

        this.zone = new Zone(
                getX() + getWidth() / 2f - zoneSize / 2f,
                getY() + getHeight() / 2f - zoneSize / 2f,
                zoneSize,
                zoneTexture
        );
        this.bullet = new Bullet(
                getX() + getWidth() / 2f - bulletSize / 2f,
                getY() + getHeight() / 2f - bulletSize / 2f,
                bulletSize,
                bulletSpeed,
                bulletPower,
                bulletTexture
        );
    }

    public Defender init(float x, float y, float size, int cost, TextureRegion texture,
                         float zoneSize, TextureRegion zoneTexture, float bulletSize,
                         float bulletSpeed, float bulletPower, TextureRegion bulletTexture) {
        super.init(new RectangleBound(x, y, size, size), texture);

        this.cost = cost;
        this.highlighted = false;

        this.zone.init(
                getX() + getWidth() / 2f - zoneSize / 2f,
                getY() + getHeight() / 2f - zoneSize / 2f,
                zoneSize,
                zoneTexture
        );
        this.bullet.init(
                getX() + getWidth() / 2f - bulletSize / 2f,
                getY() + getHeight() / 2f - bulletSize / 2f,
                bulletSize,
                bulletSpeed,
                bulletPower,
                bulletTexture
        );
        return this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (highlighted) {
            zone.draw(batch, parentAlpha);
        }
        super.draw(batch, parentAlpha);
        if (bullet.isPinned()) {
            bullet.draw(batch, parentAlpha);
        }
    }

    @Override
    public void act(float delta) {
        if (bullet.reachedTarget()) {
            bullet.hitTarget();
            this.bullet.init(
                    getX() + getWidth() / 2f - bullet.getWidth() / 2f,
                    getY() + getHeight() / 2f - bullet.getHeight() / 2f
            );
        }

        zone.act(delta);
        bullet.act(delta);
        super.act(delta);
    }

    public void shoot(Enemy enemy) {
        if (!this.bullet.isPinned()) {
            this.bullet.pinTarget(enemy);
        }
    }

    public boolean isCollidesZone(Enemy enemy) {
        return zone.isCollides(enemy);
    }

    public int getCost() {
        return cost;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public boolean isHighlighted() {
        return highlighted;
    }
}

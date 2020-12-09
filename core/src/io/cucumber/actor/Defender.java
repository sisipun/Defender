package io.cucumber.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.actor.base.StaticActor;
import io.cucumber.base.actor.bound.RectangleBound;

public class Defender extends StaticActor<Rectangle> {

    private float power;
    private Zone zone;
    private int cost;
    private boolean highlighted;

    public Defender(float x, float y, float width, float height, float power, int cost,
                    TextureRegion texture, float zoneSize, TextureRegion zoneTexture) {
        super(new RectangleBound(x, y, width, height), texture);
        this.power = power;
        this.cost = cost;
        this.zone = new Zone(
                getX() + getWidth() / 2f - zoneSize / 2f,
                getY() + getHeight() / 2f - zoneSize / 2f,
                zoneSize,
                zoneTexture
        );
        this.highlighted = false;
    }

    public Defender init(float x, float y, float width, float height, float power, int cost,
                         TextureRegion texture, float zoneSize, TextureRegion zoneTexture) {
        super.init(new RectangleBound(x, y, width, height), texture);
        this.power = power;
        this.cost = cost;
        this.zone = new Zone(
                getX() + getWidth() / 2f - zoneSize / 2f,
                getY() + getHeight() / 2f - zoneSize / 2f,
                zoneSize,
                zoneTexture
        );
        this.highlighted = false;
        return this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (highlighted) {
            zone.draw(batch, parentAlpha);
        }
        super.draw(batch, parentAlpha);
    }

    public boolean isCollidesZone(Enemy enemy) {
        return zone.isCollides(enemy);
    }

    public float getPower() {
        return power;
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

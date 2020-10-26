package io.cucumber.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;

public class Defender extends StaticActor<Rectangle> {

    private float power;
    private Zone zone;

    public Defender(float x, float y, float width, float height, float power, TextureRegion texture,
                    float zoneSize, TextureRegion zoneTexture) {
        super(new RectangleBound(x, y, width, height), texture);
        this.power = power;
        this.zone = new Zone(
                getX() + getWidth() / 2f - zoneSize / 2f,
                getY() + getHeight() / 2f - zoneSize / 2f,
                zoneSize,
                zoneTexture
        );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        zone.draw(batch, parentAlpha);
        super.draw(batch, parentAlpha);
    }

    public boolean isCollidesZone(Enemy enemy) {
        return zone.isCollides(enemy);
    }

    public float getPower() {
        return power;
    }
}

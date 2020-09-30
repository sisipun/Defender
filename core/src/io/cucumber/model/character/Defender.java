package io.cucumber.model.character;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;

public class Defender extends StaticActor<Rectangle> {

    private Zone zone;
    private float power;

    public Defender(float x, float y, float size, TextureRegion region, float power, float zoneSize,
                    float zoneAlpha, TextureRegion zoneRegion) {
        super(new RectangleBound(x, y, size, size), region);
        this.zone = new Zone(this, zoneSize, zoneAlpha, zoneRegion);
        this.power = power;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        zone.draw(batch, parentAlpha);
        super.draw(batch, parentAlpha);
    }

    public boolean isCollidesZone(Enemy enemy) {
        return zone.isCollides(enemy);
    }

    @Override
    public void setX(float x) {
        zone.setX(x);
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        zone.setY(y);
        super.setY(y);
    }

    @Override
    public void setPosition(float x, float y) {
        zone.setPosition(x, y);
        super.setPosition(x, y);
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        zone.setPosition(x, y, alignment);
        super.setPosition(x, y, alignment);
    }

    public float getPower() {
        return power;
    }
}

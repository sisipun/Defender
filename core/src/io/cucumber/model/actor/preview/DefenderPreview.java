package io.cucumber.model.actor.preview;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;

public class DefenderPreview extends StaticActor<Rectangle> {

    private DefenderZonePreview zone;
    private float power;
    private TextureRegion availableRegion;
    private TextureRegion unavailableRegion;
    private boolean available;

    public DefenderPreview(float x, float y, float size, TextureRegion availableRegion,
                           TextureRegion unavailableRegion, float power, float zoneSize,
                           float zoneAlpha, TextureRegion zoneRegion) {
        super(new RectangleBound(x, y, size, size), availableRegion);
        this.power = power;
        this.zone = new DefenderZonePreview(this, zoneSize, zoneAlpha, zoneRegion);
        this.availableRegion = availableRegion;
        this.unavailableRegion = unavailableRegion;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        zone.draw(batch, parentAlpha);
        super.draw(batch, parentAlpha);
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


    public TextureRegion getZoneRegion() {
        return zone.getRegion();
    }

    public float getZoneAlpha() {
        return zone.getAlpha();
    }

    public float getZoneSize() {
        return zone.getSize();
    }

    public float getPower() {
        return power;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
        if (available) {
            setRegion(availableRegion);
        } else {
            setRegion(unavailableRegion);
        }
    }
}

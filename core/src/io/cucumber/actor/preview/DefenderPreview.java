package io.cucumber.actor.preview;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;
import io.cucumber.storage.model.DefenderData;

public class DefenderPreview extends StaticActor<Rectangle> {

    private float power;
    private boolean available;

    private DefenderZonePreview zone;
    private TextureRegion availableTexture;
    private TextureRegion unavailableTexture;

    public DefenderPreview(float x, float y, DefenderData data, TextureRegion zoneTexture) {
        super(new RectangleBound(x, y, data.getSize(), data.getSize()), data.getAvailableTexture());
        this.power = data.getPower();
        this.available = true;
        this.zone = new DefenderZonePreview(this, data.getZoneSize(), zoneTexture);
        this.availableTexture = data.getAvailableTexture();
        this.unavailableTexture = data.getUnavailableTexture();
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

    public TextureRegion getZoneTexture() {
        return zone.getTexture();
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
            setTexture(availableTexture);
        } else {
            setTexture(unavailableTexture);
        }
    }
}

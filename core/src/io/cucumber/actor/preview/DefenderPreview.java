package io.cucumber.actor.preview;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;
import io.cucumber.storage.defender.DefenderData;

public class DefenderPreview extends StaticActor<Rectangle> {

    private float power;
    private int cost;
    private boolean available;

    private TextureRegion availableTexture;
    private TextureRegion unavailableTexture;
    private DefenderZonePreview zone;

    public DefenderPreview(float x, float y, DefenderData data, TextureRegion zoneTexture) {
        super(new RectangleBound(x, y, data.getSize(), data.getSize()), data.getAvailableTexture());
        this.power = data.getPower();
        this.cost = data.getCost();
        this.available = true;
        this.availableTexture = data.getAvailableTexture();
        this.unavailableTexture = data.getUnavailableTexture();
        this.zone = new DefenderZonePreview(
                getX() + getWidth() / 2f - data.getZoneSize() / 2f,
                getY() + getHeight() / 2f - data.getZoneSize() / 2f,
                data.getZoneSize(),
                zoneTexture
        );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        zone.draw(batch, parentAlpha);
        super.draw(batch, parentAlpha);
    }

    @Override
    public void setX(float x) {
        zone.setX(x + getWidth() / 2f - zone.getWidth() / 2f);
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        zone.setY(y + getHeight() / 2f - zone.getHeight() / 2f);
        super.setY(y);
    }

    @Override
    public void setPosition(float x, float y) {
        zone.setPosition(
                x + getWidth() / 2f - zone.getWidth() / 2f,
                y + getHeight() / 2f - zone.getHeight() / 2f
        );
        super.setPosition(x, y);
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        zone.setPosition(
                x + getWidth() / 2f - zone.getWidth() / 2f,
                y + getHeight() / 2f - zone.getHeight() / 2f,
                alignment
        );
        super.setPosition(x, y, alignment);
    }

    public float getPower() {
        return power;
    }

    public int getCost() {
        return cost;
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

    public float getZoneSize() {
        return zone.getSize();
    }

    public TextureRegion getZoneTexture() {
        return zone.getTexture();
    }
}

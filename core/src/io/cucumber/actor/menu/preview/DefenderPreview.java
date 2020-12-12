package io.cucumber.actor.menu.preview;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.actor.base.StaticActor;
import io.cucumber.base.actor.bound.RectangleBound;
import io.cucumber.storage.defender.DefenderData;

public class DefenderPreview extends StaticActor<Rectangle> {

    private boolean available;
    private DefenderData data;

    private DefenderZonePreview zone;

    public DefenderPreview(float x, float y, DefenderData data) {
        super(new RectangleBound(x, y, data.getSize(), data.getSize()), data.getAvailableTexture());
        this.available = true;
        this.data = data;
        this.zone = new DefenderZonePreview(
                getX() + getWidth() / 2f - data.getZoneSize() / 2f,
                getY() + getHeight() / 2f - data.getZoneSize() / 2f,
                data.getZoneSize(),
                data.getZoneTexture()
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

    public DefenderData getData() {
        return data;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
        if (available) {
            setTexture(data.getAvailableTexture());
        } else {
            setTexture(data.getUnavailableTexture());
        }
    }
}

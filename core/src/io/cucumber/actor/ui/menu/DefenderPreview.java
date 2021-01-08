package io.cucumber.actor.ui.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pools;

import io.cucumber.actor.Zone;
import io.cucumber.base.actor.base.StaticActor;
import io.cucumber.base.actor.bound.RectangleBound;
import io.cucumber.utils.storage.defender.DefenderData;

public class DefenderPreview extends StaticActor<Rectangle> {

    private boolean available;
    private DefenderData data;

    private Zone zone;

    public DefenderPreview() {
        super(new RectangleBound(0f, 0f, 0f, 0f), null);
        this.available = false;
        this.zone = null;
        this.data = null;
    }

    public DefenderPreview init(float x, float y, DefenderData data) {
        super.init(new RectangleBound(x, y, data.getSize(), data.getSize()), data.getAvailableTexture());
        this.available = true;
        this.data = data;

        this.zone = Pools.obtain(Zone.class).init(
                getX() + getWidth() / 2f - data.getZoneSize() / 2f,
                getY() + getHeight() / 2f - data.getZoneSize() / 2f,
                data.getZoneSize(),
                data.getZoneTexture()
        );

        return this;
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

    @Override
    public boolean remove() {
        Pools.free(zone);
        return super.remove();
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

package io.cucumber.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.actor.preview.DefenderPreview;
import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;

public class Defender extends StaticActor<Rectangle> {

    private float power;
    private Zone zone;

    public Defender(DefenderPreview preview) {
        super(new RectangleBound(preview.getX(), preview.getY(), preview.getWidth(),
                preview.getHeight()), preview.getTexture());
        this.power = preview.getPower();
        this.zone = new Zone(
                getX() + getWidth() / 2f - preview.getZoneSize() / 2f,
                getY() + getHeight() / 2f - preview.getZoneSize() / 2f,
                preview.getZoneSize(),
                preview.getZoneTexture()
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

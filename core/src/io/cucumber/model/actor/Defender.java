package io.cucumber.model.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;
import io.cucumber.model.actor.preview.DefenderPreview;

public class Defender extends StaticActor<Rectangle> {

    private Zone zone;
    private float power;

    public Defender(DefenderPreview preview) {
        super(new RectangleBound(preview.getX(), preview.getY(), preview.getWidth(),
                preview.getHeight()), preview.getRegion());
        this.zone = new Zone(this, preview.getZoneSize(), preview.getZoneRegion());
        this.power = preview.getPower();
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

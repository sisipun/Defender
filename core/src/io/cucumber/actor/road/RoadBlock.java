package io.cucumber.actor.road;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;
import io.cucumber.actor.Enemy;
import io.cucumber.actor.Zone;

public class RoadBlock extends StaticActor<Rectangle> {

    private RoadType type;
    private Zone zone;

    public RoadBlock(float x, float y, float size, RoadType type, TextureRegion texture,
                     float zoneSize, TextureRegion zoneTexture) {
        super(new RectangleBound(x, y, size, size), texture);
        this.type = type;
        this.zone = new Zone(this, zoneSize, zoneTexture);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public boolean isCollidesZone(Enemy enemy) {
        return zone.isCollides(enemy);
    }

    public RoadType getType() {
        return type;
    }
}

package io.cucumber.model.road;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;
import io.cucumber.model.character.Enemy;
import io.cucumber.model.character.Zone;

public class RoadBlock extends StaticActor<Rectangle> {

    private RoadType roadType;
    private Zone zone;

    public RoadBlock(float x, float y, float size, TextureRegion region, RoadType roadType,
                     float zoneSize, float zoneAlpha, TextureRegion zoneRegion) {
        super(new RectangleBound(x, y, size, size), region);
        this.roadType = roadType;
        this.zone = new Zone(this, zoneSize, zoneAlpha, zoneRegion);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        zone.draw(batch, parentAlpha);
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

    public boolean isCollidesZone(Enemy enemy) {
        return zone.isCollides(enemy);
    }

    public RoadType getType() {
        return roadType;
    }
}

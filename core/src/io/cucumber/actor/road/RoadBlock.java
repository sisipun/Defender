package io.cucumber.actor.road;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.actor.Enemy;
import io.cucumber.actor.Zone;
import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;

public class RoadBlock extends StaticActor<Rectangle> {

    private RoadType type;
    private Zone zone;

    public RoadBlock(float x, float y, float size, RoadType type, RoadType previousType,
                     TextureRegion texture, float zoneSize, TextureRegion zoneTexture) {
        super(new RectangleBound(x, y, size, size), texture);
        this.type = type;
        float zoneX = getX() + getWidth() / 2f - zoneSize / 2f;
        float zoneY = getY() + getWidth() / 2f - zoneSize / 2f;
        if (RoadType.LEFT.equals(previousType)) {
            zoneX = getX() - zoneSize / 2f;
        } else if (RoadType.RIGHT.equals(previousType)) {
            zoneX = getX() + getWidth() - zoneSize / 2f;
        } else if (RoadType.UP.equals(previousType)) {
            zoneY = getY() + getHeight() - zoneSize / 2f;
        } else if (RoadType.DOWN.equals(previousType)) {
            zoneY = getY() - zoneSize / 2f;
        }
        this.zone = new Zone(
                zoneX,
                zoneY,
                zoneSize,
                zoneTexture
        );
    }

    public boolean isCollidesZone(Enemy enemy) {
        return zone.isCollides(enemy);
    }

    public RoadType getType() {
        return type;
    }
}

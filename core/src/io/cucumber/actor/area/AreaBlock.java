package io.cucumber.actor.area;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pools;

import io.cucumber.actor.Enemy;
import io.cucumber.actor.Zone;
import io.cucumber.base.actor.base.StaticActor;
import io.cucumber.base.actor.bound.RectangleBound;

public class AreaBlock extends StaticActor<Rectangle> {

    private AreaType type;
    private Zone zone;

    public AreaBlock() {
        super(new RectangleBound(0, 0, 0, 0), null);
        this.type = null;
        this.zone = null;
    }

    public AreaBlock init(float x, float y, float size, AreaType type, TextureRegion texture,
                          float zoneSize, TextureRegion zoneTexture) {
        super.init(new RectangleBound(x, y, size, size), texture);
        this.type = type;
        this.zone = Pools.obtain(Zone.class).init(
                getX() + getWidth() / 2f - zoneSize / 2f,
                getY() + getWidth() / 2f - zoneSize / 2f,
                zoneSize,
                zoneTexture
        );
        return this;
    }

    @Override
    public boolean remove() {
        Pools.free(zone);
        return super.remove();
    }

    public boolean isCollidesZone(Enemy enemy) {
        return zone.isCollides(enemy);
    }

    public AreaType getType() {
        return type;
    }
}

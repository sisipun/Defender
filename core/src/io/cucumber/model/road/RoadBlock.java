package io.cucumber.model.road;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;
import io.cucumber.model.character.Enemy;

public class RoadBlock extends StaticActor<Rectangle> {

    private RoadType roadType;
    private RectangleBound changeDirectionZone;
    private TextureRegion zoneRegion;

    public RoadBlock(float x, float y, float size, TextureRegion region, float zoneSize,
                     TextureRegion zoneRegion, RoadType roadType) {
        super(new RectangleBound(x, y, size, size), region);
        this.roadType = roadType;
        this.changeDirectionZone = new RectangleBound(
                x + size / 2f - zoneSize / 2f,
                y + size / 2f - zoneSize / 2f,
                zoneSize,
                zoneSize
        );
        this.zoneRegion = zoneRegion;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        // TODO remove after debug
        batch.draw(
                zoneRegion,
                changeDirectionZone.getX(),
                changeDirectionZone.getY(),
                changeDirectionZone.getWidth(),
                changeDirectionZone.getHeight()
        );
    }

    public boolean isCollidesZone(Enemy enemy) {
        return changeDirectionZone.overlaps(enemy.getBound());
    }

    public RoadType getType() {
        return roadType;
    }
}

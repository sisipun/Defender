package io.cucumber.model.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;
import io.cucumber.model.actor.preview.DefenderPreview;
import io.cucumber.model.actor.road.RoadBlock;

public class GameZone extends StaticActor<Rectangle> {

    private Array<RoadBlock> road;

    public GameZone(float x, float y, float width, float height, TextureRegion region,
                    Array<RoadBlock> road) {
        super(new RectangleBound(x, y, width, height), region);
        this.road = new Array<>(road);
    }

    public GameZone init(float x, float y, float width, float height, TextureRegion region,
                         Array<RoadBlock> road) {
        super.init(new RectangleBound(x, y, width, height), region);
        this.road.clear();
        this.road.addAll(road);
        return this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (RoadBlock roadBlock : road) {
            roadBlock.draw(batch, parentAlpha);
        }
    }

    public void changeDirection(Enemy enemy) {
        for (RoadBlock roadBlock : road) {
            if (roadBlock.isCollidesZone(enemy)) {
                enemy.changeDirection(roadBlock.getType());
            }
        }
    }

    public boolean isCollidesRoad(DefenderPreview defender) {
        for (RoadBlock roadBlock : road) {
            if (roadBlock.isCollides(defender)) {
                return true;
            }
        }

        return false;
    }
}

package io.cucumber.actor.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import io.cucumber.actor.road.RoadBlock;
import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;

public class Background extends StaticActor<Rectangle> {

    private Array<io.cucumber.actor.road.RoadBlock> road;

    public Background(float x, float y, float width, float height, TextureRegion texture,
                      Array<io.cucumber.actor.road.RoadBlock> road) {
        super(new RectangleBound(x, y, width, height), texture);
        this.road = new Array<>(road);
    }

    public Background init(float x, float y, float width, float height, TextureRegion texture,
                           Array<io.cucumber.actor.road.RoadBlock> road) {
        super.init(new RectangleBound(x, y, width, height), texture);
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

    public Array<RoadBlock> getRoad() {
        return road;
    }
}

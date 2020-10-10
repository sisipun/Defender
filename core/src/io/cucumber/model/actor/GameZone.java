package io.cucumber.model.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;
import io.cucumber.model.actor.road.RoadBlock;

public class GameZone extends StaticActor<Rectangle> {

    private Array<RoadBlock> road;
    private float health;

    public GameZone(float x, float y, float width, float height, float health, TextureRegion region,
                    Array<RoadBlock> road) {
        super(new RectangleBound(x, y, width, height), region);
        this.health = health;
        this.road = new Array<>(road);
    }

    public GameZone init(float x, float y, float width, float height, float health,
                         TextureRegion region, Array<RoadBlock> road) {
        super.init(new RectangleBound(x, y, width, height), region);
        this.health = health;
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

    public void hit(float power) {
        health -= power;
    }

    public boolean isGameOver() {
        return health <= 0;
    }
}

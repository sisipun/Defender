package io.cucumber.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import io.cucumber.base.actor.base.StaticActor;
import io.cucumber.base.actor.bound.CircleBound;

import static io.cucumber.utils.constants.Constants.ZONE_ALPHA;

public class Zone extends StaticActor<Circle> {

    public Zone() {
        super(new CircleBound(0, 0, 0), null);
    }

    public Zone init(float x, float y, float size, TextureRegion texture) {
        super.init(new CircleBound(x, y, size / 2), texture);
        return this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = batch.getColor();
        float currentAlpha = color.a;
        batch.setColor(color.r, color.g, color.b, ZONE_ALPHA);
        super.draw(batch, parentAlpha);
        batch.setColor(color.r, color.g, color.b, currentAlpha);
    }

    public boolean isCollides(Enemy enemy) {
        return Intersector.overlaps(getBound().getValue(), enemy.getBound().getValue());
    }
}

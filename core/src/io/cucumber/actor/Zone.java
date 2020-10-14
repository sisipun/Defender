package io.cucumber.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import io.cucumber.base.model.base.Actor;
import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.CircleBound;

import static io.cucumber.utils.constants.Constants.ZONE_ALPHA;

public class Zone extends StaticActor<Circle> {

    public Zone(Actor parent, float size, TextureRegion texture) {
        super(new CircleBound(
                parent.getX() + parent.getWidth() / 2f - size / 2f,
                parent.getY() + parent.getHeight() / 2f - size / 2f,
                size / 2
        ), texture);
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

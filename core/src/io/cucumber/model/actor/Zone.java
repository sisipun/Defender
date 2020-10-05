package io.cucumber.model.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import io.cucumber.base.model.base.Actor;
import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.CircleBound;

public class Zone extends StaticActor<Circle> {

    private float alpha;

    public Zone(Actor actor, float size, float alpha, TextureRegion region) {
        super(new CircleBound(
                actor.getX() + actor.getWidth() / 2f - size / 2f,
                actor.getY() + actor.getHeight() / 2f - size / 2f,
                size / 2
        ), region);
        this.alpha = alpha;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = batch.getColor();
        float currentAlpha = color.a;
        batch.setColor(color.r, color.g, color.b, alpha);
        super.draw(batch, parentAlpha);
        batch.setColor(color.r, color.g, color.b, currentAlpha);
    }

    public boolean isCollides(Enemy enemy) {
        return Intersector.overlaps(getBound().getValue(), enemy.getBound().getValue());
    }
}

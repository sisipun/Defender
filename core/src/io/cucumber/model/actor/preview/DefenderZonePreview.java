package io.cucumber.model.actor.preview;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.CircleBound;

public class DefenderZonePreview extends StaticActor<Circle> {

    private DefenderPreview defender;
    private float alpha;
    private float size;

    public DefenderZonePreview(DefenderPreview defender, float size, float alpha, TextureRegion region) {
        super(new CircleBound(
                defender.getX() + defender.getWidth() / 2f - size / 2f,
                defender.getY() + defender.getHeight() / 2f - size / 2f,
                size / 2
        ), region);
        this.defender = defender;
        this.alpha = alpha;
        this.size = size;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = batch.getColor();
        float currentAlpha = color.a;
        batch.setColor(color.r, color.g, color.b, alpha);
        super.draw(batch, parentAlpha);
        batch.setColor(color.r, color.g, color.b, currentAlpha);
    }

    @Override
    public void setX(float x) {
        super.setX(x + defender.getWidth() / 2f - getWidth() / 2f);
    }

    @Override
    public void setY(float y) {
        super.setY(y + defender.getHeight() / 2f - getHeight() / 2f);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(
                x + defender.getWidth() / 2f - getWidth() / 2f,
                y + defender.getHeight() / 2f - getHeight() / 2f
        );
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        super.setPosition(
                x + defender.getWidth() / 2f - getWidth() / 2f,
                y + defender.getHeight() / 2f - getHeight() / 2f,
                alignment
        );
    }

    public float getAlpha() {
        return alpha;
    }

    public float getSize() {
        return size;
    }
}

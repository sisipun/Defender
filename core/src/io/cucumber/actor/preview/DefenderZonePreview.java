package io.cucumber.actor.preview;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.CircleBound;

import static io.cucumber.utils.constants.Constants.ZONE_ALPHA;

public class DefenderZonePreview extends StaticActor<Circle> {

    private float size;

    private DefenderPreview parent;

    public DefenderZonePreview(DefenderPreview parent, float size, TextureRegion texture) {
        super(new CircleBound(
                parent.getX() + parent.getWidth() / 2f - size / 2f,
                parent.getY() + parent.getHeight() / 2f - size / 2f,
                size / 2
        ), texture);
        this.size = size;
        this.parent = parent;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = batch.getColor();
        float currentAlpha = color.a;
        batch.setColor(color.r, color.g, color.b, ZONE_ALPHA);
        super.draw(batch, parentAlpha);
        batch.setColor(color.r, color.g, color.b, currentAlpha);
    }

    @Override
    public void setX(float x) {
        super.setX(x + parent.getWidth() / 2f - getWidth() / 2f);
    }

    @Override
    public void setY(float y) {
        super.setY(y + parent.getHeight() / 2f - getHeight() / 2f);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(
                x + parent.getWidth() / 2f - getWidth() / 2f,
                y + parent.getHeight() / 2f - getHeight() / 2f
        );
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        super.setPosition(
                x + parent.getWidth() / 2f - getWidth() / 2f,
                y + parent.getHeight() / 2f - getHeight() / 2f,
                alignment
        );
    }

    public float getSize() {
        return size;
    }
}

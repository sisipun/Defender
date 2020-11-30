package io.cucumber.actor.menu.preview;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.CircleBound;

import static io.cucumber.utils.constants.Constants.ZONE_ALPHA;

public class DefenderZonePreview extends StaticActor<Circle> {

    private float size;

    public DefenderZonePreview(float x, float y, float size, TextureRegion texture) {
        super(new CircleBound(x, y, size / 2), texture);
        this.size = size;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = batch.getColor();
        float currentAlpha = color.a;
        batch.setColor(color.r, color.g, color.b, ZONE_ALPHA);
        super.draw(batch, parentAlpha);
        batch.setColor(color.r, color.g, color.b, currentAlpha);
    }

    public float getSize() {
        return size;
    }
}

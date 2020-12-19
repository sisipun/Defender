package io.cucumber.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.actor.base.StaticActor;
import io.cucumber.base.actor.bound.RectangleBound;

public class Health extends StaticActor<Rectangle> {

    private float maxValue;
    private float currentValue;
    private TextureRegion texture;

    public Health() {
        super(new RectangleBound(0f, 0f, 0f, 0f), null);
        this.maxValue = 0f;
        this.currentValue = 0f;
        this.texture = null;
    }

    public Health init(float x, float y, float width, float height, TextureRegion texture,
                       TextureRegion backgroundTexture, float value) {
        super.init(new RectangleBound(x, y, width, height), backgroundTexture);
        this.maxValue = value;
        this.currentValue = value;
        this.texture = texture;
        return this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(
                texture.getTexture(),
                getX(),
                getY(),
                getOriginX(),
                getOriginY(),
                (1f * currentValue / maxValue) * getWidth(),
                getHeight(),
                getScaleX(),
                getScaleY(),
                getRotation(),
                texture.getRegionX(),
                texture.getRegionY(),
                texture.getRegionWidth(),
                texture.getRegionHeight(),
                false,
                false
        );
    }

    public void minus(float value) {
        this.currentValue = Math.max(this.currentValue - value, 0f);
    }

    public boolean hasHealth() {
        return this.currentValue > 0f;
    }
}

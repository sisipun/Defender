package io.cucumber.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.actor.base.StaticActor;
import io.cucumber.base.actor.bound.RectangleBound;

public class Health extends StaticActor<Rectangle> {

    private float maxValue;
    private float currentValue;
    private TextureRegion healthTexture;

    public Health(float x, float y, float width, float height, TextureRegion healthTexture,
                  TextureRegion healthBackTexture, float value) {
        super(new RectangleBound(x, y, width, height), healthBackTexture);
        this.healthTexture = healthTexture;
        this.maxValue = value;
        this.currentValue = value;
    }

    public Health init(float x, float y, float width, float height, TextureRegion healthTexture,
                       TextureRegion healthBackTexture, float value) {
        super.init(new RectangleBound(x, y, width, height), healthBackTexture);
        this.healthTexture = healthTexture;
        this.maxValue = value;
        this.currentValue = value;
        return this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(
                healthTexture.getTexture(),
                getX(),
                getY(),
                getOriginX(),
                getOriginY(),
                (1f * currentValue / maxValue) * getWidth(),
                getHeight(),
                getScaleX(),
                getScaleY(),
                getRotation(),
                healthTexture.getRegionX(),
                healthTexture.getRegionY(),
                healthTexture.getRegionWidth(),
                healthTexture.getRegionHeight(),
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

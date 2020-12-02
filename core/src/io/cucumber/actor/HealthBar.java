package io.cucumber.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;

public class HealthBar extends StaticActor<Rectangle> {

    private int amount;
    private TextureRegion healthTexture;

    public HealthBar(float x, float y, float width, float height, TextureRegion healthTexture,
                     TextureRegion healthBackTexture) {
        super(new RectangleBound(x, y, width, height), healthBackTexture);
        this.healthTexture = healthTexture;
        this.amount = 100;
    }

    public HealthBar init(float x, float y, float width, float height, TextureRegion healthTexture,
                          TextureRegion healthBackTexture) {
        super.init(new RectangleBound(x, y, width, height), healthBackTexture);
        this.healthTexture = healthTexture;
        this.amount = 100;
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
                (amount / 100f) * getWidth(),
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

    public void setAmount(int amount) {
        this.amount = Math.min(100, Math.max(amount, 0));
    }
}

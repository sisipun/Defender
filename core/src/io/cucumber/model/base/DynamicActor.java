package io.cucumber.model.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import io.cucumber.model.bound.CircleBound;

public abstract class DynamicActor extends Actor<Circle> {

    protected Vector2 velocity;

    protected byte orientation;

    public DynamicActor(float x, float y, float size, float horizontalVelocity, float verticalVelocity,
                        TextureRegion region, byte orientation) {
        super(new CircleBound(x, y, size / 2), region);
        this.velocity = new Vector2(horizontalVelocity, verticalVelocity);
        this.orientation = orientation;
    }

    public DynamicActor init(float x, float y, float size, float horizontalVelocity, float verticalVelocity,
                             TextureRegion region, byte orientation) {
        super.init(new CircleBound(x, y, size / 2), region);
        this.velocity = new Vector2(horizontalVelocity, verticalVelocity);
        this.orientation = orientation;
        return this;
    }

    @Override
    public void reset() {
        super.reset();
        velocity.x = 0;
        velocity.y = 0;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(
                region.getTexture(),
                getX(),
                getY(),
                getOriginX(),
                getOriginY(),
                getWidth(),
                getHeight(),
                getScaleX(),
                getScaleY(),
                getRotation(),
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                orientation == -1,
                false
        );
    }
}

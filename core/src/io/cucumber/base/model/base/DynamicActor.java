package io.cucumber.base.model.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

import io.cucumber.base.model.bound.Bound2D;

public abstract class DynamicActor<T extends Shape2D> extends Actor<T> {

    protected Vector2 velocity;

    protected byte orientation;

    public DynamicActor(Bound2D<T> bound, float horizontalVelocity, float verticalVelocity,
                        TextureRegion region, byte orientation) {
        super(bound, region);
        this.velocity = new Vector2(horizontalVelocity, verticalVelocity);
        this.orientation = orientation;
    }

    public DynamicActor init(Bound2D<T> bound, float horizontalVelocity, float verticalVelocity,
                             TextureRegion region, byte orientation) {
        super.init(bound, region);
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

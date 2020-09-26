package io.cucumber.base.model.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

import io.cucumber.base.model.bound.Bound2D;

public abstract class DynamicActor<T extends Shape2D> extends Actor<T> {

    protected Vector2 velocity;

    public DynamicActor(Bound2D<T> bound, float horizontalVelocity, float verticalVelocity,
                        TextureRegion region, boolean flipX, boolean flipY) {
        super(bound, region, flipX, flipY);
        this.velocity = new Vector2(horizontalVelocity, verticalVelocity);
    }

    public DynamicActor(Bound2D<T> bound, float horizontalVelocity, float verticalVelocity,
                        TextureRegion region) {
        super(bound, region);
        this.velocity = new Vector2(horizontalVelocity, verticalVelocity);
    }

    public DynamicActor init(Bound2D<T> bound, float horizontalVelocity, float verticalVelocity,
                             TextureRegion region, boolean flipX, boolean flipY) {
        super.init(bound, region, flipX, flipY);
        this.velocity = new Vector2(horizontalVelocity, verticalVelocity);
        return this;
    }

    public DynamicActor init(Bound2D<T> bound, float horizontalVelocity, float verticalVelocity,
                             TextureRegion region) {
        super.init(bound, region);
        this.velocity = new Vector2(horizontalVelocity, verticalVelocity);
        return this;
    }

    @Override
    public void reset() {
        super.reset();
        velocity.x = 0;
        velocity.y = 0;
    }
}

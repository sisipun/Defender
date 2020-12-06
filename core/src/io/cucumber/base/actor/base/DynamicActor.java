package io.cucumber.base.actor.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

import io.cucumber.base.actor.bound.Bound2D;

public abstract class DynamicActor<T extends Shape2D> extends Actor<T> {

    protected Vector2 velocity;

    public DynamicActor(io.cucumber.base.actor.bound.Bound2D<T> bound, float horizontalVelocity, float verticalVelocity,
                        TextureRegion texture, boolean flipX, boolean flipY) {
        super(bound, texture, flipX, flipY);
        this.velocity = new Vector2(horizontalVelocity, verticalVelocity);
    }

    public DynamicActor(io.cucumber.base.actor.bound.Bound2D<T> bound, float horizontalVelocity, float verticalVelocity,
                        TextureRegion texture) {
        super(bound, texture);
        this.velocity = new Vector2(horizontalVelocity, verticalVelocity);
    }

    public DynamicActor<T> init(io.cucumber.base.actor.bound.Bound2D<T> bound, float horizontalVelocity, float verticalVelocity,
                                TextureRegion texture, boolean flipX, boolean flipY) {
        super.init(bound, texture, flipX, flipY);
        this.velocity = new Vector2(horizontalVelocity, verticalVelocity);
        return this;
    }

    public DynamicActor<T> init(Bound2D<T> bound, float horizontalVelocity, float verticalVelocity,
                                TextureRegion texture) {
        super.init(bound, texture);
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

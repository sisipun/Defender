package io.cucumber.base.model.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;

import io.cucumber.base.model.bound.Bound2D;

public abstract class StaticActor<T extends Shape2D> extends Actor<T> {

    public StaticActor(Bound2D<T> bound, TextureRegion region,  boolean flipX, boolean flipY) {
        super(bound, region, flipX, flipY);
    }

    public StaticActor(Bound2D<T> bound, TextureRegion region) {
        super(bound, region);
    }

    public StaticActor<T> init(Bound2D<T> bound, TextureRegion region,  boolean flipX, boolean flipY) {
        super.init(bound, region, flipX, flipY);
        return this;
    }

    public StaticActor<T> init(Bound2D<T> bound, TextureRegion region) {
        super.init(bound, region);
        return this;
    }
}

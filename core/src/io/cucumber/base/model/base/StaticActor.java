package io.cucumber.base.model.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;

import io.cucumber.base.model.bound.Bound2D;

public abstract class StaticActor<T extends Shape2D> extends Actor<T> {

    public StaticActor(Bound2D<T> bound, TextureRegion texture,  boolean flipX, boolean flipY) {
        super(bound, texture, flipX, flipY);
    }

    public StaticActor(Bound2D<T> bound, TextureRegion texture) {
        super(bound, texture);
    }

    public StaticActor<T> init(Bound2D<T> bound, TextureRegion texture,  boolean flipX, boolean flipY) {
        super.init(bound, texture, flipX, flipY);
        return this;
    }

    public StaticActor<T> init(Bound2D<T> bound, TextureRegion texture) {
        super.init(bound, texture);
        return this;
    }
}

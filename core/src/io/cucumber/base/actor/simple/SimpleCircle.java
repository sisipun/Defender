package io.cucumber.base.actor.simple;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

import io.cucumber.base.actor.base.StaticActor;
import io.cucumber.base.actor.bound.CircleBound;

public class SimpleCircle extends StaticActor<Circle> {

    public SimpleCircle(float x, float y, float size, TextureRegion texture) {
        super(new CircleBound(x, y, size / 2), texture);
    }

    public SimpleCircle init(float x, float y, float size, TextureRegion texture) {
        super.init(new CircleBound(x, y, size / 2), texture);
        return this;
    }
}

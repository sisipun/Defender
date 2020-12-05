package io.cucumber.base.model.simple;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;

public class SimpleRectangle extends StaticActor<Rectangle> {

    public SimpleRectangle(float x, float y, float width, float height, TextureRegion texture) {
        super(new RectangleBound(x, y, width, height), texture);
    }

    public SimpleRectangle init(float x, float y, float width, float height,
                                TextureRegion texture) {
        super.init(new RectangleBound(x, y, width, height), texture);
        return this;
    }
}

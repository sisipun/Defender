package io.cucumber.base.actor.simple;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.actor.base.AnimationActor;
import io.cucumber.base.actor.bound.RectangleBound;

public class AnimatedRectangle extends AnimationActor<Rectangle> {

    public AnimatedRectangle(float x, float y, float size, Animation<TextureRegion> animation) {
        super(new io.cucumber.base.actor.bound.RectangleBound(x, y, size, size), 0f, 0f, animation);
    }

    public AnimatedRectangle init(float x, float y, float size, Animation<TextureRegion> animation) {
        super.init(new RectangleBound(x, y, size, size), 0f, 0f, animation);
        return this;
    }
}
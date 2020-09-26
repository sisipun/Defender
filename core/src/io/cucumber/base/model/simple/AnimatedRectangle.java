package io.cucumber.base.model.simple;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.model.base.AnimationActor;
import io.cucumber.base.model.bound.RectangleBound;

public class AnimatedRectangle extends AnimationActor<Rectangle> {

    public AnimatedRectangle(float x, float y, float size, Animation<TextureRegion> animation) {
        super(new RectangleBound(x, y, size, size), 0f, 0f, animation);
    }
}
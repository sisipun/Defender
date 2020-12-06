package io.cucumber.base.actor.simple;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

import io.cucumber.base.actor.base.AnimationActor;
import io.cucumber.base.actor.bound.CircleBound;

public class AnimatedCircle extends AnimationActor<Circle> {

    public AnimatedCircle(float x, float y, float size, Animation<TextureRegion> animation) {
        super(new io.cucumber.base.actor.bound.CircleBound(x, y, size / 2), 0f, 0f, animation);
    }

    public AnimatedCircle init(float x, float y, float size, Animation<TextureRegion> animation) {
        this.init(new CircleBound(x, y, size / 2), 0f, 0f, animation);
        return this;
    }
}
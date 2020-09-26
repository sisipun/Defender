package io.cucumber.base.model.simple;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

import io.cucumber.base.model.base.AnimationActor;
import io.cucumber.base.model.bound.CircleBound;

public class AnimatedCircle extends AnimationActor<Circle> {

    public AnimatedCircle(float x, float y, float size, Animation<TextureRegion> animation) {
        super(new CircleBound(x, y, size / 2), 0f, 0f, animation);
    }
}
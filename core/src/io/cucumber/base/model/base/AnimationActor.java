package io.cucumber.base.model.base;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;

import io.cucumber.base.model.bound.Bound2D;

import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP;

public abstract class AnimationActor<T extends Shape2D> extends DynamicActor<T> {

    private float stateTime;

    private Animation<TextureRegion> animation;

    public AnimationActor(Bound2D<T> bound, float horizontalVelocity,
                          float verticalVelocity, Animation<TextureRegion> animation, boolean flipX, boolean flipY) {
        super(bound, horizontalVelocity, verticalVelocity, animation.getKeyFrame(0), flipX, flipY);
        this.stateTime = 0f;
        this.animation = animation;
    }

    public AnimationActor(Bound2D<T> bound, float horizontalVelocity,
                          float verticalVelocity, Animation<TextureRegion> animation) {
        super(bound, horizontalVelocity, verticalVelocity, animation.getKeyFrame(0));
        this.animation = animation;
        this.stateTime = 0f;
    }

    public AnimationActor<T> init(Bound2D<T> bound, float horizontalVelocity,
                     float verticalVelocity, Animation<TextureRegion> animation, boolean flipX, boolean flipY) {
        super.init(bound, horizontalVelocity, verticalVelocity, animation.getKeyFrame(0),
                flipX, flipY);
        this.animation = animation;
        this.stateTime = 0f;
        return this;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        texture = animation.getKeyFrame(stateTime);
        if (stateTime > animation.getAnimationDuration() && animation.getPlayMode().equals(LOOP)) {
            stateTime -= animation.getAnimationDuration();
        }
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.stateTime = 0;
        this.animation = animation;
    }

    public boolean isAnimationFinished() {
        return this.animation.isAnimationFinished(this.stateTime);
    }
}

package io.cucumber.model.base;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP;

public class AnimationActor extends DynamicActor {

    private Animation<TextureRegion> animation;
    private float stateTime;

    public AnimationActor(float x, float y, float size, float horizontalVelocity,
                          float verticalVelocity, Animation<TextureRegion> animation, byte orientation) {
        super(x, y, size, horizontalVelocity, verticalVelocity, animation.getKeyFrame(0), orientation);
        this.animation = animation;
        this.stateTime = 0f;
    }

    public void init(float x, float y, float size, float horizontalVelocity,
                     float verticalVelocity, Animation<TextureRegion> animation, byte orientation) {
        super.init(x, y, size, horizontalVelocity, verticalVelocity, animation.getKeyFrame(0),
                orientation);
        this.animation = animation;
        this.stateTime = 0f;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        region = animation.getKeyFrame(stateTime);
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

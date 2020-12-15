package io.cucumber.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.actor.base.DynamicActor;
import io.cucumber.base.actor.bound.RectangleBound;

public class Bullet extends DynamicActor<Rectangle> {

    private float power;
    private float speed;

    private Enemy target;

    public Bullet() {
        super(new RectangleBound(0, 0, 0, 0), 0f, 0f, null);
        this.power = 0;
        this.speed = 0;
    }

    public Bullet init(float x, float y, float size, float speed, float power, TextureRegion texture) {
        super.init(new RectangleBound(x, y, size, size), 0f, 0f, texture);
        this.power = power;
        this.speed = speed;
        return this;
    }

    public Bullet init(float x, float y) {
        super.init(new RectangleBound(x, y, getWidth(), getHeight()), 0f, 0f, texture);
        return this;
    }

    @Override
    public void act(float delta) {
        if (target != null) {
            velocity.x = (target.getX() - getX() + target.getWidth() / 2 - getWidth() / 2) * speed;
            velocity.y = (target.getY() - getY() + target.getHeight() / 2 - getHeight() / 2) * speed;
        }
        super.act(delta);
    }

    public void pinTarget(Enemy target) {
        this.target = target;
    }

    public boolean isPinned() {
        return target != null;
    }

    public boolean reachedTarget() {
        return target != null && isCollides(target);
    }

    public void hitTarget() {
        target.hit(power);
        target = null;
    }

    public float getPower() {
        return power;
    }
}

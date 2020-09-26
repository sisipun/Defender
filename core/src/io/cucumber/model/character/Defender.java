package io.cucumber.model.character;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.CircleBound;
import io.cucumber.base.model.bound.RectangleBound;

public class Defender extends StaticActor<Rectangle> {

    private CircleBound zoneCollider;
    private int power;

    public Defender(float x, float y, float size, float colliderSize, TextureRegion region, int power) {
        super(new RectangleBound(x, y, size, size), region);
        this.zoneCollider = new CircleBound(x, y, colliderSize);
        this.power = power;
    }

    public boolean isCollidesZone(Enemy enemy) {
        return zoneCollider.overlaps(enemy.getBound());
    }

    @Override
    public void setX(float x) {
        zoneCollider.setX(x);
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        zoneCollider.setY(y);
        super.setY(y);
    }

    @Override
    public void setPosition(float x, float y) {
        zoneCollider.setX(x);
        zoneCollider.setY(y);
        super.setPosition(x, y);
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        zoneCollider.setX(x);
        zoneCollider.setY(y);
        super.setPosition(x, y, alignment);
    }

    public int getPower() {
        return power;
    }
}

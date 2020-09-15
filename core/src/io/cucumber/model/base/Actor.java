package io.cucumber.model.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.Pool;

import io.cucumber.model.bound.Bound2D;

public abstract class Actor<T extends Shape2D> extends com.badlogic.gdx.scenes.scene2d.Actor implements Pool.Poolable {

    protected TextureRegion region;

    private Bound2D<T> bound;

    public Actor(Bound2D<T> bound, TextureRegion region) {
        init(bound, region);
    }

    public Actor<T> init(Bound2D<T> bound, TextureRegion region) {
        this.bound = bound;
        this.region = region;
        setBounds(bound.getAlignX(), bound.getAlignY(), bound.getWidth(), bound.getHeight());
        setOrigin(getWidth()/ 2, getHeight() / 2);
        return this;
    }

    public boolean isCollides(Actor<T> actor) {
        return bound.overlaps(actor.bound);
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
    }

    @Override
    public void setX(float x) {
        bound.setX(x);
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        bound.setY(y);
        super.setY(y);
    }

    @Override
    public void setWidth(float width) {
        bound.setWidth(width);
        super.setWidth(width);
    }

    @Override
    public void setHeight(float height) {
        bound.setHeight(height);
        super.setHeight(height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(
                region,
                getX(),
                getY(),
                getOriginX(),
                getOriginY(),
                getWidth(),
                getHeight(),
                1F,
                1F,
                getRotation()
        );
    }

    @Override
    public void reset() {
        clearActions();
        setRotation(0);
        setX(0);
        setY(0);
        setWidth(0);
        setHeight(0);
        setOrigin(0, 0);
        region = null;
    }
}

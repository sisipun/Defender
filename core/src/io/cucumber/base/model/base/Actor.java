package io.cucumber.base.model.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.Pool;

import io.cucumber.base.model.bound.Bound2D;

public abstract class Actor<T extends Shape2D> extends com.badlogic.gdx.scenes.scene2d.Actor implements Pool.Poolable {

    private boolean flipX;
    private boolean flipY;

    protected TextureRegion texture;
    private Bound2D<T> bound;

    public Actor(Bound2D<T> bound, TextureRegion texture, boolean flipX, boolean flipY) {
        init(bound, texture, flipX, flipY);
    }

    public Actor(Bound2D<T> bound, TextureRegion texture) {
        init(bound, texture);
    }

    public Actor<T> init(Bound2D<T> bound, TextureRegion texture, boolean flipX, boolean flipY) {
        this.flipX = flipX;
        this.flipY = flipY;
        this.bound = bound;
        this.texture = texture;
        setBounds(bound.getX(), bound.getY(), bound.getWidth(), bound.getHeight());
        setOrigin(getWidth()/ 2, getHeight() / 2);
        return this;
    }

    public Actor<T> init(Bound2D<T> bound, TextureRegion texture) {
        return init(bound, texture, false, false);
    }

    public boolean isCollides(Actor<T> actor) {
        return bound.overlaps(actor.bound);
    }

    public boolean isContains(float x, float y) {
        return bound.contains(x, y);
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    public Bound2D<T> getBound() {
        return bound;
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
    public void setPosition(float x, float y) {
        bound.setX(x);
        bound.setY(y);
        super.setPosition(x, y);
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        bound.setX(x);
        bound.setY(y);
        super.setPosition(x, y, alignment);
    }

    @Override
    public void setWidth(float width) {
        bound.setWidth(width);
        super.setWidth(bound.getWidth());
    }

    @Override
    public void setHeight(float height) {
        bound.setHeight(height);
        super.setHeight(bound.getHeight() );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(
                texture.getTexture(),
                getX(),
                getY(),
                getOriginX(),
                getOriginY(),
                getWidth(),
                getHeight(),
                getScaleX(),
                getScaleY(),
                getRotation(),
                texture.getRegionX(),
                texture.getRegionY(),
                texture.getRegionWidth(),
                texture.getRegionHeight(),
                flipX,
                flipY
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
        texture = null;
    }
}

package io.cucumber.base.model.bound;

import com.badlogic.gdx.math.Shape2D;

import io.cucumber.base.helper.AlignHelper;

public abstract class Bound2D<T extends Shape2D> {

    protected final T bound;

    protected final HorizontalAlign horizontalAlign;

    protected final io.cucumber.base.model.bound.VerticalAlign verticalAlign;

    public Bound2D(T bound, HorizontalAlign horizontalAlign, io.cucumber.base.model.bound.VerticalAlign verticalAlign) {
        this.bound = bound;
        this.horizontalAlign = horizontalAlign;
        this.verticalAlign = verticalAlign;
    }

    public Bound2D(T bound) {
        this.bound = bound;
        this.horizontalAlign = HorizontalAlign.CENTER;
        this.verticalAlign = io.cucumber.base.model.bound.VerticalAlign.CENTER;
    }

    public Bound2D(T bound, HorizontalAlign align) {
        this.bound = bound;
        this.horizontalAlign = align;
        this.verticalAlign = io.cucumber.base.model.bound.VerticalAlign.CENTER;
    }


    public Bound2D(T bound, VerticalAlign align) {
        this.bound = bound;
        this.horizontalAlign = HorizontalAlign.CENTER;
        this.verticalAlign = align;
    }

    public float getAlignX() {
        return AlignHelper.computeX(getX(), getWidth(), horizontalAlign);
    }

    public float getAlignY() {
        return AlignHelper.computeY(getY(), getHeight(), verticalAlign);
    }

    protected abstract float getX();

    protected abstract float getY();

    public abstract float getWidth();

    public abstract float getHeight();

    public abstract void setX(float x);

    public abstract void setY(float y);

    public abstract void setWidth(float width);

    public abstract void setHeight(float height);

    public abstract boolean overlaps(Bound2D<T> otherBound);

    protected abstract T getBound();

    public abstract boolean contains(float x, float y);
}

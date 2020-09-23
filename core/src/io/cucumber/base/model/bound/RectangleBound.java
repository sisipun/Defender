package io.cucumber.base.model.bound;

import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.helper.AlignHelper;

public class RectangleBound extends Bound2D<Rectangle> {

    public RectangleBound(float x, float y, float width, float height) {
        super(new Rectangle(AlignHelper.computeX(x, width, HorizontalAlign.CENTER),
                AlignHelper.computeY(y, height, VerticalAlign.CENTER), width, height), HorizontalAlign.CENTER, VerticalAlign.CENTER);
    }

    public RectangleBound(float x, float y, float width, float height, HorizontalAlign align) {
        super(new Rectangle(AlignHelper.computeX(x, width, align),
                AlignHelper.computeY(y, height, VerticalAlign.CENTER), width, height), align, VerticalAlign.CENTER);
    }

    public RectangleBound(float x, float y, float width, float height, VerticalAlign align) {
        super(new Rectangle(AlignHelper.computeX(x, width, HorizontalAlign.CENTER),
                AlignHelper.computeY(y, height, align), width, height), HorizontalAlign.CENTER, align);
    }

    public RectangleBound(float x, float y, float width, float height,
                          HorizontalAlign horizontalAlign, VerticalAlign verticalAlign) {
        super(new Rectangle(AlignHelper.computeX(x, width, horizontalAlign),
                AlignHelper.computeY(y, height, verticalAlign), width, height), horizontalAlign, verticalAlign);
    }

    @Override
    public float getX() {
        return bound.x;
    }

    @Override
    public float getY() {
        return bound.y;
    }

    @Override
    public float getWidth() {
        return bound.width;
    }

    @Override
    public float getHeight() {
        return bound.height;
    }

    @Override
    public void setX(float x) {
        bound.x = AlignHelper.computeX(x, bound.width, horizontalAlign);
    }

    @Override
    public void setY(float y) {
        bound.y = AlignHelper.computeY(y, bound.height, verticalAlign);
    }

    @Override
    public void setWidth(float width) {
        bound.width = width;
    }

    @Override
    public void setHeight(float height) {
        bound.height = height;
    }

    @Override
    public boolean overlaps(Bound2D<Rectangle> otherBound) {
        return bound.overlaps(otherBound.getBound());
    }

    @Override
    protected Rectangle getBound() {
        return bound;
    }

    @Override
    public boolean contains(float x, float y) {
        return bound.contains(x, y);
    }
}

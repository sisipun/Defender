package io.cucumber.base.model.bound;

import com.badlogic.gdx.math.Rectangle;

public class RectangleBound extends io.cucumber.base.model.bound.Bound2D<Rectangle> {

    public RectangleBound(float x, float y, float width, float height) {
        super(new Rectangle(x, y, width, height));
    }

    public RectangleBound(float x, float y, float width, float height, HorizontalAlign align) {
        super(new Rectangle(x, y, width, height), align);
    }

    public RectangleBound(float x, float y, float width, float height, VerticalAlign align) {
        super(new Rectangle(x, y, width, height), align);
    }

    public RectangleBound(float x, float y, float width, float height, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign) {
        super(new Rectangle(x, y, width, height), horizontalAlign, verticalAlign);
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
        bound.x = x;
    }

    @Override
    public void setY(float y) {
        bound.y = y;
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

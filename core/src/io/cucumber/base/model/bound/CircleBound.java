package io.cucumber.base.model.bound;

import com.badlogic.gdx.math.Circle;

import io.cucumber.base.helper.AlignHelper;

public class CircleBound extends Bound2D<Circle> {

    public CircleBound(float x, float y, float radius) {
        super(new Circle(AlignHelper.computeX(x, radius, HorizontalAlign.CENTER),
                AlignHelper.computeY(y, radius, VerticalAlign.CENTER), radius), HorizontalAlign.CENTER, VerticalAlign.CENTER);
    }

    public CircleBound(float x, float y, float radius, HorizontalAlign align) {
        super(new Circle(AlignHelper.computeX(x, radius, align),
                AlignHelper.computeY(y, radius, VerticalAlign.CENTER), radius), align, VerticalAlign.CENTER);
    }

    public CircleBound(float x, float y, float radius, VerticalAlign align) {
        super(new Circle(AlignHelper.computeX(x, radius, HorizontalAlign.CENTER),
                AlignHelper.computeY(y, radius, align), radius), HorizontalAlign.CENTER, align);
    }

    public CircleBound(float x, float y, float radius, HorizontalAlign horizontalAlign,
                       VerticalAlign verticalAlign) {
        super(new Circle(AlignHelper.computeX(x, radius, horizontalAlign),
                AlignHelper.computeY(y, radius, verticalAlign), radius), horizontalAlign, verticalAlign);
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
        return 2 * bound.radius;
    }

    @Override
    public float getHeight() {
        return 2 * bound.radius;
    }

    @Override
    public void setX(float x) {
        bound.x = AlignHelper.computeX(x, bound.radius, horizontalAlign);
    }

    @Override
    public void setY(float y) {
        bound.y = AlignHelper.computeY(y, bound.radius, verticalAlign);
    }

    @Override
    public void setWidth(float width) {
        bound.radius = width / 2;
    }

    @Override
    public void setHeight(float height) {
        bound.radius = height / 2;
    }

    @Override
    public boolean overlaps(Bound2D<Circle> otherBound) {
        return bound.overlaps(otherBound.getBound());
    }

    @Override
    protected Circle getBound() {
        return bound;
    }

    @Override
    public boolean contains(float x, float y) {
        return bound.contains(x, y);
    }
}

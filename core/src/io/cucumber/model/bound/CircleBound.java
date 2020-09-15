package io.cucumber.model.bound;

import com.badlogic.gdx.math.Circle;

public class CircleBound extends Bound2D<Circle> {

    public CircleBound(float x, float y, float radius) {
        super(new Circle(x, y, radius));
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
        bound.x = x;
    }

    @Override
    public void setY(float y) {
        bound.y = y;
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
}

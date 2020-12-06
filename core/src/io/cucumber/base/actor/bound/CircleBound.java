package io.cucumber.base.actor.bound;

import com.badlogic.gdx.math.Circle;

public class CircleBound extends io.cucumber.base.actor.bound.Bound2D<Circle> {

    public CircleBound(float x, float y, float radius) {
        super(new Circle(x + radius, y + radius, radius));
    }

    @Override
    public float getX() {
        return bound.x - bound.radius;
    }

    @Override
    public float getY() {
        return bound.y - bound.radius;
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
        bound.x = x + bound.radius;
    }

    @Override
    public void setY(float y) {
        bound.y = y + bound.radius;
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
        return bound.overlaps(otherBound.getValue());
    }

    @Override
    public Circle getValue() {
        return bound;
    }

    @Override
    public boolean contains(float x, float y) {
        return bound.contains(x, y);
    }
}

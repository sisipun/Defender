package io.cucumber.utils.helper;

import io.cucumber.model.bound.HorizontalAlign;
import io.cucumber.model.bound.VerticalAlign;

public class AlignHelper {

    public static float computeX(float x, float width, HorizontalAlign align) {
        switch (align) {
            case RIGHT:
                return x - width;
            case CENTER:
                return x - (width / 2);
            default:
                return x;
        }
    }

    public static float computeY(float y, float height, VerticalAlign align) {
        switch (align) {
            case BOTTOM:
                return y - height;
            case CENTER:
                return y - (height / 2);
            default:
                return y;
        }
    }

    private AlignHelper() {}
}

package io.cucumber.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.model.base.Actor;
import io.cucumber.base.model.bound.RectangleBound;

public class MenuItem extends Actor<Rectangle> {
    public MenuItem(RectangleBound bound, TextureRegion region) {
        super(bound, region);
    }
}

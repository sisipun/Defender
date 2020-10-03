package io.cucumber.model.character;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;

public class GameZone extends StaticActor<Rectangle> {

    public GameZone(float x, float y, float width, float height, TextureRegion region) {
        super(new RectangleBound(x, y, width, height), region);
    }

    public GameZone init(float x, float y, float width, float height, TextureRegion region) {
        super.init(new RectangleBound(x, y, width, height), region);
        return this;
    }
}

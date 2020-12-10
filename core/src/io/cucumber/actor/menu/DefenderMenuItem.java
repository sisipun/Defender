package io.cucumber.actor.menu;

import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.actor.base.StaticActor;
import io.cucumber.base.actor.bound.RectangleBound;
import io.cucumber.storage.defender.DefenderData;

public class DefenderMenuItem extends StaticActor<Rectangle> {

    private DefenderData value;

    public DefenderMenuItem(float x, float y, float width, float height, DefenderData value) {
        super(new RectangleBound(x, y, width, height), value.getAvailableTexture());
        this.value = value;
    }

    public DefenderMenuItem init(float x, float y, float width, float height, DefenderData value) {
        super.init(new RectangleBound(x, y, width, height), value.getAvailableTexture());
        this.value = value;
        return this;
    }

    public DefenderData getValue() {
        return value;
    }
}

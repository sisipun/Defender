package io.cucumber.actor.ui.menu;

import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.actor.base.StaticActor;
import io.cucumber.base.actor.bound.RectangleBound;
import io.cucumber.utils.storage.defender.DefenderData;

public class DefenderMenuItem extends StaticActor<Rectangle> {

    private DefenderData value;

    public DefenderMenuItem() {
        super(new RectangleBound(0f, 0f, 0f, 0f), null);
        this.value = null;
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

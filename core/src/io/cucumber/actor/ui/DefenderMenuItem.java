package io.cucumber.actor.ui;

import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;
import io.cucumber.storage.model.DefenderData;

public class DefenderMenuItem extends StaticActor<Rectangle> {

    private final DefenderData value;

    public DefenderMenuItem(RectangleBound bound, DefenderData value) {
        super(bound, value.getAvailableTexture());
        this.value = value;
    }

    public DefenderData getValue() {
        return value;
    }
}

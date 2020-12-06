package io.cucumber.actor.menu;

import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.actor.base.StaticActor;
import io.cucumber.base.actor.bound.RectangleBound;
import io.cucumber.storage.defender.DefenderData;

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
package io.cucumber.actor.ui;

import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;
import io.cucumber.storage.model.DefenderData;

public class MenuItem extends StaticActor<Rectangle> {

    private final DefenderData defenderData;

    public MenuItem(RectangleBound bound, DefenderData defenderData) {
        super(bound, defenderData.getAvailableRegion());
        this.defenderData = defenderData;
    }

    public DefenderData getDefenderData() {
        return defenderData;
    }
}

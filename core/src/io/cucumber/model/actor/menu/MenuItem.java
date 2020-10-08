package io.cucumber.model.actor.menu;

import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;
import io.cucumber.model.level.DefenderSample;

public class MenuItem extends StaticActor<Rectangle> {

    private final DefenderSample defenderSample;

    public MenuItem(RectangleBound bound, DefenderSample defenderSample) {
        super(bound, defenderSample.getAvailableRegion());
        this.defenderSample = defenderSample;
    }

    public DefenderSample getDefenderSample() {
        return defenderSample;
    }
}

package io.cucumber.manager;

import com.badlogic.gdx.utils.Array;

import java.util.Map;

import io.cucumber.manager.event.TimeEvent;
import io.cucumber.storage.defender.DefenderData;

public class Level {

    private final float health;
    private final int length;
    private final int initialBalance;

    private final Assets assets;
    private final Array<DefenderData> defenderTypes;
    private final Map<Integer, TimeEvent> timeEvents;

    public Level(float health, int length, int initialBalance, Assets assets,
                 Array<DefenderData> defenderTypes, Map<Integer, TimeEvent> timeEvents) {
        this.health = health;
        this.length = length;
        this.initialBalance = initialBalance;
        this.assets = assets;
        this.defenderTypes = defenderTypes;
        this.timeEvents = timeEvents;
    }

    public float getHealth() {
        return health;
    }

    public int getLength() {
        return length;
    }

    public int getInitialBalance() {
        return initialBalance;
    }

    public Assets getAssets() {
        return assets;
    }

    public Array<DefenderData> getDefenderTypes() {
        return defenderTypes;
    }

    public TimeEvent getEvent(int time) {
        return timeEvents.get(time);
    }
}

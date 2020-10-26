package io.cucumber.manager;

import com.badlogic.gdx.utils.Array;

import java.util.Map;

import io.cucumber.storage.defender.DefenderData;
import io.cucumber.manager.event.Event;

public class Level {

    private final int length;
    private final int initialBalance;

    private final CommonAssets assets;
    private final Array<DefenderData> defenderTypes;
    private final Map<Integer, Event> timeEvents;

    public Level(int length, int initialBalance, CommonAssets assets, Array<DefenderData> defenderTypes,
                 Map<Integer, Event> timeEvents) {
        this.length = length;
        this.initialBalance = initialBalance;
        this.assets = assets;
        this.defenderTypes = defenderTypes;
        this.timeEvents = timeEvents;
    }

    public int getLength() {
        return length;
    }

    public int getInitialBalance() {
        return initialBalance;
    }

    public CommonAssets getAssets() {
        return assets;
    }

    public Array<DefenderData> getDefenderTypes() {
        return defenderTypes;
    }

    public Event getEvent(int time) {
        return timeEvents.get(time);
    }
}

package io.cucumber.storage.model;

import com.badlogic.gdx.utils.Array;

import java.util.Map;

import io.cucumber.storage.event.Event;

public class Level {

    private final int length;
    private final CommonAssets assets;
    private final Array<DefenderData> defenderTypes;
    private final Map<Integer, Event> timeEvents;

    public Level(int length, CommonAssets assets, Array<DefenderData> defenderTypes,
                 Map<Integer, Event> timeEvents) {
        this.length = length;
        this.assets = assets;
        this.defenderTypes = defenderTypes;
        this.timeEvents = timeEvents;
    }

    public int getLength() {
        return length;
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

package io.cucumber.utils.generator;

import com.badlogic.gdx.utils.Array;

import io.cucumber.utils.event.TimeEvent;
import io.cucumber.utils.storage.defender.DefenderData;

public class Level {

    private final float health;
    private final int timeInSeconds;
    private final int initialBalance;

    private final Array<DefenderData> defenderTypes;
    private final Array<TimeEvent> timeEvents;
    private final LevelMap map;

    public Level(float health, int timeInSeconds, int initialBalance,
                 Array<DefenderData> defenderTypes, Array<TimeEvent> timeEvents, LevelMap map) {
        this.health = health;
        this.timeInSeconds = timeInSeconds;
        this.initialBalance = initialBalance;
        this.defenderTypes = defenderTypes;
        this.timeEvents = timeEvents;
        this.map = map;
    }

    public void remove() {
        map.remove();
    }

    public float getHealth() {
        return health;
    }

    public int getTimeInSeconds() {
        return timeInSeconds;
    }

    public int getInitialBalance() {
        return initialBalance;
    }

    public Array<DefenderData> getDefenderTypes() {
        return defenderTypes;
    }

    public Array<TimeEvent> getEvents() {
        return timeEvents;
    }

    public LevelMap getMap() {
        return map;
    }
}

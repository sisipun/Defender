package io.cucumber.manager;

import com.badlogic.gdx.utils.Array;

import io.cucumber.manager.event.TimeEvent;
import io.cucumber.storage.defender.DefenderData;

public class Level {

    private final float health;
    private final int timeInSeconds;
    private final int horizontalBlockCount;
    private final int initialBalance;

    private final Assets assets;
    private final Array<DefenderData> defenderTypes;
    private final Array<TimeEvent> timeEvents;

    public Level(float health, int timeInSeconds, int horizontalBlockCount, int initialBalance,
                 Assets assets, Array<DefenderData> defenderTypes,
                 Array<TimeEvent> timeEvents) {
        this.health = health;
        this.timeInSeconds = timeInSeconds;
        this.horizontalBlockCount = horizontalBlockCount;
        this.initialBalance = initialBalance;
        this.assets = assets;
        this.defenderTypes = defenderTypes;
        this.timeEvents = timeEvents;
    }

    public float getHealth() {
        return health;
    }

    public int getTimeInSeconds() {
        return timeInSeconds;
    }

    public int getHorizontalBlockCount() {
        return horizontalBlockCount;
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

    public Array<TimeEvent> getEvents() {
        return timeEvents;
    }
}

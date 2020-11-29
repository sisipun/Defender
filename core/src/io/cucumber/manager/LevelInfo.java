package io.cucumber.manager;

import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

import io.cucumber.manager.event.TimeEvent;
import io.cucumber.storage.defender.DefenderType;
import io.cucumber.storage.enemy.EnemyType;

public class LevelInfo {

    private final float health;
    private final int length;
    private final int initialBalance;

    private final AssetsInfo assets;
    private final Array<DefenderType> defenderTypes;
    private final Array<EnemyType> enemyTypes;
    private final Map<Integer, TimeEvent> timeEvents;

    public LevelInfo(float health, int length, int initialBalance, AssetsInfo assets,
                     Array<DefenderType> defenderTypes, Array<EnemyType> enemyTypes,
                     Array<? extends TimeEvent> timeEvents) {
        this.health = health;
        this.length = length;
        this.initialBalance = initialBalance;
        this.assets = assets;
        this.defenderTypes = defenderTypes;
        this.enemyTypes = enemyTypes;
        this.timeEvents = new HashMap<>();
        for (int i = 0; i < timeEvents.size; i++) {
            this.timeEvents.put(timeEvents.get(i).getTime(), timeEvents.get(i));
        }
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

    public AssetsInfo getAssets() {
        return assets;
    }

    public Array<DefenderType> getDefenderTypes() {
        return defenderTypes;
    }

    public Array<EnemyType> getEnemyTypes() {
        return enemyTypes;
    }

    public Map<Integer, TimeEvent> getTimeEvents() {
        return timeEvents;
    }
}

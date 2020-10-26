package io.cucumber.manager.event;

import io.cucumber.storage.enemy.EnemyData;

public class GenerateEnemyEvent extends Event {

    private final EnemyData data;

    public GenerateEnemyEvent(EnemyData data) {
        super(EventType.GENERATE_ENEMY);
        this.data = data;
    }

    public EnemyData getData() {
        return data;
    }
}

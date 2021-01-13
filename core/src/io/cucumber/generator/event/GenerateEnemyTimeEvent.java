package io.cucumber.generator.event;

import io.cucumber.storage.enemy.EnemyData;

public class GenerateEnemyTimeEvent extends TimeEvent {

    private final EnemyData data;

    public GenerateEnemyTimeEvent(int time, EnemyData data) {
        super(time, TimeEventType.GENERATE_ENEMY);
        this.data = data;
    }

    public EnemyData getData() {
        return data;
    }
}

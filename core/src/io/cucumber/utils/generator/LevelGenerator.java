package io.cucumber.utils.generator;

import com.badlogic.gdx.utils.Array;

import io.cucumber.utils.random.Random;
import io.cucumber.utils.event.GenerateEnemyTimeEvent;
import io.cucumber.utils.event.IncreaseBalanceTimeEvent;
import io.cucumber.utils.event.TimeEvent;
import io.cucumber.utils.storage.GameStorage;
import io.cucumber.utils.storage.defender.DefenderType;
import io.cucumber.utils.storage.enemy.EnemyType;

public class LevelGenerator {

    private final Random random;
    private final LevelMapGenerator levelMapGenerator;

    public LevelGenerator() {
        this.random = new Random();
        this.levelMapGenerator = new LevelMapGenerator(random);
    }

    public Level generate(int width, GameStorage storage) {
        int timeInSeconds = random.nextInt(1, 5) * 50;
        int height = timeInSeconds / 2 + random.nextInt(-10, 10);
        int border = random.nextInt(1, 3);
        int balance = height * (5 + random.nextInt(-1, 1));
        Array<TimeEvent> timeEvents = new Array<>();

        int startEventGapInSeconds = 10;
        int eventsGapInSeconds = 5;
        int lastEvenTime = 0;
        for (int i = startEventGapInSeconds; i < timeInSeconds - eventsGapInSeconds; i++) {
            if (lastEvenTime + eventsGapInSeconds > i) {
                continue;
            }

            int eventType = random.nextInt(3);
            if (eventType == 0) {
                timeEvents.add(new GenerateEnemyTimeEvent(
                        i,
                        storage.getEnemy(EnemyType.values()[random.nextInt(EnemyType.values().length)]))
                );
            } else if (eventType == 1) {
                timeEvents.add(new IncreaseBalanceTimeEvent(
                        i,
                        (random.nextInt(1) + 2) * 50)
                );
            }
            lastEvenTime = i;
        }

        timeEvents.add(new GenerateEnemyTimeEvent(
                lastEvenTime + eventsGapInSeconds,
                storage.getEnemy(EnemyType.values()[random.nextInt(EnemyType.values().length)]))
        );

        return new Level(
                100,
                timeInSeconds,
                balance,
                Array.with(storage.getDefender(DefenderType.BASE), storage.getDefender(DefenderType.SMALL)),
                timeEvents,
                levelMapGenerator.generate(width, height, border)
        );
    }
}

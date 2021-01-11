package io.cucumber.utils.generator;

import com.badlogic.gdx.utils.Array;

import io.cucumber.utils.generator.event.GenerateEnemyTimeEvent;
import io.cucumber.utils.generator.event.IncreaseBalanceTimeEvent;
import io.cucumber.utils.generator.event.TimeEvent;
import io.cucumber.utils.random.Random;
import io.cucumber.utils.storage.GameStorage;
import io.cucumber.utils.storage.defender.DefenderData;
import io.cucumber.utils.storage.enemy.EnemyType;

public class LevelGenerator {

    private final Random random;
    private final LevelMapGenerator levelMapGenerator;

    public LevelGenerator() {
        this.random = new Random();
        this.levelMapGenerator = new LevelMapGenerator(random);
    }

    public Level generate(int width, Array<DefenderData> levelDefenders, GameStorage storage) {
        // Generate length of level. Main param, a lot of others depends on it (Should be chosen by user in future)
        int length = random.nextInt(1, 5);

        // Level params generation
        int border = random.nextInt(1, 3);
        int timeInSeconds = length * 60 + random.nextInt(-30, 30, 5);
        int height = length * 30 + random.nextInt(-10, 10);
        int balance = length * 100 + random.nextInt(-50, 50, 50);
        int health = length * 75 + random.nextInt(-25, 25, 5);
        int starterEventCoolDown = random.nextInt(10, 20);
        int eventCoolDown = random.nextInt(4, 8);

        return new Level(
                health,
                timeInSeconds,
                balance,
                levelDefenders,
                generateTimeEvents(timeInSeconds, starterEventCoolDown, eventCoolDown, storage),
                levelMapGenerator.generate(width, height, border)
        );
    }

    private Array<TimeEvent> generateTimeEvents(int timeInSeconds, int starterEventCoolDown,
                                                int eventCoolDown, GameStorage storage) {
        Array<TimeEvent> timeEvents = new Array<>();
        int lastEvenTime = 0;
        for (int i = starterEventCoolDown; i < timeInSeconds - eventCoolDown; i++) {
            if (lastEvenTime + eventCoolDown > i) {
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
                lastEvenTime + eventCoolDown,
                storage.getEnemy(EnemyType.values()[random.nextInt(EnemyType.values().length)]))
        );

        return timeEvents;
    }
}

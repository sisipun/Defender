package io.cucumber.utils.generator;

import com.badlogic.gdx.utils.Array;

import java.util.Arrays;
import java.util.Random;

import io.cucumber.utils.event.GenerateEnemyTimeEvent;
import io.cucumber.utils.event.IncreaseBalanceTimeEvent;
import io.cucumber.utils.storage.GameStorage;
import io.cucumber.utils.storage.defender.DefenderType;
import io.cucumber.utils.storage.enemy.EnemyType;

public class LevelGenerator {

    private final Random random;

    public LevelGenerator() {
        this.random = new Random();
    }

    public Level generate(int width, GameStorage storage) {
        return new Level(
                100,
                45,
                400,
                Array.with(storage.getDefender(DefenderType.BASE), storage.getDefender(DefenderType.SMALL)),
                Array.with(
                        new GenerateEnemyTimeEvent(1, storage.getEnemy(EnemyType.BASE)),
                        new IncreaseBalanceTimeEvent(5, 50),
                        new GenerateEnemyTimeEvent(10, storage.getEnemy(EnemyType.SMALL)),
                        new IncreaseBalanceTimeEvent(15, 50),
                        new GenerateEnemyTimeEvent(25, storage.getEnemy(EnemyType.BASE)),
                        new IncreaseBalanceTimeEvent(30, 50),
                        new IncreaseBalanceTimeEvent(35, 50),
                        new GenerateEnemyTimeEvent(40, storage.getEnemy(EnemyType.SMALL))
                ),
                generateLevelMap(width, 30, 2)
        );
    }

    public LevelMap generateLevelMap(int width, int height, int border) {
        if (width <= 0 || height <= 0) {
            return new LevelMap(new LevelBlockType[0][0], 0, 0);
        }

        LevelBlockType[][] map = new LevelBlockType[width][height];
        Array<Fork> forks = new Array<>();
        int startPositionX = random.nextInt(width);
        int startPositionY = height - border - 1;
        int currentPositionX = startPositionX;
        int currentPositionY = startPositionY;

        // Initialize area
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int type = random.nextInt(1000);
                if (type == 0) {
                    map[i][j] = LevelBlockType.WATER;
                } else if (type == 1) {
                    map[i][j] = LevelBlockType.BUILDING;
                } else {
                    map[i][j] = LevelBlockType.LAND;
                }
            }
        }

        // Create main road
        while (currentPositionY - border - 1 > 0) {
            LevelBlockType direction = random.nextInt(2) == 0 ? LevelBlockType.ROAD_LEFT : LevelBlockType.ROAD_RIGHT;
            int size = random.nextInt(LevelBlockType.ROAD_LEFT.equals(direction) ? currentPositionX : width - currentPositionX);
            if (random.nextInt(height / 4) == 0 && size > 0) {
                forks.add(new Fork(
                        currentPositionX,
                        currentPositionY,
                        direction.equals(LevelBlockType.ROAD_LEFT) ? LevelBlockType.ROAD_RIGHT : LevelBlockType.ROAD_LEFT)
                );
            }
            for (int i = 0; i < size; i++) {
                map[currentPositionX][currentPositionY] = direction;
                if (LevelBlockType.ROAD_LEFT.equals(direction)) {
                    currentPositionX--;
                } else {
                    currentPositionX++;
                }
            }

            map[currentPositionX][currentPositionY] = LevelBlockType.ROAD_DOWN;
            currentPositionY--;
            map[currentPositionX][currentPositionY] = LevelBlockType.ROAD_DOWN;
            currentPositionY--;
        }

        while (currentPositionY - border > 0) {
            map[currentPositionX][currentPositionY] = LevelBlockType.ROAD_DOWN;
            currentPositionY--;
        }

        map[currentPositionX][currentPositionY] = LevelBlockType.ROAD_END;

        // Create forks
        for (int i = 0; i < forks.size; i++) {
            Fork fork = forks.get(i);
            createHorizontalFork(fork.x, fork.y, currentPositionX, currentPositionY, fork.direction,
                    map, width);
        }

        return new LevelMap(map, startPositionX, startPositionY);
    }

    private void createHorizontalFork(int currentX, int currentY, int endX, int endY,
                                      LevelBlockType direction, LevelBlockType[][] map, int width) {
        // Check horizontal fork conditions
        if (!Arrays.asList(LevelBlockType.ROAD_LEFT, LevelBlockType.ROAD_RIGHT).contains(direction)) {
            return;
        }
        if ((LevelBlockType.ROAD_LEFT.equals(direction) && currentX <= 0) ||
                (LevelBlockType.ROAD_RIGHT.equals(direction) && currentX >= width)) {
            return;
        }
        if (currentY - 1 <= endY) {
            return;
        }

        // Initialize fork
        map[currentX][currentY] = LevelBlockType.ROAD_HORIZONTAL_RAND;
        if (LevelBlockType.ROAD_LEFT.equals(direction)) {
            currentX--;
        } else if (LevelBlockType.ROAD_RIGHT.equals(direction)) {
            currentX++;
        }

        while (currentY - 1 > endY) {
            int size = random.nextInt(LevelBlockType.ROAD_LEFT.equals(direction) ? currentX : width - currentX);
            for (int i = 0; i < size; i++) {
                if (isRoad(map[currentX][currentY])) {
                    return;
                }
                map[currentX][currentY] = direction;
                if (LevelBlockType.ROAD_LEFT.equals(direction)) {
                    currentX--;
                } else {
                    currentX++;
                }
            }
            direction = random.nextInt(2) == 0 ? LevelBlockType.ROAD_LEFT : LevelBlockType.ROAD_RIGHT;

            map[currentX][currentY] = LevelBlockType.ROAD_DOWN;
            currentY--;
            if (isRoad(map[currentX][currentY])) {
                return;
            }
            map[currentX][currentY] = LevelBlockType.ROAD_DOWN;
            currentY--;
            if (isRoad(map[currentX][currentY])) {
                return;
            }
        }

        while (currentY > endY) {
            map[currentX][currentY] = LevelBlockType.ROAD_DOWN;
            currentY--;
            if (isRoad(map[currentX][currentY])) {
                return;
            }
        }

        while (currentX > endX) {
            map[currentX][currentY] = LevelBlockType.ROAD_LEFT;
            currentX--;
        }

        while (currentX < endX) {
            map[currentX][currentY] = LevelBlockType.ROAD_RIGHT;
            currentX++;
        }
    }

    private boolean isRoad(LevelBlockType levelBlockType) {
        return Arrays.asList(
                LevelBlockType.ROAD_UP,
                LevelBlockType.ROAD_DOWN,
                LevelBlockType.ROAD_LEFT,
                LevelBlockType.ROAD_RIGHT,
                LevelBlockType.ROAD_HORIZONTAL_RAND,
                LevelBlockType.ROAD_END).contains(levelBlockType);
    }

    static class Fork {
        private int x;
        private int y;
        private LevelBlockType direction;

        public Fork(int x, int y, LevelBlockType direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }

}

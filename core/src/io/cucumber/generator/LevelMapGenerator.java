package io.cucumber.generator;

import com.badlogic.gdx.utils.Array;

import java.util.Arrays;

import io.cucumber.utils.random.Random;

public class LevelMapGenerator {

    private final Random random;

    public LevelMapGenerator(Random random) {
        this.random = random;
    }

    public LevelMap generate(int width, int height, int border) {
        if (width <= 0 || height <= 0) {
            return new LevelMap(new LevelBlockType[0][0], 0, 0);
        }

        // Initialize main params
        LevelBlockType[][] map = new LevelBlockType[width][height];
        Array<Fork> forks = new Array<>();
        int startPositionX = random.nextInt(width);
        int startPositionY = height - border - 1;
        int currentPositionX = startPositionX;
        int currentPositionY = startPositionY;

        // Initialize area
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = LevelBlockType.LAND;
            }
        }

        int mapSize = width * height;

        // Generate buildings
        int buildingCount = random.nextInt(mapSize / 64, mapSize / 32);
        for (int i = 0; i < buildingCount; i++) {
            int buildingLength = random.nextInt(1, 5);
            int direction = random.nextInt(4);
            int x = random.nextInt(border, width - border);
            int y = random.nextInt(border, height - border);
            for (int j = 0; j < buildingLength && x >= border && x <= width - border && y >= border && y <= height - border; j++) {
                map[x][y] = LevelBlockType.BUILDING;
                if (direction == 0) {
                    x++;
                } else if (direction == 1) {
                    x--;
                } else if (direction == 2) {
                    y++;
                } else if (direction == 3) {
                    y--;
                }
            }
        }

        // Generate water
        int watersCount = random.nextInt(mapSize / 256, mapSize / 128);
        for (int i = 0; i < watersCount; i++) {
            int waterHeight = random.nextInt(2, 6);
            int waterWidth = random.nextInt(2, 6);
            int x = random.nextInt(border, width - border);
            int y = random.nextInt(border, height - border);
            for (int j = x - waterWidth / 2; j < x + waterWidth / 2; j++) {
                for (int k = y - waterHeight / 2; k < y + waterHeight / 2; k++) {
                    if (j >= border && j <= width - border && k >= border && k <= height - border) {
                        map[j][k] = LevelBlockType.WATER;
                    }
                }
            }
        }

        // Create main road
        while (currentPositionY - border - 1 > 0) {
            LevelBlockType direction = random.nextInt(2) == 0 ? LevelBlockType.ROAD_LEFT : LevelBlockType.ROAD_RIGHT;
            int size = random.nextInt(LevelBlockType.ROAD_LEFT.equals(direction) ? currentPositionX : width - currentPositionX);
            if (random.nextInt(height / 4) == 0 && size > 0 && currentPositionX > 0 && currentPositionX < width - 1) {
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

        // Decorate land with water
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (LevelBlockType.LAND.equals(map[i][j])) {
                    if (i + 1 < width && LevelBlockType.WATER.equals(map[i + 1][j])) {
                        map[i][j] = LevelBlockType.LAND_WATER_LEFT;
                    }
                    if (i - 1 > 0 && LevelBlockType.WATER.equals(map[i - 1][j])) {
                        map[i][j] = LevelBlockType.LAND_WATER_RIGHT;
                    }
                    if (j + 1 < height && LevelBlockType.WATER.equals(map[i][j + 1])) {
                        map[i][j] = LevelBlockType.LAND_WATER_BOTTOM;
                    }
                    if (j - 1 > 0 && LevelBlockType.WATER.equals(map[i][j - 1])) {
                        map[i][j] = LevelBlockType.LAND_WATER_TOP;
                    }
                }
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (LevelBlockType.LAND.equals(map[i][j])) {
                    if (i + 1 < width && j + 1 < height && LevelBlockType.WATER.equals(map[i + 1][j + 1])) {
                        map[i][j] = LevelBlockType.LAND_WATER_BOTTOM_LEFT;
                    }
                    if (i - 1 > 0 && j + 1 < height && LevelBlockType.WATER.equals(map[i - 1][j + 1])) {
                        map[i][j] = LevelBlockType.LAND_WATER_BOTTOM_RIGHT;
                    }
                    if (i + 1 < width && j - 1 > 0 && LevelBlockType.WATER.equals(map[i + 1][j - 1])) {
                        map[i][j] = LevelBlockType.LAND_WATER_TOP_LEFT;
                    }
                    if (i - 1 > 0 && j - 1 > 0 && LevelBlockType.WATER.equals(map[i - 1][j - 1])) {
                        map[i][j] = LevelBlockType.LAND_WATER_TOP_RIGHT;
                    }
                }
            }
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

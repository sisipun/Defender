package io.cucumber.utils.generator;

import com.badlogic.gdx.utils.Array;

import java.util.Arrays;
import java.util.Random;

public class AreaMapGenerator {

    private final Random random;

    public AreaMapGenerator() {
        this.random = new Random();
    }

    public AreaMap generate(int width, int height, int border) {
        if (width <= 0 || height <= 0) {
            return new AreaMap(new AreaBlockType[0][0], 0, 0);
        }

        AreaBlockType[][] map = new AreaBlockType[width][height];
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
                    map[i][j] = AreaBlockType.WATER;
                } else if (type == 1) {
                    map[i][j] = AreaBlockType.BUILDING;
                } else {
                    map[i][j] = AreaBlockType.LAND;
                }
            }
        }

        // Create main road
        while (currentPositionY - border - 1 > 0) {
            AreaBlockType direction = random.nextInt(2) == 0 ? AreaBlockType.ROAD_LEFT : AreaBlockType.ROAD_RIGHT;
            int size = random.nextInt(AreaBlockType.ROAD_LEFT.equals(direction) ? currentPositionX : width - currentPositionX);
            if (random.nextInt(height / 4) == 0 && size > 0) {
                forks.add(new Fork(
                        currentPositionX,
                        currentPositionY,
                        direction.equals(AreaBlockType.ROAD_LEFT) ? AreaBlockType.ROAD_RIGHT : AreaBlockType.ROAD_LEFT)
                );
            }
            for (int i = 0; i < size; i++) {
                map[currentPositionX][currentPositionY] = direction;
                if (AreaBlockType.ROAD_LEFT.equals(direction)) {
                    currentPositionX--;
                } else {
                    currentPositionX++;
                }
            }

            map[currentPositionX][currentPositionY] = AreaBlockType.ROAD_DOWN;
            currentPositionY--;
            map[currentPositionX][currentPositionY] = AreaBlockType.ROAD_DOWN;
            currentPositionY--;
        }

        while (currentPositionY - border > 0) {
            map[currentPositionX][currentPositionY] = AreaBlockType.ROAD_DOWN;
            currentPositionY--;
        }

        map[currentPositionX][currentPositionY] = AreaBlockType.ROAD_END;

        // Create forks
        for (int i = 0; i < forks.size; i++) {
            Fork fork = forks.get(i);
            createHorizontalFork(fork.x, fork.y, currentPositionX, currentPositionY, fork.direction,
                    map, width);
        }

        return new AreaMap(map, startPositionX, startPositionY);
    }

    private void createHorizontalFork(int currentX, int currentY, int endX, int endY,
                                      AreaBlockType direction, AreaBlockType[][] map, int width) {
        // Check horizontal fork conditions
        if (!Arrays.asList(AreaBlockType.ROAD_LEFT, AreaBlockType.ROAD_RIGHT).contains(direction)) {
            return;
        }
        if ((AreaBlockType.ROAD_LEFT.equals(direction) && currentX <= 0) ||
                (AreaBlockType.ROAD_RIGHT.equals(direction) && currentX >= width)) {
            return;
        }
        if (currentY - 1 <= endY) {
            return;
        }

        // Initialize fork
        map[currentX][currentY] = AreaBlockType.ROAD_HORIZONTAL_RAND;
        if (AreaBlockType.ROAD_LEFT.equals(direction)) {
            currentX--;
        } else if (AreaBlockType.ROAD_RIGHT.equals(direction)) {
            currentX++;
        }

        while (currentY - 1 > endY) {
            int size = random.nextInt(AreaBlockType.ROAD_LEFT.equals(direction) ? currentX : width - currentX);
            for (int i = 0; i < size; i++) {
                if (isRoad(map[currentX][currentY])) {
                    return;
                }
                map[currentX][currentY] = direction;
                if (AreaBlockType.ROAD_LEFT.equals(direction)) {
                    currentX--;
                } else {
                    currentX++;
                }
            }
            direction = random.nextInt(2) == 0 ? AreaBlockType.ROAD_LEFT : AreaBlockType.ROAD_RIGHT;

            map[currentX][currentY] = AreaBlockType.ROAD_DOWN;
            currentY--;
            if (isRoad(map[currentX][currentY])) {
                return;
            }
            map[currentX][currentY] = AreaBlockType.ROAD_DOWN;
            currentY--;
            if (isRoad(map[currentX][currentY])) {
                return;
            }
        }

        while (currentY > endY) {
            map[currentX][currentY] = AreaBlockType.ROAD_DOWN;
            currentY--;
            if (isRoad(map[currentX][currentY])) {
                return;
            }
        }

        while (currentX > endX) {
            map[currentX][currentY] = AreaBlockType.ROAD_LEFT;
            currentX--;
        }

        while (currentX < endX) {
            map[currentX][currentY] = AreaBlockType.ROAD_RIGHT;
            currentX++;
        }
    }

    private boolean isRoad(AreaBlockType areaBlockType) {
        return Arrays.asList(
                AreaBlockType.ROAD_UP,
                AreaBlockType.ROAD_DOWN,
                AreaBlockType.ROAD_LEFT,
                AreaBlockType.ROAD_RIGHT,
                AreaBlockType.ROAD_HORIZONTAL_RAND,
                AreaBlockType.ROAD_END).contains(areaBlockType);
    }

    static class Fork {
        private int x;
        private int y;
        private AreaBlockType direction;

        public Fork(int x, int y, AreaBlockType direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }

}

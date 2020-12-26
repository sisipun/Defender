package io.cucumber.utils.generator;

import com.badlogic.gdx.utils.Array;

import java.util.Arrays;
import java.util.Random;

import io.cucumber.actor.area.AreaMap;
import io.cucumber.actor.area.AreaType;

public class AreaMapGenerator {

    private final Random random;

    public AreaMapGenerator() {
        this.random = new Random();
    }

    public AreaMap generate(int width, int height, int border) {
        if (width <= 0 || height <= 0) {
            return new AreaMap(new AreaType[0][0], 0, 0);
        }

        AreaType[][] map = new AreaType[width][height];
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
                    map[i][j] = AreaType.WATER;
                } else if (type == 1) {
                    map[i][j] = AreaType.BUILDING;
                } else {
                    map[i][j] = AreaType.LAND;
                }
            }
        }

        // Create main road
        while (currentPositionY - border - 1 > 0) {
            AreaType direction = random.nextInt(2) == 0 ? AreaType.ROAD_LEFT : AreaType.ROAD_RIGHT;
            int size = random.nextInt(AreaType.ROAD_LEFT.equals(direction) ? currentPositionX : width - currentPositionX);
            if (random.nextInt(height / 4) == 0 && size > 0) {
                forks.add(new Fork(
                        currentPositionX,
                        currentPositionY,
                        direction.equals(AreaType.ROAD_LEFT) ? AreaType.ROAD_RIGHT : AreaType.ROAD_LEFT)
                );
            }
            for (int i = 0; i < size; i++) {
                map[currentPositionX][currentPositionY] = direction;
                if (AreaType.ROAD_LEFT.equals(direction)) {
                    currentPositionX--;
                } else {
                    currentPositionX++;
                }
            }

            map[currentPositionX][currentPositionY] = AreaType.ROAD_DOWN;
            currentPositionY--;
            map[currentPositionX][currentPositionY] = AreaType.ROAD_DOWN;
            currentPositionY--;
        }

        while (currentPositionY - border > 0) {
            map[currentPositionX][currentPositionY] = AreaType.ROAD_DOWN;
            currentPositionY--;
        }

        map[currentPositionX][currentPositionY] = AreaType.ROAD_END;

        // Create forks
        for (int i = 0; i < forks.size; i++) {
            Fork fork = forks.get(i);
            createHorizontalFork(fork.x, fork.y, currentPositionX, currentPositionY, fork.direction,
                    map, width);
        }

        return new AreaMap(map, startPositionX, startPositionY);
    }

    private void createHorizontalFork(int currentX, int currentY, int endX, int endY,
                                      AreaType direction, AreaType[][] map, int width) {
        // Check horizontal fork conditions
        if (!Arrays.asList(AreaType.ROAD_LEFT, AreaType.ROAD_RIGHT).contains(direction)) {
            return;
        }
        if ((AreaType.ROAD_LEFT.equals(direction) && currentX <= 0) ||
                (AreaType.ROAD_RIGHT.equals(direction) && currentX >= width)) {
            return;
        }
        if (currentY - 1 <= endY) {
            return;
        }

        // Initialize fork
        map[currentX][currentY] = AreaType.ROAD_HORIZONTAL_RAND;
        if (AreaType.ROAD_LEFT.equals(direction)) {
            currentX--;
        } else if (AreaType.ROAD_RIGHT.equals(direction)) {
            currentX++;
        }

        while (currentY - 1 > endY) {
            int size = random.nextInt(AreaType.ROAD_LEFT.equals(direction) ? currentX : width - currentX);
            for (int i = 0; i < size; i++) {
                if (isRoad(map[currentX][currentY])) {
                    return;
                }
                map[currentX][currentY] = direction;
                if (AreaType.ROAD_LEFT.equals(direction)) {
                    currentX--;
                } else {
                    currentX++;
                }
            }
            direction = random.nextInt(2) == 0 ? AreaType.ROAD_LEFT : AreaType.ROAD_RIGHT;

            map[currentX][currentY] = AreaType.ROAD_DOWN;
            currentY--;
            if (isRoad(map[currentX][currentY])) {
                return;
            }
            map[currentX][currentY] = AreaType.ROAD_DOWN;
            currentY--;
            if (isRoad(map[currentX][currentY])) {
                return;
            }
        }

        while (currentY > endY) {
            map[currentX][currentY] = AreaType.ROAD_DOWN;
            currentY--;
            if (isRoad(map[currentX][currentY])) {
                return;
            }
        }

        while (currentX > endX) {
            map[currentX][currentY] = AreaType.ROAD_LEFT;
            currentX--;
        }

        while (currentX < endX) {
            map[currentX][currentY] = AreaType.ROAD_RIGHT;
            currentX++;
        }
    }

    private boolean isRoad(AreaType areaType) {
        return Arrays.asList(
                AreaType.ROAD_UP,
                AreaType.ROAD_DOWN,
                AreaType.ROAD_LEFT,
                AreaType.ROAD_RIGHT,
                AreaType.ROAD_HORIZONTAL_RAND,
                AreaType.ROAD_END).contains(areaType);
    }

    static class Fork {
        private int x;
        private int y;
        private AreaType direction;

        public Fork(int x, int y, AreaType direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }

}

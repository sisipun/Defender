package io.cucumber.utils.generator;

import java.util.Random;

import io.cucumber.model.map.Direction;
import io.cucumber.model.map.GameMap;

public class MapGenerator {

    public GameMap generate(int width, int height) {
        if (width <= 0 || height <= 0) {
            return new GameMap(new Direction[0][0], 0, 0, 0, 0);
        }

        Random random = new Random();
        Direction[][] map = new Direction[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = Direction.NONE;
            }
        }

        int startPositionX = random.nextInt(width);
        int startPositionY = height - 1;

        int currentPositionX = startPositionX;
        int currentPositionY = startPositionY;

        while (currentPositionY - 1 > 0) {
            int direction = random.nextInt(2);
            if (direction == 0) {
                int size = random.nextInt(currentPositionX);
                for (int i = 0; i < size; i++) {
                    map[currentPositionX][currentPositionY] = Direction.LEFT;
                    currentPositionX--;
                }
            } else if (direction == 1) {
                int size = random.nextInt(width - currentPositionX);
                for (int i = 0; i < size; i++) {
                    map[currentPositionX][currentPositionY] = Direction.RIGHT;
                    currentPositionX++;
                }
            }

            map[currentPositionX][currentPositionY] = Direction.DOWN;
            currentPositionY--;
            map[currentPositionX][currentPositionY] = Direction.DOWN;
            currentPositionY--;
        }

        return new GameMap(map, startPositionX, startPositionY, currentPositionX, currentPositionY);
    }

}

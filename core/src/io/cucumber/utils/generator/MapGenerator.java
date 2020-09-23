package io.cucumber.utils.generator;

import java.util.Random;

public class MapGenerator {

    public GameMap generate(int width, int height) {
        if (width <= 0 || height <= 0) {
            return new GameMap(new GameMap.Direction[0][0], 0, 0, 0, 0);
        }

        Random random = new Random();
        GameMap.Direction[][] map = new GameMap.Direction[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = GameMap.Direction.NONE;
            }
        }

        int startPositionX = random.nextInt(width);
        int startPositionY = height - 1;

        int currentPositionX = startPositionX;
        int currentPositionY = startPositionY;

        map[currentPositionX][currentPositionY] = GameMap.Direction.DOWN;

        while (currentPositionY - 1 > 0) {
            int direction = random.nextInt(2);
            if (direction == 0) {
                int size = random.nextInt(currentPositionX);
                for (int i = 0; i < size; i++) {
                    currentPositionX--;
                    map[currentPositionX][currentPositionY] = GameMap.Direction.LEFT;
                }
            } else if (direction == 1) {
                int size = random.nextInt(width - currentPositionX);
                for (int i = 0; i < size; i++) {
                    currentPositionX++;
                    map[currentPositionX][currentPositionY] = GameMap.Direction.RIGHT;
                }
            }

            currentPositionY--;
            map[currentPositionX][currentPositionY] = GameMap.Direction.DOWN;
            currentPositionY--;
            map[currentPositionX][currentPositionY] = GameMap.Direction.DOWN;
        }

        return new GameMap(map, startPositionX, startPositionY, currentPositionX, currentPositionY);
    }

}

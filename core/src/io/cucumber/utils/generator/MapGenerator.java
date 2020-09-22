package io.cucumber.utils.generator;

import java.util.Random;

public class MapGenerator {

    public GameMap generate(int width, int height) {
        if (width <= 0 || height <= 0) {
            return new GameMap(new int[0][0], 0, 0, 0, 0);
        }

        Random random = new Random();
        int[][] map = new int[width][height];

        int startPositionX = random.nextInt(width);
        int startPositionY = height - 1;

        int currentPositionX = startPositionX;
        int currentPositionY = startPositionY;

        map[currentPositionX][currentPositionY] = 1;

        while (currentPositionY - 1 > 0) {
            int direction = random.nextInt(2);
            if (direction == 0) {
                int size = random.nextInt(currentPositionX);
                for (int i = 0; i < size; i++) {
                    currentPositionX--;
                    map[currentPositionX][currentPositionY] = 1;
                }
            } else if (direction == 1) {
                int size = random.nextInt(width - currentPositionX);
                for (int i = 0; i < size; i++) {
                    currentPositionX++;
                    map[currentPositionX][currentPositionY] = 1;
                }
            }

            currentPositionY--;
            map[currentPositionX][currentPositionY] = 1;
            currentPositionY--;
            map[currentPositionX][currentPositionY] = 1;
        }

        return new GameMap(map, startPositionX, startPositionY, currentPositionX, currentPositionY);
    }

}

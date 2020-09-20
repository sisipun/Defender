package io.cucumber.utils.generator;

import java.util.Random;

public class MapGenerator {

    public Map generate(int width, int height, int coverage) {
        if (width <= 0 || height <=0 || coverage < 0 || coverage > 100) {
            return new Map(new int[0][0], 0, 0);
        }

        Random random = new Random();
        int[][] map = new int[width][height];

        int startPositionX = random.nextInt(width);
        int startPositionY = random.nextInt(height);

        int currentPositionX = startPositionX;
        int currentPositionY = startPositionY;

        map[currentPositionX][currentPositionY] = 1;

        int stepCount = width * height * (coverage / 100);

        for (int i = 1; i < stepCount;) {
            int direction = random.nextInt(4);
            if (direction == 0 && currentPositionX + 1 < width) {
                currentPositionX++;
            } else if (direction == 1 && currentPositionX - 1 > 0) {
                currentPositionX--;
            } else if (direction == 2 && currentPositionY + 1 < height) {
                currentPositionY++;
            } else if (direction == 3 && currentPositionY - 1 > 0) {
                currentPositionY--;
            }

            if (map[currentPositionX][currentPositionY] == 0) {
                map[currentPositionX][currentPositionY] = 1;
                i++;
            }
        }

        return new Map(map, startPositionX, startPositionY);
    }

}

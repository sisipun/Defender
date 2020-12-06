package io.cucumber.utils.generator;

import java.util.Random;

import io.cucumber.actor.area.AreaMap;
import io.cucumber.actor.area.AreaType;

public class AreaMapGenerator {

    public AreaMap generate(int width, int height) {
        if (width <= 0 || height <= 0) {
            return new AreaMap(new AreaType[0][0], 0, 0);
        }

        Random random = new Random();
        AreaType[][] map = new AreaType[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = AreaType.NONE;
            }
        }

        int startPositionX = random.nextInt(width);
        int startPositionY = height - 1;

        int currentPositionX = startPositionX;
        int currentPositionY = startPositionY;

        while (currentPositionY - 1 > 0) {
            int direction = random.nextInt(2);
            if (direction == 0 && currentPositionX > 0) {
                int size = random.nextInt(currentPositionX);
                for (int i = 0; i < size; i++) {
                    map[currentPositionX][currentPositionY] = AreaType.LEFT;
                    currentPositionX--;
                }
            } else if (direction == 1 && currentPositionX < width) {
                int size = random.nextInt(width - currentPositionX);
                for (int i = 0; i < size; i++) {
                    map[currentPositionX][currentPositionY] = AreaType.RIGHT;
                    currentPositionX++;
                }
            }

            map[currentPositionX][currentPositionY] = AreaType.DOWN;
            currentPositionY--;
            map[currentPositionX][currentPositionY] = AreaType.DOWN;
            currentPositionY--;
        }

        while (currentPositionY > 0) {
            map[currentPositionX][currentPositionY] = AreaType.DOWN;
            currentPositionY--;
        }

        map[currentPositionX][currentPositionY] = AreaType.END;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(map[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

        return new AreaMap(map, startPositionX, startPositionY);
    }

}

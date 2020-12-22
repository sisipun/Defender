package io.cucumber.utils.generator;

import java.util.Random;

import io.cucumber.actor.area.AreaMap;
import io.cucumber.actor.area.AreaType;

public class AreaMapGenerator {

    public AreaMap generate(int width, int height, int border) {
        if (width <= 0 || height <= 0) {
            return new AreaMap(new AreaType[0][0], 0, 0);
        }

        Random random = new Random();
        AreaType[][] map = new AreaType[width][height];
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

        int startPositionX = random.nextInt(width);
        int startPositionY = height - border - 1;

        int currentPositionX = startPositionX;
        int currentPositionY = startPositionY;

        while (currentPositionY - border - 1 > 0) {
            int direction = random.nextInt(2);
            if (direction == 0 && currentPositionX > 0) {
                int size = random.nextInt(currentPositionX);
                for (int i = 0; i < size; i++) {
                    map[currentPositionX][currentPositionY] = AreaType.ROAD_LEFT;
                    currentPositionX--;
                }
            } else if (direction == 1 && currentPositionX < width) {
                int size = random.nextInt(width - currentPositionX);
                for (int i = 0; i < size; i++) {
                    map[currentPositionX][currentPositionY] = AreaType.ROAD_RIGHT;
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

        return new AreaMap(map, startPositionX, startPositionY);
    }

}

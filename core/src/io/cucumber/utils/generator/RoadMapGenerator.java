package io.cucumber.utils.generator;

import java.util.Random;

import io.cucumber.model.actor.road.RoadMap;
import io.cucumber.model.actor.road.RoadType;

public class RoadMapGenerator {

    public RoadMap generate(int width, int height) {
        if (width <= 0 || height <= 0) {
            return new RoadMap(new RoadType[0][0], 0, 0, 0, 0);
        }

        Random random = new Random();
        RoadType[][] map = new RoadType[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = RoadType.NONE;
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
                    map[currentPositionX][currentPositionY] = RoadType.LEFT;
                    currentPositionX--;
                }
            } else if (direction == 1) {
                int size = random.nextInt(width - currentPositionX);
                for (int i = 0; i < size; i++) {
                    map[currentPositionX][currentPositionY] = RoadType.RIGHT;
                    currentPositionX++;
                }
            }

            map[currentPositionX][currentPositionY] = RoadType.DOWN;
            currentPositionY--;
            map[currentPositionX][currentPositionY] = RoadType.DOWN;
            currentPositionY--;
        }

        map[currentPositionX][currentPositionY] = RoadType.END;

        return new RoadMap(map, startPositionX, startPositionY, currentPositionX, currentPositionY);
    }

}

package io.cucumber.utils.generator;

import com.badlogic.gdx.utils.Pools;

import java.util.Random;

import io.cucumber.actor.area.AreaMap;
import io.cucumber.actor.area.AreaType;

public class AreaMapGenerator {

    public AreaMap generate(int width, int height, int border) {
        if (width <= 0 || height <= 0) {
            return new AreaMap(new AreaMap.Block[0][0], 0, 0);
        }

        Random random = new Random();
        AreaMap.Block[][] map = new AreaMap.Block[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int type = random.nextInt(100);
                AreaType areaType;
                if (type == 0) {
                    areaType = AreaType.WATER;
                } else if (type == 1) {
                    areaType = AreaType.BUILDING;
                } else {
                    areaType = AreaType.LAND;
                }
                map[i][j] = Pools.obtain(AreaMap.Block.class).init(areaType, areaType);
            }
        }

        int startPositionX = random.nextInt(width);
        int startPositionY = height - border - 1;

        int currentPositionX = startPositionX;
        int currentPositionY = startPositionY;

        AreaType previousType = AreaType.LAND;
        while (currentPositionY - border - 1 > 0) {
            int direction = random.nextInt(2);
            if (direction == 0 && currentPositionX > 0) {
                int size = random.nextInt(currentPositionX);
                for (int i = 0; i < size; i++) {
                    map[currentPositionX][currentPositionY].init(AreaType.ROAD_LEFT, previousType);
                    previousType = AreaType.ROAD_LEFT;
                    currentPositionX--;
                }
            } else if (direction == 1 && currentPositionX < width) {
                int size = random.nextInt(width - currentPositionX);
                for (int i = 0; i < size; i++) {
                    map[currentPositionX][currentPositionY].init(AreaType.ROAD_RIGHT, previousType);
                    previousType = AreaType.ROAD_RIGHT;
                    currentPositionX++;
                }
            }

            map[currentPositionX][currentPositionY].init(AreaType.ROAD_DOWN, previousType);
            previousType = AreaType.ROAD_DOWN;
            currentPositionY--;
            map[currentPositionX][currentPositionY].init(AreaType.ROAD_DOWN, previousType);
            previousType = AreaType.ROAD_DOWN;
            currentPositionY--;
        }

        while (currentPositionY - border > 0) {
            map[currentPositionX][currentPositionY].init(AreaType.ROAD_DOWN, previousType);
            previousType = AreaType.ROAD_DOWN;
            currentPositionY--;
        }

        map[currentPositionX][currentPositionY].init(AreaType.ROAD_END, previousType);

        return new AreaMap(map, startPositionX, startPositionY);
    }

}

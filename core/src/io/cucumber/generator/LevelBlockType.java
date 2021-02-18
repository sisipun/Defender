package io.cucumber.generator;

import java.util.Arrays;
import java.util.List;

public enum LevelBlockType {
    LAND,
    LAND_WATER_TOP,
    LAND_WATER_BOTTOM,
    LAND_WATER_LEFT,
    LAND_WATER_RIGHT,
    LAND_WATER_TOP_LEFT,
    LAND_WATER_TOP_RIGHT,
    LAND_WATER_BOTTOM_LEFT,
    LAND_WATER_BOTTOM_RIGHT,
    WATER,
    BUILDING,
    ROAD_DOWN,
    ROAD_LEFT,
    ROAD_RIGHT,
    ROAD_TOP_LEFT,
    ROAD_TOP_RIGHT,
    ROAD_LEFT_DOWN,
    ROAD_RIGHT_DOWN,
    ROAD_HORIZONTAL_RAND,
    ROAD_END;

    public final static List<LevelBlockType> ROADS = Arrays.asList(
            LevelBlockType.ROAD_DOWN,
            LevelBlockType.ROAD_LEFT,
            LevelBlockType.ROAD_RIGHT,
            LevelBlockType.ROAD_TOP_LEFT,
            LevelBlockType.ROAD_TOP_RIGHT,
            LevelBlockType.ROAD_LEFT_DOWN,
            LevelBlockType.ROAD_RIGHT_DOWN,
            LevelBlockType.ROAD_HORIZONTAL_RAND,
            LevelBlockType.ROAD_END
    );

    public final static List<LevelBlockType> ROADS_LEFT = Arrays.asList(
            LevelBlockType.ROAD_LEFT,
            LevelBlockType.ROAD_TOP_LEFT
    );

    public final static List<LevelBlockType> ROADS_RIGHT = Arrays.asList(
            LevelBlockType.ROAD_RIGHT,
            LevelBlockType.ROAD_TOP_RIGHT
    );

    public final static List<LevelBlockType> ROADS_DOWN = Arrays.asList(
            LevelBlockType.ROAD_DOWN,
            LevelBlockType.ROAD_LEFT_DOWN,
            LevelBlockType.ROAD_RIGHT_DOWN
    );
}

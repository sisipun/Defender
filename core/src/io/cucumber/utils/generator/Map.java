package io.cucumber.utils.generator;

public class Map {

    private final int[][] map;
    private final int startPositionX;
    private final int startPositionY;

    public Map(int[][] map, int startPositionX, int startPositionY) {
        this.map = map;
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
    }

    public int[][] getMap() {
        return map;
    }

    public int getStartPositionX() {
        return startPositionX;
    }

    public int getStartPositionY() {
        return startPositionY;
    }
}

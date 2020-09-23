package io.cucumber.utils.generator;

public class GameMap {

    private final Direction[][] map;
    private final int startPositionX;
    private final int startPositionY;
    private final int endPositionX;
    private final int endPositionY;

    public GameMap(Direction[][] map, int startPositionX, int startPositionY, int endPositionX, int endPositionY) {
        this.map = map;
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
        this.endPositionX = endPositionX;
        this.endPositionY = endPositionY;
    }

    public Direction[][] getMap() {
        return map;
    }

    public int getStartPositionX() {
        return startPositionX;
    }

    public int getStartPositionY() {
        return startPositionY;
    }

    public int getEndPositionX() {
        return endPositionX;
    }

    public int getEndPositionY() {
        return endPositionY;
    }

    public enum Direction {
        NONE,
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }
}

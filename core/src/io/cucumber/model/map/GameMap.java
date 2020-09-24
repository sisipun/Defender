package io.cucumber.model.map;

public class GameMap {

    private final Direction[][] value;
    private final int startPositionX;
    private final int startPositionY;
    private final int endPositionX;
    private final int endPositionY;

    public GameMap(Direction[][] value, int startPositionX, int startPositionY, int endPositionX, int endPositionY) {
        this.value = value;
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
        this.endPositionX = endPositionX;
        this.endPositionY = endPositionY;
    }

    public Direction[][] getValue() {
        return value;
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
}

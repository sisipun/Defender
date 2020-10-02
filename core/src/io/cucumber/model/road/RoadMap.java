package io.cucumber.model.road;

public class RoadMap {

    private final RoadType[][] value;
    private final int startPositionX;
    private final int startPositionY;
    private final int endPositionX;
    private final int endPositionY;

    public RoadMap(RoadType[][] value, int startPositionX, int startPositionY, int endPositionX, int endPositionY) {
        this.value = value;
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
        this.endPositionX = endPositionX;
        this.endPositionY = endPositionY;
    }

    public RoadType[][] getValue() {
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

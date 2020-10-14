package io.cucumber.actor.road;

public class RoadMap {

    private final int startPositionX;
    private final int startPositionY;

    private final RoadType[][] value;

    public RoadMap(RoadType[][] value, int startPositionX, int startPositionY) {
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
        this.value = value;
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

}

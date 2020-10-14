package io.cucumber.actor.road;

public class RoadMap {

    private final RoadType[][] value;
    private final int startPositionX;
    private final int startPositionY;

    public RoadMap(RoadType[][] value, int startPositionX, int startPositionY) {
        this.value = value;
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
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

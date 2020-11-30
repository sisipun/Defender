package io.cucumber.actor.area;

public class AreaMap {

    private final int startPositionX;
    private final int startPositionY;

    private final AreaType[][] value;

    public AreaMap(AreaType[][] value, int startPositionX, int startPositionY) {
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
        this.value = value;
    }

    public AreaType[][] getValue() {
        return value;
    }

    public int getStartPositionX() {
        return startPositionX;
    }

    public int getStartPositionY() {
        return startPositionY;
    }

}

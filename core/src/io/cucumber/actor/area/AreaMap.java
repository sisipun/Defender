package io.cucumber.actor.area;

import com.badlogic.gdx.utils.Pools;

public class AreaMap {

    private final int startPositionX;
    private final int startPositionY;

    private final AreaType [][] blocks;

    public AreaMap(AreaType [][] blocks, int startPositionX, int startPositionY) {
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
        this.blocks = blocks;
    }

    public AreaType[][] getBlocks() {
        return blocks;
    }

    public int getStartPositionX() {
        return startPositionX;
    }

    public int getStartPositionY() {
        return startPositionY;
    }

    public void remove() {
        for (AreaType[] block : blocks) {
            for (AreaType value : block) {
                Pools.free(value);
            }
        }
    }
}

package io.cucumber.utils.generator;

import com.badlogic.gdx.utils.Pools;

public class AreaMap {

    private final int startPositionX;
    private final int startPositionY;

    private final AreaBlockType[][] blocks;

    public AreaMap(AreaBlockType[][] blocks, int startPositionX, int startPositionY) {
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
        this.blocks = blocks;
    }

    public AreaBlockType[][] getBlocks() {
        return blocks;
    }

    public int getStartPositionX() {
        return startPositionX;
    }

    public int getStartPositionY() {
        return startPositionY;
    }

    public void remove() {
        for (AreaBlockType[] block : blocks) {
            for (AreaBlockType value : block) {
                Pools.free(value);
            }
        }
    }
}

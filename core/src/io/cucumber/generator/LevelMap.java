package io.cucumber.generator;

import com.badlogic.gdx.utils.Pools;

public class LevelMap {

    private final int startPositionX;
    private final int startPositionY;

    private final LevelBlockType[][] blocks;

    public LevelMap(LevelBlockType[][] blocks, int startPositionX, int startPositionY) {
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
        this.blocks = blocks;
    }

    public LevelBlockType[][] getBlocks() {
        return blocks;
    }

    public int getStartPositionX() {
        return startPositionX;
    }

    public int getStartPositionY() {
        return startPositionY;
    }

    public void remove() {
        for (LevelBlockType[] block : blocks) {
            for (LevelBlockType value : block) {
                Pools.free(value);
            }
        }
    }
}

package io.cucumber.actor.area;

import com.badlogic.gdx.utils.Pools;

public class AreaMap {

    private final int startPositionX;
    private final int startPositionY;

    private final Block[][] blocks;

    public AreaMap(Block[][] blocks, int startPositionX, int startPositionY) {
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
        this.blocks = blocks;
    }

    public Block[][] getBlocks() {
        return blocks;
    }

    public int getStartPositionX() {
        return startPositionX;
    }

    public int getStartPositionY() {
        return startPositionY;
    }

    public void remove() {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                Pools.free(blocks[i][j]);
            }
        }
    }

    public static class Block {

        private AreaType type;
        private AreaType previousType;

        public Block() {
            this.type = null;
            this.previousType = null;
        }

        public Block init(AreaType type, AreaType previousType) {
            this.type = type;
            this.previousType = previousType;
            return this;
        }

        public AreaType getType() {
            return type;
        }

        public AreaType getPreviousType() {
            return previousType;
        }
    }

}

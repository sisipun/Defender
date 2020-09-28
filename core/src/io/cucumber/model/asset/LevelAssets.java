package io.cucumber.model.asset;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class LevelAssets {

    private final TextureAtlas.AtlasRegion defender;
    private final TextureAtlas.AtlasRegion enemy;
    private final TextureAtlas.AtlasRegion block;
    private final TextureAtlas.AtlasRegion menuBackground;

    public LevelAssets(TextureAtlas atlas, String defender, String enemy, String block, String menuBackground) {
        this.defender = atlas.findRegion(defender);
        this.enemy = atlas.findRegion(enemy);
        this.block = atlas.findRegion(block);
        this.menuBackground = atlas.findRegion(menuBackground);
    }

    public TextureAtlas.AtlasRegion getDefender() {
        return defender;
    }

    public TextureAtlas.AtlasRegion getEnemy() {
        return enemy;
    }

    public TextureAtlas.AtlasRegion getBlock() {
        return block;
    }

    public TextureAtlas.AtlasRegion getMenuBackground() {
        return menuBackground;
    }
}

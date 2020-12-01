package io.cucumber.manager;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {

    private final TextureAtlas.AtlasRegion block;
    private final TextureAtlas.AtlasRegion zone;
    private final TextureAtlas.AtlasRegion background;
    private final TextureAtlas.AtlasRegion menuBackground;
    private final TextureAtlas.AtlasRegion health;
    private final TextureAtlas.AtlasRegion healthBackground;

    public Assets(TextureAtlas atlas, String block, String zone,
                  String background, String menuBackground,
                  String health, String healthBackground) {
        this.block = atlas.findRegion(block);
        this.zone = atlas.findRegion(zone);
        this.background = atlas.findRegion(background);
        this.menuBackground = atlas.findRegion(menuBackground);
        this.health = atlas.findRegion(health);
        this.healthBackground = atlas.findRegion(healthBackground);
    }

    public TextureAtlas.AtlasRegion getBlock() {
        return block;
    }

    public TextureAtlas.AtlasRegion getZone() {
        return zone;
    }

    public TextureAtlas.AtlasRegion getBackground() {
        return background;
    }

    public TextureAtlas.AtlasRegion getMenuBackground() {
        return menuBackground;
    }

    public TextureAtlas.AtlasRegion getHealth() {
        return health;
    }

    public TextureAtlas.AtlasRegion getHealthBackground() {
        return healthBackground;
    }
}

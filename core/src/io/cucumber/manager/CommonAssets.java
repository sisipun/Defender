package io.cucumber.manager;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class CommonAssets {

    private final TextureAtlas.AtlasRegion block;
    private final TextureAtlas.AtlasRegion zone;
    private final TextureAtlas.AtlasRegion background;
    private final TextureAtlas.AtlasRegion menuBackground;

    public CommonAssets(TextureAtlas atlas, String block, String zone,
                        String background, String menuBackground) {
        this.block = atlas.findRegion(block);
        this.zone = atlas.findRegion(zone);
        this.background = atlas.findRegion(background);
        this.menuBackground = atlas.findRegion(menuBackground);
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
}

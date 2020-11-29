package io.cucumber.manager;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {

    private final TextureAtlas.AtlasRegion block;
    private final TextureAtlas.AtlasRegion zone;
    private final TextureAtlas.AtlasRegion background;
    private final TextureAtlas.AtlasRegion menuBackground;

    public Assets(TextureAtlas atlas, AssetsInfo info) {
        this.block = atlas.findRegion(info.getBlock());
        this.zone = atlas.findRegion(info.getZone());
        this.background = atlas.findRegion(info.getBackground());
        this.menuBackground = atlas.findRegion(info.getMenuBackground());
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

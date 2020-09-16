package io.cucumber.model;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class LevelAssets {

    private final TextureAtlas.AtlasRegion hero;

    public LevelAssets(TextureAtlas atlas, String hero) {
        this.hero = atlas.findRegion(hero);
    }

    public TextureAtlas.AtlasRegion getHero() {
        return hero;
    }
}

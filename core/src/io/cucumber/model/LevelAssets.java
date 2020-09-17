package io.cucumber.model;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class LevelAssets {

    private final TextureAtlas.AtlasRegion hero;
    private final TextureAtlas.AtlasRegion enemy;
    private final TextureAtlas.AtlasRegion menuBackground;

    public LevelAssets(TextureAtlas atlas, String hero, String enemy, String menuBackground) {
        this.hero = atlas.findRegion(hero);
        this.enemy = atlas.findRegion(enemy);
        this.menuBackground = atlas.findRegion(menuBackground);
    }

    public TextureAtlas.AtlasRegion getHero() {
        return hero;
    }

    public TextureAtlas.AtlasRegion getEnemy() {
        return enemy;
    }

    public TextureAtlas.AtlasRegion getMenuBackground() {
        return menuBackground;
    }
}

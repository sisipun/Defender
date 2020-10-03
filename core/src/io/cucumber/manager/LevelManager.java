package io.cucumber.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import io.cucumber.model.asset.LevelAssets;

public class LevelManager {

    private TextureAtlas atlas;
    private Level level;

    public Level loadLevel() {
        if (level != null) {
            return level;
        }
        atlas = new TextureAtlas(Gdx.files.internal("atlas/game.atlas"));
        level = new Level(new LevelAssets(atlas, "defender", "enemy", "block", "background", "menu_background"));
        return level;
    }

    public void removeLevel() {
        level = null;
        atlas.dispose();
    }

    public static class Level {

        private final LevelAssets levelAssets;

        public Level(LevelAssets levelAssets) {
            this.levelAssets = levelAssets;
        }

        public LevelAssets getAssets() {
            return levelAssets;
        }
    }
}

package io.cucumber.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import io.cucumber.model.LevelAssets;

public class LevelManager {

    private static TextureAtlas atlas;
    private static LevelAssets levelAssets;

    public static void loadLevels() {
        atlas = new TextureAtlas(Gdx.files.internal("atlas/game.atlas"));
        levelAssets = new LevelAssets(atlas, "hero", "enemy", "block", "menu_background");
    }

    public static void removeLevels() {
        atlas.dispose();
    }

    public static LevelAssets get() {
        return levelAssets;
    }

    private LevelManager() {
    }
}

package io.cucumber.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import io.cucumber.model.level.CommonAssets;
import io.cucumber.model.level.DefenderType;
import io.cucumber.model.level.Level;

import static io.cucumber.utils.constants.Constants.GAME_LENGTH;

public class LevelManager {

    private TextureAtlas atlas;
    private Level level;
    private DefenderStorage defenderStorage;

    public LevelManager() {
        this.defenderStorage = new DefenderStorage();
    }

    public Level init() {
        if (level != null) {
            return level;
        }

        atlas = new TextureAtlas(Gdx.files.internal("atlas/game.atlas"));
        CommonAssets assets = new CommonAssets(atlas, "enemy", "block", "zone", "background",
                "menu_background");
        defenderStorage.init(atlas);

        level = new Level(GAME_LENGTH, assets, defenderStorage.get(Array.with(DefenderType.BASE)));
        return level;
    }

    public void removeLevel() {
        level = null;
        atlas.dispose();
    }
}

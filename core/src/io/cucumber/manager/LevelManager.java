package io.cucumber.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import io.cucumber.model.level.Assets;
import io.cucumber.model.level.LevelData;
import io.cucumber.model.level.DefenderSample;

import static io.cucumber.utils.constants.Constants.DEFENDER_POWER;
import static io.cucumber.utils.constants.Constants.DEFENDER_SIZE;
import static io.cucumber.utils.constants.Constants.DEFENDER_ZONE_ALPHA;
import static io.cucumber.utils.constants.Constants.DEFENDER_ZONE_SIZE;

public class LevelManager {

    private TextureAtlas atlas;
    private LevelData levelData;

    public LevelData loadLevel() {
        if (levelData != null) {
            return levelData;
        }
        atlas = new TextureAtlas(Gdx.files.internal("atlas/game.atlas"));
        Assets assets = new Assets(atlas, "defender", "enemy", "block", "zone", "background", "menu_background");
        DefenderSample defender = new DefenderSample(
                DEFENDER_SIZE,
                DEFENDER_POWER,
                DEFENDER_ZONE_SIZE,
                DEFENDER_ZONE_ALPHA,
                assets.getDefender(),
                assets.getEnemy(),
                assets.getZone()
        );
        levelData = new LevelData(assets, Array.with(defender));
        return levelData;
    }

    public void removeLevel() {
        levelData = null;
        atlas.dispose();
    }
}

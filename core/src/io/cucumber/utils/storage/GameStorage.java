package io.cucumber.utils.storage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import io.cucumber.utils.storage.assets.CommonAssets;
import io.cucumber.utils.storage.assets.CommonAssetsStorage;
import io.cucumber.utils.storage.defender.DefenderData;
import io.cucumber.utils.storage.defender.DefenderStorage;
import io.cucumber.utils.storage.defender.DefenderType;
import io.cucumber.utils.storage.enemy.EnemyStorage;
import io.cucumber.utils.storage.enemy.EnemyData;
import io.cucumber.utils.storage.enemy.EnemyType;

public class GameStorage {

    private TextureAtlas atlas;
    private CommonAssetsStorage assetsStorage;
    private DefenderStorage defenderStorage;
    private EnemyStorage enemyStorage;

    public GameStorage() {
        this.assetsStorage = new CommonAssetsStorage();
        this.defenderStorage = new DefenderStorage();
        this.enemyStorage = new EnemyStorage();
    }

    public boolean init() {
        if (atlas != null) {
            return true;
        }

        atlas = new TextureAtlas(Gdx.files.internal("atlas/game.atlas"));
        return assetsStorage.init(atlas) && defenderStorage.init(atlas) && enemyStorage.init(atlas);
    }

    public void remove() {
        atlas.dispose();
    }

    public CommonAssets getAssets() {
        return assetsStorage.get();
    }

    public DefenderData getDefender(DefenderType type) {
        return defenderStorage.get(type);
    }

    public EnemyData getEnemy(EnemyType type) {
        return enemyStorage.get(type);
    }
}

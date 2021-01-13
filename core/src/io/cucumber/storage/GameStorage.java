package io.cucumber.storage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import io.cucumber.storage.enemy.EnemyData;
import io.cucumber.storage.enemy.EnemyType;
import io.cucumber.storage.assets.CommonAssets;
import io.cucumber.storage.assets.CommonAssetsStorage;
import io.cucumber.storage.defender.DefenderData;
import io.cucumber.storage.defender.DefenderStorage;
import io.cucumber.storage.defender.DefenderType;
import io.cucumber.storage.enemy.EnemyStorage;

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

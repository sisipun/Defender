package io.cucumber.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import io.cucumber.manager.event.GenerateEnemyTimeEvent;
import io.cucumber.storage.defender.DefenderData;
import io.cucumber.storage.defender.DefenderStorage;
import io.cucumber.storage.defender.DefenderType;
import io.cucumber.storage.enemy.EnemyStorage;
import io.cucumber.storage.enemy.EnemyType;

public class LevelManager {

    private TextureAtlas atlas;
    private Array<LevelInfo> levels;
    private DefenderStorage defenderStorage;
    private EnemyStorage enemyStorage;

    public LevelManager() {
        this.defenderStorage = new DefenderStorage();
        this.enemyStorage = new EnemyStorage();
        this.levels = new Array<>();
    }

    public boolean init() {
        if (!levels.isEmpty()) {
            return true;
        }

        atlas = new TextureAtlas(Gdx.files.internal("atlas/game.atlas"));

        boolean defenderStorageInited = defenderStorage.init(atlas);
        if (!defenderStorageInited) {
            return false;
        }

        boolean enemyStorageInited = enemyStorage.init(atlas);
        if (!enemyStorageInited) {
            return false;
        }

        levels.addAll(
                new LevelInfo(
                        100,
                        60,
                        400,
                        new AssetsInfo("block", "zone", "background", "menu_background"),
                        Array.with(DefenderType.BASE, DefenderType.SMALL),
                        Array.with(EnemyType.BASE, EnemyType.SMALL),
                        Array.with(
                                // TODO move enemy data init to load level method
                                new GenerateEnemyTimeEvent(1, enemyStorage.get(EnemyType.BASE)),
                                new GenerateEnemyTimeEvent(10, enemyStorage.get(EnemyType.SMALL)),
                                new GenerateEnemyTimeEvent(25, enemyStorage.get(EnemyType.BASE)),
                                new GenerateEnemyTimeEvent(40, enemyStorage.get(EnemyType.SMALL))
                        )
                )
        );
        return true;
    }

    public Level loadLevel(int index) {
        LevelInfo levelInfo = levels.get(index);
        if (levelInfo == null) {
            return null;
        }

        Array<DefenderType> defenderTypes = levelInfo.getDefenderTypes();
        Array<DefenderData> defenders = new Array<>(defenderTypes.size);
        for (int i = 0; i < defenderTypes.size; i++) {
            defenders.add(defenderStorage.get(defenderTypes.get(i)));
        }

        return new Level(
                levelInfo.getHealth(),
                levelInfo.getLength(),
                levelInfo.getInitialBalance(),
                new Assets(atlas, levelInfo.getAssets()),
                defenders,
                levelInfo.getTimeEvents()
        );
    }

    public void removeLevel() {
        levels.clear();
        atlas.dispose();
    }
}

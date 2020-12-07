package io.cucumber.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

import io.cucumber.manager.event.GenerateEnemyTimeEvent;
import io.cucumber.storage.defender.DefenderStorage;
import io.cucumber.storage.defender.DefenderType;
import io.cucumber.storage.enemy.EnemyStorage;
import io.cucumber.storage.enemy.EnemyType;

public class LevelManager {

    private TextureAtlas atlas;
    private Array<Level> levels;
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
        Assets assets = new Assets(atlas, "block", "zone", "background", "menu_background",
                "health", "health_background");

        boolean defenderStorageInited = defenderStorage.init(atlas, assets);
        if (!defenderStorageInited) {
            return false;
        }

        boolean enemyStorageInited = enemyStorage.init(atlas, assets);
        if (!enemyStorageInited) {
            return false;
        }

        Map<Integer, GenerateEnemyTimeEvent> timeEvents = new HashMap<>();
        timeEvents.put(1, new GenerateEnemyTimeEvent(1, enemyStorage.get(EnemyType.BASE)));
        timeEvents.put(10, new GenerateEnemyTimeEvent(10, enemyStorage.get(EnemyType.SMALL)));
        timeEvents.put(25, new GenerateEnemyTimeEvent(25, enemyStorage.get(EnemyType.BASE)));
        timeEvents.put(40, new GenerateEnemyTimeEvent(40, enemyStorage.get(EnemyType.SMALL)));

        levels.addAll(
                new Level(
                        100,
                        45,
                        40,
                        400,
                        assets,
                        Array.with(defenderStorage.get(DefenderType.BASE), defenderStorage.get(DefenderType.SMALL)),
                        timeEvents
                )
        );
        return true;
    }

    public Level loadLevel(int index) {
        return levels.get(index);
    }

    public void removeLevel() {
        levels.clear();
        atlas.dispose();
    }
}

package io.cucumber.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import io.cucumber.manager.event.GenerateEnemyTimeEvent;
import io.cucumber.manager.event.TimeEvent;
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
        Assets assets = new Assets(atlas, "road", "road", "road", "road", "road", "land", "water",
                "building", "zone", "menu_background", "health", "health_background", "timer",
                "timer_background", "generate_enemy");

        boolean defenderStorageInited = defenderStorage.init(atlas, assets);
        if (!defenderStorageInited) {
            return false;
        }

        boolean enemyStorageInited = enemyStorage.init(atlas, assets);
        if (!enemyStorageInited) {
            return false;
        }

        levels.addAll(
                new Level(
                        100,
                        45,
                        30,
                        400,
                        assets,
                        Array.with(defenderStorage.get(DefenderType.BASE), defenderStorage.get(DefenderType.SMALL)),
                        Array.with(
                                (TimeEvent) new GenerateEnemyTimeEvent(1, enemyStorage.get(EnemyType.BASE)),
                                (TimeEvent) new GenerateEnemyTimeEvent(10, enemyStorage.get(EnemyType.SMALL)),
                                (TimeEvent) new GenerateEnemyTimeEvent(25, enemyStorage.get(EnemyType.BASE)),
                                (TimeEvent) new GenerateEnemyTimeEvent(40, enemyStorage.get(EnemyType.SMALL))
                        )
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

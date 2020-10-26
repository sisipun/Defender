package io.cucumber.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

import io.cucumber.storage.defender.DefenderStorage;
import io.cucumber.storage.enemy.EnemyStorage;
import io.cucumber.manager.event.Event;
import io.cucumber.manager.event.GenerateEnemyEvent;
import io.cucumber.storage.defender.DefenderData;
import io.cucumber.storage.defender.DefenderType;
import io.cucumber.storage.enemy.EnemyData;
import io.cucumber.storage.enemy.EnemyType;

import static io.cucumber.utils.constants.Constants.GAME_INITIAL_BALANCE;
import static io.cucumber.utils.constants.Constants.GAME_LENGTH;

public class LevelManager {

    private TextureAtlas atlas;
    private Level level;
    private DefenderStorage defenderStorage;
    private EnemyStorage enemyStorage;

    public LevelManager() {
        this.defenderStorage = new DefenderStorage();
        this.enemyStorage = new EnemyStorage();
    }

    public Level init() {
        if (level != null) {
            return level;
        }

        atlas = new TextureAtlas(Gdx.files.internal("atlas/game.atlas"));
        CommonAssets assets = new CommonAssets(atlas, "block", "zone", "background",
                "menu_background");

        defenderStorage.init(atlas);
        Array<DefenderData> defenders = defenderStorage.get(Array.with(
                DefenderType.BASE,
                DefenderType.SMALL
        ));

        enemyStorage.init(atlas);
        EnemyData baseEnemyData = enemyStorage.get(EnemyType.BASE);
        EnemyData smallEnemyData = enemyStorage.get(EnemyType.SMALL);

        Map<Integer, Event> timeEvents = new HashMap<>();
        timeEvents.put(1, new GenerateEnemyEvent(baseEnemyData));
        timeEvents.put(10, new GenerateEnemyEvent(smallEnemyData));
        timeEvents.put(25, new GenerateEnemyEvent(baseEnemyData));
        timeEvents.put(40, new GenerateEnemyEvent(smallEnemyData));

        level = new Level(GAME_LENGTH, GAME_INITIAL_BALANCE, assets, defenders, timeEvents);
        return level;
    }

    public void removeLevel() {
        level = null;
        atlas.dispose();
    }
}

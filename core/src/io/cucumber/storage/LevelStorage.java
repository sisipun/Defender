package io.cucumber.storage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

import io.cucumber.storage.event.Event;
import io.cucumber.storage.event.EventType;
import io.cucumber.storage.model.CommonAssets;
import io.cucumber.storage.model.DefenderData;
import io.cucumber.storage.model.DefenderType;
import io.cucumber.storage.model.Level;

import static io.cucumber.utils.constants.Constants.GAME_INITIAL_BALANCE;
import static io.cucumber.utils.constants.Constants.GAME_LENGTH;

public class LevelStorage {

    private TextureAtlas atlas;
    private Level level;
    private DefenderStorage defenderStorage;

    public LevelStorage() {
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
        Array<DefenderData> defenders = defenderStorage.get(Array.with(DefenderType.BASE));

        Map<Integer, Event> timeEvents = new HashMap<>();
        timeEvents.put(1, new Event(EventType.GENERATE_ENEMY));
        timeEvents.put(10, new Event(EventType.GENERATE_ENEMY));
        timeEvents.put(25, new Event(EventType.GENERATE_ENEMY));
        timeEvents.put(40, new Event(EventType.GENERATE_ENEMY));

        level = new Level(GAME_LENGTH, GAME_INITIAL_BALANCE, assets, defenders, timeEvents);
        return level;
    }

    public void removeLevel() {
        level = null;
        atlas.dispose();
    }
}

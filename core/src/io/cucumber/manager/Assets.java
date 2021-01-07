package io.cucumber.manager;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

import io.cucumber.utils.generator.AreaBlockType;
import io.cucumber.manager.event.TimeEventType;

public class Assets {

    private final TextureRegion zone;
    private final TextureRegion menuBackground;
    private final TextureRegion health;
    private final TextureRegion healthBackground;
    private final TextureRegion timer;
    private final TextureRegion timerBackground;
    private final Map<AreaBlockType, TextureRegion> areaTextures;
    private final Map<TimeEventType, TextureRegion> timeEventTextures;

    public Assets(TextureAtlas atlas, String roadUp, String roadDown, String roadLeft,
                  String roadRight, String roadHorizontalRandom, String roadEnd, String land,
                  String water, String building, String zone, String menuBackground, String health,
                  String healthBackground, String timer, String timerBackground,
                  String generateEnemy, String increaseBalance) {
        this.zone = atlas.findRegion(zone);
        this.menuBackground = atlas.findRegion(menuBackground);
        this.health = atlas.findRegion(health);
        this.healthBackground = atlas.findRegion(healthBackground);
        this.timer = atlas.findRegion(timer);
        this.timerBackground = atlas.findRegion(timerBackground);
        this.areaTextures = new HashMap<>();
        this.areaTextures.put(AreaBlockType.ROAD_UP, atlas.findRegion(roadUp));
        this.areaTextures.put(AreaBlockType.ROAD_DOWN, atlas.findRegion(roadDown));
        this.areaTextures.put(AreaBlockType.ROAD_LEFT, atlas.findRegion(roadLeft));
        this.areaTextures.put(AreaBlockType.ROAD_RIGHT, atlas.findRegion(roadRight));
        this.areaTextures.put(AreaBlockType.ROAD_HORIZONTAL_RAND, atlas.findRegion(roadHorizontalRandom));
        this.areaTextures.put(AreaBlockType.ROAD_END, atlas.findRegion(roadEnd));
        this.areaTextures.put(AreaBlockType.LAND, atlas.findRegion(land));
        this.areaTextures.put(AreaBlockType.WATER, atlas.findRegion(water));
        this.areaTextures.put(AreaBlockType.BUILDING, atlas.findRegion(building));
        this.timeEventTextures = new HashMap<>();
        this.timeEventTextures.put(TimeEventType.GENERATE_ENEMY, atlas.findRegion(generateEnemy));
        this.timeEventTextures.put(TimeEventType.INCREASE_BALANCE, atlas.findRegion(increaseBalance));
    }

    public TextureRegion getZone() {
        return zone;
    }

    public TextureRegion getMenuBackground() {
        return menuBackground;
    }

    public TextureRegion getHealth() {
        return health;
    }

    public TextureRegion getHealthBackground() {
        return healthBackground;
    }

    public TextureRegion getTimer() {
        return timer;
    }

    public TextureRegion getTimerBackground() {
        return timerBackground;
    }

    public Map<AreaBlockType, TextureRegion> getAreaTextures() {
        return areaTextures;
    }

    public Map<TimeEventType, TextureRegion> getTimeEventTextures() {
        return timeEventTextures;
    }
}

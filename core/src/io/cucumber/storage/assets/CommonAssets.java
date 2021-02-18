package io.cucumber.storage.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

import io.cucumber.generator.LevelBlockType;
import io.cucumber.generator.event.TimeEventType;

public class CommonAssets {

    private final TextureRegion zone;
    private final TextureRegion menuBackground;
    private final TextureRegion health;
    private final TextureRegion healthBackground;
    private final TextureRegion timer;
    private final TextureRegion timerBackground;
    private final Map<LevelBlockType, Array<? extends TextureRegion>> areaTextures;
    private final Map<TimeEventType, TextureRegion> timeEventTextures;

    public CommonAssets(TextureAtlas atlas, String roadVertical, String roadHorizontal,
                        String roadTopLeft, String roadTopRight, String roadLeftDown,
                        String roadRightDown, String roadTopHorizontal, String land,
                        String landWaterTop, String landWaterBottom, String landWaterLeft,
                        String landWaterRight, String landWaterTopLeft, String landWaterTopRight,
                        String landWaterBottomLeft, String landWaterBottomRight, String water,
                        String building, String zone, String menuBackground, String health,
                        String healthBackground, String timer, String timerBackground,
                        String generateEnemy, String increaseBalance) {
        this.zone = atlas.findRegion(zone);
        this.menuBackground = atlas.findRegion(menuBackground);
        this.health = atlas.findRegion(health);
        this.healthBackground = atlas.findRegion(healthBackground);
        this.timer = atlas.findRegion(timer);
        this.timerBackground = atlas.findRegion(timerBackground);
        this.areaTextures = new HashMap<>();
        this.areaTextures.put(LevelBlockType.ROAD_DOWN, atlas.findRegions(roadVertical));
        this.areaTextures.put(LevelBlockType.ROAD_LEFT, atlas.findRegions(roadHorizontal));
        this.areaTextures.put(LevelBlockType.ROAD_RIGHT, atlas.findRegions(roadHorizontal));
        this.areaTextures.put(LevelBlockType.ROAD_TOP_LEFT, atlas.findRegions(roadTopLeft));
        this.areaTextures.put(LevelBlockType.ROAD_TOP_RIGHT, atlas.findRegions(roadTopRight));
        this.areaTextures.put(LevelBlockType.ROAD_LEFT_DOWN, atlas.findRegions(roadLeftDown));
        this.areaTextures.put(LevelBlockType.ROAD_RIGHT_DOWN, atlas.findRegions(roadRightDown));
        this.areaTextures.put(LevelBlockType.ROAD_HORIZONTAL_RAND, atlas.findRegions(roadTopHorizontal));
        this.areaTextures.put(LevelBlockType.ROAD_END, atlas.findRegions(roadVertical));
        this.areaTextures.put(LevelBlockType.LAND, atlas.findRegions(land));
        this.areaTextures.put(LevelBlockType.LAND_WATER_TOP, atlas.findRegions(landWaterTop));
        this.areaTextures.put(LevelBlockType.LAND_WATER_BOTTOM, atlas.findRegions(landWaterBottom));
        this.areaTextures.put(LevelBlockType.LAND_WATER_LEFT, atlas.findRegions(landWaterLeft));
        this.areaTextures.put(LevelBlockType.LAND_WATER_RIGHT, atlas.findRegions(landWaterRight));
        this.areaTextures.put(LevelBlockType.LAND_WATER_TOP_LEFT, atlas.findRegions(landWaterTopLeft));
        this.areaTextures.put(LevelBlockType.LAND_WATER_TOP_RIGHT, atlas.findRegions(landWaterTopRight));
        this.areaTextures.put(LevelBlockType.LAND_WATER_BOTTOM_LEFT, atlas.findRegions(landWaterBottomLeft));
        this.areaTextures.put(LevelBlockType.LAND_WATER_BOTTOM_RIGHT, atlas.findRegions(landWaterBottomRight));
        this.areaTextures.put(LevelBlockType.WATER, atlas.findRegions(water));
        this.areaTextures.put(LevelBlockType.BUILDING, atlas.findRegions(building));
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

    public Map<LevelBlockType, Array<? extends TextureRegion>> getAreaTextures() {
        return areaTextures;
    }

    public Map<TimeEventType, TextureRegion> getTimeEventTextures() {
        return timeEventTextures;
    }
}

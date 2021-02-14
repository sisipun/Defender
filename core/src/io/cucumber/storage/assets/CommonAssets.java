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

    public CommonAssets(TextureAtlas atlas, String roadUp, String roadDown, String roadLeft,
                        String roadRight, String roadHorizontalRandom, String roadEnd, String land,
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
        this.areaTextures.put(LevelBlockType.ROAD_UP, Array.with(atlas.findRegion(roadUp)));
        this.areaTextures.put(LevelBlockType.ROAD_DOWN, Array.with(atlas.findRegion(roadDown)));
        this.areaTextures.put(LevelBlockType.ROAD_LEFT, Array.with(atlas.findRegion(roadLeft)));
        this.areaTextures.put(LevelBlockType.ROAD_RIGHT, Array.with(atlas.findRegion(roadRight)));
        this.areaTextures.put(LevelBlockType.ROAD_HORIZONTAL_RAND, Array.with(atlas.findRegion(roadHorizontalRandom)));
        this.areaTextures.put(LevelBlockType.ROAD_END, Array.with(atlas.findRegion(roadEnd)));
        this.areaTextures.put(LevelBlockType.LAND, atlas.findRegions(land));
        this.areaTextures.put(LevelBlockType.LAND_WATER_TOP, atlas.findRegions(landWaterTop));
        this.areaTextures.put(LevelBlockType.LAND_WATER_BOTTOM, atlas.findRegions(landWaterBottom));
        this.areaTextures.put(LevelBlockType.LAND_WATER_LEFT, atlas.findRegions(landWaterLeft));
        this.areaTextures.put(LevelBlockType.LAND_WATER_RIGHT, atlas.findRegions(landWaterRight));
        this.areaTextures.put(LevelBlockType.LAND_WATER_TOP_LEFT, Array.with(atlas.findRegion(landWaterTopLeft)));
        this.areaTextures.put(LevelBlockType.LAND_WATER_TOP_RIGHT, Array.with(atlas.findRegion(landWaterTopRight)));
        this.areaTextures.put(LevelBlockType.LAND_WATER_BOTTOM_LEFT, Array.with(atlas.findRegion(landWaterBottomLeft)));
        this.areaTextures.put(LevelBlockType.LAND_WATER_BOTTOM_RIGHT, Array.with(atlas.findRegion(landWaterBottomRight)));
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

package io.cucumber.manager;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.Map;

import io.cucumber.actor.area.AreaType;

public class Assets {

    private final TextureAtlas.AtlasRegion zone;
    private final TextureAtlas.AtlasRegion menuBackground;
    private final TextureAtlas.AtlasRegion health;
    private final TextureAtlas.AtlasRegion healthBackground;
    private final Map<AreaType, TextureAtlas.AtlasRegion> areaTextures;

    public Assets(TextureAtlas atlas, String roadUp, String roadDown, String roadLeft,
                  String roadRight, String roadEnd, String land, String water, String building,
                  String zone, String menuBackground, String health, String healthBackground) {
        this.zone = atlas.findRegion(zone);
        this.menuBackground = atlas.findRegion(menuBackground);
        this.health = atlas.findRegion(health);
        this.healthBackground = atlas.findRegion(healthBackground);
        this.areaTextures = new HashMap<>();
        this.areaTextures.put(AreaType.ROAD_UP, atlas.findRegion(roadUp));
        this.areaTextures.put(AreaType.ROAD_DOWN, atlas.findRegion(roadDown));
        this.areaTextures.put(AreaType.ROAD_LEFT, atlas.findRegion(roadLeft));
        this.areaTextures.put(AreaType.ROAD_RIGHT, atlas.findRegion(roadRight));
        this.areaTextures.put(AreaType.ROAD_END, atlas.findRegion(roadEnd));
        this.areaTextures.put(AreaType.LAND, atlas.findRegion(land));
        this.areaTextures.put(AreaType.WATER, atlas.findRegion(water));
        this.areaTextures.put(AreaType.BUILDING, atlas.findRegion(building));
    }

    public TextureAtlas.AtlasRegion getZone() {
        return zone;
    }

    public TextureAtlas.AtlasRegion getMenuBackground() {
        return menuBackground;
    }

    public TextureAtlas.AtlasRegion getHealth() {
        return health;
    }

    public TextureAtlas.AtlasRegion getHealthBackground() {
        return healthBackground;
    }

    public TextureAtlas.AtlasRegion getAreaTexture(AreaType type) {
        return areaTextures.get(type);
    }
}

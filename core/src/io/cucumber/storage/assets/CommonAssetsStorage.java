package io.cucumber.storage.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class CommonAssetsStorage {

    private CommonAssets commonAssets;

    public boolean init(TextureAtlas atlas) {
        if (commonAssets != null) {
            return true;
        }

        commonAssets = new CommonAssets(atlas, "road_vertical_part", "road_horizontal_part",
                "road_top_left", "road_top_right", "road_left_down", "road_right_down",
                "road_top_horizontal_part", "grass_part", "water_top_part", "water_bottom_part",
                "water_left_part", "water_right_part", "water_left_top", "water_right_top",
                "water_left_bottom", "water_right_bottom", "water_part", "house_part", "zone",
                "menu_background", "health", "health_background", "timer", "timer_background",
                "generate_enemy", "increase_balance");
        return true;
    }

    public CommonAssets get() {
        return commonAssets;
    }
}

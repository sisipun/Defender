package io.cucumber.storage.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class CommonAssetsStorage {

    private CommonAssets commonAssets;

    public boolean init(TextureAtlas atlas) {
        if (commonAssets != null) {
            return true;
        }

        commonAssets = new CommonAssets(atlas, "road", "road", "road", "road", "road", "road",
                "land", "water", "building", "zone", "menu_background", "health",
                "health_background", "timer", "timer_background", "generate_enemy",
                "increase_balance");
        return true;
    }

    public CommonAssets get() {
        return commonAssets;
    }
}

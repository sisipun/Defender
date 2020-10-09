package io.cucumber.model.level;

import com.badlogic.gdx.utils.Array;

public class Level {

    private final CommonAssets assets;
    private final Array<DefenderData> defenderTypes;

    public Level(CommonAssets assets, Array<DefenderData> defenderTypes) {
        this.assets = assets;
        this.defenderTypes = defenderTypes;
    }

    public CommonAssets getAssets() {
        return assets;
    }

    public Array<DefenderData> getDefenderTypes() {
        return defenderTypes;
    }
}

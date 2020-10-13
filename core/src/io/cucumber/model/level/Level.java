package io.cucumber.model.level;

import com.badlogic.gdx.utils.Array;

public class Level {

    private final float length;
    private final CommonAssets assets;
    private final Array<DefenderData> defenderTypes;

    public Level(float length, CommonAssets assets, Array<DefenderData> defenderTypes) {
        this.length = length;
        this.assets = assets;
        this.defenderTypes = defenderTypes;
    }

    public float getLength() {
        return length;
    }

    public CommonAssets getAssets() {
        return assets;
    }

    public Array<DefenderData> getDefenderTypes() {
        return defenderTypes;
    }
}

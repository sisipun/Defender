package io.cucumber.model.level;

import com.badlogic.gdx.utils.Array;

public class LevelData {

    private final Assets assets;
    private final Array<DefenderSample> defenderSamples;

    public LevelData(Assets assets, Array<DefenderSample> defenderSamples) {
        this.assets = assets;
        this.defenderSamples = defenderSamples;
    }

    public Assets getAssets() {
        return assets;
    }

    public Array<DefenderSample> getDefenderSamples() {
        return defenderSamples;
    }
}

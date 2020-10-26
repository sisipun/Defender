package io.cucumber.storage.defender;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

import static io.cucumber.utils.constants.Constants.DEFENDER_COST;
import static io.cucumber.utils.constants.Constants.DEFENDER_POWER;
import static io.cucumber.utils.constants.Constants.DEFENDER_SIZE;
import static io.cucumber.utils.constants.Constants.DEFENDER_SMALL_COST;
import static io.cucumber.utils.constants.Constants.DEFENDER_SMALL_POWER;
import static io.cucumber.utils.constants.Constants.DEFENDER_SMALL_SIZE;
import static io.cucumber.utils.constants.Constants.DEFENDER_SMALL_ZONE_SIZE;
import static io.cucumber.utils.constants.Constants.DEFENDER_ZONE_SIZE;

public class DefenderStorage {

    private final Map<DefenderType, DefenderData> data;

    public DefenderStorage() {
        this.data = new HashMap<>();
    }

    public void init(TextureAtlas atlas) {
        data.put(DefenderType.BASE, new DefenderData(
                atlas,
                DEFENDER_SIZE,
                DEFENDER_POWER,
                DEFENDER_COST,
                DEFENDER_ZONE_SIZE,
                "defender",
                "enemy"
        ));
        data.put(DefenderType.SMALL, new DefenderData(
                atlas,
                DEFENDER_SMALL_SIZE,
                DEFENDER_SMALL_POWER,
                DEFENDER_SMALL_COST,
                DEFENDER_SMALL_ZONE_SIZE,
                "defender_small",
                "enemy"
        ));
    }

    public Array<DefenderData> get(Array<DefenderType> types) {
        Array<DefenderData> data = new Array<>();
        for (DefenderType type : types) {
            if (this.data.containsKey(type)) {
                data.add(this.data.get(type));
            }
        }

        return data;
    }

    public DefenderData get(DefenderType type) {
        return data.get(type);
    }
}

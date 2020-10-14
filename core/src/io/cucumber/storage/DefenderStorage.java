package io.cucumber.storage;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

import io.cucumber.storage.model.DefenderData;
import io.cucumber.storage.model.DefenderType;

import static io.cucumber.utils.constants.Constants.DEFENDER_POWER;
import static io.cucumber.utils.constants.Constants.DEFENDER_SIZE;
import static io.cucumber.utils.constants.Constants.DEFENDER_ZONE_SIZE;

public class DefenderStorage {

    private final Map<DefenderType, DefenderData> defendersData;

    public DefenderStorage() {
        this.defendersData = new HashMap<>();
    }

    public void init(TextureAtlas atlas) {
        defendersData.put(DefenderType.BASE, new DefenderData(
                atlas,
                DEFENDER_SIZE,
                DEFENDER_POWER,
                DEFENDER_ZONE_SIZE,
                "defender",
                "enemy"
        ));
    }

    public Array<DefenderData> get(Array<DefenderType> types) {
        Array<DefenderData> data = new Array<>();
        for (DefenderType type : types) {
            if (defendersData.containsKey(type)) {
                data.add(defendersData.get(type));
            }
        }

        return data;
    }
}

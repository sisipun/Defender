package io.cucumber.utils.storage.defender;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.Map;

import static io.cucumber.utils.constants.Constants.DEFENDER_BULLET_POWER;
import static io.cucumber.utils.constants.Constants.DEFENDER_BULLET_SIZE;
import static io.cucumber.utils.constants.Constants.DEFENDER_BULLET_SPEED;
import static io.cucumber.utils.constants.Constants.DEFENDER_COST;
import static io.cucumber.utils.constants.Constants.DEFENDER_SIZE;
import static io.cucumber.utils.constants.Constants.DEFENDER_SMALL_BULLET_POWER;
import static io.cucumber.utils.constants.Constants.DEFENDER_SMALL_BULLET_SIZE;
import static io.cucumber.utils.constants.Constants.DEFENDER_SMALL_BULLET_SPEED;
import static io.cucumber.utils.constants.Constants.DEFENDER_SMALL_COST;
import static io.cucumber.utils.constants.Constants.DEFENDER_SMALL_SIZE;
import static io.cucumber.utils.constants.Constants.DEFENDER_SMALL_ZONE_SIZE;
import static io.cucumber.utils.constants.Constants.DEFENDER_ZONE_SIZE;

public class DefenderStorage {

    private final Map<io.cucumber.utils.storage.defender.DefenderType, io.cucumber.utils.storage.defender.DefenderData> data;

    public DefenderStorage() {
        this.data = new HashMap<>();
    }

    public boolean init(TextureAtlas atlas) {
        if (!data.isEmpty()) {
            return true;
        }

        data.put(io.cucumber.utils.storage.defender.DefenderType.BASE, new io.cucumber.utils.storage.defender.DefenderData(
                DEFENDER_SIZE,
                DEFENDER_COST,
                atlas.findRegion("defender"),
                atlas.findRegion("enemy"),
                DEFENDER_ZONE_SIZE,
                atlas.findRegion("zone"),
                DEFENDER_BULLET_SIZE,
                DEFENDER_BULLET_SPEED,
                DEFENDER_BULLET_POWER,
                atlas.findRegion("defender_bullet")
        ));
        data.put(io.cucumber.utils.storage.defender.DefenderType.SMALL, new io.cucumber.utils.storage.defender.DefenderData(
                DEFENDER_SMALL_SIZE,
                DEFENDER_SMALL_COST,
                atlas.findRegion("defender_small"),
                atlas.findRegion("enemy"),
                DEFENDER_SMALL_ZONE_SIZE,
                atlas.findRegion("zone"),
                DEFENDER_SMALL_BULLET_SIZE,
                DEFENDER_SMALL_BULLET_SPEED,
                DEFENDER_SMALL_BULLET_POWER,
                atlas.findRegion("defender_small_bullet")
        ));
        return true;
    }

    public DefenderData get(DefenderType type) {
        return data.get(type);
    }
}

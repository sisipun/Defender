package io.cucumber.storage.enemy;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.Map;

import static io.cucumber.utils.constants.Constants.ENEMY_HEALTH;
import static io.cucumber.utils.constants.Constants.ENEMY_POWER;
import static io.cucumber.utils.constants.Constants.ENEMY_SIZE;
import static io.cucumber.utils.constants.Constants.ENEMY_SMALL_HEALTH;
import static io.cucumber.utils.constants.Constants.ENEMY_SMALL_POWER;
import static io.cucumber.utils.constants.Constants.ENEMY_SMALL_SIZE;
import static io.cucumber.utils.constants.Constants.ENEMY_SMALL_SPEED;
import static io.cucumber.utils.constants.Constants.ENEMY_SPEED;

public class EnemyStorage {

    private final Map<EnemyType, EnemyData> data;

    public EnemyStorage() {
        this.data = new HashMap<>();
    }

    public boolean init(TextureAtlas atlas) {
        if (!data.isEmpty()) {
            return true;
        }

        data.put(EnemyType.BASE, new EnemyData(
                ENEMY_SIZE,
                ENEMY_POWER,
                ENEMY_HEALTH,
                ENEMY_SPEED,
                atlas.findRegion("enemy"),
                atlas.findRegion("health"),
                atlas.findRegion("health_background")
        ));
        data.put(EnemyType.SMALL, new EnemyData(
                ENEMY_SMALL_SIZE,
                ENEMY_SMALL_POWER,
                ENEMY_SMALL_HEALTH,
                ENEMY_SMALL_SPEED,
                atlas.findRegion("enemy_small"),
                atlas.findRegion("health"),
                atlas.findRegion("health_background")
        ));
        return true;
    }

    public EnemyData get(EnemyType type) {
        return data.get(type);
    }
}

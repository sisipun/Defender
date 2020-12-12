package io.cucumber.storage.enemy;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.Map;

import io.cucumber.manager.Assets;

import static io.cucumber.utils.constants.Constants.ENEMY_COST;
import static io.cucumber.utils.constants.Constants.ENEMY_HEALTH;
import static io.cucumber.utils.constants.Constants.ENEMY_POWER;
import static io.cucumber.utils.constants.Constants.ENEMY_SIZE;
import static io.cucumber.utils.constants.Constants.ENEMY_SMALL_COST;
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

    public boolean init(TextureAtlas atlas, Assets assets) {
        data.put(EnemyType.BASE, new EnemyData(
                ENEMY_SIZE,
                ENEMY_POWER,
                ENEMY_HEALTH,
                ENEMY_SPEED,
                ENEMY_COST,
                atlas.findRegion("enemy"),
                assets.getHealth(),
                assets.getHealthBackground()
        ));
        data.put(EnemyType.SMALL, new EnemyData(
                ENEMY_SMALL_SIZE,
                ENEMY_SMALL_POWER,
                ENEMY_SMALL_HEALTH,
                ENEMY_SMALL_SPEED,
                ENEMY_SMALL_COST,
                atlas.findRegion("enemy_small"),
                assets.getHealth(),
                assets.getHealthBackground()
        ));

        return true;
    }

    public EnemyData get(EnemyType type) {
        return data.get(type);
    }
}

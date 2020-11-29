package io.cucumber.storage.enemy;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.Map;

import static io.cucumber.utils.constants.Constants.ENEMY_COST;
import static io.cucumber.utils.constants.Constants.ENEMY_HEALTH;
import static io.cucumber.utils.constants.Constants.ENEMY_POWER;
import static io.cucumber.utils.constants.Constants.ENEMY_SIZE;
import static io.cucumber.utils.constants.Constants.ENEMY_SMALL_COST;
import static io.cucumber.utils.constants.Constants.ENEMY_SMALL_HEALTH;
import static io.cucumber.utils.constants.Constants.ENEMY_SMALL_POWER;
import static io.cucumber.utils.constants.Constants.ENEMY_SMALL_SIZE;
import static io.cucumber.utils.constants.Constants.ENEMY_SMALL_VELOCITY;
import static io.cucumber.utils.constants.Constants.ENEMY_VELOCITY;

public class EnemyStorage {

    private final Map<EnemyType, EnemyData> data;

    public EnemyStorage() {
        this.data = new HashMap<>();
    }

    public boolean init(TextureAtlas atlas) {
        data.put(EnemyType.BASE, new EnemyData(
                ENEMY_SIZE,
                ENEMY_POWER,
                ENEMY_HEALTH,
                ENEMY_VELOCITY,
                ENEMY_COST,
                atlas.findRegion("enemy")
        ));
        data.put(EnemyType.SMALL, new EnemyData(
                ENEMY_SMALL_SIZE,
                ENEMY_SMALL_POWER,
                ENEMY_SMALL_HEALTH,
                ENEMY_SMALL_VELOCITY,
                ENEMY_SMALL_COST,
                atlas.findRegion("enemy_small")
        ));

        return true;
    }

    public EnemyData get(EnemyType type) {
        return data.get(type);
    }
}

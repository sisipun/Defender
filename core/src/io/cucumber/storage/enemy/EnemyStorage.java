package io.cucumber.storage.enemy;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

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

    public void init(TextureAtlas atlas) {
        data.put(EnemyType.BASE, new EnemyData(
                atlas,
                ENEMY_SIZE,
                ENEMY_POWER,
                ENEMY_HEALTH,
                ENEMY_VELOCITY,
                ENEMY_COST,
                "enemy"
        ));
        data.put(EnemyType.SMALL, new EnemyData(
                atlas,
                ENEMY_SMALL_SIZE,
                ENEMY_SMALL_POWER,
                ENEMY_SMALL_HEALTH,
                ENEMY_SMALL_VELOCITY,
                ENEMY_SMALL_COST,
                "enemy_small"
        ));
    }

    public Array<EnemyData> get(Array<EnemyType> types) {
        Array<EnemyData> data = new Array<>();
        for (EnemyType type : types) {
            if (this.data.containsKey(type)) {
                data.add(this.data.get(type));
            }
        }

        return data;
    }

    public EnemyData get(EnemyType type) {
        return data.get(type);
    }
}

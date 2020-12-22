package io.cucumber.actor.area;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

import io.cucumber.actor.Defender;
import io.cucumber.actor.Enemy;
import io.cucumber.actor.ui.menu.DefenderPreview;
import io.cucumber.storage.defender.DefenderData;
import io.cucumber.storage.enemy.EnemyData;

public class GameArea extends Group {

    private Array<AreaBlock> area;
    private Array<Defender> defenders;
    private Array<Enemy> enemies;
    private float topY;
    private float bottomY;

    public GameArea() {
        this.area = new Array<>();
        this.defenders = new Array<>();
        this.enemies = new Array<>();
        this.topY = 0f;
        this.bottomY = 0f;
    }

    public GameArea init(float x, float y, Array<AreaBlock> area) {
        setPosition(x, y);
        removeChildren();

        for (int i = 0; i < area.size; i++) {
            AreaBlock block = area.get(i);
            addActor(block);
            if (block.getY() + block.getHeight() > topY) {
                this.topY = block.getY() + block.getHeight();
            } else if (block.getY() < bottomY) {
                this.bottomY = block.getY();
            }
        }
        this.area.addAll(area);

        return this;
    }

    @Override
    public boolean remove() {
        removeChildren();
        return super.remove();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public boolean addEnemy(float x, float y, EnemyData data) {
        Enemy enemy = Pools.obtain(Enemy.class).init(
                x,
                y,
                data.getSize(),
                data.getSpeed(),
                data.getPower(),
                data.getHealth(),
                data.getCost(),
                data.getTexture(),
                data.getHealthTexture(),
                data.getHealthBackTexture()
        );
        addActor(enemy);
        this.enemies.add(enemy);
        return true;
    }

    public boolean addDefender(float x, float y, DefenderData data) {
        Defender defender = Pools.obtain(Defender.class).init(
                x,
                y,
                data.getSize(),
                data.getCost(),
                data.getAvailableTexture(),
                data.getZoneSize(),
                data.getZoneTexture(),
                data.getBulletSize(),
                data.getBulletSpeed(),
                data.getBulletPower(),
                data.getBulletTexture()
        );
        addActor(defender);
        this.defenders.add(defender);
        return true;
    }

    public boolean isCollides(DefenderPreview preview) {
        for (int i = 0; i < area.size; i++) {
            AreaBlock block = area.get(i);
            if (block.getType() != AreaType.LAND && block.isCollides(preview)) {
                return true;
            }
        }

        for (int i = 0; i < enemies.size; i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.isCollides(preview)) {
                return true;
            }
        }

        for (int i = 0; i < defenders.size; i++) {
            Defender defender = defenders.get(i);
            if (defender.isCollides(preview)) {
                return true;
            }
        }

        return false;
    }

    public Array<Defender> getDefenders() {
        return defenders;
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }

    public Array<AreaBlock> getArea() {
        return area;
    }

    public float getTopY() {
        return topY;
    }

    public float getBottomY() {
        return bottomY;
    }

    private void removeChildren() {
        for (int i = 0; i < this.area.size; i++) {
            AreaBlock areaBlock = this.area.get(i);
            areaBlock.remove();
            Pools.free(areaBlock);
        }

        for (int i = 0; i < this.enemies.size; i++) {
            Enemy enemy = this.enemies.get(i);
            enemy.remove();
            Pools.free(enemy);
        }

        for (int i = 0; i < this.defenders.size; i++) {
            Defender defender = this.defenders.get(i);
            defender.remove();
            Pools.free(defender);
        }

        clearChildren();
        this.area.clear();
        this.defenders.clear();
        this.enemies.clear();
    }
}

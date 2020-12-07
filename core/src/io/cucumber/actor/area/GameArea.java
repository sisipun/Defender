package io.cucumber.actor.area;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

import io.cucumber.actor.Defender;
import io.cucumber.actor.Enemy;
import io.cucumber.actor.menu.preview.DefenderPreview;
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

    public GameArea init(Array<AreaBlock> area) {
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
        Enemy enemy = new Enemy(
                x,
                y,
                data.getSize(),
                data.getSize(),
                data.getVelocity(),
                data.getVelocity(),
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

    public boolean addDefender(float x, float y, DefenderPreview preview) {
        Defender defender = new Defender(
                x,
                y,
                preview.getWidth(),
                preview.getHeight(),
                preview.getPower(),
                preview.getCost(),
                preview.getTexture(),
                preview.getZoneSize(),
                preview.getZoneTexture()
        );
        addActor(defender);
        this.defenders.add(defender);
        return true;
    }

    public boolean isCollides(DefenderPreview preview) {
        for (int i = 0; i < area.size; i++) {
            AreaBlock block = area.get(i);
            if (block.getType() != AreaType.NONE && block.isCollides(preview)) {
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
            this.area.get(i).remove();
        }

        for (int i = 0; i < this.enemies.size; i++) {
            this.enemies.get(i).remove();
        }

        for (int i = 0; i < this.defenders.size; i++) {
            this.defenders.get(i).remove();
        }

        clearChildren();
        this.area.clear();
        this.defenders.clear();
        this.enemies.clear();
    }
}

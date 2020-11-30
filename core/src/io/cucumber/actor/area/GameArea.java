package io.cucumber.actor.area;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

import io.cucumber.actor.Defender;
import io.cucumber.actor.Enemy;
import io.cucumber.actor.menu.preview.DefenderPreview;
import io.cucumber.storage.enemy.EnemyData;

public class GameArea extends Group {

    private float health;
    private int balance;

    private float startPositionX;
    private float startPositionY;

    private Array<AreaBlock> area;
    private Array<Defender> defenders;
    private Array<Enemy> enemies;

    public GameArea(float health, int balance) {
        this.health = health;
        this.balance = balance;
        this.startPositionX = 0;
        this.startPositionY = 0;
        this.area = new Array<>();
        this.defenders = new Array<>();
        this.enemies = new Array<>();
    }

    public GameArea init(float health, int balance, float startPositionX, float startPositionY,
                         Array<AreaBlock> zone) {
        removeChildren();

        this.health = health;
        this.balance = balance;
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
        this.setArea(zone);

        return this;
    }

    @Override
    public boolean remove() {
        removeChildren();
        return super.remove();
    }

    @Override
    public void act(float delta) {
        for (int i = 0; i < enemies.size; i++) {
            Enemy enemy = enemies.get(i);
            for (int j = 0; j < defenders.size; j++) {
                Defender defender = defenders.get(j);
                if (defender.isCollidesZone(enemy)) {
                    enemy.hit(defender.getPower() * delta);
                }
            }

            for (int j = 0; j < area.size; j++) {
                AreaBlock block = area.get(j);
                if (block.isCollidesZone(enemy)) {
                    enemy.changeDirection(block.getType());
                }
            }

            if (enemy.isDead()) {
                balance += enemy.getCost();
                enemies.removeIndex(i);
                enemy.remove();
            }

            if (enemy.isPassed()) {
                health -= enemy.getPower();
                enemies.removeIndex(i);
                enemy.remove();
            }
        }
        super.act(delta);
    }

    public void setArea(Array<AreaBlock> area) {
        this.area.clear();

        for (int i = 0; i < area.size; i++) {
            addActor(area.get(i));
        }

        this.area.addAll(area);
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
                data.getTexture()
        );
        addActor(enemy);
        this.enemies.add(enemy);
        return true;
    }

    public boolean addDefender(float x, float y, DefenderPreview preview) {
        if (balance < preview.getCost()) {
            return false;
        }
        balance -= preview.getCost();

        Defender defender = new Defender(
                x,
                y,
                preview.getWidth(),
                preview.getHeight(),
                preview.getPower(),
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

    public boolean canAdd(DefenderPreview defender) {
        return balance >= defender.getCost();
    }

    public boolean isGameOver() {
        return health <= 0;
    }

    public boolean hasEnemies() {
        return enemies.size > 0;
    }

    public float getStartPositionX() {
        return startPositionX;
    }

    public float getStartPositionY() {
        return startPositionY;
    }

    public Array<AreaBlock> getArea() {
        return area;
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

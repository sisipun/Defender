package io.cucumber.model.character;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.CircleBound;

public class Enemy extends StaticActor<Circle> {

    private int health;

    public Enemy(float x, float y, float size, TextureRegion region, int health) {
        super(new CircleBound(x, y, size / 2), region);
        this.health = health;
    }

    public StaticActor<Circle> init(float x, float y, float size, TextureRegion region, int health) {
        this.health = health;
        return super.init(new CircleBound(x, y, size / 2), region);
    }

    public void hit(int power) {
        health -= power;
    }

    public boolean isDead() {
        return health <= 0;
    }
}

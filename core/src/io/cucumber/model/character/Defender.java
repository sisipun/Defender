package io.cucumber.model.character;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;

public class Defender extends StaticActor<Rectangle> {

    private RectangleBound hitZone;
    private TextureRegion zoneRegion;
    private int power;

    public Defender(float x, float y, float size, TextureRegion region, float zoneSize,
                    TextureRegion zoneRegion, int power) {
        super(new RectangleBound(x, y, size, size), region);
        this.hitZone = new RectangleBound(
                x + size / 2f - zoneSize / 2f,
                y + size / 2f - zoneSize / 2f,
                zoneSize,
                zoneSize
        );
        this.zoneRegion = zoneRegion;
        this.power = power;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO remove after debug
        batch.draw(
                zoneRegion,
                hitZone.getX(),
                hitZone.getY(),
                hitZone.getWidth(),
                hitZone.getHeight()
        );
        super.draw(batch, parentAlpha);
    }

    public boolean isCollidesZone(Enemy enemy) {
        return hitZone.overlaps(enemy.getBound());
    }

    @Override
    public void setX(float x) {
        hitZone.setX(x + getWidth() / 2f - hitZone.getWidth() / 2f);
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        hitZone.setY(y + getHeight() / 2f - hitZone.getHeight() / 2f);
        super.setY(y);
    }

    @Override
    public void setPosition(float x, float y) {
        hitZone.setX(x + getWidth() / 2f - hitZone.getWidth() / 2f);
        hitZone.setY(y + getHeight() / 2f - hitZone.getHeight() / 2f);
        super.setPosition(x, y);
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        hitZone.setX(x + getWidth() / 2 - hitZone.getWidth() / 2);
        hitZone.setY(y + getHeight() / 2 - hitZone.getHeight() / 2);
        super.setPosition(x, y, alignment);
    }

    public float getPower() {
        return power;
    }
}

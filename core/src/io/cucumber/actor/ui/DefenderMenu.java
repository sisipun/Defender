package io.cucumber.actor.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;
import io.cucumber.storage.model.DefenderData;

public class DefenderMenu extends StaticActor<Rectangle> {

    private Array<DefenderMenuItem> items;

    public DefenderMenu(float x, float y, float width, float height, TextureRegion texture,
                        Array<DefenderData> items) {
        super(new RectangleBound(x, y, width, height), texture);
        this.items = new Array<>();
        for (int i = 0; i < items.size; i++) {
            RectangleBound itemBound = new RectangleBound(
                    getX() + i * 3 * getHeight() / 2,
                    getY(),
                    getHeight(),
                    getHeight()
            );
            this.items.add(new DefenderMenuItem(itemBound, items.get(i)));
        }
    }

    public DefenderMenu init(float x, float y, float width, float height, TextureRegion texture,
                             Array<DefenderData> items) {
        super.init(new RectangleBound(x, y, width, height), texture);
        this.items.clear();
        for (int i = 0; i < items.size; i++) {
            RectangleBound itemBound = new RectangleBound(
                    getX() + i * 3 * getHeight() / 2,
                    getY(),
                    getHeight(),
                    getHeight()
            );
            this.items.add(new DefenderMenuItem(itemBound, items.get(i)));
        }
        return this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        for (DefenderMenuItem item : items) {
            item.draw(batch, parentAlpha);
        }
    }

    public DefenderMenuItem getItem(float x, float y) {
        for (int i = 0; i < items.size; i++) {
            DefenderMenuItem item = items.get(i);
            if (item.isContains(x, y)) {
                return item;
            }
        }

        return null;
    }
}

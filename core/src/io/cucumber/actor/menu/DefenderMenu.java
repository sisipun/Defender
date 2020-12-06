package io.cucumber.actor.menu;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

import io.cucumber.actor.menu.preview.DefenderPreview;
import io.cucumber.base.actor.bound.RectangleBound;
import io.cucumber.base.actor.simple.SimpleRectangle;
import io.cucumber.storage.defender.DefenderData;

public class DefenderMenu extends Group {

    private SimpleRectangle background;
    private Array<DefenderMenuItem> items;

    public DefenderMenu(float x, float y, float width, float height, TextureRegion texture,
                        Array<DefenderData> items) {
        this.background = new SimpleRectangle(x, y, width, height, texture);
        addActor(this.background);
        this.items = new Array<>();
        for (int i = 0; i < items.size; i++) {
            RectangleBound itemBound = new RectangleBound(
                    background.getX() + i * 3 * background.getHeight() / 2,
                    background.getY(),
                    background.getHeight(),
                    background.getHeight()
            );
            DefenderMenuItem item = new DefenderMenuItem(itemBound, items.get(i));
            this.items.add(item);
            addActor(item);
        }
    }

    public DefenderMenu init(float x, float y, float width, float height, TextureRegion texture,
                             Array<DefenderData> items) {
        removeChildren();

        this.background.init(x, y, width, height, texture);
        addActor(this.background);
        for (int i = 0; i < items.size; i++) {
            RectangleBound itemBound = new RectangleBound(
                    background.getX() + i * 3 * background.getHeight() / 2,
                    background.getY(),
                    background.getHeight(),
                    background.getHeight()
            );
            DefenderMenuItem item = new DefenderMenuItem(itemBound, items.get(i));
            this.items.add(item);
            addActor(item);
        }

        return this;
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

    public boolean isCollides(DefenderPreview preview) {
        for (int i = 0; i < items.size; i++) {
            DefenderMenuItem item = items.get(i);
            if (item.isCollides(preview)) {
                return true;
            }
        }

        return background.isCollides(preview);
    }

    private void removeChildren() {
        this.background.remove();
        for (int i = 0; i < this.items.size; i++) {
            this.items.get(i).remove();
        }

        clearChildren();
        this.items.clear();
    }
}
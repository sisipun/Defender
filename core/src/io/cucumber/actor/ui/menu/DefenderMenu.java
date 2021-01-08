package io.cucumber.actor.ui.menu;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

import io.cucumber.base.actor.simple.SimpleRectangle;
import io.cucumber.utils.storage.defender.DefenderData;

public class DefenderMenu extends Group {

    private SimpleRectangle background;
    private Array<DefenderMenuItem> items;

    public DefenderMenu() {
        this.background = new SimpleRectangle(0f, 0f, 0f, 0f, null);
        this.items = new Array<>();
    }

    public DefenderMenu init(float x, float y, float width, float height, TextureRegion texture,
                             Array<DefenderData> items) {
        setPosition(x, y);
        removeChildren();

        this.background.init(x, y, width, height, texture);
        addActor(this.background);
        for (int i = 0; i < items.size; i++) {
            DefenderMenuItem item = Pools.obtain(DefenderMenuItem.class).init(
                    background.getX() + i * 3 * background.getHeight() / 2,
                    background.getY(),
                    background.getHeight(),
                    background.getHeight(),
                    items.get(i)
            );
            this.items.add(item);
            addActor(item);
        }

        return this;
    }

    @Override
    public boolean remove() {
        removeChildren();
        return super.remove();
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
            DefenderMenuItem defenderMenuItem = this.items.get(i);
            defenderMenuItem.remove();
            Pools.free(defenderMenuItem);
        }

        clearChildren();
        this.items.clear();
    }
}

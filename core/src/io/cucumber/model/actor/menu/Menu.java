package io.cucumber.model.actor.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;

public class Menu extends StaticActor<Rectangle> {

    private Array<MenuItem> items;

    public Menu(RectangleBound bound, TextureRegion region, Array<TextureRegion> items) {
        super(bound, region);
        this.items = new Array<>();
        for (int i = 0; i < items.size; i++) {
            this.items.add(new MenuItem(new RectangleBound(getX() + i * 3 * bound.getHeight() / 2, getY(),
                    bound.getHeight(), bound.getHeight()), items.get(i)));
        }
    }

    public Menu init(RectangleBound bound, TextureRegion region, Array<TextureRegion> items) {
        super.init(bound, region);
        this.items.clear();
        for (int i = 0; i < items.size; i++) {
            this.items.add(new MenuItem(new RectangleBound(getX() + i * 3 * bound.getHeight() / 2, getY(),
                    bound.getHeight(), bound.getHeight()), items.get(i)));
        }
        return this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        for (int i = 0; i < items.size; i++) {
            items.get(i).draw(batch, parentAlpha);
        }
    }

    @Override
    public boolean remove() {
        for (int i = 0; i < items.size; i++) {
            items.get(i).remove();
        }

        return super.remove();
    }

    public MenuItem getItem(float x, float y) {
        for (int i = 0; i < items.size; i++) {
            MenuItem item = items.get(i);
            if (item.isContains(x, y)) {
                return item;
            }
        }

        return null;
    }
}

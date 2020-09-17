package io.cucumber.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import io.cucumber.base.model.base.StaticActor;
import io.cucumber.base.model.bound.RectangleBound;

public class Menu extends StaticActor<Rectangle> {

    private Array<MenuItem> items;

    public Menu(RectangleBound bound, TextureRegion region, Array<MenuItem> items) {
        super(bound, region);
        this.items = items;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        for (int i = 0; i < items.size; i++) {
            batch.draw(
                    items.get(i).getRegion(),
                    getX() + i * 3 * getHeight() / 2,
                    getY(),
                    getHeight(),
                    getHeight()
            );
        }
    }
}

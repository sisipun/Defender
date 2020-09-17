package io.cucumber.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MenuItem {

    private TextureRegion region;

    public MenuItem(TextureRegion region) {
        this.region = region;
    }

    public TextureRegion getRegion() {
        return region;
    }
}

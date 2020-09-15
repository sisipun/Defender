package io.cucumber.model.font;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontParams {

    private final FreeTypeFontGenerator.FreeTypeFontParameter values;

    public FontParams() {
        this.values = new FreeTypeFontGenerator.FreeTypeFontParameter();
    }

    public FontParams(int size, Color color) {
        this.values = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.values.size = size;
        this.values.color = color;
    }

    public FreeTypeFontGenerator.FreeTypeFontParameter getValues() {
        return values;
    }
}

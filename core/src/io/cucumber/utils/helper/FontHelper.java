package io.cucumber.utils.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import io.cucumber.model.font.FontParams;

public class FontHelper {

    public static BitmapFont toFont(String fontPath, FontParams params) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        BitmapFont font = generator.generateFont(params.getValues());
        generator.dispose();
        return font;
    }

    private FontHelper() {
    }
}

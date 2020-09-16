package io.cucumber.base.utils.helper;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TexturePackerHelper {

    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxWidth = 4096;
        settings.maxHeight = 4096;
        TexturePacker.process(settings, "android/assets/images", "android/assets/atlas", "game");
    }
}

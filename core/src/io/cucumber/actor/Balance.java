package io.cucumber.actor;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import io.cucumber.base.actor.base.TextLabel;

public class Balance extends TextLabel {

    private int value;

    public Balance() {
        super(0f, 0f, "", null);
        this.value = 0;
    }

    public Balance init(float x, float y, BitmapFont font, int value) {
        super.init(x, y, String.valueOf(value), font);
        this.value = value;
        return this;
    }

    public boolean hasBalance(int value) {
        return this.value >= value;
    }

    public void minus(int value) {
        this.value -= value;
        setText(String.valueOf(this.value));
    }

    public void plus(int value) {
        this.value += value;
        setText(String.valueOf(this.value));
    }
}

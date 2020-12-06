package io.cucumber.base.actor.base;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import io.cucumber.base.actor.bound.RectangleBound;

public class TextLabel extends Label {

    private RectangleBound bound;

    public TextLabel(float x, float y, CharSequence text, BitmapFont font) {
        super(text, new Label.LabelStyle(font, font.getColor()));
        this.bound = new RectangleBound(x, y, getPrefWidth(), getPrefHeight());
        setBounds(
                bound.getX(),
                bound.getY(),
                bound.getWidth(),
                bound.getHeight()
        );
    }

    public TextLabel init(float x, float y, CharSequence text, BitmapFont font) {
        setText(text);
        setStyle(new Label.LabelStyle(font, font.getColor()));
        this.bound = new RectangleBound(x, y, getPrefWidth(), getPrefHeight());
        setBounds(
                bound.getX(),
                bound.getY(),
                bound.getWidth(),
                bound.getHeight()
        );
        return this;
    }

    @Override
    public void setText(CharSequence newText) {
        super.setText(newText);
        this.bound.setWidth(getPrefWidth());
        this.bound.setHeight(getPrefHeight());
        setBounds(
                bound.getX(),
                bound.getY(),
                bound.getWidth(),
                bound.getHeight()
        );
    }

    @Override
    public void setX(float x) {
        bound.setX(x);
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        bound.setY(y);
        super.setY(y);
    }

    @Override
    public void setPosition(float x, float y) {
        bound.setPosition(x, y);
        super.setPosition(x, y);
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        bound.setPosition(x, y);
        super.setPosition(x, y, alignment);
    }

    @Override
    public void setWidth(float width) {
        bound.setWidth(width);
        super.setWidth(bound.getWidth());
    }

    @Override
    public void setHeight(float height) {
        bound.setHeight(height);
        super.setHeight(bound.getHeight());
    }

    @Override
    public void moveBy(float x, float y) {
        bound.moveBy(x, y);
        super.moveBy(x, y);
    }
}

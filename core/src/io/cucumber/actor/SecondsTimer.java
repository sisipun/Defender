package io.cucumber.actor;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Timer;

import io.cucumber.base.actor.base.TextLabel;

public class SecondsTimer extends TextLabel {

    private int duration;
    private int value;
    private Timer.Task task;


    public SecondsTimer(float x, float y, BitmapFont font, int duration) {
        super(x, y, String.valueOf(duration), font);
        this.duration = duration;
        this.value = 0;
    }

    public SecondsTimer init(float x, float y, BitmapFont font, int duration) {
        super.init(x, y, String.valueOf(duration), font);
        cancelAction();

        this.duration = duration;
        this.value = 0;
        return this;
    }

    public void scheduleAction(final Runnable action) {
        cancelAction();
        this.task = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (value >= duration) {
                    task.cancel();
                    return;
                }
                action.run();
                value++;
                setText(duration - value);
            }
        }, 0f, 1f);
    }

    public void cancelAction() {
        if (this.task != null) {
            this.task.cancel();
        }
    }

    public boolean isCompleted() {
        return value >= duration;
    }

    public int getValue() {
        return value;
    }
}

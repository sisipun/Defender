package io.cucumber.actor;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Timer;

import java.util.HashMap;
import java.util.Map;

import io.cucumber.base.actor.base.TextLabel;
import io.cucumber.manager.event.TimeEvent;
import io.cucumber.manager.event.TimeEventListener;
import io.cucumber.manager.event.TimeEventType;

public class LevelTimer extends TextLabel {

    private int duration;
    private int value;
    private Map<TimeEventType, TimeEventListener> listeners;
    private Map<Integer, TimeEvent> events;
    private Timer.Task task;


    public LevelTimer(float x, float y, BitmapFont font, int duration, Map<Integer, TimeEvent> events) {
        super(x, y, String.valueOf(duration), font);
        this.duration = duration;
        this.value = 0;
        this.listeners = new HashMap<>();
        this.events = new HashMap<>();
        this.events.putAll(events);
    }

    public LevelTimer init(float x, float y, BitmapFont font, int duration, Map<Integer, TimeEvent> events) {
        super.init(x, y, String.valueOf(duration), font);
        stop();

        this.duration = duration;
        this.value = 0;
        this.listeners.clear();
        this.events.putAll(events);
        return this;
    }

    public void addListener(TimeEventType eventType, TimeEventListener listener) {
        listeners.put(eventType, listener);
    }

    public void start() {
        stop();
        this.task = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                setText(duration - value);
                value++;
                if (value > duration) {
                    stop();
                    return;
                }
                TimeEvent event = events.get(value);
                if (event == null) {
                    return;
                }

                TimeEventListener listener = listeners.get(event.getType());
                if (listener == null) {
                    return;
                }

                listener.handle(event);
            }
        }, 0f, 1f);
    }

    public void stop() {
        if (this.task != null) {
            this.task.cancel();
        }
    }

    public boolean isCompleted() {
        return value >= duration;
    }
}

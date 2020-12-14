package io.cucumber.actor;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import io.cucumber.base.actor.base.TextLabel;
import io.cucumber.manager.event.TimeEvent;
import io.cucumber.manager.event.TimeEventListener;
import io.cucumber.manager.event.TimeEventType;

public class LevelTimer extends TextLabel {

    private int duration;
    private int value;
    private Map<TimeEventType, TimeEventListener> listeners;
    private Queue<TimeEvent> events;
    private Timer.Task task;


    public LevelTimer(float x, float y, BitmapFont font, int duration, Array<? extends TimeEvent> events) {
        super(x, y, String.valueOf(duration), font);
        this.duration = duration;
        this.value = 0;
        this.listeners = new HashMap<>();
        this.events = new PriorityQueue<>(events.size, new Comparator<TimeEvent>() {
            @Override
            public int compare(TimeEvent o1, TimeEvent o2) {
                return o1.getTime() - o2.getTime();
            }
        });
        for (int i = 0; i < events.size; i++) {
            this.events.add(events.get(i));
        }
    }

    public LevelTimer init(float x, float y, BitmapFont font, int duration, Array<? extends TimeEvent> events) {
        super.init(x, y, String.valueOf(duration), font);
        stop();

        this.duration = duration;
        this.value = 0;
        this.listeners.clear();
        this.events.clear();
        for (int i = 0; i < events.size; i++) {
            this.events.add(events.get(i));
        }
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
                TimeEvent event = events.peek();
                if (event == null || event.getTime() != value) {
                    return;
                }

                while (event != null && event.getTime() == value) {
                    TimeEventListener listener = listeners.get(event.getType());
                    if (listener == null) {
                        return;
                    }

                    listener.handle(event);
                    events.poll();
                    event = events.peek();
                }

            }
        }, 0f, 1f);
    }

    public void stop() {
        if (this.task != null) {
            this.task.cancel();
        }
    }

    public boolean isCompleted() {
        return value >= duration || events.isEmpty();
    }
}

package io.cucumber.actor.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import io.cucumber.base.actor.base.StaticActor;
import io.cucumber.base.actor.bound.RectangleBound;
import io.cucumber.base.actor.simple.SimpleRectangle;
import io.cucumber.generator.event.TimeEvent;
import io.cucumber.generator.event.TimeEventListener;
import io.cucumber.generator.event.TimeEventType;

import static io.cucumber.utils.constants.Constants.TIMER_ALPHA;

public class LevelTimer extends StaticActor<Rectangle> {

    private int duration;
    private int currentValue;
    private TextureRegion texture;
    private float backgroundGup;

    private Queue<TimeEvent> events;
    private Queue<SimpleRectangle> eventHistory;
    private Map<TimeEventType, TimeEventListener> eventListeners;

    private Timer.Task task;


    public LevelTimer() {
        super(new RectangleBound(0f, 0f, 0f, 0f), null);
        this.duration = 0;
        this.currentValue = 0;
        this.texture = null;

        this.events = new PriorityQueue<>(10, new Comparator<TimeEvent>() {
            @Override
            public int compare(TimeEvent o1, TimeEvent o2) {
                return o1.getTime() - o2.getTime();
            }
        });
        this.eventHistory = new ArrayDeque<>();
        this.eventListeners = new HashMap<>();
    }

    public LevelTimer init(float x, float y, float width, float height, TextureRegion texture,
                           TextureRegion backgroundTexture, int duration, Array<TimeEvent> events,
                           float eventHistoryItemSize, float backgroundGup,
                           Map<TimeEventType, TextureRegion> eventHistoryTextures) {
        super.init(new RectangleBound(x, y, width, height), backgroundTexture);
        stop();

        this.duration = duration;
        this.currentValue = 0;
        this.texture = texture;
        this.backgroundGup = backgroundGup;

        this.events.clear();
        this.events.addAll(Arrays.asList(events.toArray()));
        this.eventHistory.clear();
        for (TimeEvent event : this.events) {
            this.eventHistory.add(new SimpleRectangle(
                    getX() + ((1f * event.getTime() - 1) / duration) * getWidth(),
                    getY() + backgroundGup,
                    eventHistoryItemSize,
                    eventHistoryItemSize,
                    eventHistoryTextures.get(event.getType())
            ));
        }
        this.eventListeners.clear();

        return this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = batch.getColor();
        float currentAlpha = color.a;
        batch.setColor(color.r, color.g, color.b, TIMER_ALPHA);
        super.draw(batch, parentAlpha);
        batch.draw(
                texture.getTexture(),
                getX(),
                getY(),
                getOriginX(),
                getOriginY(),
                (1f * currentValue / duration) * getWidth(),
                getHeight(),
                getScaleX(),
                getScaleY(),
                getRotation(),
                texture.getRegionX(),
                texture.getRegionY(),
                texture.getRegionWidth(),
                texture.getRegionHeight(),
                false,
                false
        );
        for (SimpleRectangle eventHistoryItem : eventHistory) {
            eventHistoryItem.draw(batch, parentAlpha);
        }
        batch.setColor(color.r, color.g, color.b, currentAlpha);
    }

    @Override
    public void moveBy(float x, float y) {
        super.moveBy(x, y);
        for (SimpleRectangle eventHistoryItem : eventHistory) {
            eventHistoryItem.moveBy(x, y);
        }
    }

    public void addListener(TimeEventType eventType, TimeEventListener listener) {
        eventListeners.put(eventType, listener);
    }

    public void start() {
        stop();
        this.task = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (currentValue >= duration) {
                    stop();
                    return;
                }
                currentValue++;

                TimeEvent event = events.peek();
                if (event == null || event.getTime() != currentValue) {
                    return;
                }

                while (event != null && event.getTime() == currentValue) {
                    TimeEventListener listener = eventListeners.get(event.getType());
                    if (listener == null) {
                        return;
                    }

                    listener.handle(event);
                    events.poll();
                    eventHistory.poll();
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
        return currentValue >= duration || events.isEmpty();
    }
}

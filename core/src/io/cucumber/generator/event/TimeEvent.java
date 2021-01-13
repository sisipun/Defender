package io.cucumber.generator.event;

public abstract class TimeEvent {

    private final int time;
    private final io.cucumber.generator.event.TimeEventType type;

    public TimeEvent(int time, io.cucumber.generator.event.TimeEventType type) {
        this.time = time;
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public TimeEventType getType() {
        return type;
    }
}

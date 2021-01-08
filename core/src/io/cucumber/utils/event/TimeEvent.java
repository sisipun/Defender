package io.cucumber.utils.event;

public abstract class TimeEvent {

    private final int time;
    private final TimeEventType type;

    public TimeEvent(int time, TimeEventType type) {
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

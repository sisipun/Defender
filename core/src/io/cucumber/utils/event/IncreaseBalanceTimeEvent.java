package io.cucumber.utils.event;

public class IncreaseBalanceTimeEvent extends TimeEvent {

    private final int value;

    public IncreaseBalanceTimeEvent(int time, int value) {
        super(time, TimeEventType.INCREASE_BALANCE);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

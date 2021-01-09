package io.cucumber.utils.random;

public class Random {

    private final java.util.Random random;

    public Random() {
        this.random = new java.util.Random();
    }

    public int nextInt(int from, int to) {
        return to - from == 0 ? 0 : this.random.nextInt(to - from) + from;
    }

    public int nextInt(int from, int to, int step) {
        return nextInt(from / step, to / step) * step;
    }

    public int nextInt(int to) {
        return to == 0 ? 0 : this.random.nextInt(to);
    }
}

package io.cucumber.pool;

import com.badlogic.gdx.utils.Pool;

public abstract class BasePool<T> extends Pool<T> {

    public BasePool(int initialCapacity, int max) {
        super(initialCapacity, max);
    }

    private int obtainCount;

    @Override
    public T obtain() {
        if (obtainCount >= max) {
            throw new EmptyPoolException(String.format("Can't give object. Obtain count: %d, Max count: %d", obtainCount ,max));
        }
        obtainCount++;
        return super.obtain();
    }

    @Override
    public void free(T object) {
        obtainCount--;
        super.free(object);
    }
}

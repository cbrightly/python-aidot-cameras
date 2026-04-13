package org.glassfish.grizzly.memory;

import java.lang.reflect.Array;
import java.util.Arrays;

public abstract class AbstractBufferArray<E> {
    private E[] byteBufferArray;
    protected final Class<E> clazz;
    private PosLim[] initStateArray = new PosLim[4];
    private int size;

    /* access modifiers changed from: protected */
    public abstract int getLimit(E e);

    /* access modifiers changed from: protected */
    public abstract int getPosition(E e);

    /* access modifiers changed from: protected */
    public abstract void setPositionLimit(E e, int i, int i2);

    protected AbstractBufferArray(Class<E> clazz2) {
        this.clazz = clazz2;
        this.byteBufferArray = (Object[]) Array.newInstance(clazz2, 4);
    }

    public void add(E byteBuffer) {
        add(byteBuffer, getPosition(byteBuffer), getLimit(byteBuffer));
    }

    public void add(E byteBuffer, int restorePosition, int restoreLimit) {
        ensureCapacity(1);
        E[] eArr = this.byteBufferArray;
        int i = this.size;
        eArr[i] = byteBuffer;
        PosLim poslim = this.initStateArray[i];
        if (poslim == null) {
            poslim = new PosLim();
            this.initStateArray[this.size] = poslim;
        }
        poslim.initialPosition = getPosition(byteBuffer);
        poslim.initialLimit = getLimit(byteBuffer);
        poslim.restorePosition = restorePosition;
        poslim.restoreLimit = restoreLimit;
        this.size++;
    }

    public E[] getArray() {
        return this.byteBufferArray;
    }

    public void restore() {
        for (int i = 0; i < this.size; i++) {
            PosLim poslim = this.initStateArray[i];
            setPositionLimit(this.byteBufferArray[i], poslim.restorePosition, poslim.restoreLimit);
        }
    }

    public final int getInitialPosition(int idx) {
        return this.initStateArray[idx].initialPosition;
    }

    public int getInitialLimit(int idx) {
        return this.initStateArray[idx].initialLimit;
    }

    public final int getInitialBufferSize(int idx) {
        return getInitialLimit(idx) - getInitialPosition(idx);
    }

    public int size() {
        return this.size;
    }

    private void ensureCapacity(int grow) {
        E[] eArr = this.byteBufferArray;
        int length = eArr.length;
        int i = this.size;
        int diff = length - i;
        if (diff < grow) {
            int newSize = Math.max(i + diff, ((eArr.length * 3) / 2) + 1);
            this.byteBufferArray = Arrays.copyOf(this.byteBufferArray, newSize);
            this.initStateArray = (PosLim[]) Arrays.copyOf(this.initStateArray, newSize);
        }
    }

    public void reset() {
        Arrays.fill(this.byteBufferArray, 0, this.size, (Object) null);
        this.size = 0;
    }

    public void recycle() {
        reset();
    }

    public static final class PosLim {
        int initialLimit;
        int initialPosition;
        int restoreLimit;
        int restorePosition;

        private PosLim() {
        }
    }
}

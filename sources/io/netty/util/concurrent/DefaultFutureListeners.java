package io.netty.util.concurrent;

import java.util.Arrays;

public final class DefaultFutureListeners {
    private GenericFutureListener<? extends Future<?>>[] listeners;
    private int progressiveSize;
    private int size = 2;

    DefaultFutureListeners(GenericFutureListener<? extends Future<?>> first, GenericFutureListener<? extends Future<?>> second) {
        GenericFutureListener<? extends Future<?>>[] genericFutureListenerArr = new GenericFutureListener[2];
        this.listeners = genericFutureListenerArr;
        genericFutureListenerArr[0] = first;
        genericFutureListenerArr[1] = second;
        if (first instanceof GenericProgressiveFutureListener) {
            this.progressiveSize++;
        }
        if (second instanceof GenericProgressiveFutureListener) {
            this.progressiveSize++;
        }
    }

    public void add(GenericFutureListener<? extends Future<?>> l) {
        GenericFutureListener<? extends Future<?>>[] listeners2 = this.listeners;
        int size2 = this.size;
        if (size2 == listeners2.length) {
            GenericFutureListener<? extends Future<?>>[] genericFutureListenerArr = (GenericFutureListener[]) Arrays.copyOf(listeners2, size2 << 1);
            listeners2 = genericFutureListenerArr;
            this.listeners = genericFutureListenerArr;
        }
        listeners2[size2] = l;
        this.size = size2 + 1;
        if (l instanceof GenericProgressiveFutureListener) {
            this.progressiveSize++;
        }
    }

    public void remove(GenericFutureListener<? extends Future<?>> l) {
        GenericFutureListener<? extends Future<?>>[] listeners2 = this.listeners;
        int size2 = this.size;
        for (int i = 0; i < size2; i++) {
            if (listeners2[i] == l) {
                int listenersToMove = (size2 - i) - 1;
                if (listenersToMove > 0) {
                    System.arraycopy(listeners2, i + 1, listeners2, i, listenersToMove);
                }
                int size3 = size2 - 1;
                listeners2[size3] = null;
                this.size = size3;
                if (l instanceof GenericProgressiveFutureListener) {
                    this.progressiveSize--;
                    return;
                }
                return;
            }
        }
    }

    public GenericFutureListener<? extends Future<?>>[] listeners() {
        return this.listeners;
    }

    public int size() {
        return this.size;
    }

    public int progressiveSize() {
        return this.progressiveSize;
    }
}

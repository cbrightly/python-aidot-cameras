package org.glassfish.grizzly.utils;

import org.glassfish.grizzly.utils.PoolableObject;

public interface ObjectPool<E extends PoolableObject> {
    void clear();

    void offer(E e);

    E poll();
}

package org.glassfish.grizzly.memory;

public interface ThreadLocalPool<E> {
    E allocate(int i);

    boolean hasRemaining();

    boolean isLastAllocated(E e);

    E reallocate(E e, int i);

    E reduceLastAllocated(E e);

    boolean release(E e);

    int remaining();

    void reset(E e);

    boolean wantReset(int i);
}

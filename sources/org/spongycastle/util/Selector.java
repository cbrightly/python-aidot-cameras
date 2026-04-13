package org.spongycastle.util;

public interface Selector<T> extends Cloneable {
    boolean P0(T t);

    Object clone();
}

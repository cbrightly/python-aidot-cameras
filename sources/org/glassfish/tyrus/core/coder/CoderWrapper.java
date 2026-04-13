package org.glassfish.tyrus.core.coder;

import jakarta.websocket.Decoder;
import jakarta.websocket.d;

public class CoderWrapper<T> extends CoderAdapter implements Decoder, d {
    private final T coder;
    private final Class<? extends T> coderClass;
    private final Class<?> type;

    public CoderWrapper(Class<? extends T> coderClass2, Class<?> type2) {
        this.coderClass = coderClass2;
        this.coder = null;
        this.type = type2;
    }

    public CoderWrapper(T coder2, Class<?> type2) {
        this.coder = coder2;
        this.coderClass = coder2.getClass();
        this.type = type2;
    }

    public Class<?> getType() {
        return this.type;
    }

    public Class<? extends T> getCoderClass() {
        return this.coderClass;
    }

    public T getCoder() {
        return this.coder;
    }

    public String toString() {
        return "CoderWrapper" + "{coderClass=" + this.coderClass + ", coder=" + this.coder + ", type=" + this.type + '}';
    }
}

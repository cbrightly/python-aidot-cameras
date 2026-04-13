package org.glassfish.grizzly.utils;

public abstract class Holder<E> {
    public abstract E get();

    public static <T> Holder<T> staticHolder(final T value) {
        return new Holder<T>() {
            public T get() {
                return value;
            }
        };
    }

    public static IntHolder staticIntHolder(final int value) {
        return new IntHolder() {
            public int getInt() {
                return value;
            }
        };
    }

    public static <T> LazyHolder<T> lazyHolder(final NullaryFunction<T> factory) {
        return new LazyHolder<T>() {
            /* access modifiers changed from: protected */
            public T evaluate() {
                return factory.evaluate();
            }
        };
    }

    public static LazyIntHolder lazyIntHolder(final NullaryFunction<Integer> factory) {
        return new LazyIntHolder() {
            /* access modifiers changed from: protected */
            public int evaluate() {
                return ((Integer) factory.evaluate()).intValue();
            }
        };
    }

    public String toString() {
        E obj = get();
        if (obj == null) {
            return null;
        }
        return "{" + obj + "}";
    }

    public static abstract class LazyHolder<E> extends Holder<E> {
        private volatile boolean isSet;
        private E value;

        /* access modifiers changed from: protected */
        public abstract E evaluate();

        public final E get() {
            if (this.isSet) {
                return this.value;
            }
            synchronized (this) {
                if (!this.isSet) {
                    this.value = evaluate();
                    this.isSet = true;
                }
            }
            return this.value;
        }
    }

    public static abstract class IntHolder extends Holder<Integer> {
        public abstract int getInt();

        public final Integer get() {
            return Integer.valueOf(getInt());
        }
    }

    public static abstract class LazyIntHolder extends IntHolder {
        private volatile boolean isSet;
        private int value;

        /* access modifiers changed from: protected */
        public abstract int evaluate();

        public final int getInt() {
            if (this.isSet) {
                return this.value;
            }
            synchronized (this) {
                if (!this.isSet) {
                    this.value = evaluate();
                    this.isSet = true;
                }
            }
            return this.value;
        }
    }
}

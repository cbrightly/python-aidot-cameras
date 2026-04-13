package org.glassfish.grizzly;

public interface Interceptor<R> {
    public static final int COMPLETED = 1;
    public static final int DEFAULT = 0;
    public static final int INCOMPLETED = 2;
    public static final int RESET = 4;

    int intercept(int i, Object obj, R r);
}

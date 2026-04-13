package kotlin.reflect;

import kotlin.c;

/* compiled from: KFunction.kt */
public interface f<R> extends b<R>, c<R> {
    boolean isExternal();

    boolean isInfix();

    boolean isInline();

    boolean isOperator();

    boolean isSuspend();
}

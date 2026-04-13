package com.trello.rxlifecycle3;

import io.reactivex.functions.b;
import io.reactivex.functions.f;
import io.reactivex.l;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/* compiled from: RxLifecycle */
public class d {
    @CheckReturnValue
    @Nonnull
    public static <T, R> c<T> a(@Nonnull l<R> lifecycle) {
        return new c<>(lifecycle);
    }

    @CheckReturnValue
    @Nonnull
    public static <T, R> c<T> b(@Nonnull l<R> lifecycle, @Nonnull f<R, R> correspondingEvents) {
        com.trello.rxlifecycle3.internal.a.a(lifecycle, "lifecycle == null");
        com.trello.rxlifecycle3.internal.a.a(correspondingEvents, "correspondingEvents == null");
        return a(c(lifecycle.S(), correspondingEvents));
    }

    private static <R> l<Boolean> c(l<R> lifecycle, f<R, R> correspondingEvents) {
        return l.e(lifecycle.c0(1).G(correspondingEvents), lifecycle.V(1), new a()).N(a.a).t(a.b);
    }

    /* compiled from: RxLifecycle */
    public static final class a implements b<R, R, Boolean> {
        a() {
        }

        /* renamed from: a */
        public Boolean apply(R bindUntilEvent, R lifecycleEvent) {
            return Boolean.valueOf(lifecycleEvent.equals(bindUntilEvent));
        }
    }
}

package io.reactivex.internal.util;

import io.reactivex.exceptions.ProtocolViolationException;
import io.reactivex.internal.functions.b;
import io.reactivex.internal.subscriptions.f;
import io.reactivex.plugins.a;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.c;

/* compiled from: EndConsumerHelper */
public final class d {
    public static boolean c(AtomicReference<c> upstream, c next, Class<?> subscriber) {
        b.e(next, "next is null");
        if (upstream.compareAndSet((Object) null, next)) {
            return true;
        }
        next.cancel();
        if (upstream.get() == f.CANCELLED) {
            return false;
        }
        b(subscriber);
        return false;
    }

    public static String a(String consumer) {
        return "It is not allowed to subscribe with a(n) " + consumer + " multiple times. Please create a fresh instance of " + consumer + " and subscribe that to the target source instead.";
    }

    public static void b(Class<?> consumer) {
        a.q(new ProtocolViolationException(a(consumer.getName())));
    }
}

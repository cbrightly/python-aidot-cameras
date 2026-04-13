package io.reactivex.internal.util;

import io.reactivex.exceptions.CompositeException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ExceptionHelper */
public final class f {
    public static final Throwable a = new a();

    public static RuntimeException d(Throwable error) {
        if (error instanceof Error) {
            throw ((Error) error);
        } else if (error instanceof RuntimeException) {
            return (RuntimeException) error;
        } else {
            return new RuntimeException(error);
        }
    }

    public static <T> boolean a(AtomicReference<Throwable> field, Throwable exception) {
        Throwable current;
        Throwable update;
        do {
            current = field.get();
            if (current == a) {
                return false;
            }
            if (current == null) {
                update = exception;
            } else {
                update = new CompositeException(current, exception);
            }
        } while (!field.compareAndSet(current, update));
        return true;
    }

    public static <T> Throwable b(AtomicReference<Throwable> field) {
        Throwable current = field.get();
        Throwable th = a;
        if (current != th) {
            return field.getAndSet(th);
        }
        return current;
    }

    public static String c(long timeout, TimeUnit unit) {
        return "The source did not signal an event for " + timeout + " " + unit.toString().toLowerCase() + " and has been terminated.";
    }

    /* compiled from: ExceptionHelper */
    public static final class a extends Throwable {
        private static final long serialVersionUID = -4649703670690200604L;

        a() {
            super("No further exceptions");
        }

        public Throwable fillInStackTrace() {
            return this;
        }
    }
}

package io.reactivex.internal.util;

import io.reactivex.q;
import java.io.Serializable;

/* compiled from: NotificationLite */
public enum h {
    COMPLETE;

    /* compiled from: NotificationLite */
    public static final class b implements Serializable {
        private static final long serialVersionUID = -8759979445933046293L;
        final Throwable e;

        b(Throwable e2) {
            this.e = e2;
        }

        public String toString() {
            return "NotificationLite.Error[" + this.e + "]";
        }

        public int hashCode() {
            return this.e.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj instanceof b) {
                return io.reactivex.internal.functions.b.c(this.e, ((b) obj).e);
            }
            return false;
        }
    }

    /* compiled from: NotificationLite */
    public static final class c implements Serializable {
        private static final long serialVersionUID = -1322257508628817540L;
        final org.reactivestreams.c upstream;

        c(org.reactivestreams.c s) {
            this.upstream = s;
        }

        public String toString() {
            return "NotificationLite.Subscription[" + this.upstream + "]";
        }
    }

    /* compiled from: NotificationLite */
    public static final class a implements Serializable {
        private static final long serialVersionUID = -7482590109178395495L;
        final io.reactivex.disposables.b upstream;

        a(io.reactivex.disposables.b d) {
            this.upstream = d;
        }

        public String toString() {
            return "NotificationLite.Disposable[" + this.upstream + "]";
        }
    }

    public static <T> Object next(T value) {
        return value;
    }

    public static Object complete() {
        return COMPLETE;
    }

    public static Object error(Throwable e) {
        return new b(e);
    }

    public static Object subscription(org.reactivestreams.c s) {
        return new c(s);
    }

    public static Object disposable(io.reactivex.disposables.b d) {
        return new a(d);
    }

    public static boolean isComplete(Object o) {
        return o == COMPLETE;
    }

    public static boolean isError(Object o) {
        return o instanceof b;
    }

    public static boolean isSubscription(Object o) {
        return o instanceof c;
    }

    public static boolean isDisposable(Object o) {
        return o instanceof a;
    }

    public static <T> T getValue(Object o) {
        return o;
    }

    public static Throwable getError(Object o) {
        return ((b) o).e;
    }

    public static org.reactivestreams.c getSubscription(Object o) {
        return ((c) o).upstream;
    }

    public static io.reactivex.disposables.b getDisposable(Object o) {
        return ((a) o).upstream;
    }

    public static <T> boolean accept(Object o, org.reactivestreams.b<? super T> s) {
        if (o == COMPLETE) {
            s.onComplete();
            return true;
        } else if (o instanceof b) {
            s.onError(((b) o).e);
            return true;
        } else {
            s.onNext(o);
            return false;
        }
    }

    public static <T> boolean accept(Object o, q<? super T> observer) {
        if (o == COMPLETE) {
            observer.onComplete();
            return true;
        } else if (o instanceof b) {
            observer.onError(((b) o).e);
            return true;
        } else {
            observer.onNext(o);
            return false;
        }
    }

    public static <T> boolean acceptFull(Object o, org.reactivestreams.b<? super T> s) {
        if (o == COMPLETE) {
            s.onComplete();
            return true;
        } else if (o instanceof b) {
            s.onError(((b) o).e);
            return true;
        } else if (o instanceof c) {
            s.onSubscribe(((c) o).upstream);
            return false;
        } else {
            s.onNext(o);
            return false;
        }
    }

    public static <T> boolean acceptFull(Object o, q<? super T> observer) {
        if (o == COMPLETE) {
            observer.onComplete();
            return true;
        } else if (o instanceof b) {
            observer.onError(((b) o).e);
            return true;
        } else if (o instanceof a) {
            observer.onSubscribe(((a) o).upstream);
            return false;
        } else {
            observer.onNext(o);
            return false;
        }
    }

    public String toString() {
        return "NotificationLite.Complete";
    }
}

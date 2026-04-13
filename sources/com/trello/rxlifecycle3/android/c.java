package com.trello.rxlifecycle3.android;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.trello.rxlifecycle3.OutsideLifecycleException;
import com.trello.rxlifecycle3.d;
import io.reactivex.functions.f;
import io.reactivex.l;

/* compiled from: RxLifecycleAndroid */
public class c {
    private static final f<a, a> a = new a();
    private static final f<b, b> b = new b();

    @CheckResult
    @NonNull
    public static <T> com.trello.rxlifecycle3.c<T> a(@NonNull l<a> lifecycle) {
        return d.b(lifecycle, a);
    }

    @CheckResult
    @NonNull
    public static <T> com.trello.rxlifecycle3.c<T> b(@NonNull l<b> lifecycle) {
        return d.b(lifecycle, b);
    }

    /* compiled from: RxLifecycleAndroid */
    public static final class a implements f<a, a> {
        a() {
        }

        /* renamed from: a */
        public a apply(a lastEvent) {
            switch (C0226c.a[lastEvent.ordinal()]) {
                case 1:
                    return a.DESTROY;
                case 2:
                    return a.STOP;
                case 3:
                    return a.PAUSE;
                case 4:
                    return a.STOP;
                case 5:
                    return a.DESTROY;
                case 6:
                    throw new OutsideLifecycleException("Cannot bind to Activity lifecycle when outside of it.");
                default:
                    throw new UnsupportedOperationException("Binding to " + lastEvent + " not yet implemented");
            }
        }
    }

    /* compiled from: RxLifecycleAndroid */
    public static final class b implements f<b, b> {
        b() {
        }

        /* renamed from: a */
        public b apply(b lastEvent) {
            switch (C0226c.b[lastEvent.ordinal()]) {
                case 1:
                    return b.DETACH;
                case 2:
                    return b.DESTROY;
                case 3:
                    return b.DESTROY_VIEW;
                case 4:
                    return b.STOP;
                case 5:
                    return b.PAUSE;
                case 6:
                    return b.STOP;
                case 7:
                    return b.DESTROY_VIEW;
                case 8:
                    return b.DESTROY;
                case 9:
                    return b.DETACH;
                case 10:
                    throw new OutsideLifecycleException("Cannot bind to Fragment lifecycle when outside of it.");
                default:
                    throw new UnsupportedOperationException("Binding to " + lastEvent + " not yet implemented");
            }
        }
    }

    /* renamed from: com.trello.rxlifecycle3.android.c$c  reason: collision with other inner class name */
    /* compiled from: RxLifecycleAndroid */
    public static /* synthetic */ class C0226c {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[b.values().length];
            b = iArr;
            try {
                iArr[b.ATTACH.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                b[b.CREATE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                b[b.CREATE_VIEW.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                b[b.START.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                b[b.RESUME.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                b[b.PAUSE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                b[b.STOP.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                b[b.DESTROY_VIEW.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                b[b.DESTROY.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                b[b.DETACH.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            int[] iArr2 = new int[a.values().length];
            a = iArr2;
            try {
                iArr2[a.CREATE.ordinal()] = 1;
            } catch (NoSuchFieldError e11) {
            }
            try {
                a[a.START.ordinal()] = 2;
            } catch (NoSuchFieldError e12) {
            }
            try {
                a[a.RESUME.ordinal()] = 3;
            } catch (NoSuchFieldError e13) {
            }
            try {
                a[a.PAUSE.ordinal()] = 4;
            } catch (NoSuchFieldError e14) {
            }
            try {
                a[a.STOP.ordinal()] = 5;
            } catch (NoSuchFieldError e15) {
            }
            try {
                a[a.DESTROY.ordinal()] = 6;
            } catch (NoSuchFieldError e16) {
            }
        }
    }
}

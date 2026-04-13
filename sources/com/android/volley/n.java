package com.android.volley;

import android.os.SystemClock;
import android.util.Log;
import androidx.camera.core.CameraInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: VolleyLog */
public class n {
    public static String a = "Volley";
    public static boolean b = Log.isLoggable("Volley", 2);
    private static final String c = n.class.getName();

    public static void e(String format, Object... args) {
        if (b) {
            Log.v(a, a(format, args));
        }
    }

    public static void b(String format, Object... args) {
        Log.d(a, a(format, args));
    }

    public static void c(String format, Object... args) {
        Log.e(a, a(format, args));
    }

    public static void d(Throwable tr, String format, Object... args) {
        Log.e(a, a(format, args), tr);
    }

    public static void f(String format, Object... args) {
        Log.wtf(a, a(format, args));
    }

    private static String a(String format, Object... args) {
        String msg = args == null ? format : String.format(Locale.US, format, args);
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();
        String caller = CameraInfo.IMPLEMENTATION_TYPE_UNKNOWN;
        int i = 2;
        while (true) {
            if (i >= trace.length) {
                break;
            } else if (!trace[i].getClassName().equals(c)) {
                String callingClass = trace[i].getClassName();
                String callingClass2 = callingClass.substring(callingClass.lastIndexOf(46) + 1);
                caller = callingClass2.substring(callingClass2.lastIndexOf(36) + 1) + "." + trace[i].getMethodName();
                break;
            } else {
                i++;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", new Object[]{Long.valueOf(Thread.currentThread().getId()), caller, msg});
    }

    /* compiled from: VolleyLog */
    public static class a {
        public static final boolean a = n.b;
        private final List<C0018a> b = new ArrayList();
        private boolean c = false;

        a() {
        }

        /* renamed from: com.android.volley.n$a$a  reason: collision with other inner class name */
        /* compiled from: VolleyLog */
        public static class C0018a {
            public final String a;
            public final long b;
            public final long c;

            public C0018a(String name, long thread, long time) {
                this.a = name;
                this.b = thread;
                this.c = time;
            }
        }

        public synchronized void a(String name, long threadId) {
            if (!this.c) {
                this.b.add(new C0018a(name, threadId, SystemClock.elapsedRealtime()));
            } else {
                throw new IllegalStateException("Marker added to finished log");
            }
        }

        public synchronized void b(String header) {
            synchronized (this) {
                this.c = true;
                long duration = c();
                if (duration > 0) {
                    long prevTime = this.b.get(0).c;
                    n.b("(%-4d ms) %s", Long.valueOf(duration), header);
                    for (C0018a marker : this.b) {
                        long thisTime = marker.c;
                        n.b("(+%-4d) [%2d] %s", Long.valueOf(thisTime - prevTime), Long.valueOf(marker.b), marker.a);
                        prevTime = thisTime;
                    }
                }
            }
        }

        /* access modifiers changed from: protected */
        public void finalize() {
            if (!this.c) {
                b("Request on the loose");
                n.c("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
            }
        }

        private long c() {
            if (this.b.size() == 0) {
                return 0;
            }
            long first = this.b.get(0).c;
            List<C0018a> list = this.b;
            return list.get(list.size() - 1).c - first;
        }
    }
}

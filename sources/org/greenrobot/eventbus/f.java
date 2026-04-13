package org.greenrobot.eventbus;

import android.util.Log;
import com.meituan.robust.Constants;
import java.io.PrintStream;
import java.util.logging.Level;

/* compiled from: Logger */
public interface f {
    void a(Level level, String str, Throwable th);

    void b(Level level, String str);

    /* compiled from: Logger */
    public static class a implements f {
        static final boolean a;
        private final String b;

        static {
            boolean android2 = false;
            try {
                Class.forName("android.util.Log");
                android2 = true;
            } catch (ClassNotFoundException e) {
            }
            a = android2;
        }

        public static boolean c() {
            return a;
        }

        public a(String tag) {
            this.b = tag;
        }

        public void b(Level level, String msg) {
            if (level != Level.OFF) {
                Log.println(d(level), this.b, msg);
            }
        }

        public void a(Level level, String msg, Throwable th) {
            if (level != Level.OFF) {
                int d = d(level);
                String str = this.b;
                Log.println(d, str, msg + "\n" + Log.getStackTraceString(th));
            }
        }

        /* access modifiers changed from: protected */
        public int d(Level level) {
            int value = level.intValue();
            if (value < 800) {
                if (value < 500) {
                    return 2;
                }
                return 3;
            } else if (value < 900) {
                return 4;
            } else {
                if (value < 1000) {
                    return 5;
                }
                return 6;
            }
        }
    }

    /* compiled from: Logger */
    public static class b implements f {
        public void b(Level level, String msg) {
            PrintStream printStream = System.out;
            printStream.println(Constants.ARRAY_TYPE + level + "] " + msg);
        }

        public void a(Level level, String msg, Throwable th) {
            PrintStream printStream = System.out;
            printStream.println(Constants.ARRAY_TYPE + level + "] " + msg);
            th.printStackTrace(System.out);
        }
    }
}

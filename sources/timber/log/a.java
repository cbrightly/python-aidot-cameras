package timber.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Timber */
public final class a {
    private static final b[] a;
    private static final List<b> b = new ArrayList();
    static volatile b[] c;
    private static final b d = new C0496a();

    public static void i(@NonNls String message, Object... args) {
        d.m(message, args);
    }

    public static void a(@NonNls String message, Object... args) {
        d.a(message, args);
    }

    public static void b(Throwable t) {
        d.b(t);
    }

    public static void e(@NonNls String message, Object... args) {
        d.h(message, args);
    }

    public static void j(@NonNls String message, Object... args) {
        d.n(message, args);
    }

    public static void c(@NonNls String message, Object... args) {
        d.c(message, args);
    }

    public static void d(Throwable t) {
        d.d(t);
    }

    @NotNull
    public static b g(String tag) {
        for (b tree : c) {
            tree.a.set(tag);
        }
        return d;
    }

    public static void f(@NotNull b tree) {
        if (tree == null) {
            throw new NullPointerException("tree == null");
        } else if (tree != d) {
            List<b> list = b;
            synchronized (list) {
                list.add(tree);
                c = (b[]) list.toArray(new b[list.size()]);
            }
        } else {
            throw new IllegalArgumentException("Cannot plant Timber into itself.");
        }
    }

    public static int h() {
        int size;
        List<b> list = b;
        synchronized (list) {
            size = list.size();
        }
        return size;
    }

    static {
        b[] bVarArr = new b[0];
        a = bVarArr;
        c = bVarArr;
    }

    /* renamed from: timber.log.a$a  reason: collision with other inner class name */
    /* compiled from: Timber */
    public static final class C0496a extends b {
        C0496a() {
        }

        public void m(String message, Object... args) {
            for (b tree : a.c) {
                tree.m(message, args);
            }
        }

        public void a(String message, Object... args) {
            for (b tree : a.c) {
                tree.a(message, args);
            }
        }

        public void b(Throwable t) {
            for (b tree : a.c) {
                tree.b(t);
            }
        }

        public void h(String message, Object... args) {
            for (b tree : a.c) {
                tree.h(message, args);
            }
        }

        public void n(String message, Object... args) {
            for (b tree : a.c) {
                tree.n(message, args);
            }
        }

        public void c(String message, Object... args) {
            for (b tree : a.c) {
                tree.c(message, args);
            }
        }

        public void d(Throwable t) {
            for (b tree : a.c) {
                tree.d(t);
            }
        }

        /* access modifiers changed from: protected */
        public void k(int priority, String tag, @NotNull String message, Throwable t) {
            throw new AssertionError("Missing override for log method.");
        }
    }

    /* compiled from: Timber */
    public static abstract class b {
        final ThreadLocal<String> a = new ThreadLocal<>();

        /* access modifiers changed from: protected */
        public abstract void k(int i, @Nullable String str, @NotNull String str2, @Nullable Throwable th);

        /* access modifiers changed from: package-private */
        @Nullable
        public String g() {
            String tag = this.a.get();
            if (tag != null) {
                this.a.remove();
            }
            return tag;
        }

        public void m(String message, Object... args) {
            l(2, (Throwable) null, message, args);
        }

        public void a(String message, Object... args) {
            l(3, (Throwable) null, message, args);
        }

        public void b(Throwable t) {
            l(3, t, (String) null, new Object[0]);
        }

        public void h(String message, Object... args) {
            l(4, (Throwable) null, message, args);
        }

        public void n(String message, Object... args) {
            l(5, (Throwable) null, message, args);
        }

        public void c(String message, Object... args) {
            l(6, (Throwable) null, message, args);
        }

        public void d(Throwable t) {
            l(6, t, (String) null, new Object[0]);
        }

        /* access modifiers changed from: protected */
        @Deprecated
        public boolean i(int priority) {
            return true;
        }

        /* access modifiers changed from: protected */
        public boolean j(@Nullable String tag, int priority) {
            return i(priority);
        }

        private void l(int priority, Throwable t, String message, Object... args) {
            String tag = g();
            if (j(tag, priority)) {
                if (message != null && message.length() == 0) {
                    message = null;
                }
                if (message != null) {
                    if (args != null && args.length > 0) {
                        message = e(message, args);
                    }
                    if (t != null) {
                        message = message + "\n" + f(t);
                    }
                } else if (t != null) {
                    message = f(t);
                } else {
                    return;
                }
                k(priority, tag, message, t);
            }
        }

        /* access modifiers changed from: protected */
        public String e(@NotNull String message, @NotNull Object[] args) {
            return String.format(message, args);
        }

        private String f(Throwable t) {
            StringWriter sw = new StringWriter(256);
            PrintWriter pw = new PrintWriter(sw, false);
            t.printStackTrace(pw);
            pw.flush();
            return sw.toString();
        }
    }
}

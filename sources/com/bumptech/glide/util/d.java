package com.bumptech.glide.util;

import androidx.annotation.NonNull;
import java.util.concurrent.Executor;

/* compiled from: Executors */
public final class d {
    private static final Executor a = new a();
    private static final Executor b = new b();

    /* compiled from: Executors */
    public class a implements Executor {
        a() {
        }

        public void execute(@NonNull Runnable command) {
            j.u(command);
        }
    }

    /* compiled from: Executors */
    public class b implements Executor {
        b() {
        }

        public void execute(@NonNull Runnable command) {
            command.run();
        }
    }

    public static Executor b() {
        return a;
    }

    public static Executor a() {
        return b;
    }
}

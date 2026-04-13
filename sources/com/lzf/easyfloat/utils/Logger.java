package com.lzf.easyfloat.utils;

import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Logger.kt */
public final class Logger {
    @NotNull
    public static final Logger INSTANCE = new Logger();
    private static boolean logEnable;
    @NotNull
    private static String tag = "EasyFloat--->";

    private Logger() {
    }

    public final void i(@NotNull Object msg) {
        k.e(msg, NotificationCompat.CATEGORY_MESSAGE);
        i(tag, msg.toString());
    }

    public final void v(@NotNull Object msg) {
        k.e(msg, NotificationCompat.CATEGORY_MESSAGE);
        v(tag, msg.toString());
    }

    public final void d(@NotNull Object msg) {
        k.e(msg, NotificationCompat.CATEGORY_MESSAGE);
        d(tag, msg.toString());
    }

    public final void w(@NotNull Object msg) {
        k.e(msg, NotificationCompat.CATEGORY_MESSAGE);
        w(tag, msg.toString());
    }

    public final void e(@NotNull Object msg) {
        k.e(msg, NotificationCompat.CATEGORY_MESSAGE);
        e(tag, msg.toString());
    }

    public final void i(@NotNull String tag2, @NotNull String msg) {
        k.e(tag2, Progress.TAG);
        k.e(msg, NotificationCompat.CATEGORY_MESSAGE);
        if (logEnable) {
            Log.i(tag2, msg);
        }
    }

    public final void v(@NotNull String tag2, @NotNull String msg) {
        k.e(tag2, Progress.TAG);
        k.e(msg, NotificationCompat.CATEGORY_MESSAGE);
        if (logEnable) {
            Log.v(tag2, msg);
        }
    }

    public final void d(@NotNull String tag2, @NotNull String msg) {
        k.e(tag2, Progress.TAG);
        k.e(msg, NotificationCompat.CATEGORY_MESSAGE);
        if (logEnable) {
            Log.d(tag2, msg);
        }
    }

    public final void w(@NotNull String tag2, @NotNull String msg) {
        k.e(tag2, Progress.TAG);
        k.e(msg, NotificationCompat.CATEGORY_MESSAGE);
        if (logEnable) {
            Log.w(tag2, msg);
        }
    }

    public final void e(@NotNull String tag2, @NotNull String msg) {
        k.e(tag2, Progress.TAG);
        k.e(msg, NotificationCompat.CATEGORY_MESSAGE);
        if (logEnable) {
            Log.e(tag2, msg);
        }
    }
}

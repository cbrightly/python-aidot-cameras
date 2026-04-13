package com.petterp.floatingx.util;

import android.util.Log;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: FxLog.kt */
public final class c {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    @NotNull
    public static String b = "FloatingX";
    @NotNull
    private final String c;

    public /* synthetic */ c(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private c(String tag) {
        this.c = tag;
    }

    /* compiled from: FxLog.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        @NotNull
        public final c a(@NotNull String tag) {
            k.e(tag, Progress.TAG);
            return new c(c.b + '-' + tag, (DefaultConstructorMarker) null);
        }
    }

    public final void b(@NotNull String message) {
        k.e(message, "message");
        Log.d(this.c, message);
    }

    public final void d(@NotNull String message) {
        k.e(message, "message");
        Log.v(this.c, message);
    }

    public final void c(@NotNull String message) {
        k.e(message, "message");
        Log.e(this.c, message);
    }
}

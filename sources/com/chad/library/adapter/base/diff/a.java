package com.chad.library.adapter.base.diff;

import android.os.Handler;
import android.os.Looper;
import androidx.recyclerview.widget.ListUpdateCallback;
import com.chad.library.adapter.base.BaseQuickAdapter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: BrvahAsyncDiffer.kt */
public final class a<T> {
    private final ListUpdateCallback a;
    private Executor b;
    private final Executor c;
    private final List<?> d;
    private final BaseQuickAdapter<T, ?> e;
    private final b<T> f;

    public a(@NotNull BaseQuickAdapter<T, ?> adapter, @NotNull b<T> config) {
        k.e(adapter, "adapter");
        k.e(config, "config");
        this.e = adapter;
        this.f = config;
        this.a = new BrvahListUpdateCallback(adapter);
        Executor aVar = new C0058a();
        this.c = aVar;
        Executor a2 = config.a();
        this.b = a2 != null ? a2 : aVar;
        this.d = new CopyOnWriteArrayList();
    }

    /* renamed from: com.chad.library.adapter.base.diff.a$a  reason: collision with other inner class name */
    /* compiled from: BrvahAsyncDiffer.kt */
    public static final class C0058a implements Executor {
        @NotNull
        private final Handler c = new Handler(Looper.getMainLooper());

        public void execute(@NotNull Runnable command) {
            k.e(command, "command");
            this.c.post(command);
        }
    }
}

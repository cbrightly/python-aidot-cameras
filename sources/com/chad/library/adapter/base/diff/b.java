package com.chad.library.adapter.base.diff;

import androidx.annotation.RestrictTo;
import androidx.recyclerview.widget.DiffUtil;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BrvahAsyncDifferConfig.kt */
public final class b<T> {
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    private final Executor a;
    @NotNull
    private final Executor b;
    @NotNull
    private final DiffUtil.ItemCallback<T> c;

    public b(@Nullable Executor mainThreadExecutor, @NotNull Executor backgroundThreadExecutor, @NotNull DiffUtil.ItemCallback<T> diffCallback) {
        k.e(backgroundThreadExecutor, "backgroundThreadExecutor");
        k.e(diffCallback, "diffCallback");
        this.a = mainThreadExecutor;
        this.b = backgroundThreadExecutor;
        this.c = diffCallback;
    }

    @Nullable
    public final Executor a() {
        return this.a;
    }

    /* compiled from: BrvahAsyncDifferConfig.kt */
    public static final class a<T> {
        private static final Object a = new Object();
        private static Executor b;
        public static final C0059a c = new C0059a((DefaultConstructorMarker) null);
        private Executor d;
        private Executor e;
        private final DiffUtil.ItemCallback<T> f;

        public a(@NotNull DiffUtil.ItemCallback<T> mDiffCallback) {
            k.e(mDiffCallback, "mDiffCallback");
            this.f = mDiffCallback;
        }

        @NotNull
        public final b<T> a() {
            if (this.e == null) {
                synchronized (a) {
                    if (b == null) {
                        b = Executors.newFixedThreadPool(2);
                    }
                    x xVar = x.a;
                }
                this.e = b;
            }
            Executor executor = this.d;
            Executor executor2 = this.e;
            k.c(executor2);
            return new b<>(executor, executor2, this.f);
        }

        /* renamed from: com.chad.library.adapter.base.diff.b$a$a  reason: collision with other inner class name */
        /* compiled from: BrvahAsyncDifferConfig.kt */
        public static final class C0059a {
            private C0059a() {
            }

            public /* synthetic */ C0059a(DefaultConstructorMarker $constructor_marker) {
                this();
            }
        }
    }
}

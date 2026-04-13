package com.didichuxing.doraemonkit.widget.bravh.diff;

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
public final class BrvahAsyncDifferConfig<T> {
    @NotNull
    private final Executor backgroundThreadExecutor;
    @NotNull
    private final DiffUtil.ItemCallback<T> diffCallback;
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    private final Executor mainThreadExecutor;

    public BrvahAsyncDifferConfig(@Nullable Executor mainThreadExecutor2, @NotNull Executor backgroundThreadExecutor2, @NotNull DiffUtil.ItemCallback<T> diffCallback2) {
        k.f(backgroundThreadExecutor2, "backgroundThreadExecutor");
        k.f(diffCallback2, "diffCallback");
        this.mainThreadExecutor = mainThreadExecutor2;
        this.backgroundThreadExecutor = backgroundThreadExecutor2;
        this.diffCallback = diffCallback2;
    }

    @Nullable
    public final Executor getMainThreadExecutor() {
        return this.mainThreadExecutor;
    }

    @NotNull
    public final Executor getBackgroundThreadExecutor() {
        return this.backgroundThreadExecutor;
    }

    @NotNull
    public final DiffUtil.ItemCallback<T> getDiffCallback() {
        return this.diffCallback;
    }

    /* compiled from: BrvahAsyncDifferConfig.kt */
    public static final class Builder<T> {
        public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
        private static Executor sDiffExecutor;
        private static final Object sExecutorLock = new Object();
        private Executor mBackgroundThreadExecutor;
        private final DiffUtil.ItemCallback<T> mDiffCallback;
        private Executor mMainThreadExecutor;

        public Builder(@NotNull DiffUtil.ItemCallback<T> mDiffCallback2) {
            k.f(mDiffCallback2, "mDiffCallback");
            this.mDiffCallback = mDiffCallback2;
        }

        @NotNull
        public final Builder<T> setMainThreadExecutor(@Nullable Executor executor) {
            this.mMainThreadExecutor = executor;
            return this;
        }

        @NotNull
        public final Builder<T> setBackgroundThreadExecutor(@Nullable Executor executor) {
            this.mBackgroundThreadExecutor = executor;
            return this;
        }

        @NotNull
        public final BrvahAsyncDifferConfig<T> build() {
            if (this.mBackgroundThreadExecutor == null) {
                synchronized (sExecutorLock) {
                    if (sDiffExecutor == null) {
                        sDiffExecutor = Executors.newFixedThreadPool(2);
                    }
                    x xVar = x.a;
                }
                this.mBackgroundThreadExecutor = sDiffExecutor;
            }
            Executor executor = this.mMainThreadExecutor;
            Executor executor2 = this.mBackgroundThreadExecutor;
            if (executor2 == null) {
                k.n();
            }
            return new BrvahAsyncDifferConfig<>(executor, executor2, this.mDiffCallback);
        }

        /* compiled from: BrvahAsyncDifferConfig.kt */
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
                this();
            }
        }
    }
}

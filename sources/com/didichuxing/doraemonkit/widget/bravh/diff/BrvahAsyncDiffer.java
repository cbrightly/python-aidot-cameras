package com.didichuxing.doraemonkit.widget.bravh.diff;

import android.os.Handler;
import android.os.Looper;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BrvahAsyncDiffer.kt */
public final class BrvahAsyncDiffer<T> implements DifferImp<T> {
    private final BaseQuickAdapter<T, ?> adapter;
    /* access modifiers changed from: private */
    public final BrvahAsyncDifferConfig<T> config;
    private final List<ListChangeListener<T>> mListeners;
    /* access modifiers changed from: private */
    public Executor mMainThreadExecutor;
    /* access modifiers changed from: private */
    public int mMaxScheduledGeneration;
    private final ListUpdateCallback mUpdateCallback;
    private final Executor sMainThreadExecutor;

    public final void submitList(@Nullable List<T> list) {
        submitList$default(this, list, (Runnable) null, 2, (Object) null);
    }

    public BrvahAsyncDiffer(@NotNull BaseQuickAdapter<T, ?> adapter2, @NotNull BrvahAsyncDifferConfig<T> config2) {
        k.f(adapter2, "adapter");
        k.f(config2, "config");
        this.adapter = adapter2;
        this.config = config2;
        this.mUpdateCallback = new BrvahListUpdateCallback(adapter2);
        Executor mainThreadExecutor = new MainThreadExecutor();
        this.sMainThreadExecutor = mainThreadExecutor;
        Executor mainThreadExecutor2 = config2.getMainThreadExecutor();
        this.mMainThreadExecutor = mainThreadExecutor2 != null ? mainThreadExecutor2 : mainThreadExecutor;
        this.mListeners = new CopyOnWriteArrayList();
    }

    /* compiled from: BrvahAsyncDiffer.kt */
    public static final class MainThreadExecutor implements Executor {
        @NotNull
        private final Handler mHandler = new Handler(Looper.getMainLooper());

        @NotNull
        public final Handler getMHandler() {
            return this.mHandler;
        }

        public void execute(@NotNull Runnable command) {
            k.f(command, "command");
            this.mHandler.post(command);
        }
    }

    public static /* synthetic */ void submitList$default(BrvahAsyncDiffer brvahAsyncDiffer, List list, Runnable runnable, int i, Object obj) {
        if ((i & 2) != 0) {
            runnable = null;
        }
        brvahAsyncDiffer.submitList(list, runnable);
    }

    public final void submitList(@Nullable List<T> newList, @Nullable Runnable commitCallback) {
        this.mMaxScheduledGeneration++;
        int runGeneration = this.mMaxScheduledGeneration;
        if (newList != this.adapter.getData()) {
            List oldList = this.adapter.getData();
            if (newList == null) {
                int countRemoved = this.adapter.getData().size();
                this.adapter.setData$doraemonkit_release(new ArrayList());
                this.mUpdateCallback.onRemoved(0, countRemoved);
                onCurrentListChanged(oldList, commitCallback);
            } else if (this.adapter.getData().isEmpty()) {
                this.adapter.setData$doraemonkit_release(newList);
                this.mUpdateCallback.onInserted(0, newList.size());
                onCurrentListChanged(oldList, commitCallback);
            } else {
                this.config.getBackgroundThreadExecutor().execute(new BrvahAsyncDiffer$submitList$1(this, oldList, newList, runGeneration, commitCallback));
            }
        } else if (commitCallback != null) {
            commitCallback.run();
        }
    }

    /* access modifiers changed from: private */
    public final void latchList(List<T> newList, DiffUtil.DiffResult diffResult, Runnable commitCallback) {
        List previousList = this.adapter.getData();
        this.adapter.setData$doraemonkit_release(newList);
        diffResult.dispatchUpdatesTo(this.mUpdateCallback);
        onCurrentListChanged(previousList, commitCallback);
    }

    private final void onCurrentListChanged(List<? extends T> previousList, Runnable commitCallback) {
        for (ListChangeListener<T> listener : this.mListeners) {
            listener.onCurrentListChanged(previousList, this.adapter.getData());
        }
        if (commitCallback != null) {
            commitCallback.run();
        }
    }

    public void addListListener(@NotNull ListChangeListener<T> listener) {
        k.f(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        this.mListeners.add(listener);
    }

    public final void removeListListener(@NotNull ListChangeListener<T> listener) {
        k.f(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        this.mListeners.remove(listener);
    }

    public final void clearAllListListener() {
        this.mListeners.clear();
    }
}

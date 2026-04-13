package com.google.android.gms.common.api.internal;

import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.concurrent.HandlerExecutor;
import java.util.concurrent.Executor;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class ListenerHolder<L> {
    private final Executor zaa;
    @Nullable
    private volatile Object zab;
    @Nullable
    private volatile ListenerKey zac;

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
    public static final class ListenerKey<L> {
        private final Object zaa;
        private final String zab;

        @KeepForSdk
        ListenerKey(L l, String str) {
            this.zaa = l;
            this.zab = str;
        }

        @KeepForSdk
        public boolean equals(@Nullable Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ListenerKey)) {
                return false;
            }
            ListenerKey listenerKey = (ListenerKey) o;
            return this.zaa == listenerKey.zaa && this.zab.equals(listenerKey.zab);
        }

        @KeepForSdk
        public int hashCode() {
            return (System.identityHashCode(this.zaa) * 31) + this.zab.hashCode();
        }

        @NonNull
        @KeepForSdk
        public String toIdString() {
            String str = this.zab;
            int identityHashCode = System.identityHashCode(this.zaa);
            return str + "@" + identityHashCode;
        }
    }

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
    public interface Notifier<L> {
        @KeepForSdk
        void notifyListener(@NonNull L l);

        @KeepForSdk
        void onNotifyListenerFailed();
    }

    @KeepForSdk
    ListenerHolder(@NonNull Looper looper, @NonNull L listener, @NonNull String listenerType) {
        this.zaa = new HandlerExecutor(looper);
        this.zab = Preconditions.checkNotNull(listener, "Listener must not be null");
        this.zac = new ListenerKey(listener, Preconditions.checkNotEmpty(listenerType));
    }

    @KeepForSdk
    public void clear() {
        this.zab = null;
        this.zac = null;
    }

    @KeepForSdk
    @Nullable
    public ListenerKey<L> getListenerKey() {
        return this.zac;
    }

    @KeepForSdk
    public boolean hasListener() {
        return this.zab != null;
    }

    @KeepForSdk
    public void notifyListener(@NonNull Notifier<? super L> notifier) {
        Preconditions.checkNotNull(notifier, "Notifier must not be null");
        this.zaa.execute(new zacb(this, notifier));
    }

    /* access modifiers changed from: package-private */
    public final void zaa(Notifier notifier) {
        Object obj = this.zab;
        if (obj == null) {
            notifier.onNotifyListenerFailed();
            return;
        }
        try {
            notifier.notifyListener(obj);
        } catch (RuntimeException e) {
            notifier.onNotifyListenerFailed();
            throw e;
        }
    }

    @KeepForSdk
    ListenerHolder(@NonNull Executor executor, @NonNull L listener, @NonNull String listenerType) {
        this.zaa = (Executor) Preconditions.checkNotNull(executor, "Executor must not be null");
        this.zab = Preconditions.checkNotNull(listener, "Listener must not be null");
        this.zac = new ListenerKey(listener, Preconditions.checkNotEmpty(listenerType));
    }
}

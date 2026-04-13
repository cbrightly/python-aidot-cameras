package com.google.android.gms.common.api.internal;

import android.os.Looper;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public class ListenerHolders {
    private final Set zaa = Collections.newSetFromMap(new WeakHashMap());

    @NonNull
    @KeepForSdk
    public static <L> ListenerHolder<L> createListenerHolder(@NonNull L listener, @NonNull Looper looper, @NonNull String listenerType) {
        Preconditions.checkNotNull(listener, "Listener must not be null");
        Preconditions.checkNotNull(looper, "Looper must not be null");
        Preconditions.checkNotNull(listenerType, "Listener type must not be null");
        return new ListenerHolder<>(looper, listener, listenerType);
    }

    @NonNull
    @KeepForSdk
    public static <L> ListenerHolder.ListenerKey<L> createListenerKey(@NonNull L listener, @NonNull String listenerType) {
        Preconditions.checkNotNull(listener, "Listener must not be null");
        Preconditions.checkNotNull(listenerType, "Listener type must not be null");
        Preconditions.checkNotEmpty(listenerType, "Listener type must not be empty");
        return new ListenerHolder.ListenerKey<>(listener, listenerType);
    }

    @NonNull
    public final ListenerHolder zaa(@NonNull Object obj, @NonNull Looper looper, @NonNull String str) {
        ListenerHolder createListenerHolder = createListenerHolder(obj, looper, "NO_TYPE");
        this.zaa.add(createListenerHolder);
        return createListenerHolder;
    }

    public final void zab() {
        for (ListenerHolder clear : this.zaa) {
            clear.clear();
        }
        this.zaa.clear();
    }

    @NonNull
    @KeepForSdk
    public static <L> ListenerHolder<L> createListenerHolder(@NonNull L listener, @NonNull Executor executor, @NonNull String listenerType) {
        Preconditions.checkNotNull(listener, "Listener must not be null");
        Preconditions.checkNotNull(executor, "Executor must not be null");
        Preconditions.checkNotNull(listenerType, "Listener type must not be null");
        return new ListenerHolder<>(executor, listener, listenerType);
    }
}

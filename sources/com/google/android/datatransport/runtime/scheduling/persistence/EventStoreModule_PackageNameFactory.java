package com.google.android.datatransport.runtime.scheduling.persistence;

import android.content.Context;
import com.google.android.datatransport.runtime.dagger.internal.Factory;
import com.google.android.datatransport.runtime.dagger.internal.Preconditions;
import javax.inject.a;

public final class EventStoreModule_PackageNameFactory implements Factory<String> {
    private final a<Context> contextProvider;

    public EventStoreModule_PackageNameFactory(a<Context> contextProvider2) {
        this.contextProvider = contextProvider2;
    }

    public String get() {
        return packageName(this.contextProvider.get());
    }

    public static EventStoreModule_PackageNameFactory create(a<Context> contextProvider2) {
        return new EventStoreModule_PackageNameFactory(contextProvider2);
    }

    public static String packageName(Context context) {
        return (String) Preconditions.checkNotNull(EventStoreModule.packageName(context), "Cannot return null from a non-@Nullable @Provides method");
    }
}

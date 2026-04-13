package com.google.android.gms.tasks;

import androidx.annotation.NonNull;
import java.util.concurrent.Executor;

/* compiled from: com.google.android.gms:play-services-tasks@@18.0.2 */
public final class zzt implements Executor {
    zzt() {
    }

    public final void execute(@NonNull Runnable runnable) {
        runnable.run();
    }
}

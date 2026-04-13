package com.google.firebase.iid.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.tasks.Task;

@KeepForSdk
/* compiled from: com.google.firebase:firebase-iid-interop@@17.1.0 */
public interface FirebaseInstanceIdInternal {

    @KeepForSdk
    /* compiled from: com.google.firebase:firebase-iid-interop@@17.1.0 */
    public interface NewTokenListener {
        @KeepForSdk
        void onNewToken(String str);
    }

    @KeepForSdk
    void addNewTokenListener(NewTokenListener newTokenListener);

    @KeepForSdk
    void deleteToken(@NonNull String str, @NonNull String str2);

    @KeepForSdk
    String getId();

    @KeepForSdk
    @Nullable
    String getToken();

    @NonNull
    @KeepForSdk
    Task<String> getTokenTask();
}

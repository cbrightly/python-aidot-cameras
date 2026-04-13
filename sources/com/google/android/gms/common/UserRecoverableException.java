package com.google.android.gms.common;

import android.content.Intent;
import androidx.annotation.NonNull;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class UserRecoverableException extends Exception {
    private final Intent zza;

    public UserRecoverableException(@NonNull String msg, @NonNull Intent intent) {
        super(msg);
        this.zza = intent;
    }

    @NonNull
    public Intent getIntent() {
        return new Intent(this.zza);
    }
}

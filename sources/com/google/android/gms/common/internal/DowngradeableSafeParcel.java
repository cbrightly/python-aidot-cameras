package com.google.android.gms.common.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public abstract class DowngradeableSafeParcel extends AbstractSafeParcelable implements ReflectedParcelable {
    private static final Object zza = new Object();
    private boolean zzb = false;

    @KeepForSdk
    protected static boolean canUnparcelSafely(@NonNull String str) {
        synchronized (zza) {
        }
        return true;
    }

    @KeepForSdk
    @Nullable
    protected static Integer getUnparcelClientVersion() {
        synchronized (zza) {
        }
        return null;
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    public abstract boolean prepareForClientVersion(int i);

    @KeepForSdk
    public void setShouldDowngrade(boolean z) {
        this.zzb = z;
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    public boolean shouldDowngrade() {
        return this.zzb;
    }
}

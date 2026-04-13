package com.google.android.gms.common;

import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzz;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public abstract class zzj extends zzz {
    private final int zza;

    protected zzj(byte[] bArr) {
        boolean z;
        if (bArr.length == 25) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        this.zza = Arrays.hashCode(bArr);
    }

    protected static byte[] zze(String str) {
        try {
            return str.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(@Nullable Object obj) {
        IObjectWrapper zzd;
        if (obj == null || !(obj instanceof zzaa)) {
            return false;
        }
        try {
            zzaa zzaa = (zzaa) obj;
            if (zzaa.zzc() != this.zza || (zzd = zzaa.zzd()) == null) {
                return false;
            }
            return Arrays.equals(zzf(), (byte[]) ObjectWrapper.unwrap(zzd));
        } catch (RemoteException e) {
            Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
            return false;
        }
    }

    public final int hashCode() {
        return this.zza;
    }

    public final int zzc() {
        return this.zza;
    }

    public final IObjectWrapper zzd() {
        return ObjectWrapper.wrap(zzf());
    }

    /* access modifiers changed from: package-private */
    public abstract byte[] zzf();
}

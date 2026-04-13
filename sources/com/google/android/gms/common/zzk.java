package com.google.android.gms.common;

import java.util.Arrays;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class zzk extends zzj {
    private final byte[] zza;

    zzk(byte[] bArr) {
        super(Arrays.copyOfRange(bArr, 0, 25));
        this.zza = bArr;
    }

    /* access modifiers changed from: package-private */
    public final byte[] zzf() {
        return this.zza;
    }
}

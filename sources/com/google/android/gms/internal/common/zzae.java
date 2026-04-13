package com.google.android.gms.internal.common;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class zzae extends zzz {
    private final zzag zza;

    zzae(zzag zzag, int i) {
        super(zzag.size(), i);
        this.zza = zzag;
    }

    /* access modifiers changed from: protected */
    public final Object zza(int i) {
        return this.zza.get(i);
    }
}

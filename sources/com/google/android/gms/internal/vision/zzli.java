package com.google.android.gms.internal.vision;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzli extends zzlo {
    private final /* synthetic */ zzlh zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private zzli(zzlh zzlh) {
        super(zzlh, (zzlg) null);
        this.zza = zzlh;
    }

    public final Iterator<Map.Entry<K, V>> iterator() {
        return new zzlj(this.zza, (zzlg) null);
    }

    /* synthetic */ zzli(zzlh zzlh, zzlg zzlg) {
        this(zzlh);
    }
}

package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzjj;
import com.google.android.gms.internal.measurement.zzjk;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public abstract class zzjj<MessageType extends zzjk<MessageType, BuilderType>, BuilderType extends zzjj<MessageType, BuilderType>> implements zzmh {
    /* renamed from: zzav */
    public abstract zzjj clone();

    public zzjj zzaw(byte[] bArr, int i, int i2) {
        throw null;
    }

    public zzjj zzax(byte[] bArr, int i, int i2, zzkn zzkn) {
        throw null;
    }

    public final /* synthetic */ zzmh zzay(byte[] bArr) {
        return zzaw(bArr, 0, bArr.length);
    }

    public final /* synthetic */ zzmh zzaz(byte[] bArr, zzkn zzkn) {
        return zzax(bArr, 0, bArr.length, zzkn);
    }
}

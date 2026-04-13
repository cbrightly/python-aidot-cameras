package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzhe;
import com.google.android.gms.internal.vision.zzhf;
import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public abstract class zzhe<MessageType extends zzhf<MessageType, BuilderType>, BuilderType extends zzhe<MessageType, BuilderType>> implements zzkn {
    /* renamed from: zza */
    public abstract BuilderType clone();

    /* access modifiers changed from: protected */
    public abstract BuilderType zza(MessageType messagetype);

    public abstract BuilderType zza(zzif zzif, zzio zzio);

    public BuilderType zza(byte[] bArr, int i, int i2, zzio zzio) {
        try {
            zzif zza = zzif.zza(bArr, 0, i2, false);
            zza(zza, zzio);
            zza.zza(0);
            return this;
        } catch (zzjk e) {
            throw e;
        } catch (IOException e2) {
            String name = getClass().getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 60 + String.valueOf("byte array").length());
            sb.append("Reading ");
            sb.append(name);
            sb.append(" from a ");
            sb.append("byte array");
            sb.append(" threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e2);
        }
    }

    public final /* synthetic */ zzkn zza(zzkk zzkk) {
        if (zzr().getClass().isInstance(zzkk)) {
            return zza((zzhf) zzkk);
        }
        throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
    }
}

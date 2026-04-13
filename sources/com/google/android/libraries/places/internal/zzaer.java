package com.google.android.libraries.places.internal;

import com.google.android.libraries.places.internal.zzaeq;
import com.google.android.libraries.places.internal.zzaer;
import java.io.IOException;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzaer<MessageType extends zzaer<MessageType, BuilderType>, BuilderType extends zzaeq<MessageType, BuilderType>> implements zzahh {
    protected int zza = 0;

    /* access modifiers changed from: package-private */
    public int zzr(zzahs zzahs) {
        throw null;
    }

    public final zzafe zzs() {
        try {
            int zzv = zzv();
            zzafe zzafe = zzafe.zzb;
            byte[] bArr = new byte[zzv];
            zzafm zzz = zzafm.zzz(bArr, 0, zzv);
            zzK(zzz);
            zzz.zzA();
            return new zzafb(bArr);
        } catch (IOException e) {
            String name = getClass().getName();
            throw new RuntimeException("Serializing " + name + " to a ByteString threw an IOException (should never happen).", e);
        }
    }
}

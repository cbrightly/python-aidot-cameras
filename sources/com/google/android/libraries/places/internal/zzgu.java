package com.google.android.libraries.places.internal;

import com.google.android.datatransport.Transformer;
import java.io.IOException;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzgu implements Transformer {
    public static final /* synthetic */ zzgu zza = new zzgu();

    private /* synthetic */ zzgu() {
    }

    public final Object apply(Object obj) {
        zznf zznf = (zznf) obj;
        try {
            int zzv = zznf.zzv();
            byte[] bArr = new byte[zzv];
            zzafm zzz = zzafm.zzz(bArr, 0, zzv);
            zznf.zzK(zzz);
            zzz.zzA();
            return bArr;
        } catch (IOException e) {
            String name = zznf.getClass().getName();
            throw new RuntimeException("Serializing " + name + " to a byte array threw an IOException (should never happen).", e);
        }
    }
}

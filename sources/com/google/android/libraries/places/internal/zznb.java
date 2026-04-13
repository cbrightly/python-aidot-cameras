package com.google.android.libraries.places.internal;

import java.io.IOException;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zznb {
    private static final zznb zza = new zzmz("base64()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", '=');
    private static final zznb zzb = new zzmz("base64Url()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", '=');
    private static final zznb zzc = new zzna("base32()", "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", '=');
    private static final zznb zzd = new zzna("base32Hex()", "0123456789ABCDEFGHIJKLMNOPQRSTUV", '=');
    private static final zznb zze = new zzmy("base16()", "0123456789ABCDEF");

    zznb() {
    }

    public static zznb zzd() {
        return zze;
    }

    /* access modifiers changed from: package-private */
    public abstract void zza(Appendable appendable, byte[] bArr, int i, int i2);

    /* access modifiers changed from: package-private */
    public abstract int zzb(int i);

    public final String zze(byte[] bArr, int i, int i2) {
        zziy.zzi(0, i2, bArr.length);
        StringBuilder sb = new StringBuilder(zzb(i2));
        try {
            zza(sb, bArr, 0, i2);
            return sb.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}

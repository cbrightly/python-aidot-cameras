package com.google.android.gms.internal.vision;

import java.nio.charset.Charset;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public class zzid extends zzia {
    protected final byte[] zzb;

    zzid(byte[] bArr) {
        if (bArr != null) {
            this.zzb = bArr;
            return;
        }
        throw new NullPointerException();
    }

    public byte zza(int i) {
        return this.zzb[i];
    }

    /* access modifiers changed from: package-private */
    public byte zzb(int i) {
        return this.zzb[i];
    }

    public int zza() {
        return this.zzb.length;
    }

    public final zzht zza(int i, int i2) {
        int zzb2 = zzht.zzb(0, i2, zza());
        if (zzb2 == 0) {
            return zzht.zza;
        }
        return new zzhw(this.zzb, zze(), zzb2);
    }

    /* access modifiers changed from: protected */
    public void zza(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.zzb, 0, bArr, 0, i3);
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzhq zzhq) {
        zzhq.zza(this.zzb, zze(), zza());
    }

    /* access modifiers changed from: protected */
    public final String zza(Charset charset) {
        return new String(this.zzb, zze(), zza(), charset);
    }

    public final boolean zzc() {
        int zze = zze();
        return zzmd.zza(this.zzb, zze, zza() + zze);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzht) || zza() != ((zzht) obj).zza()) {
            return false;
        }
        if (zza() == 0) {
            return true;
        }
        if (!(obj instanceof zzid)) {
            return obj.equals(this);
        }
        zzid zzid = (zzid) obj;
        int zzd = zzd();
        int zzd2 = zzid.zzd();
        if (zzd == 0 || zzd2 == 0 || zzd == zzd2) {
            return zza(zzid, 0, zza());
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public final boolean zza(zzht zzht, int i, int i2) {
        if (i2 > zzht.zza()) {
            int zza = zza();
            StringBuilder sb = new StringBuilder(40);
            sb.append("Length too large: ");
            sb.append(i2);
            sb.append(zza);
            throw new IllegalArgumentException(sb.toString());
        } else if (i2 > zzht.zza()) {
            int zza2 = zzht.zza();
            StringBuilder sb2 = new StringBuilder(59);
            sb2.append("Ran off end of other: 0, ");
            sb2.append(i2);
            sb2.append(", ");
            sb2.append(zza2);
            throw new IllegalArgumentException(sb2.toString());
        } else if (!(zzht instanceof zzid)) {
            return zzht.zza(0, i2).equals(zza(0, i2));
        } else {
            zzid zzid = (zzid) zzht;
            byte[] bArr = this.zzb;
            byte[] bArr2 = zzid.zzb;
            int zze = zze() + i2;
            int zze2 = zze();
            int zze3 = zzid.zze();
            while (zze2 < zze) {
                if (bArr[zze2] != bArr2[zze3]) {
                    return false;
                }
                zze2++;
                zze3++;
            }
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public final int zza(int i, int i2, int i3) {
        return zzjf.zza(i, this.zzb, zze(), i3);
    }

    /* access modifiers changed from: protected */
    public int zze() {
        return 0;
    }
}

package com.google.android.libraries.places.internal;

import java.nio.charset.Charset;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public class zzafb extends zzafa {
    protected final byte[] zza;

    zzafb(byte[] bArr) {
        if (bArr != null) {
            this.zza = bArr;
            return;
        }
        throw null;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzafe) || zzd() != ((zzafe) obj).zzd()) {
            return false;
        }
        if (zzd() == 0) {
            return true;
        }
        if (!(obj instanceof zzafb)) {
            return obj.equals(this);
        }
        zzafb zzafb = (zzafb) obj;
        int zzk = zzk();
        int zzk2 = zzafb.zzk();
        if (zzk != 0 && zzk2 != 0 && zzk != zzk2) {
            return false;
        }
        int zzd = zzd();
        if (zzd > zzafb.zzd()) {
            throw new IllegalArgumentException("Length too large: " + zzd + zzd());
        } else if (zzd <= zzafb.zzd()) {
            byte[] bArr = this.zza;
            byte[] bArr2 = zzafb.zza;
            zzafb.zzc();
            int i = 0;
            int i2 = 0;
            while (i < zzd) {
                if (bArr[i] != bArr2[i2]) {
                    return false;
                }
                i++;
                i2++;
            }
            return true;
        } else {
            throw new IllegalArgumentException("Ran off end of other: 0, " + zzd + ", " + zzafb.zzd());
        }
    }

    public byte zza(int i) {
        return this.zza[i];
    }

    /* access modifiers changed from: package-private */
    public byte zzb(int i) {
        return this.zza[i];
    }

    /* access modifiers changed from: protected */
    public int zzc() {
        return 0;
    }

    public int zzd() {
        return this.zza.length;
    }

    /* access modifiers changed from: protected */
    public final int zze(int i, int i2, int i3) {
        return zzagi.zzb(i, this.zza, 0, i3);
    }

    public final zzafe zzf(int i, int i2) {
        zzafe.zzj(0, i2, zzd());
        if (i2 == 0) {
            return zzafe.zzb;
        }
        return new zzaey(this.zza, 0, i2);
    }

    /* access modifiers changed from: protected */
    public final String zzg(Charset charset) {
        return new String(this.zza, 0, zzd(), charset);
    }

    /* access modifiers changed from: package-private */
    public final void zzh(zzaeu zzaeu) {
        ((zzafj) zzaeu).zzc(this.zza, 0, zzd());
    }

    public final boolean zzi() {
        return zzaix.zze(this.zza, 0, zzd());
    }
}

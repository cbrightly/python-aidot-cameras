package com.google.android.libraries.places.internal;

import java.math.RoundingMode;
import javax.annotation.CheckForNull;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public class zzna extends zznb {
    final zzmx zzb;
    @CheckForNull
    final Character zzc;

    zzna(zzmx zzmx, @CheckForNull Character ch) {
        boolean z;
        this.zzb = zzmx;
        if (ch != null) {
            ch.charValue();
            z = !zzmx.zzb('=');
        } else {
            z = true;
        }
        if (z) {
            this.zzc = ch;
            return;
        }
        throw new IllegalArgumentException(zzjd.zza("Padding character %s was already in alphabet", ch));
    }

    public final boolean equals(@CheckForNull Object obj) {
        if (obj instanceof zzna) {
            zzna zzna = (zzna) obj;
            if (this.zzb.equals(zzna.zzb)) {
                Character ch = this.zzc;
                Character ch2 = zzna.zzc;
                if (ch == ch2) {
                    return true;
                }
                if (ch == null || !ch.equals(ch2)) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i;
        int hashCode = this.zzb.hashCode();
        Character ch = this.zzc;
        if (ch == null) {
            i = 0;
        } else {
            i = ch.hashCode();
        }
        return hashCode ^ i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BaseEncoding.");
        sb.append(this.zzb);
        if (8 % this.zzb.zzb != 0) {
            if (this.zzc == null) {
                sb.append(".omitPadding()");
            } else {
                sb.append(".withPadChar('");
                sb.append(this.zzc);
                sb.append("')");
            }
        }
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public void zza(Appendable appendable, byte[] bArr, int i, int i2) {
        int i3 = 0;
        zziy.zzi(0, i2, bArr.length);
        while (i3 < i2) {
            zzc(appendable, bArr, i3, Math.min(this.zzb.zzd, i2 - i3));
            i3 += this.zzb.zzd;
        }
    }

    /* access modifiers changed from: package-private */
    public final int zzb(int i) {
        zzmx zzmx = this.zzb;
        return zzmx.zzc * zzadj.zza(i, zzmx.zzd, RoundingMode.CEILING);
    }

    /* access modifiers changed from: package-private */
    public final void zzc(Appendable appendable, byte[] bArr, int i, int i2) {
        boolean z;
        zziy.zzi(i, i + i2, bArr.length);
        int i3 = 0;
        if (i2 <= this.zzb.zzd) {
            z = true;
        } else {
            z = false;
        }
        zziy.zzd(z);
        long j = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            j = (j | ((long) (bArr[i + i4] & 255))) << 8;
        }
        int i5 = ((i2 + 1) * 8) - this.zzb.zzb;
        while (i3 < i2 * 8) {
            zzmx zzmx = this.zzb;
            appendable.append(zzmx.zza(zzmx.zza & ((int) (j >>> (i5 - i3)))));
            i3 += this.zzb.zzb;
        }
        if (this.zzc != null) {
            while (i3 < this.zzb.zzd * 8) {
                this.zzc.charValue();
                appendable.append('=');
                i3 += this.zzb.zzb;
            }
        }
    }

    zzna(String str, String str2, @CheckForNull Character ch) {
        this(new zzmx(str, str2.toCharArray()), ch);
    }
}

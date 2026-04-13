package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Arrays;
import java.util.Comparator;

@SafeParcelable.Class(creator = "FlagCreator")
@SafeParcelable.Reserved({1})
public final class zzi extends AbstractSafeParcelable implements Comparable<zzi> {
    public static final Parcelable.Creator<zzi> CREATOR = new zzk();
    private static final Comparator<zzi> zzai = new zzj();
    @SafeParcelable.Field(id = 2)
    public final String name;
    @SafeParcelable.Field(id = 3)
    private final long zzab;
    @SafeParcelable.Field(id = 4)
    private final boolean zzac;
    @SafeParcelable.Field(id = 5)
    private final double zzad;
    @SafeParcelable.Field(id = 6)
    private final String zzae;
    @SafeParcelable.Field(id = 7)
    private final byte[] zzaf;
    @SafeParcelable.Field(id = 8)
    private final int zzag;
    @SafeParcelable.Field(id = 9)
    public final int zzah;

    @SafeParcelable.Constructor
    public zzi(@SafeParcelable.Param(id = 2) String str, @SafeParcelable.Param(id = 3) long j, @SafeParcelable.Param(id = 4) boolean z, @SafeParcelable.Param(id = 5) double d, @SafeParcelable.Param(id = 6) String str2, @SafeParcelable.Param(id = 7) byte[] bArr, @SafeParcelable.Param(id = 8) int i, @SafeParcelable.Param(id = 9) int i2) {
        this.name = str;
        this.zzab = j;
        this.zzac = z;
        this.zzad = d;
        this.zzae = str2;
        this.zzaf = bArr;
        this.zzag = i;
        this.zzah = i2;
    }

    private static int compare(int i, int i2) {
        if (i < i2) {
            return -1;
        }
        return i == i2 ? 0 : 1;
    }

    public final /* synthetic */ int compareTo(Object obj) {
        zzi zzi = (zzi) obj;
        int compareTo = this.name.compareTo(zzi.name);
        if (compareTo != 0) {
            return compareTo;
        }
        int compare = compare(this.zzag, zzi.zzag);
        if (compare != 0) {
            return compare;
        }
        switch (this.zzag) {
            case 1:
                int i = (this.zzab > zzi.zzab ? 1 : (this.zzab == zzi.zzab ? 0 : -1));
                if (i < 0) {
                    return -1;
                }
                return i == 0 ? 0 : 1;
            case 2:
                boolean z = this.zzac;
                if (z == zzi.zzac) {
                    return 0;
                }
                return z ? 1 : -1;
            case 3:
                return Double.compare(this.zzad, zzi.zzad);
            case 4:
                String str = this.zzae;
                String str2 = zzi.zzae;
                if (str == str2) {
                    return 0;
                }
                if (str == null) {
                    return -1;
                }
                if (str2 == null) {
                    return 1;
                }
                return str.compareTo(str2);
            case 5:
                byte[] bArr = this.zzaf;
                byte[] bArr2 = zzi.zzaf;
                if (bArr == bArr2) {
                    return 0;
                }
                if (bArr == null) {
                    return -1;
                }
                if (bArr2 == null) {
                    return 1;
                }
                for (int i2 = 0; i2 < Math.min(this.zzaf.length, zzi.zzaf.length); i2++) {
                    int i3 = this.zzaf[i2] - zzi.zzaf[i2];
                    if (i3 != 0) {
                        return i3;
                    }
                }
                return compare(this.zzaf.length, zzi.zzaf.length);
            default:
                int i4 = this.zzag;
                StringBuilder sb = new StringBuilder(31);
                sb.append("Invalid enum value: ");
                sb.append(i4);
                throw new AssertionError(sb.toString());
        }
    }

    public final boolean equals(Object obj) {
        int i;
        if (obj instanceof zzi) {
            zzi zzi = (zzi) obj;
            if (zzn.equals(this.name, zzi.name) && (i = this.zzag) == zzi.zzag && this.zzah == zzi.zzah) {
                switch (i) {
                    case 1:
                        if (this.zzab == zzi.zzab) {
                            return true;
                        }
                        break;
                    case 2:
                        return this.zzac == zzi.zzac;
                    case 3:
                        return this.zzad == zzi.zzad;
                    case 4:
                        return zzn.equals(this.zzae, zzi.zzae);
                    case 5:
                        return Arrays.equals(this.zzaf, zzi.zzaf);
                    default:
                        int i2 = this.zzag;
                        StringBuilder sb = new StringBuilder(31);
                        sb.append("Invalid enum value: ");
                        sb.append(i2);
                        throw new AssertionError(sb.toString());
                }
            }
        }
        return false;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String toString() {
        /*
            r6 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Flag("
            r0.append(r1)
            java.lang.String r1 = r6.name
            r0.append(r1)
            java.lang.String r1 = ", "
            r0.append(r1)
            int r2 = r6.zzag
            java.lang.String r3 = "'"
            switch(r2) {
                case 1: goto L_0x0073;
                case 2: goto L_0x006d;
                case 3: goto L_0x0067;
                case 4: goto L_0x005b;
                case 5: goto L_0x0046;
                default: goto L_0x001b;
            }
        L_0x001b:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            java.lang.String r2 = r6.name
            int r3 = r6.zzag
            java.lang.String r4 = java.lang.String.valueOf(r2)
            int r4 = r4.length()
            int r4 = r4 + 27
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = "Invalid type: "
            r5.append(r4)
            r5.append(r2)
            r5.append(r1)
            r5.append(r3)
            java.lang.String r1 = r5.toString()
            r0.<init>(r1)
            throw r0
        L_0x0046:
            byte[] r2 = r6.zzaf
            if (r2 != 0) goto L_0x0050
            java.lang.String r2 = "null"
            r0.append(r2)
            goto L_0x0078
        L_0x0050:
            r0.append(r3)
            byte[] r2 = r6.zzaf
            r4 = 3
            java.lang.String r2 = android.util.Base64.encodeToString(r2, r4)
            goto L_0x0060
        L_0x005b:
            r0.append(r3)
            java.lang.String r2 = r6.zzae
        L_0x0060:
            r0.append(r2)
            r0.append(r3)
            goto L_0x0078
        L_0x0067:
            double r2 = r6.zzad
            r0.append(r2)
            goto L_0x0078
        L_0x006d:
            boolean r2 = r6.zzac
            r0.append(r2)
            goto L_0x0078
        L_0x0073:
            long r2 = r6.zzab
            r0.append(r2)
        L_0x0078:
            r0.append(r1)
            int r2 = r6.zzag
            r0.append(r2)
            r0.append(r1)
            int r1 = r6.zzah
            r0.append(r1)
            java.lang.String r1 = ")"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.phenotype.zzi.toString():java.lang.String");
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.name, false);
        SafeParcelWriter.writeLong(parcel, 3, this.zzab);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzac);
        SafeParcelWriter.writeDouble(parcel, 5, this.zzad);
        SafeParcelWriter.writeString(parcel, 6, this.zzae, false);
        SafeParcelWriter.writeByteArray(parcel, 7, this.zzaf, false);
        SafeParcelWriter.writeInt(parcel, 8, this.zzag);
        SafeParcelWriter.writeInt(parcel, 9, this.zzah);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}

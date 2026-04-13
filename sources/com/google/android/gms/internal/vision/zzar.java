package com.google.android.gms.internal.vision;

import android.os.Parcelable;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
public final class zzar implements Parcelable.Creator<zzao> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzao[i];
    }

    /* JADX WARNING: type inference failed for: r1v3, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r1v4, types: [android.os.Parcelable] */
    /* JADX WARNING: type inference failed for: r1v5, types: [android.os.Parcelable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r13) {
        /*
            r12 = this;
            int r0 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.validateObjectHeader(r13)
            r1 = 0
            r2 = 0
            r3 = 0
            r5 = r1
            r6 = r5
            r7 = r6
            r8 = r7
            r10 = r8
            r9 = r2
            r11 = r3
        L_0x0015:
            int r1 = r13.dataPosition()
            if (r1 >= r0) goto L_0x0060
            int r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readHeader(r13)
            int r2 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.getFieldId(r1)
            switch(r2) {
                case 2: goto L_0x0056;
                case 3: goto L_0x004c;
                case 4: goto L_0x0042;
                case 5: goto L_0x003c;
                case 6: goto L_0x0036;
                case 7: goto L_0x0030;
                case 8: goto L_0x002a;
                default: goto L_0x0026;
            }
        L_0x0026:
            com.google.android.gms.common.internal.safeparcel.SafeParcelReader.skipUnknownField(r13, r1)
            goto L_0x0015
        L_0x002a:
            boolean r11 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readBoolean(r13, r1)
            goto L_0x0015
        L_0x0030:
            java.lang.String r10 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r13, r1)
            goto L_0x0015
        L_0x0036:
            float r9 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readFloat(r13, r1)
            goto L_0x0015
        L_0x003c:
            java.lang.String r8 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r13, r1)
            goto L_0x0015
        L_0x0042:
            android.os.Parcelable$Creator<com.google.android.gms.internal.vision.zzab> r2 = com.google.android.gms.internal.vision.zzab.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createParcelable(r13, r1, r2)
            r7 = r1
            com.google.android.gms.internal.vision.zzab r7 = (com.google.android.gms.internal.vision.zzab) r7
            goto L_0x0015
        L_0x004c:
            android.os.Parcelable$Creator<com.google.android.gms.internal.vision.zzab> r2 = com.google.android.gms.internal.vision.zzab.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createParcelable(r13, r1, r2)
            r6 = r1
            com.google.android.gms.internal.vision.zzab r6 = (com.google.android.gms.internal.vision.zzab) r6
            goto L_0x0015
        L_0x0056:
            android.os.Parcelable$Creator<com.google.android.gms.internal.vision.zzal> r2 = com.google.android.gms.internal.vision.zzal.CREATOR
            java.lang.Object[] r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createTypedArray(r13, r1, r2)
            r5 = r1
            com.google.android.gms.internal.vision.zzal[] r5 = (com.google.android.gms.internal.vision.zzal[]) r5
            goto L_0x0015
        L_0x0060:
            com.google.android.gms.common.internal.safeparcel.SafeParcelReader.ensureAtEnd(r13, r0)
            com.google.android.gms.internal.vision.zzao r13 = new com.google.android.gms.internal.vision.zzao
            r4 = r13
            r4.<init>(r5, r6, r7, r8, r9, r10, r11)
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzar.createFromParcel(android.os.Parcel):java.lang.Object");
    }
}

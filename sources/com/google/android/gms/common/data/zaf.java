package com.google.android.gms.common.data;

import android.os.Parcelable;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zaf implements Parcelable.Creator {
    /* JADX WARNING: type inference failed for: r1v3, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* bridge */ /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r10) {
        /*
            r9 = this;
            int r0 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.validateObjectHeader(r10)
            r1 = 0
            r2 = 0
            r4 = r1
            r7 = r4
            r5 = r2
            r6 = r5
            r8 = r6
        L_0x000b:
            int r1 = r10.dataPosition()
            if (r1 >= r0) goto L_0x003e
            int r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readHeader(r10)
            int r2 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.getFieldId(r1)
            switch(r2) {
                case 1: goto L_0x0039;
                case 2: goto L_0x002f;
                case 3: goto L_0x002a;
                case 4: goto L_0x0025;
                case 1000: goto L_0x0020;
                default: goto L_0x001c;
            }
        L_0x001c:
            com.google.android.gms.common.internal.safeparcel.SafeParcelReader.skipUnknownField(r10, r1)
            goto L_0x000b
        L_0x0020:
            int r4 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readInt(r10, r1)
            goto L_0x000b
        L_0x0025:
            android.os.Bundle r8 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createBundle(r10, r1)
            goto L_0x000b
        L_0x002a:
            int r7 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readInt(r10, r1)
            goto L_0x000b
        L_0x002f:
            android.os.Parcelable$Creator r2 = android.database.CursorWindow.CREATOR
            java.lang.Object[] r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createTypedArray(r10, r1, r2)
            r6 = r1
            android.database.CursorWindow[] r6 = (android.database.CursorWindow[]) r6
            goto L_0x000b
        L_0x0039:
            java.lang.String[] r5 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createStringArray(r10, r1)
            goto L_0x000b
        L_0x003e:
            com.google.android.gms.common.internal.safeparcel.SafeParcelReader.ensureAtEnd(r10, r0)
            com.google.android.gms.common.data.DataHolder r10 = new com.google.android.gms.common.data.DataHolder
            r3 = r10
            r3.<init>((int) r4, (java.lang.String[]) r5, (android.database.CursorWindow[]) r6, (int) r7, (android.os.Bundle) r8)
            r10.zad()
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.data.zaf.createFromParcel(android.os.Parcel):java.lang.Object");
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataHolder[i];
    }
}

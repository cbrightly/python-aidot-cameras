package com.google.android.gms.vision.barcode;

import android.os.Parcelable;
import com.google.android.gms.vision.barcode.Barcode;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
public final class zzf implements Parcelable.Creator<Barcode.CalendarEvent> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new Barcode.CalendarEvent[i];
    }

    /* JADX WARNING: type inference failed for: r1v3, types: [android.os.Parcelable] */
    /* JADX WARNING: type inference failed for: r1v4, types: [android.os.Parcelable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r11) {
        /*
            r10 = this;
            int r0 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.validateObjectHeader(r11)
            r1 = 0
            r3 = r1
            r4 = r3
            r5 = r4
            r6 = r5
            r7 = r6
            r8 = r7
            r9 = r8
        L_0x0013:
            int r1 = r11.dataPosition()
            if (r1 >= r0) goto L_0x005a
            int r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readHeader(r11)
            int r2 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.getFieldId(r1)
            switch(r2) {
                case 2: goto L_0x0054;
                case 3: goto L_0x004e;
                case 4: goto L_0x0048;
                case 5: goto L_0x0042;
                case 6: goto L_0x003c;
                case 7: goto L_0x0032;
                case 8: goto L_0x0028;
                default: goto L_0x0024;
            }
        L_0x0024:
            com.google.android.gms.common.internal.safeparcel.SafeParcelReader.skipUnknownField(r11, r1)
            goto L_0x0013
        L_0x0028:
            android.os.Parcelable$Creator<com.google.android.gms.vision.barcode.Barcode$CalendarDateTime> r2 = com.google.android.gms.vision.barcode.Barcode.CalendarDateTime.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createParcelable(r11, r1, r2)
            r9 = r1
            com.google.android.gms.vision.barcode.Barcode$CalendarDateTime r9 = (com.google.android.gms.vision.barcode.Barcode.CalendarDateTime) r9
            goto L_0x0013
        L_0x0032:
            android.os.Parcelable$Creator<com.google.android.gms.vision.barcode.Barcode$CalendarDateTime> r2 = com.google.android.gms.vision.barcode.Barcode.CalendarDateTime.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createParcelable(r11, r1, r2)
            r8 = r1
            com.google.android.gms.vision.barcode.Barcode$CalendarDateTime r8 = (com.google.android.gms.vision.barcode.Barcode.CalendarDateTime) r8
            goto L_0x0013
        L_0x003c:
            java.lang.String r7 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r11, r1)
            goto L_0x0013
        L_0x0042:
            java.lang.String r6 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r11, r1)
            goto L_0x0013
        L_0x0048:
            java.lang.String r5 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r11, r1)
            goto L_0x0013
        L_0x004e:
            java.lang.String r4 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r11, r1)
            goto L_0x0013
        L_0x0054:
            java.lang.String r3 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r11, r1)
            goto L_0x0013
        L_0x005a:
            com.google.android.gms.common.internal.safeparcel.SafeParcelReader.ensureAtEnd(r11, r0)
            com.google.android.gms.vision.barcode.Barcode$CalendarEvent r11 = new com.google.android.gms.vision.barcode.Barcode$CalendarEvent
            r2 = r11
            r2.<init>(r3, r4, r5, r6, r7, r8, r9)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.vision.barcode.zzf.createFromParcel(android.os.Parcel):java.lang.Object");
    }
}

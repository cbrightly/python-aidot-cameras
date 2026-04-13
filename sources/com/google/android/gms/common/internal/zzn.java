package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class zzn implements Parcelable.Creator {
    static void zza(GetServiceRequest getServiceRequest, Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, getServiceRequest.zzc);
        SafeParcelWriter.writeInt(parcel, 2, getServiceRequest.zzd);
        SafeParcelWriter.writeInt(parcel, 3, getServiceRequest.zze);
        SafeParcelWriter.writeString(parcel, 4, getServiceRequest.zzf, false);
        SafeParcelWriter.writeIBinder(parcel, 5, getServiceRequest.zzg, false);
        SafeParcelWriter.writeTypedArray(parcel, 6, getServiceRequest.zzh, i, false);
        SafeParcelWriter.writeBundle(parcel, 7, getServiceRequest.zzi, false);
        SafeParcelWriter.writeParcelable(parcel, 8, getServiceRequest.zzj, i, false);
        SafeParcelWriter.writeTypedArray(parcel, 10, getServiceRequest.zzk, i, false);
        SafeParcelWriter.writeTypedArray(parcel, 11, getServiceRequest.zzl, i, false);
        SafeParcelWriter.writeBoolean(parcel, 12, getServiceRequest.zzm);
        SafeParcelWriter.writeInt(parcel, 13, getServiceRequest.zzn);
        SafeParcelWriter.writeBoolean(parcel, 14, getServiceRequest.zzo);
        SafeParcelWriter.writeString(parcel, 15, getServiceRequest.zza(), false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    /* JADX WARNING: type inference failed for: r2v3, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r2v4, types: [android.os.Parcelable] */
    /* JADX WARNING: type inference failed for: r2v5, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r2v6, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* bridge */ /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r23) {
        /*
            r22 = this;
            r0 = r23
            int r1 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.validateObjectHeader(r23)
            com.google.android.gms.common.api.Scope[] r2 = com.google.android.gms.common.internal.GetServiceRequest.zza
            android.os.Bundle r3 = new android.os.Bundle
            r3.<init>()
            com.google.android.gms.common.Feature[] r4 = com.google.android.gms.common.internal.GetServiceRequest.zzb
            r5 = 0
            r6 = 0
            r13 = r2
            r14 = r3
            r16 = r4
            r17 = r16
            r11 = r5
            r12 = r11
            r15 = r12
            r21 = r15
            r8 = r6
            r9 = r8
            r10 = r9
            r18 = r10
            r19 = r18
            r20 = r19
        L_0x0025:
            int r2 = r23.dataPosition()
            if (r2 >= r1) goto L_0x0096
            int r2 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readHeader(r23)
            int r3 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.getFieldId(r2)
            switch(r3) {
                case 1: goto L_0x0091;
                case 2: goto L_0x008c;
                case 3: goto L_0x0087;
                case 4: goto L_0x0082;
                case 5: goto L_0x007d;
                case 6: goto L_0x0073;
                case 7: goto L_0x006e;
                case 8: goto L_0x0064;
                case 9: goto L_0x0036;
                case 10: goto L_0x0059;
                case 11: goto L_0x004e;
                case 12: goto L_0x0049;
                case 13: goto L_0x0044;
                case 14: goto L_0x003f;
                case 15: goto L_0x003a;
                default: goto L_0x0036;
            }
        L_0x0036:
            com.google.android.gms.common.internal.safeparcel.SafeParcelReader.skipUnknownField(r0, r2)
            goto L_0x0025
        L_0x003a:
            java.lang.String r21 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r0, r2)
            goto L_0x0025
        L_0x003f:
            boolean r20 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readBoolean(r0, r2)
            goto L_0x0025
        L_0x0044:
            int r19 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readInt(r0, r2)
            goto L_0x0025
        L_0x0049:
            boolean r18 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readBoolean(r0, r2)
            goto L_0x0025
        L_0x004e:
            android.os.Parcelable$Creator<com.google.android.gms.common.Feature> r3 = com.google.android.gms.common.Feature.CREATOR
            java.lang.Object[] r2 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createTypedArray(r0, r2, r3)
            r17 = r2
            com.google.android.gms.common.Feature[] r17 = (com.google.android.gms.common.Feature[]) r17
            goto L_0x0025
        L_0x0059:
            android.os.Parcelable$Creator<com.google.android.gms.common.Feature> r3 = com.google.android.gms.common.Feature.CREATOR
            java.lang.Object[] r2 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createTypedArray(r0, r2, r3)
            r16 = r2
            com.google.android.gms.common.Feature[] r16 = (com.google.android.gms.common.Feature[]) r16
            goto L_0x0025
        L_0x0064:
            android.os.Parcelable$Creator r3 = android.accounts.Account.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createParcelable(r0, r2, r3)
            r15 = r2
            android.accounts.Account r15 = (android.accounts.Account) r15
            goto L_0x0025
        L_0x006e:
            android.os.Bundle r14 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createBundle(r0, r2)
            goto L_0x0025
        L_0x0073:
            android.os.Parcelable$Creator<com.google.android.gms.common.api.Scope> r3 = com.google.android.gms.common.api.Scope.CREATOR
            java.lang.Object[] r2 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createTypedArray(r0, r2, r3)
            r13 = r2
            com.google.android.gms.common.api.Scope[] r13 = (com.google.android.gms.common.api.Scope[]) r13
            goto L_0x0025
        L_0x007d:
            android.os.IBinder r12 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readIBinder(r0, r2)
            goto L_0x0025
        L_0x0082:
            java.lang.String r11 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createString(r0, r2)
            goto L_0x0025
        L_0x0087:
            int r10 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readInt(r0, r2)
            goto L_0x0025
        L_0x008c:
            int r9 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readInt(r0, r2)
            goto L_0x0025
        L_0x0091:
            int r8 = com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readInt(r0, r2)
            goto L_0x0025
        L_0x0096:
            com.google.android.gms.common.internal.safeparcel.SafeParcelReader.ensureAtEnd(r0, r1)
            com.google.android.gms.common.internal.GetServiceRequest r0 = new com.google.android.gms.common.internal.GetServiceRequest
            r7 = r0
            r7.<init>(r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzn.createFromParcel(android.os.Parcel):java.lang.Object");
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new GetServiceRequest[i];
    }
}

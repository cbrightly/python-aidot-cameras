package com.google.android.gms.vision.face.internal.client;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.vision.zzb;
import com.google.android.gms.internal.vision.zzd;
import com.google.android.gms.internal.vision.zzs;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
public final class zzj extends zzb implements zzh {
    zzj(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.vision.face.internal.client.INativeFaceDetector");
    }

    public final FaceParcel[] zza(IObjectWrapper iObjectWrapper, zzs zzs) {
        Parcel a_ = a_();
        zzd.zza(a_, (IInterface) iObjectWrapper);
        zzd.zza(a_, (Parcelable) zzs);
        Parcel zza = zza(1, a_);
        FaceParcel[] faceParcelArr = (FaceParcel[]) zza.createTypedArray(FaceParcel.CREATOR);
        zza.recycle();
        return faceParcelArr;
    }

    public final FaceParcel[] zza(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, IObjectWrapper iObjectWrapper3, int i, int i2, int i3, int i4, int i5, int i6, zzs zzs) {
        Parcel a_ = a_();
        zzd.zza(a_, (IInterface) iObjectWrapper);
        zzd.zza(a_, (IInterface) iObjectWrapper2);
        zzd.zza(a_, (IInterface) iObjectWrapper3);
        a_.writeInt(i);
        a_.writeInt(i2);
        a_.writeInt(i3);
        a_.writeInt(i4);
        a_.writeInt(i5);
        a_.writeInt(i6);
        zzd.zza(a_, (Parcelable) zzs);
        Parcel zza = zza(4, a_);
        FaceParcel[] faceParcelArr = (FaceParcel[]) zza.createTypedArray(FaceParcel.CREATOR);
        zza.recycle();
        return faceParcelArr;
    }

    public final boolean zza(int i) {
        Parcel a_ = a_();
        a_.writeInt(i);
        Parcel zza = zza(2, a_);
        boolean zza2 = zzd.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final void zza() {
        zzb(3, a_());
    }
}

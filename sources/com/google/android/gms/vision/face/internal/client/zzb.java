package com.google.android.gms.vision.face.internal.client;

import android.content.Context;
import android.graphics.PointF;
import android.media.Image;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.internal.vision.zzs;
import com.google.android.gms.internal.vision.zzt;
import com.google.android.gms.internal.vision.zzu;
import com.google.android.gms.vision.face.Contour;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;
import java.nio.ByteBuffer;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
public final class zzb extends zzt<zzh> {
    private final zzf zza;

    public zzb(Context context, zzf zzf) {
        super(context, "FaceNativeHandle", "face");
        this.zza = zzf;
        zzd();
    }

    public final Face[] zza(ByteBuffer byteBuffer, zzs zzs) {
        if (!zzb()) {
            return new Face[0];
        }
        try {
            FaceParcel[] zza2 = ((zzh) Preconditions.checkNotNull((zzh) zzd())).zza(ObjectWrapper.wrap(byteBuffer), zzs);
            Face[] faceArr = new Face[zza2.length];
            for (int i = 0; i < zza2.length; i++) {
                faceArr[i] = zza(zza2[i]);
            }
            return faceArr;
        } catch (RemoteException e) {
            Log.e("FaceNativeHandle", "Could not call native face detector", e);
            return new Face[0];
        }
    }

    @RequiresApi(19)
    public final Face[] zza(Image.Plane[] planeArr, zzs zzs) {
        Image.Plane[] planeArr2 = planeArr;
        if (!zzb()) {
            Log.e("FaceNativeHandle", "Native handle is not ready to be used.");
            return new Face[0];
        } else if (planeArr2 == null || planeArr2.length == 3) {
            try {
                IObjectWrapper wrap = ObjectWrapper.wrap(planeArr2[0].getBuffer());
                IObjectWrapper wrap2 = ObjectWrapper.wrap(planeArr2[1].getBuffer());
                IObjectWrapper wrap3 = ObjectWrapper.wrap(planeArr2[2].getBuffer());
                int pixelStride = planeArr2[0].getPixelStride();
                int pixelStride2 = planeArr2[1].getPixelStride();
                int pixelStride3 = planeArr2[2].getPixelStride();
                int rowStride = planeArr2[0].getRowStride();
                FaceParcel[] zza2 = ((zzh) Preconditions.checkNotNull((zzh) zzd())).zza(wrap, wrap2, wrap3, pixelStride, pixelStride2, pixelStride3, rowStride, planeArr2[1].getRowStride(), planeArr2[2].getRowStride(), zzs);
                Face[] faceArr = new Face[zza2.length];
                for (int i = 0; i < zza2.length; i++) {
                    faceArr[i] = zza(zza2[i]);
                }
                return faceArr;
            } catch (RemoteException e) {
                Log.e("FaceNativeHandle", "Could not call native face detector", e);
                return new Face[0];
            }
        } else {
            throw new IllegalArgumentException("Only android.graphics.ImageFormat#YUV_420_888 is supported which should have 3 planes.");
        }
    }

    public final boolean zza(int i) {
        if (!zzb()) {
            return false;
        }
        try {
            return ((zzh) Preconditions.checkNotNull((zzh) zzd())).zza(i);
        } catch (RemoteException e) {
            Log.e("FaceNativeHandle", "Could not call native face detector", e);
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public final void zza() {
        ((zzh) Preconditions.checkNotNull((zzh) zzd())).zza();
    }

    private static Face zza(FaceParcel faceParcel) {
        Landmark[] landmarkArr;
        Contour[] contourArr;
        FaceParcel faceParcel2 = faceParcel;
        int i = faceParcel2.zza;
        PointF pointF = new PointF(faceParcel2.zzb, faceParcel2.zzc);
        float f = faceParcel2.zzd;
        float f2 = faceParcel2.zze;
        float f3 = faceParcel2.zzf;
        float f4 = faceParcel2.zzg;
        float f5 = faceParcel2.zzh;
        LandmarkParcel[] landmarkParcelArr = faceParcel2.zzi;
        if (landmarkParcelArr == null) {
            landmarkArr = new Landmark[0];
        } else {
            Landmark[] landmarkArr2 = new Landmark[landmarkParcelArr.length];
            int i2 = 0;
            while (i2 < landmarkParcelArr.length) {
                LandmarkParcel landmarkParcel = landmarkParcelArr[i2];
                landmarkArr2[i2] = new Landmark(new PointF(landmarkParcel.zza, landmarkParcel.zzb), landmarkParcel.zzc);
                i2++;
                landmarkParcelArr = landmarkParcelArr;
            }
            landmarkArr = landmarkArr2;
        }
        zza[] zzaArr = faceParcel2.zzm;
        if (zzaArr == null) {
            contourArr = new Contour[0];
        } else {
            Contour[] contourArr2 = new Contour[zzaArr.length];
            for (int i3 = 0; i3 < zzaArr.length; i3++) {
                zza zza2 = zzaArr[i3];
                contourArr2[i3] = new Contour(zza2.zza, zza2.zzb);
            }
            contourArr = contourArr2;
        }
        return new Face(i, pointF, f, f2, f3, f4, f5, landmarkArr, contourArr, faceParcel2.zzj, faceParcel2.zzk, faceParcel2.zzl, faceParcel2.zzn);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final /* synthetic */ Object zza(DynamiteModule dynamiteModule, Context context) {
        zzi zzi;
        if (zzu.zza(context, "com.google.android.gms.vision.dynamite.face")) {
            zzi = zzl.asInterface(dynamiteModule.instantiate("com.google.android.gms.vision.face.NativeFaceDetectorV2Creator"));
        } else {
            zzi = zzl.asInterface(dynamiteModule.instantiate("com.google.android.gms.vision.face.ChimeraNativeFaceDetectorCreator"));
        }
        if (zzi == null) {
            return null;
        }
        return zzi.newFaceDetector(ObjectWrapper.wrap(context), (zzf) Preconditions.checkNotNull(this.zza));
    }
}

package com.google.android.gms.common.images;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Asserts;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zaa implements Runnable {
    final /* synthetic */ ImageManager zaa;
    private final Uri zab;
    @Nullable
    private final ParcelFileDescriptor zac;

    public zaa(ImageManager imageManager, @Nullable Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
        this.zaa = imageManager;
        this.zab = uri;
        this.zac = parcelFileDescriptor;
    }

    public final void run() {
        boolean z;
        Bitmap bitmap;
        Asserts.checkNotMainThread("LoadBitmapFromDiskRunnable can't be executed in the main thread");
        ParcelFileDescriptor parcelFileDescriptor = this.zac;
        boolean z2 = false;
        Bitmap bitmap2 = null;
        if (parcelFileDescriptor != null) {
            try {
                bitmap2 = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor());
            } catch (OutOfMemoryError e) {
                Log.e("ImageManager", "OOM while loading bitmap for uri: ".concat(String.valueOf(String.valueOf(this.zab))), e);
                z2 = true;
            }
            try {
                this.zac.close();
            } catch (IOException e2) {
                Log.e("ImageManager", "closed failed", e2);
            }
            z = z2;
            bitmap = bitmap2;
        } else {
            z = false;
            bitmap = null;
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ImageManager imageManager = this.zaa;
        imageManager.zae.post(new zac(imageManager, this.zab, bitmap, z, countDownLatch));
        try {
            countDownLatch.await();
        } catch (InterruptedException e3) {
            Log.w("ImageManager", "Latch interrupted while posting ".concat(String.valueOf(String.valueOf(this.zab))));
        }
    }
}

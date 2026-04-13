package com.google.android.gms.common.images;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.internal.base.zam;
import com.google.android.gms.internal.base.zat;
import com.google.android.gms.internal.base.zau;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class ImageManager {
    /* access modifiers changed from: private */
    public static final Object zaa = new Object();
    /* access modifiers changed from: private */
    public static final HashSet zab = new HashSet();
    private static ImageManager zac;
    /* access modifiers changed from: private */
    public final Context zad;
    /* access modifiers changed from: private */
    public final Handler zae = new zau(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public final ExecutorService zaf = zat.zaa().zab(4, 2);
    /* access modifiers changed from: private */
    public final zam zag = new zam();
    /* access modifiers changed from: private */
    public final Map zah = new HashMap();
    /* access modifiers changed from: private */
    public final Map zai = new HashMap();
    /* access modifiers changed from: private */
    public final Map zaj = new HashMap();

    @KeepName
    /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
    public final class ImageReceiver extends ResultReceiver {
        private final Uri zab;
        /* access modifiers changed from: private */
        public final ArrayList zac = new ArrayList();

        ImageReceiver(Uri uri) {
            super(new zau(Looper.getMainLooper()));
            this.zab = uri;
        }

        public final void onReceiveResult(int i, Bundle bundle) {
            ImageManager imageManager = ImageManager.this;
            imageManager.zaf.execute(new zaa(imageManager, this.zab, (ParcelFileDescriptor) bundle.getParcelable("com.google.android.gms.extra.fileDescriptor")));
        }

        public final void zab(zag zag) {
            Asserts.checkMainThread("ImageReceiver.addImageRequest() must be called in the main thread");
            this.zac.add(zag);
        }

        public final void zac(zag zag) {
            Asserts.checkMainThread("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.zac.remove(zag);
        }

        public final void zad() {
            Intent intent = new Intent(Constants.ACTION_LOAD_IMAGE);
            intent.setPackage("com.google.android.gms");
            intent.putExtra(Constants.EXTRA_URI, this.zab);
            intent.putExtra(Constants.EXTRA_RESULT_RECEIVER, this);
            intent.putExtra(Constants.EXTRA_PRIORITY, 3);
            ImageManager.this.zad.sendBroadcast(intent);
        }
    }

    /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
    public interface OnImageLoadedListener {
        void onImageLoaded(@NonNull Uri uri, @Nullable Drawable drawable, boolean z);
    }

    private ImageManager(Context context, boolean z) {
        this.zad = context.getApplicationContext();
    }

    @NonNull
    public static ImageManager create(@NonNull Context context) {
        if (zac == null) {
            zac = new ImageManager(context, false);
        }
        return zac;
    }

    public void loadImage(@NonNull ImageView imageView, int resId) {
        zaj(new zae(imageView, resId));
    }

    public final void zaj(zag zag2) {
        Asserts.checkMainThread("ImageManager.loadImage() must be called in the main thread");
        new zab(this, zag2).run();
    }

    public void loadImage(@NonNull ImageView imageView, @NonNull Uri uri) {
        zaj(new zae(imageView, uri));
    }

    public void loadImage(@NonNull ImageView imageView, @NonNull Uri uri, int defaultResId) {
        zae zae2 = new zae(imageView, uri);
        zae2.zab = defaultResId;
        zaj(zae2);
    }

    public void loadImage(@NonNull OnImageLoadedListener listener, @NonNull Uri uri) {
        zaj(new zaf(listener, uri));
    }

    public void loadImage(@NonNull OnImageLoadedListener listener, @NonNull Uri uri, int defaultResId) {
        zaf zaf2 = new zaf(listener, uri);
        zaf2.zab = defaultResId;
        zaj(zaf2);
    }
}

package com.bumptech.glide.load.resource;

import android.annotation.SuppressLint;
import android.graphics.ColorSpace;
import android.graphics.ImageDecoder;
import android.os.Build;
import android.util.Log;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.load.b;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.h;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.j;
import com.bumptech.glide.load.k;
import com.bumptech.glide.load.resource.bitmap.l;
import com.bumptech.glide.load.resource.bitmap.m;
import com.bumptech.glide.load.resource.bitmap.r;

@RequiresApi(api = 28)
/* compiled from: ImageDecoderResourceDecoder */
public abstract class a<T> implements k<ImageDecoder.Source, T> {
    final r a = r.b();

    /* access modifiers changed from: protected */
    public abstract t<T> c(ImageDecoder.Source source, int i, int i2, ImageDecoder.OnHeaderDecodedListener onHeaderDecodedListener);

    /* renamed from: e */
    public final boolean a(@NonNull ImageDecoder.Source source, @NonNull i options) {
        return true;
    }

    @Nullable
    /* renamed from: d */
    public final t<T> b(@NonNull ImageDecoder.Source source, int requestedWidth, int requestedHeight, @NonNull i options) {
        i iVar = options;
        b decodeFormat = (b) iVar.a(m.a);
        l strategy = (l) iVar.a(l.h);
        h hVar = m.e;
        int i = requestedHeight;
        C0040a aVar = new C0040a(requestedWidth, i, iVar.a(hVar) != null && ((Boolean) iVar.a(hVar)).booleanValue(), decodeFormat, strategy, (j) iVar.a(m.b));
        ImageDecoder.Source source2 = source;
        return c(source, requestedWidth, i, aVar);
    }

    /* renamed from: com.bumptech.glide.load.resource.a$a  reason: collision with other inner class name */
    /* compiled from: ImageDecoderResourceDecoder */
    public class C0040a implements ImageDecoder.OnHeaderDecodedListener {
        final /* synthetic */ int a;
        final /* synthetic */ int b;
        final /* synthetic */ boolean c;
        final /* synthetic */ b d;
        final /* synthetic */ l e;
        final /* synthetic */ j f;

        C0040a(int i, int i2, boolean z, b bVar, l lVar, j jVar) {
            this.a = i;
            this.b = i2;
            this.c = z;
            this.d = bVar;
            this.e = lVar;
            this.f = jVar;
        }

        @SuppressLint({"Override"})
        public void onHeaderDecoded(ImageDecoder decoder, ImageDecoder.ImageInfo info, ImageDecoder.Source source) {
            boolean isP3Eligible = false;
            if (a.this.a.e(this.a, this.b, this.c, false)) {
                decoder.setAllocator(3);
            } else {
                decoder.setAllocator(1);
            }
            if (this.d == b.PREFER_RGB_565) {
                decoder.setMemorySizePolicy(0);
            }
            decoder.setOnPartialImageListener(new C0041a());
            Size size = info.getSize();
            int targetWidth = this.a;
            if (this.a == Integer.MIN_VALUE) {
                targetWidth = size.getWidth();
            }
            int targetHeight = this.b;
            if (this.b == Integer.MIN_VALUE) {
                targetHeight = size.getHeight();
            }
            float scaleFactor = this.e.b(size.getWidth(), size.getHeight(), targetWidth, targetHeight);
            int resizeWidth = Math.round(((float) size.getWidth()) * scaleFactor);
            int resizeHeight = Math.round(((float) size.getHeight()) * scaleFactor);
            if (Log.isLoggable("ImageDecoder", 2)) {
                Log.v("ImageDecoder", "Resizing from [" + size.getWidth() + "x" + size.getHeight() + "] to [" + resizeWidth + "x" + resizeHeight + "] scaleFactor: " + scaleFactor);
            }
            decoder.setTargetSize(resizeWidth, resizeHeight);
            int i = Build.VERSION.SDK_INT;
            if (i >= 28) {
                if (this.f == j.DISPLAY_P3 && info.getColorSpace() != null && info.getColorSpace().isWideGamut()) {
                    isP3Eligible = true;
                }
                decoder.setTargetColorSpace(ColorSpace.get(isP3Eligible ? ColorSpace.Named.DISPLAY_P3 : ColorSpace.Named.SRGB));
            } else if (i >= 26) {
                decoder.setTargetColorSpace(ColorSpace.get(ColorSpace.Named.SRGB));
            }
        }

        /* renamed from: com.bumptech.glide.load.resource.a$a$a  reason: collision with other inner class name */
        /* compiled from: ImageDecoderResourceDecoder */
        public class C0041a implements ImageDecoder.OnPartialImageListener {
            C0041a() {
            }

            public boolean onPartialImage(@NonNull ImageDecoder.DecodeException e) {
                return false;
            }
        }
    }
}

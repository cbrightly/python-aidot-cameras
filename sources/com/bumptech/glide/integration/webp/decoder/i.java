package com.bumptech.glide.integration.webp.decoder;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LruCache;
import com.bumptech.glide.gifdecoder.a;
import com.bumptech.glide.gifdecoder.c;
import com.bumptech.glide.integration.webp.WebpFrame;
import com.bumptech.glide.integration.webp.WebpImage;
import java.nio.ByteBuffer;

/* compiled from: WebpDecoder */
public class i implements com.bumptech.glide.gifdecoder.a {
    private ByteBuffer a;
    private WebpImage b;
    /* access modifiers changed from: private */
    public final a.C0022a c;
    private int d;
    private final int[] e;
    private final com.bumptech.glide.integration.webp.a[] f;
    private int g;
    private int h;
    private int i;
    private final Paint j;
    private n k;
    private Bitmap.Config l;
    private final LruCache<Integer, Bitmap> m;

    public i(a.C0022a provider, WebpImage webPImage, ByteBuffer rawData, int sampleSize) {
        this(provider, webPImage, rawData, sampleSize, n.a);
    }

    public i(a.C0022a provider, WebpImage webPImage, ByteBuffer rawData, int sampleSize, n cacheStrategy) {
        int maxCacheSize;
        this.d = -1;
        this.l = Bitmap.Config.ARGB_8888;
        this.c = provider;
        this.b = webPImage;
        this.e = webPImage.getFrameDurations();
        this.f = new com.bumptech.glide.integration.webp.a[webPImage.getFrameCount()];
        for (int i2 = 0; i2 < this.b.getFrameCount(); i2++) {
            this.f[i2] = this.b.getFrameInfo(i2);
            if (Log.isLoggable("WebpDecoder", 3)) {
                Log.d("WebpDecoder", "mFrameInfos: " + this.f[i2].toString());
            }
        }
        this.k = cacheStrategy;
        Paint paint = new Paint();
        this.j = paint;
        paint.setColor(0);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        if (this.k.a()) {
            maxCacheSize = webPImage.getFrameCount();
        } else {
            maxCacheSize = Math.max(5, this.k.b());
        }
        this.m = new a(maxCacheSize);
        q(new c(), rawData, sampleSize);
    }

    /* compiled from: WebpDecoder */
    public class a extends LruCache<Integer, Bitmap> {
        a(int maxSize) {
            super(maxSize);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void entryRemoved(boolean evicted, Integer key, Bitmap oldValue, Bitmap newValue) {
            if (oldValue != null) {
                i.this.c.a(oldValue);
            }
        }
    }

    public n k() {
        return this.k;
    }

    public ByteBuffer getData() {
        return this.a;
    }

    public void a() {
        this.d = (this.d + 1) % this.b.getFrameCount();
    }

    public int l(int n) {
        if (n < 0) {
            return -1;
        }
        int[] iArr = this.e;
        if (n < iArr.length) {
            return iArr[n];
        }
        return -1;
    }

    public int d() {
        int i2;
        if (this.e.length == 0 || (i2 = this.d) < 0) {
            return 0;
        }
        return l(i2);
    }

    public int b() {
        return this.b.getFrameCount();
    }

    public int f() {
        return this.d;
    }

    public void e() {
        this.d = -1;
    }

    public int g() {
        return this.b.getSizeInBytes();
    }

    public void c(Bitmap.Config config) {
        if (config == Bitmap.Config.ARGB_8888) {
            this.l = config;
            return;
        }
        throw new IllegalArgumentException("Unsupported format: " + config + ", must be one of " + Bitmap.Config.ARGB_8888);
    }

    public Bitmap getNextFrame() {
        int nextIndex;
        Bitmap cache;
        int frameNumber = f();
        Bitmap bitmap = this.c.c(this.i, this.h, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(0);
        if (Build.VERSION.SDK_INT >= 24) {
            bitmap.setDensity(DisplayMetrics.DENSITY_DEVICE_STABLE);
        }
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(0, PorterDuff.Mode.SRC);
        if (this.k.c() || (cache = this.m.get(Integer.valueOf(frameNumber))) == null) {
            if (!n(frameNumber)) {
                nextIndex = o(frameNumber - 1, canvas);
            } else {
                nextIndex = frameNumber;
            }
            if (Log.isLoggable("WebpDecoder", 3)) {
                Log.d("WebpDecoder", "frameNumber=" + frameNumber + ", nextIndex=" + nextIndex);
            }
            for (int index = nextIndex; index < frameNumber; index++) {
                com.bumptech.glide.integration.webp.a frameInfo = this.f[index];
                if (!frameInfo.g) {
                    j(canvas, frameInfo);
                }
                p(index, canvas);
                if (Log.isLoggable("WebpDecoder", 3)) {
                    Log.d("WebpDecoder", "renderFrame, index=" + index + ", blend=" + frameInfo.g + ", dispose=" + frameInfo.h);
                }
                if (frameInfo.h) {
                    j(canvas, frameInfo);
                }
            }
            com.bumptech.glide.integration.webp.a frameInfo2 = this.f[frameNumber];
            if (!frameInfo2.g) {
                j(canvas, frameInfo2);
            }
            p(frameNumber, canvas);
            if (Log.isLoggable("WebpDecoder", 3)) {
                Log.d("WebpDecoder", "renderFrame, index=" + frameNumber + ", blend=" + frameInfo2.g + ", dispose=" + frameInfo2.h);
            }
            i(frameNumber, bitmap);
            return bitmap;
        }
        if (Log.isLoggable("WebpDecoder", 3)) {
            Log.d("WebpDecoder", "hit frame bitmap from memory cache, frameNumber=" + frameNumber);
        }
        cache.setDensity(canvas.getDensity());
        canvas.drawBitmap(cache, 0.0f, 0.0f, (Paint) null);
        return bitmap;
    }

    private void p(int frameNumber, Canvas canvas) {
        com.bumptech.glide.integration.webp.a frameInfo = this.f[frameNumber];
        int i2 = frameInfo.d;
        int i3 = this.g;
        int frameWidth = i2 / i3;
        int frameHeight = frameInfo.e / i3;
        int xOffset = frameInfo.b / i3;
        int yOffset = frameInfo.c / i3;
        if (frameWidth != 0 && frameHeight != 0) {
            WebpFrame webpFrame = this.b.getFrame(frameNumber);
            try {
                Bitmap frameBitmap = this.c.c(frameWidth, frameHeight, this.l);
                frameBitmap.eraseColor(0);
                frameBitmap.setDensity(canvas.getDensity());
                webpFrame.renderFrame(frameWidth, frameHeight, frameBitmap);
                canvas.drawBitmap(frameBitmap, (float) xOffset, (float) yOffset, (Paint) null);
                this.c.a(frameBitmap);
            } catch (IllegalArgumentException | IllegalStateException e2) {
                Log.e("WebpDecoder", "Rendering of frame failed. Frame number: " + frameNumber);
            } catch (Throwable th) {
                webpFrame.dispose();
                throw th;
            }
            webpFrame.dispose();
        }
    }

    private void i(int frameNumber, Bitmap bitmap) {
        this.m.remove(Integer.valueOf(frameNumber));
        Bitmap cache = this.c.c(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        cache.eraseColor(0);
        cache.setDensity(bitmap.getDensity());
        Canvas canvas = new Canvas(cache);
        canvas.drawColor(0, PorterDuff.Mode.SRC);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        this.m.put(Integer.valueOf(frameNumber), cache);
    }

    public void clear() {
        this.b.dispose();
        this.b = null;
        this.m.evictAll();
        this.a = null;
    }

    public void q(c header, ByteBuffer buffer, int sampleSize) {
        if (sampleSize > 0) {
            int sampleSize2 = Integer.highestOneBit(sampleSize);
            ByteBuffer asReadOnlyBuffer = buffer.asReadOnlyBuffer();
            this.a = asReadOnlyBuffer;
            asReadOnlyBuffer.position(0);
            this.g = sampleSize2;
            this.i = this.b.getWidth() / sampleSize2;
            this.h = this.b.getHeight() / sampleSize2;
            return;
        }
        throw new IllegalArgumentException("Sample size must be >=0, not: " + sampleSize);
    }

    private int o(int previousFrameNumber, Canvas canvas) {
        int index = previousFrameNumber;
        while (index >= 0) {
            com.bumptech.glide.integration.webp.a frameInfo = this.f[index];
            if (frameInfo.h && m(frameInfo)) {
                return index + 1;
            }
            Bitmap bitmap = this.m.get(Integer.valueOf(index));
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.setDensity(canvas.getDensity());
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
                if (frameInfo.h) {
                    j(canvas, frameInfo);
                }
                return index + 1;
            } else if (n(index)) {
                return index;
            } else {
                index--;
            }
        }
        return 0;
    }

    private void j(Canvas canvas, com.bumptech.glide.integration.webp.a frameInfo) {
        int i2 = frameInfo.b;
        int i3 = this.g;
        int i4 = frameInfo.c;
        float top = (float) (i4 / i3);
        float bottom = (float) ((i4 + frameInfo.e) / i3);
        canvas.drawRect((float) (i2 / i3), top, (float) ((i2 + frameInfo.d) / i3), bottom, this.j);
    }

    private boolean n(int index) {
        if (index == 0) {
            return true;
        }
        com.bumptech.glide.integration.webp.a[] aVarArr = this.f;
        com.bumptech.glide.integration.webp.a curFrameInfo = aVarArr[index];
        com.bumptech.glide.integration.webp.a prevFrameInfo = aVarArr[index - 1];
        if (!curFrameInfo.g && m(curFrameInfo)) {
            return true;
        }
        if (!prevFrameInfo.h || !m(prevFrameInfo)) {
            return false;
        }
        return true;
    }

    private boolean m(com.bumptech.glide.integration.webp.a frameInfo) {
        return frameInfo.b == 0 && frameInfo.c == 0 && frameInfo.d == this.b.getWidth() && frameInfo.e == this.b.getHeight();
    }
}

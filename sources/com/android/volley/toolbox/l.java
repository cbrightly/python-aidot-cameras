package com.android.volley.toolbox;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.c;
import com.android.volley.h;
import com.android.volley.i;
import com.android.volley.k;
import com.android.volley.n;

/* compiled from: ImageRequest */
public class l extends i<Bitmap> {
    public static final float DEFAULT_IMAGE_BACKOFF_MULT = 2.0f;
    public static final int DEFAULT_IMAGE_MAX_RETRIES = 2;
    public static final int DEFAULT_IMAGE_TIMEOUT_MS = 1000;
    private static final Object sDecodeLock = new Object();
    private final Bitmap.Config mDecodeConfig;
    @GuardedBy("mLock")
    @Nullable
    private k.b<Bitmap> mListener;
    private final Object mLock;
    private final int mMaxHeight;
    private final int mMaxWidth;
    private final ImageView.ScaleType mScaleType;

    public l(String url, k.b<Bitmap> listener, int maxWidth, int maxHeight, ImageView.ScaleType scaleType, Bitmap.Config decodeConfig, @Nullable k.a errorListener) {
        super(0, url, errorListener);
        this.mLock = new Object();
        setRetryPolicy(new c(1000, 2, 2.0f));
        this.mListener = listener;
        this.mDecodeConfig = decodeConfig;
        this.mMaxWidth = maxWidth;
        this.mMaxHeight = maxHeight;
        this.mScaleType = scaleType;
    }

    @Deprecated
    public l(String url, k.b<Bitmap> listener, int maxWidth, int maxHeight, Bitmap.Config decodeConfig, k.a errorListener) {
        this(url, listener, maxWidth, maxHeight, ImageView.ScaleType.CENTER_INSIDE, decodeConfig, errorListener);
    }

    public i.c getPriority() {
        return i.c.LOW;
    }

    private static int getResizedDimension(int maxPrimary, int maxSecondary, int actualPrimary, int actualSecondary, ImageView.ScaleType scaleType) {
        if (maxPrimary == 0 && maxSecondary == 0) {
            return actualPrimary;
        }
        if (scaleType == ImageView.ScaleType.FIT_XY) {
            if (maxPrimary == 0) {
                return actualPrimary;
            }
            return maxPrimary;
        } else if (maxPrimary == 0) {
            return (int) (((double) actualPrimary) * (((double) maxSecondary) / ((double) actualSecondary)));
        } else if (maxSecondary == 0) {
            return maxPrimary;
        } else {
            double ratio = ((double) actualSecondary) / ((double) actualPrimary);
            int resized = maxPrimary;
            if (scaleType == ImageView.ScaleType.CENTER_CROP) {
                if (((double) resized) * ratio < ((double) maxSecondary)) {
                    return (int) (((double) maxSecondary) / ratio);
                }
                return resized;
            } else if (((double) resized) * ratio > ((double) maxSecondary)) {
                return (int) (((double) maxSecondary) / ratio);
            } else {
                return resized;
            }
        }
    }

    /* access modifiers changed from: protected */
    public k<Bitmap> parseNetworkResponse(h response) {
        k<Bitmap> doParse;
        synchronized (sDecodeLock) {
            try {
                doParse = doParse(response);
            } catch (OutOfMemoryError e) {
                n.c("Caught OOM for %d byte image, url=%s", Integer.valueOf(response.b.length), getUrl());
                return k.a(new ParseError((Throwable) e));
            } catch (Throwable th) {
                throw th;
            }
        }
        return doParse;
    }

    private k<Bitmap> doParse(h response) {
        Bitmap bitmap;
        byte[] data = response.b;
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        if (this.mMaxWidth == 0 && this.mMaxHeight == 0) {
            decodeOptions.inPreferredConfig = this.mDecodeConfig;
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);
        } else {
            decodeOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);
            int actualWidth = decodeOptions.outWidth;
            int actualHeight = decodeOptions.outHeight;
            int desiredWidth = getResizedDimension(this.mMaxWidth, this.mMaxHeight, actualWidth, actualHeight, this.mScaleType);
            int desiredHeight = getResizedDimension(this.mMaxHeight, this.mMaxWidth, actualHeight, actualWidth, this.mScaleType);
            decodeOptions.inJustDecodeBounds = false;
            decodeOptions.inSampleSize = findBestSampleSize(actualWidth, actualHeight, desiredWidth, desiredHeight);
            Bitmap tempBitmap = BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);
            if (tempBitmap == null || (tempBitmap.getWidth() <= desiredWidth && tempBitmap.getHeight() <= desiredHeight)) {
                bitmap = tempBitmap;
            } else {
                bitmap = Bitmap.createScaledBitmap(tempBitmap, desiredWidth, desiredHeight, true);
                tempBitmap.recycle();
            }
        }
        if (bitmap == null) {
            return k.a(new ParseError(response));
        }
        return k.c(bitmap, g.e(response));
    }

    public void cancel() {
        super.cancel();
        synchronized (this.mLock) {
            this.mListener = null;
        }
    }

    /* access modifiers changed from: protected */
    public void deliverResponse(Bitmap response) {
        Response.Listener<Bitmap> listener;
        synchronized (this.mLock) {
            listener = this.mListener;
        }
        if (listener != null) {
            listener.onResponse(response);
        }
    }

    @VisibleForTesting
    static int findBestSampleSize(int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {
        float n = 1.0f;
        while (((double) (n * 2.0f)) <= Math.min(((double) actualWidth) / ((double) desiredWidth), ((double) actualHeight) / ((double) desiredHeight))) {
            n *= 2.0f;
        }
        return (int) n;
    }
}

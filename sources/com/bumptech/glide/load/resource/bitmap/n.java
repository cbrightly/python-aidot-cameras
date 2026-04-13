package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.engine.bitmap_recycle.f;
import com.bumptech.glide.load.engine.t;
import java.util.concurrent.locks.Lock;

/* compiled from: DrawableToBitmapConverter */
public final class n {
    private static final e a = new a();

    /* compiled from: DrawableToBitmapConverter */
    public class a extends f {
        a() {
        }

        public void b(Bitmap bitmap) {
        }
    }

    @Nullable
    static t<Bitmap> a(e bitmapPool, Drawable drawable, int width, int height) {
        Drawable drawable2 = drawable.getCurrent();
        Bitmap result = null;
        boolean isRecycleable = false;
        if (drawable2 instanceof BitmapDrawable) {
            result = ((BitmapDrawable) drawable2).getBitmap();
        } else if (!(drawable2 instanceof Animatable)) {
            result = b(bitmapPool, drawable2, width, height);
            isRecycleable = true;
        }
        return e.c(result, isRecycleable ? bitmapPool : a);
    }

    @Nullable
    private static Bitmap b(e bitmapPool, Drawable drawable, int width, int height) {
        if (width == Integer.MIN_VALUE && drawable.getIntrinsicWidth() <= 0) {
            if (Log.isLoggable("DrawableToBitmap", 5)) {
                Log.w("DrawableToBitmap", "Unable to draw " + drawable + " to Bitmap with Target.SIZE_ORIGINAL because the Drawable has no intrinsic width");
            }
            return null;
        } else if (height != Integer.MIN_VALUE || drawable.getIntrinsicHeight() > 0) {
            int targetWidth = drawable.getIntrinsicWidth() > 0 ? drawable.getIntrinsicWidth() : width;
            int targetHeight = drawable.getIntrinsicHeight() > 0 ? drawable.getIntrinsicHeight() : height;
            Lock lock = z.i();
            lock.lock();
            Bitmap result = bitmapPool.c(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
            try {
                Canvas canvas = new Canvas(result);
                drawable.setBounds(0, 0, targetWidth, targetHeight);
                drawable.draw(canvas);
                canvas.setBitmap((Bitmap) null);
                return result;
            } finally {
                lock.unlock();
            }
        } else {
            if (Log.isLoggable("DrawableToBitmap", 5)) {
                Log.w("DrawableToBitmap", "Unable to draw " + drawable + " to Bitmap with Target.SIZE_ORIGINAL because the Drawable has no intrinsic height");
            }
            return null;
        }
    }
}

package com.leedarson.view.rangeseekbar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: Utils */
public class e {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static Bitmap f(Context context, int width, int height, int drawableId) {
        Object[] objArr = {context, new Integer(width), new Integer(height), new Integer(drawableId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 11981, new Class[]{Context.class, cls, cls, cls}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        if (context == null || width <= 0 || height <= 0 || drawableId == 0) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            return e(width, height, context.getResources().getDrawable(drawableId, (Resources.Theme) null));
        }
        return e(width, height, context.getResources().getDrawable(drawableId));
    }

    public static Bitmap e(int i, int i2, Drawable drawable) {
        Bitmap bitmap;
        Object[] objArr = {new Integer(i), new Integer(i2), drawable};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 11982, new Class[]{cls, cls, Drawable.class}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        int width = i;
        Drawable drawable2 = drawable;
        int height = i2;
        try {
            if (!(drawable2 instanceof BitmapDrawable) || (bitmap = ((BitmapDrawable) drawable2).getBitmap()) == null || bitmap.getHeight() <= 0) {
                Bitmap bitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap2);
                drawable2.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable2.draw(canvas);
                return bitmap2;
            }
            Matrix matrix = new Matrix();
            matrix.postScale((((float) width) * 1.0f) / ((float) bitmap.getWidth()), (((float) height) * 1.0f) / ((float) bitmap.getHeight()));
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void d(Canvas canvas, Bitmap bmp, Rect rect) {
        Class[] clsArr = {Canvas.class, Bitmap.class, Rect.class};
        if (!PatchProxy.proxy(new Object[]{canvas, bmp, rect}, (Object) null, changeQuickRedirect, true, 11983, clsArr, Void.TYPE).isSupported) {
            NinePatch.isNinePatchChunk(bmp.getNinePatchChunk());
            new NinePatch(bmp, bmp.getNinePatchChunk(), (String) null).draw(canvas, rect);
        }
    }

    public static void c(Canvas canvas, Paint paint, Bitmap bmp, Rect rect) {
        if (!PatchProxy.proxy(new Object[]{canvas, paint, bmp, rect}, (Object) null, changeQuickRedirect, true, 11984, new Class[]{Canvas.class, Paint.class, Bitmap.class, Rect.class}, Void.TYPE).isSupported) {
            try {
                if (NinePatch.isNinePatchChunk(bmp.getNinePatchChunk())) {
                    d(canvas, bmp, rect);
                    return;
                }
            } catch (Exception e) {
            }
            canvas.drawBitmap(bmp, (float) rect.left, (float) rect.top, paint);
        }
    }

    public static int b(Context context, float dpValue) {
        Object[] objArr = {context, new Float(dpValue)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 11985, new Class[]{Context.class, Float.TYPE}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (context == null || a(0.0f, dpValue) == 0) {
            return 0;
        }
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int a(float a, float b) {
        Object[] objArr = {new Float(a), new Float(b)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 11986, new Class[]{cls, cls}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int ta = Math.round(a * 1000000.0f);
        int tb = Math.round(1000000.0f * b);
        if (ta > tb) {
            return 1;
        }
        if (ta < tb) {
            return -1;
        }
        return 0;
    }

    public static float h(String s) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{s}, (Object) null, changeQuickRedirect, true, 11988, new Class[]{String.class}, Float.TYPE);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    public static Rect g(String text, float textSize) {
        Object[] objArr = {text, new Float(textSize)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 11989, new Class[]{String.class, Float.TYPE}, Rect.class);
        if (proxy.isSupported) {
            return (Rect) proxy.result;
        }
        Paint paint = new Paint();
        Rect textRect = new Rect();
        paint.setTextSize(textSize);
        paint.getTextBounds(text, 0, text.length(), textRect);
        paint.reset();
        return textRect;
    }

    public static boolean i(Bitmap bitmap) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bitmap}, (Object) null, changeQuickRedirect, true, 11990, new Class[]{Bitmap.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return bitmap != null && !bitmap.isRecycled() && bitmap.getWidth() > 0 && bitmap.getHeight() > 0;
    }
}

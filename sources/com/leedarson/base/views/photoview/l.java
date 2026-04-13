package com.leedarson.base.views.photoview;

import android.widget.ImageView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: Util */
public class l {
    public static ChangeQuickRedirect changeQuickRedirect;

    static void a(float minZoom, float midZoom, float maxZoom) {
        Object[] objArr = {new Float(minZoom), new Float(midZoom), new Float(maxZoom)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 949, new Class[]{cls, cls, cls}, Void.TYPE).isSupported) {
            if (minZoom >= midZoom) {
                throw new IllegalArgumentException("Minimum zoom has to be less than Medium zoom. Call setMinimumZoom() with a more appropriate value");
            } else if (midZoom >= maxZoom) {
                throw new IllegalArgumentException("Medium zoom has to be less than Maximum zoom. Call setMaximumZoom() with a more appropriate value");
            }
        }
    }

    static boolean c(ImageView imageView) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{imageView}, (Object) null, changeQuickRedirect, true, 950, new Class[]{ImageView.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return imageView.getDrawable() != null;
    }

    static boolean d(ImageView.ScaleType scaleType) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{scaleType}, (Object) null, changeQuickRedirect, true, 951, new Class[]{ImageView.ScaleType.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (scaleType == null) {
            return false;
        }
        switch (a.a[scaleType.ordinal()]) {
            case 1:
                throw new IllegalStateException("Matrix scale type is not supported");
            default:
                return true;
        }
    }

    /* compiled from: Util */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[ImageView.ScaleType.values().length];
            a = iArr;
            try {
                iArr[ImageView.ScaleType.MATRIX.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    static int b(int action) {
        return (65280 & action) >> 8;
    }
}

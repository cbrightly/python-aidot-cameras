package com.leedarson.serviceimpl.business.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.base.utils.c;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressLint({"StaticFieldLeak"})
/* compiled from: PhotoUtils.kt */
public final class PhotoUtils {
    @NotNull
    public static final PhotoUtils INSTANCE = new PhotoUtils();
    public static ChangeQuickRedirect changeQuickRedirect;

    private PhotoUtils() {
    }

    @Nullable
    public final Bitmap takeScreenShot(int i, int i2, int i3, int i4) {
        Object[] objArr = {new Integer(i), new Integer(i2), new Integer(i3), new Integer(i4)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, cls, cls, cls};
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7346, clsArr, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        int y = i2;
        int height = i4;
        int x = i;
        int width = i3;
        WVJBWebView webView = c.h().j();
        if (webView == null) {
            return null;
        }
        boolean preDrawingCacheEnable = webView.isDrawingCacheEnabled();
        webView.destroyDrawingCache();
        webView.setDrawingCacheEnabled(true);
        webView.buildDrawingCache();
        Bitmap bitmap = webView.getDrawingCache();
        if (bitmap != null && width > 0 && height > 0) {
            float density = Resources.getSystem().getDisplayMetrics().density;
            bitmap = Bitmap.createBitmap(bitmap, (int) (((float) x) * density), (int) (((float) y) * density), (int) (((float) width) * density), (int) (((float) height) * density));
        }
        webView.setDrawingCacheEnabled(preDrawingCacheEnable);
        return bitmap;
    }

    @Nullable
    public final Uri saveScreenShot(@NotNull Activity activity, int x, int y, int width, int height) {
        Object[] objArr = {activity, new Integer(x), new Integer(y), new Integer(width), new Integer(height)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {Activity.class, cls, cls, cls, cls};
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7347, clsArr, Uri.class);
        if (proxy.isSupported) {
            return (Uri) proxy.result;
        }
        k.e(activity, "activity");
        Bitmap bitmap = takeScreenShot(x, y, width, height);
        k.c(bitmap);
        return ImageExt.saveToAlbum(bitmap, activity, "leedarson_screenshot_" + System.currentTimeMillis() + ".jpg", (String) null, 100);
    }
}

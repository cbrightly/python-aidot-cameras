package com.leedarson.base.utils;

import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: AppUtils */
public class d {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static int b(Context context, float dipValue) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, new Float(dipValue)}, (Object) null, changeQuickRedirect, true, 444, new Class[]{Context.class, Float.TYPE}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        try {
            return (int) ((dipValue * context.getResources().getDisplayMetrics().density) + 0.5f);
        } catch (Exception e) {
            return (int) dipValue;
        }
    }

    public static int e(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 446, new Class[]{Context.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        WindowManager wm = (WindowManager) context.getSystemService("window");
        DisplayMetrics var2 = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(var2);
        }
        return var2.widthPixels;
    }

    public static int d(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 447, new Class[]{Context.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        WindowManager wm = (WindowManager) context.getSystemService("window");
        DisplayMetrics var2 = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(var2);
        }
        return var2.heightPixels;
    }

    public static String a(String string) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{string}, (Object) null, changeQuickRedirect, true, 448, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 255) < 16) {
                    hex.append("0");
                }
                hex.append(Integer.toHexString(b & 255));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String c(String url) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{url}, (Object) null, changeQuickRedirect, true, 449, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        try {
            Uri uri = Uri.parse(url);
            if (uri != null) {
                return uri.getHost();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}

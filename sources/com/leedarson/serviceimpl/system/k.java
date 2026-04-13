package com.leedarson.serviceimpl.system;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.util.Base64;
import androidx.core.net.MailTo;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.secret.JNIUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import net.sqlcipher.database.SQLiteDatabase;

/* compiled from: Utils */
public class k {
    private static final String a = JNIUtil.getInstance().getStr12();
    public static ChangeQuickRedirect changeQuickRedirect;

    public static int i(Context context, float pxValue) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, new Float(pxValue)}, (Object) null, changeQuickRedirect, true, 8848, new Class[]{Context.class, Float.TYPE}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int d(Context context, float dpValue) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, new Float(dpValue)}, (Object) null, changeQuickRedirect, true, 8849, new Class[]{Context.class, Float.TYPE}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static boolean h(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 8856, new Class[]{Context.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        LocationManager locationManager = (LocationManager) context.getApplicationContext().getSystemService(FirebaseAnalytics.Param.LOCATION);
        return locationManager.isProviderEnabled("gps") || locationManager.isProviderEnabled("network");
    }

    public static void a(Context context, String phoneNum) {
        Class[] clsArr = {Context.class, String.class};
        if (!PatchProxy.proxy(new Object[]{context, phoneNum}, (Object) null, changeQuickRedirect, true, 8857, clsArr, Void.TYPE).isSupported) {
            Intent intent = new Intent("android.intent.action.DIAL");
            intent.setData(Uri.parse("tel:" + phoneNum));
            context.startActivity(intent);
        }
    }

    public static void j(Context context, String address, String title, String content) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{context, address, title, content}, (Object) null, changeQuickRedirect, true, 8858, new Class[]{Context.class, cls, cls, cls}, Void.TYPE).isSupported) {
            Intent emailIntent = new Intent("android.intent.action.SENDTO", Uri.parse(MailTo.MAILTO_SCHEME + address));
            emailIntent.putExtra("android.intent.extra.EMAIL", address);
            emailIntent.putExtra("android.intent.extra.SUBJECT", title);
            emailIntent.putExtra("android.intent.extra.TEXT", content);
            context.startActivity(Intent.createChooser(emailIntent, "Select Email"));
        }
    }

    public static void g(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 8861, new Class[]{Context.class}, Void.TYPE).isSupported) {
            Intent intent = new Intent("android.settings.SETTINGS");
            intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            context.startActivity(intent);
        }
    }

    public static String e(String sSrc, String sKey) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{sSrc, sKey}, (Object) null, changeQuickRedirect, true, 8862, new Class[]{cls, cls}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        } else if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        } else {
            SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes("utf-8"), "AES");
            Cipher cipher = Cipher.getInstance(JNIUtil.getInstance().getStr11());
            cipher.init(1, skeySpec);
            return Base64.encodeToString(cipher.doFinal(sSrc.getBytes("utf-8")), 2);
        }
    }

    public static String c(String sSrc, String sKey) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{sSrc, sKey}, (Object) null, changeQuickRedirect2, true, 8863, new Class[]{cls, cls}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (sKey == null) {
            try {
                System.out.print("Key为空null");
                return null;
            } catch (Exception ex) {
                System.out.println(ex.toString());
                return null;
            }
        } else if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        } else {
            SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes("utf-8"), "AES");
            Cipher cipher = Cipher.getInstance(JNIUtil.getInstance().getStr11());
            cipher.init(2, skeySpec);
            try {
                return new String(cipher.doFinal(Base64.decode(sSrc, 2)), "utf-8");
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        }
    }

    public static Map<String, String> f(Uri mUrl) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{mUrl}, (Object) null, changeQuickRedirect, true, 8865, new Class[]{Uri.class}, Map.class);
        if (proxy.isSupported) {
            return (Map) proxy.result;
        }
        Map<String, String> query_pairs = new LinkedHashMap<>();
        if (mUrl == null) {
            return query_pairs;
        }
        try {
            String query = mUrl.getQuery();
            if (query == null) {
                return query_pairs;
            }
            if (query.contains("url=")) {
                int index = query.indexOf("url=");
                query_pairs.put("url", URLDecoder.decode(query.substring(index + 4), "UTF-8"));
                query = query.substring(0, index);
            }
            if (query.length() > 0) {
                for (String pair : query.split("&")) {
                    int idx = pair.indexOf("=");
                    if (idx > 0 && idx < pair.length() - 1) {
                        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
                    }
                }
            }
            return query_pairs;
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public static void b(Activity activity, String content) {
        Class[] clsArr = {Activity.class, String.class};
        if (!PatchProxy.proxy(new Object[]{activity, content}, (Object) null, changeQuickRedirect, true, 8866, clsArr, Void.TYPE).isSupported) {
            ((ClipboardManager) activity.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Label", content));
        }
    }
}

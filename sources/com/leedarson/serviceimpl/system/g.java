package com.leedarson.serviceimpl.system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.verify.domain.DomainVerificationManager;
import android.content.pm.verify.domain.DomainVerificationUserState;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.w;
import com.leedarson.base.views.common.dialogs.a;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import net.sqlcipher.database.SQLiteDatabase;
import timber.log.a;

/* compiled from: AndroidSCompat */
public class g {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void d(String pkgName, String schemeUrl, String marketUrl, Activity activity) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{pkgName, schemeUrl, marketUrl, activity}, (Object) null, changeQuickRedirect, true, 8793, new Class[]{cls, cls, cls, Activity.class}, Void.TYPE).isSupported) {
            if (!a(pkgName)) {
                c(pkgName, schemeUrl, marketUrl, activity);
            } else if (!b(activity)) {
                e(pkgName, schemeUrl, activity);
            } else {
                c(pkgName, schemeUrl, marketUrl, activity);
            }
        }
    }

    public static void e(String pkgName, String str, Activity activity) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{pkgName, str, activity}, (Object) null, changeQuickRedirect, true, 8794, new Class[]{cls, cls, Activity.class}, Void.TYPE).isSupported) {
            com.leedarson.base.views.common.dialogs.a actionDialog = new com.leedarson.base.views.common.dialogs.a(activity);
            String app = "Amazon Alexa";
            if (pkgName.equals("com.google.android.apps.chromecast.app")) {
                app = "Google Assistant";
            }
            String contentTxt = String.format(Locale.US, PubUtils.getString(activity, R$string.applink_tip), new Object[]{app});
            actionDialog.i(PubUtils.getString(activity, R$string.applink_title));
            actionDialog.d(PubUtils.getString(activity, R$string.cancel));
            actionDialog.f(PubUtils.getString(activity, R$string.per_guide_go_setting));
            actionDialog.h(contentTxt);
            actionDialog.c(new a(activity));
            actionDialog.show();
        }
    }

    /* compiled from: AndroidSCompat */
    public class a implements a.c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Activity a;

        a(Activity activity) {
            this.a = activity;
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8798, new Class[0], Void.TYPE).isSupported) {
                w.J(this.a);
            }
        }

        public void onCancel() {
        }
    }

    public static void c(String pkgName, String schemeUrl, String marketUrl, Activity activity) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{pkgName, schemeUrl, marketUrl, activity}, (Object) null, changeQuickRedirect, true, 8795, new Class[]{cls, cls, cls, Activity.class}, Void.TYPE).isSupported) {
            try {
                Log.i("zqr", "openThirdApe schemeUrl:" + schemeUrl);
                Log.i("zqr", "openThirdApe after  schemeUrl:" + schemeUrl);
                Intent i = new Intent("android.intent.action.VIEW", Uri.parse(schemeUrl));
                i.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                if (!TextUtils.isEmpty(pkgName) && w.T(BaseApplication.b(), pkgName, 1L)) {
                    i.setPackage(pkgName);
                }
                activity.startActivity(i);
            } catch (Exception e) {
                if (!TextUtils.isEmpty(marketUrl)) {
                    Intent i2 = new Intent("android.intent.action.VIEW", Uri.parse(marketUrl));
                    i2.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                    activity.startActivity(i2);
                }
            }
        }
    }

    public static boolean a(String pkgName) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{pkgName}, (Object) null, changeQuickRedirect, true, 8796, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return "com.amazon.dee.app".equals(pkgName) || "com.google.android.apps.chromecast.app".equals(pkgName);
    }

    public static boolean b(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 8797, new Class[]{Context.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (Build.VERSION.SDK_INT >= 31) {
            try {
                DomainVerificationUserState state = ((DomainVerificationManager) context.getApplicationContext().getSystemService("domain_verification")).getDomainVerificationUserState(context.getPackageName());
                Map<String, Integer> hostToStateMap = state.getHostToStateMap();
                boolean allowed = state.isLinkHandlingAllowed();
                a.b g = timber.log.a.g("AndroidS");
                g.h("isLinkHandlingAllowed:" + allowed + "," + hostToStateMap, new Object[0]);
                boolean addLinks = false;
                Iterator<String> iterator = hostToStateMap.keySet().iterator();
                while (true) {
                    if (!iterator.hasNext()) {
                        break;
                    }
                    String linkKey = iterator.next();
                    if ("apple-app.arnoo.com".equals(linkKey) || "www.innr.com".equals(linkKey) || "applink.aidot.com".equals(linkKey)) {
                        Integer linkState = hostToStateMap.get(linkKey);
                        if (linkState.intValue() == 2 || linkState.intValue() == 1) {
                            addLinks = true;
                        }
                    }
                }
                if (!allowed || !addLinks) {
                    return false;
                }
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}

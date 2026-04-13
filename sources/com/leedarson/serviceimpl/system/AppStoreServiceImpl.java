package com.leedarson.serviceimpl.system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.leedarson.serviceinterface.AppStoreService;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import net.sqlcipher.database.SQLiteDatabase;
import org.greenrobot.eventbus.c;
import timber.log.a;

public class AppStoreServiceImpl implements AppStoreService {
    public static ChangeQuickRedirect changeQuickRedirect;

    public void handleData(Activity activity, String callbackKey, String action, String str) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{activity, callbackKey, action, str}, this, changeQuickRedirect, false, 8808, new Class[]{Activity.class, cls, cls, cls}, Void.TYPE).isSupported) {
            if (AppStoreService.ACTION_OPEN_USER_REVIEWS.contains(action)) {
                a(activity);
                c.c().l(new JsBridgeCallbackEvent(callbackKey, "{\"code\":200}"));
            }
        }
    }

    private void a(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 8809, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.iotsolution.aidot"));
                intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                activity.startActivity(intent);
            } catch (Exception e) {
                a.c("您的手机没有安装Android应用市场  e= " + e.toString(), new Object[0]);
            }
        }
    }

    public void init(Context context) {
    }
}

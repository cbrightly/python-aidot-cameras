package com.leedarson.serviceimpl.system;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class ScreenOffAdminReceiver extends DeviceAdminReceiver {
    public static ChangeQuickRedirect changeQuickRedirect;

    private void a(Context context, String msg) {
        if (!PatchProxy.proxy(new Object[]{context, msg}, this, changeQuickRedirect, false, 8820, new Class[]{Context.class, String.class}, Void.TYPE).isSupported) {
            Toast.makeText(context, msg, 0).show();
        }
    }

    public void onEnabled(Context context, Intent intent) {
        Class[] clsArr = {Context.class, Intent.class};
        if (!PatchProxy.proxy(new Object[]{context, intent}, this, changeQuickRedirect, false, 8821, clsArr, Void.TYPE).isSupported) {
            a(context, "设备管理器使能");
        }
    }

    public void onDisabled(Context context, Intent intent) {
        Class[] clsArr = {Context.class, Intent.class};
        if (!PatchProxy.proxy(new Object[]{context, intent}, this, changeQuickRedirect, false, 8822, clsArr, Void.TYPE).isSupported) {
            a(context, "设备管理器没有使能");
        }
    }
}

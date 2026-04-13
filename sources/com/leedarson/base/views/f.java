package com.leedarson.base.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import com.leedarson.base.views.common.dialogs.a;
import com.leedarson.module_base.R$string;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;
import pub.devrel.easypermissions.b;

/* compiled from: LdsPermissionDialogStrategy */
public class f extends b {
    public static ChangeQuickRedirect changeQuickRedirect;

    public Dialog a(Activity activity, String confirm, String cancel, String content) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{activity, confirm, cancel, content}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART, new Class[]{Activity.class, cls, cls, cls}, Dialog.class);
        if (proxy.isSupported) {
            return (Dialog) proxy.result;
        }
        com.leedarson.base.views.common.dialogs.a actionDialog = new com.leedarson.base.views.common.dialogs.a(activity);
        d(activity, actionDialog);
        actionDialog.i(PubUtils.getString(activity, R$string.permission_title_setting));
        actionDialog.d(cancel);
        actionDialog.f(confirm);
        actionDialog.h(content);
        actionDialog.c(new a());
        return actionDialog;
    }

    /* compiled from: LdsPermissionDialogStrategy */
    public class a implements a.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void a() {
            DialogInterface.OnClickListener listener;
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 771, new Class[0], Void.TYPE).isSupported && (listener = f.this.b()) != null) {
                listener.onClick((DialogInterface) null, -1);
            }
        }

        public void onCancel() {
            DialogInterface.OnClickListener listener;
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 772, new Class[0], Void.TYPE).isSupported && (listener = f.this.b()) != null) {
                listener.onClick((DialogInterface) null, -2);
            }
        }
    }

    public boolean c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTOP, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : super.c();
    }

    private void d(Activity activity, com.leedarson.base.views.common.dialogs.a dialog) {
        Class[] clsArr = {Activity.class, com.leedarson.base.views.common.dialogs.a.class};
        if (!PatchProxy.proxy(new Object[]{activity, dialog}, this, changeQuickRedirect, false, 770, clsArr, Void.TYPE).isSupported) {
            String prefString = SharePreferenceUtils.getPrefString(activity, "repositoryName", "");
            String themeColor = SharePreferenceUtils.getPrefString(activity, "themeColor", "#FDBA14");
            dialog.g(themeColor);
            dialog.e(themeColor);
        }
    }
}

package com.leedarson.base.utils;

import com.alibaba.android.arouter.launcher.a;
import com.leedarson.serviceinterface.LoggerService;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.List;

/* compiled from: PermissionDelegate */
public class s {
    public static ChangeQuickRedirect changeQuickRedirect;

    public void b(int requestCode, List<String> list) {
        Object[] objArr = {new Integer(requestCode), list};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 511, new Class[]{Integer.TYPE, List.class}, Void.TYPE).isSupported) {
            LoggerService loggerService = (LoggerService) a.c().g(LoggerService.class);
            if (loggerService != null) {
                switch (requestCode) {
                    case NeedPermissionEvent.PER_GET_LOCATION_DISCOVER_DEV /*137*/:
                    case NeedPermissionEvent.PER_GET_LOCATION_BLE /*139*/:
                        loggerService.reportPermissionSensorsData("ClickLocationSystemSettings", true);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public void a(int requestCode, List<String> list) {
        if (!PatchProxy.proxy(new Object[]{new Integer(requestCode), list}, this, changeQuickRedirect, false, 512, new Class[]{Integer.TYPE, List.class}, Void.TYPE).isSupported) {
            LoggerService loggerService = (LoggerService) a.c().g(LoggerService.class);
            if (loggerService != null) {
                switch (requestCode) {
                    case NeedPermissionEvent.PER_GET_LOCATION_DISCOVER_DEV /*137*/:
                    case NeedPermissionEvent.PER_GET_LOCATION_BLE /*139*/:
                        loggerService.reportPermissionSensorsData("ClickLocationSystemSettings", false);
                        return;
                    default:
                        return;
                }
            }
        }
    }
}

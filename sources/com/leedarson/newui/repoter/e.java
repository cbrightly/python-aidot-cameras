package com.leedarson.newui.repoter;

import com.leedarson.base.logger.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: IPCLiveReporter */
public class e extends a {
    public static ChangeQuickRedirect changeQuickRedirect;

    public void k(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4531, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.c("tutk", "IPCLiveReporter.Sufun tutkConnectFail  " + message);
            b(message).x("IPC_LIVE_TRACE_ID").s("tutkConnectFail").a().b();
        }
    }

    public void e(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4535, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("IPC_CHECK_SDCARD_STATUES").s("checkSdCardStatusStart").a().b();
        }
    }

    public void g(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4537, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("IPC_CHECK_SDCARD_STATUES").s("sdCardStatusCheckFail").a().b();
        }
    }

    public void i(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4539, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("IPC_LIVE_ALARM_TRACE_ID").s("startAlarmStateChange").a().b();
        }
    }

    public void j(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4541, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("IPC_LIVE_ALARM_TRACE_ID").s("startAlarmSuccess").a().b();
        }
    }

    public void h(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4542, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("IPC_LIVE_ALARM_TRACE_ID").s("startAlarmFail").a().b();
        }
    }

    public void f(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4543, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("IPC_LIVE_PROPS_CHANGE_TRACE_ID").s("propsChangeStart").a().b();
        }
    }
}

package com.leedarson.newui.repoter;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: IPCPlayBackReporter */
public class f extends a {
    public static ChangeQuickRedirect changeQuickRedirect;

    public void t(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4546, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("START_PLAY_RECORD_EVENT").s("startPlayRecordVideo").a().b();
        }
    }

    public void l(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4547, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("START_PLAY_RECORD_EVENT").s("getConnectParams").a().b();
        }
    }

    public void n(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4548, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("START_PLAY_RECORD_EVENT").s("getConnectParamsSuccess").a().b();
        }
    }

    public void m(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4549, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("START_PLAY_RECORD_EVENT").s("getConnectParamsFail").a().b();
        }
    }

    public void s(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4550, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("START_PLAY_RECORD_EVENT").s("playWithLocalCache").a().b();
        }
    }

    public void i(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4551, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("START_PLAY_RECORD_EVENT").s("fetchPlayBackRecordResource").a().b();
        }
    }

    public void k(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4552, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("START_PLAY_RECORD_EVENT").s("fetchPlayBackRecordResourceSuccess").a().b();
        }
    }

    public void j(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4553, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("START_PLAY_RECORD_EVENT").s("fetchPlayBackRecordResourceFail").a().b();
        }
    }

    public void o(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4554, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("START_PLAY_RECORD_EVENT").s("nettyConnectStateChangeLog").a().b();
        }
    }

    public void r(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4555, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("START_PLAY_RECORD_EVENT").s("nettyStartLogin").a().b();
        }
    }

    public void q(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4556, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("START_PLAY_RECORD_EVENT").s("nettyLoginSuccess").a().b();
        }
    }

    public void p(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4557, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("START_PLAY_RECORD_EVENT").s("nettyLoginFail").a().b();
        }
    }

    public void g(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4558, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("START_PLAY_RECORD_EVENT").s("cloudStreamStateChangeSuccess").a().b();
        }
    }

    public void f(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4559, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("START_PLAY_RECORD_EVENT").s("cloudStreamParseEnd").a().b();
        }
    }

    public void h(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4560, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("START_PLAY_RECORD_EVENT").s("cloudStremEnd").a().b();
        }
    }

    public void e(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4561, new Class[]{String.class}, Void.TYPE).isSupported) {
            b(message).x("START_PLAY_RECORD_EVENT").s("cloudStreamFail").a().b();
        }
    }
}

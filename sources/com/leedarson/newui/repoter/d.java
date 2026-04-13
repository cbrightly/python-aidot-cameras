package com.leedarson.newui.repoter;

import com.leedarson.log.elk.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: IPCLiveKVSReporter */
public class d extends a {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String h = "IPC_LIVE_KVS_CONNECT_TRACE_ID";

    public void x(String message, String bzref, String desc) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{message, bzref, desc}, this, changeQuickRedirect, false, 4490, clsArr, Void.TYPE).isSupported) {
            a s = b(bzref + message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("startUseKVSLiveRoom");
            s.u("desc", "进入直播间" + desc).u("logVersion", "0.1.0.0825155016").a().b();
        }
    }

    public void i(String message, String ref) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{message, ref}, this, changeQuickRedirect, false, 4491, clsArr, Void.TYPE).isSupported) {
            a u = b(ref + message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("exitKVSLiveRoom").u("logVersion", "0.1.0.0825155016");
            u.u("desc", "离开直播间" + ref).a().b();
        }
    }

    public void j(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4492, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("KVSPre", "deviceId=" + this.b + "IPC_LIVE_KVS_CONNECT_TRACE_ID.fetchKvsParams--->" + message);
            b(message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("fetchKvsParams").a().b();
        }
    }

    public void l(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4493, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("KVSPre", "deviceId=" + this.b + "IPC_LIVE_KVS_CONNECT_TRACE_ID.fetchKvsParamsSuccess--->" + message);
            b(message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("fetchKvsParamsSuccess").a().b();
        }
    }

    public void k(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4494, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("KVSPre", "deviceId=" + this.b + "IPC_LIVE_KVS_CONNECT_TRACE_ID.fetchKvsParamsFail--->" + message);
            b(message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("fetchKvsParamsFail").a().b();
        }
    }

    public void e(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4495, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("KVSPre", "deviceId=" + this.b + "IPC_LIVE_KVS_CONNECT_TRACE_ID.createKvsChannel--->" + message);
            b(message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("createKvsChannel").a().b();
        }
    }

    public void m(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4496, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("KVSPre", "deviceId=" + this.b + "IPC_LIVE_KVS_CONNECT_TRACE_ID.kvsConnect--->" + message);
            b(message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("kvsConnect").a().b();
        }
    }

    public void t(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4497, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("KVSPre", "deviceId=" + this.b + "IPC_LIVE_KVS_CONNECT_TRACE_ID.kvsSdpOffer--->要看详细可以在这边打开");
            b(message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("kvsSdpOffer").a().b();
        }
    }

    public void s(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4498, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("KVSPre", "deviceId=" + this.b + "IPC_LIVE_KVS_CONNECT_TRACE_ID.kvsSdpAnswer--->要看详细可以在这边打开");
            b(message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("kvsSdpAnswer").a().b();
        }
    }

    public void r(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4499, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("KVSPre", "deviceId=" + this.b + "IPC_LIVE_KVS_CONNECT_TRACE_ID.kvsIceCandidate--->要看详细可以在这边打开");
            b(message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("kvsIceCandidate").a().b();
        }
    }

    public void q(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4500, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("KVSPre", "deviceId=" + this.b + "IPC_LIVE_KVS_CONNECT_TRACE_ID.kvsConnectOpen--->要看详细可以在这边打开");
            b(message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("kvsConnectOpen").a().b();
        }
    }

    public void n(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4501, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("KVSPre", "deviceId=" + this.b + "IPC_LIVE_KVS_CONNECT_TRACE_ID.kvsConnectClose--->要看详细可以在这边打开");
            b(message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("kvsConnectClose").a().b();
        }
    }

    public void o(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4503, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("KVSPre", "deviceId=" + this.b + "IPC_LIVE_KVS_CONNECT_TRACE_ID.kvsConnectError--->" + message);
            b(message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("kvsConnectError").a().b();
        }
    }

    public void p(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4504, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("KVSPre", "deviceId=" + this.b + "IPC_LIVE_KVS_CONNECT_TRACE_ID.kvsConnectException--->" + message);
            b(message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("kvsConnectException").a().b();
        }
    }

    public void u(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4505, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("KVSPre", "deviceId=" + this.b + "IPC_LIVE_KVS_CONNECT_TRACE_ID.kvsSetResolution--->" + message);
            b(message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("kvsSetResolution").a().b();
        }
    }

    public void v(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4506, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("KVSPre", "deviceId=" + this.b + "IPC_LIVE_KVS_CONNECT_TRACE_ID.kvsSetResolutionSuccess--->" + message);
            b(message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("kvsSetResolutionSuccess").a().b();
        }
    }

    public void w(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4508, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("KVSPre", "deviceId=" + this.b + "IPC_LIVE_KVS_CONNECT_TRACE_ID.startCreateSdpOffer--->" + message);
            b(message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("startCreateSdpOffer").a().b();
        }
    }

    public void g(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4509, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("KVSPre", "deviceId=" + this.b + "IPC_LIVE_KVS_CONNECT_TRACE_ID.createSdpOfferSuccess--->要看详细可以在这边打开");
            b(message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("createSdpOfferSuccess").a().b();
        }
    }

    public void f(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4510, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("KVSPre", "deviceId=" + this.b + "IPC_LIVE_KVS_CONNECT_TRACE_ID.createSdpOfferFail--->" + message);
            b(message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("createSdpOfferFail").a().b();
        }
    }

    public void y(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4511, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("KVSPre", "deviceId=" + this.b + "IPC_LIVE_KVS_CONNECT_TRACE_ID.webRtcSdpStateChange--->" + message);
            b(message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("webRtcSdpStateChange").a().b();
        }
    }

    public void h(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4512, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("KVSPre", "deviceId=" + this.b + "IPC_LIVE_KVS_CONNECT_TRACE_ID.dataChannelStateChanged--->" + message);
            b(message).x("IPC_LIVE_KVS_CONNECT_TRACE_ID").s("dataChannelStateChanged").a().b();
        }
    }

    public void z(String deviceId, String message, int code) {
        Class<String> cls = String.class;
        Object[] objArr = {deviceId, message, new Integer(code)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4517, new Class[]{cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            d(deviceId);
            c(message).x("LdsLiveViewWakeUpTrackId").u("deviceId", deviceId).u("code", Integer.valueOf(code)).a().b();
        }
    }
}

package com.leedarson.newui.repoter;

import android.content.Context;
import android.util.Log;
import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.log.f;
import com.leedarson.log.mgr.u;
import com.leedarson.log.tracker.BaseStepBean;
import com.leedarson.smartcamera.bean.IPCLiveStepBean;
import com.leedarson.smartcamera.bean.KVSParamBean;
import com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
import meshsdk.BaseResp;

/* compiled from: NewIPCLiveKVSReporter */
public class h {
    private static h a;
    public static ChangeQuickRedirect changeQuickRedirect;
    private String b;
    private String c;
    /* access modifiers changed from: private */
    public String d;
    private long e;

    public static h e() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 4584, new Class[0], h.class);
        if (proxy.isSupported) {
            return (h) proxy.result;
        }
        if (a == null) {
            synchronized (h.class) {
                if (a == null) {
                    a = new h();
                }
            }
        }
        return a;
    }

    /* compiled from: NewIPCLiveKVSReporter */
    public class a extends c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ IpcDeviceBean i;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(Context context, IpcDeviceBean ipcDeviceBean) {
            super(context);
            this.i = ipcDeviceBean;
        }

        public HashMap<String, Object> f() {
            int i2 = 0;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4600, new Class[0], HashMap.class);
            if (proxy.isSupported) {
                return (HashMap) proxy.result;
            }
            HashMap<String, Object> fields = new HashMap<>();
            try {
                if (this.i != null) {
                    fields.put("deviceId", h.this.d);
                    fields.put("liveType", this.i.props.liveType);
                    fields.put("hardwareVersion", this.i.hardwareVersion);
                    fields.put("firmwareVersion", this.i.firmwareVersion);
                    if (this.i.online.booleanValue()) {
                        i2 = 1;
                    }
                    fields.put("deviceOnlineStatue", Integer.valueOf(i2));
                    fields.put("modelId", this.i.modelId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            fields.put("IPCSDKVersion", "");
            fields.put("module", "LiveKVS");
            return fields;
        }
    }

    public void h(Context context, IpcDeviceBean deviceBean) {
        if (!PatchProxy.proxy(new Object[]{context, deviceBean}, this, changeQuickRedirect, false, 4585, new Class[]{Context.class, IpcDeviceBean.class}, Void.TYPE).isSupported) {
            Log.d("NewIPCLiveKVSReporter", "newLiveKVSTracker: ");
            c tracker = new a(context, deviceBean);
            if (deviceBean != null) {
                this.d = deviceBean.id;
                String replace = UUID.randomUUID().toString().replace("-", "");
                this.c = replace;
                String format = String.format(Locale.US, "%s:%s", new Object[]{replace, this.d});
                this.b = format;
                com.leedarson.base.logger.a.a("NewIPCLiveKVSReporter", format);
                tracker.j(this.b, "LdsPlayer", "FirstFrameRender");
                u.d().b(tracker);
            }
        }
    }

    public void d(String str, long duration, int i, String response, String str2) {
        Class<String> cls = String.class;
        Object[] objArr = {str, new Long(duration), new Integer(i), response, str2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4586, new Class[]{cls, Long.TYPE, Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
            String message = str2;
            int code = i;
            String deviceId = str;
            Log.d("NewIPCLiveKVSReporter", "fetchKvsParams: ");
            IPCLiveStepBean bean = new IPCLiveStepBean("fetchKvsParams", code);
            bean.putRequestParams("deviceId", deviceId);
            bean.putRequestParams("traceId", this.b);
            bean.setResponse(response);
            bean.setDuration(duration);
            bean.setDesc(message);
            u d2 = u.d();
            Locale locale = Locale.US;
            d2.a(String.format(locale, "%s:%s", new Object[]{this.c, deviceId}), bean);
            if (code != 200) {
                u.d().e(String.format(locale, "%s:%s", new Object[]{this.c, deviceId}));
            }
            com.leedarson.base.logger.a.a("NewIPCLiveKVSReporter", "Step ：fetchKvsParams - " + message);
        }
    }

    public void b(String deviceId, KVSParamBean param, String message) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, param, message}, this, changeQuickRedirect, false, 4587, new Class[]{cls, KVSParamBean.class, cls}, Void.TYPE).isSupported) {
            Log.d("NewIPCLiveKVSReporter", "createKvsChannel: ");
            IPCLiveStepBean bean = new IPCLiveStepBean("createWebRtcChannel", 200);
            bean.putRequestParams("accessKeyId", param.accessKeyId);
            bean.putRequestParams("sessionToken", param.sessionToken);
            bean.putRequestParams("channelArn", param.channelArn);
            bean.putRequestParams("region", param.region);
            bean.setDesc(message);
            u.d().a(String.format(Locale.US, "%s:%s", new Object[]{this.c, deviceId}), bean);
        }
    }

    public void c(String liveType, String str, KVSParamBean kVSParamBean, String str2) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{liveType, str, kVSParamBean, str2}, this, changeQuickRedirect, false, 4588, new Class[]{cls, cls, KVSParamBean.class, cls}, Void.TYPE).isSupported) {
            String deviceId = str;
            String message = str2;
            KVSParamBean param = kVSParamBean;
            f.b("NewIPCLiveKVSReporter", "createWebRTCChannel: ");
            IPCLiveStepBean bean = new IPCLiveStepBean("createWebRtcChannel", 200);
            bean.putRequestParams("liveType", liveType);
            if (param != null) {
                bean.putRequestParams("accessKeyId", param.accessKeyId);
                bean.putRequestParams("sessionToken", param.sessionToken);
                bean.putRequestParams("channelArn", param.channelArn);
                bean.putRequestParams("region", param.region);
            }
            bean.setDesc(message);
            u.d().a(String.format(Locale.US, "%s:%s", new Object[]{this.c, deviceId}), bean);
        }
    }

    public void f(String str, long j, String host, int i, String response, String message) {
        Class<String> cls = String.class;
        Object[] objArr = {str, new Long(j), host, new Integer(i), response, message};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4590, new Class[]{cls, Long.TYPE, cls, Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
            long duration = j;
            String deviceId = str;
            int code = i;
            Log.d("NewIPCLiveKVSReporter", "kvsConnect: ");
            this.e = duration;
            IPCLiveStepBean bean = new IPCLiveStepBean("webRtcConnect", code);
            bean.putRequestParams(SerializableCookie.HOST, host);
            bean.setResponse(response);
            bean.setDuration(duration);
            bean.setDesc(message);
            u d2 = u.d();
            Locale locale = Locale.US;
            d2.a(String.format(locale, "%s:%s", new Object[]{this.c, deviceId}), bean);
            if (code != 200) {
                u.d().e(String.format(locale, "%s:%s", new Object[]{this.c, deviceId}));
            }
        }
    }

    public void m(String deviceId, WebRtcServiceStateChangeLogBean stepBean) {
        if (!PatchProxy.proxy(new Object[]{deviceId, stepBean}, this, changeQuickRedirect, false, 4591, new Class[]{String.class, WebRtcServiceStateChangeLogBean.class}, Void.TYPE).isSupported) {
            Log.d("NewIPCLiveKVSReporter", "webRtcSignalChannelServiceInitStep: ");
            BaseStepBean baseStepBean = new BaseStepBean("webRtcConnect", stepBean.code);
            baseStepBean.setDuration(stepBean.rsponseTime - stepBean.requestTime);
            baseStepBean.setResponse(stepBean.endResponseObj.toString());
            baseStepBean.putRequestParams("requestData", stepBean.requestObj);
            baseStepBean.setCode(stepBean.code);
            baseStepBean.setDesc("连接webRtc信令服务");
            u d2 = u.d();
            Locale locale = Locale.US;
            d2.a(String.format(locale, "%s:%s", new Object[]{this.c, deviceId}), baseStepBean);
            if (stepBean.code != 0) {
                u.d().e(String.format(locale, "%s:%s", new Object[]{this.c, deviceId}));
            }
        }
    }

    public void k(String deviceId, WebRtcServiceStateChangeLogBean stepBean) {
        if (!PatchProxy.proxy(new Object[]{deviceId, stepBean}, this, changeQuickRedirect, false, 4592, new Class[]{String.class, WebRtcServiceStateChangeLogBean.class}, Void.TYPE).isSupported) {
            Log.d("NewIPCLiveKVSReporter", "webRtcIceConfigListServiceInitStep: ");
            BaseStepBean baseStepBean = new BaseStepBean("fetchLdsWebRTCIceList", stepBean.code);
            baseStepBean.setDuration(stepBean.rsponseTime - stepBean.requestTime);
            baseStepBean.setResponse(stepBean.endResponseObj.toString());
            baseStepBean.putRequestParams("requestData", stepBean.requestObj);
            baseStepBean.setCode(stepBean.code);
            baseStepBean.setDesc("IceConfig获取");
            u d2 = u.d();
            Locale locale = Locale.US;
            d2.a(String.format(locale, "%s:%s", new Object[]{this.c, deviceId}), baseStepBean);
            if (stepBean.code != 200) {
                u.d().e(String.format(locale, "%s:%s", new Object[]{this.c, deviceId}));
            }
        }
    }

    public void l(String deviceId, WebRtcServiceStateChangeLogBean stepBean) {
        if (!PatchProxy.proxy(new Object[]{deviceId, stepBean}, this, changeQuickRedirect, false, 4593, new Class[]{String.class, WebRtcServiceStateChangeLogBean.class}, Void.TYPE).isSupported) {
            Log.d("NewIPCLiveKVSReporter", "webRtcSdpOfferServiceInitStep: ");
            BaseStepBean baseStepBean = new BaseStepBean("kvsSdpOffer", stepBean.code);
            baseStepBean.setDuration(stepBean.rsponseTime - stepBean.requestTime);
            baseStepBean.setResponse(stepBean.endResponseObj.toString());
            baseStepBean.putRequestParams("requestData", stepBean.requestObj);
            baseStepBean.setDesc("webrtc sdp 媒体协商");
            u d2 = u.d();
            Locale locale = Locale.US;
            d2.a(String.format(locale, "%s:%s", new Object[]{this.c, deviceId}), baseStepBean);
            if (stepBean.code != 200) {
                u.d().e(String.format(locale, "%s:%s", new Object[]{this.c, deviceId}));
            }
        }
    }

    public void j(String deviceId, WebRtcServiceStateChangeLogBean stepBean) {
        if (!PatchProxy.proxy(new Object[]{deviceId, stepBean}, this, changeQuickRedirect, false, 4594, new Class[]{String.class, WebRtcServiceStateChangeLogBean.class}, Void.TYPE).isSupported) {
            Log.d("NewIPCLiveKVSReporter", "webRtcCandidateServiceInitStep: ");
            BaseStepBean baseStepBean = new BaseStepBean("kvsIceCandidate", stepBean.code);
            baseStepBean.setDuration(stepBean.rsponseTime - stepBean.requestTime);
            baseStepBean.setResponse(stepBean.endResponseObj.toString());
            baseStepBean.putRequestParams("requestData", stepBean.requestObj);
            baseStepBean.setDesc("webrtc 候选人获取");
            if (stepBean.code == 200) {
                u.d().a(String.format(Locale.US, "%s:%s", new Object[]{this.c, deviceId}), baseStepBean);
                return;
            }
            u d2 = u.d();
            Locale locale = Locale.US;
            d2.a(String.format(locale, "%s:%s", new Object[]{this.c, deviceId}), baseStepBean);
            u.d().e(String.format(locale, "%s:%s", new Object[]{this.c, deviceId}));
        }
    }

    public void g(String deviceId, String message) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, message}, this, changeQuickRedirect, false, 4595, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            Log.d("NewIPCLiveKVSReporter", "kvsIceConnectionChange: ");
            IPCLiveStepBean bean = new IPCLiveStepBean("kvsIceConnectionChange", BaseResp.ERR_MSG_SEND_FAIL);
            bean.setDesc(message);
            u d2 = u.d();
            Locale locale = Locale.US;
            d2.a(String.format(locale, "%s:%s", new Object[]{this.c, deviceId}), bean);
            u.d().e(String.format(locale, "%s:%s", new Object[]{this.c, deviceId}));
            com.leedarson.base.logger.a.a("NewIPCLiveKVSReporter", "Step ：kvsIceConnectionChange - " + message);
        }
    }

    public void i(String deviceId, String message) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, message}, this, changeQuickRedirect, false, 4597, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            Log.d("NewIPCLiveKVSReporter", "onWebRtcConnectionException: ");
            IPCLiveStepBean bean = new IPCLiveStepBean("WebRtcConnectException", 1002);
            bean.setDesc(message);
            u d2 = u.d();
            Locale locale = Locale.US;
            d2.a(String.format(locale, "%s:%s", new Object[]{this.c, deviceId}), bean);
            u.d().e(String.format(locale, "%s:%s", new Object[]{this.c, deviceId}));
        }
    }
}

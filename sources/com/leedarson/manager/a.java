package com.leedarson.manager;

import android.text.TextUtils;
import android.util.Log;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.log.f;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.smartcamera.bean.KVSParamBean;
import com.leedarson.smartcamera.kvswebrtc.f0;
import com.leedarson.smartcamera.reporters.WebRtcLogStepBean;
import com.leedarson.smartcamera.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: LdsChannelManager */
public class a {
    private static a a = null;
    public static ChangeQuickRedirect changeQuickRedirect;
    private ConcurrentHashMap<String, com.leedarson.smartcamera.sdk.a> b = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> c = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, f0> d = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> e = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Boolean> f = new ConcurrentHashMap<>();

    private a() {
    }

    public static a i() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 1383, new Class[0], a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        if (a == null) {
            synchronized (a.class) {
                if (a == null) {
                    a = new a();
                }
            }
        }
        return a;
    }

    public void c(String p2pId, com.leedarson.smartcamera.sdk.a ldsTutkChannel) {
        Class[] clsArr = {String.class, com.leedarson.smartcamera.sdk.a.class};
        if (!PatchProxy.proxy(new Object[]{p2pId, ldsTutkChannel}, this, changeQuickRedirect, false, 1384, clsArr, Void.TYPE).isSupported) {
            if (!TextUtils.isEmpty(p2pId) && ldsTutkChannel != null) {
                this.b.remove(p2pId);
                this.b.put(p2pId, ldsTutkChannel);
            }
        }
    }

    public com.leedarson.smartcamera.sdk.a l(String p2pId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{p2pId}, this, changeQuickRedirect, false, 1385, new Class[]{String.class}, com.leedarson.smartcamera.sdk.a.class);
        if (proxy.isSupported) {
            return (com.leedarson.smartcamera.sdk.a) proxy.result;
        }
        if (TextUtils.isEmpty(p2pId)) {
            return null;
        }
        return this.b.get(p2pId);
    }

    public void b(String deviceId, String p2pId) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, p2pId}, this, changeQuickRedirect, false, 1386, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            if (!TextUtils.isEmpty(p2pId) && !TextUtils.isEmpty(deviceId)) {
                this.c.remove(deviceId);
                this.c.put(deviceId, p2pId);
                this.f.put(deviceId, false);
            }
        }
    }

    public String k(String deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 1387, new Class[]{String.class}, String.class);
        return proxy.isSupported ? (String) proxy.result : this.c.get(deviceId);
    }

    public com.leedarson.smartcamera.sdk.a m(String deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 1388, new Class[]{String.class}, com.leedarson.smartcamera.sdk.a.class);
        if (proxy.isSupported) {
            return (com.leedarson.smartcamera.sdk.a) proxy.result;
        }
        return l(k(deviceId));
    }

    public boolean o(String deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 1389, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : this.f.get(deviceId).booleanValue();
    }

    public void a(String deviceId, f0 webRTCChannel) {
        if (!PatchProxy.proxy(new Object[]{deviceId, webRTCChannel}, this, changeQuickRedirect, false, 1390, new Class[]{String.class, f0.class}, Void.TYPE).isSupported) {
            if (!TextUtils.isEmpty(deviceId) && webRTCChannel != null) {
                f0 preWebRTCChannel = this.d.remove(deviceId);
                if (!(preWebRTCChannel == null || preWebRTCChannel.hashCode() == webRTCChannel.hashCode())) {
                    f.b("LdsChannelManager", "Remove previous webrtc channel deviceId" + deviceId);
                    preWebRTCChannel.N2();
                    preWebRTCChannel.I2(true);
                }
                this.d.put(deviceId, webRTCChannel);
                this.f.put(deviceId, true);
            }
        }
    }

    public void q(String deviceId) {
        f0 preWebRTCChannel;
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 1391, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (!TextUtils.isEmpty(deviceId) && (preWebRTCChannel = this.d.remove(deviceId)) != null) {
                f.b("LdsChannelManager", "Remove previous webrtc channel deviceId" + deviceId);
                preWebRTCChannel.N2();
                preWebRTCChannel.I2(true);
            }
        }
    }

    public void p(String deviceId, JSONObject jsonObject) {
        Class[] clsArr = {String.class, JSONObject.class};
        if (!PatchProxy.proxy(new Object[]{deviceId, jsonObject}, this, changeQuickRedirect, false, 1392, clsArr, Void.TYPE).isSupported) {
            for (String key : this.d.keySet()) {
                try {
                    if (!TextUtils.isEmpty(deviceId) && key.contains(deviceId)) {
                        Log.d("TAG", "notifyOfMqttMessageComing  key: " + key + "==" + jsonObject.toString());
                        this.d.get(key).u2(jsonObject);
                    }
                } catch (Exception e2) {
                    Log.e("LdsChannelManager", "notifyOfMqttMessageComing   e=" + e2.toString());
                }
            }
        }
    }

    public f0 j(String deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 1393, new Class[]{String.class}, f0.class);
        if (proxy.isSupported) {
            return (f0) proxy.result;
        }
        if (TextUtils.isEmpty(deviceId)) {
            return null;
        }
        return this.d.get(deviceId);
    }

    public f0 n(String str, String str2, String str3, KVSParamBean kVSParamBean, f0.r rVar) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str, str2, str3, kVSParamBean, rVar}, this, changeQuickRedirect, false, 1394, new Class[]{cls, cls, cls, KVSParamBean.class, f0.r.class}, f0.class);
        if (proxy.isSupported) {
            return (f0) proxy.result;
        }
        String liveType = str2;
        KVSParamBean param = kVSParamBean;
        String deviceId = str;
        String supportIpv6 = str3;
        f0.r mode = rVar;
        String userId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", "");
        f0 webRTCChannel = i().j(deviceId);
        if (Constans.IPC_LIVE_TYPE_LDS.equals(liveType)) {
            if (webRTCChannel == null) {
                return new f0(deviceId, userId, supportIpv6, mode);
            }
            return webRTCChannel;
        } else if (param == null) {
            return null;
        } else {
            if (webRTCChannel == null) {
                return new f0(liveType, param, userId, deviceId, supportIpv6, mode);
            }
            if (!param.accessKeyId.equals(webRTCChannel.S0())) {
                return new f0(liveType, param, userId, deviceId, supportIpv6, mode);
            }
            return webRTCChannel;
        }
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1395, new Class[0], Void.TYPE).isSupported) {
            e.c("", "disConnectAll" + this.b.size());
            try {
                for (com.leedarson.smartcamera.sdk.a channel : this.b.values()) {
                    channel.C0();
                }
                this.b.clear();
                for (f0 kvsWebRTCChannel : this.d.values()) {
                    kvsWebRTCChannel.I2(true);
                }
                this.d.clear();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1396, new Class[0], Void.TYPE).isSupported) {
            new ArrayList();
            for (String key : this.d.keySet()) {
                f0 channel = this.d.get(key);
                if (channel.a1() == f0.q.IDLE) {
                    a.b g = timber.log.a.g("LdsChannelManager");
                    g.h("[网络切换] IPC 服务开始释放断开的连接 ---> " + key, new Object[0]);
                    channel.I2(true);
                }
            }
        }
    }

    public void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1397, new Class[0], Void.TYPE).isSupported) {
            for (f0 kvsWebRTCChannel : this.d.values()) {
                kvsWebRTCChannel.I2(true);
            }
            this.d.clear();
        }
    }

    public void h(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 1398, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                com.leedarson.smartcamera.sdk.a channel = m(deviceId);
                if (channel != null) {
                    channel.C0();
                }
                this.b.remove(channel);
                f0 webRTCChannel = j(deviceId);
                if (webRTCChannel != null) {
                    webRTCChannel.I2(true);
                    this.d.remove(webRTCChannel);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1399, new Class[0], Void.TYPE).isSupported) {
            for (String key : this.d.keySet()) {
                f0 channel = this.d.get(key);
                if (channel.a1() == f0.q.LIVING) {
                    a.b g = timber.log.a.g("LdsChannelManager");
                    g.h("[网络切换] IPC 释放正在播放的直播 && 进行重新通道连接 ---> " + key, new Object[0]);
                    channel.K2("网络变化-进行重新拉流", -31006002, WebRtcLogStepBean.DEVICE_NET_WORK_CHANGED_STEP);
                }
            }
        }
    }
}

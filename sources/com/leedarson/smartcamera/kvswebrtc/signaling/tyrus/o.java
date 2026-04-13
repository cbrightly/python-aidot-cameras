package com.leedarson.smartcamera.kvswebrtc.signaling.tyrus;

import androidx.core.app.NotificationCompat;
import com.amazonaws.regions.ServiceAbbreviations;
import com.amazonaws.services.kinesisvideosignaling.model.IceServer;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.l;
import com.leedarson.log.f;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.IpcService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.smartcamera.bean.DataBean;
import com.leedarson.smartcamera.bean.PayloadBean;
import com.leedarson.smartcamera.bean.SdpAnswerBean;
import com.leedarson.smartcamera.kvswebrtc.signaling.e;
import com.leedarson.smartcamera.kvswebrtc.signaling.model.liveplay.LivePlayPaylodBean;
import com.leedarson.smartcamera.kvswebrtc.signaling.model.liveplay.LiveRequestParamsBean;
import com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel.LdsSignalSendConfigBean;
import com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel.a;
import com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel.b;
import com.leedarson.smartcamera.kvswebrtc.utils.g;
import com.leedarson.smartcamera.reporters.WebRtcLogStepBean;
import com.leedarson.smartcamera.reporters.WebRtcReporterV3;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.PeerConnection;
import timber.log.a;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: LDSMQTTClient */
public class o {
    public static ChangeQuickRedirect changeQuickRedirect;
    private JSONArray a = new JSONArray();
    private e b;
    m c = new m();
    io.reactivex.disposables.b d;
    private com.leedarson.smartcamera.kvswebrtc.signaling.c e;
    public j f;
    public com.leedarson.smartcamera.kvswebrtc.signaling.a g;
    public boolean h = false;
    /* access modifiers changed from: private */
    public String i;
    private String j = "";
    WebRtcLogStepBean k;

    static /* synthetic */ void b(o x0, String x1) {
        Class[] clsArr = {o.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 10052, clsArr, Void.TYPE).isSupported) {
            x0.o(x1);
        }
    }

    static /* synthetic */ void c(o x0, String x1, String x2, WebRtcLogStepBean x3, JSONObject x4, String x5) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3, x4, x5}, (Object) null, changeQuickRedirect, true, 10053, new Class[]{o.class, cls, cls, WebRtcLogStepBean.class, JSONObject.class, cls}, Void.TYPE).isSupported) {
            x0.j(x1, x2, x3, x4, x5);
        }
    }

    static /* synthetic */ void e(o x0, String x1) {
        Class[] clsArr = {o.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 10054, clsArr, Void.TYPE).isSupported) {
            x0.a(x1);
        }
    }

    public void t(String peerid) {
        this.i = peerid;
    }

    public void setmWebRtcConnectListerner(com.leedarson.smartcamera.kvswebrtc.signaling.c mWebRtcConnectListerner) {
        this.e = mWebRtcConnectListerner;
    }

    public void s(com.leedarson.smartcamera.kvswebrtc.signaling.a onPrelinkErrorHandler) {
        this.g = onPrelinkErrorHandler;
    }

    public void r(e callback) {
        this.b = callback;
    }

    private void o(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 10040, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("WEBRTC_MQTT");
            g2.h(msg + ",mqttclient=" + this + "WEBRTC_MQTT peerId=" + this.i + ", webrtcLogV2", new Object[0]);
        }
    }

    private void a(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 10041, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("WEBRTC_MQTT");
            g2.c(msg + " peerId=" + this.i + ", webrtcLogV2", new Object[0]);
        }
    }

    public void f(String ref) {
        if (!PatchProxy.proxy(new Object[]{ref}, this, changeQuickRedirect, false, 10042, new Class[]{String.class}, Void.TYPE).isSupported) {
            o("执行删除iceConfig缓存文件" + ref);
            this.c.e();
        }
    }

    public void h(String deviceId, List<PeerConnection.IceServer> peerIceServers, com.leedarson.smartcamera.kvswebrtc.signaling.c connectListener, String liveType) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, peerIceServers, connectListener, liveType}, this, changeQuickRedirect, false, 10043, new Class[]{cls, List.class, com.leedarson.smartcamera.kvswebrtc.signaling.c.class, cls}, Void.TYPE).isSupported) {
            f.a("获取IceConfig数据：(开始)  设备ID=" + deviceId + ",liveType=" + liveType);
            this.j = liveType;
            io.reactivex.disposables.b bVar = this.d;
            if (bVar != null && !bVar.isDisposed()) {
                this.d.dispose();
            }
            this.k = new WebRtcLogStepBean("GET_ICECONFIG", 200);
            WebRtcReporterV3.v(this.i, deviceId).F(deviceId).G(WebRtcReporterV3.f).K(this.k);
            this.d = this.c.d(deviceId).c(l.c()).I(new h(this, deviceId, peerIceServers, connectListener), new g(this, connectListener, deviceId));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: k */
    public /* synthetic */ void l(String deviceId, List peerIceServers, com.leedarson.smartcamera.kvswebrtc.signaling.c connectListener, String response) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, peerIceServers, connectListener, response}, this, changeQuickRedirect, false, 10051, new Class[]{cls, List.class, com.leedarson.smartcamera.kvswebrtc.signaling.c.class, cls}, Void.TYPE).isSupported) {
            o("获取IceConfig数据（成功）response:" + response);
            com.leedarson.smartcamera.logreport.c b2 = com.leedarson.smartcamera.logreport.c.b();
            String str = this.i;
            b2.e(deviceId, str, "lds geticeconfig", "getIceConfig onSuccess:" + response);
            i(deviceId, peerIceServers, connectListener, response);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: m */
    public /* synthetic */ void n(com.leedarson.smartcamera.kvswebrtc.signaling.c connectListener, String deviceId, Throwable throwable) {
        Class[] clsArr = {com.leedarson.smartcamera.kvswebrtc.signaling.c.class, String.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{connectListener, deviceId, throwable}, this, changeQuickRedirect, false, 10050, clsArr, Void.TYPE).isSupported) {
            o("获取IceConfig数据(失败) response:" + throwable.toString());
            WebRtcLogStepBean webRtcLogStepBean = this.k;
            webRtcLogStepBean.endTrace("获取IceConfig数据(失败) response:" + throwable.toString(), -31007001);
            if (throwable.toString().contains("Chain validation failed")) {
                connectListener.onException(new Exception("获取IceConfig数据(失败)" + throwable.toString()));
            }
            WebRtcReporterV3 v = WebRtcReporterV3.v(this.i, deviceId);
            v.L("获取IceConfig数据(失败) response:" + throwable.toString());
            com.leedarson.smartcamera.logreport.c b2 = com.leedarson.smartcamera.logreport.c.b();
            String str = this.i;
            b2.e(deviceId, str, "lds geticeconfig", "getIceConfig onError:" + throwable.toString());
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: com.leedarson.smartcamera.kvswebrtc.signaling.c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v1, resolved type: com.leedarson.smartcamera.kvswebrtc.signaling.c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v2, resolved type: com.leedarson.smartcamera.kvswebrtc.signaling.c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v3, resolved type: com.leedarson.smartcamera.kvswebrtc.signaling.c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v4, resolved type: com.leedarson.smartcamera.kvswebrtc.signaling.c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v5, resolved type: com.leedarson.smartcamera.kvswebrtc.signaling.c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v8, resolved type: com.leedarson.smartcamera.kvswebrtc.signaling.c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v9, resolved type: com.leedarson.smartcamera.kvswebrtc.signaling.c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v11, resolved type: org.json.JSONObject} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v10, resolved type: com.leedarson.smartcamera.kvswebrtc.signaling.c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v27, resolved type: com.leedarson.smartcamera.kvswebrtc.signaling.c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v28, resolved type: com.leedarson.smartcamera.kvswebrtc.signaling.c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v30, resolved type: com.leedarson.smartcamera.kvswebrtc.signaling.c} */
    /* JADX WARNING: type inference failed for: r16v4, types: [org.json.JSONObject] */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00ac, code lost:
        r11 = r4.uris;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00b3, code lost:
        r22 = new org.json.JSONObject();
        r17 = r5;
        r18 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00bd, code lost:
        r23 = r8;
        r8 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        r8.put("Password", (java.lang.Object) r4.token);
        r8.put("Ttl", (java.lang.Object) r4.ttl);
        r3 = new org.json.JSONArray();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00d0, code lost:
        r5 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00d1, code lost:
        r22 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00d7, code lost:
        if (r5 >= r11.size()) goto L_0x00e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00d9, code lost:
        r3.put((java.lang.Object) r11.get(r5));
        r5 = r5 + 1;
        r10 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00e5, code lost:
        r8.put("Uris", (java.lang.Object) r3);
        r8.put("Username", (java.lang.Object) r4.id);
        r6.a.put((java.lang.Object) r8);
        o("筛选turn服务器 - 匹配设备ID=" + r4.id);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x010e, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x010f, code lost:
        r2 = r23;
        r1 = r10;
        r3 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0116, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0117, code lost:
        r2 = r8;
        r1 = r10;
        r3 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0215, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0216, code lost:
        r1 = r22;
        r2 = r23;
        r3 = r17;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x0386  */
    /* JADX WARNING: Removed duplicated region for block: B:118:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0232 A[SYNTHETIC, Splitter:B:68:0x0232] */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x029c A[Catch:{ Exception -> 0x02ef }] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x02ec A[Catch:{ Exception -> 0x0327 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void i(java.lang.String r20, java.util.List<org.webrtc.PeerConnection.IceServer> r21, com.leedarson.smartcamera.kvswebrtc.signaling.c r22, java.lang.String r23) {
        /*
            r19 = this;
            java.lang.String r0 = " uri="
            java.lang.String r1 = " password="
            java.lang.String r2 = "peerIceServers add: username="
            java.lang.String r3 = "iceConfig获取失败（未找匹配的目标设备）"
            java.lang.String r4 = "筛选iceSturn服务器获取异常 (转化数据失败)"
            java.lang.String r5 = "getIceConfig 获取出异常！"
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r7 = 4
            java.lang.Object[] r8 = new java.lang.Object[r7]
            r15 = 0
            r8[r15] = r20
            r9 = 1
            r8[r9] = r21
            r10 = 2
            r8[r10] = r22
            r11 = 3
            r8[r11] = r23
            com.meituan.robust.ChangeQuickRedirect r12 = changeQuickRedirect
            java.lang.Class[] r13 = new java.lang.Class[r7]
            r13[r15] = r6
            java.lang.Class<java.util.List> r7 = java.util.List.class
            r13[r9] = r7
            java.lang.Class<com.leedarson.smartcamera.kvswebrtc.signaling.c> r7 = com.leedarson.smartcamera.kvswebrtc.signaling.c.class
            r13[r10] = r7
            r13[r11] = r6
            java.lang.Class r14 = java.lang.Void.TYPE
            r11 = 0
            r6 = 10044(0x273c, float:1.4075E-41)
            r9 = r19
            r10 = r12
            r12 = r6
            com.meituan.robust.PatchProxyResult r6 = com.meituan.robust.PatchProxy.proxy(r8, r9, r10, r11, r12, r13, r14)
            boolean r6 = r6.isSupported
            if (r6 == 0) goto L_0x0041
            return
        L_0x0041:
            r6 = r19
            r7 = r21
            r8 = r23
            r9 = r20
            r10 = r22
            java.lang.String r11 = "开始分析添加IceConfig数据(进行中)"
            r6.o(r11)
            java.lang.Class<com.leedarson.smartcamera.bean.IceConfigBean> r11 = com.leedarson.smartcamera.bean.IceConfigBean.class
            java.lang.Object r11 = com.leedarson.base.utils.m.a(r8, r11)     // Catch:{ Exception -> 0x032b }
            com.leedarson.smartcamera.bean.IceConfigBean r11 = (com.leedarson.smartcamera.bean.IceConfigBean) r11     // Catch:{ Exception -> 0x032b }
            if (r11 == 0) goto L_0x02fa
            org.json.JSONArray r4 = new org.json.JSONArray     // Catch:{ Exception -> 0x02f5 }
            r4.<init>()     // Catch:{ Exception -> 0x02f5 }
            r6.a = r4     // Catch:{ Exception -> 0x02f5 }
            java.util.List<com.leedarson.smartcamera.bean.IceConfigBean$DevBean> r4 = r11.dev     // Catch:{ Exception -> 0x02f5 }
            java.util.List<com.leedarson.smartcamera.bean.IceConfigBean$AppBean> r12 = r11.app     // Catch:{ Exception -> 0x02f5 }
            java.lang.String r13 = "lds geticeconfig"
            if (r12 == 0) goto L_0x0224
            int r14 = r12.size()     // Catch:{ Exception -> 0x021e }
            if (r14 <= 0) goto L_0x0224
            java.lang.Object r14 = r12.get(r15)     // Catch:{ Exception -> 0x021e }
            com.leedarson.smartcamera.bean.IceConfigBean$AppBean r14 = (com.leedarson.smartcamera.bean.IceConfigBean.AppBean) r14     // Catch:{ Exception -> 0x021e }
            java.lang.String r14 = r14.id     // Catch:{ Exception -> 0x021e }
            java.lang.Object r16 = r12.get(r15)     // Catch:{ Exception -> 0x021e }
            r15 = r16
            com.leedarson.smartcamera.bean.IceConfigBean$AppBean r15 = (com.leedarson.smartcamera.bean.IceConfigBean.AppBean) r15     // Catch:{ Exception -> 0x021e }
            java.lang.String r15 = r15.token     // Catch:{ Exception -> 0x021e }
            if (r4 == 0) goto L_0x014b
            int r16 = r4.size()     // Catch:{ Exception -> 0x021e }
            if (r16 <= 0) goto L_0x014b
            java.util.Iterator r16 = r4.iterator()     // Catch:{ Exception -> 0x021e }
        L_0x008e:
            boolean r17 = r16.hasNext()     // Catch:{ Exception -> 0x021e }
            if (r17 == 0) goto L_0x013e
            java.lang.Object r17 = r16.next()     // Catch:{ Exception -> 0x021e }
            com.leedarson.smartcamera.bean.IceConfigBean$DevBean r17 = (com.leedarson.smartcamera.bean.IceConfigBean.DevBean) r17     // Catch:{ Exception -> 0x021e }
            r20 = r17
            r21 = r4
            r4 = r20
            if (r4 == 0) goto L_0x0126
            r20 = r11
            java.lang.String r11 = r4.id     // Catch:{ Exception -> 0x021e }
            boolean r11 = r11.equals(r9)     // Catch:{ Exception -> 0x021e }
            if (r11 == 0) goto L_0x011d
            java.util.List<java.lang.String> r11 = r4.uris     // Catch:{ Exception -> 0x021e }
            org.json.JSONObject r16 = new org.json.JSONObject     // Catch:{ Exception -> 0x021e }
            r16.<init>()     // Catch:{ Exception -> 0x021e }
            r22 = r16
            r17 = r5
            java.lang.String r5 = "Password"
            r18 = r3
            java.lang.String r3 = r4.token     // Catch:{ Exception -> 0x0116 }
            r23 = r8
            r8 = r22
            r8.put((java.lang.String) r5, (java.lang.Object) r3)     // Catch:{ Exception -> 0x010e }
            java.lang.String r3 = "Ttl"
            java.lang.Integer r5 = r4.ttl     // Catch:{ Exception -> 0x010e }
            r8.put((java.lang.String) r3, (java.lang.Object) r5)     // Catch:{ Exception -> 0x010e }
            org.json.JSONArray r3 = new org.json.JSONArray     // Catch:{ Exception -> 0x010e }
            r3.<init>()     // Catch:{ Exception -> 0x010e }
            r5 = 0
        L_0x00d1:
            r22 = r10
            int r10 = r11.size()     // Catch:{ Exception -> 0x0215 }
            if (r5 >= r10) goto L_0x00e5
            java.lang.Object r10 = r11.get(r5)     // Catch:{ Exception -> 0x0215 }
            r3.put((java.lang.Object) r10)     // Catch:{ Exception -> 0x0215 }
            int r5 = r5 + 1
            r10 = r22
            goto L_0x00d1
        L_0x00e5:
            java.lang.String r5 = "Uris"
            r8.put((java.lang.String) r5, (java.lang.Object) r3)     // Catch:{ Exception -> 0x0215 }
            java.lang.String r5 = "Username"
            java.lang.String r10 = r4.id     // Catch:{ Exception -> 0x0215 }
            r8.put((java.lang.String) r5, (java.lang.Object) r10)     // Catch:{ Exception -> 0x0215 }
            org.json.JSONArray r5 = r6.a     // Catch:{ Exception -> 0x0215 }
            r5.put((java.lang.Object) r8)     // Catch:{ Exception -> 0x0215 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0215 }
            r5.<init>()     // Catch:{ Exception -> 0x0215 }
            java.lang.String r10 = "筛选turn服务器 - 匹配设备ID="
            r5.append(r10)     // Catch:{ Exception -> 0x0215 }
            java.lang.String r10 = r4.id     // Catch:{ Exception -> 0x0215 }
            r5.append(r10)     // Catch:{ Exception -> 0x0215 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0215 }
            r6.o(r5)     // Catch:{ Exception -> 0x0215 }
            goto L_0x0157
        L_0x010e:
            r0 = move-exception
            r2 = r23
            r1 = r10
            r3 = r17
            goto L_0x032f
        L_0x0116:
            r0 = move-exception
            r2 = r8
            r1 = r10
            r3 = r17
            goto L_0x0222
        L_0x011d:
            r18 = r3
            r17 = r5
            r23 = r8
            r22 = r10
            goto L_0x0130
        L_0x0126:
            r18 = r3
            r17 = r5
            r23 = r8
            r22 = r10
            r20 = r11
        L_0x0130:
            r11 = r20
            r4 = r21
            r10 = r22
            r8 = r23
            r5 = r17
            r3 = r18
            goto L_0x008e
        L_0x013e:
            r18 = r3
            r21 = r4
            r17 = r5
            r23 = r8
            r22 = r10
            r20 = r11
            goto L_0x0157
        L_0x014b:
            r18 = r3
            r21 = r4
            r17 = r5
            r23 = r8
            r22 = r10
            r20 = r11
        L_0x0157:
            r3 = 0
            java.lang.Object r4 = r12.get(r3)     // Catch:{ Exception -> 0x0215 }
            com.leedarson.smartcamera.bean.IceConfigBean$AppBean r4 = (com.leedarson.smartcamera.bean.IceConfigBean.AppBean) r4     // Catch:{ Exception -> 0x0215 }
            java.util.List<java.lang.String> r3 = r4.dnsUris     // Catch:{ Exception -> 0x0215 }
            if (r3 == 0) goto L_0x017b
            r3 = 0
            java.lang.Object r4 = r12.get(r3)     // Catch:{ Exception -> 0x0215 }
            com.leedarson.smartcamera.bean.IceConfigBean$AppBean r4 = (com.leedarson.smartcamera.bean.IceConfigBean.AppBean) r4     // Catch:{ Exception -> 0x0215 }
            java.util.List<java.lang.String> r3 = r4.dnsUris     // Catch:{ Exception -> 0x0215 }
            int r3 = r3.size()     // Catch:{ Exception -> 0x0215 }
            if (r3 <= 0) goto L_0x017b
            r3 = 0
            java.lang.Object r3 = r12.get(r3)     // Catch:{ Exception -> 0x0215 }
            com.leedarson.smartcamera.bean.IceConfigBean$AppBean r3 = (com.leedarson.smartcamera.bean.IceConfigBean.AppBean) r3     // Catch:{ Exception -> 0x0215 }
            java.util.List<java.lang.String> r3 = r3.dnsUris     // Catch:{ Exception -> 0x0215 }
            goto L_0x0184
        L_0x017b:
            r3 = 0
            java.lang.Object r3 = r12.get(r3)     // Catch:{ Exception -> 0x0215 }
            com.leedarson.smartcamera.bean.IceConfigBean$AppBean r3 = (com.leedarson.smartcamera.bean.IceConfigBean.AppBean) r3     // Catch:{ Exception -> 0x0215 }
            java.util.List<java.lang.String> r3 = r3.uris     // Catch:{ Exception -> 0x0215 }
        L_0x0184:
            if (r3 == 0) goto L_0x0230
            int r4 = r3.size()     // Catch:{ Exception -> 0x0215 }
            if (r4 <= 0) goto L_0x0230
            r4 = 0
        L_0x018d:
            int r5 = r3.size()     // Catch:{ Exception -> 0x0215 }
            if (r4 >= r5) goto L_0x0230
            java.lang.Object r5 = r3.get(r4)     // Catch:{ Exception -> 0x0215 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Exception -> 0x0215 }
            org.webrtc.PeerConnection$IceServer$Builder r5 = org.webrtc.PeerConnection.IceServer.builder((java.lang.String) r5)     // Catch:{ Exception -> 0x0215 }
            org.webrtc.PeerConnection$IceServer$Builder r5 = r5.setUsername(r14)     // Catch:{ Exception -> 0x0215 }
            org.webrtc.PeerConnection$IceServer$Builder r5 = r5.setPassword(r15)     // Catch:{ Exception -> 0x0215 }
            org.webrtc.PeerConnection$IceServer r5 = r5.createIceServer()     // Catch:{ Exception -> 0x0215 }
            r7.add(r5)     // Catch:{ Exception -> 0x0215 }
            com.leedarson.smartcamera.logreport.c r8 = com.leedarson.smartcamera.logreport.c.b()     // Catch:{ Exception -> 0x0215 }
            java.lang.String r10 = r6.i     // Catch:{ Exception -> 0x0215 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0215 }
            r11.<init>()     // Catch:{ Exception -> 0x0215 }
            r16 = r5
            int r5 = r7.size()     // Catch:{ Exception -> 0x0215 }
            r11.append(r5)     // Catch:{ Exception -> 0x0215 }
            r11.append(r2)     // Catch:{ Exception -> 0x0215 }
            r11.append(r14)     // Catch:{ Exception -> 0x0215 }
            r11.append(r1)     // Catch:{ Exception -> 0x0215 }
            r11.append(r15)     // Catch:{ Exception -> 0x0215 }
            r11.append(r0)     // Catch:{ Exception -> 0x0215 }
            java.lang.Object r5 = r3.get(r4)     // Catch:{ Exception -> 0x0215 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Exception -> 0x0215 }
            r11.append(r5)     // Catch:{ Exception -> 0x0215 }
            java.lang.String r5 = r11.toString()     // Catch:{ Exception -> 0x0215 }
            r8.e(r9, r10, r13, r5)     // Catch:{ Exception -> 0x0215 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0215 }
            r5.<init>()     // Catch:{ Exception -> 0x0215 }
            java.lang.String r8 = "筛选-sturn服务器："
            r5.append(r8)     // Catch:{ Exception -> 0x0215 }
            int r8 = r7.size()     // Catch:{ Exception -> 0x0215 }
            r5.append(r8)     // Catch:{ Exception -> 0x0215 }
            r5.append(r2)     // Catch:{ Exception -> 0x0215 }
            r5.append(r14)     // Catch:{ Exception -> 0x0215 }
            r5.append(r1)     // Catch:{ Exception -> 0x0215 }
            r5.append(r15)     // Catch:{ Exception -> 0x0215 }
            r5.append(r0)     // Catch:{ Exception -> 0x0215 }
            java.lang.Object r8 = r3.get(r4)     // Catch:{ Exception -> 0x0215 }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ Exception -> 0x0215 }
            r5.append(r8)     // Catch:{ Exception -> 0x0215 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0215 }
            r6.o(r5)     // Catch:{ Exception -> 0x0215 }
            int r4 = r4 + 1
            goto L_0x018d
        L_0x0215:
            r0 = move-exception
            r1 = r22
            r2 = r23
            r3 = r17
            goto L_0x032f
        L_0x021e:
            r0 = move-exception
            r3 = r5
            r2 = r8
            r1 = r10
        L_0x0222:
            goto L_0x032f
        L_0x0224:
            r18 = r3
            r21 = r4
            r17 = r5
            r23 = r8
            r22 = r10
            r20 = r11
        L_0x0230:
            if (r22 == 0) goto L_0x0294
            int r0 = r7.size()     // Catch:{ Exception -> 0x028b }
            if (r0 <= 0) goto L_0x0294
            com.leedarson.smartcamera.logreport.c r0 = com.leedarson.smartcamera.logreport.c.b()     // Catch:{ Exception -> 0x028b }
            java.lang.String r1 = r6.i     // Catch:{ Exception -> 0x028b }
            java.lang.String r2 = "get iceconfig suc!"
            r0.e(r9, r1, r13, r2)     // Catch:{ Exception -> 0x028b }
            r22.b()     // Catch:{ Exception -> 0x028b }
            com.leedarson.smartcamera.reporters.WebRtcLogStepBean r0 = r6.k     // Catch:{ Exception -> 0x028b }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x028b }
            r1.<init>()     // Catch:{ Exception -> 0x028b }
            java.lang.String r2 = "iceConfig数据获取成功"
            r1.append(r2)     // Catch:{ Exception -> 0x028b }
            r2 = r23
            r1.append(r2)     // Catch:{ Exception -> 0x0284 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0284 }
            r3 = 200(0xc8, float:2.8E-43)
            r0.endTrace(r1, r3)     // Catch:{ Exception -> 0x0284 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0284 }
            r0.<init>()     // Catch:{ Exception -> 0x0284 }
            java.lang.String r1 = "筛选iceSturn服务器（成功）: deviceId="
            r0.append(r1)     // Catch:{ Exception -> 0x0284 }
            r0.append(r9)     // Catch:{ Exception -> 0x0284 }
            java.lang.String r1 = ", 共找到serverSize="
            r0.append(r1)     // Catch:{ Exception -> 0x0284 }
            int r1 = r7.size()     // Catch:{ Exception -> 0x0284 }
            r0.append(r1)     // Catch:{ Exception -> 0x0284 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0284 }
            r6.o(r0)     // Catch:{ Exception -> 0x0284 }
            r1 = r22
            goto L_0x02ee
        L_0x0284:
            r0 = move-exception
            r1 = r22
            r3 = r17
            goto L_0x032f
        L_0x028b:
            r0 = move-exception
            r2 = r23
            r1 = r22
            r3 = r17
            goto L_0x032f
        L_0x0294:
            r2 = r23
            int r0 = r7.size()     // Catch:{ Exception -> 0x02ef }
            if (r0 != 0) goto L_0x02ec
            java.lang.String r0 = "筛选iceSturn服务器失败(失败) peerIceServers.size=0"
            r6.o(r0)     // Catch:{ Exception -> 0x02ef }
            com.leedarson.smartcamera.reporters.WebRtcLogStepBean r0 = r6.k     // Catch:{ Exception -> 0x02ef }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ef }
            r1.<init>()     // Catch:{ Exception -> 0x02ef }
            r3 = r18
            r1.append(r3)     // Catch:{ Exception -> 0x02ef }
            r1.append(r2)     // Catch:{ Exception -> 0x02ef }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x02ef }
            r4 = -2001(0xfffffffffffff82f, float:NaN)
            r0.endTrace(r1, r4)     // Catch:{ Exception -> 0x02ef }
            java.lang.String r0 = r6.i     // Catch:{ Exception -> 0x02ef }
            com.leedarson.smartcamera.reporters.WebRtcReporterV3 r0 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.v(r0, r9)     // Catch:{ Exception -> 0x02ef }
            r0.L(r3)     // Catch:{ Exception -> 0x02ef }
            if (r22 == 0) goto L_0x02e9
            java.lang.Exception r0 = new java.lang.Exception     // Catch:{ Exception -> 0x02ef }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ef }
            r1.<init>()     // Catch:{ Exception -> 0x02ef }
            java.lang.String r3 = "getIceConfig 当前设备未找到合适的iceServer: deviceId="
            r1.append(r3)     // Catch:{ Exception -> 0x02ef }
            r1.append(r9)     // Catch:{ Exception -> 0x02ef }
            java.lang.String r3 = "  response:"
            r1.append(r3)     // Catch:{ Exception -> 0x02ef }
            r1.append(r2)     // Catch:{ Exception -> 0x02ef }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x02ef }
            r0.<init>(r1)     // Catch:{ Exception -> 0x02ef }
            r1 = r22
            r1.onException(r0)     // Catch:{ Exception -> 0x0327 }
            goto L_0x02ee
        L_0x02e9:
            r1 = r22
            goto L_0x02ee
        L_0x02ec:
            r1 = r22
        L_0x02ee:
            goto L_0x0326
        L_0x02ef:
            r0 = move-exception
            r1 = r22
            r3 = r17
            goto L_0x032f
        L_0x02f5:
            r0 = move-exception
            r2 = r8
            r1 = r10
            r3 = r5
            goto L_0x032f
        L_0x02fa:
            r17 = r5
            r2 = r8
            r1 = r10
            r20 = r11
            java.lang.String r0 = "筛选iceSturn服务器获取异常 getIceConfig 获取出异常！(转化数据失败)"
            r6.o(r0)     // Catch:{ Exception -> 0x0327 }
            com.leedarson.smartcamera.reporters.WebRtcLogStepBean r0 = r6.k     // Catch:{ Exception -> 0x0327 }
            r3 = -200101(0xfffffffffffcf25b, float:NaN)
            r0.endTrace(r4, r3)     // Catch:{ Exception -> 0x0327 }
            java.lang.String r0 = r6.i     // Catch:{ Exception -> 0x0327 }
            com.leedarson.smartcamera.reporters.WebRtcReporterV3 r0 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.v(r0, r9)     // Catch:{ Exception -> 0x0327 }
            r0.L(r4)     // Catch:{ Exception -> 0x0327 }
            if (r1 == 0) goto L_0x0326
            java.lang.Exception r0 = new java.lang.Exception     // Catch:{ Exception -> 0x0327 }
            r3 = r17
            r0.<init>(r3)     // Catch:{ Exception -> 0x0324 }
            r1.onException(r0)     // Catch:{ Exception -> 0x0324 }
            goto L_0x0326
        L_0x0324:
            r0 = move-exception
            goto L_0x032f
        L_0x0326:
            goto L_0x038e
        L_0x0327:
            r0 = move-exception
            r3 = r17
            goto L_0x032f
        L_0x032b:
            r0 = move-exception
            r3 = r5
            r2 = r8
            r1 = r10
        L_0x032f:
            r0.printStackTrace()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "筛选iceSturn/turn 服务器异常出现异常: e="
            r4.append(r5)
            java.lang.String r5 = r0.toString()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r6.o(r4)
            com.leedarson.smartcamera.reporters.WebRtcLogStepBean r4 = r6.k
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r8 = "getIceConfig 获取出异常！e="
            r5.append(r8)
            java.lang.String r10 = r0.toString()
            r5.append(r10)
            java.lang.String r5 = r5.toString()
            r10 = -200102(0xfffffffffffcf25a, float:NaN)
            r4.endTrace(r5, r10)
            java.lang.String r4 = r6.i
            com.leedarson.smartcamera.reporters.WebRtcReporterV3 r4 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.v(r4, r9)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r8)
            java.lang.String r8 = r0.toString()
            r5.append(r8)
            java.lang.String r5 = r5.toString()
            r4.L(r5)
            if (r1 == 0) goto L_0x038e
            java.lang.Exception r4 = new java.lang.Exception
            r4.<init>(r3)
            r1.onException(r4)
        L_0x038e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.o.i(java.lang.String, java.util.List, com.leedarson.smartcamera.kvswebrtc.signaling.c, java.lang.String):void");
    }

    public void q(String str, String str2, String str3, String str4, List<IceServer> list, String str5, String str6, String str7) {
        WebRtcLogStepBean _sdpOfferStep;
        o oVar;
        JSONObject payloadObj;
        WebRtcLogStepBean _sdpOfferStep2;
        List<IceServer> iceServerList;
        String region;
        String str8;
        List<IceServer> iceServerList2;
        String compressSdp;
        String region2;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, str3, str4, list, str5, str6, str7}, this, changeQuickRedirect, false, 10045, new Class[]{cls, cls, cls, cls, List.class, cls, cls, cls}, Void.TYPE).isSupported) {
            String peerId = str2;
            String region3 = str5;
            String sdp = str4;
            String desc = str7;
            String deviceId = str;
            String psk = str3;
            String liveType = str6;
            List<IceServer> iceServerList3 = list;
            WebRtcLogStepBean _sdpOfferStep3 = new WebRtcLogStepBean(WebRtcLogStepBean.EXCHANGE_SDP, 200);
            try {
                String tip = "媒体协商(数据准备ing) deviceId：" + deviceId + "，peerId：" + peerId + ",liveType=" + liveType + ",desc=" + desc + ",region=" + region3;
                o(tip + " sdp原日志：" + sdp);
                _sdpOfferStep3.desc = tip;
                WebRtcReporterV3.v(peerId, deviceId).K(_sdpOfferStep3);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", (Object) deviceId);
                JSONObject extendsObj = new JSONObject();
                String str9 = desc;
                String str10 = tip;
                try {
                    extendsObj.put(FirebaseAnalytics.Param.METHOD, (Object) "webrtcReq");
                    extendsObj.put("devId", (Object) deviceId);
                    extendsObj.put(NotificationCompat.CATEGORY_SERVICE, (Object) "IPC");
                    payloadObj = new JSONObject();
                    _sdpOfferStep2 = _sdpOfferStep3;
                } catch (Exception e2) {
                    e = e2;
                    String str11 = region3;
                    String str12 = sdp;
                    String str13 = psk;
                    String str14 = liveType;
                    List<IceServer> list2 = iceServerList3;
                    _sdpOfferStep = _sdpOfferStep3;
                    oVar = this;
                    e.printStackTrace();
                    String retryErrorTip = "媒体协商:(未知异常)" + e.toString();
                    _sdpOfferStep.desc = retryErrorTip;
                    _sdpOfferStep.endTrace(retryErrorTip, -2010);
                    WebRtcReporterV3.v(peerId, deviceId).L("媒体协商:(未知异常)" + e.toString());
                    oVar.o("媒体协商 出现转化数据异常exception=" + e.toString());
                }
                try {
                    IpcService _serviceOfIpc = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
                    JSONObject jsonObject2 = jsonObject;
                    if (_serviceOfIpc != null) {
                        try {
                            Gson gson = new Gson();
                            new WebRtcReporterV3.TempIpcDeviceBean();
                            IpcService ipcService = _serviceOfIpc;
                            region = region3;
                            iceServerList = iceServerList3;
                            Gson gson2 = gson;
                            try {
                                WebRtcReporterV3.TempIpcDeviceBean ipcDeviceBean = (WebRtcReporterV3.TempIpcDeviceBean) gson2.fromJson(_serviceOfIpc.getIPCDeviceInfoByDeviceId(deviceId).toString(), WebRtcReporterV3.TempIpcDeviceBean.class);
                                StringBuilder sb = new StringBuilder();
                                Gson gson3 = gson2;
                                sb.append(ipcDeviceBean.powerType);
                                sb.append("");
                                payloadObj.put("powerType", (Object) sb.toString());
                                payloadObj.put("p2pCache", (Object) ipcDeviceBean.p2pCache + "");
                            } catch (Exception e3) {
                                e = e3;
                                String str15 = sdp;
                                String str16 = psk;
                                String str17 = liveType;
                                _sdpOfferStep = _sdpOfferStep2;
                                oVar = this;
                                List<IceServer> list3 = iceServerList;
                            }
                        } catch (Exception e4) {
                            e = e4;
                            String str18 = region3;
                            String str19 = sdp;
                            String str20 = psk;
                            String str21 = liveType;
                            List<IceServer> list4 = iceServerList3;
                            _sdpOfferStep = _sdpOfferStep2;
                            oVar = this;
                            e.printStackTrace();
                            String retryErrorTip2 = "媒体协商:(未知异常)" + e.toString();
                            _sdpOfferStep.desc = retryErrorTip2;
                            _sdpOfferStep.endTrace(retryErrorTip2, -2010);
                            WebRtcReporterV3.v(peerId, deviceId).L("媒体协商:(未知异常)" + e.toString());
                            oVar.o("媒体协商 出现转化数据异常exception=" + e.toString());
                        }
                    } else {
                        region = region3;
                        iceServerList = iceServerList3;
                        IpcService ipcService2 = _serviceOfIpc;
                        try {
                            payloadObj.put("powerType", (Object) "1");
                            payloadObj.put("p2pCache", (Object) "0");
                        } catch (Exception e5) {
                            e = e5;
                            String str22 = sdp;
                            String str23 = psk;
                            String str24 = liveType;
                            _sdpOfferStep = _sdpOfferStep2;
                            oVar = this;
                            List<IceServer> list5 = iceServerList;
                            e.printStackTrace();
                            String retryErrorTip22 = "媒体协商:(未知异常)" + e.toString();
                            _sdpOfferStep.desc = retryErrorTip22;
                            _sdpOfferStep.endTrace(retryErrorTip22, -2010);
                            WebRtcReporterV3.v(peerId, deviceId).L("媒体协商:(未知异常)" + e.toString());
                            oVar.o("媒体协商 出现转化数据异常exception=" + e.toString());
                        }
                    }
                    payloadObj.put("encOffer", 1);
                    payloadObj.put("liveMqtt", 1);
                    JSONObject wPayloadObj = new JSONObject();
                    wPayloadObj.put("peerid", (Object) peerId);
                    wPayloadObj.put(ServiceAbbreviations.STS, System.currentTimeMillis());
                    wPayloadObj.put("psk", (Object) psk);
                    JSONObject offerObj = new JSONObject();
                    String compressSdp2 = g.b(sdp);
                    offerObj.put("sdp", (Object) compressSdp2);
                    offerObj.put(IjkMediaMeta.IJKM_KEY_TYPE, (Object) "offer");
                    wPayloadObj.put("offer", (Object) offerObj);
                    payloadObj.put("dstAddr", (Object) deviceId);
                    payloadObj.put("wPayload", (Object) wPayloadObj);
                    payloadObj.put("IceServerList", (Object) new JSONArray());
                    extendsObj.put("payload", (Object) payloadObj);
                    extendsObj.put("userId", (Object) SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", ""));
                    if (Constans.IPC_LIVE_TYPE_KVS_AND_LDS.equals(liveType)) {
                        try {
                            JSONArray iceServerArray = new JSONArray();
                            if (iceServerList != null) {
                                int size = iceServerList.size() > 2 ? 2 : iceServerList.size();
                                int i2 = 0;
                                while (i2 < size) {
                                    JSONObject wPayloadObj2 = wPayloadObj;
                                    Locale locale = Locale.US;
                                    String sdp2 = sdp;
                                    String psk2 = psk;
                                    String liveType2 = liveType;
                                    try {
                                        Object[] objArr = new Object[2];
                                        objArr[0] = region;
                                        JSONObject offerObj2 = offerObj;
                                        String region4 = region;
                                        try {
                                            if (region4.contains("cn-")) {
                                                str8 = ".cn";
                                            } else {
                                                str8 = "";
                                            }
                                            objArr[1] = str8;
                                            String stunUri = String.format(locale, "stun:stun.kinesisvideo.%s.amazonaws.com%s:443", objArr);
                                            JSONArray iceUris = new JSONArray();
                                            iceUris.put((Object) stunUri);
                                            List<IceServer> iceServerList4 = iceServerList;
                                            try {
                                                List<String> turnUris = iceServerList4.get(i2).getUris();
                                                if (turnUris != null) {
                                                    String str25 = stunUri;
                                                    int t = 0;
                                                    while (true) {
                                                        region2 = region4;
                                                        try {
                                                            if (t >= turnUris.size()) {
                                                                break;
                                                            }
                                                            try {
                                                                iceUris.put((Object) turnUris.get(t));
                                                                t++;
                                                                region4 = region2;
                                                            } catch (Exception e6) {
                                                                e = e6;
                                                                List<IceServer> list6 = iceServerList4;
                                                                _sdpOfferStep = _sdpOfferStep2;
                                                                oVar = this;
                                                                e.printStackTrace();
                                                                String retryErrorTip222 = "媒体协商:(未知异常)" + e.toString();
                                                                _sdpOfferStep.desc = retryErrorTip222;
                                                                _sdpOfferStep.endTrace(retryErrorTip222, -2010);
                                                                WebRtcReporterV3.v(peerId, deviceId).L("媒体协商:(未知异常)" + e.toString());
                                                                oVar.o("媒体协商 出现转化数据异常exception=" + e.toString());
                                                            }
                                                        } catch (Exception e7) {
                                                            e = e7;
                                                            List<IceServer> list7 = iceServerList4;
                                                            _sdpOfferStep = _sdpOfferStep2;
                                                            oVar = this;
                                                            e.printStackTrace();
                                                            String retryErrorTip2222 = "媒体协商:(未知异常)" + e.toString();
                                                            _sdpOfferStep.desc = retryErrorTip2222;
                                                            _sdpOfferStep.endTrace(retryErrorTip2222, -2010);
                                                            WebRtcReporterV3.v(peerId, deviceId).L("媒体协商:(未知异常)" + e.toString());
                                                            oVar.o("媒体协商 出现转化数据异常exception=" + e.toString());
                                                        }
                                                    }
                                                    JSONObject iceServerObj = new JSONObject();
                                                    String password = iceServerList4.get(i2).getPassword();
                                                    List<String> list8 = turnUris;
                                                    compressSdp = compressSdp2;
                                                    int ttlTime = SharePreferenceUtils.getPrefInt(BaseApplication.b(), password, 0);
                                                    if (ttlTime == 0) {
                                                        ttlTime = g() + iceServerList4.get(i2).getTtl().intValue();
                                                        SharePreferenceUtils.setPrefInt(BaseApplication.b(), password, ttlTime);
                                                    }
                                                    iceServerObj.put("Password", (Object) password);
                                                    iceServerObj.put("Ttl", ttlTime);
                                                    iceServerObj.put("Uris", (Object) iceUris);
                                                    JSONArray jSONArray = iceUris;
                                                    iceServerObj.put("Username", (Object) iceServerList4.get(i2).getUsername());
                                                    iceServerArray.put((Object) iceServerObj);
                                                    StringBuilder sb2 = new StringBuilder();
                                                    iceServerList2 = iceServerList4;
                                                    try {
                                                        sb2.append("LDS.WEBRTC sendSdpOffer Password：");
                                                        sb2.append(password);
                                                        sb2.append("，Ttl：");
                                                        sb2.append(ttlTime);
                                                        sb2.append("   iceServerObj=");
                                                        sb2.append(iceServerObj.toString());
                                                        f.b("LDSMQTTClient", sb2.toString());
                                                    } catch (Exception e8) {
                                                        e = e8;
                                                        _sdpOfferStep = _sdpOfferStep2;
                                                        oVar = this;
                                                        e.printStackTrace();
                                                        String retryErrorTip22222 = "媒体协商:(未知异常)" + e.toString();
                                                        _sdpOfferStep.desc = retryErrorTip22222;
                                                        _sdpOfferStep.endTrace(retryErrorTip22222, -2010);
                                                        WebRtcReporterV3.v(peerId, deviceId).L("媒体协商:(未知异常)" + e.toString());
                                                        oVar.o("媒体协商 出现转化数据异常exception=" + e.toString());
                                                    }
                                                } else {
                                                    String str26 = stunUri;
                                                    JSONArray jSONArray2 = iceUris;
                                                    iceServerList2 = iceServerList4;
                                                    List<String> list9 = turnUris;
                                                    region2 = region4;
                                                    compressSdp = compressSdp2;
                                                }
                                                i2++;
                                                liveType = liveType2;
                                                wPayloadObj = wPayloadObj2;
                                                compressSdp2 = compressSdp;
                                                sdp = sdp2;
                                                psk = psk2;
                                                offerObj = offerObj2;
                                                iceServerList = iceServerList2;
                                            } catch (Exception e9) {
                                                e = e9;
                                                List<IceServer> list10 = iceServerList4;
                                                String str27 = region4;
                                                _sdpOfferStep = _sdpOfferStep2;
                                                oVar = this;
                                                e.printStackTrace();
                                                String retryErrorTip222222 = "媒体协商:(未知异常)" + e.toString();
                                                _sdpOfferStep.desc = retryErrorTip222222;
                                                _sdpOfferStep.endTrace(retryErrorTip222222, -2010);
                                                WebRtcReporterV3.v(peerId, deviceId).L("媒体协商:(未知异常)" + e.toString());
                                                oVar.o("媒体协商 出现转化数据异常exception=" + e.toString());
                                            }
                                        } catch (Exception e10) {
                                            e = e10;
                                            String str28 = region4;
                                            List<IceServer> list11 = iceServerList;
                                            _sdpOfferStep = _sdpOfferStep2;
                                            oVar = this;
                                            e.printStackTrace();
                                            String retryErrorTip2222222 = "媒体协商:(未知异常)" + e.toString();
                                            _sdpOfferStep.desc = retryErrorTip2222222;
                                            _sdpOfferStep.endTrace(retryErrorTip2222222, -2010);
                                            WebRtcReporterV3.v(peerId, deviceId).L("媒体协商:(未知异常)" + e.toString());
                                            oVar.o("媒体协商 出现转化数据异常exception=" + e.toString());
                                        }
                                    } catch (Exception e11) {
                                        e = e11;
                                        List<IceServer> list12 = iceServerList;
                                        _sdpOfferStep = _sdpOfferStep2;
                                        oVar = this;
                                        e.printStackTrace();
                                        String retryErrorTip22222222 = "媒体协商:(未知异常)" + e.toString();
                                        _sdpOfferStep.desc = retryErrorTip22222222;
                                        _sdpOfferStep.endTrace(retryErrorTip22222222, -2010);
                                        WebRtcReporterV3.v(peerId, deviceId).L("媒体协商:(未知异常)" + e.toString());
                                        oVar.o("媒体协商 出现转化数据异常exception=" + e.toString());
                                    }
                                }
                                JSONObject jSONObject = wPayloadObj;
                                String str29 = sdp;
                                String str30 = psk;
                                String str31 = liveType;
                                JSONObject jSONObject2 = offerObj;
                                List<IceServer> list13 = iceServerList;
                                String str32 = compressSdp2;
                            } else {
                                String str33 = sdp;
                                String str34 = psk;
                                String str35 = liveType;
                                JSONObject jSONObject3 = offerObj;
                                List<IceServer> list14 = iceServerList;
                                String str36 = compressSdp2;
                            }
                            payloadObj.put("IceServerList", (Object) iceServerArray);
                            oVar = this;
                        } catch (Exception e12) {
                            e = e12;
                            String str37 = sdp;
                            String str38 = psk;
                            String str39 = liveType;
                            List<IceServer> list15 = iceServerList;
                            _sdpOfferStep = _sdpOfferStep2;
                            oVar = this;
                            e.printStackTrace();
                            String retryErrorTip222222222 = "媒体协商:(未知异常)" + e.toString();
                            _sdpOfferStep.desc = retryErrorTip222222222;
                            _sdpOfferStep.endTrace(retryErrorTip222222222, -2010);
                            WebRtcReporterV3.v(peerId, deviceId).L("媒体协商:(未知异常)" + e.toString());
                            oVar.o("媒体协商 出现转化数据异常exception=" + e.toString());
                        }
                    } else {
                        JSONObject jSONObject4 = wPayloadObj;
                        String str40 = sdp;
                        String str41 = psk;
                        String str42 = liveType;
                        JSONObject jSONObject5 = offerObj;
                        List<IceServer> list16 = iceServerList;
                        String str43 = compressSdp2;
                        oVar = this;
                        try {
                            payloadObj.put("IceServerList", (Object) oVar.a);
                        } catch (Exception e13) {
                            e = e13;
                            _sdpOfferStep = _sdpOfferStep2;
                            e.printStackTrace();
                            String retryErrorTip2222222222 = "媒体协商:(未知异常)" + e.toString();
                            _sdpOfferStep.desc = retryErrorTip2222222222;
                            _sdpOfferStep.endTrace(retryErrorTip2222222222, -2010);
                            WebRtcReporterV3.v(peerId, deviceId).L("媒体协商:(未知异常)" + e.toString());
                            oVar.o("媒体协商 出现转化数据异常exception=" + e.toString());
                        }
                    }
                    JSONObject jsonObject3 = jsonObject2;
                    jsonObject3.put("extends", (Object) extendsObj);
                    oVar.o("媒体协商（App==>设备）数据内容为：" + jsonObject3.toString());
                    _sdpOfferStep = _sdpOfferStep2;
                    try {
                        _sdpOfferStep.requestParams = jsonObject3.toString();
                        com.leedarson.smartcamera.logreport.c.b().e(deviceId, oVar.i, "ldsoffer-answer", "Sending Offer:" + jsonObject3.toString());
                        LdsSignalSendConfigBean configBean = new LdsSignalSendConfigBean();
                        configBean.topic = "iot/v1/s/userId/IPC/webrtcReq".replace("userId", SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", ""));
                        configBean.onlyPubAck = false;
                        configBean.timeOutLimitOfMs = 15000;
                        com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel.c.f().g(oVar.i, deviceId, extendsObj.toString(), new a(deviceId, peerId, _sdpOfferStep, jsonObject3), configBean, a.C0173a.OFFER);
                    } catch (Exception e14) {
                        e = e14;
                    }
                } catch (Exception e15) {
                    e = e15;
                    String str44 = region3;
                    String str45 = sdp;
                    String str46 = psk;
                    String str47 = liveType;
                    List<IceServer> list17 = iceServerList3;
                    _sdpOfferStep = _sdpOfferStep2;
                    oVar = this;
                    e.printStackTrace();
                    String retryErrorTip22222222222 = "媒体协商:(未知异常)" + e.toString();
                    _sdpOfferStep.desc = retryErrorTip22222222222;
                    _sdpOfferStep.endTrace(retryErrorTip22222222222, -2010);
                    WebRtcReporterV3.v(peerId, deviceId).L("媒体协商:(未知异常)" + e.toString());
                    oVar.o("媒体协商 出现转化数据异常exception=" + e.toString());
                }
            } catch (Exception e16) {
                e = e16;
                String str48 = region3;
                String str49 = sdp;
                String str50 = desc;
                String str51 = psk;
                String str52 = liveType;
                List<IceServer> list18 = iceServerList3;
                _sdpOfferStep = _sdpOfferStep3;
                oVar = this;
                e.printStackTrace();
                String retryErrorTip222222222222 = "媒体协商:(未知异常)" + e.toString();
                _sdpOfferStep.desc = retryErrorTip222222222222;
                _sdpOfferStep.endTrace(retryErrorTip222222222222, -2010);
                WebRtcReporterV3.v(peerId, deviceId).L("媒体协商:(未知异常)" + e.toString());
                oVar.o("媒体协商 出现转化数据异常exception=" + e.toString());
            }
        }
    }

    /* compiled from: LDSMQTTClient */
    public class a implements com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ WebRtcLogStepBean c;
        final /* synthetic */ JSONObject d;

        a(String str, String str2, WebRtcLogStepBean webRtcLogStepBean, JSONObject jSONObject) {
            this.a = str;
            this.b = str2;
            this.c = webRtcLogStepBean;
            this.d = jSONObject;
        }

        public void a(String taskId, JSONObject callbackData, b.a aVar) {
            Class[] clsArr = {String.class, JSONObject.class, b.a.class};
            if (!PatchProxy.proxy(new Object[]{taskId, callbackData, aVar}, this, changeQuickRedirect, false, 10055, clsArr, Void.TYPE).isSupported) {
                o oVar = o.this;
                o.b(oVar, "媒体协商发起-->收到回执...." + taskId + "   callbackData=" + callbackData.toString());
                JSONArray dataArr = new JSONArray();
                dataArr.put((Object) callbackData);
                JSONObject _rsponseData = new JSONObject();
                try {
                    _rsponseData.put("code", 200);
                    _rsponseData.put("data", (Object) dataArr);
                    o.c(o.this, this.a, this.b, this.c, this.d, _rsponseData.toString());
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }
        }

        public void b(int i, String str, String str2, b.a aVar) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(i), str, str2, aVar};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10056, new Class[]{Integer.TYPE, cls, cls, b.a.class}, Void.TYPE).isSupported) {
                String taskId = str;
                b.a aVar2 = aVar;
                int code = i;
                String desc = str2;
                try {
                    o oVar = o.this;
                    o.b(oVar, "媒体协商发起-->收到回执 Offer发送失败 code=" + code + ", taskId=" + taskId + " ,desc=" + desc);
                    SdpAnswerBean _responseData = new SdpAnswerBean();
                    _responseData.code = 200;
                    DataBean _itemData = new DataBean();
                    _itemData.code = code;
                    _itemData.method = "webrtcResp";
                    PayloadBean _payload = new PayloadBean();
                    PayloadBean.OfferBean _offer = new PayloadBean.OfferBean();
                    _offer.type = "answer";
                    _payload.offer = _offer;
                    _payload.peerid = this.b;
                    _itemData.payload = _payload;
                    _itemData.desc = desc;
                    ArrayList arrayList = new ArrayList();
                    _responseData.data = arrayList;
                    arrayList.add(_itemData);
                    _responseData.desc = desc;
                    o.c(o.this, this.a, this.b, this.c, this.d, new Gson().toJson((Object) _responseData));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x0177 A[Catch:{ Exception -> 0x0228 }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0187 A[Catch:{ Exception -> 0x0228 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void j(java.lang.String r24, java.lang.String r25, com.leedarson.smartcamera.reporters.WebRtcLogStepBean r26, org.json.JSONObject r27, java.lang.String r28) {
        /*
            r23 = this;
            java.lang.String r0 = "媒体协商 （设备回复出现sd卡的最大连接数）"
            java.lang.String r1 = "媒体协商（设备回复已达最大直播推流路数）"
            java.lang.String r2 = "媒体协商(mqtt)返回非200"
            java.lang.Class<java.lang.String> r3 = java.lang.String.class
            r4 = 5
            java.lang.Object[] r5 = new java.lang.Object[r4]
            r12 = 0
            r5[r12] = r24
            r13 = 1
            r5[r13] = r25
            r6 = 2
            r5[r6] = r26
            r7 = 3
            r5[r7] = r27
            r8 = 4
            r5[r8] = r28
            com.meituan.robust.ChangeQuickRedirect r9 = changeQuickRedirect
            java.lang.Class[] r10 = new java.lang.Class[r4]
            r10[r12] = r3
            r10[r13] = r3
            java.lang.Class<com.leedarson.smartcamera.reporters.WebRtcLogStepBean> r4 = com.leedarson.smartcamera.reporters.WebRtcLogStepBean.class
            r10[r6] = r4
            java.lang.Class<org.json.JSONObject> r4 = org.json.JSONObject.class
            r10[r7] = r4
            r10[r8] = r3
            java.lang.Class r11 = java.lang.Void.TYPE
            r8 = 0
            r3 = 10046(0x273e, float:1.4077E-41)
            r6 = r23
            r7 = r9
            r9 = r3
            com.meituan.robust.PatchProxyResult r3 = com.meituan.robust.PatchProxy.proxy(r5, r6, r7, r8, r9, r10, r11)
            boolean r3 = r3.isSupported
            if (r3 == 0) goto L_0x0041
            return
        L_0x0041:
            r3 = r23
            r4 = r25
            r5 = r27
            r6 = r24
            r7 = r26
            r8 = r28
            r7.responseParams = r8
            com.google.gson.Gson r10 = new com.google.gson.Gson     // Catch:{ Exception -> 0x022a }
            r10.<init>()     // Catch:{ Exception -> 0x022a }
            java.lang.Class<com.leedarson.smartcamera.bean.SdpAnswerBean> r11 = com.leedarson.smartcamera.bean.SdpAnswerBean.class
            java.lang.Object r11 = r10.fromJson((java.lang.String) r8, r11)     // Catch:{ Exception -> 0x022a }
            com.leedarson.smartcamera.bean.SdpAnswerBean r11 = (com.leedarson.smartcamera.bean.SdpAnswerBean) r11     // Catch:{ Exception -> 0x022a }
            com.leedarson.smartcamera.logreport.c r14 = com.leedarson.smartcamera.logreport.c.b()     // Catch:{ Exception -> 0x022a }
            java.lang.String r15 = r3.i     // Catch:{ Exception -> 0x022a }
            java.lang.String r12 = "ldsoffer-answer"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x022a }
            r9.<init>()     // Catch:{ Exception -> 0x022a }
            java.lang.String r13 = "received answer:"
            r9.append(r13)     // Catch:{ Exception -> 0x022a }
            java.lang.String r13 = r5.toString()     // Catch:{ Exception -> 0x022a }
            r9.append(r13)     // Catch:{ Exception -> 0x022a }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x022a }
            r14.e(r6, r15, r12, r9)     // Catch:{ Exception -> 0x022a }
            java.lang.Integer r9 = r11.code     // Catch:{ Exception -> 0x022a }
            int r9 = r9.intValue()     // Catch:{ Exception -> 0x022a }
            r12 = 200(0xc8, float:2.8E-43)
            if (r9 != r12) goto L_0x0210
            java.util.List<com.leedarson.smartcamera.bean.DataBean> r2 = r11.data     // Catch:{ Exception -> 0x022a }
            java.util.Iterator r9 = r2.iterator()     // Catch:{ Exception -> 0x022a }
        L_0x008d:
            boolean r13 = r9.hasNext()     // Catch:{ Exception -> 0x022a }
            if (r13 == 0) goto L_0x0209
            java.lang.Object r13 = r9.next()     // Catch:{ Exception -> 0x022a }
            com.leedarson.smartcamera.bean.DataBean r13 = (com.leedarson.smartcamera.bean.DataBean) r13     // Catch:{ Exception -> 0x022a }
            java.lang.String r14 = "webrtcResp"
            java.lang.String r15 = r13.method     // Catch:{ Exception -> 0x022a }
            boolean r14 = r14.equals(r15)     // Catch:{ Exception -> 0x022a }
            if (r14 == 0) goto L_0x01ed
            com.leedarson.smartcamera.bean.PayloadBean r14 = r13.payload     // Catch:{ Exception -> 0x022a }
            java.lang.String r15 = r14.peerid     // Catch:{ Exception -> 0x022a }
            int r12 = r14.pskEnable     // Catch:{ Exception -> 0x022a }
            r7.responseParams = r8     // Catch:{ Exception -> 0x022a }
            r26 = r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x022a }
            r2.<init>()     // Catch:{ Exception -> 0x022a }
            r27 = r5
            java.lang.String r5 = "媒体协商(数据回执) data="
            r2.append(r5)     // Catch:{ Exception -> 0x0228 }
            r2.append(r8)     // Catch:{ Exception -> 0x0228 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0228 }
            r3.o(r2)     // Catch:{ Exception -> 0x0228 }
            int r2 = r13.code     // Catch:{ Exception -> 0x0228 }
            r5 = 200(0xc8, float:2.8E-43)
            if (r2 != r5) goto L_0x01b1
            com.leedarson.smartcamera.bean.PayloadBean$OfferBean r2 = r14.offer     // Catch:{ Exception -> 0x0228 }
            java.lang.String r5 = r2.type     // Catch:{ Exception -> 0x0228 }
            r28 = r9
            java.lang.String r9 = "answer"
            boolean r9 = r9.equals(r5)     // Catch:{ Exception -> 0x0228 }
            if (r9 == 0) goto L_0x01a6
            java.lang.String r9 = r2.sdp     // Catch:{ Exception -> 0x0228 }
            com.leedarson.smartcamera.kvswebrtc.signaling.model.Event r16 = new com.leedarson.smartcamera.kvswebrtc.signaling.model.Event     // Catch:{ Exception -> 0x0228 }
            r16.<init>()     // Catch:{ Exception -> 0x0228 }
            r17 = r16
            r16 = r2
            r2 = r17
            r2.setSenderClientId(r15)     // Catch:{ Exception -> 0x0228 }
            r2.setMessageType(r5)     // Catch:{ Exception -> 0x0228 }
            r17 = r5
            r5 = 1
            if (r12 != r5) goto L_0x00f2
            goto L_0x00f3
        L_0x00f2:
            r5 = 0
        L_0x00f3:
            r2.setPskEnable(r5)     // Catch:{ Exception -> 0x0228 }
            int r5 = r14.trackId     // Catch:{ Exception -> 0x0228 }
            r2.setTrackId(r5)     // Catch:{ Exception -> 0x0228 }
            r2.setSdp(r9)     // Catch:{ Exception -> 0x0228 }
            int r5 = r14.supportRtpExt     // Catch:{ Exception -> 0x0228 }
            r2.setSupportRtpExt(r5)     // Catch:{ Exception -> 0x0228 }
            java.lang.String r5 = "媒体协商(数据回执) 收到设备Answer回执(分析中...)"
            r3.o(r5)     // Catch:{ Exception -> 0x0228 }
            java.lang.String r5 = ""
            r18 = r5
            com.leedarson.smartcamera.bean.AckBean r5 = r13.ack     // Catch:{ Exception -> 0x0228 }
            if (r5 == 0) goto L_0x019d
            r19 = r9
            java.lang.Integer r9 = r5.code     // Catch:{ Exception -> 0x0228 }
            int r9 = r9.intValue()     // Catch:{ Exception -> 0x0228 }
            r20 = r10
            r10 = 200(0xc8, float:2.8E-43)
            if (r9 != r10) goto L_0x0137
            java.lang.String r9 = "媒体协商(数据回执) 协商成功"
            r7.endTrace(r9, r10)     // Catch:{ Exception -> 0x0228 }
            com.leedarson.smartcamera.kvswebrtc.signaling.e r10 = r3.b     // Catch:{ Exception -> 0x0228 }
            if (r10 == 0) goto L_0x0136
            r3.o(r9)     // Catch:{ Exception -> 0x0228 }
            com.leedarson.smartcamera.kvswebrtc.signaling.e r10 = r3.b     // Catch:{ Exception -> 0x0228 }
            r10.c(r2)     // Catch:{ Exception -> 0x0228 }
            r10 = 200(0xc8, float:2.8E-43)
            r7.endTrace(r9, r10)     // Catch:{ Exception -> 0x0228 }
        L_0x0136:
            goto L_0x0171
        L_0x0137:
            java.lang.Integer r9 = r5.code     // Catch:{ Exception -> 0x0228 }
            int r9 = r9.intValue()     // Catch:{ Exception -> 0x0228 }
            r10 = -50002(0xffffffffffff3cae, float:NaN)
            if (r9 != r10) goto L_0x0154
            r9 = r1
            r3.o(r9)     // Catch:{ Exception -> 0x0228 }
            r7.desc = r9     // Catch:{ Exception -> 0x0228 }
            r7.endTrace(r9, r10)     // Catch:{ Exception -> 0x0228 }
            com.leedarson.smartcamera.reporters.WebRtcReporterV3 r10 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.v(r4, r6)     // Catch:{ Exception -> 0x0228 }
            r10.L(r1)     // Catch:{ Exception -> 0x0228 }
            goto L_0x0173
        L_0x0154:
            java.lang.Integer r9 = r5.code     // Catch:{ Exception -> 0x0228 }
            int r9 = r9.intValue()     // Catch:{ Exception -> 0x0228 }
            r10 = -50015(0xffffffffffff3ca1, float:NaN)
            if (r9 != r10) goto L_0x0171
            r9 = r0
            r7.desc = r9     // Catch:{ Exception -> 0x0228 }
            r3.o(r9)     // Catch:{ Exception -> 0x0228 }
            r7.endTrace(r9, r10)     // Catch:{ Exception -> 0x0228 }
            com.leedarson.smartcamera.reporters.WebRtcReporterV3 r10 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.v(r4, r6)     // Catch:{ Exception -> 0x0228 }
            r10.L(r0)     // Catch:{ Exception -> 0x0228 }
            goto L_0x0173
        L_0x0171:
            r9 = r18
        L_0x0173:
            com.leedarson.smartcamera.kvswebrtc.signaling.a r10 = r3.g     // Catch:{ Exception -> 0x0228 }
            if (r10 == 0) goto L_0x0187
            r21 = r0
            int r0 = r7.code     // Catch:{ Exception -> 0x0228 }
            r22 = r1
            r1 = 200(0xc8, float:2.8E-43)
            if (r0 == r1) goto L_0x018b
            java.lang.String r1 = r7.desc     // Catch:{ Exception -> 0x0228 }
            r10.a(r0, r1, r7, r4)     // Catch:{ Exception -> 0x0228 }
            goto L_0x018b
        L_0x0187:
            r21 = r0
            r22 = r1
        L_0x018b:
            com.leedarson.smartcamera.kvswebrtc.signaling.c r0 = r3.e     // Catch:{ Exception -> 0x0228 }
            if (r0 == 0) goto L_0x01b0
            boolean r1 = r3.h     // Catch:{ Exception -> 0x0228 }
            if (r1 != 0) goto L_0x01b0
            java.lang.Integer r1 = r5.code     // Catch:{ Exception -> 0x0228 }
            int r1 = r1.intValue()     // Catch:{ Exception -> 0x0228 }
            r0.d(r1)     // Catch:{ Exception -> 0x0228 }
            goto L_0x01b0
        L_0x019d:
            r21 = r0
            r22 = r1
            r19 = r9
            r20 = r10
            goto L_0x01b0
        L_0x01a6:
            r21 = r0
            r22 = r1
            r16 = r2
            r17 = r5
            r20 = r10
        L_0x01b0:
            goto L_0x01f9
        L_0x01b1:
            r21 = r0
            r22 = r1
            r28 = r9
            r20 = r10
            com.leedarson.smartcamera.kvswebrtc.signaling.a r0 = r3.g     // Catch:{ Exception -> 0x0228 }
            java.lang.String r1 = "媒体协商(超时)在规定时间内无收到Answer回执"
            if (r0 == 0) goto L_0x01cb
            r0 = -31007502(0xfffffffffe26dcf2, float:-5.5449765E37)
            r7.endTrace(r1, r0)     // Catch:{ Exception -> 0x0228 }
            com.leedarson.smartcamera.kvswebrtc.signaling.a r2 = r3.g     // Catch:{ Exception -> 0x0228 }
            r2.a(r0, r1, r7, r4)     // Catch:{ Exception -> 0x0228 }
        L_0x01cb:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0228 }
            r0.<init>()     // Catch:{ Exception -> 0x0228 }
            java.lang.String r2 = r13.desc     // Catch:{ Exception -> 0x0228 }
            r0.append(r2)     // Catch:{ Exception -> 0x0228 }
            java.lang.String r2 = "媒体协商(超时)"
            r0.append(r2)     // Catch:{ Exception -> 0x0228 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0228 }
            int r2 = r13.code     // Catch:{ Exception -> 0x0228 }
            r7.endTrace(r0, r2)     // Catch:{ Exception -> 0x0228 }
            com.leedarson.smartcamera.reporters.WebRtcReporterV3 r0 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.v(r4, r6)     // Catch:{ Exception -> 0x0228 }
            r0.L(r1)     // Catch:{ Exception -> 0x0228 }
            goto L_0x01f9
        L_0x01ed:
            r21 = r0
            r22 = r1
            r26 = r2
            r27 = r5
            r28 = r9
            r20 = r10
        L_0x01f9:
            r2 = r26
            r5 = r27
            r9 = r28
            r10 = r20
            r0 = r21
            r1 = r22
            r12 = 200(0xc8, float:2.8E-43)
            goto L_0x008d
        L_0x0209:
            r26 = r2
            r27 = r5
            r20 = r10
            goto L_0x0227
        L_0x0210:
            r27 = r5
            r20 = r10
            r0 = r2
            r3.o(r0)     // Catch:{ Exception -> 0x0228 }
            r1 = -2010(0xfffffffffffff826, float:NaN)
            r7.endTrace(r0, r1)     // Catch:{ Exception -> 0x0228 }
            r7.responseParams = r8     // Catch:{ Exception -> 0x0228 }
            com.leedarson.smartcamera.reporters.WebRtcReporterV3 r1 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.v(r4, r6)     // Catch:{ Exception -> 0x0228 }
            r1.L(r2)     // Catch:{ Exception -> 0x0228 }
        L_0x0227:
            goto L_0x0255
        L_0x0228:
            r0 = move-exception
            goto L_0x022d
        L_0x022a:
            r0 = move-exception
            r27 = r5
        L_0x022d:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "媒体协商(数据转化异常)"
            r1.append(r2)
            java.lang.String r5 = r0.toString()
            r1.append(r5)
            java.lang.String r1 = r1.toString()
            r3.o(r1)
            r7.desc = r1
            r5 = -2010(0xfffffffffffff826, float:NaN)
            r7.endTrace(r1, r5)
            com.leedarson.smartcamera.reporters.WebRtcReporterV3 r5 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.v(r4, r6)
            r5.L(r2)
        L_0x0255:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.o.j(java.lang.String, java.lang.String, com.leedarson.smartcamera.reporters.WebRtcLogStepBean, org.json.JSONObject, java.lang.String):void");
    }

    public void p(String str, String str2, String str3, String str4) {
        JSONObject wPayloadObj;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, str3, str4}, this, changeQuickRedirect, false, 10047, new Class[]{cls, cls, cls, cls}, Void.TYPE).isSupported) {
            String peerId = str2;
            String desc = str4;
            String deviceId = str;
            String candidate = str3;
            try {
                WebRtcLogStepBean _iceCandadite = new WebRtcLogStepBean(WebRtcLogStepBean.EXCHANGE_CENDIDITE_SEND_TO, 200);
                WebRtcReporterV3.v(peerId, deviceId).K(_iceCandadite);
                String inputParams = "开始发送(iceCandidate)sendIceCandidate:" + deviceId + "=" + peerId + " candidate=" + candidate + "=" + desc;
                o(inputParams);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", (Object) deviceId);
                JSONObject extendsObj = new JSONObject();
                extendsObj.put(FirebaseAnalytics.Param.METHOD, (Object) "iceCandidateReq");
                extendsObj.put("devId", (Object) deviceId);
                extendsObj.put(NotificationCompat.CATEGORY_SERVICE, (Object) "IPC");
                JSONObject payloadObj = new JSONObject();
                payloadObj.put("dstAddr", (Object) deviceId);
                JSONObject wPayloadObj2 = new JSONObject();
                wPayloadObj2.put("peerid", (Object) peerId);
                String str5 = desc;
                try {
                    wPayloadObj2.put(ServiceAbbreviations.STS, System.currentTimeMillis());
                    JSONObject candidateObj = new JSONObject();
                    candidateObj.put("candidate", (Object) candidate);
                    wPayloadObj2.put("candidate", (Object) candidateObj);
                    payloadObj.put("wPayload", (Object) wPayloadObj2);
                    extendsObj.put("payload", (Object) payloadObj);
                    IpcService _serviceOfIpc = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
                    if (_serviceOfIpc != null) {
                        Gson gson = new Gson();
                        new WebRtcReporterV3.TempIpcDeviceBean();
                        IpcService ipcService = _serviceOfIpc;
                        wPayloadObj = wPayloadObj2;
                        JSONObject jSONObject = candidateObj;
                        Gson gson2 = gson;
                        WebRtcReporterV3.TempIpcDeviceBean ipcDeviceBean = (WebRtcReporterV3.TempIpcDeviceBean) gson2.fromJson(_serviceOfIpc.getIPCDeviceInfoByDeviceId(deviceId).toString(), WebRtcReporterV3.TempIpcDeviceBean.class);
                        StringBuilder sb = new StringBuilder();
                        Gson gson3 = gson2;
                        sb.append(ipcDeviceBean.powerType);
                        sb.append("");
                        payloadObj.put("powerType", (Object) sb.toString());
                        payloadObj.put("p2pCache", (Object) ipcDeviceBean.p2pCache + "");
                    } else {
                        wPayloadObj = wPayloadObj2;
                        JSONObject jSONObject2 = candidateObj;
                        payloadObj.put("powerType", (Object) "1");
                        payloadObj.put("p2pCache", (Object) "0");
                    }
                    extendsObj.put("userId", (Object) SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", ""));
                    jsonObject.put("extends", (Object) extendsObj);
                    o("iceCandidate数据内容(发送) iceCandidateReq params：" + jsonObject.toString());
                    _iceCandadite.requestParams = jsonObject.toString();
                    LdsSignalSendConfigBean configBean = new LdsSignalSendConfigBean();
                    configBean.topic = "iot/v1/s/userId/IPC/iceCandidateReq".replace("userId", BaseApplication.b().d());
                    configBean.timeOutLimitOfMs = 15000;
                    configBean.onlyPubAck = true;
                    JSONObject jSONObject3 = wPayloadObj;
                    JSONObject jSONObject4 = payloadObj;
                    JSONObject jSONObject5 = extendsObj;
                    JSONObject jSONObject6 = jsonObject;
                    WebRtcLogStepBean webRtcLogStepBean = _iceCandadite;
                    com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel.c.f().g(peerId, deviceId, extendsObj.toString(), new b(deviceId, jsonObject, _iceCandadite, inputParams), configBean, a.C0173a.CANDIDATE);
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                }
            } catch (Exception e3) {
                e = e3;
                String str6 = desc;
                e.printStackTrace();
            }
        }
    }

    /* compiled from: LDSMQTTClient */
    public class b implements com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ JSONObject b;
        final /* synthetic */ WebRtcLogStepBean c;
        final /* synthetic */ String d;

        b(String str, JSONObject jSONObject, WebRtcLogStepBean webRtcLogStepBean, String str2) {
            this.a = str;
            this.b = jSONObject;
            this.c = webRtcLogStepBean;
            this.d = str2;
        }

        public void a(String str, JSONObject jSONObject, b.a aVar) {
            Class[] clsArr = {String.class, JSONObject.class, b.a.class};
            if (!PatchProxy.proxy(new Object[]{str, jSONObject, aVar}, this, changeQuickRedirect, false, 10057, clsArr, Void.TYPE).isSupported) {
                com.leedarson.smartcamera.logreport.c b2 = com.leedarson.smartcamera.logreport.c.b();
                String str2 = this.a;
                String d2 = o.this.i;
                b2.e(str2, d2, "lds-sendIceCandidate", "send ICE:" + this.b.toString());
                o oVar = o.this;
                o.b(oVar, "MqttLog 候选人发送(成功) " + "候选人数据交换(App==>设备)");
                WebRtcLogStepBean webRtcLogStepBean = this.c;
                webRtcLogStepBean.requestParams = this.d + " data==>" + this.b.toString();
                WebRtcLogStepBean webRtcLogStepBean2 = this.c;
                webRtcLogStepBean2.desc = "候选人数据交换(App==>设备)";
                webRtcLogStepBean2.endTrace("候选人数据交换(App==>设备)", 200);
            }
        }

        public void b(int code, String str, String desc, b.a aVar) {
            Class<String> cls = String.class;
            if (!PatchProxy.proxy(new Object[]{new Integer(code), str, desc, aVar}, this, changeQuickRedirect, false, 10058, new Class[]{Integer.TYPE, cls, cls, b.a.class}, Void.TYPE).isSupported) {
                com.leedarson.smartcamera.logreport.c.b().e(this.a, o.this.i, "lds-sendIceCandidate", "send ICE(FAIL):" + this.b.toString());
                String tip = "候选人数据交换(App==>设备) " + desc;
                o.b(o.this, "MqttLog 候选人发送(失败) " + tip);
                this.c.requestParams = this.d + " data==>" + this.b.toString();
                WebRtcLogStepBean webRtcLogStepBean = this.c;
                webRtcLogStepBean.responseParams = tip;
                webRtcLogStepBean.desc = tip;
                webRtcLogStepBean.endTrace(tip, code);
            }
        }
    }

    public static int g() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 10048, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : (int) (System.currentTimeMillis() / 1000);
    }

    public void u(String str, String str2, int i2) {
        Class<String> cls = String.class;
        Object[] objArr = {str, str2, new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10049, new Class[]{cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            String peerid = str2;
            String deviceId = str;
            int dseq = i2;
            try {
                WebRtcReporterV3.TempIpcDeviceBean ipcDeviceBean = (WebRtcReporterV3.TempIpcDeviceBean) new Gson().fromJson(((IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class)).getIPCDeviceInfoByDeviceId(deviceId).toString(), WebRtcReporterV3.TempIpcDeviceBean.class);
                LiveRequestParamsBean _liveRequest = new LiveRequestParamsBean();
                _liveRequest.srcAddr = "0." + BaseApplication.b().d();
                _liveRequest.dstAddr = deviceId;
                _liveRequest.livePlay = 1;
                LivePlayPaylodBean livePlayPaylodBean = _liveRequest.payload;
                livePlayPaylodBean.livePlay = 1;
                livePlayPaylodBean.dstAddr = deviceId;
                livePlayPaylodBean.powerType = ipcDeviceBean.powerType;
                livePlayPaylodBean.dseq = dseq;
                livePlayPaylodBean.peerid = peerid;
                livePlayPaylodBean.p2pCache = ipcDeviceBean.p2pCache;
                LdsSignalSendConfigBean configBean = new LdsSignalSendConfigBean();
                configBean.topic = "iot/v1/s/userId/IPC/livePlayReq".replace("userId", BaseApplication.b().d());
                configBean.timeOutLimitOfMs = 15000;
                configBean.onlyPubAck = false;
                LiveRequestParamsBean liveRequestParamsBean = _liveRequest;
                com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel.c.f().g(peerid, deviceId, _liveRequest.toJson(), new c(), configBean, a.C0173a.LIVEPLAY);
            } catch (Exception e2) {
                a("起播指令发送失败：(双通道) Exception=" + e2.toString());
            }
        }
    }

    /* compiled from: LDSMQTTClient */
    public class c implements com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void a(String taskId, JSONObject callbackData, b.a aVar) {
            Class[] clsArr = {String.class, JSONObject.class, b.a.class};
            if (!PatchProxy.proxy(new Object[]{taskId, callbackData, aVar}, this, changeQuickRedirect, false, 10059, clsArr, Void.TYPE).isSupported) {
                o oVar = o.this;
                o.b(oVar, "起播指令投递成功: taskId=" + taskId + ", callbackData=" + callbackData);
            }
        }

        public void b(int code, String taskId, String desc, b.a aVar) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(code), taskId, desc, aVar};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10060, new Class[]{Integer.TYPE, cls, cls, b.a.class}, Void.TYPE).isSupported) {
                o oVar = o.this;
                o.e(oVar, "起播指令发送失败：(Mqtt通道-callback) Exception=" + desc + "  taskId=" + taskId + " , code=" + code);
            }
        }
    }
}

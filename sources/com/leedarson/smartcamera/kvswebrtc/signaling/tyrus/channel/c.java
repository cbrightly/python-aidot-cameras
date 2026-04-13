package com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel;

import com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel.a;
import com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel.b;
import com.leedarson.smartcamera.reporters.WebRtcReporterV3;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONObject;

/* compiled from: LdsSignalSwitchChanelWrap */
public class c {
    private static c a = null;
    public static ChangeQuickRedirect changeQuickRedirect;
    private ArrayList<a> b = new ArrayList<>();
    /* access modifiers changed from: private */
    public ConcurrentHashMap<Long, Boolean> c = new ConcurrentHashMap<>();

    static /* synthetic */ void c(c x0, String x1) {
        Class[] clsArr = {c.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 10070, clsArr, Void.TYPE).isSupported) {
            x0.b(x1);
        }
    }

    static /* synthetic */ void e(c x0, String x1) {
        Class[] clsArr = {c.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 10071, clsArr, Void.TYPE).isSupported) {
            x0.a(x1);
        }
    }

    private c() {
        this.b.add(new a());
        this.b.add(new b());
    }

    /* compiled from: LdsSignalSwitchChanelWrap */
    public class a extends e {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }
    }

    /* compiled from: LdsSignalSwitchChanelWrap */
    public class b extends d {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }
    }

    public static c f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 10065, new Class[0], c.class);
        if (proxy.isSupported) {
            return (c) proxy.result;
        }
        if (a == null) {
            a = new c();
        }
        return a;
    }

    public void g(String peerid, String str, String str2, b _handler, LdsSignalSendConfigBean configBean, a.C0173a aVar) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{peerid, str, str2, _handler, configBean, aVar}, this, changeQuickRedirect, false, 10066, new Class[]{cls, cls, cls, b.class, LdsSignalSendConfigBean.class, a.C0173a.class}, Void.TYPE).isSupported) {
            String deviceId = str;
            a.C0173a msgType = aVar;
            String messageBody = str2;
            long taskId = System.nanoTime();
            this.c.put(Long.valueOf(taskId), false);
            b _temp = new C0174c(_handler, msgType, peerid, deviceId, messageBody, taskId);
            for (int i = 0; i < this.b.size(); i++) {
                this.b.get(i).a(deviceId, messageBody, _temp, configBean, msgType);
            }
        }
    }

    /* renamed from: com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel.c$c  reason: collision with other inner class name */
    /* compiled from: LdsSignalSwitchChanelWrap */
    public class C0174c implements b {
        public static ChangeQuickRedirect changeQuickRedirect;
        private AtomicInteger a = new AtomicInteger(0);
        final /* synthetic */ b b;
        final /* synthetic */ a.C0173a c;
        final /* synthetic */ String d;
        final /* synthetic */ String e;
        final /* synthetic */ String f;
        final /* synthetic */ long g;

        C0174c(b bVar, a.C0173a aVar, String str, String str2, String str3, long j) {
            this.b = bVar;
            this.c = aVar;
            this.d = str;
            this.e = str2;
            this.f = str3;
            this.g = j;
        }

        public void a(String taskId, JSONObject callbackData, b.a signal) {
            if (!PatchProxy.proxy(new Object[]{taskId, callbackData, signal}, this, changeQuickRedirect, false, 10074, new Class[]{String.class, JSONObject.class, b.a.class}, Void.TYPE).isSupported) {
                c cVar = c.this;
                c.c(cVar, "发送成功 通道类型 " + signal.toString() + " callbackData: " + callbackData);
                if (!((Boolean) c.this.c.get(Long.valueOf(c()))).booleanValue()) {
                    c.this.c.put(Long.valueOf(c()), true);
                    c cVar2 = c.this;
                    c.c(cVar2, "最终发送成功是通道： " + signal + " callbackData : " + callbackData);
                    this.b.a(taskId, callbackData, signal);
                    if (this.c == a.C0173a.OFFER) {
                        WebRtcReporterV3.v(this.d, this.e).o = signal == b.a.MQTT ? "mqtt" : "tcp";
                    }
                } else if (this.c == a.C0173a.OFFER) {
                    WebRtcReporterV3.v(this.d, this.e).o = signal == b.a.MQTT ? "tcp_mqtt" : "mqtt_tcp";
                }
                c cVar3 = c.this;
                c.c(cVar3, "WebRtcReporterV3 _answerSource： " + WebRtcReporterV3.v(this.d, this.e).o);
            }
        }

        public void b(int code, String taskId, String desc, b.a signal) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(code), taskId, desc, signal};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10075, new Class[]{Integer.TYPE, cls, cls, b.a.class}, Void.TYPE).isSupported) {
                c cVar = c.this;
                c.e(cVar, "发送失败 通道类型 " + signal.toString() + " desc: " + desc);
                if (this.a.incrementAndGet() >= 2) {
                    c cVar2 = c.this;
                    c.e(cVar2, "双通道发送失败 消息为：" + this.f + " failCount : " + this.a + " taskId : " + taskId);
                    this.b.b(code, taskId, desc, signal);
                    if (this.c == a.C0173a.OFFER) {
                        WebRtcReporterV3.v(this.d, this.e).o = "未收到";
                    }
                }
            }
        }

        public long c() {
            return this.g;
        }
    }

    private void a(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 10067, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("LdsSignalSwitchChanelWr").c(msg, new Object[0]);
        }
    }

    private void b(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 10068, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("LdsSignalSwitchChanelWr").h(msg, new Object[0]);
        }
    }
}

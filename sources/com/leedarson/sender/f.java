package com.leedarson.sender;

import com.alibaba.android.arouter.launcher.a;
import com.leedarson.serviceinterface.TcpService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: TCPSender */
public class f extends e {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String a;
    private String b;
    private String c;
    AtomicLong d;
    TcpService e;
    long f = 0;

    public f(String sessionId, String seq, long ascNum, String devId) {
        this.a = sessionId;
        this.b = seq;
        this.d = new AtomicLong(ascNum);
        this.c = devId;
        this.f = 0;
        this.e = (TcpService) a.c().g(TcpService.class);
    }

    public void a(String str, JSONObject jsonObject) {
        Class[] clsArr = {String.class, JSONObject.class};
        if (!PatchProxy.proxy(new Object[]{str, jsonObject}, this, changeQuickRedirect, false, 5693, clsArr, Void.TYPE).isSupported) {
            if (this.e != null) {
                try {
                    jsonObject.getJSONObject("payload").put("ascNumber", e());
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                this.e.setActionTcpSend(this.a, jsonObject.toString(), this.b, "");
                f(jsonObject);
            }
        }
    }

    public void b(String mac, String groupId, byte able, com.leedarson.base.http.listener.a listener) {
    }

    public void c(String mac, String groupId, int[] colors) {
    }

    private long e() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5694, new Class[0], Long.TYPE);
        return proxy.isSupported ? ((Long) proxy.result).longValue() : this.d.addAndGet(3);
    }

    private void f(JSONObject sendJson) {
        if (!PatchProxy.proxy(new Object[]{sendJson}, this, changeQuickRedirect, false, 5695, new Class[]{JSONObject.class}, Void.TYPE).isSupported) {
            if (System.currentTimeMillis() - this.f > 700) {
                JSONObject json = d(sendJson);
                TcpService tcpService = this.e;
                if (tcpService != null) {
                    tcpService.onMessage("onMessage", json.toString());
                }
                this.f = System.currentTimeMillis();
            }
        }
    }

    private JSONObject d(JSONObject sendJson) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{sendJson}, this, changeQuickRedirect, false, 5696, new Class[]{JSONObject.class}, JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        JSONObject json = new JSONObject();
        JSONObject message = sendJson;
        try {
            JSONObject ack = new JSONObject();
            ack.put("code", 200).put("desc", (Object) "OK");
            message.put("ack", (Object) ack);
            JSONObject payload = message.getJSONObject("payload");
            payload.put("ascNumber", this.d.get());
            payload.put("devId", (Object) this.c);
            message.put("payload", (Object) payload);
            json.put("message", (Object) message);
            json.put("sessionId", (Object) this.a);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return json;
    }
}

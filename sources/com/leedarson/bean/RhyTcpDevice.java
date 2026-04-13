package com.leedarson.bean;

import android.graphics.Color;
import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.sender.f;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.json.JSONException;
import org.json.JSONObject;

public class RhyTcpDevice extends IRhyDevice {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String rhythmType;
    private JSONObject tcpSendData;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public RhyTcpDevice(String rhythmType2, String protocolType, JSONObject protocolData) {
        super(rhythmType2, protocolType, protocolData);
        JSONObject jSONObject = protocolData;
        try {
            this.rhythmType = rhythmType2;
            String sessionId = jSONObject.get("sessionId").toString();
            String clientId = jSONObject.get("clientId").toString();
            String service = jSONObject.get(NotificationCompat.CATEGORY_SERVICE).toString();
            String method = jSONObject.get(FirebaseAnalytics.Param.METHOD).toString();
            String seq = jSONObject.get("seq").toString();
            String srcAddr = jSONObject.get("srcAddr").toString();
            JSONObject payload = (JSONObject) jSONObject.get("payload");
            String ascNumber = payload.get("ascNumber").toString();
            String devId = payload.get("devId").toString();
            String parentId = payload.get("parentId").toString();
            String password = payload.get("password").toString();
            String userId = payload.get("userId").toString();
            JSONObject jSONObject2 = payload;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(FirebaseAnalytics.Param.METHOD, (Object) method);
            jsonObject.put(NotificationCompat.CATEGORY_SERVICE, (Object) service);
            jsonObject.put("sessionId", (Object) sessionId);
            jsonObject.put("clientId", (Object) clientId);
            jsonObject.put("srcAddr", (Object) srcAddr);
            String seq2 = seq;
            jsonObject.put("seq", (Object) seq2);
            JSONObject jsonItem = new JSONObject();
            String devId2 = devId;
            jsonItem.put("devId", (Object) devId2);
            jsonItem.put("parentId", (Object) parentId);
            jsonItem.put("userId", (Object) userId);
            jsonItem.put("password", (Object) password);
            String ascNumber2 = ascNumber;
            jsonItem.put("ascNumber", (Object) ascNumber2);
            jsonObject.put("payload", (Object) jsonItem);
            this.tcpSendData = jsonObject;
            if (this.sender == null) {
                this.sender = new f(sessionId, seq2, Long.parseLong(ascNumber2), devId2);
            }
            setRhyId(devId2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void start() {
    }

    public void send() {
    }

    public void stop() {
    }

    private int color2RGBW(int color) {
        Object[] objArr = {new Integer(color)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1102, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return (Color.red(color) << 24) | (Color.green(color) << 16) | (Color.blue(color) << 8);
    }
}

package com.leedarson.smartcamera.kvswebrtc.beans;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.json.JSONException;
import org.json.JSONObject;

public class WebRtcServiceStateChangeLogBean {
    public static int CODE_SUCCESS = 200;
    public static int PROCESS_STATE_FAIL = -1;
    public static int PROCESS_STATE_START = 0;
    public static int PROCESS_STATE_SUCCESS = 1;
    public static ChangeQuickRedirect changeQuickRedirect;
    public int code;
    public String desc;
    public JSONObject endResponseObj;
    private int processState;
    public JSONObject requestObj;
    public long requestTime;
    public long rsponseTime;

    public void begainTrace(JSONObject requestObj2) {
        if (!PatchProxy.proxy(new Object[]{requestObj2}, this, changeQuickRedirect, false, 9929, new Class[]{JSONObject.class}, Void.TYPE).isSupported) {
            this.requestTime = System.currentTimeMillis();
            this.requestObj = requestObj2;
        }
    }

    public WebRtcServiceStateChangeLogBean() {
        this.code = 200;
        this.requestTime = 0;
        this.rsponseTime = 0;
        this.requestObj = new JSONObject();
        this.endResponseObj = new JSONObject();
        this.desc = "";
        this.processState = 0;
        this.requestTime = System.currentTimeMillis();
    }

    public void endTraceExcept(int code2, JSONObject jsonObject) {
        Object[] objArr = {new Integer(code2), jsonObject};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9930, new Class[]{Integer.TYPE, JSONObject.class}, Void.TYPE).isSupported) {
            this.code = code2;
            this.endResponseObj = jsonObject;
            this.rsponseTime = System.currentTimeMillis();
        }
    }

    public void endTraceSuccess(JSONObject jsonObject) {
        if (!PatchProxy.proxy(new Object[]{jsonObject}, this, changeQuickRedirect, false, 9931, new Class[]{JSONObject.class}, Void.TYPE).isSupported) {
            this.code = 200;
            this.endResponseObj = jsonObject;
            this.rsponseTime = System.currentTimeMillis();
        }
    }

    public WebRtcServiceStateChangeLogBean putData(JSONObject jsonObject, String key, Object v) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{jsonObject, key, v}, this, changeQuickRedirect2, false, 9932, new Class[]{JSONObject.class, String.class, Object.class}, WebRtcServiceStateChangeLogBean.class);
        if (proxy.isSupported) {
            return (WebRtcServiceStateChangeLogBean) proxy.result;
        }
        try {
            jsonObject.put(key, v);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return this;
    }
}

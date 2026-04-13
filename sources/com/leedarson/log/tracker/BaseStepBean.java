package com.leedarson.log.tracker;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.gson.Gson;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;
import io.netty.util.internal.StringUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class BaseStepBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    public transient long _beginTraceTimeSpan;
    public transient long _endTraceTimeSpan;
    public String beginTraceFormatTime;
    public int code;
    public String desc;
    public long duration;
    public String end;
    public String endTraceFormatTime;
    private transient SimpleDateFormat format;
    public transient HashMap<String, Object> request;
    private String response;
    public transient HashMap<String, Object> responseData;
    public String start;
    public String step;

    public BaseStepBean(String step2, int code2) {
        this.format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        this.duration = 0;
        this.response = "";
        this._beginTraceTimeSpan = 0;
        this._endTraceTimeSpan = 0;
        this.start = "";
        this.end = "";
        this.step = step2;
        this.code = code2;
        this._beginTraceTimeSpan = System.currentTimeMillis();
        this._endTraceTimeSpan = System.currentTimeMillis();
        this.beginTraceFormatTime = formatDate(this._beginTraceTimeSpan);
        this.endTraceFormatTime = formatDate(this._endTraceTimeSpan);
    }

    public BaseStepBean(String step2, int code2, long duration2) {
        this.format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        this.duration = 0;
        this.response = "";
        this._beginTraceTimeSpan = 0;
        this._endTraceTimeSpan = 0;
        this.start = "";
        this.end = "";
        this._beginTraceTimeSpan = System.currentTimeMillis();
        this.step = step2;
        this.code = code2;
        this.duration = duration2;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc2) {
        this.desc = desc2;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code2) {
        this.code = code2;
    }

    public String getStep() {
        return this.step;
    }

    public void setStep(String step2) {
        this.step = step2;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration2) {
        this.duration = duration2;
    }

    public HashMap<String, Object> getRequest() {
        return this.request;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response2) {
        if (!PatchProxy.proxy(new Object[]{response2}, this, changeQuickRedirect, false, 1359, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.response = response2;
            long currentTimeMillis = System.currentTimeMillis();
            this._endTraceTimeSpan = currentTimeMillis;
            this.endTraceFormatTime = formatDate(currentTimeMillis);
            this.duration = this._endTraceTimeSpan - this._beginTraceTimeSpan;
        }
    }

    public void putRequestParams(String key, Object obj) {
        Class[] clsArr = {String.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{key, obj}, this, changeQuickRedirect, false, 1360, clsArr, Void.TYPE).isSupported) {
            if (this.request == null) {
                this.request = new HashMap<>();
            }
            this.request.put(key, obj);
        }
    }

    public void putResponseParams(String key, Object obj) {
        Class[] clsArr = {String.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{key, obj}, this, changeQuickRedirect, false, 1361, clsArr, Void.TYPE).isSupported) {
            if (this.responseData == null) {
                this.responseData = new HashMap<>();
            }
            this.responseData.put(key, obj);
        }
    }

    public JSONObject toJson() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1362, new Class[0], JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        JSONObject item = new JSONObject(new Gson().toJson((Object) this));
        wrapRequest(item);
        wrapResponse(item);
        return item;
    }

    private void wrapRequest(JSONObject json) {
        if (!PatchProxy.proxy(new Object[]{json}, this, changeQuickRedirect, false, 1363, new Class[]{JSONObject.class}, Void.TYPE).isSupported) {
            JSONObject reqJson = new JSONObject();
            HashMap<String, Object> hashMap = this.request;
            if (hashMap != null && hashMap.size() > 0) {
                for (Map.Entry<String, Object> entry : this.request.entrySet()) {
                    reqJson.put(entry.getKey(), entry.getValue());
                }
            }
            json.put(Progress.REQUEST, (Object) reqJson);
        }
    }

    private void wrapResponse(JSONObject json) {
        if (!PatchProxy.proxy(new Object[]{json}, this, changeQuickRedirect, false, 1364, new Class[]{JSONObject.class}, Void.TYPE).isSupported) {
            JSONObject resJson = new JSONObject();
            HashMap<String, Object> hashMap = this.responseData;
            if (hashMap != null && hashMap.size() > 0) {
                for (Map.Entry<String, Object> entry : this.responseData.entrySet()) {
                    resJson.put(entry.getKey(), entry.getValue());
                }
            }
            json.put("response", (Object) resJson);
        }
    }

    public String toSimpleString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1365, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "{" + "\"step\":\"" + this.step + StringUtil.DOUBLE_QUOTE + ",\"code\":" + this.code + '}';
    }

    public String getFormatTime() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1366, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss").format(new Date(System.currentTimeMillis()));
    }

    public void beginTrace() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1367, new Class[0], Void.TYPE).isSupported) {
            this._beginTraceTimeSpan = System.currentTimeMillis();
        }
    }

    public void endTrace(String desc2, int code2) {
        if (!PatchProxy.proxy(new Object[]{desc2, new Integer(code2)}, this, changeQuickRedirect, false, 1368, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            long currentTimeMillis = System.currentTimeMillis();
            this._endTraceTimeSpan = currentTimeMillis;
            this.endTraceFormatTime = formatDate(currentTimeMillis);
            this.code = code2;
            long j = this._endTraceTimeSpan;
            long j2 = this._beginTraceTimeSpan;
            this.duration = j - j2;
            this.desc = desc2;
            this.start = formatDate(j2);
            this.end = formatDate(this._endTraceTimeSpan);
        }
    }

    private String formatDate(long timeSpan) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Long(timeSpan)}, this, changeQuickRedirect, false, 1369, new Class[]{Long.TYPE}, String.class);
        return proxy.isSupported ? (String) proxy.result : this.format.format(Long.valueOf(timeSpan));
    }

    public void resetStartTime() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_FTP_REQ, new Class[0], Void.TYPE).isSupported) {
            this._beginTraceTimeSpan = System.currentTimeMillis();
            this._endTraceTimeSpan = System.currentTimeMillis();
            this.beginTraceFormatTime = formatDate(this._beginTraceTimeSpan);
            this.endTraceFormatTime = formatDate(this._endTraceTimeSpan);
        }
    }
}

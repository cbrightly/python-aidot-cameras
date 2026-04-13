package com.leedarson.smartcamera.bean;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.leedarson.log.tracker.BaseStepBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.json.JSONObject;

public class IPCLiveStepBean extends BaseStepBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int deviceOnlieStatue = -1;
    private long totalDuration = 0;

    public IPCLiveStepBean(String step, int code) {
        super(step, code);
    }

    public void setTotalDuration(long totalDuration2) {
        this.totalDuration = totalDuration2;
    }

    public long getTotalDuration() {
        return this.totalDuration;
    }

    public int getDeviceOnlieStatue() {
        return this.deviceOnlieStatue;
    }

    public void setDeviceOnlieStatue(int deviceOnlieStatue2) {
        this.deviceOnlieStatue = deviceOnlieStatue2;
    }

    public JSONObject toJson() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9635, new Class[0], JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        JSONObject obj = super.toJson();
        if (obj != null && obj.has("totalDuration") && obj.getInt("totalDuration") == 0) {
            obj.remove("totalDuration");
        }
        if (obj != null && obj.has(TypedValues.TransitionType.S_DURATION) && obj.getInt(TypedValues.TransitionType.S_DURATION) == 0) {
            obj.remove(TypedValues.TransitionType.S_DURATION);
        }
        if (obj != null && obj.has("response") && obj.getString("response").isEmpty()) {
            obj.remove("response");
        }
        if (obj != null && obj.has(Progress.REQUEST) && obj.getJSONObject(Progress.REQUEST).length() == 0) {
            obj.remove(Progress.REQUEST);
        }
        if (obj != null && obj.has("deviceOnlieStatue") && obj.getInt("deviceOnlieStatue") == -1) {
            obj.remove("deviceOnlieStatue");
        }
        if (obj != null && obj.has("code") && obj.getInt("code") == 0) {
            obj.remove("code");
            obj.remove("_beginTraceTimeSpan");
            obj.remove("_endTraceTimeSpan");
        }
        return obj;
    }
}

package com.leedarson.serviceimpl.bodyfat.model;

import com.leedarson.bean.Constants;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class BFDeviceNotifyDataBean {
    public static final int TYPE_HISTORY = 3;
    public static final int TYPE_INSTABLE = 1;
    public static final int TYPE_STABLE = 2;
    public static ChangeQuickRedirect changeQuickRedirect;
    public int algorithmsType;
    public int heartRate;
    public int impedance;
    public int state;
    public long timestamp;
    public int type;
    public int weight1000;

    public BFDeviceNotifyDataBean(int type2) {
        this.type = type2;
        if (type2 != 3) {
            this.timestamp = System.currentTimeMillis() / 1000;
        }
    }

    public String toString() {
        String typeDesc;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6929, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        int i = this.type;
        if (i == 1) {
            typeDesc = "不稳定";
        } else if (i == 2) {
            typeDesc = "稳定";
        } else {
            typeDesc = "历史";
        }
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"type\":");
        sb.append(this.type + typeDesc);
        sb.append(",\"state\":");
        sb.append(this.state);
        sb.append(",\"weight1000\":");
        sb.append(this.weight1000);
        sb.append(",\"heartRate\":");
        sb.append(this.heartRate);
        sb.append(",\"algorithmsType\":");
        sb.append(this.algorithmsType);
        sb.append(",\"impedance\":");
        sb.append(this.impedance);
        sb.append(",\"timestamp\":");
        sb.append(this.timestamp);
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toBaseJSON(boolean hasUserInfo) {
        Object[] objArr = {new Byte(hasUserInfo ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 6930, new Class[]{Boolean.TYPE}, JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        JSONObject json = new JSONObject();
        json.put(IjkMediaMeta.IJKM_KEY_TYPE, this.type);
        json.put(Constants.ACTION_STATE, this.state);
        json.put("timestamp", this.timestamp);
        json.put("weight", (double) ((((float) this.weight1000) * 1.0f) / 1000.0f));
        json.put("heartRate", this.heartRate);
        json.put("impedance", this.impedance);
        json.put("hasUserInfo", hasUserInfo);
        return json;
    }

    public boolean isSame(BFDeviceNotifyDataBean data) {
        if (data != null && data.weight1000 == this.weight1000 && data.heartRate == this.heartRate && data.impedance == this.impedance && data.algorithmsType == this.algorithmsType) {
            return true;
        }
        return false;
    }
}

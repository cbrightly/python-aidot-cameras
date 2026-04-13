package com.leedarson.skiprope.bean;

import com.google.gson.Gson;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SRDeviceHistoryDataBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    public int fallDownCount;
    public List<HistoryItemBean> historyItems = new ArrayList();
    public long jumpedCountActually;
    public long jumpedSecondActually;
    public String mac;
    public int maxFrequency;
    public int maxKeepJump;
    public int meanFrequency;
    public int mode;
    public int target;
    public long timestamp;

    public void addHistoryItem(HistoryItemBean item) {
        if (!PatchProxy.proxy(new Object[]{item}, this, changeQuickRedirect, false, 9495, new Class[]{HistoryItemBean.class}, Void.TYPE).isSupported) {
            if (!(item.count == 0 || item.duration == 0)) {
                this.historyItems.add(item);
                this.fallDownCount = this.historyItems.size() - 1;
            }
            for (HistoryItemBean bean : this.historyItems) {
                int i = this.maxKeepJump;
                int i2 = bean.count;
                if (i < i2) {
                    this.maxKeepJump = i2;
                }
            }
        }
    }

    public static class HistoryItemBean {
        public int count;
        public int duration;

        public HistoryItemBean(int duration2, int count2) {
            this.duration = duration2;
            this.count = count2;
        }
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9496, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "{" + "\"mac\":\"" + this.mac + StringUtil.DOUBLE_QUOTE + ",\"target\":" + this.target + ",\"mode\":" + this.mode + ",\"jumpedSecondActually\":" + this.jumpedSecondActually + ",\"jumpedCountActually\":" + this.jumpedCountActually + ",\"meanFrequency\":" + this.meanFrequency + ",\"maxFrequency\":" + this.maxFrequency + ",\"timestamp\":" + this.timestamp + ",\"fallDownCount\":" + this.fallDownCount + ",\"maxKeepJump\":" + this.maxKeepJump + ",\"historyItems\":" + getHistoryItem() + '}';
    }

    private JSONArray getHistoryItem() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9497, new Class[0], JSONArray.class);
        if (proxy.isSupported) {
            return (JSONArray) proxy.result;
        }
        JSONArray array = new JSONArray();
        for (HistoryItemBean itemBean : this.historyItems) {
            try {
                array.put((Object) new JSONObject(new Gson().toJson((Object) itemBean)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return array;
    }
}

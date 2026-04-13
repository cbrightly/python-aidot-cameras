package com.leedarson.newui.repos.beans;

import com.leedarson.bean.EventBean;
import com.leedarson.newui.cloud_play_back.repos.a0;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;

public class EventListItemBean extends EventBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    public ArrayList<String> eventCodes = new ArrayList<>();
    public long eventTime = 0;
    public int frameworkFlag = 1;
    public EventUrlResponseItemBean playerReource;
    public int view_type = 0;

    public boolean checkLocalVideoSourceAvailable() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4469, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.playerReource == null) {
            return false;
        }
        for (int i = 0; i < this.playerReource.videoUrlList.size(); i++) {
            if (!a0.b(this.playerReource.videoUrlList.get(i), getDeviceId(), getEventUuid())) {
                return false;
            }
        }
        return true;
    }
}

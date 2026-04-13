package com.leedarson.bean;

import com.chad.library.adapter.base.entity.b;
import com.leedarson.newui.repos.beans.EventListItemBean;
import com.meituan.robust.ChangeQuickRedirect;
import java.util.List;

public class SecurityCamEntity implements b {
    public static final int KVS = 2;
    public static final int TUTK = 1;
    public static ChangeQuickRedirect changeQuickRedirect;
    private IpcDeviceBean deviceBean;
    private List<EventListItemBean> eventList;
    private boolean isLive = true;
    private boolean isMute = true;
    private int itemType;

    public SecurityCamEntity(int itemType2, IpcDeviceBean deviceBean2) {
        this.itemType = itemType2;
        this.deviceBean = deviceBean2;
    }

    public boolean isMute() {
        return this.isMute;
    }

    public void setMute(boolean mute) {
        this.isMute = mute;
    }

    public IpcDeviceBean getDeviceBean() {
        return this.deviceBean;
    }

    public boolean isLive() {
        return this.isLive;
    }

    public void setLive(boolean live) {
        this.isLive = live;
    }

    public List<EventListItemBean> getEventList() {
        return this.eventList;
    }

    public void setEventList(List<EventListItemBean> list) {
        this.eventList = list;
    }

    public int getItemType() {
        return this.itemType;
    }
}

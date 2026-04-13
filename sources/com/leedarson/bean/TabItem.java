package com.leedarson.bean;

import com.leedarson.R$drawable;
import com.meituan.robust.ChangeQuickRedirect;

public enum TabItem {
    liveStream("Live Stream", R$drawable.ic_tab_live_selector),
    events("Events", R$drawable.ic_tab_event_selector);
    
    public static ChangeQuickRedirect changeQuickRedirect;
    int resId;
    String tabName;

    private TabItem(String tabName2, int resId2) {
        this.tabName = tabName2;
        this.resId = resId2;
    }

    public void setTabName(String tabName2) {
        this.tabName = tabName2;
    }

    public String getTabName() {
        return this.tabName;
    }

    public int getResId() {
        return this.resId;
    }

    public void setResId(int resId2) {
        this.resId = resId2;
    }
}

package com.leedarson.bean;

import com.chad.library.adapter.base.entity.b;
import com.leedarson.newui.repos.beans.EventListItemBean;
import com.meituan.robust.ChangeQuickRedirect;

public class SecurityEventEntity implements b {
    public static final int VIEW_TYPE_ALL_EVENT = 1;
    public static final int VIEW_TYPE_EVENT = 0;
    public static final int VIEW_TYPE_NO_EMPTY_FOOTER = 3;
    public static final int VIEW_TYPE_NO_MORE = 2;
    public static ChangeQuickRedirect changeQuickRedirect;
    private EventListItemBean eventListItemBean;
    private int itemType;

    public SecurityEventEntity(int itemType2, EventListItemBean eventListItemBean2) {
        this.itemType = itemType2;
        this.eventListItemBean = eventListItemBean2;
    }

    public int getItemType() {
        return this.itemType;
    }

    public EventListItemBean getEventListItemBean() {
        return this.eventListItemBean;
    }
}

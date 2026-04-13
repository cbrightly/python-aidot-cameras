package com.leedarson.bean;

import com.chad.library.adapter.base.entity.b;
import com.leedarson.newui.repos.beans.EventListItemBean;
import com.meituan.robust.ChangeQuickRedirect;
import java.util.List;

public class CloudEventEntity implements b {
    public static final int VIEW_TYPE_EVENT = 0;
    public static final int VIEW_TYPE_FOLD = 1;
    public static ChangeQuickRedirect changeQuickRedirect;
    private EventListItemBean eventListItemBean;
    private List<EventListItemBean> foldLists;
    private int itemType;

    public CloudEventEntity(int itemType2, EventListItemBean eventListItemBean2) {
        this.itemType = itemType2;
        this.eventListItemBean = eventListItemBean2;
    }

    public CloudEventEntity(int itemType2, List<EventListItemBean> foldLists2) {
        this.itemType = itemType2;
        this.foldLists = foldLists2;
    }

    public int getItemType() {
        return this.itemType;
    }

    public EventListItemBean getEventListItemBean() {
        return this.eventListItemBean;
    }

    public List<EventListItemBean> getFoldLists() {
        return this.foldLists;
    }
}

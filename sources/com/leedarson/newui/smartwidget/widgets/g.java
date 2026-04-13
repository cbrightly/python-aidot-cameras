package com.leedarson.newui.smartwidget.widgets;

import com.leedarson.newui.cloud_play_back.repos.beans.LDSBasePageBean;
import com.leedarson.newui.repos.beans.EventListItemBean;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class g implements e {
    public final /* synthetic */ SecurityPlaybackDropItemView c;
    public final /* synthetic */ EventListItemBean d;

    public /* synthetic */ g(SecurityPlaybackDropItemView securityPlaybackDropItemView, EventListItemBean eventListItemBean) {
        this.c = securityPlaybackDropItemView;
        this.d = eventListItemBean;
    }

    public final void accept(Object obj) {
        this.c.r(this.d, (LDSBasePageBean) obj);
    }
}

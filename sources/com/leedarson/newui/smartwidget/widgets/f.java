package com.leedarson.newui.smartwidget.widgets;

import com.leedarson.newui.repos.beans.EventListItemBean;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class f implements e {
    public final /* synthetic */ SecurityPlaybackDropItemView c;
    public final /* synthetic */ EventListItemBean d;

    public /* synthetic */ f(SecurityPlaybackDropItemView securityPlaybackDropItemView, EventListItemBean eventListItemBean) {
        this.c = securityPlaybackDropItemView;
        this.d = eventListItemBean;
    }

    public final void accept(Object obj) {
        this.c.t(this.d, (Throwable) obj);
    }
}

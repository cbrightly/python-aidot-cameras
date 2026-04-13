package com.leedarson.newui.smartwidget.widgets;

import com.leedarson.newui.repos.beans.EventListItemBean;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class c implements e {
    public final /* synthetic */ SecurityPlaybackDropItemView c;

    public /* synthetic */ c(SecurityPlaybackDropItemView securityPlaybackDropItemView) {
        this.c = securityPlaybackDropItemView;
    }

    public final void accept(Object obj) {
        this.c.B((EventListItemBean) obj);
    }
}

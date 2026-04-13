package com.leedarson.newui.smartwidget;

import com.leedarson.newui.cloud_play_back.repos.beans.LDSBasePageBean;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class b implements e {
    public final /* synthetic */ c c;
    public final /* synthetic */ int d;

    public /* synthetic */ b(c cVar, int i) {
        this.c = cVar;
        this.d = i;
    }

    public final void accept(Object obj) {
        this.c.t(this.d, (LDSBasePageBean) obj);
    }
}

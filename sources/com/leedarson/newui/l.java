package com.leedarson.newui;

import com.leedarson.newui.cloud_play_back.repos.beans.LDSBasePageBean;
import com.leedarson.newui.repos.beans.EventListItemBean;
import com.leedarson.newui.repoter.beans.ELKStepRecordBean;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class l implements e {
    public final /* synthetic */ CloudPlaybackFragment c;
    public final /* synthetic */ ELKStepRecordBean d;
    public final /* synthetic */ EventListItemBean f;

    public /* synthetic */ l(CloudPlaybackFragment cloudPlaybackFragment, ELKStepRecordBean eLKStepRecordBean, EventListItemBean eventListItemBean) {
        this.c = cloudPlaybackFragment;
        this.d = eLKStepRecordBean;
        this.f = eventListItemBean;
    }

    public final void accept(Object obj) {
        this.c.p3(this.d, this.f, (LDSBasePageBean) obj);
    }
}

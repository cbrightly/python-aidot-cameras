package com.leedarson.newui.cloud_play_back;

import com.leedarson.newui.cloud_play_back.repos.beans.LDSBasePageBean;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class j implements e {
    public final /* synthetic */ CloudPlayBackEventSignalFragment c;

    public /* synthetic */ j(CloudPlayBackEventSignalFragment cloudPlayBackEventSignalFragment) {
        this.c = cloudPlayBackEventSignalFragment;
    }

    public final void accept(Object obj) {
        this.c.E2((LDSBasePageBean) obj);
    }
}

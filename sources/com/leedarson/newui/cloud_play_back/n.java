package com.leedarson.newui.cloud_play_back;

import com.leedarson.newui.cloud_play_back.repos.beans.LDSBaseBean;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class n implements e {
    public final /* synthetic */ CloudPlayBackEventSignalFragment c;

    public /* synthetic */ n(CloudPlayBackEventSignalFragment cloudPlayBackEventSignalFragment) {
        this.c = cloudPlayBackEventSignalFragment;
    }

    public final void accept(Object obj) {
        this.c.j2((LDSBaseBean) obj);
    }
}

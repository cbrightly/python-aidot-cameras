package com.leedarson.newui.cloud_play_back;

import com.leedarson.newui.cloud_play_back.repos.beans.EventItemDetailBean;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class v implements e {
    public final /* synthetic */ CloudPlayBackEventSignalFragment c;

    public /* synthetic */ v(CloudPlayBackEventSignalFragment cloudPlayBackEventSignalFragment) {
        this.c = cloudPlayBackEventSignalFragment;
    }

    public final void accept(Object obj) {
        this.c.H2((EventItemDetailBean) obj);
    }
}

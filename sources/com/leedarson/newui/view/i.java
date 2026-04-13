package com.leedarson.newui.view;

import com.leedarson.newui.cloud_play_back.repos.beans.LDSBaseBean;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class i implements e {
    public final /* synthetic */ BaseKVSCameraView c;
    public final /* synthetic */ String d;

    public /* synthetic */ i(BaseKVSCameraView baseKVSCameraView, String str) {
        this.c = baseKVSCameraView;
        this.d = str;
    }

    public final void accept(Object obj) {
        this.c.F(this.d, (LDSBaseBean) obj);
    }
}

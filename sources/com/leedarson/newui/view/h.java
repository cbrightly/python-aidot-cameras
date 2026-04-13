package com.leedarson.newui.view;

import com.leedarson.smartcamera.bean.KVSParamBean;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class h implements e {
    public final /* synthetic */ BaseKVSCameraView c;

    public /* synthetic */ h(BaseKVSCameraView baseKVSCameraView) {
        this.c = baseKVSCameraView;
    }

    public final void accept(Object obj) {
        this.c.x((KVSParamBean) obj);
    }
}

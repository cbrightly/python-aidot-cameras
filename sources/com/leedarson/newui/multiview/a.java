package com.leedarson.newui.multiview;

import com.leedarson.bean.IpcDeviceComparableBean;
import com.leedarson.newui.view.LiveCameraView;

/* compiled from: lambda */
public final /* synthetic */ class a implements LiveCameraView.p {
    public final /* synthetic */ MultiViewAdapter a;
    public final /* synthetic */ IpcDeviceComparableBean b;

    public /* synthetic */ a(MultiViewAdapter multiViewAdapter, IpcDeviceComparableBean ipcDeviceComparableBean) {
        this.a = multiViewAdapter;
        this.b = ipcDeviceComparableBean;
    }

    public final void a() {
        MultiViewAdapter.C(this.a, this.b);
    }
}

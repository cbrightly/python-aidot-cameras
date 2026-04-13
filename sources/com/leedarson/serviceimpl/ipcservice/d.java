package com.leedarson.serviceimpl.ipcservice;

import android.view.View;
import com.leedarson.serviceinterface.INoNetSnapTipView;

/* compiled from: lambda */
public final /* synthetic */ class d implements View.OnClickListener {
    public final /* synthetic */ IpcServiceImpl c;
    public final /* synthetic */ INoNetSnapTipView d;

    public /* synthetic */ d(IpcServiceImpl ipcServiceImpl, INoNetSnapTipView iNoNetSnapTipView) {
        this.c = ipcServiceImpl;
        this.d = iNoNetSnapTipView;
    }

    public final void onClick(View view) {
        this.c.w(this.d, view);
    }
}

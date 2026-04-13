package com.leedarson.mqtt;

import com.leedarson.serviceinterface.IpcService;
import io.reactivex.functions.e;
import org.json.JSONObject;

/* compiled from: lambda */
public final /* synthetic */ class g implements e {
    public final /* synthetic */ l c;
    public final /* synthetic */ IpcService d;

    public /* synthetic */ g(l lVar, IpcService ipcService) {
        this.c = lVar;
        this.d = ipcService;
    }

    public final void accept(Object obj) {
        this.c.N(this.d, (JSONObject) obj);
    }
}

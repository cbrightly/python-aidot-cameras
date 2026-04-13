package com.leedarson.smarthome.robust;

import com.leedarson.smarthome.robust.beans.ELKRobustStepRecordBean;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class d implements e {
    public final /* synthetic */ h c;
    public final /* synthetic */ ELKRobustStepRecordBean d;

    public /* synthetic */ d(h hVar, ELKRobustStepRecordBean eLKRobustStepRecordBean) {
        this.c = hVar;
        this.d = eLKRobustStepRecordBean;
    }

    public final void accept(Object obj) {
        this.c.n(this.d, (Throwable) obj);
    }
}

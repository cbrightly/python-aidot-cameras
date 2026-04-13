package com.leedarson.smarthome.robust;

import com.leedarson.smarthome.robust.beans.ELKRobustStepRecordBean;
import com.leedarson.smarthome.robust.beans.PatchResultConfigBean;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class b implements e {
    public final /* synthetic */ h c;
    public final /* synthetic */ ELKRobustStepRecordBean d;

    public /* synthetic */ b(h hVar, ELKRobustStepRecordBean eLKRobustStepRecordBean) {
        this.c = hVar;
        this.d = eLKRobustStepRecordBean;
    }

    public final void accept(Object obj) {
        this.c.l(this.d, (PatchResultConfigBean) obj);
    }
}

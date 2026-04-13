package com.leedarson.serviceimpl.business;

import com.leedarson.serviceimpl.business.reporters.OTAStepBean;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class s implements e {
    public final /* synthetic */ TRVOta c;
    public final /* synthetic */ OTAStepBean d;

    public /* synthetic */ s(TRVOta tRVOta, OTAStepBean oTAStepBean) {
        this.c = tRVOta;
        this.d = oTAStepBean;
    }

    public final void accept(Object obj) {
        this.c.d(this.d, (Throwable) obj);
    }
}

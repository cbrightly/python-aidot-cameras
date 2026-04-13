package com.leedarson.serviceimpl.business;

import com.leedarson.serviceimpl.business.TRVOta;
import com.leedarson.serviceimpl.business.reporters.OTAStepBean;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class q implements e {
    public final /* synthetic */ TRVOta c;
    public final /* synthetic */ OTAStepBean d;
    public final /* synthetic */ String f;
    public final /* synthetic */ TRVOta.TRVOTACallback q;

    public /* synthetic */ q(TRVOta tRVOta, OTAStepBean oTAStepBean, String str, TRVOta.TRVOTACallback tRVOTACallback) {
        this.c = tRVOta;
        this.d = oTAStepBean;
        this.f = str;
        this.q = tRVOTACallback;
    }

    public final void accept(Object obj) {
        this.c.c(this.d, this.f, this.q, (Integer) obj);
    }
}

package com.leedarson.serviceimpl.business;

import com.leedarson.serviceinterface.BusinessService;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class g implements e {
    public final /* synthetic */ BusinessServiceImpl c;
    public final /* synthetic */ BusinessService.UploadCallback d;
    public final /* synthetic */ String f;

    public /* synthetic */ g(BusinessServiceImpl businessServiceImpl, BusinessService.UploadCallback uploadCallback, String str) {
        this.c = businessServiceImpl;
        this.d = uploadCallback;
        this.f = str;
    }

    public final void accept(Object obj) {
        this.c.i(this.d, this.f, obj);
    }
}

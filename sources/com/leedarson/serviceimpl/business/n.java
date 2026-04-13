package com.leedarson.serviceimpl.business;

import com.leedarson.serviceinterface.BusinessService;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class n implements e {
    public final /* synthetic */ BusinessServiceImpl c;
    public final /* synthetic */ BusinessService.UploadCallback d;

    public /* synthetic */ n(BusinessServiceImpl businessServiceImpl, BusinessService.UploadCallback uploadCallback) {
        this.c = businessServiceImpl;
        this.d = uploadCallback;
    }

    public final void accept(Object obj) {
        this.c.h(this.d, obj);
    }
}

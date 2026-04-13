package com.leedarson.serviceimpl.business;

import com.leedarson.serviceinterface.BusinessService;
import io.reactivex.functions.e;
import org.json.JSONObject;

/* compiled from: lambda */
public final /* synthetic */ class h implements e {
    public final /* synthetic */ BusinessServiceImpl c;
    public final /* synthetic */ JSONObject d;
    public final /* synthetic */ BusinessService.UploadCallback f;

    public /* synthetic */ h(BusinessServiceImpl businessServiceImpl, JSONObject jSONObject, BusinessService.UploadCallback uploadCallback) {
        this.c = businessServiceImpl;
        this.d = jSONObject;
        this.f = uploadCallback;
    }

    public final void accept(Object obj) {
        this.c.j(this.d, this.f, (String) obj);
    }
}

package com.leedarson.serviceimpl.business;

import com.google.gson.Gson;
import com.leedarson.base.beans.FeedbackRequestBean;
import com.leedarson.serviceinterface.BusinessService;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class m implements e {
    public final /* synthetic */ BusinessServiceImpl c;
    public final /* synthetic */ FeedbackRequestBean d;
    public final /* synthetic */ Gson f;
    public final /* synthetic */ BusinessService.UploadCallback q;

    public /* synthetic */ m(BusinessServiceImpl businessServiceImpl, FeedbackRequestBean feedbackRequestBean, Gson gson, BusinessService.UploadCallback uploadCallback) {
        this.c = businessServiceImpl;
        this.d = feedbackRequestBean;
        this.f = gson;
        this.q = uploadCallback;
    }

    public final void accept(Object obj) {
        this.c.l(this.d, this.f, this.q, (String) obj);
    }
}

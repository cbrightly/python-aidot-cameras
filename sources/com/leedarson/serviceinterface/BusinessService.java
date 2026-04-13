package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;
import com.leedarson.base.beans.FeedbackRequestBean;

public interface BusinessService extends c {

    public interface UploadCallback<T> {
        void fail(Object obj, Object obj2);

        void success(T t);
    }

    void handleData(String str, Activity activity, String str2, String str3);

    /* synthetic */ void init(Context context);

    void reUploadLog(String str);

    void reportIssues(FeedbackRequestBean feedbackRequestBean, UploadCallback uploadCallback);

    void startNetDiag();
}

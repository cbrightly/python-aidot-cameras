package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;
import java.io.File;

public interface HttpService extends c {
    void cancelRequest();

    void handleData(String str, Activity activity, String str2, String str3);

    void handleData(String str, Activity activity, String str2, String str3, HttpResponseListener httpResponseListener);

    void handleData(String str, String str2, File file);

    /* synthetic */ void init(Context context);
}

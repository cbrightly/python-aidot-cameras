package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;

public interface BleBusinessService extends c, LDSService {
    void disConnectAllDevices();

    void handleData(Activity activity, String str, String str2, String str3);

    /* synthetic */ void init(Context context);

    void onClientConnectDisConnect(String str);

    void reset(String str);
}

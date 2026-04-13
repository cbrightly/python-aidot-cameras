package com.leedarson.serviceinterface;

import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;

public interface BleService extends c {
    void handleData(String str, String str2, String str3);

    /* synthetic */ void init(Context context);
}

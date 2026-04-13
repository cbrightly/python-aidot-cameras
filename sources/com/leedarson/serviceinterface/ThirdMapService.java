package com.leedarson.serviceinterface;

import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;

public interface ThirdMapService extends c {
    void getAddressInfo(String str, float f, float f2);

    /* synthetic */ void init(Context context);

    void locale(String str);
}

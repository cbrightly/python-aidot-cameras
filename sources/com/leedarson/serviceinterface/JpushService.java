package com.leedarson.serviceinterface;

import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;

public interface JpushService extends c {
    String getRegId();

    /* synthetic */ void init(Context context);

    void initJpush();

    void initJpush(boolean z);
}

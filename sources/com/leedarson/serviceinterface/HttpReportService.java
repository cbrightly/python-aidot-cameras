package com.leedarson.serviceinterface;

import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;

public interface HttpReportService extends c {
    String getNetWorkInfoDetail();

    String getUuid();

    /* synthetic */ void init(Context context);

    void onBackgroundAndFrontSwitch(boolean z);
}

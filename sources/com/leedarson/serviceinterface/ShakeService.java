package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;

public interface ShakeService extends c {
    void handleShake(Activity activity);

    /* synthetic */ void init(Context context);

    void jumpShakeActivity(Context context);

    void setJumpShake(boolean z);
}

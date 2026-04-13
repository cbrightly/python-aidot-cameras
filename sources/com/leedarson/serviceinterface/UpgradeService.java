package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;

public interface UpgradeService extends c {
    void handleData(Activity activity, String str, String str2, String str3);

    /* synthetic */ void init(Context context);

    void unZipH5(String str, int i);
}

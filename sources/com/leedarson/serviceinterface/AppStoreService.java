package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;

public interface AppStoreService extends c {
    public static final String ACTION_OPEN_USER_REVIEWS = "AppStore.openUserReviews";

    void handleData(Activity activity, String str, String str2, String str3);

    /* synthetic */ void init(Context context);
}

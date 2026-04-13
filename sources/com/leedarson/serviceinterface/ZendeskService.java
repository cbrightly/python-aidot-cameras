package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;

public interface ZendeskService extends c {
    public static final String ACTION_GET_UNREAD_MESSAGE_COUNT = "getUnreadMessageCount";
    public static final String ACTION_OPEN = "open";
    public static final String KEY_ZENDESK = "Zendesk";
    public static final String ON_MESSAGE = "onMessage";

    void getJwt();

    void handleData(String str, Activity activity, String str2, String str3);

    /* synthetic */ void init(Context context);

    void initZendesk(Activity activity);

    void logout();

    void openZendesk(Activity activity);
}

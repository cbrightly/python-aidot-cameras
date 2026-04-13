package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;

public interface IMService extends c {
    public static final String CLEAR_UNREAD_COUNT = "clearUnreadCount";
    public static final String CLEAR__GROUP_UNREAD_COUNT = "clearGroupUnreadCount";
    public static final String CLOSE_CONVERSATION = "closeConversation";
    public static final String CREATE_CONVERSATION = "createConversation";
    public static final String DEL_GROUP_CONVERSATION = "deleteGroupConversation";
    public static final String GET_ALL_UNREAD_COUNT = "getAllUnreadCount";
    public static final String GET_CONVERSATION = "getConversations";
    public static final String KEY_IM = "IM";
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String NOTIFY_MESSAGE = "notifyMessage";
    public static final String ON_MESSAGE = "onMessage";
    public static final String ON_NAVIGATION = "onNavigation";
    public static final String SHOW_KEY_BOARD = "showKeyboard";

    void handleData(String str, String str2, String str3);

    /* synthetic */ void init(Context context);

    void initIMSDK(Activity activity);

    void navigateTo(String str, String str2, int i, String str3, String str4, String str5, int i2);

    void releaseIMSDK();
}

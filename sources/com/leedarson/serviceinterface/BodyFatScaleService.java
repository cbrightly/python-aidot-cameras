package com.leedarson.serviceinterface;

import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;

public interface BodyFatScaleService extends c {
    public static final String GET_BODY_FAT_DATA = "getBodyFatData";
    public static final String KEY_BODY_FAT = "BodyFatScale";
    public static final String ON_MESSAGE = "onMessage";
    public static final String SET_CURRENT_USER_INFO = "setCurrentUserInfo";
    public static final String SET_USER_INFO_LIST = "setUserInfoList";
    public static final String START_SCAN = "startScan";
    public static final String STOP_SCAN = "stopScan";
    public static final String TEST_CRASH = "test_crash";

    void handleData(String str, String str2, String str3);

    /* synthetic */ void init(Context context);

    void initBodyFatSDK(Context context);

    void releaseBodyFatSDK();
}

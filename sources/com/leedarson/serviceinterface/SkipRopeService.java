package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;

public interface SkipRopeService extends c {
    public static final String CHECK_SOURCE = "checkSource";
    public static final String GET_BGM_LIST = "getBgmList";
    public static final String KEY_SKIP_ROPE = "SkipRope";
    public static final String ON_MESSAGE = "onMessage";
    public static final String PLAY_BGM = "playBgm";
    public static final String SET_MUTE = "setMute";
    public static final String SHARE = "share";
    public static final String START = "start";
    public static final String START_RECV_DATA = "startReceiveData";
    public static final String STOP = "stop";
    public static final String STOP_BGM = "stopBgm";
    public static final String STOP_RECV_DATA = "stopReceiveData";
    public static final String TEST = "test";

    void checkSource();

    void handleData(String str, Activity activity, String str2, String str3);

    /* synthetic */ void init(Context context);

    void initSkipRopeSDK(Context context);

    void releaseSkipRopeSDK();

    void stopJump();
}

package com.leedarson.serviceinterface;

import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;
import org.json.JSONArray;

public interface LightsRhythmService extends c {
    public static final String ACTION_GET_PERMISSION = "getPermission";
    public static final String ACTION_GET_STATUS = "getStatus";
    public static final String ACTION_SET_CONFIG = "setConfig";
    public static final String ACTION_SET_MODE = "setMode";
    public static final String ACTION_START = "start";
    public static final String ACTION_STOP = "stop";
    public static final int ERR_NO_PERMISSION = 401;
    public static final int ERR_REFUSE_ONE_TIME_PERMISSION = 4010;
    public static final String KEY_MUSIC_RHYTHM = "VoiceRhythm";
    public static final String ON_MESSAGE = "onMessage";
    public static final String ON_STATUS_CHANGE = "onStatusChange";

    JSONArray getRhythmDevices();

    void handleData(String str, String str2, String str3);

    /* synthetic */ void init(Context context);

    void initRhythm(Context context);

    boolean isMeshRhythm();

    boolean isRhythm();

    void onResume();

    void reportELK(String str, String str2);

    void sendToDevicesTest();

    void setActionStart();

    void setActionStop();

    void stopRhythmWithParams(String str);

    void unInit();
}

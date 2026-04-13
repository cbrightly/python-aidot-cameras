package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;

public interface AudioPlayerService extends c {
    public static final String ACTION_GET_PERMISSION = "getPermission";
    public static final String ACTION_GET_STATUS = "getStatus";
    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_PLAY = "play";
    public static final String ACTION_SET_AUDIO_SOURCE = "setAudioSource";
    public static final String ACTION_SET_CLEAR_COUNT_DOWN = "clearCountdown";
    public static final String ACTION_SET_COUNT_DOWN = "setCountdown";
    public static final String ACTION_SET_MODE = "setMode";
    public static final int ERR_NO_PERMISSION = 401;
    public static final String KEY_MUSIC_RHYTHM = "AudioPlayer";
    public static final String ON_MESSAGE = "onMessage";

    void handleData(Activity activity, String str, String str2, String str3);

    /* synthetic */ void init(Context context);

    void initAudioPlayer(Context context);

    void setActionStart();

    void setActionStop();

    void unInitAudioPlayer();
}

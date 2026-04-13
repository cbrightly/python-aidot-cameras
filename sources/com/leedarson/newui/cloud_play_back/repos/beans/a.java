package com.leedarson.newui.cloud_play_back.repos.beans;

/* compiled from: PlayErrorTrackException */
public class a extends Throwable {
    public static final int ERROR_CONNECT_NETTY = -3;
    public static final int ERROR_GET_CLOUD_STRESM = -5;
    public static final int ERROR_GET_CONNECT_PARAMS = -1;
    public static final int ERROR_GET_RECORD_SOURCE = -2;
    public static final int ERROR_TCP_LOGIN = -4;
    public int _code = 0;
    public String _desc = "";

    public a(int code, String desc) {
        this._code = code;
        this._desc = desc;
    }
}

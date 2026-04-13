package com.leedarson.serviceinterface.listener;

public interface OnControlRespListener {
    void onFail(int i, String str, String str2);

    void onResult(int i, String str);
}

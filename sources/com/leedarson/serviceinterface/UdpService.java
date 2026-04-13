package com.leedarson.serviceinterface;

import android.content.Context;
import android.net.Network;
import com.alibaba.android.arouter.facade.template.c;

public interface UdpService extends c, LDSService {

    public interface CommonUdpCallback {
        void onBindSuccess(String str, String str2);
    }

    void commonBind(int i, CommonUdpCallback commonUdpCallback);

    void handleData(String str, String str2);

    /* synthetic */ void init(Context context);

    void removeNetWork();

    void sendUdpMessage(boolean z, String str, int i, String str2, byte[] bArr);

    void setNetWork(Network network);
}

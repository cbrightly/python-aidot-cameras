package com.leedarson.serviceinterface;

import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;
import com.leedarson.serviceinterface.listener.OnGetRecordListener;

public interface TcpService extends c {

    public interface INativeTcpSendMsgHandler {
        void onSendMessageFail(String str, int i);

        void onSendMessageSuccess(String str, int i);
    }

    void disConnect();

    void getRecordStream(String str, long j, int i, int i2, int i3, OnGetRecordListener onGetRecordListener);

    void getSeekStream(String str, long j, int i, int i2, int i3, int i4, OnGetRecordListener onGetRecordListener);

    void getSeekStream(String str, long j, int i, int i2, int i3, OnGetRecordListener onGetRecordListener);

    boolean getTcpConnectStatus(String str);

    void handleData(String str, String str2, String str3);

    /* synthetic */ void init(Context context);

    void nativeSendMessageToTarget(String str, String str2, long j, int i, INativeTcpSendMsgHandler iNativeTcpSendMsgHandler);

    void onMessage(String str, String str2);

    void onNativeReceiveTcpMessage(String str, String str2);

    void setActionTcpSend(String str, String str2, String str3, String str4);

    void updateTcpChannelConnectStateBySessionId(String str, int i);

    void updateTcpChannelLoginStateBySessionId(String str, int i);
}

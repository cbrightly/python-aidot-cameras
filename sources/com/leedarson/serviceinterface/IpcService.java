package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import androidx.fragment.app.FragmentActivity;
import com.alibaba.android.arouter.facade.template.c;
import com.leedarson.serviceinterface.event.NetWorkStatusEvent;
import io.reactivex.e;
import kotlin.n;
import org.json.JSONObject;

public interface IpcService extends c {
    void actualFullUpdateByNativeMqtt(JSONObject jSONObject, boolean z, boolean z2);

    void actualUpdatePartialFieldByNativeMqtt(JSONObject jSONObject);

    boolean checkWebRtcChannelIsFromBack(String str);

    void disConnectAllWebRtcChannel();

    void disconnectAll();

    void dismissNoNetTipView();

    JSONObject getIPCDeviceInfoByDeviceId(String str);

    void handleData(Activity activity, String str, String str2, String str3, String str4);

    /* synthetic */ void init(Context context);

    void notifyOfSignalMessageComing(String str, JSONObject jSONObject);

    void onNetWorkChange(NetWorkStatusEvent netWorkStatusEvent);

    void preconnectAll();

    void showNoNetTipView(Activity activity, INoNetSnapTipView iNoNetSnapTipView);

    void showSnap(FragmentActivity fragmentActivity, Uri uri, int i, int i2, int i3, int i4, int i5);

    void unInitTutkIpc();

    e<n<Boolean, String>> wakeUpDeviceById(String str);
}

package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.alibaba.android.arouter.facade.template.c;
import com.leedarson.serviceinterface.listener.OnRequestListener;

public interface MapService extends c {
    void getCountry(Activity activity);

    void getGDPlacePickerData(Intent intent);

    void getLocation(Context context);

    void getLocationInfo(Activity activity, double d, double d2, String str);

    String getMapKey();

    void getPlacePickerData(Intent intent);

    void getRadarMap(String str, OnRequestListener onRequestListener);

    void handleData(String str, Activity activity, String str2, String str3);

    /* synthetic */ void init(Context context);

    void setSupportGoogle(boolean z);
}

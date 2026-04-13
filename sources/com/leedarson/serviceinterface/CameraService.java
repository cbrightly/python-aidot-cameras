package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.alibaba.android.arouter.facade.template.c;

public interface CameraService extends c {
    public static final int REQ_CODE_SCAN_QRCODE = 11;

    void clickBack();

    void clickButton();

    int getCameraPermissionStatus(Activity activity);

    void handleCallBack(String str);

    void handleData(String str, Activity activity, String str2, String str3);

    void handleImageCrop(Intent intent);

    void handleQRCodeData(String str);

    /* synthetic */ void init(Context context);

    void saveNetImage(String str);

    void saveQRCodePic();

    void scanQrcodeForNative(Activity activity);
}

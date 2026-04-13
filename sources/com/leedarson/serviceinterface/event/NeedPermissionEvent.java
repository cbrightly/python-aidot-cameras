package com.leedarson.serviceinterface.event;

import android.app.Activity;
import com.meituan.robust.ChangeQuickRedirect;

public class NeedPermissionEvent {
    public static final int PER_ANDROID_NOTIFICATION = 141;
    public static final int PER_ANDROID_S_BLE = 140;
    public static final int PER_GET_BLE_ABOUT = 9;
    public static final int PER_GET_GPS = 71;
    public static final int PER_GET_LOCATION = 3;
    public static final int PER_GET_LOCATION_BLE = 139;
    public static final int PER_GET_LOCATION_DISCOVER_DEV = 137;
    public static final int PER_IPC_ALBUM_PERM = 130;
    public static final int PER_IPC_GET_SPEAK_PERM = 129;
    public static final int PER_IPC_RECORD_PERM = 128;
    public static final int PER_IPC_SAVE_SCREENSHOT_STORAGE = 126;
    public static final int PER_IPC_SPEAK_PERM = 127;
    public static final int PER_SAVE_NET_IMAGE = 201;
    public static final int PER_SELECT_PHOTO = 2;
    public static final int PER_TAKE_PHOTO = 1;
    public static final int REQUEST_ENABLE_BT = 24;
    public static final int REQUEST_PHOTO_PERM = 124;
    public static final int REQUEST_TAKEPHOTO_PERM = 123;
    public static ChangeQuickRedirect changeQuickRedirect;
    private Activity activity;
    private String callbackKey;
    private int flag;
    public IBluetoothEnableStatueChange mBluetoothOpenHandler;
    private String msg;

    public NeedPermissionEvent(int flag2, String msg2) {
        this.flag = flag2;
        this.msg = msg2;
    }

    public NeedPermissionEvent(int flag2, String msg2, Activity activity2) {
        this.flag = flag2;
        this.msg = msg2;
        this.activity = activity2;
    }

    public NeedPermissionEvent(int flag2, String msg2, String callbackKey2) {
        this.flag = flag2;
        this.msg = msg2;
        this.callbackKey = callbackKey2;
    }

    public int getFlag() {
        return this.flag;
    }

    public void setFlag(int flag2) {
        this.flag = flag2;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg2) {
        this.msg = msg2;
    }

    public String getCallbackKey() {
        return this.callbackKey;
    }

    public void setCallbackKey(String callbackKey2) {
        this.callbackKey = callbackKey2;
    }

    public Activity getActivity() {
        return this.activity;
    }
}

package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;
import org.json.JSONObject;

public interface MatterService extends c {
    public static final String ACTION_ADD_DEVICE = "addDevice";
    public static final String ACTION_ADD_GROUP_MEMBER = "addGroupMember";
    public static final String ACTION_AES_CTR = "aesCTR";
    public static final String ACTION_CONTROL_DEVICE = "controlDevice";
    public static final String ACTION_DISCONNECT_BLE = "disconnectBle";
    public static final String ACTION_GET_FABRICS = "getFabrics";
    public static final String ACTION_PARSE_BLE = "parseBle";
    public static final String ACTION_PARSE_ManualCode = "parseManualCode";
    public static final String ACTION_REMOVE_DEVICE = "removeDevice";
    public static final String ACTION_REMOVE_FABRICS = "removeFabrics";
    public static final String ACTION_REMOVE_GROUP_MEMBER = "removeGroupMember";
    public static final String ACTION_SYNC_DEVICE_STATUS = "syncDeviceProperties";
    public static final String ACTION_getDCLModel = "getDCLModel";
    public static final String ON_DEVICE_ONLINE_CHANGE = "onDeviceOnlineChange";
    public static final String ON_DEVICE_PROPERTIES_CHANGE = "onDevicePropertiesChange";
    public static final String ON_GET_DEV_TYPE = "getDevType";
    public static final String ON_OPEN_PAIRINGWINDOW = "openPairingWindow";

    void handleData(Activity activity, String str, String str2, String str3);

    /* synthetic */ void init(Context context);

    void initMatterSDK(Activity activity);

    void onHouseChange(String str, String str2);

    void openDebug(Activity activity);

    JSONObject parseBle(String str);

    JSONObject parseQrcode(String str);

    void uninitMatterSDK(Activity activity);
}

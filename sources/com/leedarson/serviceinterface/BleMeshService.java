package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;
import com.leedarson.base.http.listener.a;
import java.util.List;
import org.json.JSONObject;

public interface BleMeshService extends c, LDSService {
    @Deprecated
    public static final String ACTION_ADD_DEVICE = "addDevice";
    public static final String ACTION_ADD_DEVICES = "addDevices";
    public static final String ACTION_ADD_GROUP = "addGroup";
    public static final String ACTION_ADD_GROUP_MEMBER = "addGroupMember";
    public static final String ACTION_ADD_SCENE = "addScene";
    public static final String ACTION_ADD_SCENE_ACTION = "addSceneRule";
    public static final String ACTION_CLEAR_MESH_DATA = "clearMeshData";
    public static final String ACTION_CONNECT_MESH_NETWORK = "connectMeshNetwork";
    public static final String ACTION_CONTROL_DEVICE = "controlDevice";
    public static final String ACTION_CONTROL_GROUP = "controlGroup";
    public static final String ACTION_DISCONNECT_MESH_NETWORK = "disconnectMeshNetwork";
    public static final String ACTION_GET_ALARM_STATUS = "getAlarmStatus";
    public static final String ACTION_GET_BATTERY = "getBattery";
    public static final String ACTION_GET_CACHE_BATTERY = "getCacheBattery";
    public static final String ACTION_GET_CACHE_VERSION = "getCacheVersion";
    public static final String ACTION_GET_COLOR_MODE = "getColorMode";
    public static final String ACTION_GET_CURRENT_CUSTOMIZE_EFFECT_MODE = "getCurrentCustomizeEffectMode";
    public static final String ACTION_GET_CURRENT_DETECT_MODE = "getCurrentDetectionMode";
    public static final String ACTION_GET_DETECTION_MODE_PARAMS = "getDetectionMode";
    public static final String ACTION_GET_DEVICES = "getDevices";
    public static final String ACTION_GET_DEVICE_FEATURE = "getDeviceFeature";
    public static final String ACTION_GET_DEVICE_GROUPS = "getDeviceGroups";
    public static final String ACTION_GET_DEVICE_STATUS = "getDeviceStatus";
    public static final String ACTION_GET_EFFECT_LINKAGE = "getEffectLinkage";
    public static final String ACTION_GET_EFFECT_MODE = "getEffectMode";
    public static final String ACTION_GET_ENERGY_CONSUMPTION = "getEnergyConsumption";
    public static final String ACTION_GET_GROUPS = "getGroups";
    public static final String ACTION_GET_ILLUMINATIONSTATE = "getIlluminationState";
    public static final String ACTION_GET_LIGHT_RHYTHM_STATUS = "getLightsRhythmStatus";
    @Deprecated
    public static final String ACTION_GET_MESH_DATA = "getMeshData";
    public static final String ACTION_GET_PIR_CONFIG = "getPIRConfig";
    public static final String ACTION_GET_SCENES = "getScenes";
    public static final String ACTION_GET_SINGLE_CAPPED_ALARM = "getSingleCappedAlarm";
    public static final String ACTION_GET_TEMPORARY_CONTROL_DURATION = "getTemporaryControlDuration";
    public static final String ACTION_GET_TIME = "getTime";
    public static final String ACTION_GET_VERSION = "getVersion";
    public static final String ACTION_OTA_UPGRADE = "OTA";
    public static final String ACTION_PERFORM_CUSTOM_EFFECT = "performCustomizeEffectMode";
    public static final String ACTION_PERFORM_EFFECT = "performEffectMode";
    public static final String ACTION_PERFORM_EFFECT_LINKAGE = "performEffectLinkage";
    public static final String ACTION_REMOVE_DEVICE = "removeDevice";
    public static final String ACTION_REMOVE_DEVICE_FOCUS = "removeDeviceFocus";
    public static final String ACTION_REMOVE_DEVICE_FROM_GROUPS = "removeDeviceFromGroups";
    public static final String ACTION_REMOVE_GROUP = "removeGroup";
    public static final String ACTION_REMOVE_GROUP_MEMBER = "removeGroupMember";
    public static final String ACTION_REMOVE_SCENE = "removeScene";
    public static final String ACTION_REMOVE_SCENE_ACTION = "removeSceneRule";
    public static final String ACTION_REMOVE_SMART_RULE = "removeSmartRule";
    public static final String ACTION_RUN_SCENE = "performScene";
    public static final String ACTION_SET_ALARM_STATUS = "setAlarmStatus";
    public static final String ACTION_SET_CURRENT_DETECT_MODE = "setCurrentDetectionMode";
    public static final String ACTION_SET_DETECTION_MODE_PARAMS = "setDetectionMode";
    public static final String ACTION_SET_DEVICE_FOCUS = "setDeviceFocus";
    public static final String ACTION_SET_DST = "setDST";
    public static final String ACTION_SET_EFFECT_MODE = "setEffectMode";
    public static final String ACTION_SET_PIR_CONFIG = "setPIRConfig";
    public static final String ACTION_SET_SINGLE_CAPPED_ALBUM = "setSingleCappedAlarm";
    public static final String ACTION_SET_SMART_RULE = "setSmartRule";
    public static final String ACTION_SET_SMART_RULE_ENABLE = "setSmartRuleEnable";
    public static final String ACTION_SET_TEMPORARY_CONTROL_DURATION = "setTemporaryControlDuration";
    public static final String ACTION_SET_VOICE_RHYTHM_STOP_ATTR = "setVoiceRhythmStopAttr";
    public static final String ACTION_START_SCAN = "startScan";
    public static final String ACTION_STOP_SCAN = "stopScan";
    public static final String ACTION_TEST_ATTACK = "test_attack";
    public static final String ACTION_TEST_CONNECT_STATE = "test_connect_state";
    public static final String ACTION_TEST_EXPORT = "test_export";
    public static final String ACTION_TEST_IDLE = "test_idle";
    public static final String ACTION_TEST_RESET = "test_reset";
    public static final String ACTION_UPDATE_MESH_DATA = "updateMeshData";
    public static final String ACTION_UPLOAD_MESH_DATA = "uploadMeshData";
    public static final String EVT_ALARM_STATUS_CHANGE_EVENT = "alarmStatusChangeEvent";
    public static final String EVT_GOTO_SLEEP_EVENT = "sleepEvent";
    public static final String EVT_ILLUMINATION_STATE_CHANGE = "illuminationStateChange";
    public static final String EVT_LOW_BATTERY = "lowBatteryEvent";
    public static final String EVT_SECURITY_ALARM = "securityAlarmEvent";
    public static final String EVT_SINGLE_CAPPED_ALARM_EVENT = "singleCappedAlarmEvent";
    public static final String ON_BROAD_CAST = "onBroadcast";
    public static final String ON_DEVICES_ONLINE_CHANGE = "onDeviceOnlineChange";
    public static final String ON_DEVICES_STATUS_CHANGE = "onDeviceStatusChange";
    public static final String ON_EVENT_MESSAGE = "onEventMessage";
    public static final String ON_NETWORK_STATUS_CHANGE = "onNetworkStatusChange";
    public static final String ON_OTA_PROGRESS_CHANGE = "onOTAProgressChange";
    public static final String ON_VOICE_RHYTHM_STATUS_CHANGE = "onVoiceRhythmStatusChange";

    void cancelTimer();

    void checkNeedToAutoConnectProcess();

    void clearDelayRhythmRef();

    void deviceConnect();

    void forceUpdateConfig();

    JSONObject getELKMessageBody(Object obj, String str, String str2, String str3, String str4, String str5);

    List<String> getNodeAddressByGroupId(String str);

    void handleData(Activity activity, String str, String str2, String str3);

    /* synthetic */ void init(Context context);

    void initBleMesh(Activity activity);

    boolean isDelayRhythmRef();

    boolean meshGroupExist(int i);

    void onAlarmStatusChangeEvent(int i, int i2);

    void onDidRender();

    void onGotoSleepEvent(int i, int i2);

    void onHeartBeat(int i, int i2, boolean z);

    void onHouseChange(String str);

    void onIlluminationStateChange(int i, int i2);

    void onLowBatteryEvent(int i, int i2, int i3, int i4, String str);

    void onPermissionRequestGranted(String str, boolean z);

    void onResume();

    void onRetryReportDeviceOnLine();

    void onSecurityTrigger(int i, int i2);

    void onSingleCappedAlarmTrigger(int i, int i2);

    void postDeviceStatusChange(String str, int i, Object obj);

    void reportMeshJsonReport(String str);

    boolean setDelayRhythmRef(String str);

    void setMsgQueueMode(boolean z);

    void setOfflineCheckEnable(String str, boolean z);

    void setRhythmEnable(String str, String str2, byte b, a aVar);

    void setRhythmTheme(String str, String str2, int[] iArr);

    void stopScan(String str);

    void test();

    void transferRhythm(String str, String str2, JSONObject jSONObject);
}

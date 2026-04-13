package meshsdk.util;

import com.leedarson.serviceinterface.BleMeshService;
import meshsdk.model.json.RoutineRule;

public class MeshConstants {
    public static final String AC_STATE_DEV_CONNECTED = "connected";
    public static final String AC_STATE_DEV_CONNECTING = "connecting";
    public static final String AC_STATE_DEV_FOUND = "deviceFound";
    public static final String AC_STATE_IDLE = "idle";
    public static final String AC_STATE_LOGIN_SUCCESS = "online";
    public static final String AC_STATE_SERVICE_FOUND = "serviceFound";
    public static final String AC_STATE_SET_WHITELIST = "setWhiteList";
    public static final String AC_STATE_SET_WHITELIST_FAIL = "setWhiteFail";
    public static final String AC_STATE_SET_WHITELIST_SUCCESS = "setWhiteSuccess";
    public static final String AC_STATE_START_FIND_SERVICE = "startFindService";
    public static final String AC_STATE_START_SCAN = "startScan";
    public static String AUTO_CONNECT = "connect";
    public static String EVENT_ADD_GROUP_MEMBERS = "addGroupMembers";
    public static String EVENT_ADD_MESH_DEVICE = "addMeshDevice";
    public static String EVENT_AUTO_REPAIR_BIND_GROUP = "autoRepairBindGroup";
    public static String EVENT_AUTO_REPAIR_UNBIND_GROUP = "autoRepairUnbindGroup";
    public static String EVENT_DEVICE_CONTROL = "deviceControl";
    public static String EVENT_DEVICE_PRESET = BleMeshService.ACTION_PERFORM_EFFECT;
    public static String EVENT_GROUP_CONTROL = "groupControl";
    public static String EVENT_GROUP_PRESET = "groupPerformEffectMode";
    public static String EVENT_MUSIC = "musicRhythm";
    public static String EVENT_OTA = "OTA";
    public static String EVENT_REMOVE_GROUP_MEMBERS = "removeGroupMembers";
    public static String EVENT_RHYTHM_START = "start";
    public static String EVENT_RHYTHM_STOP = "stop";
    public static String EVENT_SET_CURRENT_DETECTION_MODE = BleMeshService.ACTION_SET_CURRENT_DETECT_MODE;
    public static String EVENT_SET_DETECTION_MODE = BleMeshService.ACTION_SET_DETECTION_MODE_PARAMS;
    public static String EVENT_SET_ENERGY_MODE = "setEnergyMode";
    public static String EVENT_SET_SECURITY_ALARM = "setSecurityAlarm";
    public static String TRACE_ID_ADD_DEVICES = BleMeshService.ACTION_ADD_DEVICES;
    public static String TRACE_ID_AUTO_CONNECT = "meshAutoConnect";
    public static String TRACE_ID_CONTROL = "controlDevice";
    public static String TRACE_ID_EXPORT = "exportConfig";
    public static String TRACE_ID_GROUP = "group";
    public static String TRACE_ID_IMPORT = "importConfig";
    public static String TRACE_ID_LOW_POWER = "lowPower";
    public static String TRACE_ID_MESHJSON = "meshJsonConfig";
    public static String TRACE_ID_OTA = "deviceOTA";
    public static String TRACE_ID_REMOVE_DEVICE = "removeDevice";
    public static String TRACE_ID_SCENE = RoutineRule.THEN_TYPE_SCENE;
    public static String TRACE_ID_SMART = "smart";
}

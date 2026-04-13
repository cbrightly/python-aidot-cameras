package com.tutk.IOTC;

import java.io.PrintStream;

public class IOTCAPIs {
    public static final int API_ER_ANDROID_NULL = -10000;
    public static final int IOTC_ER_ABORTED = -52;
    public static final int IOTC_ER_AES_CERTIFY_FAIL = -29;
    public static final int IOTC_ER_ALREADY_INITIALIZED = -3;
    public static final int IOTC_ER_BLOCKED_CALL = -49;
    public static final int IOTC_ER_CAN_NOT_FIND_DEVICE = -19;
    public static final int IOTC_ER_CH_NOT_ON = -26;
    public static final int IOTC_ER_CLIENT_NOT_SECURE_MODE = -34;
    public static final int IOTC_ER_CLIENT_SECURE_MODE = -35;
    public static final int IOTC_ER_CONNECT_IS_CALLING = -20;
    public static final int IOTC_ER_DEVICE_EXCEED_MAX_SESSION = -48;
    public static final int IOTC_ER_DEVICE_IS_BANNED = -59;
    public static final int IOTC_ER_DEVICE_IS_SLEEP = -64;
    public static final int IOTC_ER_DEVICE_MULTI_LOGIN = -45;
    public static final int IOTC_ER_DEVICE_NOT_LISTENING = -24;
    public static final int IOTC_ER_DEVICE_NOT_SECURE_MODE = -36;
    public static final int IOTC_ER_DEVICE_NOT_USE_KEY_AUTHENTICATION = -69;
    public static final int IOTC_ER_DEVICE_OFFLINE = -90;
    public static final int IOTC_ER_DEVICE_REJECT_BYPORT = -67;
    public static final int IOTC_ER_DEVICE_REJECT_BY_WRONG_AUTH_KEY = -68;
    public static final int IOTC_ER_DEVICE_SECURE_MODE = -37;
    public static final int IOTC_ER_DID_NOT_LOGIN = -70;
    public static final int IOTC_ER_DID_NOT_LOGIN_WITH_AUTHKEY = -71;
    public static final int IOTC_ER_EXCEED_MAX_PACKET_SIZE = -53;
    public static final int IOTC_ER_EXCEED_MAX_SESSION = -18;
    public static final int IOTC_ER_EXIT_LISTEN = -39;
    public static final int IOTC_ER_FAIL_CONNECT_SEARCH = -27;
    public static final int IOTC_ER_FAIL_CREATE_MUTEX = -4;
    public static final int IOTC_ER_FAIL_CREATE_SOCKET = -6;
    public static final int IOTC_ER_FAIL_CREATE_THREAD = -5;
    public static final int IOTC_ER_FAIL_GET_LOCAL_IP = -16;
    public static final int IOTC_ER_FAIL_RESOLVE_HOSTNAME = -2;
    public static final int IOTC_ER_FAIL_SETUP_RELAY = -42;
    public static final int IOTC_ER_FAIL_SOCKET_BIND = -8;
    public static final int IOTC_ER_FAIL_SOCKET_OPT = -7;
    public static final int IOTC_ER_INVALID_ARG = -46;
    public static final int IOTC_ER_INVALID_MODE = -38;
    public static final int IOTC_ER_INVALID_SID = -14;
    public static final int IOTC_ER_LISTEN_ALREADY_CALLED = -17;
    public static final int IOTC_ER_LOGIN_ALREADY_CALLED = -11;
    public static final int IOTC_ER_MASTER_INVALID = -91;
    public static final int IOTC_ER_MASTER_NOT_RESPONSE = -60;
    public static final int IOTC_ER_MASTER_TOO_FEW = -28;
    public static final int IOTC_ER_NETWORK_UNREACHABLE = -41;
    public static final int IOTC_ER_NOT_ENOUGH_MEMORY = -58;
    public static final int IOTC_ER_NOT_INITIALIZED = -12;
    public static final int IOTC_ER_NOT_SUPPORT = -63;
    public static final int IOTC_ER_NOT_SUPPORT_PE = -47;
    public static final int IOTC_ER_NOT_SUPPORT_RELAY = -43;
    public static final int IOTC_ER_NO_PATH_TO_WRITE_DATA = -55;
    public static final int IOTC_ER_NO_PERMISSION = -40;
    public static final int IOTC_ER_NO_SERVER_LIST = -44;
    public static final int IOTC_ER_NoERROR = 0;
    public static final int IOTC_ER_QUEUE_FULL = -62;
    public static final int IOTC_ER_REMOTE_NOT_SUPPORTED = -51;
    public static final int IOTC_ER_REMOTE_TIMEOUT_DISCONNECT = -23;
    public static final int IOTC_ER_RESOURCE_ERROR = -61;
    public static final int IOTC_ER_SERVER_NOT_RESPONSE = -1;
    public static final int IOTC_ER_SERVER_NOT_SUPPORT = -54;
    public static final int IOTC_ER_SERVICE_IS_NOT_STARTED = -56;
    public static final int IOTC_ER_SESSION_CLOSED = -50;
    public static final int IOTC_ER_SESSION_CLOSE_BY_REMOTE = -22;
    public static final int IOTC_ER_SESSION_NO_FREE_CHANNEL = -31;
    public static final int IOTC_ER_STILL_IN_PROCESSING = -57;
    public static final int IOTC_ER_TCP_CONNECT_TO_SERVER_FAILED = -33;
    public static final int IOTC_ER_TCP_NOT_SUPPORT = -65;
    public static final int IOTC_ER_TCP_TRAVEL_FAILED = -32;
    public static final int IOTC_ER_TIMEOUT = -13;
    public static final int IOTC_ER_UNKNOWN_DEVICE = -15;
    public static final int IOTC_ER_UNLICENSE = -10;
    public static final int IOTC_ER_WAKEUP_NOT_INITIALIZED = -66;

    @Deprecated
    public static native int IOTC_Check_Device_On_Line(String str, int i, byte[] bArr, Object obj);

    public static native int IOTC_Connect_ByUID_Parallel(String str, int i);

    public static native void IOTC_Connect_Stop();

    public static native int IOTC_Connect_Stop_BySID(int i);

    public static native int IOTC_DeInitialize();

    public static native int IOTC_DebugTool_Initialize(String str);

    public static native int IOTC_Get_DebugTool_Info(String str, String str2, String[] strArr, int i);

    public static native int IOTC_Get_Login_Info(int[] iArr);

    public static native void IOTC_Get_Login_Info_ByCallBackFn(Object obj);

    public static native int IOTC_Get_Nat_Type();

    public static native int IOTC_Get_SessionID();

    @Deprecated
    public static native void IOTC_Get_Version(int[] iArr);

    public static native String IOTC_Get_Version_String();

    public static native int IOTC_GlobalLock_Lock();

    public static native int IOTC_GlobalLock_Unlock();

    @Deprecated
    public static native int IOTC_Initialize(int i, String str, String str2, String str3, String str4);

    public static native int IOTC_Initialize2(int i);

    public static native int IOTC_IsDeviceSecureMode(int i);

    public static native void IOTC_Listen_Exit();

    public static native int IOTC_ReInitSocket(int i);

    public static native int IOTC_Sessioin_Channel_Check_ON_OFF(int i, int i2);

    public static native int IOTC_Session_Channel_OFF(int i, int i2);

    public static native int IOTC_Session_Channel_ON(int i, int i2);

    @Deprecated
    public static native int IOTC_Session_Check_ByCallBackFn(int i, Object obj);

    public static native int IOTC_Session_Check_Ex(int i, St_SInfoEx st_SInfoEx);

    public static native void IOTC_Session_Close(int i);

    public static native int IOTC_Session_Get_Free_Channel(int i);

    public static native int IOTC_Session_Read(int i, byte[] bArr, int i2, int i3, int i4);

    @Deprecated
    public static native int IOTC_Session_Read_Check_Lost(int i, byte[] bArr, int i2, int i3, int[] iArr, int[] iArr2, int i4);

    public static native int IOTC_Session_Write(int i, byte[] bArr, int i2, int i3);

    public static native void IOTC_Set_Device_Name(String str);

    public static native int IOTC_Set_LanSearchPort(int i);

    public static native int IOTC_Set_Log_Attr(St_LogAttr st_LogAttr);

    @Deprecated
    public static native void IOTC_Set_Log_Path(String str, int i);

    public static native void IOTC_Set_Max_Session_Number(int i);

    public static native int IOTC_Set_Partial_Encryption(int i, int i2);

    public static native void IOTC_Setup_LANConnection_Timeout(int i);

    public static native void IOTC_Setup_P2PConnection_Timeout(int i);

    public static native void IOTC_Setup_Session_Alive_Timeout(int i);

    public static native void IOTC_TCPRelayOnly_TurnOn();

    public static native int IOTC_WakeUp_WakeDevice(String str);

    public void loginInfoCB(int nLonInfo) {
        PrintStream printStream = System.out;
        printStream.println("[parent] LoginInfo Callback, nLogInfo=" + nLonInfo);
    }

    public void sessionStatusCB(int nSID, int nErrCode) {
        PrintStream printStream = System.out;
        printStream.println("[parent] SessionStatus Callback, nSID=" + nSID + ", nErrCode=" + nErrCode);
    }

    public void ConnectModeChangeCB(int nSID, int nConnMode) {
        PrintStream printStream = System.out;
        printStream.println("[parent] ConnectModeChangeCB Callback, nSID = " + nSID + ", nConnMode = " + nConnMode);
    }
}

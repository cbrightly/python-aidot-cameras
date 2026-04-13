package com.tutk.IOTC;

import java.util.ArrayList;

public class AVAPIs {
    public static final int API_ER_ANDROID_NULL = -100000;
    public static final int AV_ER_ALREADY_INITIALIZED = -20031;
    public static final int AV_ER_BUFPARA_MAXSIZE_INSUFF = -20001;
    public static final int AV_ER_CLEANBUF_ALREADY_CALLED = -20029;
    public static final int AV_ER_CLIENT_EXIT = -20018;
    public static final int AV_ER_CLIENT_NOT_SUPPORT = -20020;
    public static final int AV_ER_CLIENT_NO_AVLOGIN = -20008;
    public static final int AV_ER_DASA_CLEAN_BUFFER = -20032;
    public static final int AV_ER_DATA_NOREADY = -20012;
    public static final int AV_ER_DTLS_AUTH_FAIL = -20041;
    public static final int AV_ER_DTLS_WRONG_PWD = -20040;
    public static final int AV_ER_EXCEED_MAX_ALARM = -20005;
    public static final int AV_ER_EXCEED_MAX_CHANNEL = -20002;
    public static final int AV_ER_EXCEED_MAX_SIZE = -20006;
    public static final int AV_ER_FAIL_CREATE_DTLS = -20035;
    public static final int AV_ER_FAIL_CREATE_THREAD = -20004;
    public static final int AV_ER_FAIL_INITIALIZE_DTLS = -20034;
    public static final int AV_ER_INCOMPLETE_FRAME = -20013;
    public static final int AV_ER_INVALID_ARG = -20000;
    public static final int AV_ER_INVALID_SID = -20010;
    public static final int AV_ER_IOTC_CHANNEL_IN_USED = -20027;
    public static final int AV_ER_IOTC_DEINITIALIZED = -20026;
    public static final int AV_ER_IOTC_SESSION_CLOSED = -20025;
    public static final int AV_ER_LOSED_THIS_FRAME = -20014;
    public static final int AV_ER_MEM_INSUFF = -20003;
    public static final int AV_ER_NOT_INITIALIZED = -20019;
    public static final int AV_ER_NOT_SUPPORT = -20033;
    public static final int AV_ER_NO_PERMISSION = -20023;
    public static final int AV_ER_NoERROR = 0;
    public static final int AV_ER_REMOTE_NOT_SUPPORT = -20037;
    public static final int AV_ER_REMOTE_NOT_SUPPORT_DTLS = -20039;
    public static final int AV_ER_REMOTE_TIMEOUT_DISCONNECT = -20016;
    public static final int AV_ER_REQUEST_ALREADY_CALLED = -20036;
    public static final int AV_ER_SDK_NOT_SUPPORT_DTLS = -21334;
    public static final int AV_ER_SENDIOCTRL_ALREADY_CALLED = -20021;
    public static final int AV_ER_SENDIOCTRL_EXIT = -20022;
    public static final int AV_ER_SERVER_EXIT = -20017;
    public static final int AV_ER_SERV_NO_RESPONSE = -20007;
    public static final int AV_ER_SESSION_CLOSE_BY_REMOTE = -20015;
    public static final int AV_ER_SOCKET_QUEUE_FULL = -20030;
    public static final int AV_ER_TIMEOUT = -20011;
    public static final int AV_ER_TOKEN_EXCEED_MAX_SIZE = -20038;
    public static final int AV_ER_WAIT_KEY_FRAME = -20028;
    public static final int AV_ER_WRONG_ACCPWD_LENGTH = -20024;
    public static final int AV_ER_WRONG_VIEWACCorPWD = -20009;
    public static final int IOTYPE_INNER_SND_DATA_DELAY = 255;
    public static final int TIME_DELAY_DELTA = 1;
    public static final int TIME_DELAY_INITIAL = 0;
    public static final int TIME_DELAY_MAX = 500;
    public static final int TIME_DELAY_MIN = 4;
    public static final int TIME_SPAN_LOSED = 1000;

    public interface avAbilityRequestFn {
        void ability_request(int i, String[] strArr);
    }

    public interface avChangePasswordRequestFn {
        int change_password_request(int i, String str, String str2, String str3, String str4);
    }

    public interface avIdentityArrayRequestFn {
        void identity_array_request(int i, ArrayList<St_AvIdentity> arrayList, int[] iArr);
    }

    public interface avJsonCtrlRequestFn {
        int json_request(int i, String str, String str2, String[] strArr);
    }

    public interface avPasswordAuthFn {
        int password_auth(String str, String[] strArr);
    }

    public interface avTokenAuthFn {
        int token_auth(String str, String[] strArr);
    }

    public interface avTokenDeleteFn {
        int token_delete(int i, String str);
    }

    public interface avTokenRequestFn {
        int token_request(int i, String str, String str2, String[] strArr);
    }

    public interface avVSaaSConfigChangedFn {
        void on_config_changed(String str);
    }

    public static native int AV_Set_Log_Attr(St_LogAttr st_LogAttr);

    @Deprecated
    public static native void AV_Set_Log_Path(String str, int i);

    public static native int avCheckAudioBuf(int i);

    public static native int avClientCleanAudioBuf(int i);

    public static native int avClientCleanBuf(int i);

    public static native int avClientCleanLocalBuf(int i);

    public static native int avClientCleanLocalVideoBuf(int i);

    public static native int avClientCleanVideoBuf(int i);

    public static native int avClientCleanVideoBufNB(int i);

    public static native int avClientDeleteIdentity(int i, String str, int i2, int[] iArr, int i3);

    public static native void avClientExit(int i, int i2);

    public static native float avClientRecvBufUsageRate(int i);

    public static native int avClientRequestChangeServerPassword(int i, String str, String str2, String str3, String[] strArr, int[] iArr, int i2);

    public static native int avClientRequestIdentityArray(int i, ArrayList<St_AvIdentity>[] arrayListArr, int[] iArr, int[] iArr2, int i2);

    public static native int avClientRequestServerAbility(int i, String[] strArr, int i2);

    public static native int avClientRequestTokenWithIdentity(int i, String str, String str2, String[] strArr, int[] iArr, int i2);

    @Deprecated
    public static native void avClientSetMaxBufSize(int i);

    public static native int avClientSetRecvBufMaxSize(int i, int i2);

    @Deprecated
    public static native int avClientStart(int i, String str, String str2, int i2, int[] iArr, int i3);

    @Deprecated
    public static native int avClientStart2(int i, String str, String str2, int i2, int[] iArr, int i3, int[] iArr2);

    public static native int avClientStartEx(St_AVClientStartInConfig st_AVClientStartInConfig, St_AVClientStartOutConfig st_AVClientStartOutConfig);

    public static native void avClientStop(int i);

    public static native int avDeInitialize();

    @Deprecated
    public static native int avGetAVApiVer();

    public static native String avGetAVApiVersionString();

    public static native int avInitialize(int i);

    public static native int avRecvAudioData(int i, byte[] bArr, int i2, byte[] bArr2, int i3, int[] iArr);

    public static native int avRecvFrameData2(int i, byte[] bArr, int i2, int[] iArr, int[] iArr2, byte[] bArr2, int i3, int[] iArr3, int[] iArr4);

    public static native int avRecvIOCtrl(int i, int[] iArr, byte[] bArr, int i2, int i3);

    public static native float avResendBufUsageRate(int i);

    public static native int avSendAudioData(int i, byte[] bArr, int i2, byte[] bArr2, int i3);

    public static native int avSendFrameData(int i, byte[] bArr, int i2, byte[] bArr2, int i3);

    public static native int avSendIOCtrl(int i, int i2, byte[] bArr, int i3);

    public static native int avSendIOCtrlExit(int i);

    public static native int avSendJSONCtrlRequest(int i, String str, String[] strArr, int i2);

    public static native void avServExit(int i, int i2);

    public static native int avServResetBuffer(int i, int i2, int i3);

    public static native int avServSetDelayInterval(int i, int i2, int i3);

    public static native void avServSetResendSize(int i, int i2);

    @Deprecated
    public static native int avServStart2(int i, String str, String str2, int i2, int i3, int i4);

    @Deprecated
    public static native int avServStart3(int i, String str, String str2, int i2, int i3, int i4, int[] iArr);

    public static native int avServStartEx(St_AVServStartInConfig st_AVServStartInConfig, St_AVServStartOutConfig st_AVServStartOutConfig);

    public static native void avServStop(int i);

    public static native int avStatusCheck(int i, St_AvStatus st_AvStatus);

    public static int avClientStart(int nSID, byte[] viewAcc, byte[] viewPwd, int timeout_sec, int[] pservType, int ChID) {
        return avClientStart(nSID, new String(viewAcc), new String(viewPwd), timeout_sec, pservType, ChID);
    }
}

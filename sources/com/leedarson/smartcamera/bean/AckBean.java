package com.leedarson.smartcamera.bean;

import com.google.gson.annotations.SerializedName;

public class AckBean {
    public static final int LIVE_SD_MAX_CONNECT_ERROR = -50015;
    public static final int WEBRTC_ERROR_CONNECT_TIMEOUT = -1100;
    public static int WEBRTC_ERROR_EN_RTC_ERR_CODE_CONFIGURATION_NOT_INIT = -50009;
    public static int WEBRTC_ERROR_EN_RTC_ERR_CODE_JSON_FORMAT_ERROR = -50005;
    public static int WEBRTC_ERROR_EN_RTC_ERR_CODE_KVS_SESSION_NOT_FOUND = -50008;
    public static int WEBRTC_ERROR_EN_RTC_ERR_CODE_RTC_SESSION_NOT_FOUND = -50006;
    public static final int WEBRTC_ERROR_EN_RTC_ERR_CODE_SESSION_EXCEED = -50002;
    public static int WEBRTC_ERROR_EN_RTC_ERR_CODE_SESSION_INIT_FAILED = -50004;
    public static int WEBRTC_ERROR_EN_RTC_ERR_CODE_SESSION_MAKE_SIGNAL_ERROR = -50003;
    public static int WEBRTC_ERROR_EN_RTC_ERR_CODE_SESSION_NOT_INIT = -50007;
    public static int WEBRTC_ERROR_INVALID_PARAMS = -50001;
    public static int WEBRTC_RESPONSE_SUCCESS = 200;
    @SerializedName("code")
    public Integer code;
    @SerializedName("desc")
    public String desc;
}

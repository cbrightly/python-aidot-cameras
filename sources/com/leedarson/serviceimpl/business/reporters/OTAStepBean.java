package com.leedarson.serviceimpl.business.reporters;

import com.leedarson.log.tracker.BaseStepBean;

public class OTAStepBean extends BaseStepBean {
    public static final String CHECK_OTA_MD5_STEP = "CHECK_OTA_MD5_STEP";
    public static final int CHECK_OTA_MD5_STEP_FAIL = -400006;
    public static final int GET_WARE_VERSION_FAIL = -400005;
    public static final String GET_WARE_VERSION_STEP = "GET_WARE_VERSION_STEP";
    public static final int ON_RECEIVE_DEVICE_INVALID_DATA = -400009;
    public static final String OPEN_OTA_NOTIFY_STEP = "OPEN_OTA_NOTIFY_STEP";
    public static final int OPEN_OTA_NOTIFY_STEP_FAIL = -400004;
    public static final int OTA_BLE_UN_CONNECT = 403;
    public static final int OTA_CHECK_PARAMS_ERROR = 401;
    public static final int OTA_DOWN_LOAD_FAIL = -400003;
    public static final String OTA_DOWN_LOAD_OTA_STEP = "OTA_DOWN_LOAD_OTA_STEP";
    public static final String OTA_EXCEPTION_STEP = "OTA_EXCEPTION_STEP";
    public static final int OTA_SET_MTU_FAIL_CODE = -400001;
    public static final String OTA_SET_MTU_STEP = "OTA_SET_MTU_STEP";
    public static final String OTA_START_CHECK_PARAMS = "OTA_START_CHECK_PARAMS";
    public static final String OTA_SUCCESS_STEP = "OTA_SUCCESS_STEP";
    public static final int SEND_OTA_DATA_REPEAT_FAIL = -400008;
    public static final String SEND_OTA_PREPARE_CMD_STEP = "SEND_OTA_PREPARE_CMD_STEP";
    public static final int SEND_OTA_PREPARE_CMD_STEP_FAIL = -400007;

    public OTAStepBean(String step, int code) {
        super(step, code);
    }

    public OTAStepBean(String step, int code, long duration) {
        super(step, code, duration);
    }
}

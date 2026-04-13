package com.leedarson.serviceimpl.reporters;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.meituan.robust.ChangeQuickRedirect;
import meshsdk.BaseResp;

/* compiled from: Code */
public enum e {
    CODE_SUCCESS(200, "成功"),
    CODE_SCAN_NOT_FOUND_DEVICE(201, "上线扫描不到设备"),
    CODE_OOB_FAIL(2001, "获取oob失败"),
    CODE_BLE_DISABLE(401, "蓝牙不可用，系统开关未打开"),
    CODE_BLE_NO_PERMISSION(402, "蓝牙不可用，没有蓝牙权限"),
    CODE_SCAN_BLE_DEVICE_TIMEOUT(403, "配网，超时未搜索到设备"),
    CODE_BLE_CONNECT_FAIL(BaseResp.ERR_CONNECT_FAIL, "连接设备超时(ble连接失败)"),
    CODE_BLE_DISCOVER_SERVICE_TIMEOUT(BaseResp.ERR_WAIT_RESPONSE, "获取蓝牙特征超时"),
    CODE_SET_FILTER_FAIL(407, "setFilter指令下发失败"),
    CODE_BLE_DEVICE_NETWORK_UNMATCH(BaseResp.ERR_INVAILD_MODEL_ID, "扫描到设备，但是不匹配networkId"),
    CODE_BLE_SCAN_SYSTEM_FAIL(BaseResp.ERR_DISCONNECT_FAIL, "ble回调扫描失败"),
    CODE_BLE_DISCOVER_SERVICE_FAIL(BaseResp.ERR_UPLOAD_EXPORT_FAIL, "ble发现服务失败"),
    CODE_PROVISION_FAIL(411, AddDeviceStepBean.STEP_PROVISION_FAIL),
    CODE_BIND_GET_COMPOSITION_DATA_FAIL(BaseResp.ERR_DOWNLOAD_MESH_FAIL, AddDeviceStepBean.STEP_BIND_GET_COMPOSITION_DATA_FAIL),
    CODE_BIND_ADD_APPKEY_FAIL(BaseResp.ERR_DOWNLOAD_IMPORT_FAIL, AddDeviceStepBean.STEP_BIND_ADD_APP_KEY_FAIL),
    CODE_BIND_MODELS_FAIL(BaseResp.ERR_OP_FAIL, AddDeviceStepBean.STEP_BIND_MODELS_FAIL),
    CODE_BIND_COMPLETE_FAIL(BaseResp.ERR_PARAM_ERROR, AddDeviceStepBean.STEP_BIND_COMMAND_COMPLETE_FAIL),
    CODE_BIND_TIMEOUT(416, AddDeviceStepBean.STEP_BIND_TIMEOUT),
    CODE_ADD_DEVICE_TIMEOUT(BaseResp.ERR_GROUP_NOT_EMPTY, AddDeviceStepBean.STEP_ADD_MESH_TIMEOUT),
    CODE_BIND_START_FAIL(BaseResp.ERR_NODE_NOT_EXIST, AddDeviceStepBean.STEP_BIND_FAIL_MESHADDRESS_INVALIDA),
    CODE_BIND_SETFILTER_FAIL(BaseResp.ERR_SCENE_NOT_EXIST, "bind setFilter fail"),
    CODE_PRE_ADD_ERROR_CODE_NO_FOUND_DEVICE(7, AddDeviceStepBean.STEP_CACHE_NO_FOUND),
    CODE_FOUND_OOB_FAIL(500, AddDeviceStepBean.STEP_REQUEST_OOB_FAIL),
    CODE_POST_DEVICE_FAIL(TypedValues.PositionType.TYPE_TRANSITION_EASING, AddDeviceStepBean.STEP_POST_DEVICE_FAIL),
    CODE_EMPTY_HOURSE_ID(601, AddDeviceStepBean.STEP_HOUSEID_EMPTY);
    
    public static ChangeQuickRedirect changeQuickRedirect;
    private int code;
    private String desc;

    private e(int code2, String desc2) {
        this.code = code2;
        this.desc = desc2;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}

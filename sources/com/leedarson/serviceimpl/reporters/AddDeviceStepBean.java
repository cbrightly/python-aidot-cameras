package com.leedarson.serviceimpl.reporters;

import com.leedarson.log.tracker.BaseStepBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class AddDeviceStepBean extends BaseStepBean {
    public static final String STEP_ADD_MESH_SUCCESS = "配网成功";
    public static final String STEP_ADD_MESH_TIMEOUT = "配网超时";
    public static final String STEP_BIND_ADD_APP_KEY = "bind add appkey";
    public static final String STEP_BIND_ADD_APP_KEY_FAIL = "bind add appkey fail";
    public static final String STEP_BIND_ADD_APP_KEY_SUCCESS = "bind add appkey success";
    public static final String STEP_BIND_COMMAND_COMPLETE_FAIL = "bind complete fail";
    public static final String STEP_BIND_FAIL_MESHADDRESS_INVALIDA = "bind meshaddress 不合法";
    public static final String STEP_BIND_GET_COMPOSITION_DATA = "bind getCompositionData";
    public static final String STEP_BIND_GET_COMPOSITION_DATA_FAIL = "bind getCompositionData fail";
    public static final String STEP_BIND_GET_COMPOSITION_DATA_SUCCESS = "bind getCompositionData success";
    public static final String STEP_BIND_MODELS_FAIL = "bind model fail";
    public static final String STEP_BIND_SET_FILTER = "bind setFilter";
    public static final String STEP_BIND_SET_FILTER_FAIL = "bind setFilter fail";
    public static final String STEP_BIND_SET_FILTER_SUCCESS = "bind setFilter success";
    public static final String STEP_BIND_START = "bind Start";
    public static final String STEP_BIND_TIMEOUT = "bind Timeout";
    public static final String STEP_CACHE_NO_FOUND = "添加设备时，未从设备扫描缓存列表中找到设备";
    public static final String STEP_DISCONNECT_MAIN_BLE = "断开ble主节点";
    public static final String STEP_HOUSEID_EMPTY = "添加设备时，houseId为空";
    public static final String STEP_PARSE_WEB_PARAM = "解析web入参";
    public static final String STEP_POST_DEVICE = "提交云端";
    public static final String STEP_POST_DEVICE_FAIL = "提交云端失败:%s";
    public static final String STEP_POST_DEVICE_SUCCESS = "提交云端成功";
    public static final String STEP_PROVISION_BEGIN = "provisionBegin(sendProvisioningInvitePDU,设置60s超时时间)";
    public static final String STEP_PROVISION_FAIL = "provisionFail";
    public static final String STEP_PROVISION_INIT = "provisionInit(enableNotifycation, writeCCCForPv, writeCCCForPx)";
    public static final String STEP_PROVISION_SUCCESS = "provisionSuccess(receive 0x100B)";
    public static final String STEP_REQUEST_OOB = "请求oob数据";
    public static final String STEP_REQUEST_OOB_FAIL = "请求oob数据失败:%s";
    public static ChangeQuickRedirect changeQuickRedirect;
    private transient boolean isStepAddAppKeyRetry;
    private transient boolean isStepBleConnected;
    private transient boolean isStepGetCompositionDataRetry;
    private transient boolean isStepPostDevice;
    private transient boolean isStepRequestOob;
    private transient boolean isStepSetFilterRetry;
    private transient boolean isStepStartBleConnect;
    private int rssi;
    private transient int sdkErrorCode;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public AddDeviceStepBean(java.lang.String r3) {
        /*
            r2 = this;
            com.leedarson.serviceimpl.reporters.e r0 = com.leedarson.serviceimpl.reporters.e.CODE_SUCCESS
            int r1 = r0.getCode()
            r2.<init>(r3, r1)
            int r0 = r0.getCode()
            r2.sdkErrorCode = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.reporters.AddDeviceStepBean.<init>(java.lang.String):void");
    }

    public AddDeviceStepBean(String step, int code) {
        super(step, code);
        this.sdkErrorCode = e.CODE_SUCCESS.getCode();
    }

    public void setSdkErrorCode(int code) {
        this.sdkErrorCode = code;
    }

    public int getSdkErrorCode() {
        return this.sdkErrorCode;
    }

    public AddDeviceStepBean(String step, int code, long duration) {
        super(step, code, duration);
        this.sdkErrorCode = e.CODE_SUCCESS.getCode();
    }

    public AddDeviceStepBean putResponseCodeSuccess() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8518, new Class[0], AddDeviceStepBean.class);
        if (proxy.isSupported) {
            return (AddDeviceStepBean) proxy.result;
        }
        putResponseParams("code", Integer.valueOf(e.CODE_SUCCESS.getCode()));
        return this;
    }

    public boolean isStepStartBleConnect() {
        return this.isStepStartBleConnect;
    }

    public AddDeviceStepBean setStepStartBleConnect(boolean stepStartBleConnect) {
        this.isStepStartBleConnect = stepStartBleConnect;
        return this;
    }

    public boolean isStepBleConnected() {
        return this.isStepBleConnected;
    }

    public void setStepBleConnected(boolean stepBleConnected) {
        this.isStepBleConnected = stepBleConnected;
    }

    public boolean isStepPostDevice() {
        return this.isStepPostDevice;
    }

    public boolean isStepRequestOob() {
        return this.isStepRequestOob;
    }

    public boolean isStepAddAppKeyRetry() {
        return this.isStepAddAppKeyRetry;
    }

    public boolean isStepGetCompositionDataRetry() {
        return this.isStepGetCompositionDataRetry;
    }

    public void setStepAddAppKeyRetry(boolean stepAddAppKeyRetry) {
        this.isStepAddAppKeyRetry = stepAddAppKeyRetry;
    }

    public void setStepGetCompositionDataRetry(boolean stepGetCompositionDataRetry) {
        this.isStepGetCompositionDataRetry = stepGetCompositionDataRetry;
    }

    public boolean isStepSetFilterRetry() {
        return this.isStepSetFilterRetry;
    }

    public AddDeviceStepBean setStepSetFilterRetry(boolean stepSetFilterRetry) {
        this.isStepSetFilterRetry = stepSetFilterRetry;
        return this;
    }

    public void setStepPostDevice(boolean stepPostDevice) {
        this.isStepPostDevice = stepPostDevice;
    }

    public void setStepRequestOob(boolean stepRequestOob) {
        this.isStepRequestOob = stepRequestOob;
    }

    public AddDeviceStepBean setRssi(int rssi2) {
        this.rssi = rssi2;
        return this;
    }

    public int getRssi() {
        return this.rssi;
    }
}

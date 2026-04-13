package com.leedarson.serviceimpl.reporters;

import com.leedarson.log.tracker.BaseStepBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class AutoConnectDeviceStepBean extends BaseStepBean {
    public static final String STEP_BIND_SET_FILTER = "bind setFilter";
    public static final String STEP_BIND_SET_FILTER_FAIL = "bind setFilter fail";
    public static final String STEP_CHECK_SEQUENCE_NUMBER_SUCCESS = "checkSequenceNumber ble send success";
    public static final String STEP_CONNECT_MESH_SUCCESS = "连接mesh成功";
    public static final String STEP_CONNECT_MESH_TIMEOUT = "连接mesh网络超时失败";
    public static final String STEP_PROXY_INIT = "proxyInit(enableNotifycation, writeCCCForPx)";
    public static final String STEP_PROXY_INIT_SEND_SUCCESS = "proxyInit ble send success";
    public static ChangeQuickRedirect changeQuickRedirect;
    private transient boolean isStepBleConnected;
    private transient boolean isStepStartBleConnect;
    private String mac;
    private int rssi;

    public AutoConnectDeviceStepBean(String step) {
        super(step, 0);
    }

    public AutoConnectDeviceStepBean(String step, int code) {
        super(step, code);
    }

    public AutoConnectDeviceStepBean(String step, int code, long duration) {
        super(step, code, duration);
    }

    public AutoConnectDeviceStepBean putResponseCodeSuccess() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8526, new Class[0], AutoConnectDeviceStepBean.class);
        if (proxy.isSupported) {
            return (AutoConnectDeviceStepBean) proxy.result;
        }
        putResponseParams("code", Integer.valueOf(e.CODE_SUCCESS.getCode()));
        return this;
    }

    public AutoConnectDeviceStepBean setMac(String mac2) {
        this.mac = mac2;
        return this;
    }

    public String getMac() {
        return this.mac;
    }

    public boolean isStepStartBleConnect() {
        return this.isStepStartBleConnect;
    }

    public AutoConnectDeviceStepBean setStepStartBleConnect(boolean stepStartBleConnect) {
        this.isStepStartBleConnect = stepStartBleConnect;
        return this;
    }

    public AutoConnectDeviceStepBean setStepBleConnected(boolean stepBleConnected) {
        this.isStepBleConnected = stepBleConnected;
        return this;
    }

    public boolean isStepBleConnected() {
        return this.isStepBleConnected;
    }

    public AutoConnectDeviceStepBean setRssi(int rssi2) {
        this.rssi = rssi2;
        return this;
    }

    public int getRssi() {
        return this.rssi;
    }
}

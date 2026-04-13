package com.leedarson.serviceimpl.blec075.reports;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.json.JSONObject;
import timber.log.a;

public class BleConnectStepBean {
    public static final int BLE_CONNECTED = 200;
    public static final int BLE_CONNECT_CONNECT_FAIL = 1004;
    public static final int BLE_CONNECT_DEVICE_UNBIND = 1005;
    public static final int BLE_CONNECT_DISCONNECT = 1003;
    public static final int BLE_CONNECT_FAIL_FOR_DEVICE_BEEN_RESET = 1011;
    public static final int BLE_CONNECT_FAIL_FOR_DEVICE_RESETED_WEB_CODE = 411;
    public static final int BLE_CONNECT_FORCE_TO_SHUTDOWN = 1006;
    public static final int BLE_CONNECT_SEARCH_TIME_OUT = 402;
    public static final int BLE_NOTIFY_REGISTER_ERROR = 1007;
    public static final int BLE_SETUP_MTU_FAIL = 10012;
    public static final int BLE_WRITE_COMMAND_ERROR = 1009;
    public static final int BLE_WRITE_COMMAND_ERROR_BLE_DISCONNECTED = 1010;
    public static final int BLE_WRITE_SIGN_IN_ERROR = 407;
    public static ChangeQuickRedirect changeQuickRedirect;
    public int code = 200;
    public String desc = "";
    public long endTime = 0;
    public JSONObject requestData = new JSONObject();
    public JSONObject responseData = new JSONObject();
    public long startTime = 0;
    public long stepDuration = 0;
    public String stepName = "";

    public static BleConnectStepBean create(String stepName2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{stepName2}, (Object) null, changeQuickRedirect, true, 6551, new Class[]{String.class}, BleConnectStepBean.class);
        if (proxy.isSupported) {
            return (BleConnectStepBean) proxy.result;
        }
        BleConnectStepBean bleConnectStepBean = new BleConnectStepBean();
        bleConnectStepBean.stepName = stepName2;
        bleConnectStepBean.startTime = System.currentTimeMillis();
        return bleConnectStepBean;
    }

    public void putRequestData(String key, String data) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{key, data}, this, changeQuickRedirect, false, 6552, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            try {
                this.requestData.put(key, (Object) data);
            } catch (Exception e) {
                e.printStackTrace();
                a.c("BleConnectStep.put Exception: key=" + key + "   data=" + data, new Object[0]);
            }
        }
    }

    public void putResponseData(String key, String data, int code2) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{key, data, new Integer(code2)}, this, changeQuickRedirect, false, 6553, new Class[]{cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            try {
                if ("desc".equals(key)) {
                    this.desc = data;
                }
                this.responseData.put(key, (Object) data);
                long currentTimeMillis = System.currentTimeMillis();
                this.endTime = currentTimeMillis;
                this.stepDuration = currentTimeMillis - this.startTime;
                if (code2 != -1) {
                    this.code = code2;
                }
            } catch (Exception e) {
                e.printStackTrace();
                a.c("BleConnectStep.putResponseData.put Exception: key=" + key + "   data=" + data, new Object[0]);
            }
        }
    }
}

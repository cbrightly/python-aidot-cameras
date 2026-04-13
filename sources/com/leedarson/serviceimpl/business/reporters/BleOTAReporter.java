package com.leedarson.serviceimpl.business.reporters;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.gson.Gson;
import com.leedarson.bean.Constants;
import com.leedarson.log.elk.a;
import com.leedarson.serviceimpl.business.bean.BleBusinessConnectBean;
import com.leedarson.serviceinterface.BleC075Service;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;

public class BleOTAReporter {
    private static HashMap<String, BleOTAReporter> _reporters = new HashMap<>();
    public static ChangeQuickRedirect changeQuickRedirect;
    private BleBusinessConnectBean _deviceBean;
    private ArrayList<OTAStepBean> _steps = new ArrayList<>();
    public String currentFirmwareVersion = "";
    public String descExtro = "";
    public String deviceErrorCode = "";
    public int numberBleRssi = 0;
    public String targetFrimwareVersion = "";
    public String url = "";

    public BleOTAReporter(BleBusinessConnectBean deviceBean) {
        this._deviceBean = deviceBean;
    }

    public static BleOTAReporter getInstance(BleBusinessConnectBean deviceBean) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceBean}, (Object) null, changeQuickRedirect, true, 7247, new Class[]{BleBusinessConnectBean.class}, BleOTAReporter.class);
        if (proxy.isSupported) {
            return (BleOTAReporter) proxy.result;
        }
        if (!_reporters.containsKey(deviceBean.mac)) {
            _reporters.put(deviceBean.mac, new BleOTAReporter(deviceBean));
        }
        return _reporters.get(deviceBean.mac);
    }

    public void putStep(OTAStepBean step) {
        if (!PatchProxy.proxy(new Object[]{step}, this, changeQuickRedirect, false, 7248, new Class[]{OTAStepBean.class}, Void.TYPE).isSupported) {
            this._steps.add(step);
        }
    }

    public void report() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7249, new Class[0], Void.TYPE).isSupported) {
            try {
                ArrayList<OTAStepBean> arrayList = this._steps;
                if (arrayList == null) {
                    return;
                }
                if (arrayList.size() != 0) {
                    a.y(this).c(BleOTAReporter.class.getSimpleName()).t(Constants.SERVICE_BLUETOOTH_NEW).o("info").e("OTA").u("mac", this._deviceBean.mac).u("modelId", this._deviceBean.modelId).u("url", getUrl()).u("deviceErrorCode", getDeviceErrorCode()).u("currentFirmwareVersion", this.currentFirmwareVersion).u("targetFirmwareVersion", this.targetFrimwareVersion).u("desc", getDesc()).u(TypedValues.TransitionType.S_DURATION, Long.valueOf(getTotalDuration())).u("numberBleRssi", Integer.valueOf(getBleRssi())).u("code", Integer.valueOf(getBleRssi() == 0 ? 403 : getCode())).r(new JSONArray(new Gson().toJson((Object) this._steps))).a().b();
                    this._steps.clear();
                    _reporters.remove(this._deviceBean.mac);
                }
            } catch (Exception e) {
                timber.log.a.c("WebRtcReportV3 11数据上报出现异常" + e.toString(), new Object[0]);
            }
        }
    }

    private String getDeviceErrorCode() {
        return this.deviceErrorCode;
    }

    private String getDesc() {
        return this.descExtro;
    }

    private String getUrl() {
        return this.url;
    }

    private int getCode() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7250, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        for (int i = 0; i < this._steps.size(); i++) {
            if (this._steps.get(i).code < 0) {
                return this._steps.get(i).code;
            }
        }
        return 200;
    }

    private long getTotalDuration() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7251, new Class[0], Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        return calculateStepDuration(this._steps);
    }

    private long calculateStepDuration(ArrayList<OTAStepBean> _tempConfig) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{_tempConfig}, this, changeQuickRedirect, false, 7252, new Class[]{ArrayList.class}, Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        if (_tempConfig.size() == 0) {
            return 0;
        }
        if (_tempConfig.size() == 1) {
            return _tempConfig.get(0).getDuration();
        }
        long maxValue = _tempConfig.get(0)._endTraceTimeSpan;
        for (int i = 0; i < _tempConfig.size(); i++) {
            if (_tempConfig.get(i)._endTraceTimeSpan > maxValue) {
                maxValue = _tempConfig.get(i)._endTraceTimeSpan;
            }
        }
        return maxValue - _tempConfig.get(0)._beginTraceTimeSpan;
    }

    private int getBleRssi() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7253, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        BleC075Service _bleC075 = (BleC075Service) com.alibaba.android.arouter.launcher.a.c().g(BleC075Service.class);
        if (_bleC075 != null) {
            this.numberBleRssi = _bleC075.getConnectedDeviceRssiDetail(this._deviceBean.mac);
        }
        return this.numberBleRssi;
    }
}

package com.leedarson.bean;

import android.bluetooth.BluetoothDevice;
import android.os.Build;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.graphics.ColorUtils;
import com.alibaba.android.arouter.launcher.a;
import com.leedarson.sender.d;
import com.leedarson.sender.e;
import com.leedarson.serviceinterface.BleC075Service;
import com.leedarson.serviceinterface.event.BleDeviceFoundEvent;
import com.leedarson.utils.l;
import com.leedarson.utils.p;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import org.greenrobot.eventbus.c;
import org.json.JSONArray;
import org.json.JSONObject;
import timber.log.a;

public class RhyBLEDevice extends IRhyDevice {
    public static ChangeQuickRedirect changeQuickRedirect;
    private BluetoothDevice bluetoothDevice;
    private int colorIndex;
    private ConcurrentHashMap<String, Integer> effectIndexMap = new ConcurrentHashMap<>();
    private String groupId;
    private long lastColorChangeTime;
    private int[] lastRhythmColors;
    private String modelId = "RhyBleDevice";
    private e sender;
    private BleC075Service service;
    private String wifiMac;

    public RhyBLEDevice(String rhythmType, String protocolType, JSONObject protocolData) {
        super(rhythmType, protocolType, protocolData);
        try {
            if (protocolData.has("mac")) {
                this.wifiMac = protocolData.getString("mac");
                setGroup(false);
            }
            if (protocolData.has("groupId")) {
                this.groupId = protocolData.getString("groupId");
                setGroup(true);
            }
            if (protocolData.has("modelId")) {
                this.modelId = protocolData.optString("modelId", "RhyBleDevice");
            }
            setRhyId(this.groupId + "-" + this.wifiMac);
            if (this.sender == null) {
                this.sender = new d(this.groupId);
            }
            this.service = (BleC075Service) a.c().g(BleC075Service.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1070, new Class[0], Void.TYPE).isSupported) {
            c.c().p(this);
            l.a().c(10000);
        }
    }

    @org.greenrobot.eventbus.l
    public void onBleDeviceFoundEvent(BleDeviceFoundEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 1071, new Class[]{BleDeviceFoundEvent.class}, Void.TYPE).isSupported) {
            try {
                a.b g = timber.log.a.g("Rhythm");
                g.a("onBleDeviceFoundEvent:" + event.bluetoothDevice.getAddress(), new Object[0]);
                BleC075Service bleC075Service = this.service;
                if (bleC075Service != null) {
                    String scanRecord = bleC075Service.toHexAdvertData(event.scanRecord);
                    a.b g2 = timber.log.a.g("Rhythm");
                    g2.a("onBleDeviceFoundEvent scanRecord:" + scanRecord, new Object[0]);
                    if (scanRecord.contains(this.wifiMac.replace(":", "").toLowerCase())) {
                        l.a().d();
                        BluetoothDevice bluetoothDevice2 = event.bluetoothDevice;
                        this.bluetoothDevice = bluetoothDevice2;
                        e eVar = this.sender;
                        if (eVar != null) {
                            ((d) eVar).h(bluetoothDevice2);
                        }
                        a.b g3 = timber.log.a.g("Rhythm");
                        g3.a("onBleDeviceFoundEvent bluetoothDevice:" + this.bluetoothDevice, new Object[0]);
                        BluetoothDevice bluetoothDevice3 = this.bluetoothDevice;
                        if (bluetoothDevice3 != null) {
                            this.service.commonConnect(bluetoothDevice3.getAddress(), this.wifiMac, false, this.bluetoothDevice, (BleC075Service.CommonBleConnectCallback) null, this.modelId, false, "RhyBLEDevice.onBleDeviceFoundEvent");
                        }
                        this.isRunning.set(true);
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public void send() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1072, new Class[0], Void.TYPE).isSupported) {
            try {
                a.b g = timber.log.a.g("Rhythm");
                g.a("send bluetoothDevice：" + this.bluetoothDevice + "，service：" + this.service, new Object[0]);
                e eVar = this.sender;
                if (eVar == null) {
                    return;
                }
                if (this.service != null) {
                    BluetoothDevice senderDevice = ((d) eVar).e();
                    if (senderDevice == null) {
                        senderDevice = this.bluetoothDevice;
                    }
                    if (senderDevice == null) {
                        timber.log.a.g("Rhythm").c("send 蓝牙未连接 - 未扫描到设备", new Object[0]);
                        l.a().c(10000);
                        return;
                    }
                    a.b g2 = timber.log.a.g("Rhythm");
                    g2.a("send senderDevice.getAddress()：" + senderDevice.getAddress() + "，service：" + this.service, new Object[0]);
                    boolean isConnected = this.service.isConnected(senderDevice.getAddress());
                    boolean isConnecting = this.service.isConnecting(senderDevice.getAddress());
                    if (!isConnected && !isConnecting) {
                        timber.log.a.g("Rhythm").a("send 蓝牙未连接 - 未连上设备", new Object[0]);
                        this.service.commonConnect(this.bluetoothDevice.getAddress(), this.wifiMac, false, this.bluetoothDevice, (BleC075Service.CommonBleConnectCallback) null, this.modelId, false, "RhyBLEDevice.send");
                    } else if (IRhyDevice.RHYTHM_TYPE_ASYNC.equals(this.rhythmType)) {
                        asyncRhythmData();
                    } else if (isOldVersion("1.0.0")) {
                        syncRhythmData();
                    } else {
                        syncNewRhythmData();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1073, new Class[0], Void.TYPE).isSupported) {
            c.c().r(this);
            l.a().d();
            BleC075Service bleC075Service = this.service;
            if (!(bleC075Service == null || this.bluetoothDevice == null)) {
                bleC075Service.disConnectTask(this.wifiMac, "");
            }
            this.isRunning.set(false);
        }
    }

    private void syncRhythmData() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1074, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                float[] hsl = new float[3];
                ColorUtils.colorToHSL(getNextColorV1(), hsl);
                jsonObject.put("HSLHue", (int) hsl[0]);
                jsonObject.put("HSLSaturation", (int) (hsl[1] * 100.0f));
                jsonObject.put("HSLLightness", (int) (hsl[2] * 100.0f));
                jsonObject.put(TypedValues.Custom.S_COLOR, getNextColorV1());
                jsonObject.put("Dimming", this.rhythmMAXDimming);
                jsonObject.put("mode", (Object) this.rhythmMode);
                jsonObject.put("soundWaveType", 1);
                jsonObject.put("isOldVersion", true);
                a.b g = timber.log.a.g("Rhythm");
                g.a("send jsonObject:" + jsonObject, new Object[0]);
                this.sender.a(this.rhythmType, jsonObject);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    private void syncNewRhythmData() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1075, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rhythmType", (Object) this.rhythmType);
                jsonObject.put("effects", (Object) getSyncRhythmEffects());
                jsonObject.put("isOldVersion", false);
                a.b g = timber.log.a.g("Rhythm");
                g.a("send jsonObject:" + jsonObject, new Object[0]);
                this.sender.a(this.rhythmType, jsonObject);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    private void asyncRhythmData() {
        double countRate;
        double countRate2;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1076, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rhythmType", (Object) this.rhythmType);
                if (this.rhythmTap == 4) {
                    countRate = 0.5d;
                } else {
                    countRate = Math.random();
                }
                List lights = p.d(countRate, this.rhythmLightIndexes);
                a.b g = timber.log.a.g("Rhythm");
                g.a("LightList :" + lights.size(), new Object[0]);
                JSONArray asyncArray = new JSONArray();
                int i = 0;
                while (i < lights.size()) {
                    JSONArray lightIdArray = new JSONArray();
                    JSONObject asyncObj = new JSONObject();
                    int lightId = ((Integer) lights.get(i)).intValue();
                    lightIdArray.put(lightId);
                    asyncObj.put("lightIds", (Object) lightIdArray);
                    asyncObj.put("effects", (Object) getAsyncRhythmEffectsWithLightId(lightId + ""));
                    asyncArray.put((Object) asyncObj);
                    if (lights.size() > i + 1) {
                        JSONObject asyncObj2 = new JSONObject();
                        JSONArray lightIdArray2 = new JSONArray();
                        countRate2 = countRate;
                        lightIdArray2.put(((Integer) lights.get(i + 1)).intValue());
                        asyncObj2.put("lightIds", (Object) lightIdArray2);
                        asyncObj2.put("effects", (Object) getAsyncRhythmEffectsWithLightId(lightId + ""));
                        asyncArray.put((Object) asyncObj2);
                    } else {
                        countRate2 = countRate;
                    }
                    jsonObject.put("asyncData", (Object) asyncArray);
                    a.b g2 = timber.log.a.g("Rhythm");
                    g2.a("send jsonObject:" + jsonObject, new Object[0]);
                    this.sender.a(this.rhythmType, jsonObject);
                    i += 2;
                    countRate = countRate2;
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    private JSONObject getEffectWithLightId(String lightId) {
        int i = 0;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{lightId}, this, changeQuickRedirect, false, 1077, new Class[]{String.class}, JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        try {
            String effectIndexKey = String.format(Locale.US, "lightId-%s", new Object[]{lightId});
            Integer effectIndexValue = this.effectIndexMap.get(effectIndexKey);
            if (effectIndexValue != null) {
                i = effectIndexValue.intValue();
            }
            int effectIndex = i;
            if (effectIndexValue == null && this.effectIndexMap.size() > 0 && Build.VERSION.SDK_INT >= 24) {
                effectIndex = a.a(this.effectIndexMap.size() * 2, this.effectArray.length());
            }
            if (this.effectArray.length() <= effectIndex) {
                effectIndex = 0;
            }
            JSONObject effect = (JSONObject) this.effectArray.get(effectIndex);
            this.effectIndexMap.put(effectIndexKey, Integer.valueOf(effectIndex + 1));
            return effect;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private int getNextColorV1() {
        int currentColor;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1078, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        long nowTimestamp = System.currentTimeMillis();
        if (Arrays.equals(this.lastRhythmColors, this.rhythmColors)) {
            if (nowTimestamp - this.lastColorChangeTime > ((long) changeColorSpeed())) {
                this.lastColorChangeTime = nowTimestamp;
                int i = this.colorIndex;
                int[] iArr = this.rhythmColors;
                if (i == iArr.length - 1 || i > iArr.length - 1) {
                    this.colorIndex = 0;
                } else {
                    this.colorIndex = i + 1;
                }
            }
            a.b g = timber.log.a.g("Rhythm");
            g.a("same mode, colorIndex=" + this.colorIndex, new Object[0]);
            currentColor = this.rhythmColors[this.colorIndex];
        } else {
            this.lastRhythmColors = this.rhythmColors;
            a.b g2 = timber.log.a.g("Rhythm");
            g2.a("colorIndex=" + this.colorIndex, new Object[0]);
            this.colorIndex = com.leedarson.utils.d.a(this.rhythmColors, this.lastColor, 0);
            a.b g3 = timber.log.a.g("Rhythm");
            g3.a("colorIndex=" + this.colorIndex, new Object[0]);
            currentColor = this.rhythmColors[this.colorIndex];
        }
        this.lastColor = currentColor;
        a.b g4 = timber.log.a.g("Rhythm");
        g4.a(" lastColor=" + this.lastColor + "，colorIndex=" + this.colorIndex, new Object[0]);
        return currentColor;
    }
}

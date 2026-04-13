package com.leedarson.bean;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.graphics.ColorUtils;
import com.alibaba.android.arouter.launcher.a;
import com.google.gson.Gson;
import com.leedarson.sender.b;
import com.leedarson.sender.e;
import com.leedarson.serviceimpl.m;
import com.leedarson.serviceinterface.BleMeshService;
import com.leedarson.utils.d;
import com.leedarson.utils.p;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.model.NodeInfo;
import meshsdk.util.LDSMeshUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

public class RhyBLEMeshDevice extends IRhyDevice {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final int MAX_COUNT = 4;
    private int colorIndex;
    /* access modifiers changed from: private */
    public int count = 0;
    private ConcurrentHashMap<String, Integer> effectIndexMap = new ConcurrentHashMap<>();
    /* access modifiers changed from: private */
    public int enable = 0;
    private EnableTask enableTask = new EnableTask();
    /* access modifiers changed from: private */
    public String groupId;
    /* access modifiers changed from: private */
    public Handler handler;
    /* access modifiers changed from: private */
    public boolean isMeshGroupExist = false;
    private long lastColorChangeTime;
    private long lastCtrlTime = 0;
    protected int[] lastRhythmColors;
    private long lastSendTime;
    /* access modifiers changed from: private */
    public String mac;
    /* access modifiers changed from: private */
    public e sender;

    static /* synthetic */ int access$108(RhyBLEMeshDevice x0) {
        int i = x0.count;
        x0.count = i + 1;
        return i;
    }

    static /* synthetic */ boolean access$600(RhyBLEMeshDevice x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 1094, new Class[]{RhyBLEMeshDevice.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.isSyncPhy();
    }

    public RhyBLEMeshDevice(String rhythmType, String protocolType, JSONObject protocolData) {
        super(rhythmType, protocolType, protocolData);
        HandlerThread thread = new HandlerThread("RhyBLEMeshDevice");
        thread.start();
        this.handler = new Handler(thread.getLooper());
        try {
            if (protocolData.has("mac")) {
                this.mac = protocolData.getString("mac");
                setGroup(false);
            }
            if (protocolData.has("groupId")) {
                this.groupId = protocolData.getString("groupId");
                setGroup(true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setRhyId(this.isGroup ? this.groupId : this.mac);
        if (this.sender == null) {
            this.sender = new b(this.mac, this.groupId, this);
        }
    }

    public void start() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1079, new Class[0], Void.TYPE).isSupported) {
            if (this.sender != null) {
                this.isMeshGroupExist = false;
                this.handler.removeCallbacks(this.enableTask);
                this.count = 0;
                this.enable = 1;
                this.handler.post(this.enableTask);
                if (!isGroup()) {
                    ((BleMeshService) a.c().g(BleMeshService.class)).setOfflineCheckEnable(this.mac, false);
                }
            }
        }
    }

    private boolean isAsyncPhy() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1080, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : IRhyDevice.RHYTHM_TYPE_ASYNC.equals(this.rhythmType);
    }

    private boolean isSyncPhy() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1081, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : IRhyDevice.RHYTHM_TYPE_SYNC.equals(this.rhythmType);
    }

    public boolean isSendCommand(double amplitude) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Double(amplitude)}, this, changeQuickRedirect, false, 1082, new Class[]{Double.TYPE}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (super.isSendCommand(amplitude)) {
            this.lastSendTime = System.currentTimeMillis();
            return true;
        } else if (this.rhythmMicSensitivity < 50) {
            return false;
        } else {
            if (this.lastSendTime == 0) {
                this.lastSendTime = System.currentTimeMillis();
            }
            if (this.lastSendTime == 0 || System.currentTimeMillis() - this.lastSendTime <= 1200) {
                return false;
            }
            MeshLog.logMusicRhythmWarn(" 踩到波峰,不满足条件-手动发:" + this.rhythmMicSensitivity);
            this.lastSendTime = System.currentTimeMillis();
            return true;
        }
    }

    public void send() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1083, new Class[0], Void.TYPE).isSupported) {
            if (this.sender != null) {
                boolean isChange = false;
                int syncColor = 0;
                try {
                    if (isSyncPhy()) {
                        JSONObject jsonObject = getModel2NextColor();
                        isChange = jsonObject.optBoolean("isChange");
                        syncColor = jsonObject.optInt(TypedValues.Custom.S_COLOR);
                    }
                    MeshLog.logMusicRhythm("web下发律动类型:" + this.rhythmType + ",groupid:" + this.groupId + ",mac:" + this.mac);
                    if (!TextUtils.isEmpty(this.groupId)) {
                        List<String> addressList = this.macList;
                        List selectedDevices = p.d(Math.random(), addressList);
                        if (isChange) {
                            selectedDevices = this.macList;
                        }
                        boolean allDevicesSupportAsync = true;
                        for (int a = 0; a < selectedDevices.size(); a++) {
                            if (!SIGMesh.getInstance().isSupportAsyncRhy(selectedDevices.get(a))) {
                                allDevicesSupportAsync = false;
                            }
                        }
                        if (allDevicesSupportAsync) {
                            MeshLog.logMusicRhythm("组id:" + this.groupId + " 下所有设备都支持异步律动");
                            List<String> selectedLightsRhythmV3 = new ArrayList<>();
                            List<String> asyncRhythmMacsV2 = new ArrayList<>();
                            for (int a2 = 0; a2 < selectedDevices.size(); a2++) {
                                String mac2 = selectedDevices.get(a2);
                                NodeInfo nodeInfo = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, mac2);
                                MeshLog.logMusicRhythm("设备:" + nodeInfo.macAddress + " 协议版本:" + nodeInfo.protocolVersion + ",isMeshGroupExist:" + this.isMeshGroupExist);
                                if (nodeInfo.protocolVersion < SIGMesh.NEW_PROTOCOL6 || !this.isMeshGroupExist) {
                                    asyncRhythmMacsV2.add(mac2);
                                } else {
                                    selectedLightsRhythmV3.add(mac2);
                                }
                            }
                            if (selectedLightsRhythmV3.size() > 0) {
                                int supportV3Count = getSupportV3RhythmDeviceCount(addressList);
                                MeshLog.logMusicRhythm("支持异步律动，存在新设备个数:" + selectedLightsRhythmV3.size() + " 走v3组控,组下支持v3的设备总数:" + supportV3Count);
                                rhythmV3(selectedLightsRhythmV3, supportV3Count, syncColor);
                            }
                            if (asyncRhythmMacsV2.size() > 0) {
                                asyncRhythmDataWithQueue(asyncRhythmMacsV2, syncColor);
                            }
                        } else {
                            MeshLog.logMusicRhythm("组id:" + this.groupId + " 下存在设备不支持异步律动，走V1同步律动");
                            syncRhythmData();
                        }
                    } else if (LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, this.mac) == null) {
                    } else {
                        if (SIGMesh.getInstance().isSupportAsyncRhy(this.mac)) {
                            MeshLog.logMusicRhythm("单灯律动:mac:" + this.mac + "支持异步律动,走v2");
                            asyncRhythmData(this.mac, syncColor);
                            return;
                        }
                        MeshLog.logMusicRhythm("mac:" + this.mac + "走同步律动v1");
                        syncRhythmData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int getSupportV3RhythmDeviceCount(List<String> addressList) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{addressList}, this, changeQuickRedirect, false, 1084, new Class[]{List.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int count2 = 0;
        for (int i = 0; i < addressList.size(); i++) {
            if (LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, addressList.get(i)).protocolVersion >= SIGMesh.NEW_PROTOCOL6) {
                count2++;
            }
        }
        return count2;
    }

    public void stop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1085, new Class[0], Void.TYPE).isSupported) {
            MeshLog.logMusicRhythm("stop");
            this.handler.removeCallbacks(this.enableTask);
            this.count = 2;
            this.enable = 0;
            this.lastSendTime = 0;
            this.handler.post(this.enableTask);
            if (!isGroup()) {
                ((BleMeshService) a.c().g(BleMeshService.class)).setOfflineCheckEnable(this.mac, true);
            }
        }
    }

    public final class EnableTask implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public boolean hasSendRhythmTheme = false;

        EnableTask() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1096, new Class[0], Void.TYPE).isSupported) {
                BleMeshService bleMeshService = (BleMeshService) a.c().g(BleMeshService.class);
                if (RhyBLEMeshDevice.this.enable == 1 && bleMeshService.isDelayRhythmRef()) {
                    MeshLog.logMusicRhythmWarn("正在控制，不发enable");
                } else if (RhyBLEMeshDevice.this.count >= 4) {
                    int unused = RhyBLEMeshDevice.this.count = 0;
                    RhyBLEMeshDevice.this.handler.removeCallbacksAndMessages((Object) null);
                } else {
                    RhyBLEMeshDevice rhyBLEMeshDevice = RhyBLEMeshDevice.this;
                    if (rhyBLEMeshDevice.isGroup) {
                        if (bleMeshService.meshGroupExist(Integer.parseInt(rhyBLEMeshDevice.groupId))) {
                            boolean unused2 = RhyBLEMeshDevice.this.isMeshGroupExist = true;
                            MeshLog.logMusicRhythm("mesh组:" + RhyBLEMeshDevice.this.groupId + "存在，通知设备开始音乐律动模式");
                            RhyBLEMeshDevice.this.sender.b(RhyBLEMeshDevice.this.mac, RhyBLEMeshDevice.this.groupId, (byte) RhyBLEMeshDevice.this.enable, new com.leedarson.base.http.listener.a() {
                                public static ChangeQuickRedirect changeQuickRedirect;

                                public void onSuccess(Object obj) {
                                    if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1097, new Class[]{Object.class}, Void.TYPE).isSupported) {
                                        if (RhyBLEMeshDevice.access$600(RhyBLEMeshDevice.this)) {
                                            MeshLog.logMusicRhythm("同步律动不需要下发律动主题颜色");
                                        } else if (EnableTask.this.hasSendRhythmTheme) {
                                            MeshLog.logMusicRhythm("已经调用设置主题了，不需要再调用");
                                        } else {
                                            boolean unused = EnableTask.this.hasSendRhythmTheme = true;
                                            if (RhyBLEMeshDevice.this.enable == 1) {
                                                MeshLog.logMusicRhythm("设置主题颜色 mac:" + RhyBLEMeshDevice.this.mac);
                                                RhyBLEMeshDevice.this.setRhythmTheme();
                                            }
                                        }
                                    }
                                }

                                public void onFail(String str) {
                                    if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 1098, new Class[]{String.class}, Void.TYPE).isSupported) {
                                        if (RhyBLEMeshDevice.this.enable == 1) {
                                            onSuccess("");
                                        }
                                    }
                                }
                            });
                        } else {
                            boolean unused3 = RhyBLEMeshDevice.this.isMeshGroupExist = false;
                            StringBuilder sb = new StringBuilder();
                            sb.append("mesh组:");
                            sb.append(RhyBLEMeshDevice.this.groupId);
                            sb.append("不存在，往");
                            sb.append(RhyBLEMeshDevice.this.macList.size());
                            sb.append("个设备下发");
                            sb.append(RhyBLEMeshDevice.this.enable == 0 ? "关闭" : "开启");
                            sb.append("律动模式");
                            MeshLog.logMusicRhythmWarn(sb.toString());
                            List<String> list = RhyBLEMeshDevice.this.macList;
                            if (list != null && list.size() > 0) {
                                for (String mac : RhyBLEMeshDevice.this.macList) {
                                    RhyBLEMeshDevice.this.sender.b(mac, (String) null, (byte) RhyBLEMeshDevice.this.enable, (com.leedarson.base.http.listener.a) null);
                                    try {
                                        Thread.sleep(300);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        RhyBLEMeshDevice.access$108(RhyBLEMeshDevice.this);
                        RhyBLEMeshDevice.this.handler.postDelayed(this, 1000);
                        return;
                    }
                    rhyBLEMeshDevice.sender.b(RhyBLEMeshDevice.this.mac, RhyBLEMeshDevice.this.groupId, (byte) RhyBLEMeshDevice.this.enable, new com.leedarson.base.http.listener.a() {
                        public static ChangeQuickRedirect changeQuickRedirect;

                        public void onSuccess(Object object) {
                        }

                        public void onFail(String msg) {
                        }
                    });
                }
            }
        }
    }

    private void syncRhythmData() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1086, new Class[0], Void.TYPE).isSupported) {
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
                if (this.isMeshGroupExist) {
                    if (isGroup()) {
                        this.sender.a(this.rhythmType, jsonObject);
                        return;
                    }
                }
                for (String mac2 : this.macList) {
                    jsonObject.put("mac", (Object) mac2);
                    this.sender.a(this.rhythmType, jsonObject);
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    private void rhythmV3(List<String> selectedMacsRhythmV3, int supportV3RhythmDeviceCount, int syncColor) {
        Object[] objArr = {selectedMacsRhythmV3, new Integer(supportV3RhythmDeviceCount), new Integer(syncColor)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1087, new Class[]{List.class, cls, cls}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rhythmType", (Object) this.rhythmType);
                JSONArray array = getAsyncRhythmEffectsWithLightId(this.mac);
                if (isSyncPhy()) {
                    array.getJSONObject(0).put(TypedValues.Custom.S_COLOR, syncColor);
                    array.getJSONObject(1).put(TypedValues.Custom.S_COLOR, syncColor);
                }
                jsonObject.put("effects", (Object) array);
                jsonObject.put("effects", (Object) array);
                if (!TextUtils.isEmpty(this.mac)) {
                    jsonObject.put("mac", (Object) this.mac);
                }
                jsonObject.put("supportV3RhythmDeviceCount", supportV3RhythmDeviceCount);
                jsonObject.put("selectedMacsRhythmV3", (Object) new JSONArray(new Gson().toJson((Object) selectedMacsRhythmV3)));
                this.sender.a(this.rhythmType, jsonObject);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    private void asyncRhythmDataWithQueue(List<String> macs, int syncColor) {
        if (!PatchProxy.proxy(new Object[]{macs, new Integer(syncColor)}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_PRESET_SETPRESET_REQ, new Class[]{List.class, Integer.TYPE}, Void.TYPE).isSupported) {
            MeshLog.logMusicRhythm("律动随机选到 asyncRhythmDataWithQueue size:" + macs.size());
            for (int i = 0; i < macs.size(); i++) {
                if (tooFrequently() || this.lastCtrlTime == 0) {
                    try {
                        if (Looper.myLooper() != Looper.getMainLooper()) {
                            Thread.sleep(80);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                MeshLog.logMusicRhythm("律动随机选到,发送:" + macs.get(i) + " 走v2单控");
                asyncRhythmData(macs.get(i), syncColor);
                this.lastCtrlTime = System.currentTimeMillis();
                if (m.o().u()) {
                    break;
                }
            }
            this.lastCtrlTime = 0;
        }
    }

    private boolean tooFrequently() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_PRESET_SETPRESET_RESP, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return System.currentTimeMillis() - this.lastCtrlTime < 200;
    }

    private void asyncRhythmData(String mac2, int syncColor) {
        if (!PatchProxy.proxy(new Object[]{mac2, new Integer(syncColor)}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_PRESET_GETPRESET_REQ, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rhythmType", (Object) this.rhythmType);
                JSONArray array = getAsyncRhythmEffectsWithLightId(mac2);
                if (isSyncPhy()) {
                    array.getJSONObject(0).put(TypedValues.Custom.S_COLOR, syncColor);
                    array.getJSONObject(1).put(TypedValues.Custom.S_COLOR, syncColor);
                }
                jsonObject.put("effects", (Object) array);
                jsonObject.put("supportAsyncRhythm", true);
                if (!TextUtils.isEmpty(mac2)) {
                    jsonObject.put("mac", (Object) mac2);
                }
                this.sender.a(this.rhythmType, jsonObject);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    private JSONObject getEffectWithLightId(String lightId) {
        int i = 0;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{lightId}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_PRESET_GETPRESET_RESP, new Class[]{String.class}, JSONObject.class);
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
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1092, new Class[0], Integer.TYPE);
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
            this.colorIndex = d.a(this.rhythmColors, this.lastColor, 0);
            a.b g3 = timber.log.a.g("Rhythm");
            g3.a("colorIndex=" + this.colorIndex, new Object[0]);
            currentColor = this.rhythmColors[this.colorIndex];
        }
        this.lastColor = currentColor;
        a.b g4 = timber.log.a.g("Rhythm");
        g4.a(" lastColor=" + this.lastColor + "，colorIndex=" + this.colorIndex, new Object[0]);
        return currentColor;
    }

    public void setRhythmTheme() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1093, new Class[0], Void.TYPE).isSupported) {
            if (this.sender != null) {
                this.handler.post(new Runnable() {
                    public static ChangeQuickRedirect changeQuickRedirect;

                    public void run() {
                        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1095, new Class[0], Void.TYPE).isSupported) {
                            RhyBLEMeshDevice.this.sender.c(RhyBLEMeshDevice.this.mac, RhyBLEMeshDevice.this.groupId, RhyBLEMeshDevice.this.getRhythmColors());
                        }
                    }
                });
            }
        }
    }
}

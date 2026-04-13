package com.leedarson.bean;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.leedarson.sender.e;
import com.leedarson.sender.g;
import com.leedarson.serviceimpl.f;
import com.leedarson.utils.p;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.List;
import org.greenrobot.eventbus.c;
import org.json.JSONArray;
import org.json.JSONObject;
import timber.log.a;

public class RhyUDPDevice extends IRhyDevice {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String autoModePhythmType = IRhyDevice.RHYTHM_TYPE_SYNC;
    private String groupId;
    private long lastCommandTime;
    private e sender;
    private String wifiMac;

    public RhyUDPDevice(String rhythmType, String protocolType, JSONObject protocolData) {
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
            setRhyId(this.isGroup ? this.groupId : this.wifiMac);
            if (this.sender == null) {
                this.sender = new g(this.groupId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
    }

    public void send() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1103, new Class[0], Void.TYPE).isSupported) {
            try {
                if (this.sender == null) {
                    a.b g = a.g("Rhythm");
                    g.a("send sender:" + this.sender, new Object[0]);
                    this.sender = new g(this.groupId);
                }
                if (this.rhythmTap == 6) {
                    int autoFading = f.c().b();
                    if (autoFading < 350) {
                        a.g("Rhythm-Auto").c("切换到异步律动", new Object[0]);
                        this.autoModePhythmType = IRhyDevice.RHYTHM_TYPE_ASYNC;
                    } else if (autoFading > 500) {
                        a.g("Rhythm-Auto").c("切换到同步律动", new Object[0]);
                        this.autoModePhythmType = IRhyDevice.RHYTHM_TYPE_SYNC;
                    }
                    if (IRhyDevice.RHYTHM_TYPE_ASYNC.equals(this.autoModePhythmType)) {
                        asyncRhythmData();
                    } else {
                        syncRhythmDataV2();
                    }
                } else if (IRhyDevice.RHYTHM_TYPE_ASYNC.equals(this.rhythmType)) {
                    asyncRhythmData();
                } else {
                    syncRhythmDataV2();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1104, new Class[0], Void.TYPE).isSupported) {
            c.c().r(this);
            this.isRunning.set(false);
        }
    }

    private void syncRhythmData() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1105, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rhythmType", (Object) this.rhythmType);
                jsonObject.put("effects", (Object) getSyncRhythmEffects());
                a.b g = a.g("Rhythm");
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
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1106, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rhythmType", (Object) this.rhythmType);
                if (this.rhythmTap == 4) {
                    countRate = 0.5d;
                } else {
                    countRate = Math.random();
                }
                List lights = p.d(countRate, this.rhythmLightIndexes);
                a.b g = a.g("Rhythm");
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
                    a.b g2 = a.g("Rhythm");
                    g2.a("send jsonObject:" + jsonObject, new Object[0]);
                    this.sender.a(IRhyDevice.RHYTHM_TYPE_ASYNC, jsonObject);
                    i += 2;
                    countRate = countRate2;
                }
            } catch (Exception e) {
                e.printStackTrace();
                a.b g3 = a.g("Rhythm");
                g3.c("异步 asyncRhythmData exception:" + e.toString(), new Object[0]);
            }
        }
    }

    private void syncRhythmDataV2() {
        double countRate;
        String str;
        String str2 = "lightIds";
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1107, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rhythmType", (Object) this.rhythmType);
                int i = this.rhythmTap;
                if (i == 4) {
                    countRate = 0.5d;
                } else if (i == 5) {
                    long nowTime = System.currentTimeMillis();
                    if (nowTime - this.lastCommandTime >= 1000) {
                        this.lastCommandTime = nowTime;
                        jsonObject.put("rhythmType", (Object) this.rhythmType);
                        jsonObject.put("effects", (Object) getOnlyChangeColorSyncRhythmEffects(1800));
                        a.b g = a.g("Rhythm");
                        g.a("send jsonObject:" + jsonObject, new Object[0]);
                        this.sender.a(this.rhythmType, jsonObject);
                        return;
                    }
                    return;
                } else {
                    countRate = Math.random();
                }
                List lights = p.d(countRate, this.rhythmLightIndexes);
                a.b g2 = a.g("Rhythm");
                g2.a("LightList :" + lights.size(), new Object[0]);
                JSONObject nextColorObj = getModel2NextColor();
                int color = ((Integer) nextColorObj.get(TypedValues.Custom.S_COLOR)).intValue();
                boolean isChange = ((Boolean) nextColorObj.get("isChange")).booleanValue();
                a.b g3 = a.g("Rhythm");
                double d = countRate;
                g3.a("isChange :" + isChange, new Object[0]);
                if (isChange) {
                    jsonObject.put("rhythmType", (Object) this.rhythmType);
                    jsonObject.put("effects", (Object) getModel2SyncRhythmEffects(color));
                    a.b g4 = a.g("Rhythm");
                    g4.a("send jsonObject:" + jsonObject, new Object[0]);
                    if (this.rhythmTap == 6) {
                        this.sender.a(this.autoModePhythmType, jsonObject);
                    } else {
                        this.sender.a(this.rhythmType, jsonObject);
                    }
                } else {
                    JSONArray asyncArray = new JSONArray();
                    int i2 = 0;
                    while (i2 < lights.size()) {
                        JSONArray lightIdArray = new JSONArray();
                        JSONObject asyncObj = new JSONObject();
                        lightIdArray.put(((Integer) lights.get(i2)).intValue());
                        asyncObj.put(str2, (Object) lightIdArray);
                        asyncObj.put("effects", (Object) getModel2SyncRhythmEffects(color));
                        asyncArray.put((Object) asyncObj);
                        JSONArray jSONArray = lightIdArray;
                        if (lights.size() > i2 + 1) {
                            JSONObject asyncObj2 = new JSONObject();
                            JSONArray lightIdArray2 = new JSONArray();
                            JSONObject jSONObject = asyncObj;
                            lightIdArray2.put(((Integer) lights.get(i2 + 1)).intValue());
                            asyncObj2.put(str2, (Object) lightIdArray2);
                            str = str2;
                            asyncObj2.put("effects", (Object) getModel2SyncRhythmEffects(color));
                            asyncArray.put((Object) asyncObj2);
                        } else {
                            str = str2;
                            JSONObject jSONObject2 = asyncObj;
                        }
                        jsonObject.put("asyncData", (Object) asyncArray);
                        a.b g5 = a.g("Rhythm");
                        g5.a("send jsonObject:" + jsonObject, new Object[0]);
                        this.sender.a(IRhyDevice.RHYTHM_TYPE_ASYNC, jsonObject);
                        i2 += 2;
                        str2 = str;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                a.b g6 = a.g("Rhythm");
                g6.c("同步 syncRhythmDataV2 exception:" + e.toString(), new Object[0]);
            }
        }
    }
}

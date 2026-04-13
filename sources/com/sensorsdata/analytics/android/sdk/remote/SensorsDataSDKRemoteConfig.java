package com.sensorsdata.analytics.android.sdk.remote;

import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.encrypt.SecreteKey;
import org.json.JSONArray;
import org.json.JSONObject;

public class SensorsDataSDKRemoteConfig {
    static final int REMOTE_EVENT_TYPE_NO_USE = -1;
    private int autoTrackMode = -1;
    private boolean disableDebugMode = false;
    private boolean disableSDK = false;
    private int effectMode;
    private JSONArray eventBlacklist;
    private int mAutoTrackEventType;
    private SecreteKey mSecretKey;
    private String newVersion;
    private String oldVersion;

    /* access modifiers changed from: package-private */
    public String getOldVersion() {
        return this.oldVersion;
    }

    public void setOldVersion(String oldVersion2) {
        this.oldVersion = oldVersion2;
    }

    /* access modifiers changed from: package-private */
    public boolean isDisableDebugMode() {
        return this.disableDebugMode;
    }

    public void setDisableDebugMode(boolean disableDebugMode2) {
        this.disableDebugMode = disableDebugMode2;
    }

    /* access modifiers changed from: package-private */
    public boolean isDisableSDK() {
        return this.disableSDK;
    }

    public void setDisableSDK(boolean disableSDK2) {
        this.disableSDK = disableSDK2;
    }

    public SecreteKey getSecretKey() {
        return this.mSecretKey;
    }

    public void setSecretKey(SecreteKey mSecretKey2) {
        this.mSecretKey = mSecretKey2;
    }

    /* access modifiers changed from: package-private */
    public int getAutoTrackMode() {
        return this.autoTrackMode;
    }

    public void setAutoTrackMode(int autoTrackMode2) {
        this.autoTrackMode = autoTrackMode2;
        if (autoTrackMode2 == -1 || autoTrackMode2 == 0) {
            this.mAutoTrackEventType = 0;
            return;
        }
        if ((autoTrackMode2 & 1) == 1) {
            this.mAutoTrackEventType |= 1;
        }
        if ((autoTrackMode2 & 2) == 2) {
            this.mAutoTrackEventType |= 2;
        }
        if ((autoTrackMode2 & 4) == 4) {
            this.mAutoTrackEventType |= 4;
        }
        if ((autoTrackMode2 & 8) == 8) {
            this.mAutoTrackEventType |= 8;
        }
    }

    /* access modifiers changed from: package-private */
    public int getAutoTrackEventType() {
        return this.mAutoTrackEventType;
    }

    /* access modifiers changed from: package-private */
    public boolean isAutoTrackEventTypeIgnored(int eventType) {
        int i = this.autoTrackMode;
        if (i == -1) {
            return false;
        }
        if (i == 0) {
            return true;
        }
        int i2 = this.mAutoTrackEventType;
        if ((i2 | eventType) != i2) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("v", (Object) this.oldVersion);
            JSONObject configObject = new JSONObject();
            configObject.put("disableDebugMode", this.disableDebugMode);
            configObject.put("autoTrackMode", this.autoTrackMode);
            configObject.put("disableSDK", this.disableSDK);
            configObject.put("event_blacklist", (Object) this.eventBlacklist);
            configObject.put("nv", (Object) this.newVersion);
            configObject.put("effect_mode", this.effectMode);
            jsonObject.put("configs", (Object) configObject);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        return jsonObject;
    }

    public String toString() {
        return "{ v=" + this.oldVersion + ", disableDebugMode=" + this.disableDebugMode + ", disableSDK=" + this.disableSDK + ", autoTrackMode=" + this.autoTrackMode + ", event_blacklist=" + this.eventBlacklist + ", nv=" + this.newVersion + ", effect_mode=" + this.effectMode + "}";
    }

    public JSONArray getEventBlacklist() {
        return this.eventBlacklist;
    }

    public void setEventBlacklist(JSONArray eventArray) {
        this.eventBlacklist = eventArray;
    }

    public String getNewVersion() {
        return this.newVersion;
    }

    public void setNewVersion(String newVersion2) {
        this.newVersion = newVersion2;
    }

    public void setEffectMode(int effectMode2) {
        this.effectMode = effectMode2;
    }

    public int getEffectMode() {
        return this.effectMode;
    }
}

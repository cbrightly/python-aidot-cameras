package com.sensorsdata.analytics.android.sdk.remote;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Patterns;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SAConfigOptions;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.encrypt.SAEncryptListener;
import com.sensorsdata.analytics.android.sdk.encrypt.SecreteKey;
import com.sensorsdata.analytics.android.sdk.encrypt.SensorsDataEncrypt;
import com.sensorsdata.analytics.android.sdk.network.HttpCallback;
import com.sensorsdata.analytics.android.sdk.network.HttpMethod;
import com.sensorsdata.analytics.android.sdk.network.RequestHelper;
import com.sensorsdata.analytics.android.sdk.util.AppInfoUtils;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public abstract class BaseSensorsDataSDKRemoteManager {
    protected static final String TAG = "SA.SensorsDataSDKRemoteConfigBase";
    protected static SensorsDataSDKRemoteConfig mSDKRemoteConfig;
    protected Context mContext;
    protected boolean mDisableDefaultRemoteConfig;
    protected SAConfigOptions mSAConfigOptions = AbstractSensorsDataAPI.getConfigOptions();
    protected SensorsDataAPI mSensorsDataAPI;
    protected SensorsDataEncrypt mSensorsDataEncrypt;

    public enum RandomTimeType {
        RandomTimeTypeWrite,
        RandomTimeTypeClean,
        RandomTimeTypeNone
    }

    public abstract void applySDKConfigFromCache();

    public abstract void pullSDKConfigFromServer();

    public abstract void requestRemoteConfig(RandomTimeType randomTimeType, boolean z);

    public abstract void resetPullSDKConfigTimer();

    /* access modifiers changed from: protected */
    public abstract void setSDKRemoteConfig(SensorsDataSDKRemoteConfig sensorsDataSDKRemoteConfig);

    protected BaseSensorsDataSDKRemoteManager(SensorsDataAPI sensorsDataAPI) {
        this.mSensorsDataAPI = sensorsDataAPI;
        this.mContext = sensorsDataAPI.getContext();
        this.mSensorsDataEncrypt = sensorsDataAPI.getSensorsDataEncrypt();
        this.mDisableDefaultRemoteConfig = sensorsDataAPI.isDisableDefaultRemoteConfig();
    }

    public boolean ignoreEvent(String eventName) {
        SensorsDataSDKRemoteConfig sensorsDataSDKRemoteConfig = mSDKRemoteConfig;
        if (sensorsDataSDKRemoteConfig == null || sensorsDataSDKRemoteConfig.getEventBlacklist() == null) {
            return false;
        }
        try {
            int size = mSDKRemoteConfig.getEventBlacklist().length();
            for (int i = 0; i < size; i++) {
                if (eventName.equals(mSDKRemoteConfig.getEventBlacklist().get(i))) {
                    SALog.i(TAG, "remote config: " + eventName + " is ignored by remote config");
                    return true;
                }
            }
            return false;
        } catch (JSONException e) {
            SALog.printStackTrace(e);
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public SensorsDataSDKRemoteConfig toSDKRemoteConfig(String config) {
        SensorsDataSDKRemoteConfig sdkRemoteConfig = new SensorsDataSDKRemoteConfig();
        try {
            if (!TextUtils.isEmpty(config)) {
                JSONObject jsonObject = new JSONObject(config);
                sdkRemoteConfig.setOldVersion(jsonObject.optString("v"));
                String configs = jsonObject.optString("configs");
                SecreteKey secreteKey = new SecreteKey("", -1, "", "");
                if (!TextUtils.isEmpty(configs)) {
                    JSONObject configObject = new JSONObject(configs);
                    sdkRemoteConfig.setDisableDebugMode(configObject.optBoolean("disableDebugMode", false));
                    sdkRemoteConfig.setDisableSDK(configObject.optBoolean("disableSDK", false));
                    sdkRemoteConfig.setAutoTrackMode(configObject.optInt("autoTrackMode", -1));
                    sdkRemoteConfig.setEventBlacklist(configObject.optJSONArray("event_blacklist"));
                    sdkRemoteConfig.setNewVersion(configObject.optString("nv", ""));
                    sdkRemoteConfig.setEffectMode(configObject.optInt("effect_mode", 0));
                    if (this.mSAConfigOptions.getEncryptors() != null && !this.mSAConfigOptions.getEncryptors().isEmpty()) {
                        JSONObject keyObject = configObject.optJSONObject("key_v2");
                        if (keyObject != null) {
                            String[] types = keyObject.optString(IjkMediaMeta.IJKM_KEY_TYPE).split("\\+");
                            if (types.length == 2) {
                                String asymmetricType = types[0];
                                String symmetricType = types[1];
                                for (SAEncryptListener encryptListener : this.mSAConfigOptions.getEncryptors()) {
                                    if (asymmetricType.equals(encryptListener.asymmetricEncryptType()) && symmetricType.equals(encryptListener.symmetricEncryptType())) {
                                        secreteKey.key = keyObject.optString("public_key");
                                        secreteKey.version = keyObject.optInt("pkv");
                                        secreteKey.asymmetricEncryptType = asymmetricType;
                                        secreteKey.symmetricEncryptType = symmetricType;
                                    }
                                }
                            }
                        }
                        if (TextUtils.isEmpty(secreteKey.key)) {
                            parseSecreteKey(configObject.optJSONObject(CacheEntity.KEY), secreteKey);
                        }
                        sdkRemoteConfig.setSecretKey(secreteKey);
                    }
                } else {
                    sdkRemoteConfig.setDisableDebugMode(false);
                    sdkRemoteConfig.setDisableSDK(false);
                    sdkRemoteConfig.setAutoTrackMode(-1);
                    sdkRemoteConfig.setSecretKey(secreteKey);
                    sdkRemoteConfig.setEventBlacklist(new JSONArray());
                    sdkRemoteConfig.setNewVersion("");
                    sdkRemoteConfig.setEffectMode(0);
                }
                return sdkRemoteConfig;
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        return sdkRemoteConfig;
    }

    private void parseSecreteKey(JSONObject keyObject, SecreteKey secreteKey) {
        if (keyObject != null) {
            try {
                if (keyObject.has("key_ec") && SensorsDataEncrypt.isECEncrypt()) {
                    String key_ec = keyObject.optString("key_ec");
                    if (!TextUtils.isEmpty(key_ec)) {
                        keyObject = new JSONObject(key_ec);
                    }
                }
                secreteKey.key = keyObject.optString("public_key");
                secreteKey.symmetricEncryptType = "AES";
                if (keyObject.has(IjkMediaMeta.IJKM_KEY_TYPE)) {
                    String type = keyObject.optString(IjkMediaMeta.IJKM_KEY_TYPE);
                    secreteKey.key = type + ":" + secreteKey.key;
                    secreteKey.asymmetricEncryptType = type;
                } else {
                    secreteKey.asymmetricEncryptType = "RSA";
                }
                secreteKey.version = keyObject.optInt("pkv");
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public Boolean isAutoTrackEventTypeIgnored(int autoTrackEventType) {
        SensorsDataSDKRemoteConfig sensorsDataSDKRemoteConfig = mSDKRemoteConfig;
        if (sensorsDataSDKRemoteConfig == null || sensorsDataSDKRemoteConfig.getAutoTrackMode() == -1) {
            return null;
        }
        if (mSDKRemoteConfig.getAutoTrackMode() == 0) {
            return true;
        }
        return Boolean.valueOf(mSDKRemoteConfig.isAutoTrackEventTypeIgnored(autoTrackEventType));
    }

    public static boolean isSDKDisabledByRemote() {
        SensorsDataSDKRemoteConfig sensorsDataSDKRemoteConfig = mSDKRemoteConfig;
        if (sensorsDataSDKRemoteConfig == null) {
            return false;
        }
        return sensorsDataSDKRemoteConfig.isDisableSDK();
    }

    public Boolean isAutoTrackEnabled() {
        SensorsDataSDKRemoteConfig sensorsDataSDKRemoteConfig = mSDKRemoteConfig;
        if (sensorsDataSDKRemoteConfig == null) {
            return null;
        }
        if (sensorsDataSDKRemoteConfig.getAutoTrackMode() == 0) {
            SALog.i(TAG, "remote config: AutoTrackMode is closing by remote config");
            return false;
        } else if (mSDKRemoteConfig.getAutoTrackMode() > 0) {
            return true;
        } else {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public String buildRemoteUrl(boolean enableConfigV) {
        SensorsDataEncrypt sensorsDataEncrypt;
        String remoteUrl = null;
        boolean configV = enableConfigV;
        String serverUlr = this.mSensorsDataAPI.getServerUrl();
        String configOptionsRemoteUrl = null;
        SAConfigOptions sAConfigOptions = this.mSAConfigOptions;
        if (sAConfigOptions != null) {
            configOptionsRemoteUrl = sAConfigOptions.mRemoteConfigUrl;
        }
        if (!TextUtils.isEmpty(configOptionsRemoteUrl) && Patterns.WEB_URL.matcher(configOptionsRemoteUrl).matches()) {
            remoteUrl = configOptionsRemoteUrl;
            SALog.i(TAG, "SAConfigOptions remote url is " + remoteUrl);
        } else if (TextUtils.isEmpty(serverUlr) || !Patterns.WEB_URL.matcher(serverUlr).matches()) {
            SALog.i(TAG, String.format(Locale.CHINA, "ServerUlr: %s, SAConfigOptions remote url: %s", new Object[]{serverUlr, configOptionsRemoteUrl}));
            SALog.i(TAG, "Remote config url verification failed");
            return null;
        } else {
            int pathPrefix = serverUlr.lastIndexOf("/");
            if (pathPrefix != -1) {
                remoteUrl = serverUlr.substring(0, pathPrefix) + "/config/Android.conf";
            }
            SALog.i(TAG, "SensorsDataAPI remote url is " + remoteUrl);
        }
        if (configV && (SensorsDataUtils.checkVersionIsNew(this.mContext, this.mSensorsDataAPI.getSDKVersion()) || ((sensorsDataEncrypt = this.mSensorsDataEncrypt) != null && sensorsDataEncrypt.isPublicSecretKeyNull()))) {
            configV = false;
        }
        Uri configUri = Uri.parse(remoteUrl);
        Uri.Builder builder = configUri.buildUpon();
        if (!TextUtils.isEmpty(remoteUrl) && configV) {
            String oldVersion = null;
            String newVersion = null;
            SensorsDataSDKRemoteConfig SDKRemoteConfig = mSDKRemoteConfig;
            if (SDKRemoteConfig != null) {
                oldVersion = SDKRemoteConfig.getOldVersion();
                newVersion = SDKRemoteConfig.getNewVersion();
                SALog.i(TAG, "The current config: " + SDKRemoteConfig.toString());
            }
            if (!TextUtils.isEmpty(oldVersion) && TextUtils.isEmpty(configUri.getQueryParameter("v"))) {
                builder.appendQueryParameter("v", oldVersion);
            }
            if (!TextUtils.isEmpty(newVersion) && TextUtils.isEmpty(configUri.getQueryParameter("nv"))) {
                builder.appendQueryParameter("nv", newVersion);
            }
        }
        if (!TextUtils.isEmpty(serverUlr) && TextUtils.isEmpty(configUri.getQueryParameter("project"))) {
            String project = Uri.parse(serverUlr).getQueryParameter("project");
            if (!TextUtils.isEmpty(project)) {
                builder.appendQueryParameter("project", project);
            }
        }
        if (TextUtils.isEmpty(configUri.getQueryParameter("app_id"))) {
            builder.appendQueryParameter("app_id", AppInfoUtils.getProcessName(this.mContext));
        }
        builder.build();
        String remoteUrl2 = builder.toString();
        SALog.i(TAG, "Android remote config url is " + remoteUrl2);
        return remoteUrl2;
    }

    /* access modifiers changed from: protected */
    public void requestRemoteConfig(boolean enableConfigV, HttpCallback.StringCallback callback) {
        try {
            String configUrl = buildRemoteUrl(enableConfigV);
            if (!TextUtils.isEmpty(configUrl)) {
                new RequestHelper.Builder(HttpMethod.GET, configUrl).callback(callback).execute();
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }
}

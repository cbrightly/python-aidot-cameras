package com.sensorsdata.analytics.android.sdk;

import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.advert.utils.ChannelUtils;
import com.sensorsdata.analytics.android.sdk.encrypt.IPersistentSecretKey;
import com.sensorsdata.analytics.android.sdk.encrypt.SAEncryptListener;
import com.sensorsdata.analytics.android.sdk.plugin.encrypt.StorePlugin;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLSocketFactory;

public final class SAConfigOptions extends AbstractSAConfigOptions implements Cloneable {
    boolean mInvokeHeatMapEnabled;
    boolean mInvokeLog;
    boolean mInvokeVisualizedEnabled;

    public /* bridge */ /* synthetic */ List getEncryptors() {
        return super.getEncryptors();
    }

    public /* bridge */ /* synthetic */ List getStorePlugins() {
        return super.getStorePlugins();
    }

    public /* bridge */ /* synthetic */ boolean isDataCollectEnable() {
        return super.isDataCollectEnable();
    }

    public /* bridge */ /* synthetic */ boolean isDisableDeviceId() {
        return super.isDisableDeviceId();
    }

    public /* bridge */ /* synthetic */ boolean isDisableSDK() {
        return super.isDisableSDK();
    }

    public /* bridge */ /* synthetic */ boolean isEnableSession() {
        return super.isEnableSession();
    }

    public /* bridge */ /* synthetic */ boolean isEnableTrackPush() {
        return super.isEnableTrackPush();
    }

    public /* bridge */ /* synthetic */ boolean isMultiProcessFlush() {
        return super.isMultiProcessFlush();
    }

    public /* bridge */ /* synthetic */ boolean isSaveDeepLinkInfo() {
        return super.isSaveDeepLinkInfo();
    }

    public /* bridge */ /* synthetic */ boolean isTrackFragmentPageLeave() {
        return super.isTrackFragmentPageLeave();
    }

    public /* bridge */ /* synthetic */ boolean isTrackPageLeave() {
        return super.isTrackPageLeave();
    }

    public /* bridge */ /* synthetic */ boolean isVisualizedPropertiesEnabled() {
        return super.isVisualizedPropertiesEnabled();
    }

    private SAConfigOptions() {
    }

    public SAConfigOptions(String serverUrl) {
        this.mServerUrl = serverUrl;
    }

    public SAConfigOptions setRemoteConfigUrl(String remoteConfigUrl) {
        this.mRemoteConfigUrl = remoteConfigUrl;
        return this;
    }

    public SAConfigOptions setServerUrl(String serverUrl) {
        this.mServerUrl = serverUrl;
        return this;
    }

    public SAConfigOptions setAutoTrackEventType(int autoTrackEventType) {
        this.mAutoTrackEventType = autoTrackEventType;
        return this;
    }

    public SAConfigOptions enableTrackAppCrash() {
        this.mEnableTrackAppCrash = true;
        return this;
    }

    public SAConfigOptions setFlushInterval(int flushInterval) {
        this.mFlushInterval = Math.max(5000, flushInterval);
        return this;
    }

    public SAConfigOptions setFlushBulkSize(int flushBulkSize) {
        this.mFlushBulkSize = Math.max(50, flushBulkSize);
        return this;
    }

    public SAConfigOptions setMaxCacheSize(long maxCacheSize) {
        this.mMaxCacheSize = Math.max(16777216, maxCacheSize);
        return this;
    }

    public SAConfigOptions setMinRequestInterval(int minRequestInterval) {
        if (minRequestInterval > 0) {
            this.mMinRequestInterval = Math.min(minRequestInterval, 168);
        }
        return this;
    }

    public SAConfigOptions setMaxRequestInterval(int maxRequestInterval) {
        if (maxRequestInterval > 0) {
            this.mMaxRequestInterval = Math.min(maxRequestInterval, 168);
        }
        return this;
    }

    public SAConfigOptions disableRandomTimeRequestRemoteConfig() {
        this.mDisableRandomTimeRequestRemoteConfig = true;
        return this;
    }

    public SAConfigOptions disableDebugAssistant() {
        this.mDisableDebugAssistant = true;
        return this;
    }

    public SAConfigOptions enableHeatMap(boolean enableHeatMap) {
        this.mHeatMapEnabled = enableHeatMap;
        this.mInvokeHeatMapEnabled = true;
        return this;
    }

    public SAConfigOptions enableVisualizedProperties(boolean enableVisualizedProperties) {
        this.mVisualizedPropertiesEnabled = enableVisualizedProperties;
        return this;
    }

    public SAConfigOptions enableVisualizedAutoTrack(boolean enableVisualizedAutoTrack) {
        this.mVisualizedEnabled = enableVisualizedAutoTrack;
        this.mInvokeVisualizedEnabled = true;
        return this;
    }

    public SAConfigOptions enableLog(boolean enableLog) {
        this.mLogEnabled = enableLog;
        this.mInvokeLog = true;
        return this;
    }

    public SAConfigOptions enableTrackScreenOrientation(boolean enableScreenOrientation) {
        this.mTrackScreenOrientationEnabled = enableScreenOrientation;
        return this;
    }

    public SAConfigOptions setNetworkTypePolicy(int networkTypePolicy) {
        this.mNetworkTypePolicy = networkTypePolicy;
        return this;
    }

    public SAConfigOptions enableSaveDeepLinkInfo(boolean enableSave) {
        this.mEnableSaveDeepLinkInfo = enableSave;
        return this;
    }

    public SAConfigOptions setSourceChannels(String... channels) {
        ChannelUtils.setSourceChannelKeys(channels);
        return this;
    }

    public SAConfigOptions enableJavaScriptBridge(boolean isSupportJellyBean) {
        this.isAutoTrackWebView = true;
        this.isWebViewSupportJellyBean = isSupportJellyBean;
        return this;
    }

    public SAConfigOptions enableAutoAddChannelCallbackEvent(boolean isAutoAddChannelCallbackEvent) {
        this.isAutoAddChannelCallbackEvent = isAutoAddChannelCallbackEvent;
        return this;
    }

    public SAConfigOptions enableEncrypt(boolean enableEncrypt) {
        this.mEnableEncrypt = enableEncrypt;
        return this;
    }

    public SAConfigOptions persistentSecretKey(IPersistentSecretKey persistentSecretKey) {
        this.mPersistentSecretKey = persistentSecretKey;
        return this;
    }

    public SAConfigOptions enableSubProcessFlushData() {
        this.isSubProcessFlushData = true;
        return this;
    }

    @Deprecated
    public SAConfigOptions disableDataCollect() {
        this.isDataCollectEnable = false;
        return this;
    }

    public boolean isDataCollect() {
        return this.isDataCollectEnable;
    }

    public SAConfigOptions setSSLSocketFactory(SSLSocketFactory SSLSocketFactory) {
        this.mSSLSocketFactory = SSLSocketFactory;
        return this;
    }

    public SAConfigOptions enableTrackPush(boolean enableTrackPush) {
        this.mEnableTrackPush = enableTrackPush;
        return this;
    }

    public SAConfigOptions disableSDK(boolean disableSDK) {
        this.isDisableSDK = disableSDK;
        return this;
    }

    @Deprecated
    public SAConfigOptions enableTrackPageLeave(boolean isTrackPageLeave) {
        return enableTrackPageLeave(isTrackPageLeave, false);
    }

    public SAConfigOptions enableTrackPageLeave(boolean isTrackPageLeave, boolean isTrackFragmentPageLeave) {
        this.mIsTrackPageLeave = isTrackPageLeave;
        this.mIsTrackFragmentPageLeave = isTrackFragmentPageLeave;
        return this;
    }

    public SAConfigOptions ignorePageLeave(List<Class<?>> ignoreList) {
        this.mIgnorePageLeave = ignoreList;
        return this;
    }

    public SAConfigOptions registerEncryptor(SAEncryptListener encryptListener) {
        if (encryptListener != null && !TextUtils.isEmpty(encryptListener.asymmetricEncryptType()) && !TextUtils.isEmpty(encryptListener.symmetricEncryptType()) && !this.mEncryptors.contains(encryptListener)) {
            this.mEncryptors.add(0, encryptListener);
        }
        return this;
    }

    public SAConfigOptions registerStorePlugin(StorePlugin plugin) {
        if (this.mStorePlugins == null) {
            this.mStorePlugins = new ArrayList();
        }
        this.mStorePlugins.add(plugin);
        return this;
    }

    public SAConfigOptions setLoginIDKey(String loginIDKey) {
        this.mLoginIDKey = loginIDKey;
        return this;
    }

    public SAConfigOptions disableDeviceId() {
        this.mDisableDeviceId = true;
        return this;
    }

    public String getLoginIDKey() {
        return this.mLoginIDKey;
    }

    /* access modifiers changed from: protected */
    public SAConfigOptions clone() {
        try {
            return (SAConfigOptions) super.clone();
        } catch (CloneNotSupportedException e) {
            SALog.printStackTrace(e);
            return this;
        }
    }

    public SAConfigOptions enableSession(boolean enableSession) {
        this.mEnableSession = enableSession;
        return this;
    }
}

package com.sensorsdata.analytics.android.sdk;

import com.sensorsdata.analytics.android.sdk.encrypt.IPersistentSecretKey;
import com.sensorsdata.analytics.android.sdk.encrypt.SAEncryptListener;
import com.sensorsdata.analytics.android.sdk.plugin.encrypt.StorePlugin;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLSocketFactory;

public abstract class AbstractSAConfigOptions {
    boolean isAutoAddChannelCallbackEvent;
    boolean isAutoTrackWebView;
    boolean isDataCollectEnable = true;
    boolean isDisableSDK = false;
    boolean isSubProcessFlushData = false;
    boolean isWebViewSupportJellyBean;
    int mAutoTrackEventType;
    public boolean mDisableDebugAssistant;
    boolean mDisableDeviceId = false;
    public boolean mDisableRandomTimeRequestRemoteConfig;
    boolean mEnableEncrypt = false;
    boolean mEnableSaveDeepLinkInfo = false;
    boolean mEnableSession = false;
    boolean mEnableTrackAppCrash;
    public boolean mEnableTrackPush;
    List<SAEncryptListener> mEncryptListeners;
    List<SAEncryptListener> mEncryptors = new ArrayList();
    int mFlushBulkSize;
    int mFlushInterval;
    boolean mHeatMapEnabled;
    List<Class<?>> mIgnorePageLeave;
    protected boolean mIsTrackFragmentPageLeave = false;
    protected boolean mIsTrackPageLeave = false;
    boolean mLogEnabled;
    String mLoginIDKey = "$identity_login_id";
    long mMaxCacheSize = 33554432;
    public int mMaxRequestInterval = 48;
    public int mMinRequestInterval = 24;
    int mNetworkTypePolicy = 30;
    IPersistentSecretKey mPersistentSecretKey;
    public String mRemoteConfigUrl;
    public SSLSocketFactory mSSLSocketFactory;
    String mServerUrl;
    List<StorePlugin> mStorePlugins;
    boolean mTrackScreenOrientationEnabled;
    boolean mVisualizedEnabled;
    boolean mVisualizedPropertiesEnabled;

    AbstractSAConfigOptions() {
    }

    public boolean isDataCollectEnable() {
        return this.isDataCollectEnable;
    }

    public boolean isSaveDeepLinkInfo() {
        return this.mEnableSaveDeepLinkInfo;
    }

    public boolean isMultiProcessFlush() {
        return this.isSubProcessFlushData;
    }

    public boolean isTrackPageLeave() {
        return this.mIsTrackPageLeave;
    }

    public boolean isTrackFragmentPageLeave() {
        return this.mIsTrackPageLeave && this.mIsTrackFragmentPageLeave;
    }

    public List<SAEncryptListener> getEncryptors() {
        return this.mEncryptors;
    }

    public boolean isDisableSDK() {
        return this.isDisableSDK;
    }

    public boolean isEnableSession() {
        return this.mEnableSession;
    }

    public boolean isEnableTrackPush() {
        return this.mEnableTrackPush;
    }

    public boolean isVisualizedPropertiesEnabled() {
        return this.mVisualizedPropertiesEnabled;
    }

    public List<StorePlugin> getStorePlugins() {
        return this.mStorePlugins;
    }

    public boolean isDisableDeviceId() {
        return this.mDisableDeviceId;
    }
}

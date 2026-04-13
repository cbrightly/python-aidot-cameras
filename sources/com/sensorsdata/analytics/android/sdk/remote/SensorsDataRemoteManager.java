package com.sensorsdata.analytics.android.sdk.remote;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.work.WorkRequest;
import com.sensorsdata.analytics.android.sdk.SAConfigOptions;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter;
import com.sensorsdata.analytics.android.sdk.encrypt.SensorsDataEncrypt;
import com.sensorsdata.analytics.android.sdk.network.HttpCallback;
import com.sensorsdata.analytics.android.sdk.plugin.encrypt.SAStoreManager;
import com.sensorsdata.analytics.android.sdk.remote.BaseSensorsDataSDKRemoteManager;
import java.security.SecureRandom;
import org.json.JSONObject;

public class SensorsDataRemoteManager extends BaseSensorsDataSDKRemoteManager {
    private static final String SHARED_PREF_REQUEST_TIME = "sensorsdata.request.time";
    private static final String SHARED_PREF_REQUEST_TIME_RANDOM = "sensorsdata.request.time.random";
    private static final String TAG = "SA.SensorsDataRemoteManager";
    private volatile boolean mIsInit = true;
    private CountDownTimer mPullSDKConfigCountDownTimer;
    private final SAStoreManager mStorageManager = SAStoreManager.getInstance();

    public SensorsDataRemoteManager(SensorsDataAPI sensorsDataAPI) {
        super(sensorsDataAPI);
        SALog.i(TAG, "Construct a SensorsDataRemoteManager");
    }

    private boolean isRequestValid() {
        try {
            long lastRequestTime = this.mStorageManager.getLong(SHARED_PREF_REQUEST_TIME, 0).longValue();
            int randomTime = this.mStorageManager.getInteger(SHARED_PREF_REQUEST_TIME_RANDOM, 0);
            if (lastRequestTime == 0 || randomTime == 0) {
                return true;
            }
            float requestInterval = (float) (SystemClock.elapsedRealtime() - lastRequestTime);
            if (requestInterval <= 0.0f || requestInterval / 1000.0f >= ((float) (randomTime * 3600))) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return true;
        }
    }

    private void writeRemoteRequestRandomTime() {
        SAConfigOptions sAConfigOptions = this.mSAConfigOptions;
        if (sAConfigOptions != null) {
            int randomTime = sAConfigOptions.mMinRequestInterval;
            long currentTime = SystemClock.elapsedRealtime();
            SAConfigOptions sAConfigOptions2 = this.mSAConfigOptions;
            if (sAConfigOptions2.mMaxRequestInterval > sAConfigOptions2.mMinRequestInterval) {
                SecureRandom secureRandom = new SecureRandom();
                SAConfigOptions sAConfigOptions3 = this.mSAConfigOptions;
                randomTime += secureRandom.nextInt((sAConfigOptions3.mMaxRequestInterval - sAConfigOptions3.mMinRequestInterval) + 1);
            }
            this.mStorageManager.setLong(SHARED_PREF_REQUEST_TIME, currentTime);
            this.mStorageManager.setInteger(SHARED_PREF_REQUEST_TIME_RANDOM, randomTime);
        }
    }

    private void cleanRemoteRequestRandomTime() {
        this.mStorageManager.remove(SHARED_PREF_REQUEST_TIME);
        this.mStorageManager.remove(SHARED_PREF_REQUEST_TIME_RANDOM);
    }

    public void pullSDKConfigFromServer() {
        SAConfigOptions sAConfigOptions = this.mSAConfigOptions;
        if (sAConfigOptions != null) {
            if (sAConfigOptions.mDisableRandomTimeRequestRemoteConfig || sAConfigOptions.mMinRequestInterval > sAConfigOptions.mMaxRequestInterval) {
                requestRemoteConfig(BaseSensorsDataSDKRemoteManager.RandomTimeType.RandomTimeTypeClean, true);
                SALog.i(TAG, "remote config: Request remote config because disableRandomTimeRequestRemoteConfig or minHourInterval greater than maxHourInterval");
                return;
            }
            SensorsDataEncrypt sensorsDataEncrypt = this.mSensorsDataEncrypt;
            if (sensorsDataEncrypt != null && sensorsDataEncrypt.isPublicSecretKeyNull()) {
                requestRemoteConfig(BaseSensorsDataSDKRemoteManager.RandomTimeType.RandomTimeTypeWrite, false);
                SALog.i(TAG, "remote config: Request remote config because encrypt key is null");
            } else if (isRequestValid()) {
                requestRemoteConfig(BaseSensorsDataSDKRemoteManager.RandomTimeType.RandomTimeTypeWrite, true);
                SALog.i(TAG, "remote config: Request remote config because satisfy the random request condition");
            }
        }
    }

    public void requestRemoteConfig(BaseSensorsDataSDKRemoteManager.RandomTimeType randomTimeType, boolean enableConfigV) {
        SensorsDataAPI sensorsDataAPI = this.mSensorsDataAPI;
        if (sensorsDataAPI != null && !sensorsDataAPI.isNetworkRequestEnable()) {
            SALog.i(TAG, "Close network request");
        } else if (this.mDisableDefaultRemoteConfig) {
            SALog.i(TAG, "disableDefaultRemoteConfig is true");
        } else {
            switch (AnonymousClass2.$SwitchMap$com$sensorsdata$analytics$android$sdk$remote$BaseSensorsDataSDKRemoteManager$RandomTimeType[randomTimeType.ordinal()]) {
                case 1:
                    writeRemoteRequestRandomTime();
                    break;
                case 2:
                    cleanRemoteRequestRandomTime();
                    break;
            }
            CountDownTimer countDownTimer = this.mPullSDKConfigCountDownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
                this.mPullSDKConfigCountDownTimer = null;
            }
            final boolean z = enableConfigV;
            AnonymousClass1 r1 = new CountDownTimer(90000, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS) {
                public void onTick(long l) {
                    SensorsDataRemoteManager.this.requestRemoteConfig(z, (HttpCallback.StringCallback) new HttpCallback.StringCallback() {
                        public void onFailure(int code, String errorMessage) {
                            if (code == 304 || code == 404) {
                                SensorsDataRemoteManager.this.resetPullSDKConfigTimer();
                            }
                            SALog.i(SensorsDataRemoteManager.TAG, "Remote request failed,responseCode is " + code + ",errorMessage is " + errorMessage);
                        }

                        public void onResponse(String response) {
                            SensorsDataRemoteManager.this.resetPullSDKConfigTimer();
                            if (!TextUtils.isEmpty(response)) {
                                SensorsDataSDKRemoteConfig sdkRemoteConfig = SensorsDataRemoteManager.this.toSDKRemoteConfig(response);
                                try {
                                    SensorsDataEncrypt sensorsDataEncrypt = SensorsDataRemoteManager.this.mSensorsDataEncrypt;
                                    if (sensorsDataEncrypt != null) {
                                        sensorsDataEncrypt.saveSecretKey(sdkRemoteConfig.getSecretKey());
                                    }
                                } catch (Exception e) {
                                    SALog.printStackTrace(e);
                                }
                                SensorsDataRemoteManager.this.setSDKRemoteConfig(sdkRemoteConfig);
                            }
                            SALog.i(SensorsDataRemoteManager.TAG, "Remote request was successful,response data is " + response);
                        }

                        public void onAfter() {
                        }
                    });
                }

                public void onFinish() {
                }
            };
            this.mPullSDKConfigCountDownTimer = r1;
            r1.start();
        }
    }

    /* renamed from: com.sensorsdata.analytics.android.sdk.remote.SensorsDataRemoteManager$2  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$sensorsdata$analytics$android$sdk$remote$BaseSensorsDataSDKRemoteManager$RandomTimeType;

        static {
            int[] iArr = new int[BaseSensorsDataSDKRemoteManager.RandomTimeType.values().length];
            $SwitchMap$com$sensorsdata$analytics$android$sdk$remote$BaseSensorsDataSDKRemoteManager$RandomTimeType = iArr;
            try {
                iArr[BaseSensorsDataSDKRemoteManager.RandomTimeType.RandomTimeTypeWrite.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sensorsdata$analytics$android$sdk$remote$BaseSensorsDataSDKRemoteManager$RandomTimeType[BaseSensorsDataSDKRemoteManager.RandomTimeType.RandomTimeTypeClean.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public void resetPullSDKConfigTimer() {
        try {
            CountDownTimer countDownTimer = this.mPullSDKConfigCountDownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        } catch (Throwable th) {
            this.mPullSDKConfigCountDownTimer = null;
            throw th;
        }
        this.mPullSDKConfigCountDownTimer = null;
    }

    /* access modifiers changed from: protected */
    public void setSDKRemoteConfig(SensorsDataSDKRemoteConfig sdkRemoteConfig) {
        try {
            JSONObject eventProperties = new JSONObject();
            String remoteConfigString = sdkRemoteConfig.toJson().toString();
            eventProperties.put("$app_remote_config", (Object) remoteConfigString);
            SensorsDataAPI.sharedInstance().trackInternal("$AppRemoteConfigChanged", eventProperties);
            SensorsDataAPI.sharedInstance().flush();
            DbAdapter.getInstance().commitRemoteConfig(remoteConfigString);
            SALog.i(TAG, "Save remote data");
            if (1 == sdkRemoteConfig.getEffectMode()) {
                applySDKConfigFromCache();
                SALog.i(TAG, "The remote configuration takes effect immediately");
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void applySDKConfigFromCache() {
        String remoteConfig;
        try {
            if (this.mIsInit) {
                remoteConfig = DbAdapter.getInstance().getRemoteConfigFromLocal();
                this.mIsInit = false;
            } else {
                remoteConfig = DbAdapter.getInstance().getRemoteConfig();
            }
            SensorsDataSDKRemoteConfig sdkRemoteConfig = toSDKRemoteConfig(remoteConfig);
            if (SALog.isLogEnabled()) {
                SALog.i(TAG, "Cache remote config is " + sdkRemoteConfig.toString());
            }
            if (this.mSensorsDataAPI != null) {
                if (sdkRemoteConfig.isDisableDebugMode()) {
                    this.mSensorsDataAPI.setDebugMode(SensorsDataAPI.DebugMode.DEBUG_OFF);
                    SALog.i(TAG, "Set DebugOff Mode");
                }
                if (sdkRemoteConfig.isDisableSDK()) {
                    try {
                        this.mSensorsDataAPI.flush();
                        SALog.i(TAG, "DisableSDK is true");
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                }
            }
            BaseSensorsDataSDKRemoteManager.mSDKRemoteConfig = sdkRemoteConfig;
        } catch (Exception e2) {
            SALog.printStackTrace(e2);
        }
    }
}

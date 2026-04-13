package com.sensorsdata.analytics.android.sdk.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SAConfigOptions;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPIEmptyImplementation;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.ServerUrl;
import com.sensorsdata.analytics.android.sdk.advert.utils.ChannelUtils;
import com.sensorsdata.analytics.android.sdk.dialog.SensorsDataDialogUtils;
import com.sensorsdata.analytics.android.sdk.remote.BaseSensorsDataSDKRemoteManager;
import com.sensorsdata.analytics.android.sdk.remote.SensorsDataRemoteManagerDebug;

public class SASchemeHelper {
    private static final String TAG = "SA.SASchemeUtil";

    public static void handleSchemeUrl(Activity activity, Intent intent) {
        String tip;
        if (AbstractSensorsDataAPI.isSDKDisabled()) {
            SALog.i(TAG, "SDK is disabled,scan code function has been turned off");
        } else if (SensorsDataAPI.sharedInstance() instanceof SensorsDataAPIEmptyImplementation) {
            SALog.i(TAG, "SDK is not init");
        } else {
            Uri uri = null;
            if (!(activity == null || intent == null)) {
                try {
                    uri = intent.getData();
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                    return;
                }
            }
            if (uri != null) {
                SensorsDataAPI sensorsDataAPI = SensorsDataAPI.sharedInstance();
                String host = uri.getHost();
                if ("heatmap".equals(host)) {
                    String featureCode = uri.getQueryParameter("feature_code");
                    String postUrl = uri.getQueryParameter("url");
                    if (checkProjectIsValid(postUrl)) {
                        SensorsDataDialogUtils.showOpenHeatMapDialog(activity, featureCode, postUrl);
                    } else {
                        SensorsDataDialogUtils.showDialog(activity, "App 集成的项目与电脑浏览器打开的项目不同，无法进行点击分析");
                    }
                    intent.setData((Uri) null);
                } else if ("debugmode".equals(host)) {
                    SensorsDataDialogUtils.showDebugModeSelectDialog(activity, uri.getQueryParameter("info_id"), uri.getQueryParameter("sf_push_distinct_id"), uri.getQueryParameter("project"));
                    intent.setData((Uri) null);
                } else if ("visualized".equals(host)) {
                    String featureCode2 = uri.getQueryParameter("feature_code");
                    String postUrl2 = uri.getQueryParameter("url");
                    if (checkProjectIsValid(postUrl2)) {
                        SensorsDataDialogUtils.showOpenVisualizedAutoTrackDialog(activity, featureCode2, postUrl2);
                    } else {
                        SensorsDataDialogUtils.showDialog(activity, "App 集成的项目与电脑浏览器打开的项目不同，无法进行可视化全埋点。");
                    }
                    intent.setData((Uri) null);
                } else if ("popupwindow".equals(host)) {
                    SensorsDataDialogUtils.showPopupWindowDialog(activity, uri);
                    intent.setData((Uri) null);
                } else if ("encrypt".equals(host)) {
                    String version = uri.getQueryParameter("v");
                    String key = Uri.decode(uri.getQueryParameter(CacheEntity.KEY));
                    String symmetricEncryptType = Uri.decode(uri.getQueryParameter("symmetricEncryptType"));
                    String asymmetricEncryptType = Uri.decode(uri.getQueryParameter("asymmetricEncryptType"));
                    SALog.d(TAG, "Encrypt, version = " + version + ", key = " + key + ", symmetricEncryptType = " + symmetricEncryptType + ", asymmetricEncryptType = " + asymmetricEncryptType);
                    if (!TextUtils.isEmpty(version)) {
                        if (!TextUtils.isEmpty(key)) {
                            if (sensorsDataAPI.getSensorsDataEncrypt() != null) {
                                tip = sensorsDataAPI.getSensorsDataEncrypt().checkPublicSecretKey(version, key, symmetricEncryptType, asymmetricEncryptType);
                            } else {
                                tip = "当前 App 未开启加密，请开启加密后再试";
                            }
                            ToastUtil.showLong(activity, tip);
                            SensorsDataDialogUtils.startLaunchActivity(activity);
                            intent.setData((Uri) null);
                        }
                    }
                    tip = "密钥验证不通过，所选密钥无效";
                    ToastUtil.showLong(activity, tip);
                    SensorsDataDialogUtils.startLaunchActivity(activity);
                    intent.setData((Uri) null);
                } else if ("channeldebug".equals(host)) {
                    if (ChannelUtils.hasUtmByMetaData(activity)) {
                        SensorsDataDialogUtils.showDialog(activity, "当前为渠道包，无法使用联调诊断工具");
                        return;
                    }
                    String monitorId = uri.getQueryParameter("monitor_id");
                    if (TextUtils.isEmpty(monitorId)) {
                        SensorsDataDialogUtils.startLaunchActivity(activity);
                        return;
                    }
                    String url = SensorsDataAPI.sharedInstance().getServerUrl();
                    if (TextUtils.isEmpty(url)) {
                        SensorsDataDialogUtils.showDialog(activity, "数据接收地址错误，无法使用联调诊断工具");
                        return;
                    }
                    ServerUrl serverUrl = new ServerUrl(url);
                    if (serverUrl.getProject().equals(uri.getQueryParameter("project_name"))) {
                        String projectId = uri.getQueryParameter("project_id");
                        String accountId = uri.getQueryParameter("account_id");
                        if (!"1".equals(uri.getQueryParameter("is_relink"))) {
                            SensorsDataDialogUtils.showChannelDebugDialog(activity, serverUrl.getBaseUrl(), monitorId, projectId, accountId);
                        } else if (ChannelUtils.checkDeviceInfo(activity, uri.getQueryParameter("device_code"))) {
                            SensorsDataAutoTrackHelper.showChannelDebugActiveDialog(activity);
                        } else {
                            SensorsDataDialogUtils.showDialog(activity, "无法重连，请检查是否更换了联调手机");
                        }
                    } else {
                        SensorsDataDialogUtils.showDialog(activity, "App 集成的项目与电脑浏览器打开的项目不同，无法使用联调诊断工具");
                    }
                    intent.setData((Uri) null);
                } else if ("abtest".equals(host)) {
                    try {
                        ReflectUtil.callStaticMethod(Class.forName("com.sensorsdata.abtest.core.SensorsABTestSchemeHandler"), "handleSchemeUrl", uri.toString());
                    } catch (Exception e2) {
                        SALog.printStackTrace(e2);
                    }
                    SensorsDataDialogUtils.startLaunchActivity(activity);
                    intent.setData((Uri) null);
                } else if ("sensorsdataremoteconfig".equals(host)) {
                    SensorsDataAPI.sharedInstance().enableLog(true);
                    BaseSensorsDataSDKRemoteManager sensorsDataSDKRemoteManager = sensorsDataAPI.getRemoteManager();
                    if (sensorsDataSDKRemoteManager != null) {
                        sensorsDataSDKRemoteManager.resetPullSDKConfigTimer();
                    }
                    SensorsDataRemoteManagerDebug sensorsDataRemoteManagerDebug = new SensorsDataRemoteManagerDebug(sensorsDataAPI);
                    sensorsDataAPI.setRemoteManager(sensorsDataRemoteManagerDebug);
                    SALog.i(TAG, "Start debugging remote config");
                    sensorsDataRemoteManagerDebug.checkRemoteConfig(uri, activity);
                    intent.setData((Uri) null);
                } else if ("assistant".equals(host)) {
                    SAConfigOptions configOptions = AbstractSensorsDataAPI.getConfigOptions();
                    if (configOptions == null || !configOptions.mDisableDebugAssistant) {
                        if ("pairingCode".equals(uri.getQueryParameter(NotificationCompat.CATEGORY_SERVICE))) {
                            SensorsDataDialogUtils.showPairingCodeInputDialog(activity);
                        }
                    }
                } else {
                    SensorsDataDialogUtils.startLaunchActivity(activity);
                }
            }
        }
    }

    private static boolean checkProjectIsValid(String url) {
        Uri serverUri;
        Uri schemeUri;
        String serverUrl = SensorsDataAPI.sharedInstance().getServerUrl();
        String sdkProject = null;
        String serverProject = null;
        if (!TextUtils.isEmpty(url) && (schemeUri = Uri.parse(url)) != null) {
            sdkProject = schemeUri.getQueryParameter("project");
        }
        if (!TextUtils.isEmpty(serverUrl) && (serverUri = Uri.parse(serverUrl)) != null) {
            serverProject = serverUri.getQueryParameter("project");
        }
        return !TextUtils.isEmpty(sdkProject) && !TextUtils.isEmpty(serverProject) && TextUtils.equals(sdkProject, serverProject);
    }
}

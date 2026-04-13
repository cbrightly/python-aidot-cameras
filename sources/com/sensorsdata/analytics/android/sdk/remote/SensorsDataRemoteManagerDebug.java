package com.sensorsdata.analytics.android.sdk.remote;

import android.app.Activity;
import android.content.DialogInterface;
import android.net.Uri;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.ServerUrl;
import com.sensorsdata.analytics.android.sdk.dialog.SensorsDataDialogUtils;
import com.sensorsdata.analytics.android.sdk.dialog.SensorsDataLoadingDialog;
import com.sensorsdata.analytics.android.sdk.network.HttpCallback;
import com.sensorsdata.analytics.android.sdk.remote.BaseSensorsDataSDKRemoteManager;
import com.sensorsdata.analytics.android.sdk.util.AppInfoUtils;
import com.sensorsdata.analytics.android.sdk.util.NetworkUtils;
import org.json.JSONObject;

public class SensorsDataRemoteManagerDebug extends BaseSensorsDataSDKRemoteManager {
    private static final String TAG = "SA.SensorsDataRemoteManagerDebug";
    private String errorMsg = "";

    public SensorsDataRemoteManagerDebug(SensorsDataAPI sensorsDataAPI) {
        super(sensorsDataAPI);
        SALog.i(TAG, "remote config: Construct a SensorsDataRemoteManagerDebug");
    }

    public void pullSDKConfigFromServer() {
        SALog.i(TAG, "remote config: Running pullSDKConfigFromServer");
    }

    public void requestRemoteConfig(BaseSensorsDataSDKRemoteManager.RandomTimeType randomTimeType, boolean enableConfigV) {
        SALog.i(TAG, "remote config: Running requestRemoteConfig");
    }

    public void resetPullSDKConfigTimer() {
        SALog.i(TAG, "remote config: Running resetPullSDKConfigTimer");
    }

    public void applySDKConfigFromCache() {
        SALog.i(TAG, "remote config: Running applySDKConfigFromCache");
    }

    public void setSDKRemoteConfig(SensorsDataSDKRemoteConfig sdkRemoteConfig) {
        try {
            JSONObject eventProperties = new JSONObject();
            eventProperties.put("$app_remote_config", (Object) sdkRemoteConfig.toJson().put("debug", true).toString());
            SensorsDataAPI.sharedInstance().trackInternal("$AppRemoteConfigChanged", eventProperties);
            SensorsDataAPI.sharedInstance().flush();
            BaseSensorsDataSDKRemoteManager.mSDKRemoteConfig = sdkRemoteConfig;
            SALog.i(TAG, "remote config: The remote configuration takes effect immediately");
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void checkRemoteConfig(final Uri uri, final Activity activity) {
        if (verifyRemoteRequestParameter(uri, activity)) {
            SensorsDataDialogUtils.showDialog(activity, "提示", "开始获取采集控制信息", "继续", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    final SensorsDataLoadingDialog loadingDialog = new SensorsDataLoadingDialog(activity);
                    SensorsDataDialogUtils.dialogShowDismissOld(loadingDialog);
                    SensorsDataRemoteManagerDebug.this.requestRemoteConfig(false, (HttpCallback.StringCallback) new HttpCallback.StringCallback() {
                        public void onFailure(int code, String errorMessage) {
                            loadingDialog.dismiss();
                            SensorsDataDialogUtils.showDialog(activity, "远程配置获取失败，请稍后重新扫描二维码");
                            SALog.i(SensorsDataRemoteManagerDebug.TAG, "remote config: Remote request was failed,code is " + code + ",errorMessage is" + errorMessage);
                        }

                        public void onResponse(String response) {
                            loadingDialog.dismiss();
                            if (!TextUtils.isEmpty(response)) {
                                SensorsDataSDKRemoteConfig sdkRemoteConfig = SensorsDataRemoteManagerDebug.this.toSDKRemoteConfig(response);
                                String nv = uri.getQueryParameter("nv");
                                if (!sdkRemoteConfig.getNewVersion().equals(nv)) {
                                    Activity activity = activity;
                                    SensorsDataDialogUtils.showDialog(activity, "信息版本不一致", "获取到采集控制信息的版本：" + sdkRemoteConfig.getNewVersion() + "，二维码信息的版本：" + nv + "，请稍后重新扫描二维码", "确认", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            SensorsDataDialogUtils.startLaunchActivity(activity);
                                        }
                                    }, (String) null, (DialogInterface.OnClickListener) null);
                                } else {
                                    SensorsDataDialogUtils.showDialog(activity, "采集控制加载完成，可以通过 Android Studio 控制台日志来调试");
                                    SensorsDataRemoteManagerDebug.this.setSDKRemoteConfig(sdkRemoteConfig);
                                }
                            } else {
                                SensorsDataDialogUtils.showDialog(activity, "远程配置获取失败，请稍后再试");
                            }
                            SALog.i(SensorsDataRemoteManagerDebug.TAG, "remote config: Remote request was successful,response data is " + response);
                        }

                        public void onAfter() {
                        }
                    });
                }
            }, "取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SensorsDataDialogUtils.startLaunchActivity(activity);
                }
            });
            return;
        }
        SensorsDataDialogUtils.showDialog(activity, this.errorMsg);
    }

    private boolean verifyRemoteRequestParameter(Uri uri, Activity activity) {
        boolean isVerify = false;
        String appId = uri.getQueryParameter("app_id");
        String os = uri.getQueryParameter("os");
        String project = uri.getQueryParameter("project");
        String nv = uri.getQueryParameter("nv");
        String localProject = "";
        String serverUrl = this.mSensorsDataAPI.getServerUrl();
        if (!TextUtils.isEmpty(serverUrl)) {
            localProject = new ServerUrl(serverUrl).getProject();
        }
        SALog.i(TAG, "remote config: ServerUrl is " + serverUrl);
        if (!NetworkUtils.isNetworkAvailable(this.mContext)) {
            this.errorMsg = "网络连接失败，请检查设备网络，确认网络畅通后，请重新扫描二维码进行调试";
        } else {
            SensorsDataAPI sensorsDataAPI = this.mSensorsDataAPI;
            if (sensorsDataAPI != null && !sensorsDataAPI.isNetworkRequestEnable()) {
                this.errorMsg = "SDK 网络权限已关闭，请允许 SDK 访问网络";
                SALog.i(TAG, "enableNetworkRequest is false");
            } else if (this.mDisableDefaultRemoteConfig) {
                this.errorMsg = "采集控制网络权限已关闭，请允许采集控制访问网络";
                SALog.i(TAG, "disableDefaultRemoteConfig is true");
            } else if (!localProject.equals(project)) {
                this.errorMsg = "App 集成的项目与二维码对应的项目不同，无法进行调试";
            } else if (!"Android".equals(os)) {
                this.errorMsg = "App 与二维码对应的操作系统不同，无法进行调试";
            } else if (!AppInfoUtils.getProcessName(activity).equals(appId)) {
                this.errorMsg = "当前 App 与二维码对应的 App 不同，无法进行调试";
            } else if (TextUtils.isEmpty(nv)) {
                this.errorMsg = "二维码信息校验失败，请检查采集控制是否配置正确";
            } else {
                isVerify = true;
            }
        }
        SALog.i(TAG, "remote config: Uri is " + uri.toString());
        SALog.i(TAG, "remote config: The verification result is " + isVerify);
        return isVerify;
    }
}

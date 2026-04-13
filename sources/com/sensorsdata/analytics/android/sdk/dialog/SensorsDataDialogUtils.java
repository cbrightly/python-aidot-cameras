package com.sensorsdata.analytics.android.sdk.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;
import com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SAConfigOptions;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.ThreadNameConstants;
import com.sensorsdata.analytics.android.sdk.advert.utils.ChannelUtils;
import com.sensorsdata.analytics.android.sdk.advert.utils.OaidHelper;
import com.sensorsdata.analytics.android.sdk.dialog.DebugModeSelectDialog;
import com.sensorsdata.analytics.android.sdk.network.HttpCallback;
import com.sensorsdata.analytics.android.sdk.network.HttpMethod;
import com.sensorsdata.analytics.android.sdk.network.RequestHelper;
import com.sensorsdata.analytics.android.sdk.util.NetworkUtils;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import com.sensorsdata.analytics.android.sdk.util.ToastUtil;
import com.sensorsdata.analytics.android.sdk.visual.HeatMapService;
import com.sensorsdata.analytics.android.sdk.visual.VisualizedAutoTrackService;
import com.sensorsdata.analytics.android.sdk.visual.view.PairingCodeEditDialog;
import com.tencent.bugly.Bugly;
import com.yanzhenjie.andserver.util.h;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.glassfish.grizzly.http.server.Constants;
import org.json.JSONObject;

public class SensorsDataDialogUtils {
    private static final String TAG = "SA.SensorsDataDialogUtils";
    private static Dialog sDialog;

    public static void showDialog(Activity activity, String title, String content, String positiveLabel, DialogInterface.OnClickListener positiveOnClickListener, String negativeLabel, DialogInterface.OnClickListener negativeOnClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(content)) {
            builder.setMessage(content);
        }
        builder.setCancelable(false);
        builder.setNegativeButton(negativeLabel, negativeOnClickListener);
        builder.setPositiveButton(positiveLabel, positiveOnClickListener);
        dialogShowDismissOld(builder.create());
    }

    public static void showChannelDebugDialog(final Activity activity, String baseUrl, String monitorId, String projectId, String accountId) {
        final Activity activity2 = activity;
        final String str = baseUrl;
        final String str2 = monitorId;
        final String str3 = projectId;
        final String str4 = accountId;
        showDialog(activity, "即将开启联调模式", "", "确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                Context context = activity2.getApplicationContext();
                boolean isTrackInstallation = ChannelUtils.isTrackInstallation(context);
                if (!isTrackInstallation || ChannelUtils.isCorrectTrackInstallation(context)) {
                    String androidId = SensorsDataUtils.getAndroidID(context);
                    String oaid = OaidHelper.getOAID(context);
                    if (isTrackInstallation && !ChannelUtils.isGetDeviceInfo(context, androidId, oaid)) {
                        SensorsDataDialogUtils.showChannelDebugErrorDialog(activity2);
                    } else if (!NetworkUtils.isNetworkAvailable(context)) {
                        SensorsDataDialogUtils.showDialog(activity2, "当前网络不可用，请检查网络！");
                    } else {
                        String deviceCode = ChannelUtils.getDeviceInfo(activity2, androidId, oaid);
                        final SensorsDataLoadingDialog loadingDialog = new SensorsDataLoadingDialog(activity2);
                        SensorsDataDialogUtils.dialogShowDismissOld(loadingDialog);
                        SensorsDataDialogUtils.requestActiveChannel(str, str2, str3, str4, deviceCode, isTrackInstallation, new HttpCallback.JsonCallback() {
                            public void onFailure(int code, String errorMessage) {
                                loadingDialog.dismiss();
                                SALog.i(SensorsDataDialogUtils.TAG, "ChannelDebug request error:" + errorMessage);
                                SensorsDataDialogUtils.showDialog(activity2, "网络异常,请求失败!");
                            }

                            public void onResponse(JSONObject response) {
                                loadingDialog.dismiss();
                                if (response == null) {
                                    SALog.i(SensorsDataDialogUtils.TAG, "ChannelDebug response error msg: response is null");
                                    SensorsDataDialogUtils.showDialog(activity2, "添加白名单请求失败，请联系神策技术支持人员排查问题!");
                                } else if (response.optInt("code", 0) == 1) {
                                    SensorsDataAutoTrackHelper.showChannelDebugActiveDialog(activity2);
                                } else {
                                    SALog.i(SensorsDataDialogUtils.TAG, "ChannelDebug response error msg:" + response.optString("message"));
                                    SensorsDataDialogUtils.showDialog(activity2, "添加白名单请求失败，请联系神策技术支持人员排查问题!");
                                }
                            }
                        });
                    }
                } else {
                    SensorsDataDialogUtils.showChannelDebugErrorDialog(activity2);
                }
            }
        }, "取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SensorsDataDialogUtils.startLaunchActivity(activity);
            }
        });
    }

    /* access modifiers changed from: private */
    public static void showChannelDebugErrorDialog(final Activity activity) {
        showDialog(activity, "检测到 “设备码为空”，可能原因如下，请排查：", "1. 开启 App 时拒绝“电话”授权；\n2. 手机系统权限设置中是否关闭“电话”授权；\n3. 请联系研发人员确认是否“调用 trackInstallation 接口在获取“电话”授权之后。\n\n 排查修复后，请先卸载应用并重新安装，再扫码进行联调。", "确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SensorsDataDialogUtils.startLaunchActivity(activity);
            }
        }, (String) null, (DialogInterface.OnClickListener) null);
    }

    public static void showPopupWindowDialog(final Activity activity, Uri uri) {
        try {
            Class<?> clazz = Class.forName("com.sensorsdata.sf.ui.utils.PreviewUtil");
            String sfPopupTest = uri.getQueryParameter("sf_popup_test");
            String popupWindowId = uri.getQueryParameter("popup_window_id");
            boolean isSfPopupTest = false;
            if (!TextUtils.isEmpty(sfPopupTest)) {
                isSfPopupTest = Boolean.parseBoolean(sfPopupTest);
            }
            Method[] methods = clazz.getDeclaredMethods();
            Method currentMethod = null;
            Runnable failCallback = null;
            int length = methods.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Method method = methods[i];
                if (method.getName().equals("showPreview")) {
                    currentMethod = method;
                    if (method.getParameterTypes().length == 4) {
                        failCallback = new Runnable() {
                            public void run() {
                                Activity activity = activity;
                                if (activity != null) {
                                    activity.runOnUiThread(new Runnable() {
                                        public void run() {
                                            SensorsDataDialogUtils.showDialog(activity, "测试弹窗加载失败，请确认网络或项目环境是否正常！");
                                        }
                                    });
                                }
                            }
                        };
                        break;
                    }
                }
                i++;
            }
            if (currentMethod != null) {
                if (failCallback != null) {
                    currentMethod.invoke((Object) null, new Object[]{activity, Boolean.valueOf(isSfPopupTest), popupWindowId, failCallback});
                } else {
                    currentMethod.invoke((Object) null, new Object[]{activity, Boolean.valueOf(isSfPopupTest), popupWindowId});
                }
                SchemeActivity.isPopWindow = true;
                return;
            }
            startLaunchActivity(activity);
        } catch (Exception e) {
            SALog.printStackTrace(e);
            startLaunchActivity(activity);
        }
    }

    public static void showDebugModeSelectDialog(final Activity activity, final String infoId, final String locationHref, final String project) {
        try {
            DebugModeSelectDialog dialog = new DebugModeSelectDialog(activity, SensorsDataAPI.sharedInstance().getDebugMode());
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnDebugModeDialogClickListener(new DebugModeSelectDialog.OnDebugModeViewClickListener() {
                public void onCancel(Dialog dialog) {
                    dialog.cancel();
                }

                public void setDebugMode(Dialog dialog, SensorsDataAPI.DebugMode debugMode) {
                    SensorsDataAPI.sharedInstance().setDebugMode(debugMode);
                    dialog.cancel();
                }
            });
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    String serverUrl = SensorsDataAPI.sharedInstance().getServerUrl();
                    SensorsDataAPI.DebugMode mCurrentDebugMode = SensorsDataAPI.sharedInstance().getDebugMode();
                    if (SensorsDataAPI.sharedInstance().isNetworkRequestEnable() && !TextUtils.isEmpty(serverUrl) && !TextUtils.isEmpty(infoId) && mCurrentDebugMode != SensorsDataAPI.DebugMode.DEBUG_OFF) {
                        if (TextUtils.isEmpty(locationHref)) {
                            new SendDebugIdThread(serverUrl, SensorsDataAPI.sharedInstance().getDistinctId(), infoId, ThreadNameConstants.THREAD_SEND_DISTINCT_ID).start();
                        } else {
                            try {
                                if (!TextUtils.isEmpty(project)) {
                                    String url = locationHref + "?project=" + project;
                                    SALog.i(SensorsDataDialogUtils.TAG, "sf url:" + url);
                                    new SendDebugIdThread(url, SensorsDataAPI.sharedInstance().getDistinctId(), infoId, ThreadNameConstants.THREAD_SEND_DISTINCT_ID).start();
                                }
                            } catch (Exception e) {
                                SALog.printStackTrace(e);
                            }
                        }
                    }
                    String currentDebugToastMsg = "";
                    if (mCurrentDebugMode == SensorsDataAPI.DebugMode.DEBUG_OFF) {
                        currentDebugToastMsg = "已关闭调试模式，请重新扫描二维码进行开启";
                    } else if (mCurrentDebugMode == SensorsDataAPI.DebugMode.DEBUG_ONLY) {
                        currentDebugToastMsg = "开启调试模式，校验数据，但不进行数据导入；关闭 App 进程后，将自动关闭调试模式";
                    } else if (mCurrentDebugMode == SensorsDataAPI.DebugMode.DEBUG_AND_TRACK) {
                        currentDebugToastMsg = "开启调试模式，校验数据，并将数据导入到神策分析中；关闭 App 进程后，将自动关闭调试模式";
                    }
                    ToastUtil.showLong(activity, currentDebugToastMsg);
                    SALog.info(SensorsDataDialogUtils.TAG, "您当前的调试模式是：" + mCurrentDebugMode, (Throwable) null);
                    SensorsDataDialogUtils.startLaunchActivity(activity);
                }
            });
            dialogShowDismissOld(dialog);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public static void showOpenHeatMapDialog(final Activity context, final String featureCode, final String postUrl) {
        try {
            if (!SensorsDataAPI.sharedInstance().isNetworkRequestEnable()) {
                showDialog(context, "已关闭网络请求（NetworkRequest），无法使用 App 点击分析，请开启后再试！");
            } else if (!SensorsDataAPI.sharedInstance().isHeatMapEnabled()) {
                showDialog(context, "SDK 没有被正确集成，请联系贵方技术人员开启点击分析。");
            } else {
                boolean isWifi = false;
                try {
                    if (LDNetUtil.NETWORKTYPE_WIFI.equals(NetworkUtils.networkType(context))) {
                        isWifi = true;
                    }
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示");
                if (isWifi) {
                    builder.setMessage("正在连接 App 点击分析...");
                } else {
                    builder.setMessage("正在连接 App 点击分析，建议在 WiFi 环境下使用。");
                }
                builder.setCancelable(false);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SensorsDataDialogUtils.startLaunchActivity(context);
                    }
                });
                builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        HeatMapService.getInstance().start(context, featureCode, postUrl);
                        SensorsDataDialogUtils.startLaunchActivity(context);
                    }
                });
                AlertDialog dialog = builder.create();
                dialogShowDismissOld(dialog);
                try {
                    dialog.getButton(-2).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    dialog.getButton(-2).setBackgroundColor(-1);
                    dialog.getButton(-1).setTextColor(SupportMenu.CATEGORY_MASK);
                    dialog.getButton(-1).setBackgroundColor(-1);
                    if (Build.VERSION.SDK_INT >= 16) {
                        dialog.getButton(-2).setBackground(getDrawable());
                        dialog.getButton(-1).setBackground(getDrawable());
                        return;
                    }
                    dialog.getButton(-2).setBackgroundDrawable(getDrawable());
                    dialog.getButton(-1).setBackgroundDrawable(getDrawable());
                } catch (Exception e2) {
                    SALog.printStackTrace(e2);
                }
            }
        } catch (Exception e3) {
            SALog.printStackTrace(e3);
        }
    }

    static StateListDrawable getDrawable() {
        GradientDrawable pressDrawable = new GradientDrawable();
        pressDrawable.setShape(0);
        pressDrawable.setColor(Color.parseColor("#dddddd"));
        GradientDrawable normalDrawable = new GradientDrawable();
        normalDrawable.setShape(0);
        normalDrawable.setColor(-1);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842908}, pressDrawable);
        stateListDrawable.addState(new int[0], normalDrawable);
        return stateListDrawable;
    }

    public static void showOpenVisualizedAutoTrackDialog(final Activity context, final String featureCode, final String postUrl) {
        try {
            if (!SensorsDataAPI.sharedInstance().isNetworkRequestEnable()) {
                showDialog(context, "已关闭网络请求（NetworkRequest），无法使用 App 可视化全埋点，请开启后再试！");
            } else if (!SensorsDataAPI.sharedInstance().isVisualizedAutoTrackEnabled()) {
                showDialog(context, "SDK 没有被正确集成，请联系贵方技术人员开启可视化全埋点。");
            } else {
                boolean isWifi = false;
                try {
                    if (LDNetUtil.NETWORKTYPE_WIFI.equals(NetworkUtils.networkType(context))) {
                        isWifi = true;
                    }
                } catch (Exception e) {
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示");
                if (isWifi) {
                    builder.setMessage("正在连接 App 可视化全埋点...");
                } else {
                    builder.setMessage("正在连接 App 可视化全埋点，建议在 WiFi 环境下使用。");
                }
                builder.setCancelable(false);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SensorsDataDialogUtils.startLaunchActivity(context);
                    }
                });
                builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        VisualizedAutoTrackService.getInstance().start(context, featureCode, postUrl);
                        SensorsDataDialogUtils.startLaunchActivity(context);
                    }
                });
                AlertDialog dialog = builder.create();
                dialogShowDismissOld(dialog);
                try {
                    dialog.getButton(-2).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    dialog.getButton(-2).setBackgroundColor(-1);
                    dialog.getButton(-1).setTextColor(SupportMenu.CATEGORY_MASK);
                    dialog.getButton(-1).setBackgroundColor(-1);
                    if (Build.VERSION.SDK_INT >= 16) {
                        dialog.getButton(-2).setBackground(getDrawable());
                        dialog.getButton(-1).setBackground(getDrawable());
                        return;
                    }
                    dialog.getButton(-2).setBackgroundDrawable(getDrawable());
                    dialog.getButton(-1).setBackgroundDrawable(getDrawable());
                } catch (Exception e2) {
                    SALog.printStackTrace(e2);
                }
            }
        } catch (Exception e3) {
            SALog.printStackTrace(e3);
        }
    }

    public static void showDialog(final Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示").setMessage(message).setCancelable(false).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SensorsDataDialogUtils.startLaunchActivity(context);
            }
        });
        AlertDialog dialog = builder.create();
        dialogShowDismissOld(dialog);
        try {
            dialog.getButton(-1).setTextColor(SupportMenu.CATEGORY_MASK);
            dialog.getButton(-1).setBackgroundColor(-1);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public static void showPairingCodeInputDialog(final Context context) {
        if (context == null) {
            SALog.i(TAG, "The argument context can't be null");
        } else if (!(context instanceof Activity)) {
            SALog.i(TAG, "The static method showPairingCodeEditDialog(Context context) only accepts Activity as a parameter");
        } else {
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                    new PairingCodeEditDialog(context).show();
                }
            });
        }
    }

    public static void startLaunchActivity(Context context) {
        try {
            if (isSchemeActivity(context)) {
                context.startActivity(context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()));
                ((SchemeActivity) context).finish();
                SALog.i(TAG, "startLaunchActivity");
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public static boolean isSchemeActivity(Context context) {
        if (context == null) {
            return false;
        }
        return context instanceof SchemeActivity;
    }

    public static void dialogShowDismissOld(Dialog dialog) {
        try {
            Dialog dialog2 = sDialog;
            if (dialog2 != null && dialog2.isShowing()) {
                try {
                    sDialog.dismiss();
                    SALog.i(TAG, "Dialog dismiss");
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
            sDialog = dialog;
            dialog.show();
        } catch (Exception e2) {
            SALog.printStackTrace(e2);
        }
    }

    /* access modifiers changed from: private */
    public static void requestActiveChannel(String baseUrl, String monitorId, String projectId, String accountId, String deviceCode, boolean isActive, HttpCallback callback) {
        try {
            JSONObject json = new JSONObject();
            json.put("monitor_id", (Object) monitorId);
            json.put("distinct_id", (Object) SensorsDataAPI.sharedInstance().getDistinctId());
            json.put("project_id", (Object) projectId);
            json.put("account_id", (Object) accountId);
            json.put("has_active", (Object) isActive ? "true" : Bugly.SDK_IS_DEV);
            json.put("device_code", (Object) deviceCode);
            HttpMethod httpMethod = HttpMethod.POST;
            new RequestHelper.Builder(httpMethod, baseUrl + "/api/sdk/channel_tool/url").jsonData(json.toString()).callback(callback).execute();
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public static class SendDebugIdThread extends Thread {
        private String distinctId;
        private String infoId;
        private String serverUrl;

        SendDebugIdThread(String serverUrl2, String distinctId2, String infoId2, String name) {
            super(name);
            this.distinctId = distinctId2;
            this.infoId = infoId2;
            this.serverUrl = serverUrl2;
        }

        public void run() {
            super.run();
            sendHttpRequest(this.serverUrl, false);
        }

        private void sendHttpRequest(String serverUrl2, boolean isRedirects) {
            SSLSocketFactory sSLSocketFactory;
            String str = serverUrl2;
            ByteArrayOutputStream out = null;
            OutputStream out2 = null;
            BufferedOutputStream bout = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(String.format(str + "&info_id=%s", new Object[]{this.infoId}));
                SALog.info(SensorsDataDialogUtils.TAG, String.format("DebugMode URL:%s", new Object[]{url}), (Throwable) null);
                connection = (HttpURLConnection) url.openConnection();
                if (connection == null) {
                    SALog.info(SensorsDataDialogUtils.TAG, String.format("can not connect %s,shouldn't happen", new Object[]{url.toString()}), (Throwable) null);
                    closeStream((ByteArrayOutputStream) null, (OutputStream) null, (BufferedOutputStream) null, connection);
                    return;
                }
                SAConfigOptions configOptions = AbstractSensorsDataAPI.getConfigOptions();
                if (!(configOptions == null || (sSLSocketFactory = configOptions.mSSLSocketFactory) == null || !(connection instanceof HttpsURLConnection))) {
                    ((HttpsURLConnection) connection).setSSLSocketFactory(sSLSocketFactory);
                }
                connection.setInstanceFollowRedirects(false);
                out = new ByteArrayOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(out);
                String requestBody = "{\"distinct_id\": \"" + this.distinctId + "\"}";
                writer.write(requestBody);
                writer.flush();
                SALog.info(SensorsDataDialogUtils.TAG, String.format("DebugMode request body : %s", new Object[]{requestBody}), (Throwable) null);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setRequestMethod(Constants.POST);
                connection.setRequestProperty("Content-type", h.TEXT_PLAIN_VALUE);
                out2 = connection.getOutputStream();
                bout = new BufferedOutputStream(out2);
                bout.write(out.toString().getBytes("UTF-8"));
                bout.flush();
                out.close();
                int responseCode = connection.getResponseCode();
                SALog.info(SensorsDataDialogUtils.TAG, String.format(Locale.CHINA, "DebugMode 后端的响应码是:%d", new Object[]{Integer.valueOf(responseCode)}), (Throwable) null);
                if (!isRedirects && NetworkUtils.needRedirects(responseCode)) {
                    String location = NetworkUtils.getLocation(connection, str);
                    if (!TextUtils.isEmpty(location)) {
                        closeStream(out, out2, bout, connection);
                        sendHttpRequest(location, true);
                    }
                }
                closeStream(out, out2, bout, connection);
            } catch (Exception e) {
                SALog.printStackTrace(e);
            } catch (Throwable th) {
                closeStream((ByteArrayOutputStream) null, (OutputStream) null, (BufferedOutputStream) null, connection);
                throw th;
            }
        }

        private void closeStream(ByteArrayOutputStream out, OutputStream out2, BufferedOutputStream bout, HttpURLConnection connection) {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
            if (out2 != null) {
                try {
                    out2.close();
                } catch (Exception e2) {
                    SALog.printStackTrace(e2);
                }
            }
            if (bout != null) {
                try {
                    bout.close();
                } catch (Exception e3) {
                    SALog.printStackTrace(e3);
                }
            }
            if (connection != null) {
                try {
                    connection.disconnect();
                } catch (Exception e4) {
                    SALog.printStackTrace(e4);
                }
            }
        }
    }
}

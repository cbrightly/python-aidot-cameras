package com.sensorsdata.analytics.android.sdk;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter;
import com.sensorsdata.analytics.android.sdk.exceptions.ConnectErrorException;
import com.sensorsdata.analytics.android.sdk.exceptions.DebugModeException;
import com.sensorsdata.analytics.android.sdk.exceptions.InvalidDataException;
import com.sensorsdata.analytics.android.sdk.exceptions.ResponseErrorException;
import com.sensorsdata.analytics.android.sdk.util.Base64Coder;
import com.sensorsdata.analytics.android.sdk.util.JSONUtils;
import com.sensorsdata.analytics.android.sdk.util.NetworkUtils;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.glassfish.grizzly.http.GZipContentEncoding;
import org.glassfish.grizzly.http.server.Constants;
import org.json.JSONObject;

public class AnalyticsMessages {
    private static final int DELETE_ALL = 4;
    private static final int FLUSH_QUEUE = 3;
    private static final int FLUSH_SCHEDULE = 5;
    private static final Map<Context, AnalyticsMessages> S_INSTANCES = new HashMap();
    private static final String TAG = "SA.AnalyticsMessages";
    private final Context mContext;
    /* access modifiers changed from: private */
    public final DbAdapter mDbAdapter = DbAdapter.getInstance();
    private SensorsDataAPI mSensorsDataAPI;
    private final Worker mWorker = new Worker();

    private AnalyticsMessages(Context context, SensorsDataAPI sensorsDataAPI) {
        this.mContext = context;
        this.mSensorsDataAPI = sensorsDataAPI;
    }

    public static AnalyticsMessages getInstance(Context messageContext, SensorsDataAPI sensorsDataAPI) {
        AnalyticsMessages ret;
        Map<Context, AnalyticsMessages> map = S_INSTANCES;
        synchronized (map) {
            Context appContext = messageContext.getApplicationContext();
            if (!map.containsKey(appContext)) {
                ret = new AnalyticsMessages(appContext, sensorsDataAPI);
                map.put(appContext, ret);
            } else {
                ret = map.get(appContext);
            }
        }
        return ret;
    }

    private static byte[] slurp(InputStream inputStream) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[8192];
        while (true) {
            int read = inputStream.read(data, 0, data.length);
            int nRead = read;
            if (read != -1) {
                buffer.write(data, 0, nRead);
            } else {
                buffer.flush();
                return buffer.toByteArray();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void enqueueEventMessage(String type, JSONObject eventJson) {
        try {
            synchronized (this.mDbAdapter) {
                int ret = this.mDbAdapter.addJSON(eventJson);
                if (ret < 0) {
                    String error = "Failed to enqueue the event: " + eventJson;
                    if (!this.mSensorsDataAPI.isDebugMode()) {
                        SALog.i(TAG, error);
                    } else {
                        throw new DebugModeException(error);
                    }
                }
                Message m = Message.obtain();
                m.what = 3;
                if (!this.mSensorsDataAPI.isDebugMode()) {
                    if (ret != -2) {
                        if (!type.equals("track_signup")) {
                            if (ret <= this.mSensorsDataAPI.getFlushBulkSize()) {
                                this.mWorker.runMessageOnce(m, (long) this.mSensorsDataAPI.getFlushInterval());
                            }
                        }
                        this.mWorker.runMessage(m);
                    }
                }
                this.mWorker.runMessage(m);
            }
        } catch (Exception e) {
            SALog.i(TAG, "enqueueEventMessage error:" + e);
        }
    }

    /* access modifiers changed from: package-private */
    public void flush() {
        try {
            Message m = Message.obtain();
            m.what = 3;
            this.mWorker.runMessage(m);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    /* access modifiers changed from: package-private */
    public void flushScheduled() {
        try {
            Message m = Message.obtain();
            m.what = 5;
            this.mWorker.runMessageOnce(m, (long) this.mSensorsDataAPI.getFlushInterval());
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    /* access modifiers changed from: package-private */
    public void deleteAll() {
        try {
            Message m = Message.obtain();
            m.what = 4;
            this.mWorker.runMessage(m);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:189:0x0313 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void sendData() {
        /*
            r18 = this;
            r7 = r18
            com.sensorsdata.analytics.android.sdk.SensorsDataAPI r0 = r7.mSensorsDataAPI     // Catch:{ Exception -> 0x0349 }
            boolean r0 = r0.isNetworkRequestEnable()     // Catch:{ Exception -> 0x0349 }
            if (r0 != 0) goto L_0x0012
            java.lang.String r0 = "SA.AnalyticsMessages"
            java.lang.String r1 = "NetworkRequest 已关闭，不发送数据！"
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r0, (java.lang.String) r1)     // Catch:{ Exception -> 0x0349 }
            return
        L_0x0012:
            com.sensorsdata.analytics.android.sdk.SensorsDataAPI r0 = r7.mSensorsDataAPI     // Catch:{ Exception -> 0x0349 }
            java.lang.String r0 = r0.getServerUrl()     // Catch:{ Exception -> 0x0349 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0349 }
            if (r0 == 0) goto L_0x0026
            java.lang.String r0 = "SA.AnalyticsMessages"
            java.lang.String r1 = "Server url is null or empty."
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r0, (java.lang.String) r1)     // Catch:{ Exception -> 0x0349 }
            return
        L_0x0026:
            android.content.Context r0 = r7.mContext     // Catch:{ Exception -> 0x0349 }
            boolean r0 = com.sensorsdata.analytics.android.sdk.util.NetworkUtils.isNetworkAvailable((android.content.Context) r0)     // Catch:{ Exception -> 0x0349 }
            if (r0 != 0) goto L_0x002f
            return
        L_0x002f:
            android.content.Context r0 = r7.mContext     // Catch:{ Exception -> 0x0349 }
            java.lang.String r0 = com.sensorsdata.analytics.android.sdk.util.NetworkUtils.networkType(r0)     // Catch:{ Exception -> 0x0349 }
            com.sensorsdata.analytics.android.sdk.SensorsDataAPI r1 = r7.mSensorsDataAPI     // Catch:{ Exception -> 0x0349 }
            int r1 = r1.getFlushNetworkPolicy()     // Catch:{ Exception -> 0x0349 }
            boolean r1 = com.sensorsdata.analytics.android.sdk.util.NetworkUtils.isShouldFlush(r0, r1)     // Catch:{ Exception -> 0x0349 }
            r8 = 0
            r9 = 1
            if (r1 != 0) goto L_0x0054
            java.lang.String r1 = "SA.AnalyticsMessages"
            java.lang.String r2 = "您当前网络为 %s，无法发送数据，请确认您的网络发送策略！"
            java.lang.Object[] r3 = new java.lang.Object[r9]     // Catch:{ Exception -> 0x0349 }
            r3[r8] = r0     // Catch:{ Exception -> 0x0349 }
            java.lang.String r2 = java.lang.String.format(r2, r3)     // Catch:{ Exception -> 0x0349 }
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r1, (java.lang.String) r2)     // Catch:{ Exception -> 0x0349 }
            return
        L_0x0054:
            com.sensorsdata.analytics.android.sdk.SAConfigOptions r1 = com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI.getConfigOptions()     // Catch:{ Exception -> 0x0349 }
            boolean r1 = r1.isMultiProcessFlush()     // Catch:{ Exception -> 0x0349 }
            if (r1 == 0) goto L_0x0071
            com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter r1 = com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter.getInstance()     // Catch:{ Exception -> 0x0349 }
            boolean r1 = r1.isSubProcessFlushing()     // Catch:{ Exception -> 0x0349 }
            if (r1 == 0) goto L_0x0069
            return
        L_0x0069:
            com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter r1 = com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter.getInstance()     // Catch:{ Exception -> 0x0349 }
            r1.commitSubProcessFlushState(r9)     // Catch:{ Exception -> 0x0349 }
            goto L_0x0076
        L_0x0071:
            boolean r1 = com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI.mIsMainProcess     // Catch:{ Exception -> 0x0349 }
            if (r1 != 0) goto L_0x0076
            return
        L_0x0076:
            r0 = 100
            r10 = r0
        L_0x007a:
            if (r10 <= 0) goto L_0x0337
            r11 = 1
            com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter r1 = r7.mDbAdapter
            monitor-enter(r1)
            com.sensorsdata.analytics.android.sdk.SensorsDataAPI r0 = r7.mSensorsDataAPI     // Catch:{ all -> 0x0334 }
            boolean r0 = r0.isDebugMode()     // Catch:{ all -> 0x0334 }
            if (r0 == 0) goto L_0x0092
            com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter r0 = r7.mDbAdapter     // Catch:{ all -> 0x0334 }
            java.lang.String r2 = "events"
            java.lang.String[] r0 = r0.generateDataString(r2, r9)     // Catch:{ all -> 0x0334 }
            r12 = r0
            goto L_0x009d
        L_0x0092:
            com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter r0 = r7.mDbAdapter     // Catch:{ all -> 0x0334 }
            java.lang.String r2 = "events"
            r3 = 50
            java.lang.String[] r0 = r0.generateDataString(r2, r3)     // Catch:{ all -> 0x0334 }
            r12 = r0
        L_0x009d:
            monitor-exit(r1)     // Catch:{ all -> 0x0334 }
            if (r12 != 0) goto L_0x00a8
            com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter r0 = com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter.getInstance()
            r0.commitSubProcessFlushState(r8)
            return
        L_0x00a8:
            r13 = r12[r8]
            r14 = r12[r9]
            r0 = 2
            r15 = r12[r0]
            r6 = 0
            r0 = r14
            java.lang.String r1 = "1"
            boolean r1 = r1.equals(r15)     // Catch:{ ConnectErrorException -> 0x028a, InvalidDataException -> 0x0231, ResponseErrorException -> 0x01ce, Exception -> 0x0173, all -> 0x016f }
            if (r1 == 0) goto L_0x00d3
            java.lang.String r1 = r7.encodeData(r14)     // Catch:{ ConnectErrorException -> 0x00cf, InvalidDataException -> 0x00cb, ResponseErrorException -> 0x00c7, Exception -> 0x00c3, all -> 0x00bf }
            r0 = r1
            goto L_0x00d3
        L_0x00bf:
            r0 = move-exception
            r2 = r6
            goto L_0x02ed
        L_0x00c3:
            r0 = move-exception
            r2 = r6
            goto L_0x0175
        L_0x00c7:
            r0 = move-exception
            r2 = r6
            goto L_0x01d0
        L_0x00cb:
            r0 = move-exception
            r2 = r6
            goto L_0x0233
        L_0x00cf:
            r0 = move-exception
            r2 = r6
            goto L_0x028c
        L_0x00d3:
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ ConnectErrorException -> 0x028a, InvalidDataException -> 0x0231, ResponseErrorException -> 0x01ce, Exception -> 0x0173, all -> 0x016f }
            if (r1 != 0) goto L_0x011b
            com.sensorsdata.analytics.android.sdk.SensorsDataAPI r1 = r7.mSensorsDataAPI     // Catch:{ ConnectErrorException -> 0x0117, InvalidDataException -> 0x0113, ResponseErrorException -> 0x010f, Exception -> 0x010b, all -> 0x0107 }
            java.lang.String r2 = r1.getServerUrl()     // Catch:{ ConnectErrorException -> 0x0117, InvalidDataException -> 0x0113, ResponseErrorException -> 0x010f, Exception -> 0x010b, all -> 0x0107 }
            r16 = 0
            r1 = r18
            r3 = r0
            r4 = r15
            r5 = r14
            r17 = r6
            r6 = r16
            r1.sendHttpRequest(r2, r3, r4, r5, r6)     // Catch:{ ConnectErrorException -> 0x0102, InvalidDataException -> 0x00fd, ResponseErrorException -> 0x00f8, Exception -> 0x00f3, all -> 0x00ee }
            goto L_0x011d
        L_0x00ee:
            r0 = move-exception
            r2 = r17
            goto L_0x02ed
        L_0x00f3:
            r0 = move-exception
            r2 = r17
            goto L_0x0175
        L_0x00f8:
            r0 = move-exception
            r2 = r17
            goto L_0x01d0
        L_0x00fd:
            r0 = move-exception
            r2 = r17
            goto L_0x0233
        L_0x0102:
            r0 = move-exception
            r2 = r17
            goto L_0x028c
        L_0x0107:
            r0 = move-exception
            r2 = r6
            goto L_0x02ed
        L_0x010b:
            r0 = move-exception
            r2 = r6
            goto L_0x0175
        L_0x010f:
            r0 = move-exception
            r2 = r6
            goto L_0x01d0
        L_0x0113:
            r0 = move-exception
            r2 = r6
            goto L_0x0233
        L_0x0117:
            r0 = move-exception
            r2 = r6
            goto L_0x028c
        L_0x011b:
            r17 = r6
        L_0x011d:
            com.sensorsdata.analytics.android.sdk.SensorsDataAPI r0 = r7.mSensorsDataAPI
            boolean r0 = r0.isDebugMode()
            boolean r1 = android.text.TextUtils.isEmpty(r17)
            if (r1 != 0) goto L_0x0148
            if (r0 != 0) goto L_0x0135
            boolean r1 = com.sensorsdata.analytics.android.sdk.SALog.isLogEnabled()
            if (r1 == 0) goto L_0x0132
            goto L_0x0135
        L_0x0132:
            r2 = r17
            goto L_0x014a
        L_0x0135:
            java.lang.String r1 = "SA.AnalyticsMessages"
            r2 = r17
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r1, (java.lang.String) r2)
            if (r0 == 0) goto L_0x014a
            boolean r1 = com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI.SHOW_DEBUG_INFO_VIEW
            if (r1 == 0) goto L_0x014a
            android.content.Context r1 = r7.mContext
            com.sensorsdata.analytics.android.sdk.util.ToastUtil.showShort(r1, r2)
            goto L_0x014a
        L_0x0148:
            r2 = r17
        L_0x014a:
            if (r11 != 0) goto L_0x0151
            if (r0 == 0) goto L_0x014f
            goto L_0x0151
        L_0x014f:
            r1 = 0
            goto L_0x016c
        L_0x0151:
            com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter r1 = r7.mDbAdapter
            int r1 = r1.cleanupEvents(r13)
            java.lang.String r3 = "SA.AnalyticsMessages"
            java.util.Locale r4 = java.util.Locale.CHINA
            java.lang.String r5 = "Events flushed. [left = %d]"
            java.lang.Object[] r6 = new java.lang.Object[r9]
            java.lang.Integer r10 = java.lang.Integer.valueOf(r1)
            r6[r8] = r10
            java.lang.String r4 = java.lang.String.format(r4, r5, r6)
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r3, (java.lang.String) r4)
        L_0x016c:
            r10 = r1
            goto L_0x02ea
        L_0x016f:
            r0 = move-exception
            r2 = r6
            goto L_0x02ed
        L_0x0173:
            r0 = move-exception
            r2 = r6
        L_0x0175:
            r11 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ec }
            r1.<init>()     // Catch:{ all -> 0x02ec }
            java.lang.String r3 = "Exception: "
            r1.append(r3)     // Catch:{ all -> 0x02ec }
            java.lang.String r3 = r0.getMessage()     // Catch:{ all -> 0x02ec }
            r1.append(r3)     // Catch:{ all -> 0x02ec }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x02ec }
            r0 = r1
            com.sensorsdata.analytics.android.sdk.SensorsDataAPI r1 = r7.mSensorsDataAPI
            boolean r1 = r1.isDebugMode()
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x01b0
            if (r1 != 0) goto L_0x01a0
            boolean r2 = com.sensorsdata.analytics.android.sdk.SALog.isLogEnabled()
            if (r2 == 0) goto L_0x01b0
        L_0x01a0:
            java.lang.String r2 = "SA.AnalyticsMessages"
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r2, (java.lang.String) r0)
            if (r1 == 0) goto L_0x01b0
            boolean r2 = com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI.SHOW_DEBUG_INFO_VIEW
            if (r2 == 0) goto L_0x01b0
            android.content.Context r2 = r7.mContext
            com.sensorsdata.analytics.android.sdk.util.ToastUtil.showShort(r2, r0)
        L_0x01b0:
            if (r11 != 0) goto L_0x01b4
            if (r1 == 0) goto L_0x02cc
        L_0x01b4:
            com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter r2 = r7.mDbAdapter
            int r2 = r2.cleanupEvents(r13)
            java.lang.String r3 = "SA.AnalyticsMessages"
            java.util.Locale r4 = java.util.Locale.CHINA
            java.lang.String r5 = "Events flushed. [left = %d]"
            java.lang.Object[] r6 = new java.lang.Object[r9]
            java.lang.Integer r10 = java.lang.Integer.valueOf(r2)
            r6[r8] = r10
            java.lang.String r4 = java.lang.String.format(r4, r5, r6)
            goto L_0x02e6
        L_0x01ce:
            r0 = move-exception
            r2 = r6
        L_0x01d0:
            int r1 = r0.getHttpCode()     // Catch:{ all -> 0x02ec }
            boolean r1 = r7.isDeleteEventsByCode(r1)     // Catch:{ all -> 0x02ec }
            r11 = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ec }
            r1.<init>()     // Catch:{ all -> 0x02ec }
            java.lang.String r3 = "ResponseErrorException: "
            r1.append(r3)     // Catch:{ all -> 0x02ec }
            java.lang.String r3 = r0.getMessage()     // Catch:{ all -> 0x02ec }
            r1.append(r3)     // Catch:{ all -> 0x02ec }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x02ec }
            r0 = r1
            com.sensorsdata.analytics.android.sdk.SensorsDataAPI r1 = r7.mSensorsDataAPI
            boolean r1 = r1.isDebugMode()
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x0213
            if (r1 != 0) goto L_0x0203
            boolean r2 = com.sensorsdata.analytics.android.sdk.SALog.isLogEnabled()
            if (r2 == 0) goto L_0x0213
        L_0x0203:
            java.lang.String r2 = "SA.AnalyticsMessages"
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r2, (java.lang.String) r0)
            if (r1 == 0) goto L_0x0213
            boolean r2 = com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI.SHOW_DEBUG_INFO_VIEW
            if (r2 == 0) goto L_0x0213
            android.content.Context r2 = r7.mContext
            com.sensorsdata.analytics.android.sdk.util.ToastUtil.showShort(r2, r0)
        L_0x0213:
            if (r11 != 0) goto L_0x0217
            if (r1 == 0) goto L_0x02cc
        L_0x0217:
            com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter r2 = r7.mDbAdapter
            int r2 = r2.cleanupEvents(r13)
            java.lang.String r3 = "SA.AnalyticsMessages"
            java.util.Locale r4 = java.util.Locale.CHINA
            java.lang.String r5 = "Events flushed. [left = %d]"
            java.lang.Object[] r6 = new java.lang.Object[r9]
            java.lang.Integer r10 = java.lang.Integer.valueOf(r2)
            r6[r8] = r10
            java.lang.String r4 = java.lang.String.format(r4, r5, r6)
            goto L_0x02e6
        L_0x0231:
            r0 = move-exception
            r2 = r6
        L_0x0233:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ec }
            r1.<init>()     // Catch:{ all -> 0x02ec }
            java.lang.String r3 = "Invalid data: "
            r1.append(r3)     // Catch:{ all -> 0x02ec }
            java.lang.String r3 = r0.getMessage()     // Catch:{ all -> 0x02ec }
            r1.append(r3)     // Catch:{ all -> 0x02ec }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x02ec }
            r0 = r1
            com.sensorsdata.analytics.android.sdk.SensorsDataAPI r1 = r7.mSensorsDataAPI
            boolean r1 = r1.isDebugMode()
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x026d
            if (r1 != 0) goto L_0x025d
            boolean r2 = com.sensorsdata.analytics.android.sdk.SALog.isLogEnabled()
            if (r2 == 0) goto L_0x026d
        L_0x025d:
            java.lang.String r2 = "SA.AnalyticsMessages"
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r2, (java.lang.String) r0)
            if (r1 == 0) goto L_0x026d
            boolean r2 = com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI.SHOW_DEBUG_INFO_VIEW
            if (r2 == 0) goto L_0x026d
            android.content.Context r2 = r7.mContext
            com.sensorsdata.analytics.android.sdk.util.ToastUtil.showShort(r2, r0)
        L_0x026d:
            if (r11 != 0) goto L_0x0271
            if (r1 == 0) goto L_0x02cc
        L_0x0271:
            com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter r2 = r7.mDbAdapter
            int r2 = r2.cleanupEvents(r13)
            java.lang.String r3 = "SA.AnalyticsMessages"
            java.util.Locale r4 = java.util.Locale.CHINA
            java.lang.String r5 = "Events flushed. [left = %d]"
            java.lang.Object[] r6 = new java.lang.Object[r9]
            java.lang.Integer r10 = java.lang.Integer.valueOf(r2)
            r6[r8] = r10
            java.lang.String r4 = java.lang.String.format(r4, r5, r6)
            goto L_0x02e6
        L_0x028a:
            r0 = move-exception
            r2 = r6
        L_0x028c:
            r11 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ec }
            r1.<init>()     // Catch:{ all -> 0x02ec }
            java.lang.String r3 = "Connection error: "
            r1.append(r3)     // Catch:{ all -> 0x02ec }
            java.lang.String r3 = r0.getMessage()     // Catch:{ all -> 0x02ec }
            r1.append(r3)     // Catch:{ all -> 0x02ec }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x02ec }
            r0 = r1
            com.sensorsdata.analytics.android.sdk.SensorsDataAPI r1 = r7.mSensorsDataAPI
            boolean r1 = r1.isDebugMode()
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x02c7
            if (r1 != 0) goto L_0x02b7
            boolean r2 = com.sensorsdata.analytics.android.sdk.SALog.isLogEnabled()
            if (r2 == 0) goto L_0x02c7
        L_0x02b7:
            java.lang.String r2 = "SA.AnalyticsMessages"
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r2, (java.lang.String) r0)
            if (r1 == 0) goto L_0x02c7
            boolean r2 = com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI.SHOW_DEBUG_INFO_VIEW
            if (r2 == 0) goto L_0x02c7
            android.content.Context r2 = r7.mContext
            com.sensorsdata.analytics.android.sdk.util.ToastUtil.showShort(r2, r0)
        L_0x02c7:
            if (r11 != 0) goto L_0x02ce
            if (r1 == 0) goto L_0x02cc
            goto L_0x02ce
        L_0x02cc:
            r2 = 0
            goto L_0x02e9
        L_0x02ce:
            com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter r2 = r7.mDbAdapter
            int r2 = r2.cleanupEvents(r13)
            java.lang.String r3 = "SA.AnalyticsMessages"
            java.util.Locale r4 = java.util.Locale.CHINA
            java.lang.String r5 = "Events flushed. [left = %d]"
            java.lang.Object[] r6 = new java.lang.Object[r9]
            java.lang.Integer r10 = java.lang.Integer.valueOf(r2)
            r6[r8] = r10
            java.lang.String r4 = java.lang.String.format(r4, r5, r6)
        L_0x02e6:
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r3, (java.lang.String) r4)
        L_0x02e9:
            r10 = r2
        L_0x02ea:
            goto L_0x007a
        L_0x02ec:
            r0 = move-exception
        L_0x02ed:
            com.sensorsdata.analytics.android.sdk.SensorsDataAPI r1 = r7.mSensorsDataAPI
            boolean r1 = r1.isDebugMode()
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x0311
            if (r1 != 0) goto L_0x0301
            boolean r3 = com.sensorsdata.analytics.android.sdk.SALog.isLogEnabled()
            if (r3 == 0) goto L_0x0311
        L_0x0301:
            java.lang.String r3 = "SA.AnalyticsMessages"
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r3, (java.lang.String) r2)
            if (r1 == 0) goto L_0x0311
            boolean r3 = com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI.SHOW_DEBUG_INFO_VIEW
            if (r3 == 0) goto L_0x0311
            android.content.Context r3 = r7.mContext
            com.sensorsdata.analytics.android.sdk.util.ToastUtil.showShort(r3, r2)
        L_0x0311:
            if (r11 != 0) goto L_0x0318
            if (r1 == 0) goto L_0x0316
            goto L_0x0318
        L_0x0316:
            r3 = 0
            goto L_0x0333
        L_0x0318:
            com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter r3 = r7.mDbAdapter
            int r3 = r3.cleanupEvents(r13)
            java.lang.String r4 = "SA.AnalyticsMessages"
            java.util.Locale r5 = java.util.Locale.CHINA
            java.lang.String r6 = "Events flushed. [left = %d]"
            java.lang.Object[] r9 = new java.lang.Object[r9]
            java.lang.Integer r10 = java.lang.Integer.valueOf(r3)
            r9[r8] = r10
            java.lang.String r5 = java.lang.String.format(r5, r6, r9)
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r4, (java.lang.String) r5)
        L_0x0333:
            throw r0
        L_0x0334:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0334 }
            throw r0
        L_0x0337:
            com.sensorsdata.analytics.android.sdk.SAConfigOptions r0 = com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI.getConfigOptions()
            boolean r0 = r0.isMultiProcessFlush()
            if (r0 == 0) goto L_0x0348
            com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter r0 = com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter.getInstance()
            r0.commitSubProcessFlushState(r8)
        L_0x0348:
            return
        L_0x0349:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.AnalyticsMessages.sendData():void");
    }

    private void sendHttpRequest(String path, String data, String gzip, String rawMessage, boolean isRedirects) {
        int responseCode;
        OutputStream out;
        BufferedOutputStream bout;
        InputStream in;
        SSLSocketFactory sSLSocketFactory;
        String str = path;
        HttpURLConnection connection = null;
        InputStream in2 = null;
        OutputStream out2 = null;
        BufferedOutputStream bout2 = null;
        try {
            URL url = new URL(str);
            HttpURLConnection connection2 = (HttpURLConnection) url.openConnection();
            if (connection2 == null) {
                try {
                    SALog.i(TAG, String.format("can not connect %s, it shouldn't happen", new Object[]{url.toString()}), (Throwable) null);
                    closeStream((BufferedOutputStream) null, (OutputStream) null, (InputStream) null, connection2);
                } catch (IOException e) {
                    e = e;
                    connection = connection2;
                    try {
                        throw new ConnectErrorException((Throwable) e);
                    } catch (Throwable th) {
                        e = th;
                        closeStream(bout2, out2, in2, connection);
                        throw e;
                    }
                } catch (Throwable th2) {
                    e = th2;
                    connection = connection2;
                    closeStream(bout2, out2, in2, connection);
                    throw e;
                }
            } else {
                SAConfigOptions configOptions = AbstractSensorsDataAPI.getConfigOptions();
                if (!(configOptions == null || (sSLSocketFactory = configOptions.mSSLSocketFactory) == null || !(connection2 instanceof HttpsURLConnection))) {
                    ((HttpsURLConnection) connection2).setSSLSocketFactory(sSLSocketFactory);
                }
                connection2.setInstanceFollowRedirects(false);
                if (this.mSensorsDataAPI.getDebugMode() == SensorsDataAPI.DebugMode.DEBUG_ONLY) {
                    connection2.addRequestProperty("Dry-Run", "true");
                }
                String cookie = this.mSensorsDataAPI.getCookie(false);
                if (!TextUtils.isEmpty(cookie)) {
                    connection2.setRequestProperty(HttpHeaders.HEAD_KEY_COOKIE, cookie);
                }
                Uri.Builder builder = new Uri.Builder();
                if (!TextUtils.isEmpty(data)) {
                    builder.appendQueryParameter("crc", String.valueOf(data.hashCode()));
                }
                builder.appendQueryParameter(GZipContentEncoding.NAME, gzip);
                builder.appendQueryParameter("data_list", data);
                String query = builder.build().getEncodedQuery();
                if (TextUtils.isEmpty(query)) {
                    closeStream((BufferedOutputStream) null, (OutputStream) null, (InputStream) null, connection2);
                    return;
                }
                connection2.setFixedLengthStreamingMode(query.getBytes("UTF-8").length);
                connection2.setDoOutput(true);
                connection2.setRequestMethod(Constants.POST);
                connection2.setConnectTimeout(30000);
                connection2.setReadTimeout(30000);
                out2 = connection2.getOutputStream();
                try {
                    bout2 = new BufferedOutputStream(out2);
                    try {
                        bout2.write(query.getBytes("UTF-8"));
                        bout2.flush();
                        int responseCode2 = connection2.getResponseCode();
                        StringBuilder sb = new StringBuilder();
                        sb.append("responseCode: ");
                        int responseCode3 = responseCode2;
                        sb.append(responseCode3);
                        SALog.i(TAG, sb.toString());
                        if (isRedirects || !NetworkUtils.needRedirects(responseCode3)) {
                            responseCode = responseCode3;
                            out = out2;
                            bout = bout2;
                        } else {
                            String str2 = query;
                            String query2 = NetworkUtils.getLocation(connection2, str);
                            if (!TextUtils.isEmpty(query2)) {
                                try {
                                    closeStream(bout2, out2, (InputStream) null, connection2);
                                    OutputStream out3 = out2;
                                    BufferedOutputStream bout3 = bout2;
                                    int i = responseCode3;
                                    try {
                                        sendHttpRequest(query2, data, gzip, rawMessage, true);
                                        closeStream(bout3, out3, (InputStream) null, connection2);
                                        return;
                                    } catch (IOException e2) {
                                        e = e2;
                                        bout2 = bout3;
                                        connection = connection2;
                                        out2 = out3;
                                        throw new ConnectErrorException((Throwable) e);
                                    } catch (Throwable th3) {
                                        e = th3;
                                        bout2 = bout3;
                                        connection = connection2;
                                        out2 = out3;
                                        closeStream(bout2, out2, in2, connection);
                                        throw e;
                                    }
                                } catch (IOException e3) {
                                    e = e3;
                                    OutputStream outputStream = out2;
                                    OutputStream out4 = bout2;
                                    connection = connection2;
                                    out2 = outputStream;
                                    throw new ConnectErrorException((Throwable) e);
                                } catch (Throwable th4) {
                                    e = th4;
                                    OutputStream outputStream2 = out2;
                                    OutputStream out5 = bout2;
                                    connection = connection2;
                                    out2 = outputStream2;
                                    closeStream(bout2, out2, in2, connection);
                                    throw e;
                                }
                            } else {
                                responseCode = responseCode3;
                                out = out2;
                                bout = bout2;
                            }
                        }
                        try {
                            in = connection2.getInputStream();
                        } catch (FileNotFoundException e4) {
                            FileNotFoundException fileNotFoundException = e4;
                            try {
                                in = connection2.getErrorStream();
                            } catch (IOException e5) {
                                e = e5;
                                bout2 = bout;
                                out2 = out;
                                connection = connection2;
                                throw new ConnectErrorException((Throwable) e);
                            } catch (Throwable th5) {
                                e = th5;
                                bout2 = bout;
                                out2 = out;
                                connection = connection2;
                                closeStream(bout2, out2, in2, connection);
                                throw e;
                            }
                        }
                        byte[] responseBody = slurp(in);
                        in.close();
                        in2 = null;
                        String response = new String(responseBody, "UTF-8");
                        if (SALog.isLogEnabled()) {
                            String jsonMessage = JSONUtils.formatJson(rawMessage);
                            if (responseCode < 200 || responseCode >= 300) {
                                SALog.i(TAG, "invalid message: \n" + jsonMessage);
                                byte[] bArr = responseBody;
                                String str3 = jsonMessage;
                                SALog.i(TAG, String.format(Locale.CHINA, "ret_code: %d", new Object[]{Integer.valueOf(responseCode)}));
                                SALog.i(TAG, String.format(Locale.CHINA, "ret_content: %s", new Object[]{response}));
                            } else {
                                SALog.i(TAG, "valid message: \n" + jsonMessage);
                                byte[] bArr2 = responseBody;
                            }
                        }
                        if (responseCode < 200 || responseCode >= 300) {
                            throw new ResponseErrorException(String.format("flush failure with response '%s', the response code is '%d'", new Object[]{response, Integer.valueOf(responseCode)}), responseCode);
                        } else {
                            closeStream(bout, out, (InputStream) null, connection2);
                        }
                    } catch (IOException e6) {
                        e = e6;
                        OutputStream outputStream3 = out2;
                        OutputStream out6 = bout2;
                        out2 = outputStream3;
                        connection = connection2;
                        throw new ConnectErrorException((Throwable) e);
                    } catch (Throwable th6) {
                        e = th6;
                        OutputStream outputStream4 = out2;
                        OutputStream out7 = bout2;
                        out2 = outputStream4;
                        connection = connection2;
                        closeStream(bout2, out2, in2, connection);
                        throw e;
                    }
                } catch (IOException e7) {
                    e = e7;
                    OutputStream outputStream5 = out2;
                    connection = connection2;
                    throw new ConnectErrorException((Throwable) e);
                } catch (Throwable th7) {
                    e = th7;
                    OutputStream outputStream6 = out2;
                    connection = connection2;
                    closeStream(bout2, out2, in2, connection);
                    throw e;
                }
            }
        } catch (IOException e8) {
            e = e8;
            throw new ConnectErrorException((Throwable) e);
        }
    }

    private boolean isDeleteEventsByCode(int httpCode) {
        if (httpCode == 404 || httpCode == 403 || (httpCode >= 500 && httpCode < 600)) {
            return false;
        }
        return true;
    }

    private void closeStream(BufferedOutputStream bout, OutputStream out, InputStream in, HttpURLConnection connection) {
        if (bout != null) {
            try {
                bout.close();
            } catch (Exception e) {
                SALog.i(TAG, e.getMessage());
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (Exception e2) {
                SALog.i(TAG, e2.getMessage());
            }
        }
        if (in != null) {
            try {
                in.close();
            } catch (Exception e3) {
                SALog.i(TAG, e3.getMessage());
            }
        }
        if (connection != null) {
            try {
                connection.disconnect();
            } catch (Exception e4) {
                SALog.i(TAG, e4.getMessage());
            }
        }
    }

    private String encodeData(String rawMessage) {
        GZIPOutputStream gos = null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream(rawMessage.getBytes("UTF-8").length);
            gos = new GZIPOutputStream(os);
            gos.write(rawMessage.getBytes("UTF-8"));
            gos.close();
            byte[] compressed = os.toByteArray();
            os.close();
            String str = new String(Base64Coder.encode(compressed));
            try {
                gos.close();
            } catch (IOException e) {
            }
            return str;
        } catch (IOException exception) {
            throw new InvalidDataException((Throwable) exception);
        } catch (Throwable th) {
            if (gos != null) {
                try {
                    gos.close();
                } catch (IOException e2) {
                }
            }
            throw th;
        }
    }

    public class Worker {
        private Handler mHandler;
        private final Object mHandlerLock = new Object();

        Worker() {
            HandlerThread thread = new HandlerThread("com.sensorsdata.analytics.android.sdk.AnalyticsMessages.Worker", 1);
            thread.start();
            this.mHandler = new AnalyticsMessageHandler(thread.getLooper());
        }

        /* access modifiers changed from: package-private */
        public void runMessage(Message msg) {
            synchronized (this.mHandlerLock) {
                Handler handler = this.mHandler;
                if (handler == null) {
                    SALog.i(AnalyticsMessages.TAG, "Dead worker dropping a message: " + msg.what);
                } else {
                    handler.sendMessage(msg);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void runMessageOnce(Message msg, long delay) {
            synchronized (this.mHandlerLock) {
                Handler handler = this.mHandler;
                if (handler == null) {
                    SALog.i(AnalyticsMessages.TAG, "Dead worker dropping a message: " + msg.what);
                } else if (!handler.hasMessages(msg.what)) {
                    this.mHandler.sendMessageDelayed(msg, delay);
                }
            }
        }

        public class AnalyticsMessageHandler extends Handler {
            AnalyticsMessageHandler(Looper looper) {
                super(looper);
            }

            public void handleMessage(Message msg) {
                try {
                    int i = msg.what;
                    if (i == 3) {
                        AnalyticsMessages.this.sendData();
                    } else if (i == 4) {
                        try {
                            AnalyticsMessages.this.mDbAdapter.deleteAllEvents();
                        } catch (Exception e) {
                            SALog.printStackTrace(e);
                        }
                    } else if (i == 5) {
                        AnalyticsMessages.this.flushScheduled();
                        AnalyticsMessages.this.sendData();
                    } else {
                        SALog.i(AnalyticsMessages.TAG, "Unexpected message received by SensorsData worker: " + msg);
                    }
                } catch (RuntimeException e2) {
                    SALog.i(AnalyticsMessages.TAG, "Worker threw an unhandled exception", e2);
                }
            }
        }
    }
}

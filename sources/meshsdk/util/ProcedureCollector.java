package meshsdk.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.log.elk.a;
import com.leedarson.log.reporter.b;
import com.leedarson.serviceimpl.reporters.c;
import io.netty.util.internal.StringUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import meshsdk.MeshLog;
import meshsdk.MeshLogNew;
import meshsdk.SIGMesh;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProcedureCollector {
    public static final String FUNC_MESH_PROVISION = MeshConstants.TRACE_ID_ADD_DEVICES;
    public static final String FUNC_Mesh_OTA = MeshConstants.TRACE_ID_OTA;
    private static final int NUM = 2000;
    public static String autoConnectState = MeshConstants.AC_STATE_IDLE;
    /* access modifiers changed from: private */
    public static ArrayList<LogBean> collectList = new ArrayList<>();
    /* access modifiers changed from: private */
    public static String currentFunc = "";
    private static ConcurrentHashMap<String, ProvisionCollector> deviceCollectorMap = new ConcurrentHashMap<>();
    private static boolean enable = false;
    private static ReportHandler handler;
    private static String lastReportState;
    private static int level;
    public static long mainTid;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static long startAddDevicesTime = 0;

    public static void setProvisionCollectEnable(String mac, boolean enable2) {
        if (!deviceCollectorMap.containsKey(mac)) {
            deviceCollectorMap.put(mac, new ProvisionCollector(mac));
        }
        deviceCollectorMap.get(mac).setEnable(enable2);
    }

    public static int getLevel() {
        return level;
    }

    public static void startCollect(String func) {
        startCollect(func, 0);
    }

    public static void startCollect(String func, int le) {
        level = le;
        collectList.clear();
        setEnable(true, false, func);
        if (func.equals(MeshConstants.TRACE_ID_AUTO_CONNECT)) {
            if (handler == null) {
                handler = new ReportHandler(Looper.getMainLooper());
            }
            if (!handler.hasMessages(0)) {
                MeshLog.i("开启120秒检测自动连接流程");
                handler.sendEmptyMessageDelayed(0, 120000);
            }
            c.e();
        }
    }

    public static String getRunningFunction() {
        if (enable) {
            return currentFunc;
        }
        return MeshConstants.AC_STATE_IDLE;
    }

    public static class ReportHandler extends Handler {
        public ReportHandler(@NonNull Looper looper) {
            super(looper);
        }

        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                ProcedureCollector.endCollectThenReport(MeshConstants.TRACE_ID_AUTO_CONNECT);
                c.g(true, "连接mesh网络超时");
            }
        }
    }

    public static void endCollectAndClear(String mac, String func) {
        if (deviceCollectorMap.containsKey(mac)) {
            deviceCollectorMap.get(mac).endCollectAndClear();
            deviceCollectorMap.remove(mac);
        }
    }

    public static void endCollectAndClear(String func) {
        setEnable(false, false, func);
    }

    public static void endCollectThenReport(String mac, String func) {
        if (deviceCollectorMap.containsKey(mac)) {
            deviceCollectorMap.get(mac).endCollectThenReport();
            deviceCollectorMap.remove(mac);
        }
    }

    public static void endCollectThenReport(String func) {
        setEnable(false, true, func);
    }

    public static void removeTimeoutMessage() {
        ReportHandler reportHandler = handler;
        if (reportHandler != null) {
            reportHandler.removeMessages(0);
        }
    }

    public static synchronized void setEnable(boolean b, boolean report, String func) {
        synchronized (ProcedureCollector.class) {
            enable = b;
            currentFunc = func;
            if (!b) {
                if (func.equals(MeshConstants.TRACE_ID_AUTO_CONNECT)) {
                    MeshLog.i("移除自动连接超时检测任务");
                    removeTimeoutMessage();
                }
                if (!report) {
                    clear();
                } else if (!func.equals(MeshConstants.TRACE_ID_AUTO_CONNECT)) {
                    reportELK(1);
                } else if (!autoConnectState.equals(lastReportState)) {
                    lastReportState = autoConnectState;
                    reportELK(1);
                } else {
                    MeshLog.e("autoConnect失败原因与上次相同，不重复上报");
                    clear();
                }
            }
        }
    }

    public static void addLog(String s, String tag, long threadId) {
        try {
            if (enable && !currentFunc.equals(FUNC_MESH_PROVISION)) {
                try {
                    collectList.add(createBean(s, tag, threadId));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (String str : deviceCollectorMap.keySet()) {
                ProvisionCollector collector = deviceCollectorMap.get(str);
                if (collector.isEnable()) {
                    collector.addLog(s, tag, threadId);
                }
            }
        } catch (Exception e2) {
            MeshLogNew.e("ProcedureCollector addLog error:" + e2.getMessage());
        }
    }

    public static LogBean createBean(String s, String tag, long threadId) {
        if (mainTid == 0) {
            mainTid = SIGMesh.getInstance().getContext().getApplicationContext().getMainLooper().getThread().getId();
        }
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            mainTid = threadId;
        }
        return new LogBean(sdf.format(new Date()), s, threadId, tag);
    }

    public static void clear() {
        collectList.clear();
        currentFunc = "";
        level = 0;
    }

    public static void reportELK(final int type) {
        MeshLog.e("*****  reportELK  ***** func:" + currentFunc + ",size:" + collectList.size());
        SIGMesh.getInstance().executorTask(new Runnable() {
            public void run() {
                int end;
                int start;
                int count = ProcedureCollector.collectList.size() % 2000 == 0 ? ProcedureCollector.collectList.size() / 2000 : (ProcedureCollector.collectList.size() / 2000) + 1;
                for (int i = 0; i < count; i++) {
                    if (i == count - 1) {
                        start = i * 2000;
                        end = ProcedureCollector.collectList.size() - (i * 2000);
                    } else {
                        start = i * 2000;
                        end = (i + 1) * 2000;
                    }
                    JSONArray array = new JSONArray();
                    for (int k = start; k < end; k++) {
                        StringBuffer sb = new StringBuffer();
                        try {
                            LogBean bean = (LogBean) ProcedureCollector.collectList.get(k);
                            sb.append(bean.time);
                            sb.append(" ");
                            sb.append(ProcedureCollector.mainTid);
                            sb.append("/");
                            sb.append(bean.threadId);
                            sb.append(" ");
                            sb.append(" ");
                            sb.append("/TAG:");
                            sb.append(!TextUtils.isEmpty(bean.tag) ? bean.tag : "");
                            sb.append(" ");
                            sb.append(bean.content);
                            array.put((Object) sb.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    final CountDownLatch countDownLatch = new CountDownLatch(count);
                    a builder = a.y(this).x(ProcedureCollector.currentFunc).o(ProcedureCollector.collectList.size() == 1 ? "info" : "silly").t("LdsBleMesh").p(ProcedureCollector.currentFunc).r(array).b(type);
                    if (ProcedureCollector.currentFunc.equals(MeshConstants.TRACE_ID_AUTO_CONNECT) && ProcedureCollector.collectList.size() > 1) {
                        builder.p("失败阶段:" + ProcedureCollector.autoConnectState);
                        builder.o("silly");
                    }
                    if (type == 1) {
                        ((b) builder.a()).k(new b.c() {
                            public void onSuccess() {
                                countDownLatch.countDown();
                            }

                            public void onFail() {
                                countDownLatch.countDown();
                            }
                        });
                        try {
                            countDownLatch.await(10, TimeUnit.SECONDS);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                    } else {
                        builder.a().b();
                    }
                }
                ProcedureCollector.clear();
            }
        });
    }

    public static void addAndReportELK(String content, String tag, long threadId, String func) {
        collectList.add(createBean(content, tag, threadId));
        currentFunc = func;
        reportELK(8);
    }

    public static class LogBean {
        String content;
        String tag;
        long threadId;
        String time;

        public String toString() {
            return "{" + "\"time\":\"" + this.time + StringUtil.DOUBLE_QUOTE + ",\"tag\":\"" + this.tag + StringUtil.DOUBLE_QUOTE + ",\"threadId\":" + this.threadId + ",\"content\":\"" + this.content + StringUtil.DOUBLE_QUOTE + '}';
        }

        public JSONObject toJson() {
            JSONObject object = new JSONObject();
            try {
                JSONObject put = object.put("time", (Object) this.time).put(Progress.TAG, (Object) this.tag);
                put.put("tid", (Object) ProcedureCollector.mainTid + "/" + this.threadId).put(FirebaseAnalytics.Param.CONTENT, (Object) this.content);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return object;
        }

        public LogBean(String time2, String content2, long threadId2, String tag2) {
            this.time = time2;
            this.content = content2;
            this.threadId = threadId2;
            this.tag = tag2;
        }
    }
}

package com.didichuxing.doraemonkit.kit.performance;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.view.Choreographer;
import androidx.annotation.RequiresApi;
import com.blankj.utilcode.util.a;
import com.blankj.utilcode.util.d;
import com.blankj.utilcode.util.d0;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.config.DokitMemoryConfig;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.health.AppHealthInfoUtil;
import com.didichuxing.doraemonkit.kit.health.model.AppHealthInfo;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class PerformanceDataManager {
    private static final int FPS_SAMPLING_TIME = 1000;
    private static final int MAX_FRAME_RATE = 60;
    private static final int MSG_CPU = 1;
    private static final int MSG_MEMORY = 2;
    private static final int MSG_NET_FLOW = 4;
    private static final int NORMAL_SAMPLING_TIME = 500;
    public static final int PERFORMANCE_TYPE_CPU = 1;
    public static final int PERFORMANCE_TYPE_FPS = 3;
    public static final int PERFORMANCE_TYPE_MEMORY = 2;
    private static final String TAG = "PerformanceDataManager";
    private String cpuFileName;
    private String fpsFileName;
    private boolean mAboveAndroidO;
    private ActivityManager mActivityManager;
    private RandomAccessFile mAppStatFile;
    private Context mContext;
    /* access modifiers changed from: private */
    public long mDownBytes;
    private HandlerThread mHandlerThread;
    private Long mLastAppCpuTime;
    private float mLastCpuRate;
    private Long mLastCpuTime;
    /* access modifiers changed from: private */
    public long mLastDownBytes;
    /* access modifiers changed from: private */
    public int mLastFrameRate;
    private float mLastMemoryRate;
    /* access modifiers changed from: private */
    public long mLastUpBytes;
    /* access modifiers changed from: private */
    public Handler mMainHandler;
    private float mMaxMemory;
    /* access modifiers changed from: private */
    public Handler mNormalHandler;
    private RandomAccessFile mProcStatFile;
    private FrameRateRunnable mRateRunnable;
    /* access modifiers changed from: private */
    public long mUpBytes;
    private String memoryFileName;

    /* access modifiers changed from: private */
    public void executeCpuData() {
        if (this.mAboveAndroidO) {
            this.mLastCpuRate = getCpuDataForO();
            writeCpuDataIntoFile();
            return;
        }
        this.mLastCpuRate = getCPUData();
        writeCpuDataIntoFile();
    }

    /* access modifiers changed from: private */
    public void executeMemoryData() {
        this.mLastMemoryRate = getMemoryData();
        writeMemoryDataIntoFile();
    }

    private float getCpuDataForO() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("top -n 1");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            int cpuIndex = -1;
            while (true) {
                String readLine = reader.readLine();
                String line = readLine;
                if (readLine == null) {
                    break;
                }
                String line2 = line.trim();
                if (!TextUtils.isEmpty(line2)) {
                    int tempIndex = getCPUIndex(line2);
                    if (tempIndex != -1) {
                        cpuIndex = tempIndex;
                    } else if (!line2.startsWith(String.valueOf(Process.myPid()))) {
                        continue;
                    } else if (cpuIndex != -1) {
                        String[] param = line2.split("\\s+");
                        if (param.length > cpuIndex) {
                            String cpu = param[cpuIndex];
                            if (cpu.endsWith("%")) {
                                cpu = cpu.substring(0, cpu.lastIndexOf("%"));
                            }
                            float rate = Float.parseFloat(cpu) / ((float) Runtime.getRuntime().availableProcessors());
                            process.destroy();
                            return rate;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (process == null) {
                return 0.0f;
            }
        } catch (Throwable th) {
            if (process != null) {
                process.destroy();
            }
            throw th;
        }
        process.destroy();
        return 0.0f;
    }

    private int getCPUIndex(String line) {
        if (!line.contains("CPU")) {
            return -1;
        }
        String[] titles = line.split("\\s+");
        for (int i = 0; i < titles.length; i++) {
            if (titles[i].contains("CPU")) {
                return i;
            }
        }
        return -1;
    }

    public static class Holder {
        /* access modifiers changed from: private */
        public static PerformanceDataManager INSTANCE = new PerformanceDataManager();

        private Holder() {
        }
    }

    private PerformanceDataManager() {
        this.memoryFileName = "memory.txt";
        this.cpuFileName = "cpu.txt";
        this.fpsFileName = "fps.txt";
        this.mLastFrameRate = 60;
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mRateRunnable = new FrameRateRunnable();
    }

    public static PerformanceDataManager getInstance() {
        return Holder.INSTANCE;
    }

    public void init() {
        this.mContext = DoraemonKit.APPLICATION.getApplicationContext();
        this.mActivityManager = (ActivityManager) DoraemonKit.APPLICATION.getSystemService("activity");
        if (Build.VERSION.SDK_INT >= 26) {
            this.mAboveAndroidO = true;
        }
        if (this.mHandlerThread == null) {
            HandlerThread handlerThread = new HandlerThread("handler-thread");
            this.mHandlerThread = handlerThread;
            handlerThread.start();
        }
        if (this.mNormalHandler == null) {
            this.mNormalHandler = new Handler(this.mHandlerThread.getLooper()) {
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    int i = msg.what;
                    if (i == 1) {
                        if (d.p()) {
                            PerformanceDataManager.this.executeCpuData();
                        }
                        PerformanceDataManager.this.mNormalHandler.sendEmptyMessageDelayed(1, 500);
                    } else if (i == 2) {
                        if (d.p()) {
                            PerformanceDataManager.this.executeMemoryData();
                        }
                        PerformanceDataManager.this.mNormalHandler.sendEmptyMessageDelayed(2, 500);
                    } else if (i == 4) {
                        long unused = PerformanceDataManager.this.mLastUpBytes = NetworkManager.get().getTotalRequestSize() - PerformanceDataManager.this.mUpBytes;
                        long unused2 = PerformanceDataManager.this.mLastDownBytes = NetworkManager.get().getTotalResponseSize() - PerformanceDataManager.this.mDownBytes;
                        PerformanceDataManager.this.mNormalHandler.sendEmptyMessageDelayed(4, 500);
                    }
                }
            };
        }
    }

    private String getFilePath(Context context) {
        return context.getCacheDir() + File.separator + "doraemon/";
    }

    @RequiresApi(api = 16)
    public void startMonitorFrameInfo() {
        DokitMemoryConfig.FPS_STATUS = true;
        this.mMainHandler.postDelayed(this.mRateRunnable, 1000);
        Choreographer.getInstance().postFrameCallback(this.mRateRunnable);
    }

    @RequiresApi(api = 16)
    public void stopMonitorFrameInfo() {
        DokitMemoryConfig.FPS_STATUS = false;
        Choreographer.getInstance().removeFrameCallback(this.mRateRunnable);
        this.mMainHandler.removeCallbacks(this.mRateRunnable);
    }

    public void startMonitorCPUInfo() {
        DokitMemoryConfig.CPU_STATUS = true;
        this.mNormalHandler.sendEmptyMessageDelayed(1, 500);
    }

    public void startMonitorNetFlowInfo() {
        DokitMemoryConfig.NETWORK_STATUS = true;
        this.mNormalHandler.sendEmptyMessageDelayed(4, 500);
    }

    public void stopMonitorNetFlowInfo() {
        DokitMemoryConfig.NETWORK_STATUS = false;
        this.mNormalHandler.removeMessages(4);
    }

    public void destroy() {
        stopMonitorMemoryInfo();
        stopMonitorCPUInfo();
        stopMonitorFrameInfo();
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quit();
        }
        this.mHandlerThread = null;
        this.mNormalHandler = null;
    }

    public void stopMonitorCPUInfo() {
        DokitMemoryConfig.CPU_STATUS = false;
        this.mNormalHandler.removeMessages(1);
    }

    public void startMonitorMemoryInfo() {
        DokitMemoryConfig.RAM_STATUS = true;
        if (this.mMaxMemory == 0.0f) {
            this.mMaxMemory = (float) this.mActivityManager.getMemoryClass();
        }
        this.mNormalHandler.sendEmptyMessageDelayed(2, 500);
    }

    public void stopMonitorMemoryInfo() {
        DokitMemoryConfig.RAM_STATUS = false;
        this.mNormalHandler.removeMessages(2);
    }

    private void writeCpuDataIntoFile() {
        if (DokitConstant.APP_HEALTH_RUNNING) {
            addPerformanceDataInAppHealth(this.mLastCpuRate, 1);
        }
    }

    private void writeMemoryDataIntoFile() {
        if (DokitConstant.APP_HEALTH_RUNNING) {
            addPerformanceDataInAppHealth(this.mLastMemoryRate, 2);
        }
    }

    /* access modifiers changed from: private */
    public void writeFpsDataIntoFile() {
        if (DokitConstant.APP_HEALTH_RUNNING) {
            int i = this.mLastFrameRate;
            addPerformanceDataInAppHealth(i > 60 ? 60.0f : (float) i, 3);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x00a8 A[Catch:{ Exception -> 0x00d9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x00b5 A[Catch:{ Exception -> 0x00d9 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private float getCPUData() {
        /*
            r12 = this;
            java.lang.String r0 = " "
            java.lang.String r1 = "r"
            r2 = 0
            java.io.RandomAccessFile r3 = r12.mProcStatFile     // Catch:{ Exception -> 0x00d9 }
            if (r3 == 0) goto L_0x0019
            java.io.RandomAccessFile r4 = r12.mAppStatFile     // Catch:{ Exception -> 0x00d9 }
            if (r4 != 0) goto L_0x000e
            goto L_0x0019
        L_0x000e:
            r4 = 0
            r3.seek(r4)     // Catch:{ Exception -> 0x00d9 }
            java.io.RandomAccessFile r1 = r12.mAppStatFile     // Catch:{ Exception -> 0x00d9 }
            r1.seek(r4)     // Catch:{ Exception -> 0x00d9 }
            goto L_0x0043
        L_0x0019:
            java.io.RandomAccessFile r3 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x00d9 }
            java.lang.String r4 = "/proc/stat"
            r3.<init>(r4, r1)     // Catch:{ Exception -> 0x00d9 }
            r12.mProcStatFile = r3     // Catch:{ Exception -> 0x00d9 }
            java.io.RandomAccessFile r3 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x00d9 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00d9 }
            r4.<init>()     // Catch:{ Exception -> 0x00d9 }
            java.lang.String r5 = "/proc/"
            r4.append(r5)     // Catch:{ Exception -> 0x00d9 }
            int r5 = android.os.Process.myPid()     // Catch:{ Exception -> 0x00d9 }
            r4.append(r5)     // Catch:{ Exception -> 0x00d9 }
            java.lang.String r5 = "/stat"
            r4.append(r5)     // Catch:{ Exception -> 0x00d9 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x00d9 }
            r3.<init>(r4, r1)     // Catch:{ Exception -> 0x00d9 }
            r12.mAppStatFile = r3     // Catch:{ Exception -> 0x00d9 }
        L_0x0043:
            java.io.RandomAccessFile r1 = r12.mProcStatFile     // Catch:{ Exception -> 0x00d9 }
            java.lang.String r1 = r1.readLine()     // Catch:{ Exception -> 0x00d9 }
            java.io.RandomAccessFile r3 = r12.mAppStatFile     // Catch:{ Exception -> 0x00d9 }
            java.lang.String r3 = r3.readLine()     // Catch:{ Exception -> 0x00d9 }
            java.lang.String[] r4 = r1.split(r0)     // Catch:{ Exception -> 0x00d9 }
            java.lang.String[] r0 = r3.split(r0)     // Catch:{ Exception -> 0x00d9 }
            r5 = 2
            r5 = r4[r5]     // Catch:{ Exception -> 0x00d9 }
            long r5 = java.lang.Long.parseLong(r5)     // Catch:{ Exception -> 0x00d9 }
            r7 = 3
            r7 = r4[r7]     // Catch:{ Exception -> 0x00d9 }
            long r7 = java.lang.Long.parseLong(r7)     // Catch:{ Exception -> 0x00d9 }
            long r5 = r5 + r7
            r7 = 4
            r7 = r4[r7]     // Catch:{ Exception -> 0x00d9 }
            long r7 = java.lang.Long.parseLong(r7)     // Catch:{ Exception -> 0x00d9 }
            long r5 = r5 + r7
            r7 = 5
            r7 = r4[r7]     // Catch:{ Exception -> 0x00d9 }
            long r7 = java.lang.Long.parseLong(r7)     // Catch:{ Exception -> 0x00d9 }
            long r5 = r5 + r7
            r7 = 6
            r7 = r4[r7]     // Catch:{ Exception -> 0x00d9 }
            long r7 = java.lang.Long.parseLong(r7)     // Catch:{ Exception -> 0x00d9 }
            long r5 = r5 + r7
            r7 = 7
            r7 = r4[r7]     // Catch:{ Exception -> 0x00d9 }
            long r7 = java.lang.Long.parseLong(r7)     // Catch:{ Exception -> 0x00d9 }
            long r5 = r5 + r7
            r7 = 8
            r7 = r4[r7]     // Catch:{ Exception -> 0x00d9 }
            long r7 = java.lang.Long.parseLong(r7)     // Catch:{ Exception -> 0x00d9 }
            long r5 = r5 + r7
            r7 = 13
            r7 = r0[r7]     // Catch:{ Exception -> 0x00d9 }
            long r7 = java.lang.Long.parseLong(r7)     // Catch:{ Exception -> 0x00d9 }
            r9 = 14
            r9 = r0[r9]     // Catch:{ Exception -> 0x00d9 }
            long r9 = java.lang.Long.parseLong(r9)     // Catch:{ Exception -> 0x00d9 }
            long r7 = r7 + r9
            java.lang.Long r9 = r12.mLastCpuTime     // Catch:{ Exception -> 0x00d9 }
            if (r9 != 0) goto L_0x00b5
            java.lang.Long r9 = r12.mLastAppCpuTime     // Catch:{ Exception -> 0x00d9 }
            if (r9 != 0) goto L_0x00b5
            java.lang.Long r9 = java.lang.Long.valueOf(r5)     // Catch:{ Exception -> 0x00d9 }
            r12.mLastCpuTime = r9     // Catch:{ Exception -> 0x00d9 }
            java.lang.Long r9 = java.lang.Long.valueOf(r7)     // Catch:{ Exception -> 0x00d9 }
            r12.mLastAppCpuTime = r9     // Catch:{ Exception -> 0x00d9 }
            return r2
        L_0x00b5:
            java.lang.Long r9 = r12.mLastAppCpuTime     // Catch:{ Exception -> 0x00d9 }
            long r9 = r9.longValue()     // Catch:{ Exception -> 0x00d9 }
            long r9 = r7 - r9
            float r9 = (float) r9     // Catch:{ Exception -> 0x00d9 }
            java.lang.Long r10 = r12.mLastCpuTime     // Catch:{ Exception -> 0x00d9 }
            long r10 = r10.longValue()     // Catch:{ Exception -> 0x00d9 }
            long r10 = r5 - r10
            float r10 = (float) r10     // Catch:{ Exception -> 0x00d9 }
            float r9 = r9 / r10
            r10 = 1120403456(0x42c80000, float:100.0)
            float r2 = r9 * r10
            java.lang.Long r9 = java.lang.Long.valueOf(r5)     // Catch:{ Exception -> 0x00d9 }
            r12.mLastCpuTime = r9     // Catch:{ Exception -> 0x00d9 }
            java.lang.Long r9 = java.lang.Long.valueOf(r7)     // Catch:{ Exception -> 0x00d9 }
            r12.mLastAppCpuTime = r9     // Catch:{ Exception -> 0x00d9 }
            goto L_0x00dd
        L_0x00d9:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00dd:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.performance.PerformanceDataManager.getCPUData():float");
    }

    private float getMemoryData() {
        Debug.MemoryInfo memInfo = null;
        try {
            if (Build.VERSION.SDK_INT > 28) {
                memInfo = new Debug.MemoryInfo();
                Debug.getMemoryInfo(memInfo);
            } else {
                Debug.MemoryInfo[] memInfos = this.mActivityManager.getProcessMemoryInfo(new int[]{Process.myPid()});
                if (memInfos != null && memInfos.length > 0) {
                    memInfo = memInfos[0];
                }
            }
            int totalPss = memInfo.getTotalPss();
            if (totalPss >= 0) {
                return ((float) totalPss) / 1024.0f;
            }
            return 0.0f;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    private float parseMemoryData(String data) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data.getBytes())));
        while (true) {
            String readLine = bufferedReader.readLine();
            String line = readLine;
            if (readLine == null) {
                return 0.0f;
            }
            String line2 = line.trim();
            if (line2.contains("Permission Denial")) {
                return 0.0f;
            }
            String[] lineItems = line2.split("\\s+");
            if (lineItems != null && lineItems.length > 1) {
                String result = lineItems[0];
                bufferedReader.close();
                if (!TextUtils.isEmpty(result) && result.contains("K:")) {
                    result = result.replace("K:", "");
                    if (result.contains(",")) {
                        result = result.replace(",", ".");
                    }
                }
                return Float.parseFloat(result) / 1024.0f;
            }
        }
    }

    private float parseCPUData(String data) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data.getBytes())));
        while (true) {
            String readLine = bufferedReader.readLine();
            String line = readLine;
            if (readLine == null) {
                return 0.0f;
            }
            String line2 = line.trim();
            if (line2.contains("Permission Denial")) {
                return 0.0f;
            }
            String[] lineItems = line2.split("\\s+");
            if (lineItems != null && lineItems.length > 1) {
                bufferedReader.close();
                return Float.parseFloat(lineItems[0].replace("%", ""));
            }
        }
    }

    public String getCpuFilePath() {
        return getFilePath(this.mContext) + this.cpuFileName;
    }

    public String getMemoryFilePath() {
        return getFilePath(this.mContext) + this.memoryFileName;
    }

    public String getFpsFilePath() {
        return getFilePath(this.mContext) + this.fpsFileName;
    }

    public long getLastFrameRate() {
        return (long) this.mLastFrameRate;
    }

    public float getLastCpuRate() {
        return this.mLastCpuRate;
    }

    public float getLastMemoryInfo() {
        return this.mLastMemoryRate;
    }

    public float getMaxMemory() {
        return this.mMaxMemory;
    }

    public class FrameRateRunnable implements Runnable, Choreographer.FrameCallback {
        private int totalFramesPerSecond;

        private FrameRateRunnable() {
        }

        public void run() {
            int unused = PerformanceDataManager.this.mLastFrameRate = this.totalFramesPerSecond;
            if (PerformanceDataManager.this.mLastFrameRate > 60) {
                int unused2 = PerformanceDataManager.this.mLastFrameRate = 60;
            }
            if (d.p()) {
                PerformanceDataManager.this.writeFpsDataIntoFile();
            }
            this.totalFramesPerSecond = 0;
            PerformanceDataManager.this.mMainHandler.postDelayed(this, 1000);
        }

        public void doFrame(long frameTimeNanos) {
            this.totalFramesPerSecond++;
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    public long getLastUpBytes() {
        return this.mLastUpBytes;
    }

    public long getLastDownBytes() {
        return this.mLastDownBytes;
    }

    private synchronized void addPerformanceDataInAppHealth(float performanceValue, int performanceType) {
        try {
            AppHealthInfo.DataBean.PerformanceBean lastPerformanceInfo = AppHealthInfoUtil.getInstance().getLastPerformanceInfo(performanceType);
            if (lastPerformanceInfo == null) {
                AppHealthInfo.DataBean.PerformanceBean performanceBean = new AppHealthInfo.DataBean.PerformanceBean();
                List<AppHealthInfo.DataBean.PerformanceBean.ValuesBean> valuesBeans = new ArrayList<>();
                valuesBeans.add(new AppHealthInfo.DataBean.PerformanceBean.ValuesBean("" + d0.b(), "" + performanceValue));
                performanceBean.setPage(a.b().getClass().getCanonicalName());
                performanceBean.setPageKey(a.b().toString());
                performanceBean.setValues(valuesBeans);
                if (performanceType == 1) {
                    AppHealthInfoUtil.getInstance().addCPUInfo(performanceBean);
                } else if (performanceType == 2) {
                    AppHealthInfoUtil.getInstance().addMemoryInfo(performanceBean);
                } else {
                    AppHealthInfoUtil.getInstance().addFPSInfo(performanceBean);
                }
            } else if (lastPerformanceInfo.getPageKey().equals(a.b().toString())) {
                List<AppHealthInfo.DataBean.PerformanceBean.ValuesBean> valuesBeans2 = lastPerformanceInfo.getValues();
                if (valuesBeans2.size() < 40) {
                    valuesBeans2.add(new AppHealthInfo.DataBean.PerformanceBean.ValuesBean("" + d0.b(), "" + performanceValue));
                }
            } else {
                int valueSize = lastPerformanceInfo.getValues().size();
                if (performanceType == 1 && valueSize < 20) {
                    AppHealthInfoUtil.getInstance().removeLastPerformanceInfo(performanceType);
                } else if (performanceType == 2 && valueSize < 20) {
                    AppHealthInfoUtil.getInstance().removeLastPerformanceInfo(performanceType);
                } else if (performanceType == 3 && valueSize < 10) {
                    AppHealthInfoUtil.getInstance().removeLastPerformanceInfo(performanceType);
                }
                AppHealthInfo.DataBean.PerformanceBean performanceBean2 = new AppHealthInfo.DataBean.PerformanceBean();
                List<AppHealthInfo.DataBean.PerformanceBean.ValuesBean> newValuesBeans = new ArrayList<>();
                newValuesBeans.add(new AppHealthInfo.DataBean.PerformanceBean.ValuesBean("" + d0.b(), "" + performanceValue));
                performanceBean2.setPage(a.b().getClass().getCanonicalName());
                performanceBean2.setPageKey(a.b().toString());
                performanceBean2.setValues(newValuesBeans);
                if (performanceType == 1) {
                    AppHealthInfoUtil.getInstance().addCPUInfo(performanceBean2);
                } else if (performanceType == 2) {
                    AppHealthInfoUtil.getInstance().addMemoryInfo(performanceBean2);
                } else {
                    AppHealthInfoUtil.getInstance().addFPSInfo(performanceBean2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }
}

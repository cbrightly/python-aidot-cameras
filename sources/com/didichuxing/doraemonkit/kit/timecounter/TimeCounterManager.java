package com.didichuxing.doraemonkit.kit.timecounter;

import android.os.Looper;
import android.util.Printer;
import com.blankj.utilcode.util.k;
import com.didichuxing.doraemonkit.aop.DokitPluginConfig;
import com.didichuxing.doraemonkit.aop.method_stack.MethodStackUtil;
import com.didichuxing.doraemonkit.kit.core.DokitIntent;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.kit.health.AppHealthInfoUtil;
import com.didichuxing.doraemonkit.kit.methodtrace.AppHealthMethodCostBean;
import com.didichuxing.doraemonkit.kit.methodtrace.AppHealthMethodCostBeanWrap;
import com.didichuxing.doraemonkit.kit.timecounter.bean.CounterInfo;
import com.didichuxing.doraemonkit.kit.timecounter.counter.ActivityCounter;
import com.didichuxing.doraemonkit.kit.timecounter.counter.AppCounter;
import java.util.ArrayList;
import java.util.List;

public class TimeCounterManager {
    private static final String TAG = "TimeCounterManager";
    private ActivityCounter mActivityCounter = new ActivityCounter();
    private AppCounter mAppCounter = new AppCounter();
    private boolean mIsRunning;

    public static class Holder {
        /* access modifiers changed from: private */
        public static TimeCounterManager INSTANCE = new TimeCounterManager();

        private Holder() {
        }
    }

    public static TimeCounterManager get() {
        return Holder.INSTANCE;
    }

    public void onAppAttachBaseContextStart() {
        this.mAppCounter.attachStart();
    }

    public void onAppAttachBaseContextEnd() {
        this.mAppCounter.attachEnd();
    }

    public void onAppCreateStart() {
        this.mAppCounter.start();
    }

    public void onAppCreateEnd() {
        this.mAppCounter.end();
        CounterInfo counterInfo = getAppSetupInfo();
        if (DokitPluginConfig.VALUE_METHOD_STRATEGY == DokitPluginConfig.STRATEGY_STACK) {
            AppHealthInfoUtil.getInstance().setAppStartInfo(counterInfo.totalCost, MethodStackUtil.STR_APP_ATTACH_BASECONTEXT + "\n" + MethodStackUtil.STR_APP_ON_CREATE, new ArrayList());
            return;
        }
        List<AppHealthMethodCostBean> appHealthMethodCostBeans = new ArrayList<>();
        AppHealthMethodCostBean onCreate = new AppHealthMethodCostBean();
        onCreate.setCostTime(this.mAppCounter.getStartCountTime() + "ms");
        onCreate.setFunctionName("Application onCreate");
        appHealthMethodCostBeans.add(onCreate);
        AppHealthMethodCostBean onAttach = new AppHealthMethodCostBean();
        onAttach.setCostTime(this.mAppCounter.getAttachCountTime() + "ms");
        onAttach.setFunctionName("Application attachBaseContext");
        appHealthMethodCostBeans.add(onAttach);
        AppHealthMethodCostBeanWrap appHealthMethodCostBeanWrap = new AppHealthMethodCostBeanWrap();
        appHealthMethodCostBeanWrap.setTitle("App启动耗时");
        appHealthMethodCostBeanWrap.setData(appHealthMethodCostBeans);
        AppHealthInfoUtil.getInstance().setAppStartInfo(counterInfo.totalCost, k.j(appHealthMethodCostBeanWrap), new ArrayList());
    }

    public void onActivityPause() {
        this.mActivityCounter.pause();
    }

    public void onActivityPaused() {
        this.mActivityCounter.paused();
    }

    public void onActivityLaunch() {
        this.mActivityCounter.launch();
    }

    public void onActivityLaunched() {
        this.mActivityCounter.launchEnd();
    }

    public void onEnterBackground() {
        this.mActivityCounter.enterBackground();
    }

    public void start() {
        if (!this.mIsRunning) {
            this.mIsRunning = true;
            DokitViewManager.getInstance().detachToolPanel();
            DokitIntent pageIntent = new DokitIntent(TimeCounterDokitView.class);
            pageIntent.mode = 1;
            DokitViewManager.getInstance().attach(pageIntent);
        }
    }

    public boolean isRunning() {
        return this.mIsRunning;
    }

    public void stop() {
        if (this.mIsRunning) {
            Looper.getMainLooper().setMessageLogging((Printer) null);
            this.mIsRunning = false;
            DokitViewManager.getInstance().detach(TimeCounterDokitView.class.getSimpleName());
        }
    }

    public List<CounterInfo> getHistory() {
        return this.mActivityCounter.getHistory();
    }

    public CounterInfo getAppSetupInfo() {
        return this.mAppCounter.getAppSetupInfo();
    }
}

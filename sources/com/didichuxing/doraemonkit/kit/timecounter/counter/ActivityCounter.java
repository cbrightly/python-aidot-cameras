package com.didichuxing.doraemonkit.kit.timecounter.counter;

import android.app.Activity;
import com.blankj.utilcode.util.a;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.kit.health.AppHealthInfoUtil;
import com.didichuxing.doraemonkit.kit.health.model.AppHealthInfo;
import com.didichuxing.doraemonkit.kit.timecounter.TimeCounterDokitView;
import com.didichuxing.doraemonkit.kit.timecounter.bean.CounterInfo;
import java.util.ArrayList;
import java.util.List;

public class ActivityCounter {
    private static final String TAG = "ActivityCounter";
    private List<CounterInfo> mCounterInfos = new ArrayList();
    private String mCurrentActivity;
    private long mLaunchCostTime;
    private long mLaunchStartTime;
    private long mOtherCostTime;
    private long mPauseCostTime;
    private String mPreviousActivity;
    private long mRenderCostTime;
    private long mRenderStartTime;
    private long mStartTime;
    private long mTotalCostTime;

    public void pause() {
        this.mStartTime = System.currentTimeMillis();
        this.mPauseCostTime = 0;
        this.mRenderCostTime = 0;
        this.mOtherCostTime = 0;
        this.mLaunchCostTime = 0;
        this.mLaunchStartTime = 0;
        this.mTotalCostTime = 0;
        this.mPreviousActivity = null;
        Activity activity = a.b();
        if (activity != null) {
            this.mPreviousActivity = activity.getClass().getSimpleName();
        }
    }

    public void paused() {
        this.mPauseCostTime = System.currentTimeMillis() - this.mStartTime;
    }

    public void launch() {
        if (this.mStartTime == 0) {
            this.mStartTime = System.currentTimeMillis();
            this.mPauseCostTime = 0;
            this.mRenderCostTime = 0;
            this.mOtherCostTime = 0;
            this.mLaunchCostTime = 0;
            this.mLaunchStartTime = 0;
            this.mTotalCostTime = 0;
        }
        this.mLaunchStartTime = System.currentTimeMillis();
        this.mLaunchCostTime = 0;
    }

    public void launchEnd() {
        this.mLaunchCostTime = System.currentTimeMillis() - this.mLaunchStartTime;
        render();
    }

    public void render() {
        this.mRenderStartTime = System.currentTimeMillis();
        final Activity activity = a.b();
        if (activity == null || activity.getWindow() == null) {
            renderEnd();
            return;
        }
        this.mCurrentActivity = activity.getClass().getSimpleName();
        activity.getWindow().getDecorView().post(new Runnable() {
            public void run() {
                activity.getWindow().getDecorView().post(new Runnable() {
                    public void run() {
                        ActivityCounter.this.renderEnd();
                    }
                });
            }
        });
    }

    public void enterBackground() {
        this.mStartTime = 0;
    }

    /* access modifiers changed from: private */
    public void renderEnd() {
        this.mRenderCostTime = System.currentTimeMillis() - this.mRenderStartTime;
        long currentTimeMillis = System.currentTimeMillis() - this.mStartTime;
        this.mTotalCostTime = currentTimeMillis;
        this.mOtherCostTime = ((currentTimeMillis - this.mRenderCostTime) - this.mPauseCostTime) - this.mLaunchCostTime;
        print();
    }

    private void print() {
        CounterInfo counterInfo = new CounterInfo();
        counterInfo.time = System.currentTimeMillis();
        counterInfo.type = 1;
        counterInfo.title = this.mPreviousActivity + " -> " + this.mCurrentActivity;
        counterInfo.launchCost = this.mLaunchCostTime;
        counterInfo.pauseCost = this.mPauseCostTime;
        counterInfo.renderCost = this.mRenderCostTime;
        counterInfo.totalCost = this.mTotalCostTime;
        counterInfo.otherCost = this.mOtherCostTime;
        try {
            if (DokitConstant.APP_HEALTH_RUNNING && !a.b().getClass().getCanonicalName().equals("com.didichuxing.doraemonkit.kit.base.UniversalActivity")) {
                AppHealthInfo.DataBean.PageLoadBean pageLoadBean = new AppHealthInfo.DataBean.PageLoadBean();
                pageLoadBean.setPage(a.b().getClass().getCanonicalName());
                pageLoadBean.setTime("" + counterInfo.totalCost);
                pageLoadBean.setTrace(counterInfo.title);
                AppHealthInfoUtil.getInstance().addPageLoadInfo(pageLoadBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mCounterInfos.add(counterInfo);
        TimeCounterDokitView dokitView = (TimeCounterDokitView) DokitViewManager.getInstance().getDokitView(a.b(), TimeCounterDokitView.class.getSimpleName());
        if (dokitView != null) {
            dokitView.showInfo(counterInfo);
        }
    }

    public List<CounterInfo> getHistory() {
        return this.mCounterInfos;
    }
}

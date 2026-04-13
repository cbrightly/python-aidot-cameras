package com.didichuxing.doraemonkit.kit.performance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.blankj.utilcode.util.a;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.DokitMemoryConfig;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitIntent;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.kit.performance.datasource.DataSourceFactory;
import com.didichuxing.doraemonkit.kit.performance.datasource.IDataSource;
import com.didichuxing.doraemonkit.kit.performance.widget.LineChart;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class PerformanceDokitView extends AbsDokitView implements PerformanceCloseListener {
    static final int DEFAULT_REFRESH_INTERVAL = 1000;
    FrameLayout mFlWrap0;
    FrameLayout mFlWrap1;
    FrameLayout mFlWrap2;
    FrameLayout mFlWrap3;
    ImageView mIvClose0;
    ImageView mIvClose1;
    ImageView mIvClose2;
    ImageView mIvClose3;
    LineChart mLineChart0;
    LineChart mLineChart1;
    LineChart mLineChart2;
    LineChart mLineChart3;
    PerformanceCloseDokitView mPerformanceCloseDokitView;
    private PerformanceFragmentCloseListener mPerformanceFragmentCloseListener;
    LinearLayout mPerformanceWrap;

    /* access modifiers changed from: package-private */
    public void addPerformanceFragmentCloseListener(PerformanceFragmentCloseListener listener) {
        this.mPerformanceFragmentCloseListener = listener;
    }

    /* access modifiers changed from: package-private */
    public void removePerformanceFragmentCloseListener(PerformanceFragmentCloseListener listener) {
        PerformanceFragmentCloseListener performanceFragmentCloseListener = this.mPerformanceFragmentCloseListener;
        if (performanceFragmentCloseListener != null && performanceFragmentCloseListener == listener) {
            this.mPerformanceFragmentCloseListener = null;
        }
    }

    public void onCreate(Context context) {
    }

    public View onCreateView(Context context, FrameLayout rootView) {
        return LayoutInflater.from(context).inflate(R.layout.dk_performance_wrap, rootView, false);
    }

    /* access modifiers changed from: package-private */
    public void addItem(int performanceType, String title, int interval) {
        PerformanceCloseDokitView performanceCloseDokitView;
        if (this.mPerformanceWrap != null) {
            int needOperateViewIndex = -1;
            int index = 0;
            while (true) {
                if (index >= this.mPerformanceWrap.getChildCount()) {
                    break;
                } else if (this.mPerformanceWrap.getChildAt(index).getVisibility() == 8) {
                    needOperateViewIndex = index;
                    break;
                } else {
                    index++;
                }
            }
            if (needOperateViewIndex != -1) {
                FrameLayout needOperateViewWrap = (FrameLayout) this.mPerformanceWrap.getChildAt(needOperateViewIndex);
                needOperateViewWrap.setVisibility(0);
                LineChart needOperateLineChart = (LineChart) needOperateViewWrap.findViewWithTag("lineChart");
                IDataSource dataSource = DataSourceFactory.createDataSource(performanceType);
                needOperateLineChart.setPerformanceType(performanceType);
                needOperateLineChart.setTitle(title);
                needOperateLineChart.setInterval(interval);
                needOperateLineChart.setDataSource(dataSource);
                needOperateLineChart.startMove();
                if (!isNormalMode() && (performanceCloseDokitView = this.mPerformanceCloseDokitView) != null) {
                    performanceCloseDokitView.addItem(needOperateViewIndex, performanceType);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void removeItem(int performanceType) {
        PerformanceCloseDokitView performanceCloseDokitView;
        if (this.mPerformanceWrap != null) {
            int needOperateViewIndex = -1;
            int index = 0;
            while (true) {
                if (index < this.mPerformanceWrap.getChildCount()) {
                    if (this.mPerformanceWrap.getChildAt(index).getVisibility() != 8 && ((LineChart) this.mPerformanceWrap.getChildAt(index).findViewWithTag("lineChart")).getPerformanceType() == performanceType) {
                        needOperateViewIndex = index;
                        break;
                    }
                    index++;
                } else {
                    break;
                }
            }
            if (needOperateViewIndex != -1) {
                FrameLayout frameLayout = (FrameLayout) this.mPerformanceWrap.getChildAt(needOperateViewIndex);
                frameLayout.setVisibility(8);
                LineChart needOperateLineChart = (LineChart) frameLayout.findViewWithTag("lineChart");
                needOperateLineChart.stopMove();
                needOperateLineChart.setPerformanceType(-1);
                switch (performanceType) {
                    case 1:
                        DokitMemoryConfig.NETWORK_STATUS = false;
                        break;
                    case 2:
                        DokitMemoryConfig.CPU_STATUS = false;
                        break;
                    case 3:
                        DokitMemoryConfig.RAM_STATUS = false;
                        break;
                    case 4:
                        DokitMemoryConfig.FPS_STATUS = false;
                        break;
                }
                if (!isNormalMode() && (performanceCloseDokitView = this.mPerformanceCloseDokitView) != null) {
                    performanceCloseDokitView.removeItem(needOperateViewIndex);
                }
            }
        }
    }

    public void onViewCreated(FrameLayout rootView) {
        this.mPerformanceWrap = (LinearLayout) findViewById(R.id.ll_performance_wrap);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fl_chart0);
        this.mFlWrap0 = frameLayout;
        frameLayout.setVisibility(8);
        FrameLayout frameLayout2 = (FrameLayout) findViewById(R.id.fl_chart1);
        this.mFlWrap1 = frameLayout2;
        frameLayout2.setVisibility(8);
        FrameLayout frameLayout3 = (FrameLayout) findViewById(R.id.fl_chart2);
        this.mFlWrap2 = frameLayout3;
        frameLayout3.setVisibility(8);
        FrameLayout frameLayout4 = (FrameLayout) findViewById(R.id.fl_chart3);
        this.mFlWrap3 = frameLayout4;
        frameLayout4.setVisibility(8);
        this.mLineChart0 = (LineChart) findViewById(R.id.linechart0);
        this.mLineChart1 = (LineChart) findViewById(R.id.linechart1);
        this.mLineChart2 = (LineChart) findViewById(R.id.linechart2);
        this.mLineChart3 = (LineChart) findViewById(R.id.linechart3);
        this.mIvClose0 = (ImageView) findViewById(R.id.iv_close0);
        this.mIvClose1 = (ImageView) findViewById(R.id.iv_close1);
        this.mIvClose2 = (ImageView) findViewById(R.id.iv_close2);
        this.mIvClose3 = (ImageView) findViewById(R.id.iv_close3);
        setDokitViewNotResponseTouchEvent(getRootView());
        setDokitViewNotResponseTouchEvent(this.mLineChart0);
        setDokitViewNotResponseTouchEvent(this.mLineChart1);
        setDokitViewNotResponseTouchEvent(this.mLineChart2);
        setDokitViewNotResponseTouchEvent(this.mLineChart3);
        if (isNormalMode()) {
            this.mIvClose0.setVisibility(0);
            this.mIvClose1.setVisibility(0);
            this.mIvClose2.setVisibility(0);
            this.mIvClose3.setVisibility(0);
        } else {
            this.mIvClose0.setVisibility(8);
            this.mIvClose1.setVisibility(8);
            this.mIvClose2.setVisibility(8);
            this.mIvClose3.setVisibility(8);
        }
        this.mIvClose0.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View v) {
                PerformanceDokitView.this.onClose(((LineChart) ((FrameLayout) v.getParent()).findViewWithTag("lineChart")).getPerformanceType());
                SensorsDataAutoTrackHelper.trackViewOnClick(v);
            }
        });
        this.mIvClose1.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View v) {
                PerformanceDokitView.this.onClose(((LineChart) ((FrameLayout) v.getParent()).findViewWithTag("lineChart")).getPerformanceType());
                SensorsDataAutoTrackHelper.trackViewOnClick(v);
            }
        });
        this.mIvClose2.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View v) {
                PerformanceDokitView.this.onClose(((LineChart) ((FrameLayout) v.getParent()).findViewWithTag("lineChart")).getPerformanceType());
                SensorsDataAutoTrackHelper.trackViewOnClick(v);
            }
        });
        this.mIvClose3.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View v) {
                PerformanceDokitView.this.onClose(((LineChart) ((FrameLayout) v.getParent()).findViewWithTag("lineChart")).getPerformanceType());
                SensorsDataAutoTrackHelper.trackViewOnClick(v);
            }
        });
    }

    public void initDokitViewLayoutParams(DokitViewLayoutParams params) {
        params.flags = DokitViewLayoutParams.FLAG_NOT_FOCUSABLE_AND_NOT_TOUCHABLE;
        int i = DokitViewLayoutParams.MATCH_PARENT;
        params.width = i;
        params.height = i;
    }

    public boolean canDrag() {
        return false;
    }

    private void showSystemPerfoemanceCloseDokitView() {
        Class<PerformanceCloseDokitView> cls = PerformanceCloseDokitView.class;
        DokitViewManager.getInstance().attach(new DokitIntent(cls));
        PerformanceCloseDokitView performanceCloseDokitView = (PerformanceCloseDokitView) DokitViewManager.getInstance().getDokitView(a.b(), cls.getSimpleName());
        this.mPerformanceCloseDokitView = performanceCloseDokitView;
        if (performanceCloseDokitView != null) {
            performanceCloseDokitView.setPerformanceCloseListener(this);
        }
    }

    public void onResume() {
        super.onResume();
        if (!isNormalMode()) {
            showSystemPerfoemanceCloseDokitView();
        }
        if (isNormalMode()) {
            hideAllPerformanceView();
            for (performanceViewInfo performanceViewInfo : PerformanceDokitViewManager.singleperformanceViewInfos.values()) {
                PerformanceDokitViewManager.open(performanceViewInfo.performanceType, performanceViewInfo.title, (PerformanceFragmentCloseListener) null);
            }
        }
    }

    public void onClose(int performanceType) {
        if (performanceType != -1) {
            PerformanceFragmentCloseListener performanceFragmentCloseListener = this.mPerformanceFragmentCloseListener;
            if (performanceFragmentCloseListener != null) {
                performanceFragmentCloseListener.onClose(performanceType);
            }
            PerformanceDokitViewManager.close(performanceType, PerformanceDokitViewManager.getTitleByPerformanceType(getContext(), performanceType));
        }
    }

    public void onEnterForeground() {
        super.onEnterForeground();
        if (((FrameLayout) this.mLineChart0.getParent()).getVisibility() == 0) {
            this.mLineChart0.startMove();
        }
        if (((FrameLayout) this.mLineChart1.getParent()).getVisibility() == 0) {
            this.mLineChart1.startMove();
        }
        if (((FrameLayout) this.mLineChart2.getParent()).getVisibility() == 0) {
            this.mLineChart2.startMove();
        }
        if (((FrameLayout) this.mLineChart3.getParent()).getVisibility() == 0) {
            this.mLineChart3.startMove();
        }
    }

    public void onEnterBackground() {
        super.onEnterBackground();
        this.mLineChart0.stopMove();
        this.mLineChart1.stopMove();
        this.mLineChart2.stopMove();
        this.mLineChart3.stopMove();
    }

    public void onDestroy() {
        super.onDestroy();
        this.mPerformanceFragmentCloseListener = null;
        this.mLineChart0.stopMove();
        this.mLineChart0 = null;
        this.mLineChart1.stopMove();
        this.mLineChart1 = null;
        this.mLineChart2.stopMove();
        this.mLineChart2 = null;
        this.mLineChart3.stopMove();
        this.mLineChart3 = null;
    }

    private void hideAllPerformanceView() {
        if (isNormalMode()) {
            this.mFlWrap0.setVisibility(8);
            this.mFlWrap1.setVisibility(8);
            this.mFlWrap2.setVisibility(8);
            this.mFlWrap3.setVisibility(8);
        }
    }
}

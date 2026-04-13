package com.didichuxing.doraemonkit.kit.layoutborder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import com.blankj.utilcode.util.a;
import com.blankj.utilcode.util.e0;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.LayoutBorderConfig;
import com.didichuxing.doraemonkit.datapick.DataPickManager;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.kit.core.UniversalActivity;
import com.didichuxing.doraemonkit.util.LifecycleListenerUtil;
import com.didichuxing.doraemonkit.util.LogHelper;
import com.didichuxing.doraemonkit.util.UIUtils;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class LayoutLevelDokitView extends AbsDokitView {
    private static final String TAG = "LayoutLevelDokitView";
    private View mClose;
    /* access modifiers changed from: private */
    public boolean mIsCheck;
    private LifecycleListenerUtil.LifecycleListener mLifecycleListener = new LifecycleListenerUtil.LifecycleListener() {
        public void onActivityResumed(Activity activity) {
            LayoutLevelDokitView.this.resolveActivity(activity);
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onFragmentAttached(Fragment f) {
        }

        public void onFragmentDetached(Fragment f) {
        }
    };
    /* access modifiers changed from: private */
    public ScalpelFrameLayout mScalpelFrameLayout;
    private CheckBox mSwitchButton;

    /* access modifiers changed from: private */
    public void resolveActivity(Activity activity) {
        Window window;
        ViewGroup appContentView;
        if (activity != null && !(activity instanceof UniversalActivity) && (window = activity.getWindow()) != null) {
            if (isNormalMode()) {
                appContentView = (ViewGroup) UIUtils.getDokitAppContentView(activity);
            } else {
                appContentView = (ViewGroup) window.getDecorView();
            }
            if (appContentView == null) {
                e0.n("当前根布局功能不支持");
            } else if (appContentView.toString().contains("SwipeBackLayout")) {
                LogHelper.i(TAG, "普通模式下布局层级功能暂不支持以SwipeBackLayout为根布局,请改用系统模式");
                e0.m("普通模式下布局层级功能暂不支持以SwipeBackLayout为根布局");
            } else {
                this.mScalpelFrameLayout = new ScalpelFrameLayout(appContentView.getContext());
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, -1);
                while (appContentView.getChildCount() != 0) {
                    View child = appContentView.getChildAt(0);
                    if (child instanceof ScalpelFrameLayout) {
                        this.mScalpelFrameLayout = (ScalpelFrameLayout) child;
                        return;
                    } else {
                        appContentView.removeView(child);
                        this.mScalpelFrameLayout.addView(child);
                    }
                }
                this.mScalpelFrameLayout.setLayerInteractionEnabled(this.mIsCheck);
                this.mScalpelFrameLayout.setLayoutParams(params);
                appContentView.addView(this.mScalpelFrameLayout);
            }
        }
    }

    public View onCreateView(Context context, FrameLayout view) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_layout_level, view, false);
    }

    public void onViewCreated(FrameLayout view) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.switch_btn);
        this.mSwitchButton = checkBox;
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SensorsDataInstrumented
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                CompoundButton compoundButton2 = compoundButton;
                if (isChecked) {
                    if (LayoutLevelDokitView.this.mScalpelFrameLayout != null) {
                        LayoutLevelDokitView.this.mScalpelFrameLayout.setLayerInteractionEnabled(true);
                    }
                    DataPickManager.getInstance().addData("dokit_sdk_ui_ck_widget_3d");
                } else if (LayoutLevelDokitView.this.mScalpelFrameLayout != null) {
                    LayoutLevelDokitView.this.mScalpelFrameLayout.setLayerInteractionEnabled(false);
                }
                boolean unused = LayoutLevelDokitView.this.mIsCheck = isChecked;
                SensorsDataAutoTrackHelper.trackViewOnClick(compoundButton);
            }
        });
        View findViewById = findViewById(R.id.close);
        this.mClose = findViewById;
        findViewById.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                if (LayoutLevelDokitView.this.mScalpelFrameLayout != null) {
                    LayoutLevelDokitView.this.mScalpelFrameLayout.setLayerInteractionEnabled(false);
                }
                LayoutBorderConfig.setLayoutLevelOpen(false);
                LayoutBorderConfig.setLayoutBorderOpen(false);
                LayoutBorderManager.getInstance().stop();
                DokitViewManager.getInstance().detach((AbsDokitView) LayoutLevelDokitView.this);
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
    }

    public void initDokitViewLayoutParams(DokitViewLayoutParams params) {
        params.gravity = 1;
        params.x = 0;
        params.y = UIUtils.getHeightPixels() - UIUtils.dp2px(125.0f);
        params.width = getScreenShortSideLength();
        params.height = DokitViewLayoutParams.WRAP_CONTENT;
    }

    public void onCreate(Context context) {
        resolveActivity(a.b());
        LifecycleListenerUtil.registerListener(this.mLifecycleListener);
    }

    public void onDestroy() {
        super.onDestroy();
        ScalpelFrameLayout scalpelFrameLayout = this.mScalpelFrameLayout;
        if (scalpelFrameLayout != null) {
            scalpelFrameLayout.setLayerInteractionEnabled(false);
            this.mScalpelFrameLayout = null;
        }
        LifecycleListenerUtil.unRegisterListener(this.mLifecycleListener);
    }
}

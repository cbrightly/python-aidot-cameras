package com.didichuxing.doraemonkit.kit.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import com.blankj.utilcode.util.a;
import com.blankj.utilcode.util.x;
import com.didichuxing.doraemonkit.config.FloatIconConfig;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.kit.core.TouchProxy;
import com.didichuxing.doraemonkit.kit.main.MainIconDokitView;
import com.didichuxing.doraemonkit.util.LogHelper;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import java.lang.ref.WeakReference;

public abstract class AbsDokitView implements DokitView, TouchProxy.OnTouchEventListener, DokitViewManager.DokitViewAttachedListener {
    private String TAG;
    private WeakReference<Activity> mAttachActivity;
    private Bundle mBundle;
    private View mChildView;
    /* access modifiers changed from: private */
    public int mDokitViewHeight;
    private DokitViewLayoutParams mDokitViewLayoutParams;
    /* access modifiers changed from: private */
    public int mDokitViewWidth;
    private FrameLayout.LayoutParams mFrameLayoutParams;
    private Handler mHandler;
    private InnerReceiver mInnerReceiver;
    /* access modifiers changed from: private */
    public LastDokitViewPosInfo mLastDokitViewPosInfo;
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;
    /* access modifiers changed from: private */
    public FrameLayout mRootView;
    private String mTag;
    public TouchProxy mTouchProxy;
    private ViewTreeObserver mViewTreeObserver;
    private WindowManager.LayoutParams mWindowLayoutParams;
    protected WindowManager mWindowManager;
    private int mode;

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode2) {
        this.mode = mode2;
    }

    public AbsDokitView() {
        this.TAG = getClass().getSimpleName();
        this.mTouchProxy = new TouchProxy(this);
        this.mWindowManager = DokitViewManager.getInstance().getWindowManager();
        this.mInnerReceiver = new InnerReceiver();
        this.mTag = this.TAG;
        this.mDokitViewWidth = 0;
        this.mDokitViewHeight = 0;
        this.mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (AbsDokitView.this.mRootView != null) {
                    AbsDokitView absDokitView = AbsDokitView.this;
                    int unused = absDokitView.mDokitViewWidth = absDokitView.mRootView.getMeasuredWidth();
                    AbsDokitView absDokitView2 = AbsDokitView.this;
                    int unused2 = absDokitView2.mDokitViewHeight = absDokitView2.mRootView.getMeasuredHeight();
                    if (AbsDokitView.this.mLastDokitViewPosInfo != null) {
                        AbsDokitView.this.mLastDokitViewPosInfo.setDokitViewWidth(AbsDokitView.this.mDokitViewWidth);
                        AbsDokitView.this.mLastDokitViewPosInfo.setDokitViewHeight(AbsDokitView.this.mDokitViewHeight);
                    }
                }
            }
        };
        this.TAG = getClass().getSimpleName();
        if (DokitViewManager.getInstance().getLastDokitViewPosInfo(this.mTag) == null) {
            this.mLastDokitViewPosInfo = new LastDokitViewPosInfo();
            DokitViewManager.getInstance().saveLastDokitViewPosInfo(this.mTag, this.mLastDokitViewPosInfo);
        } else {
            this.mLastDokitViewPosInfo = DokitViewManager.getInstance().getLastDokitViewPosInfo(this.mTag);
        }
        this.mHandler = new Handler(Looper.myLooper());
    }

    /* access modifiers changed from: package-private */
    @SuppressLint({"ClickableViewAccessibility"})
    public void performCreate(Context context) {
        try {
            onCreate(context);
            if (!isNormalMode()) {
                DokitViewManager.getInstance().addDokitViewAttachedListener(this);
            }
            if (isNormalMode()) {
                this.mRootView = new DokitFrameLayout(context);
            } else {
                this.mRootView = new DokitFrameLayout(context) {
                    public boolean dispatchKeyEvent(KeyEvent event) {
                        if (event.getAction() == 1 && AbsDokitView.this.shouldDealBackKey() && (event.getKeyCode() == 4 || event.getKeyCode() == 3)) {
                            return AbsDokitView.this.onBackPressed();
                        }
                        return super.dispatchKeyEvent(event);
                    }
                };
            }
            addViewTreeObserverListener();
            View onCreateView = onCreateView(context, this.mRootView);
            this.mChildView = onCreateView;
            this.mRootView.addView(onCreateView);
            this.mRootView.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if (AbsDokitView.this.getRootView() != null) {
                        return AbsDokitView.this.mTouchProxy.onTouchEvent(v, event);
                    }
                    return false;
                }
            });
            onViewCreated(this.mRootView);
            this.mDokitViewLayoutParams = new DokitViewLayoutParams();
            if (isNormalMode()) {
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
                this.mFrameLayoutParams = layoutParams;
                layoutParams.gravity = 51;
                this.mDokitViewLayoutParams.gravity = 51;
            } else {
                WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams();
                this.mWindowLayoutParams = layoutParams2;
                if (Build.VERSION.SDK_INT >= 26) {
                    layoutParams2.type = 2038;
                } else {
                    layoutParams2.type = 2002;
                }
                if (!shouldDealBackKey()) {
                    this.mWindowLayoutParams.flags = 8;
                    this.mDokitViewLayoutParams.flags = DokitViewLayoutParams.FLAG_NOT_FOCUSABLE;
                }
                WindowManager.LayoutParams layoutParams3 = this.mWindowLayoutParams;
                layoutParams3.format = -2;
                layoutParams3.gravity = 51;
                this.mDokitViewLayoutParams.gravity = 51;
                context.registerReceiver(this.mInnerReceiver, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
            }
            initDokitViewLayoutParams(this.mDokitViewLayoutParams);
            if (isNormalMode()) {
                onNormalLayoutParamsCreated(this.mFrameLayoutParams);
            } else {
                onSystemLayoutParamsCreated(this.mWindowLayoutParams);
            }
        } catch (Exception e) {
            String str = this.TAG;
            LogHelper.e(str, "e===>" + e.getMessage());
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public void performDestroy() {
        if (!isNormalMode()) {
            getContext().unregisterReceiver(this.mInnerReceiver);
        }
        removeViewTreeObserverListener();
        this.mHandler = null;
        this.mRootView = null;
        onDestroy();
    }

    private void addViewTreeObserverListener() {
        FrameLayout frameLayout;
        if (this.mViewTreeObserver == null && (frameLayout = this.mRootView) != null && this.mOnGlobalLayoutListener != null) {
            ViewTreeObserver viewTreeObserver = frameLayout.getViewTreeObserver();
            this.mViewTreeObserver = viewTreeObserver;
            viewTreeObserver.addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
        }
    }

    private void removeViewTreeObserverListener() {
        ViewTreeObserver viewTreeObserver = this.mViewTreeObserver;
        if (viewTreeObserver != null && this.mOnGlobalLayoutListener != null && viewTreeObserver.isAlive()) {
            this.mViewTreeObserver.removeOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
        }
    }

    private void onNormalLayoutParamsCreated(FrameLayout.LayoutParams params) {
        DokitViewLayoutParams dokitViewLayoutParams = this.mDokitViewLayoutParams;
        params.width = dokitViewLayoutParams.width;
        params.height = dokitViewLayoutParams.height;
        params.gravity = dokitViewLayoutParams.gravity;
        portraitOrLandscape(params);
    }

    private void portraitOrLandscape(FrameLayout.LayoutParams params) {
        Point point = DokitViewManager.getInstance().getDokitViewPos(this.mTag);
        if (point != null) {
            if (x.e()) {
                if (this.mLastDokitViewPosInfo.isPortrait()) {
                    params.leftMargin = point.x;
                    params.topMargin = point.y;
                } else {
                    params.leftMargin = (int) (((float) point.x) * this.mLastDokitViewPosInfo.getLeftMarginPercent());
                    params.topMargin = (int) (((float) point.y) * this.mLastDokitViewPosInfo.getTopMarginPercent());
                }
            } else if (this.mLastDokitViewPosInfo.isPortrait()) {
                params.leftMargin = (int) (((float) point.x) * this.mLastDokitViewPosInfo.getLeftMarginPercent());
                params.topMargin = (int) (((float) point.y) * this.mLastDokitViewPosInfo.getTopMarginPercent());
            } else {
                params.leftMargin = point.x;
                params.topMargin = point.y;
            }
        } else if (x.e()) {
            if (this.mLastDokitViewPosInfo.isPortrait()) {
                DokitViewLayoutParams dokitViewLayoutParams = this.mDokitViewLayoutParams;
                params.leftMargin = dokitViewLayoutParams.x;
                params.topMargin = dokitViewLayoutParams.y;
            } else {
                params.leftMargin = (int) (((float) this.mDokitViewLayoutParams.x) * this.mLastDokitViewPosInfo.getLeftMarginPercent());
                params.topMargin = (int) (((float) this.mDokitViewLayoutParams.y) * this.mLastDokitViewPosInfo.getTopMarginPercent());
            }
        } else if (this.mLastDokitViewPosInfo.isPortrait()) {
            params.leftMargin = (int) (((float) this.mDokitViewLayoutParams.x) * this.mLastDokitViewPosInfo.getLeftMarginPercent());
            params.topMargin = (int) (((float) this.mDokitViewLayoutParams.y) * this.mLastDokitViewPosInfo.getTopMarginPercent());
        } else {
            DokitViewLayoutParams dokitViewLayoutParams2 = this.mDokitViewLayoutParams;
            params.leftMargin = dokitViewLayoutParams2.x;
            params.topMargin = dokitViewLayoutParams2.y;
        }
        this.mLastDokitViewPosInfo.setPortrait();
        this.mLastDokitViewPosInfo.setLeftMargin(params.leftMargin);
        this.mLastDokitViewPosInfo.setTopMargin(params.topMargin);
    }

    private void onSystemLayoutParamsCreated(WindowManager.LayoutParams params) {
        DokitViewLayoutParams dokitViewLayoutParams = this.mDokitViewLayoutParams;
        params.flags = dokitViewLayoutParams.flags;
        params.gravity = dokitViewLayoutParams.gravity;
        params.width = dokitViewLayoutParams.width;
        params.height = dokitViewLayoutParams.height;
        Point point = DokitViewManager.getInstance().getDokitViewPos(this.mTag);
        if (point != null) {
            params.x = point.x;
            params.y = point.y;
            return;
        }
        DokitViewLayoutParams dokitViewLayoutParams2 = this.mDokitViewLayoutParams;
        params.x = dokitViewLayoutParams2.x;
        params.y = dokitViewLayoutParams2.y;
    }

    public void onDestroy() {
        if (!isNormalMode()) {
            DokitViewManager.getInstance().removeDokitViewAttachedListener(this);
        }
        DokitViewManager.getInstance().removeLastDokitViewPosInfo(this.mTag);
        this.mAttachActivity = null;
    }

    public boolean canDrag() {
        return true;
    }

    public boolean onBackPressed() {
        return false;
    }

    public boolean shouldDealBackKey() {
        return false;
    }

    public void onEnterBackground() {
        FrameLayout frameLayout;
        if (!isNormalMode() && (frameLayout = this.mRootView) != null) {
            frameLayout.setVisibility(8);
        }
    }

    public void onEnterForeground() {
        FrameLayout frameLayout;
        if (!isNormalMode() && (frameLayout = this.mRootView) != null) {
            frameLayout.setVisibility(0);
        }
    }

    public void onMove(int x, int y, int dx, int dy) {
        if (canDrag()) {
            if (isNormalMode()) {
                FrameLayout.LayoutParams layoutParams = this.mFrameLayoutParams;
                layoutParams.leftMargin += dx;
                layoutParams.topMargin += dy;
                updateViewLayout(this.mTag, false);
                return;
            }
            WindowManager.LayoutParams layoutParams2 = this.mWindowLayoutParams;
            layoutParams2.x += dx;
            layoutParams2.y += dy;
            this.mWindowManager.updateViewLayout(this.mRootView, layoutParams2);
        }
    }

    public void onUp(int x, int y) {
        if (canDrag()) {
            if (this.mTag.equals(MainIconDokitView.class.getSimpleName())) {
                if (isNormalMode()) {
                    FloatIconConfig.saveLastPosX(this.mFrameLayoutParams.leftMargin);
                    FloatIconConfig.saveLastPosY(this.mFrameLayoutParams.topMargin);
                    return;
                }
                FloatIconConfig.saveLastPosX(this.mWindowLayoutParams.x);
                FloatIconConfig.saveLastPosY(this.mWindowLayoutParams.y);
            } else if (isNormalMode()) {
                DokitViewManager instance = DokitViewManager.getInstance();
                String str = this.mTag;
                FrameLayout.LayoutParams layoutParams = this.mFrameLayoutParams;
                instance.saveDokitViewPos(str, layoutParams.leftMargin, layoutParams.topMargin);
            } else {
                DokitViewManager instance2 = DokitViewManager.getInstance();
                String str2 = this.mTag;
                WindowManager.LayoutParams layoutParams2 = this.mWindowLayoutParams;
                instance2.saveDokitViewPos(str2, layoutParams2.x, layoutParams2.y);
            }
        }
    }

    public void onDown(int x, int y) {
        if (canDrag()) {
        }
    }

    public class InnerReceiver extends BroadcastReceiver {
        final String SYSTEM_DIALOG_REASON_HOME_KEY;
        final String SYSTEM_DIALOG_REASON_KEY;
        final String SYSTEM_DIALOG_REASON_RECENT_APPS;

        private InnerReceiver() {
            this.SYSTEM_DIALOG_REASON_KEY = "reason";
            this.SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
            this.SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
        }

        public void onReceive(Context context, Intent intent) {
            String reason;
            PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
            if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction()) && (reason = intent.getStringExtra("reason")) != null) {
                if (reason.equals("homekey")) {
                    AbsDokitView.this.onHomeKeyPress();
                } else if (reason.equals("recentapps")) {
                    AbsDokitView.this.onRecentAppKeyPress();
                }
            }
        }
    }

    public void onHomeKeyPress() {
    }

    public void onRecentAppKeyPress() {
    }

    public void onDokitViewAdd(AbsDokitView dokitView) {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public Context getContext() {
        FrameLayout frameLayout = this.mRootView;
        if (frameLayout != null) {
            return frameLayout.getContext();
        }
        return null;
    }

    public Resources getResources() {
        if (getContext() == null) {
            return null;
        }
        return getContext().getResources();
    }

    public String getString(@StringRes int resId) {
        if (getContext() == null) {
            return null;
        }
        return getContext().getString(resId);
    }

    public boolean isShow() {
        return this.mRootView.isShown();
    }

    /* access modifiers changed from: protected */
    public <T extends View> T findViewById(@IdRes int id) {
        return this.mRootView.findViewById(id);
    }

    public View getRootView() {
        return this.mRootView;
    }

    public FrameLayout.LayoutParams getNormalLayoutParams() {
        return this.mFrameLayoutParams;
    }

    public WindowManager.LayoutParams getSystemLayoutParams() {
        return this.mWindowLayoutParams;
    }

    public void detach() {
        DokitViewManager.getInstance().detach(this);
    }

    public void dealDecorRootView(FrameLayout decorRootView) {
        if (isNormalMode() && decorRootView != null) {
        }
    }

    public void updateViewLayout(String tag, boolean isActivityResume) {
        Class<MainIconDokitView> cls = MainIconDokitView.class;
        if (this.mRootView != null && this.mChildView != null && this.mFrameLayoutParams != null && isNormalMode()) {
            if (!isActivityResume) {
                this.mLastDokitViewPosInfo.setPortrait();
                this.mLastDokitViewPosInfo.setLeftMargin(this.mFrameLayoutParams.leftMargin);
                this.mLastDokitViewPosInfo.setTopMargin(this.mFrameLayoutParams.topMargin);
            } else if (tag.equals(cls.getSimpleName())) {
                this.mFrameLayoutParams.leftMargin = FloatIconConfig.getLastPosX();
                this.mFrameLayoutParams.topMargin = FloatIconConfig.getLastPosY();
            } else {
                Point point = DokitViewManager.getInstance().getDokitViewPos(tag);
                if (point != null) {
                    FrameLayout.LayoutParams layoutParams = this.mFrameLayoutParams;
                    layoutParams.leftMargin = point.x;
                    layoutParams.topMargin = point.y;
                }
            }
            if (tag.equals(cls.getSimpleName())) {
                FrameLayout.LayoutParams layoutParams2 = this.mFrameLayoutParams;
                int i = MainIconDokitView.FLOAT_SIZE;
                layoutParams2.width = i;
                layoutParams2.height = i;
            } else {
                FrameLayout.LayoutParams layoutParams3 = this.mFrameLayoutParams;
                layoutParams3.width = this.mDokitViewWidth;
                layoutParams3.height = this.mDokitViewHeight;
            }
            resetBorderline(this.mFrameLayoutParams);
            this.mRootView.setLayoutParams(this.mFrameLayoutParams);
        }
    }

    private void resetBorderline(FrameLayout.LayoutParams normalFrameLayoutParams) {
        if (restrictBorderline() && isNormalMode()) {
            if (normalFrameLayoutParams.topMargin <= 0) {
                normalFrameLayoutParams.topMargin = 0;
            }
            if (x.e()) {
                if (normalFrameLayoutParams.topMargin >= getScreenLongSideLength() - this.mDokitViewHeight) {
                    normalFrameLayoutParams.topMargin = getScreenLongSideLength() - this.mDokitViewHeight;
                }
            } else if (normalFrameLayoutParams.topMargin >= getScreenShortSideLength() - this.mDokitViewHeight) {
                normalFrameLayoutParams.topMargin = getScreenShortSideLength() - this.mDokitViewHeight;
            }
            if (normalFrameLayoutParams.leftMargin <= 0) {
                normalFrameLayoutParams.leftMargin = 0;
            }
            if (x.e()) {
                if (normalFrameLayoutParams.leftMargin >= getScreenShortSideLength() - this.mDokitViewWidth) {
                    normalFrameLayoutParams.leftMargin = getScreenShortSideLength() - this.mDokitViewWidth;
                }
            } else if (normalFrameLayoutParams.leftMargin >= getScreenLongSideLength() - this.mDokitViewWidth) {
                normalFrameLayoutParams.leftMargin = getScreenLongSideLength() - this.mDokitViewWidth;
            }
        }
    }

    public boolean restrictBorderline() {
        return true;
    }

    public String getTag() {
        return this.mTag;
    }

    public void setTag(String mTag2) {
        this.mTag = mTag2;
    }

    public Bundle getBundle() {
        return this.mBundle;
    }

    public void setBundle(Bundle mBundle2) {
        this.mBundle = mBundle2;
    }

    public Activity getActivity() {
        WeakReference<Activity> weakReference = this.mAttachActivity;
        if (weakReference != null) {
            return (Activity) weakReference.get();
        }
        return a.b();
    }

    public void setActivity(Activity activity) {
        this.mAttachActivity = new WeakReference<>(activity);
    }

    public void post(Runnable r) {
        this.mHandler.post(r);
    }

    public void postDelayed(Runnable r, long delayMillis) {
        this.mHandler.postDelayed(r, delayMillis);
    }

    public void setDokitViewNotResponseTouchEvent(View view) {
        if (isNormalMode()) {
            if (view != null) {
                view.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });
            }
        } else if (view != null) {
            view.setOnTouchListener((View.OnTouchListener) null);
        }
    }

    public int getScreenShortSideLength() {
        if (x.e()) {
            return x.b();
        }
        return x.a();
    }

    public int getScreenLongSideLength() {
        if (x.e()) {
            return x.a();
        }
        return x.b();
    }

    public boolean isNormalMode() {
        return DokitConstant.IS_NORMAL_FLOAT_MODE;
    }

    public void invalidate() {
        FrameLayout.LayoutParams layoutParams;
        FrameLayout frameLayout = this.mRootView;
        if (frameLayout != null && (layoutParams = this.mFrameLayoutParams) != null) {
            frameLayout.setLayoutParams(layoutParams);
        }
    }
}

package com.didichuxing.doraemonkit.widget.easyrefresh;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Scroller;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.didichuxing.doraemonkit.widget.easyrefresh.exception.ERVHRuntimeException;
import com.didichuxing.doraemonkit.widget.easyrefresh.view.SimpleLoadMoreView;
import com.didichuxing.doraemonkit.widget.easyrefresh.view.SimpleRefreshHeaderView;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class EasyRefreshLayout extends ViewGroup {
    private static final float DRAG_RATE = 1.0f;
    private static final int INVALID_POINTER = -1;
    private static long SCROLL_TO_LOADING_DURATION = 500;
    /* access modifiers changed from: private */
    public static int SCROLL_TO_REFRESH_DURATION = 250;
    /* access modifiers changed from: private */
    public static int SCROLL_TO_TOP_DURATION = 800;
    private static long SHOW_COMPLETED_TIME = 500;
    private static long SHOW_SCROLL_DOWN_DURATION = 300;
    private static final int START_POSITION = 0;
    private static final String TAG = "EsayRefreshLayout";
    private int activePointerId;
    /* access modifiers changed from: private */
    public int advanceCount;
    private Runnable autoRefreshRunnable;
    /* access modifiers changed from: private */
    public AutoScroll autoScroll;
    private View contentView;
    /* access modifiers changed from: private */
    public int currentOffsetTop;
    private Runnable delayToScrollTopRunnable;
    /* access modifiers changed from: private */
    public EasyEvent easyEvent;
    private boolean hasMeasureHeaderView;
    private boolean hasMeasureLoadMoreView;
    private boolean hasSendCancelEvent;
    private int headerViewHight;
    private float initDownX;
    private float initDownY;
    /* access modifiers changed from: private */
    public boolean isAutoRefresh;
    private boolean isBeginDragged;
    boolean isCanLoad;
    private boolean isEnablePullToRefresh;
    /* access modifiers changed from: private */
    public boolean isLoading;
    /* access modifiers changed from: private */
    public boolean isLoadingFail;
    /* access modifiers changed from: private */
    public boolean isNotMoreLoading;
    private boolean isRecycerView;
    /* access modifiers changed from: private */
    public boolean isRefreshing;
    private boolean isTouch;
    private MotionEvent lastEvent;
    private float lastMotionX;
    private float lastMotionY;
    private int lastOffsetTop;
    /* access modifiers changed from: private */
    public LoadModel loadMoreModel;
    private int loadMoreViewHeight;
    /* access modifiers changed from: private */
    public float mDistance;
    private LayoutInflater mInflater;
    /* access modifiers changed from: private */
    public View mLoadMoreView;
    /* access modifiers changed from: private */
    public RecyclerView mRecyclerView;
    private float offsetY;
    private double pull_resistance;
    private View refreshHeaderView;
    private State state;
    /* access modifiers changed from: private */
    public int totalDragDistance;
    /* access modifiers changed from: private */
    public int touchSlop;
    private float yDiff;

    public interface EasyEvent extends OnRefreshListener, LoadMoreEvent {
    }

    public interface Event {
        void complete();
    }

    public interface LoadMoreEvent {
        void onLoadMore();
    }

    public interface OnRefreshListener {
        void onRefreshing();
    }

    public EasyRefreshLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public EasyRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.pull_resistance = 2.0d;
        this.state = State.RESET;
        this.isEnablePullToRefresh = true;
        this.hasMeasureHeaderView = false;
        this.isCanLoad = false;
        this.isLoading = false;
        this.isLoadingFail = false;
        this.loadMoreModel = LoadModel.COMMON_MODEL;
        this.advanceCount = 0;
        this.delayToScrollTopRunnable = new Runnable() {
            public void run() {
                EasyRefreshLayout.this.autoScroll.scrollTo(0, EasyRefreshLayout.SCROLL_TO_TOP_DURATION);
            }
        };
        this.autoRefreshRunnable = new Runnable() {
            public void run() {
                boolean unused = EasyRefreshLayout.this.isAutoRefresh = true;
                EasyRefreshLayout.this.changeState(State.PULL);
                EasyRefreshLayout.this.autoScroll.scrollTo(EasyRefreshLayout.this.totalDragDistance, EasyRefreshLayout.SCROLL_TO_REFRESH_DURATION);
            }
        };
        initParameter(context, attrs);
    }

    private void initParameter(Context context, AttributeSet attrs) {
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setRefreshHeadView(getDefaultRefreshView());
        setLoadMoreView(getDefaultLoadMoreView());
        this.autoScroll = new AutoScroll();
    }

    public void setRefreshHeadView(View headerView) {
        View view;
        if (!(headerView == null || headerView == (view = this.refreshHeaderView))) {
            removeView(view);
        }
        if (headerView.getLayoutParams() == null) {
            headerView.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        }
        this.refreshHeaderView = headerView;
        addView(headerView);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.contentView == null) {
            initContentView();
        }
        if (this.contentView != null) {
            this.contentView.measure(View.MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824), View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824));
            measureChild(this.refreshHeaderView, widthMeasureSpec, heightMeasureSpec);
            if (!this.hasMeasureHeaderView) {
                this.hasMeasureHeaderView = true;
                int measuredHeight = this.refreshHeaderView.getMeasuredHeight();
                this.headerViewHight = measuredHeight;
                this.totalDragDistance = measuredHeight;
            }
            measureChild(this.mLoadMoreView, widthMeasureSpec, heightMeasureSpec);
            if (!this.hasMeasureLoadMoreView) {
                this.hasMeasureLoadMoreView = true;
                this.loadMoreViewHeight = this.mLoadMoreView.getMeasuredHeight();
            }
        }
    }

    private void initContentView() {
        if (this.contentView == null) {
            int i = 0;
            while (true) {
                if (i >= getChildCount()) {
                    break;
                }
                View child = getChildAt(i);
                if (child.equals(this.refreshHeaderView) || child.equals(this.mLoadMoreView)) {
                    i++;
                } else {
                    this.contentView = child;
                    if (child instanceof RecyclerView) {
                        this.isRecycerView = true;
                    } else {
                        this.isRecycerView = false;
                    }
                }
            }
        }
        if (this.isRecycerView != 0) {
            initERVH();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (getChildCount() != 0) {
            if (this.contentView == null) {
                initContentView();
            }
            if (this.contentView != null) {
                View child = this.contentView;
                int childLeft = getPaddingLeft();
                int childTop = getPaddingTop() + this.currentOffsetTop;
                int childWidth = (width - getPaddingLeft()) - getPaddingRight();
                int childHeight = (height - getPaddingTop()) - getPaddingBottom();
                child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
                int refreshViewWidth = this.refreshHeaderView.getMeasuredWidth();
                int i4 = this.currentOffsetTop;
                this.refreshHeaderView.layout((width / 2) - (refreshViewWidth / 2), (-this.headerViewHight) + i4, (width / 2) + (refreshViewWidth / 2), i4);
                int loadMoreViewWidth = this.mLoadMoreView.getMeasuredWidth();
                this.mLoadMoreView.layout((width / 2) - (loadMoreViewWidth / 2), childHeight, (width / 2) + (loadMoreViewWidth / 2), this.loadMoreViewHeight + childHeight);
                return;
            }
            throw new RuntimeException("main content of view can not be empty ");
        }
        throw new RuntimeException("child view can not be empty");
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.isLoading || this.contentView == null) {
            return super.dispatchTouchEvent(ev);
        }
        boolean canMoveHeadUp = false;
        switch (ev.getActionMasked()) {
            case 0:
                this.mDistance = 0.0f;
                this.activePointerId = ev.getPointerId(0);
                this.isTouch = true;
                this.hasSendCancelEvent = false;
                this.isBeginDragged = false;
                this.lastOffsetTop = this.currentOffsetTop;
                this.currentOffsetTop = this.contentView.getTop();
                float x = ev.getX(0);
                this.lastMotionX = x;
                this.initDownX = x;
                float y = ev.getY(0);
                this.lastMotionY = y;
                this.initDownY = y;
                this.autoScroll.stop();
                removeCallbacks(this.delayToScrollTopRunnable);
                removeCallbacks(this.autoRefreshRunnable);
                super.dispatchTouchEvent(ev);
                return true;
            case 1:
            case 3:
                if (this.currentOffsetTop > 0) {
                    finishSpinner();
                }
                this.isTouch = false;
                this.activePointerId = -1;
                break;
            case 2:
                if (this.activePointerId == -1) {
                    return super.dispatchTouchEvent(ev);
                }
                this.autoScroll.stop();
                this.lastEvent = ev;
                float x2 = ev.getX(MotionEventCompat.findPointerIndex(ev, this.activePointerId));
                float y2 = ev.getY(MotionEventCompat.findPointerIndex(ev, this.activePointerId));
                float f = y2 - this.lastMotionY;
                this.yDiff = f;
                this.mDistance += f;
                this.offsetY = f * 1.0f;
                this.lastMotionX = x2;
                this.lastMotionY = y2;
                if (Math.abs(x2 - this.lastMotionX) <= ((float) this.touchSlop)) {
                    if (!this.isBeginDragged && Math.abs(y2 - this.initDownY) > ((float) this.touchSlop)) {
                        this.isBeginDragged = true;
                    }
                    if (this.isBeginDragged) {
                        boolean isMoveHeadDown = this.offsetY > 0.0f;
                        boolean canMoveHeadDown = !canChildScrollUp();
                        boolean isMoveHeadUp = !isMoveHeadDown;
                        if (this.currentOffsetTop > 0) {
                            canMoveHeadUp = true;
                        }
                        if ((isMoveHeadDown && canMoveHeadDown) || (isMoveHeadUp && canMoveHeadUp)) {
                            moveSpinner(this.offsetY);
                            return true;
                        }
                    }
                }
                break;
            case 5:
                int pointerIndex = MotionEventCompat.getActionIndex(ev);
                if (pointerIndex >= 0) {
                    this.lastMotionX = ev.getX(pointerIndex);
                    this.lastMotionY = ev.getY(pointerIndex);
                    this.activePointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
                    break;
                } else {
                    return super.dispatchTouchEvent(ev);
                }
            case 6:
                onSecondaryPointerUp(ev);
                this.lastMotionY = ev.getY(ev.findPointerIndex(this.activePointerId));
                this.lastMotionX = ev.getX(ev.findPointerIndex(this.activePointerId));
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        int pointerIndex = MotionEventCompat.getActionIndex(ev);
        if (MotionEventCompat.getPointerId(ev, pointerIndex) == this.activePointerId) {
            int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            this.lastMotionY = ev.getY(newPointerIndex);
            this.lastMotionX = ev.getX(newPointerIndex);
            this.activePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }

    /* access modifiers changed from: private */
    public void moveSpinner(float offsetY2) {
        int offset;
        int i;
        State state2;
        if (this.isEnablePullToRefresh && (offset = Math.round(offsetY2)) != 0) {
            if (!this.hasSendCancelEvent && this.isTouch && this.currentOffsetTop > 0) {
                sendCancelEvent();
                this.hasSendCancelEvent = true;
            }
            int nextOffsetTop = Math.max(0, this.currentOffsetTop + offset);
            int offset2 = nextOffsetTop - this.currentOffsetTop;
            int i2 = this.totalDragDistance;
            float slingshotDist = (float) i2;
            float tensionSlingshotPercent = Math.max(0.0f, Math.min((float) (nextOffsetTop - i2), 2.0f * slingshotDist) / slingshotDist);
            float tensionPercent = (float) (((double) tensionSlingshotPercent) - Math.pow(((double) tensionSlingshotPercent) / this.pull_resistance, 2.0d));
            if (offset2 > 0) {
                offset2 = (int) (((float) offset2) * (1.0f - tensionPercent));
                nextOffsetTop = Math.max(0, this.currentOffsetTop + offset2);
            }
            State state3 = this.state;
            State state4 = State.RESET;
            if (state3 == state4 && this.currentOffsetTop == 0 && nextOffsetTop > 0) {
                if (this.isNotMoreLoading || this.isLoadingFail) {
                    closeLoadView();
                }
                changeState(State.PULL);
            }
            if (this.currentOffsetTop > 0 && nextOffsetTop <= 0 && ((state2 = this.state) == State.PULL || state2 == State.COMPLETE)) {
                changeState(state4);
            }
            if (this.state == State.PULL && !this.isTouch && this.currentOffsetTop > (i = this.totalDragDistance) && nextOffsetTop <= i) {
                this.autoScroll.stop();
                changeState(State.REFRESHING);
                EasyEvent easyEvent2 = this.easyEvent;
                if (easyEvent2 != null) {
                    this.isRefreshing = true;
                    easyEvent2.onRefreshing();
                }
                offset2 += this.totalDragDistance - nextOffsetTop;
            }
            setTargetOffsetTopAndBottom(offset2);
            View view = this.refreshHeaderView;
            if (view instanceof IRefreshHeader) {
                ((IRefreshHeader) view).onPositionChange((float) this.currentOffsetTop, (float) this.lastOffsetTop, (float) this.totalDragDistance, this.isTouch, this.state);
            }
        }
    }

    private void finishSpinner() {
        if (this.state == State.REFRESHING) {
            int i = this.currentOffsetTop;
            int i2 = this.totalDragDistance;
            if (i > i2) {
                this.autoScroll.scrollTo(i2, SCROLL_TO_REFRESH_DURATION);
                return;
            }
            return;
        }
        this.autoScroll.scrollTo(0, SCROLL_TO_TOP_DURATION);
    }

    private boolean canChildScrollUp() {
        if (Build.VERSION.SDK_INT >= 14) {
            return ViewCompat.canScrollVertically(this.contentView, -1);
        }
        View view = this.contentView;
        if (view instanceof AbsListView) {
            AbsListView absListView = (AbsListView) view;
            if (absListView.getChildCount() <= 0 || (absListView.getFirstVisiblePosition() <= 0 && absListView.getChildAt(0).getTop() >= absListView.getPaddingTop())) {
                return false;
            }
            return true;
        } else if (ViewCompat.canScrollVertically(view, -1) || this.contentView.getScrollY() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private void setTargetOffsetTopAndBottom(int offset) {
        if (offset != 0) {
            this.contentView.offsetTopAndBottom(offset);
            this.refreshHeaderView.offsetTopAndBottom(offset);
            this.lastOffsetTop = this.currentOffsetTop;
            this.currentOffsetTop = this.contentView.getTop();
            invalidate();
        }
    }

    private void sendCancelEvent() {
        MotionEvent motionEvent = this.lastEvent;
        if (motionEvent != null) {
            MotionEvent ev = MotionEvent.obtain(motionEvent);
            ev.setAction(3);
            super.dispatchTouchEvent(ev);
        }
    }

    /* access modifiers changed from: private */
    public void changeState(State state2) {
        this.state = state2;
        View view = this.refreshHeaderView;
        IRefreshHeader refreshHeader = view instanceof IRefreshHeader ? (IRefreshHeader) view : null;
        if (refreshHeader != null) {
            switch (AnonymousClass10.$SwitchMap$com$didichuxing$doraemonkit$widget$easyrefresh$State[state2.ordinal()]) {
                case 1:
                    refreshHeader.reset();
                    return;
                case 2:
                    refreshHeader.pull();
                    return;
                case 3:
                    refreshHeader.refreshing();
                    return;
                case 4:
                    refreshHeader.complete();
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: com.didichuxing.doraemonkit.widget.easyrefresh.EasyRefreshLayout$10  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass10 {
        static final /* synthetic */ int[] $SwitchMap$com$didichuxing$doraemonkit$widget$easyrefresh$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$com$didichuxing$doraemonkit$widget$easyrefresh$State = iArr;
            try {
                iArr[State.RESET.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$didichuxing$doraemonkit$widget$easyrefresh$State[State.PULL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$didichuxing$doraemonkit$widget$easyrefresh$State[State.REFRESHING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$didichuxing$doraemonkit$widget$easyrefresh$State[State.COMPLETE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public void refreshComplete() {
        this.isRefreshing = false;
        changeState(State.COMPLETE);
        if (this.currentOffsetTop == 0) {
            changeState(State.RESET);
        } else if (!this.isTouch) {
            postDelayed(this.delayToScrollTopRunnable, SHOW_COMPLETED_TIME);
        }
    }

    public void autoRefresh() {
        autoRefresh(500);
    }

    public void autoRefresh(long duration) {
        if (this.state == State.RESET) {
            postDelayed(this.autoRefreshRunnable, duration);
        }
    }

    public View getDefaultRefreshView() {
        return new SimpleRefreshHeaderView(getContext());
    }

    public class AutoScroll implements Runnable {
        private int lastY;
        private Scroller scroller;

        public AutoScroll() {
            this.scroller = new Scroller(EasyRefreshLayout.this.getContext());
        }

        public void run() {
            if (!(!this.scroller.computeScrollOffset() || this.scroller.isFinished())) {
                int currY = this.scroller.getCurrY();
                this.lastY = currY;
                EasyRefreshLayout.this.moveSpinner((float) (currY - this.lastY));
                EasyRefreshLayout.this.post(this);
                EasyRefreshLayout.this.onScrollFinish(false);
                return;
            }
            stop();
            EasyRefreshLayout.this.onScrollFinish(true);
        }

        public void scrollTo(int to, int duration) {
            int distance = to - EasyRefreshLayout.this.currentOffsetTop;
            stop();
            if (distance != 0) {
                this.scroller.startScroll(0, 0, 0, distance, duration);
                EasyRefreshLayout.this.post(this);
            }
        }

        /* access modifiers changed from: private */
        public void stop() {
            EasyRefreshLayout.this.removeCallbacks(this);
            if (!this.scroller.isFinished()) {
                this.scroller.forceFinished(true);
            }
            this.lastY = 0;
        }
    }

    /* access modifiers changed from: private */
    public void onScrollFinish(boolean isForceFinish) {
        if (this.isAutoRefresh && !isForceFinish) {
            this.isAutoRefresh = false;
            changeState(State.REFRESHING);
            EasyEvent easyEvent2 = this.easyEvent;
            if (easyEvent2 != null) {
                easyEvent2.onRefreshing();
            }
            finishSpinner();
        }
    }

    public void addEasyEvent(EasyEvent event) {
        if (event != null) {
            this.easyEvent = event;
            return;
        }
        throw new ERVHRuntimeException("adapter can not be null");
    }

    public boolean isEnablePullToRefresh() {
        return this.isEnablePullToRefresh;
    }

    public void setEnablePullToRefresh(boolean enable) {
        this.isEnablePullToRefresh = enable;
    }

    public boolean isRefreshing() {
        return this.isRefreshing;
    }

    public void setRefreshing(boolean refreshing) {
        if (refreshing) {
            changeState(State.REFRESHING);
            if (this.isNotMoreLoading || this.isLoadingFail) {
                closeLoadView();
            }
        }
        changeState(State.RESET);
    }

    private void initERVH() {
        if (this.mLoadMoreView == null) {
            getDefaultLoadMoreView();
            setLoadMoreView(this.mLoadMoreView);
        }
        if (this.isRecycerView) {
            RecyclerView recyclerView = (RecyclerView) this.contentView;
            this.mRecyclerView = recyclerView;
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (EasyRefreshLayout.this.loadMoreModel == LoadModel.ADVANCE_MODEL && !EasyRefreshLayout.this.isLoading && !EasyRefreshLayout.this.isRefreshing && !EasyRefreshLayout.this.isLoadingFail && !EasyRefreshLayout.this.isNotMoreLoading) {
                        int lastVisibleItem = EasyRefreshLayout.this.getLastVisiBleItem();
                        int totalItemCount = EasyRefreshLayout.this.mRecyclerView.getLayoutManager().getItemCount();
                        int totalChildCount = EasyRefreshLayout.this.mRecyclerView.getLayoutManager().getChildCount();
                        if (totalChildCount > 0 && lastVisibleItem >= (totalItemCount - 1) - EasyRefreshLayout.this.advanceCount && totalItemCount >= totalChildCount) {
                            EasyRefreshLayout.this.isCanLoad = true;
                        }
                        EasyRefreshLayout easyRefreshLayout = EasyRefreshLayout.this;
                        if (easyRefreshLayout.isCanLoad) {
                            easyRefreshLayout.isCanLoad = false;
                            boolean unused = easyRefreshLayout.isLoading = true;
                            if (EasyRefreshLayout.this.easyEvent != null) {
                                EasyRefreshLayout.this.easyEvent.onLoadMore();
                            }
                        }
                    }
                }

                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (EasyRefreshLayout.this.loadMoreModel != LoadModel.ADVANCE_MODEL && Math.abs(EasyRefreshLayout.this.mDistance) > ((float) EasyRefreshLayout.this.touchSlop) && EasyRefreshLayout.this.mDistance < 0.0f && !EasyRefreshLayout.this.isLoading && EasyRefreshLayout.this.loadMoreModel == LoadModel.COMMON_MODEL && !EasyRefreshLayout.this.isRefreshing && !EasyRefreshLayout.this.isLoadingFail && !EasyRefreshLayout.this.isNotMoreLoading) {
                        int lastVisibleItem = EasyRefreshLayout.this.getLastVisiBleItem();
                        int totalItemCount = EasyRefreshLayout.this.mRecyclerView.getLayoutManager().getItemCount();
                        int totalChildCount = EasyRefreshLayout.this.mRecyclerView.getLayoutManager().getChildCount();
                        if (totalChildCount > 0 && lastVisibleItem >= totalItemCount - 1 && totalItemCount >= totalChildCount) {
                            EasyRefreshLayout.this.isCanLoad = true;
                        }
                        EasyRefreshLayout easyRefreshLayout = EasyRefreshLayout.this;
                        if (easyRefreshLayout.isCanLoad) {
                            easyRefreshLayout.isCanLoad = false;
                            boolean unused = easyRefreshLayout.isLoading = true;
                            ((ILoadMoreView) EasyRefreshLayout.this.mLoadMoreView).reset();
                            EasyRefreshLayout.this.mLoadMoreView.measure(0, 0);
                            ((ILoadMoreView) EasyRefreshLayout.this.mLoadMoreView).loading();
                            EasyRefreshLayout.this.showLoadView();
                        }
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void showLoadView() {
        ValueAnimator animator = ValueAnimator.ofInt(new int[]{0, -this.mLoadMoreView.getMeasuredHeight()});
        animator.setTarget(this.mLoadMoreView);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private int lastDs;

            public void onAnimationUpdate(ValueAnimator animation) {
                int ds = ((Integer) animation.getAnimatedValue()).intValue();
                this.lastDs = ds;
                EasyRefreshLayout.this.mLoadMoreView.bringToFront();
                EasyRefreshLayout.this.mLoadMoreView.setTranslationY((float) ds);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (EasyRefreshLayout.this.easyEvent != null) {
                    EasyRefreshLayout.this.easyEvent.onLoadMore();
                }
            }
        });
        animator.setDuration(SCROLL_TO_LOADING_DURATION);
        animator.start();
    }

    /* access modifiers changed from: private */
    public void hideLoadView() {
        View view = this.mLoadMoreView;
        if (view != null && this.isRecycerView) {
            ValueAnimator animator = ValueAnimator.ofInt(new int[]{0, view.getMeasuredHeight()});
            animator.setTarget(this.mLoadMoreView);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                private int lastDs;

                public void onAnimationUpdate(ValueAnimator animation) {
                    int ds = ((Integer) animation.getAnimatedValue()).intValue();
                    this.lastDs = ds;
                    EasyRefreshLayout.this.mLoadMoreView.bringToFront();
                    EasyRefreshLayout.this.mLoadMoreView.setTranslationY((float) ds);
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                public void onAnimationStart(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                    boolean unused = EasyRefreshLayout.this.isLoading = false;
                }

                public void onAnimationCancel(Animator animator) {
                    boolean unused = EasyRefreshLayout.this.isLoading = false;
                }

                public void onAnimationRepeat(Animator animator) {
                    boolean unused = EasyRefreshLayout.this.isLoading = false;
                }
            });
            animator.setDuration(SHOW_SCROLL_DOWN_DURATION);
            animator.start();
        }
    }

    public void closeLoadView() {
        if (this.loadMoreModel != LoadModel.ADVANCE_MODEL) {
            View view = this.mLoadMoreView;
            if (view != null && this.isRecycerView) {
                view.bringToFront();
                View view2 = this.mLoadMoreView;
                view2.setTranslationY((float) view2.getMeasuredHeight());
                resetLoadMoreState();
                return;
            }
            return;
        }
        throw new RuntimeException("enableAdance Model cant not called closeLoadView method");
    }

    public View getLoadMoreView() {
        return getDefaultLoadMoreView();
    }

    public void setLoadMoreView(View loadMoreView) {
        if (loadMoreView != null) {
            View view = this.mLoadMoreView;
            if (loadMoreView != view) {
                removeView(view);
            }
            if (loadMoreView.getLayoutParams() == null) {
                loadMoreView.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
            }
            this.mLoadMoreView = loadMoreView;
            addView(loadMoreView);
            resetLoadMoreState();
            ((ILoadMoreView) this.mLoadMoreView).reset();
            ((ILoadMoreView) this.mLoadMoreView).getCanClickFailView().setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    if (EasyRefreshLayout.this.isLoadingFail && EasyRefreshLayout.this.easyEvent != null) {
                        boolean unused = EasyRefreshLayout.this.isLoading = true;
                        ((ILoadMoreView) EasyRefreshLayout.this.mLoadMoreView).loading();
                        EasyRefreshLayout.this.easyEvent.onLoadMore();
                    }
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
            return;
        }
        throw new ERVHRuntimeException("loadMoreView can not be null");
    }

    public void loadMoreComplete() {
        LoadModel loadModel = this.loadMoreModel;
        if (loadModel == LoadModel.ADVANCE_MODEL) {
            this.isLoading = false;
        } else if (loadModel == LoadModel.COMMON_MODEL) {
            loadMoreComplete((Event) null);
        }
    }

    @Deprecated
    public void loadMoreComplete(Event event) {
        if (this.loadMoreModel != LoadModel.ADVANCE_MODEL) {
            loadMoreComplete(event, 500);
            return;
        }
        throw new RuntimeException("enableAdance Model cant not called closeLoadView method");
    }

    @Deprecated
    public void loadMoreComplete(final Event event, long delayedTime) {
        if (this.loadMoreModel != LoadModel.ADVANCE_MODEL) {
            ((ILoadMoreView) this.mLoadMoreView).loadComplete();
            if (event == null) {
                hideLoadView();
                resetLoadMoreState();
                return;
            }
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    event.complete();
                    EasyRefreshLayout.this.hideLoadView();
                    EasyRefreshLayout.this.resetLoadMoreState();
                }
            }, delayedTime);
            return;
        }
        throw new RuntimeException("enableAdance Model cant not called closeLoadView method");
    }

    /* access modifiers changed from: private */
    public void resetLoadMoreState() {
        this.isCanLoad = false;
        this.isLoading = false;
        this.isLoadingFail = false;
        this.isNotMoreLoading = false;
    }

    public void loadMoreFail() {
        if (this.loadMoreModel != LoadModel.ADVANCE_MODEL) {
            ((ILoadMoreView) this.mLoadMoreView).loadFail();
            resetLoadMoreState();
            this.isLoadingFail = true;
            return;
        }
        throw new RuntimeException("enableAdance Model cant not called closeLoadView method");
    }

    public void loadNothing() {
        if (this.loadMoreModel != LoadModel.ADVANCE_MODEL) {
            ((ILoadMoreView) this.mLoadMoreView).loadNothing();
            resetLoadMoreState();
            this.isNotMoreLoading = true;
            return;
        }
        throw new RuntimeException("enableAdance Model cant not called closeLoadView method");
    }

    private View getDefaultLoadMoreView() {
        return new SimpleLoadMoreView(getContext());
    }

    /* access modifiers changed from: private */
    public int getLastVisiBleItem() {
        int layoutManagerType;
        RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            layoutManagerType = 1;
        } else if (layoutManager instanceof LinearLayoutManager) {
            layoutManagerType = 0;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            layoutManagerType = 2;
        } else {
            throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
        }
        switch (layoutManagerType) {
            case 0:
                return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            case 1:
                return ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            case 2:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                int[] lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                return findMax(lastPositions);
            default:
                return -1;
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public boolean isEnableLoadMore() {
        LoadModel loadModel = this.loadMoreModel;
        return loadModel == LoadModel.COMMON_MODEL || loadModel == LoadModel.ADVANCE_MODEL;
    }

    public LoadModel getLoadMoreModel() {
        return this.loadMoreModel;
    }

    public void setLoadMoreModel(LoadModel loadMoreModel2, int advanceCount2) {
        this.loadMoreModel = loadMoreModel2;
        this.advanceCount = advanceCount2;
    }

    public int getAdvanceCount() {
        return this.advanceCount;
    }

    public void setAdvanceCount(int advanceCount2) {
        this.advanceCount = advanceCount2;
    }

    public void setLoadMoreModel(LoadModel loadMoreModel2) {
        setLoadMoreModel(loadMoreModel2, 0);
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    public long getShowLoadViewAnimatorDuration() {
        return SCROLL_TO_LOADING_DURATION;
    }

    public void setShowLoadViewAnimatorDuration(long scrollToLoadingDuration) {
        SCROLL_TO_LOADING_DURATION = scrollToLoadingDuration;
    }

    public int getScrollToRefreshDuration() {
        return SCROLL_TO_REFRESH_DURATION;
    }

    public void setScrollToRefreshDuration(int scrollToRefreshDuration) {
        SCROLL_TO_REFRESH_DURATION = scrollToRefreshDuration;
    }

    public int getScrollToTopDuration() {
        return SCROLL_TO_TOP_DURATION;
    }

    public void setScrollToTopDuration(int scrollToTopDuration) {
        SCROLL_TO_TOP_DURATION = scrollToTopDuration;
    }

    public long getHideLoadViewAnimatorDuration() {
        return SHOW_COMPLETED_TIME;
    }

    public void setHideLoadViewAnimatorDuration(long showCompletedTime) {
        SHOW_COMPLETED_TIME = showCompletedTime;
    }

    public double getPullResistance() {
        return this.pull_resistance;
    }

    public void setPullResistance(double PullResistance) {
        this.pull_resistance = PullResistance;
    }
}

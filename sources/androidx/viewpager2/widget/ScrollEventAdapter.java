package androidx.viewpager2.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.util.Locale;

public final class ScrollEventAdapter extends RecyclerView.OnScrollListener {
    private static final int NO_POSITION = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_IN_PROGRESS_FAKE_DRAG = 4;
    private static final int STATE_IN_PROGRESS_IMMEDIATE_SCROLL = 3;
    private static final int STATE_IN_PROGRESS_MANUAL_DRAG = 1;
    private static final int STATE_IN_PROGRESS_SMOOTH_SCROLL = 2;
    private int mAdapterState;
    private ViewPager2.OnPageChangeCallback mCallback;
    private boolean mDataSetChangeHappened;
    private boolean mDispatchSelected;
    private int mDragStartPosition;
    private boolean mFakeDragging;
    @NonNull
    private final LinearLayoutManager mLayoutManager;
    @NonNull
    private final RecyclerView mRecyclerView;
    private boolean mScrollHappened;
    private int mScrollState;
    private ScrollEventValues mScrollValues = new ScrollEventValues();
    private int mTarget;
    @NonNull
    private final ViewPager2 mViewPager;

    ScrollEventAdapter(@NonNull ViewPager2 viewPager) {
        this.mViewPager = viewPager;
        RecyclerView recyclerView = viewPager.mRecyclerView;
        this.mRecyclerView = recyclerView;
        this.mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        resetState();
    }

    private void resetState() {
        this.mAdapterState = 0;
        this.mScrollState = 0;
        this.mScrollValues.reset();
        this.mDragStartPosition = -1;
        this.mTarget = -1;
        this.mDispatchSelected = false;
        this.mScrollHappened = false;
        this.mFakeDragging = false;
        this.mDataSetChangeHappened = false;
    }

    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        if (!(this.mAdapterState == 1 && this.mScrollState == 1) && newState == 1) {
            startDrag(false);
        } else if (!isInAnyDraggingState() || newState != 2) {
            if (isInAnyDraggingState() && newState == 0) {
                boolean dispatchIdle = false;
                updateScrollEventValues();
                if (!this.mScrollHappened) {
                    int i = this.mScrollValues.mPosition;
                    if (i != -1) {
                        dispatchScrolled(i, 0.0f, 0);
                    }
                    dispatchIdle = true;
                } else {
                    ScrollEventValues scrollEventValues = this.mScrollValues;
                    if (scrollEventValues.mOffsetPx == 0) {
                        dispatchIdle = true;
                        int i2 = this.mDragStartPosition;
                        int i3 = scrollEventValues.mPosition;
                        if (i2 != i3) {
                            dispatchSelected(i3);
                        }
                    }
                }
                if (dispatchIdle) {
                    dispatchStateChanged(0);
                    resetState();
                }
            }
            if (this.mAdapterState == 2 && newState == 0 && this.mDataSetChangeHappened) {
                updateScrollEventValues();
                ScrollEventValues scrollEventValues2 = this.mScrollValues;
                if (scrollEventValues2.mOffsetPx == 0) {
                    int i4 = this.mTarget;
                    int i5 = scrollEventValues2.mPosition;
                    if (i4 != i5) {
                        if (i5 == -1) {
                            i5 = 0;
                        }
                        dispatchSelected(i5);
                    }
                    dispatchStateChanged(0);
                    resetState();
                }
            }
        } else if (this.mScrollHappened) {
            dispatchStateChanged(2);
            this.mDispatchSelected = true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001d, code lost:
        if ((r8 < 0) == r6.mViewPager.isRtl()) goto L_0x0022;
     */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0025  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0039  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onScrolled(@androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView r7, int r8, int r9) {
        /*
            r6 = this;
            r0 = 1
            r6.mScrollHappened = r0
            r6.updateScrollEventValues()
            boolean r1 = r6.mDispatchSelected
            r2 = -1
            r3 = 0
            if (r1 == 0) goto L_0x003d
            r6.mDispatchSelected = r3
            if (r9 > 0) goto L_0x0022
            if (r9 != 0) goto L_0x0020
            if (r8 >= 0) goto L_0x0016
            r1 = r0
            goto L_0x0017
        L_0x0016:
            r1 = r3
        L_0x0017:
            androidx.viewpager2.widget.ViewPager2 r4 = r6.mViewPager
            boolean r4 = r4.isRtl()
            if (r1 != r4) goto L_0x0020
            goto L_0x0022
        L_0x0020:
            r1 = r3
            goto L_0x0023
        L_0x0022:
            r1 = r0
        L_0x0023:
            if (r1 == 0) goto L_0x002f
            androidx.viewpager2.widget.ScrollEventAdapter$ScrollEventValues r4 = r6.mScrollValues
            int r5 = r4.mOffsetPx
            if (r5 == 0) goto L_0x002f
            int r4 = r4.mPosition
            int r4 = r4 + r0
            goto L_0x0033
        L_0x002f:
            androidx.viewpager2.widget.ScrollEventAdapter$ScrollEventValues r4 = r6.mScrollValues
            int r4 = r4.mPosition
        L_0x0033:
            r6.mTarget = r4
            int r5 = r6.mDragStartPosition
            if (r5 == r4) goto L_0x004e
            r6.dispatchSelected(r4)
            goto L_0x004e
        L_0x003d:
            int r1 = r6.mAdapterState
            if (r1 != 0) goto L_0x004e
            androidx.viewpager2.widget.ScrollEventAdapter$ScrollEventValues r1 = r6.mScrollValues
            int r1 = r1.mPosition
            if (r1 != r2) goto L_0x0049
            r4 = r3
            goto L_0x004a
        L_0x0049:
            r4 = r1
        L_0x004a:
            r6.dispatchSelected(r4)
            goto L_0x004f
        L_0x004e:
        L_0x004f:
            androidx.viewpager2.widget.ScrollEventAdapter$ScrollEventValues r1 = r6.mScrollValues
            int r4 = r1.mPosition
            if (r4 != r2) goto L_0x0056
            r4 = r3
        L_0x0056:
            float r5 = r1.mOffset
            int r1 = r1.mOffsetPx
            r6.dispatchScrolled(r4, r5, r1)
            androidx.viewpager2.widget.ScrollEventAdapter$ScrollEventValues r1 = r6.mScrollValues
            int r4 = r1.mPosition
            int r5 = r6.mTarget
            if (r4 == r5) goto L_0x0067
            if (r5 != r2) goto L_0x0075
        L_0x0067:
            int r1 = r1.mOffsetPx
            if (r1 != 0) goto L_0x0075
            int r1 = r6.mScrollState
            if (r1 == r0) goto L_0x0075
            r6.dispatchStateChanged(r3)
            r6.resetState()
        L_0x0075:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager2.widget.ScrollEventAdapter.onScrolled(androidx.recyclerview.widget.RecyclerView, int, int):void");
    }

    private void updateScrollEventValues() {
        int start;
        int sizePx;
        ScrollEventValues values = this.mScrollValues;
        int findFirstVisibleItemPosition = this.mLayoutManager.findFirstVisibleItemPosition();
        values.mPosition = findFirstVisibleItemPosition;
        if (findFirstVisibleItemPosition == -1) {
            values.reset();
            return;
        }
        View firstVisibleView = this.mLayoutManager.findViewByPosition(findFirstVisibleItemPosition);
        if (firstVisibleView == null) {
            values.reset();
            return;
        }
        int leftDecorations = this.mLayoutManager.getLeftDecorationWidth(firstVisibleView);
        int rightDecorations = this.mLayoutManager.getRightDecorationWidth(firstVisibleView);
        int topDecorations = this.mLayoutManager.getTopDecorationHeight(firstVisibleView);
        int bottomDecorations = this.mLayoutManager.getBottomDecorationHeight(firstVisibleView);
        ViewGroup.LayoutParams params = firstVisibleView.getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams margin = (ViewGroup.MarginLayoutParams) params;
            leftDecorations += margin.leftMargin;
            rightDecorations += margin.rightMargin;
            topDecorations += margin.topMargin;
            bottomDecorations += margin.bottomMargin;
        }
        int decoratedHeight = firstVisibleView.getHeight() + topDecorations + bottomDecorations;
        int decoratedWidth = firstVisibleView.getWidth() + leftDecorations + rightDecorations;
        if (this.mLayoutManager.getOrientation() == 0) {
            sizePx = decoratedWidth;
            start = (firstVisibleView.getLeft() - leftDecorations) - this.mRecyclerView.getPaddingLeft();
            if (this.mViewPager.isRtl()) {
                start = -start;
            }
        } else {
            sizePx = decoratedHeight;
            start = (firstVisibleView.getTop() - topDecorations) - this.mRecyclerView.getPaddingTop();
        }
        int i = -start;
        values.mOffsetPx = i;
        if (i >= 0) {
            values.mOffset = sizePx == 0 ? 0.0f : ((float) i) / ((float) sizePx);
        } else if (new AnimateLayoutChangeDetector(this.mLayoutManager).mayHaveInterferingAnimations()) {
            throw new IllegalStateException("Page(s) contain a ViewGroup with a LayoutTransition (or animateLayoutChanges=\"true\"), which interferes with the scrolling animation. Make sure to call getLayoutTransition().setAnimateParentHierarchy(false) on all ViewGroups with a LayoutTransition before an animation is started.");
        } else {
            throw new IllegalStateException(String.format(Locale.US, "Page can only be offset by a positive amount, not by %d", new Object[]{Integer.valueOf(values.mOffsetPx)}));
        }
    }

    private void startDrag(boolean isFakeDrag) {
        this.mFakeDragging = isFakeDrag;
        this.mAdapterState = isFakeDrag ? 4 : 1;
        int i = this.mTarget;
        if (i != -1) {
            this.mDragStartPosition = i;
            this.mTarget = -1;
        } else if (this.mDragStartPosition == -1) {
            this.mDragStartPosition = getPosition();
        }
        dispatchStateChanged(1);
    }

    /* access modifiers changed from: package-private */
    public void notifyDataSetChangeHappened() {
        this.mDataSetChangeHappened = true;
    }

    /* access modifiers changed from: package-private */
    public void notifyProgrammaticScroll(int target, boolean smooth) {
        this.mAdapterState = smooth ? 2 : 3;
        boolean hasNewTarget = false;
        this.mFakeDragging = false;
        if (this.mTarget != target) {
            hasNewTarget = true;
        }
        this.mTarget = target;
        dispatchStateChanged(2);
        if (hasNewTarget) {
            dispatchSelected(target);
        }
    }

    /* access modifiers changed from: package-private */
    public void notifyBeginFakeDrag() {
        this.mAdapterState = 4;
        startDrag(true);
    }

    /* access modifiers changed from: package-private */
    public void notifyEndFakeDrag() {
        if (!isDragging() || this.mFakeDragging) {
            this.mFakeDragging = false;
            updateScrollEventValues();
            ScrollEventValues scrollEventValues = this.mScrollValues;
            if (scrollEventValues.mOffsetPx == 0) {
                int i = scrollEventValues.mPosition;
                if (i != this.mDragStartPosition) {
                    dispatchSelected(i);
                }
                dispatchStateChanged(0);
                resetState();
                return;
            }
            dispatchStateChanged(2);
        }
    }

    /* access modifiers changed from: package-private */
    public void setOnPageChangeCallback(ViewPager2.OnPageChangeCallback callback) {
        this.mCallback = callback;
    }

    /* access modifiers changed from: package-private */
    public int getScrollState() {
        return this.mScrollState;
    }

    /* access modifiers changed from: package-private */
    public boolean isIdle() {
        return this.mScrollState == 0;
    }

    /* access modifiers changed from: package-private */
    public boolean isDragging() {
        return this.mScrollState == 1;
    }

    /* access modifiers changed from: package-private */
    public boolean isFakeDragging() {
        return this.mFakeDragging;
    }

    private boolean isInAnyDraggingState() {
        int i = this.mAdapterState;
        return i == 1 || i == 4;
    }

    /* access modifiers changed from: package-private */
    public double getRelativeScrollPosition() {
        updateScrollEventValues();
        ScrollEventValues scrollEventValues = this.mScrollValues;
        return ((double) scrollEventValues.mPosition) + ((double) scrollEventValues.mOffset);
    }

    private void dispatchStateChanged(int state) {
        if ((this.mAdapterState != 3 || this.mScrollState != 0) && this.mScrollState != state) {
            this.mScrollState = state;
            ViewPager2.OnPageChangeCallback onPageChangeCallback = this.mCallback;
            if (onPageChangeCallback != null) {
                onPageChangeCallback.onPageScrollStateChanged(state);
            }
        }
    }

    private void dispatchSelected(int target) {
        ViewPager2.OnPageChangeCallback onPageChangeCallback = this.mCallback;
        if (onPageChangeCallback != null) {
            onPageChangeCallback.onPageSelected(target);
        }
    }

    private void dispatchScrolled(int position, float offset, int offsetPx) {
        ViewPager2.OnPageChangeCallback onPageChangeCallback = this.mCallback;
        if (onPageChangeCallback != null) {
            onPageChangeCallback.onPageScrolled(position, offset, offsetPx);
        }
    }

    private int getPosition() {
        return this.mLayoutManager.findFirstVisibleItemPosition();
    }

    public static final class ScrollEventValues {
        float mOffset;
        int mOffsetPx;
        int mPosition;

        ScrollEventValues() {
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            this.mPosition = -1;
            this.mOffset = 0.0f;
            this.mOffsetPx = 0;
        }
    }
}

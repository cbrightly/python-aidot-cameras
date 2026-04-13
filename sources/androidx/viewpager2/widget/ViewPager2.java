package androidx.viewpager2.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.R;
import androidx.viewpager2.adapter.StatefulAdapter;
import com.google.android.material.badge.BadgeDrawable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class ViewPager2 extends ViewGroup {
    public static final int OFFSCREEN_PAGE_LIMIT_DEFAULT = -1;
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    static boolean sFeatureEnhancedA11yEnabled = true;
    AccessibilityProvider mAccessibilityProvider;
    int mCurrentItem;
    private RecyclerView.AdapterDataObserver mCurrentItemDataSetChangeObserver = new DataSetChangeObserver() {
        public void onChanged() {
            ViewPager2 viewPager2 = ViewPager2.this;
            viewPager2.mCurrentItemDirty = true;
            viewPager2.mScrollEventAdapter.notifyDataSetChangeHappened();
        }
    };
    boolean mCurrentItemDirty = false;
    private CompositeOnPageChangeCallback mExternalPageChangeCallbacks = new CompositeOnPageChangeCallback(3);
    private FakeDrag mFakeDragger;
    private LinearLayoutManager mLayoutManager;
    private int mOffscreenPageLimit = -1;
    private CompositeOnPageChangeCallback mPageChangeEventDispatcher;
    private PageTransformerAdapter mPageTransformerAdapter;
    private PagerSnapHelper mPagerSnapHelper;
    private Parcelable mPendingAdapterState;
    private int mPendingCurrentItem = -1;
    RecyclerView mRecyclerView;
    private RecyclerView.ItemAnimator mSavedItemAnimator = null;
    private boolean mSavedItemAnimatorPresent = false;
    ScrollEventAdapter mScrollEventAdapter;
    private final Rect mTmpChildRect = new Rect();
    private final Rect mTmpContainerRect = new Rect();
    private boolean mUserInputEnabled = true;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @IntRange(from = 1)
    @Retention(RetentionPolicy.SOURCE)
    public @interface OffscreenPageLimit {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
    }

    public interface PageTransformer {
        void transformPage(@NonNull View view, float f);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ScrollState {
    }

    public ViewPager2(@NonNull Context context) {
        super(context);
        initialize(context, (AttributeSet) null);
    }

    public ViewPager2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public ViewPager2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    @RequiresApi(21)
    public ViewPager2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        this.mAccessibilityProvider = sFeatureEnhancedA11yEnabled ? new PageAwareAccessibilityProvider() : new BasicAccessibilityProvider();
        RecyclerViewImpl recyclerViewImpl = new RecyclerViewImpl(context);
        this.mRecyclerView = recyclerViewImpl;
        recyclerViewImpl.setId(ViewCompat.generateViewId());
        this.mRecyclerView.setDescendantFocusability(131072);
        LinearLayoutManagerImpl linearLayoutManagerImpl = new LinearLayoutManagerImpl(context);
        this.mLayoutManager = linearLayoutManagerImpl;
        this.mRecyclerView.setLayoutManager(linearLayoutManagerImpl);
        this.mRecyclerView.setScrollingTouchSlop(1);
        setOrientation(context, attrs);
        this.mRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.mRecyclerView.addOnChildAttachStateChangeListener(enforceChildFillListener());
        ScrollEventAdapter scrollEventAdapter = new ScrollEventAdapter(this);
        this.mScrollEventAdapter = scrollEventAdapter;
        this.mFakeDragger = new FakeDrag(this, scrollEventAdapter, this.mRecyclerView);
        PagerSnapHelperImpl pagerSnapHelperImpl = new PagerSnapHelperImpl();
        this.mPagerSnapHelper = pagerSnapHelperImpl;
        pagerSnapHelperImpl.attachToRecyclerView(this.mRecyclerView);
        this.mRecyclerView.addOnScrollListener(this.mScrollEventAdapter);
        CompositeOnPageChangeCallback compositeOnPageChangeCallback = new CompositeOnPageChangeCallback(3);
        this.mPageChangeEventDispatcher = compositeOnPageChangeCallback;
        this.mScrollEventAdapter.setOnPageChangeCallback(compositeOnPageChangeCallback);
        OnPageChangeCallback currentItemUpdater = new OnPageChangeCallback() {
            public void onPageSelected(int position) {
                ViewPager2 viewPager2 = ViewPager2.this;
                if (viewPager2.mCurrentItem != position) {
                    viewPager2.mCurrentItem = position;
                    viewPager2.mAccessibilityProvider.onSetNewCurrentItem();
                }
            }

            public void onPageScrollStateChanged(int newState) {
                if (newState == 0) {
                    ViewPager2.this.updateCurrentItem();
                }
            }
        };
        OnPageChangeCallback focusClearer = new OnPageChangeCallback() {
            public void onPageSelected(int position) {
                ViewPager2.this.clearFocus();
                if (ViewPager2.this.hasFocus()) {
                    ViewPager2.this.mRecyclerView.requestFocus(2);
                }
            }
        };
        this.mPageChangeEventDispatcher.addOnPageChangeCallback(currentItemUpdater);
        this.mPageChangeEventDispatcher.addOnPageChangeCallback(focusClearer);
        this.mAccessibilityProvider.onInitialize(this.mPageChangeEventDispatcher, this.mRecyclerView);
        this.mPageChangeEventDispatcher.addOnPageChangeCallback(this.mExternalPageChangeCallbacks);
        PageTransformerAdapter pageTransformerAdapter = new PageTransformerAdapter(this.mLayoutManager);
        this.mPageTransformerAdapter = pageTransformerAdapter;
        this.mPageChangeEventDispatcher.addOnPageChangeCallback(pageTransformerAdapter);
        RecyclerView recyclerView = this.mRecyclerView;
        attachViewToParent(recyclerView, 0, recyclerView.getLayoutParams());
    }

    private RecyclerView.OnChildAttachStateChangeListener enforceChildFillListener() {
        return new RecyclerView.OnChildAttachStateChangeListener() {
            public void onChildViewAttachedToWindow(@NonNull View view) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                if (layoutParams.width != -1 || layoutParams.height != -1) {
                    throw new IllegalStateException("Pages must fill the whole ViewPager2 (use match_parent)");
                }
            }

            public void onChildViewDetachedFromWindow(@NonNull View view) {
            }
        };
    }

    @RequiresApi(23)
    public CharSequence getAccessibilityClassName() {
        if (this.mAccessibilityProvider.handlesGetAccessibilityClassName()) {
            return this.mAccessibilityProvider.onGetAccessibilityClassName();
        }
        return super.getAccessibilityClassName();
    }

    private void setOrientation(Context context, AttributeSet attrs) {
        int[] iArr = R.styleable.ViewPager2;
        TypedArray a = context.obtainStyledAttributes(attrs, iArr);
        if (Build.VERSION.SDK_INT >= 29) {
            saveAttributeDataForStyleable(context, iArr, attrs, a, 0, 0);
        }
        try {
            setOrientation(a.getInt(R.styleable.ViewPager2_android_orientation, 0));
        } finally {
            a.recycle();
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.mRecyclerViewId = this.mRecyclerView.getId();
        int i = this.mPendingCurrentItem;
        if (i == -1) {
            i = this.mCurrentItem;
        }
        ss.mCurrentItem = i;
        Parcelable parcelable = this.mPendingAdapterState;
        if (parcelable != null) {
            ss.mAdapterState = parcelable;
        } else {
            RecyclerView.Adapter<?> adapter = this.mRecyclerView.getAdapter();
            if (adapter instanceof StatefulAdapter) {
                ss.mAdapterState = ((StatefulAdapter) adapter).saveState();
            }
        }
        return ss;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.mPendingCurrentItem = ss.mCurrentItem;
        this.mPendingAdapterState = ss.mAdapterState;
    }

    private void restorePendingState() {
        RecyclerView.Adapter<?> adapter;
        if (this.mPendingCurrentItem != -1 && (adapter = getAdapter()) != null) {
            Parcelable parcelable = this.mPendingAdapterState;
            if (parcelable != null) {
                if (adapter instanceof StatefulAdapter) {
                    ((StatefulAdapter) adapter).restoreState(parcelable);
                }
                this.mPendingAdapterState = null;
            }
            int max = Math.max(0, Math.min(this.mPendingCurrentItem, adapter.getItemCount() - 1));
            this.mCurrentItem = max;
            this.mPendingCurrentItem = -1;
            this.mRecyclerView.scrollToPosition(max);
            this.mAccessibilityProvider.onRestorePendingState();
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        Parcelable state = container.get(getId());
        if (state instanceof SavedState) {
            int previousRvId = ((SavedState) state).mRecyclerViewId;
            container.put(this.mRecyclerView.getId(), container.get(previousRvId));
            container.remove(previousRvId);
        }
        super.dispatchRestoreInstanceState(container);
        restorePendingState();
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
            public SavedState createFromParcel(Parcel source, ClassLoader loader) {
                return Build.VERSION.SDK_INT >= 24 ? new SavedState(source, loader) : new SavedState(source);
            }

            public SavedState createFromParcel(Parcel source) {
                return createFromParcel(source, (ClassLoader) null);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        Parcelable mAdapterState;
        int mCurrentItem;
        int mRecyclerViewId;

        @RequiresApi(24)
        SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            readValues(source, loader);
        }

        SavedState(Parcel source) {
            super(source);
            readValues(source, (ClassLoader) null);
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        private void readValues(Parcel source, ClassLoader loader) {
            this.mRecyclerViewId = source.readInt();
            this.mCurrentItem = source.readInt();
            this.mAdapterState = source.readParcelable(loader);
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.mRecyclerViewId);
            out.writeInt(this.mCurrentItem);
            out.writeParcelable(this.mAdapterState, flags);
        }
    }

    public void setAdapter(@Nullable RecyclerView.Adapter adapter) {
        RecyclerView.Adapter<?> currentAdapter = this.mRecyclerView.getAdapter();
        this.mAccessibilityProvider.onDetachAdapter(currentAdapter);
        unregisterCurrentItemDataSetTracker(currentAdapter);
        this.mRecyclerView.setAdapter(adapter);
        this.mCurrentItem = 0;
        restorePendingState();
        this.mAccessibilityProvider.onAttachAdapter(adapter);
        registerCurrentItemDataSetTracker(adapter);
    }

    private void registerCurrentItemDataSetTracker(@Nullable RecyclerView.Adapter<?> adapter) {
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.mCurrentItemDataSetChangeObserver);
        }
    }

    private void unregisterCurrentItemDataSetTracker(@Nullable RecyclerView.Adapter<?> adapter) {
        if (adapter != null) {
            adapter.unregisterAdapterDataObserver(this.mCurrentItemDataSetChangeObserver);
        }
    }

    @Nullable
    public RecyclerView.Adapter getAdapter() {
        return this.mRecyclerView.getAdapter();
    }

    public void onViewAdded(View child) {
        throw new IllegalStateException(getClass().getSimpleName() + " does not support direct child views");
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChild(this.mRecyclerView, widthMeasureSpec, heightMeasureSpec);
        int width = this.mRecyclerView.getMeasuredWidth();
        int height = this.mRecyclerView.getMeasuredHeight();
        int childState = this.mRecyclerView.getMeasuredState();
        int width2 = width + getPaddingLeft() + getPaddingRight();
        int height2 = height + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(ViewGroup.resolveSizeAndState(Math.max(width2, getSuggestedMinimumWidth()), widthMeasureSpec, childState), ViewGroup.resolveSizeAndState(Math.max(height2, getSuggestedMinimumHeight()), heightMeasureSpec, childState << 16));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = this.mRecyclerView.getMeasuredWidth();
        int height = this.mRecyclerView.getMeasuredHeight();
        this.mTmpContainerRect.left = getPaddingLeft();
        this.mTmpContainerRect.right = (r - l) - getPaddingRight();
        this.mTmpContainerRect.top = getPaddingTop();
        this.mTmpContainerRect.bottom = (b - t) - getPaddingBottom();
        Gravity.apply(BadgeDrawable.TOP_START, width, height, this.mTmpContainerRect, this.mTmpChildRect);
        RecyclerView recyclerView = this.mRecyclerView;
        Rect rect = this.mTmpChildRect;
        recyclerView.layout(rect.left, rect.top, rect.right, rect.bottom);
        if (this.mCurrentItemDirty) {
            updateCurrentItem();
        }
    }

    /* access modifiers changed from: package-private */
    public void updateCurrentItem() {
        PagerSnapHelper pagerSnapHelper = this.mPagerSnapHelper;
        if (pagerSnapHelper != null) {
            View snapView = pagerSnapHelper.findSnapView(this.mLayoutManager);
            if (snapView != null) {
                int snapPosition = this.mLayoutManager.getPosition(snapView);
                if (snapPosition != this.mCurrentItem && getScrollState() == 0) {
                    this.mPageChangeEventDispatcher.onPageSelected(snapPosition);
                }
                this.mCurrentItemDirty = false;
                return;
            }
            return;
        }
        throw new IllegalStateException("Design assumption violated.");
    }

    /* access modifiers changed from: package-private */
    public int getPageSize() {
        RecyclerView rv = this.mRecyclerView;
        if (getOrientation() == 0) {
            return (rv.getWidth() - rv.getPaddingLeft()) - rv.getPaddingRight();
        }
        return (rv.getHeight() - rv.getPaddingTop()) - rv.getPaddingBottom();
    }

    public void setOrientation(int orientation) {
        this.mLayoutManager.setOrientation(orientation);
        this.mAccessibilityProvider.onSetOrientation();
    }

    public int getOrientation() {
        return this.mLayoutManager.getOrientation();
    }

    /* access modifiers changed from: package-private */
    public boolean isRtl() {
        return this.mLayoutManager.getLayoutDirection() == 1;
    }

    public void setCurrentItem(int item) {
        setCurrentItem(item, true);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        if (!isFakeDragging()) {
            setCurrentItemInternal(item, smoothScroll);
            return;
        }
        throw new IllegalStateException("Cannot change current item when ViewPager2 is fake dragging");
    }

    /* access modifiers changed from: package-private */
    public void setCurrentItemInternal(int item, boolean smoothScroll) {
        RecyclerView.Adapter<?> adapter = getAdapter();
        if (adapter == null) {
            if (this.mPendingCurrentItem != -1) {
                this.mPendingCurrentItem = Math.max(item, 0);
            }
        } else if (adapter.getItemCount() > 0) {
            int item2 = Math.min(Math.max(item, 0), adapter.getItemCount() - 1);
            if (item2 != this.mCurrentItem || !this.mScrollEventAdapter.isIdle()) {
                int i = this.mCurrentItem;
                if (item2 != i || !smoothScroll) {
                    double previousItem = (double) i;
                    this.mCurrentItem = item2;
                    this.mAccessibilityProvider.onSetNewCurrentItem();
                    if (!this.mScrollEventAdapter.isIdle()) {
                        previousItem = this.mScrollEventAdapter.getRelativeScrollPosition();
                    }
                    this.mScrollEventAdapter.notifyProgrammaticScroll(item2, smoothScroll);
                    if (!smoothScroll) {
                        this.mRecyclerView.scrollToPosition(item2);
                    } else if (Math.abs(((double) item2) - previousItem) > 3.0d) {
                        this.mRecyclerView.scrollToPosition(((double) item2) > previousItem ? item2 - 3 : item2 + 3);
                        RecyclerView recyclerView = this.mRecyclerView;
                        recyclerView.post(new SmoothScrollToPosition(item2, recyclerView));
                    } else {
                        this.mRecyclerView.smoothScrollToPosition(item2);
                    }
                }
            }
        }
    }

    public int getCurrentItem() {
        return this.mCurrentItem;
    }

    public int getScrollState() {
        return this.mScrollEventAdapter.getScrollState();
    }

    public boolean beginFakeDrag() {
        return this.mFakeDragger.beginFakeDrag();
    }

    public boolean fakeDragBy(@Px @SuppressLint({"SupportAnnotationUsage"}) float offsetPxFloat) {
        return this.mFakeDragger.fakeDragBy(offsetPxFloat);
    }

    public boolean endFakeDrag() {
        return this.mFakeDragger.endFakeDrag();
    }

    public boolean isFakeDragging() {
        return this.mFakeDragger.isFakeDragging();
    }

    /* access modifiers changed from: package-private */
    public void snapToPage() {
        View view = this.mPagerSnapHelper.findSnapView(this.mLayoutManager);
        if (view != null) {
            int[] snapDistance = this.mPagerSnapHelper.calculateDistanceToFinalSnap(this.mLayoutManager, view);
            if (snapDistance[0] != 0 || snapDistance[1] != 0) {
                this.mRecyclerView.smoothScrollBy(snapDistance[0], snapDistance[1]);
            }
        }
    }

    public void setUserInputEnabled(boolean enabled) {
        this.mUserInputEnabled = enabled;
        this.mAccessibilityProvider.onSetUserInputEnabled();
    }

    public boolean isUserInputEnabled() {
        return this.mUserInputEnabled;
    }

    public void setOffscreenPageLimit(int limit) {
        if (limit >= 1 || limit == -1) {
            this.mOffscreenPageLimit = limit;
            this.mRecyclerView.requestLayout();
            return;
        }
        throw new IllegalArgumentException("Offscreen page limit must be OFFSCREEN_PAGE_LIMIT_DEFAULT or a number > 0");
    }

    public int getOffscreenPageLimit() {
        return this.mOffscreenPageLimit;
    }

    public boolean canScrollHorizontally(int direction) {
        return this.mRecyclerView.canScrollHorizontally(direction);
    }

    public boolean canScrollVertically(int direction) {
        return this.mRecyclerView.canScrollVertically(direction);
    }

    public void registerOnPageChangeCallback(@NonNull OnPageChangeCallback callback) {
        this.mExternalPageChangeCallbacks.addOnPageChangeCallback(callback);
    }

    public void unregisterOnPageChangeCallback(@NonNull OnPageChangeCallback callback) {
        this.mExternalPageChangeCallbacks.removeOnPageChangeCallback(callback);
    }

    public void setPageTransformer(@Nullable PageTransformer transformer) {
        if (transformer != null) {
            if (!this.mSavedItemAnimatorPresent) {
                this.mSavedItemAnimator = this.mRecyclerView.getItemAnimator();
                this.mSavedItemAnimatorPresent = true;
            }
            this.mRecyclerView.setItemAnimator((RecyclerView.ItemAnimator) null);
        } else if (this.mSavedItemAnimatorPresent) {
            this.mRecyclerView.setItemAnimator(this.mSavedItemAnimator);
            this.mSavedItemAnimator = null;
            this.mSavedItemAnimatorPresent = false;
        }
        if (transformer != this.mPageTransformerAdapter.getPageTransformer()) {
            this.mPageTransformerAdapter.setPageTransformer(transformer);
            requestTransform();
        }
    }

    public void requestTransform() {
        if (this.mPageTransformerAdapter.getPageTransformer() != null) {
            double relativePosition = this.mScrollEventAdapter.getRelativeScrollPosition();
            int position = (int) relativePosition;
            float positionOffset = (float) (relativePosition - ((double) position));
            this.mPageTransformerAdapter.onPageScrolled(position, positionOffset, Math.round(((float) getPageSize()) * positionOffset));
        }
    }

    @RequiresApi(17)
    public void setLayoutDirection(int layoutDirection) {
        super.setLayoutDirection(layoutDirection);
        this.mAccessibilityProvider.onSetLayoutDirection();
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        this.mAccessibilityProvider.onInitializeAccessibilityNodeInfo(info);
    }

    @RequiresApi(16)
    public boolean performAccessibilityAction(int action, Bundle arguments) {
        if (this.mAccessibilityProvider.handlesPerformAccessibilityAction(action, arguments)) {
            return this.mAccessibilityProvider.onPerformAccessibilityAction(action, arguments);
        }
        return super.performAccessibilityAction(action, arguments);
    }

    public class RecyclerViewImpl extends RecyclerView {
        RecyclerViewImpl(@NonNull Context context) {
            super(context);
        }

        @RequiresApi(23)
        public CharSequence getAccessibilityClassName() {
            if (ViewPager2.this.mAccessibilityProvider.handlesRvGetAccessibilityClassName()) {
                return ViewPager2.this.mAccessibilityProvider.onRvGetAccessibilityClassName();
            }
            return super.getAccessibilityClassName();
        }

        public void onInitializeAccessibilityEvent(@NonNull AccessibilityEvent event) {
            super.onInitializeAccessibilityEvent(event);
            event.setFromIndex(ViewPager2.this.mCurrentItem);
            event.setToIndex(ViewPager2.this.mCurrentItem);
            ViewPager2.this.mAccessibilityProvider.onRvInitializeAccessibilityEvent(event);
        }

        @SuppressLint({"ClickableViewAccessibility"})
        public boolean onTouchEvent(MotionEvent event) {
            return ViewPager2.this.isUserInputEnabled() && super.onTouchEvent(event);
        }

        public boolean onInterceptTouchEvent(MotionEvent ev) {
            return ViewPager2.this.isUserInputEnabled() && super.onInterceptTouchEvent(ev);
        }
    }

    public class LinearLayoutManagerImpl extends LinearLayoutManager {
        LinearLayoutManagerImpl(Context context) {
            super(context);
        }

        public boolean performAccessibilityAction(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, int action, @Nullable Bundle args) {
            if (ViewPager2.this.mAccessibilityProvider.handlesLmPerformAccessibilityAction(action)) {
                return ViewPager2.this.mAccessibilityProvider.onLmPerformAccessibilityAction(action);
            }
            return super.performAccessibilityAction(recycler, state, action, args);
        }

        public void onInitializeAccessibilityNodeInfo(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, @NonNull AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(recycler, state, info);
            ViewPager2.this.mAccessibilityProvider.onLmInitializeAccessibilityNodeInfo(info);
        }

        /* access modifiers changed from: protected */
        public void calculateExtraLayoutSpace(@NonNull RecyclerView.State state, @NonNull int[] extraLayoutSpace) {
            int pageLimit = ViewPager2.this.getOffscreenPageLimit();
            if (pageLimit == -1) {
                super.calculateExtraLayoutSpace(state, extraLayoutSpace);
                return;
            }
            int offscreenSpace = ViewPager2.this.getPageSize() * pageLimit;
            extraLayoutSpace[0] = offscreenSpace;
            extraLayoutSpace[1] = offscreenSpace;
        }

        public boolean requestChildRectangleOnScreen(@NonNull RecyclerView parent, @NonNull View child, @NonNull Rect rect, boolean immediate, boolean focusedChildVisible) {
            return false;
        }
    }

    public class PagerSnapHelperImpl extends PagerSnapHelper {
        PagerSnapHelperImpl() {
        }

        @Nullable
        public View findSnapView(RecyclerView.LayoutManager layoutManager) {
            if (ViewPager2.this.isFakeDragging()) {
                return null;
            }
            return super.findSnapView(layoutManager);
        }
    }

    public static class SmoothScrollToPosition implements Runnable {
        private final int mPosition;
        private final RecyclerView mRecyclerView;

        SmoothScrollToPosition(int position, RecyclerView recyclerView) {
            this.mPosition = position;
            this.mRecyclerView = recyclerView;
        }

        public void run() {
            this.mRecyclerView.smoothScrollToPosition(this.mPosition);
        }
    }

    public static abstract class OnPageChangeCallback {
        public void onPageScrolled(int position, float positionOffset, @Px int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    public void addItemDecoration(@NonNull RecyclerView.ItemDecoration decor) {
        this.mRecyclerView.addItemDecoration(decor);
    }

    public void addItemDecoration(@NonNull RecyclerView.ItemDecoration decor, int index) {
        this.mRecyclerView.addItemDecoration(decor, index);
    }

    @NonNull
    public RecyclerView.ItemDecoration getItemDecorationAt(int index) {
        return this.mRecyclerView.getItemDecorationAt(index);
    }

    public int getItemDecorationCount() {
        return this.mRecyclerView.getItemDecorationCount();
    }

    public void invalidateItemDecorations() {
        this.mRecyclerView.invalidateItemDecorations();
    }

    public void removeItemDecorationAt(int index) {
        this.mRecyclerView.removeItemDecorationAt(index);
    }

    public void removeItemDecoration(@NonNull RecyclerView.ItemDecoration decor) {
        this.mRecyclerView.removeItemDecoration(decor);
    }

    public abstract class AccessibilityProvider {
        private AccessibilityProvider() {
        }

        /* access modifiers changed from: package-private */
        public void onInitialize(@NonNull CompositeOnPageChangeCallback pageChangeEventDispatcher, @NonNull RecyclerView recyclerView) {
        }

        /* access modifiers changed from: package-private */
        public boolean handlesGetAccessibilityClassName() {
            return false;
        }

        /* access modifiers changed from: package-private */
        public String onGetAccessibilityClassName() {
            throw new IllegalStateException("Not implemented.");
        }

        /* access modifiers changed from: package-private */
        public void onRestorePendingState() {
        }

        /* access modifiers changed from: package-private */
        public void onAttachAdapter(@Nullable RecyclerView.Adapter<?> adapter) {
        }

        /* access modifiers changed from: package-private */
        public void onDetachAdapter(@Nullable RecyclerView.Adapter<?> adapter) {
        }

        /* access modifiers changed from: package-private */
        public void onSetOrientation() {
        }

        /* access modifiers changed from: package-private */
        public void onSetNewCurrentItem() {
        }

        /* access modifiers changed from: package-private */
        public void onSetUserInputEnabled() {
        }

        /* access modifiers changed from: package-private */
        public void onSetLayoutDirection() {
        }

        /* access modifiers changed from: package-private */
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        }

        /* access modifiers changed from: package-private */
        public boolean handlesPerformAccessibilityAction(int action, Bundle arguments) {
            return false;
        }

        /* access modifiers changed from: package-private */
        public boolean onPerformAccessibilityAction(int action, Bundle arguments) {
            throw new IllegalStateException("Not implemented.");
        }

        /* access modifiers changed from: package-private */
        public void onRvInitializeAccessibilityEvent(@NonNull AccessibilityEvent event) {
        }

        /* access modifiers changed from: package-private */
        public boolean handlesLmPerformAccessibilityAction(int action) {
            return false;
        }

        /* access modifiers changed from: package-private */
        public boolean onLmPerformAccessibilityAction(int action) {
            throw new IllegalStateException("Not implemented.");
        }

        /* access modifiers changed from: package-private */
        public void onLmInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfoCompat info) {
        }

        /* access modifiers changed from: package-private */
        public boolean handlesRvGetAccessibilityClassName() {
            return false;
        }

        /* access modifiers changed from: package-private */
        public CharSequence onRvGetAccessibilityClassName() {
            throw new IllegalStateException("Not implemented.");
        }
    }

    public class BasicAccessibilityProvider extends AccessibilityProvider {
        BasicAccessibilityProvider() {
            super();
        }

        public boolean handlesLmPerformAccessibilityAction(int action) {
            return (action == 8192 || action == 4096) && !ViewPager2.this.isUserInputEnabled();
        }

        public boolean onLmPerformAccessibilityAction(int action) {
            if (handlesLmPerformAccessibilityAction(action)) {
                return false;
            }
            throw new IllegalStateException();
        }

        public void onLmInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfoCompat info) {
            if (!ViewPager2.this.isUserInputEnabled()) {
                info.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD);
                info.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD);
                info.setScrollable(false);
            }
        }

        public boolean handlesRvGetAccessibilityClassName() {
            return true;
        }

        public CharSequence onRvGetAccessibilityClassName() {
            if (handlesRvGetAccessibilityClassName()) {
                return "androidx.viewpager.widget.ViewPager";
            }
            throw new IllegalStateException();
        }
    }

    public class PageAwareAccessibilityProvider extends AccessibilityProvider {
        private final AccessibilityViewCommand mActionPageBackward = new AccessibilityViewCommand() {
            public boolean perform(@NonNull View view, @Nullable AccessibilityViewCommand.CommandArguments arguments) {
                PageAwareAccessibilityProvider.this.setCurrentItemFromAccessibilityCommand(((ViewPager2) view).getCurrentItem() - 1);
                return true;
            }
        };
        private final AccessibilityViewCommand mActionPageForward = new AccessibilityViewCommand() {
            public boolean perform(@NonNull View view, @Nullable AccessibilityViewCommand.CommandArguments arguments) {
                PageAwareAccessibilityProvider.this.setCurrentItemFromAccessibilityCommand(((ViewPager2) view).getCurrentItem() + 1);
                return true;
            }
        };
        private RecyclerView.AdapterDataObserver mAdapterDataObserver;

        PageAwareAccessibilityProvider() {
            super();
        }

        public void onInitialize(@NonNull CompositeOnPageChangeCallback pageChangeEventDispatcher, @NonNull RecyclerView recyclerView) {
            ViewCompat.setImportantForAccessibility(recyclerView, 2);
            this.mAdapterDataObserver = new DataSetChangeObserver() {
                public void onChanged() {
                    PageAwareAccessibilityProvider.this.updatePageAccessibilityActions();
                }
            };
            if (ViewCompat.getImportantForAccessibility(ViewPager2.this) == 0) {
                ViewCompat.setImportantForAccessibility(ViewPager2.this, 1);
            }
        }

        public boolean handlesGetAccessibilityClassName() {
            return true;
        }

        public String onGetAccessibilityClassName() {
            if (handlesGetAccessibilityClassName()) {
                return "androidx.viewpager.widget.ViewPager";
            }
            throw new IllegalStateException();
        }

        public void onRestorePendingState() {
            updatePageAccessibilityActions();
        }

        public void onAttachAdapter(@Nullable RecyclerView.Adapter<?> newAdapter) {
            updatePageAccessibilityActions();
            if (newAdapter != null) {
                newAdapter.registerAdapterDataObserver(this.mAdapterDataObserver);
            }
        }

        public void onDetachAdapter(@Nullable RecyclerView.Adapter<?> oldAdapter) {
            if (oldAdapter != null) {
                oldAdapter.unregisterAdapterDataObserver(this.mAdapterDataObserver);
            }
        }

        public void onSetOrientation() {
            updatePageAccessibilityActions();
        }

        public void onSetNewCurrentItem() {
            updatePageAccessibilityActions();
        }

        public void onSetUserInputEnabled() {
            updatePageAccessibilityActions();
            if (Build.VERSION.SDK_INT < 21) {
                ViewPager2.this.sendAccessibilityEvent(2048);
            }
        }

        public void onSetLayoutDirection() {
            updatePageAccessibilityActions();
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
            addCollectionInfo(info);
            if (Build.VERSION.SDK_INT >= 16) {
                addScrollActions(info);
            }
        }

        public boolean handlesPerformAccessibilityAction(int action, Bundle arguments) {
            return action == 8192 || action == 4096;
        }

        public boolean onPerformAccessibilityAction(int action, Bundle arguments) {
            int nextItem;
            if (handlesPerformAccessibilityAction(action, arguments)) {
                if (action == 8192) {
                    nextItem = ViewPager2.this.getCurrentItem() - 1;
                } else {
                    nextItem = ViewPager2.this.getCurrentItem() + 1;
                }
                setCurrentItemFromAccessibilityCommand(nextItem);
                return true;
            }
            throw new IllegalStateException();
        }

        public void onRvInitializeAccessibilityEvent(@NonNull AccessibilityEvent event) {
            event.setSource(ViewPager2.this);
            event.setClassName(onGetAccessibilityClassName());
        }

        /* access modifiers changed from: package-private */
        public void setCurrentItemFromAccessibilityCommand(int item) {
            if (ViewPager2.this.isUserInputEnabled()) {
                ViewPager2.this.setCurrentItemInternal(item, true);
            }
        }

        /* access modifiers changed from: package-private */
        public void updatePageAccessibilityActions() {
            int itemCount;
            ViewPager2 viewPager = ViewPager2.this;
            int actionIdPageBackward = 16908360;
            ViewCompat.removeAccessibilityAction(viewPager, 16908360);
            ViewCompat.removeAccessibilityAction(viewPager, 16908361);
            ViewCompat.removeAccessibilityAction(viewPager, 16908358);
            ViewCompat.removeAccessibilityAction(viewPager, 16908359);
            if (ViewPager2.this.getAdapter() != null && (itemCount = ViewPager2.this.getAdapter().getItemCount()) != 0 && ViewPager2.this.isUserInputEnabled()) {
                if (ViewPager2.this.getOrientation() == 0) {
                    boolean isLayoutRtl = ViewPager2.this.isRtl();
                    int actionIdPageForward = isLayoutRtl ? 16908360 : 16908361;
                    if (isLayoutRtl) {
                        actionIdPageBackward = 16908361;
                    }
                    if (ViewPager2.this.mCurrentItem < itemCount - 1) {
                        ViewCompat.replaceAccessibilityAction(viewPager, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(actionIdPageForward, (CharSequence) null), (CharSequence) null, this.mActionPageForward);
                    }
                    if (ViewPager2.this.mCurrentItem > 0) {
                        ViewCompat.replaceAccessibilityAction(viewPager, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(actionIdPageBackward, (CharSequence) null), (CharSequence) null, this.mActionPageBackward);
                        return;
                    }
                    return;
                }
                if (ViewPager2.this.mCurrentItem < itemCount - 1) {
                    ViewCompat.replaceAccessibilityAction(viewPager, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16908359, (CharSequence) null), (CharSequence) null, this.mActionPageForward);
                }
                if (ViewPager2.this.mCurrentItem > 0) {
                    ViewCompat.replaceAccessibilityAction(viewPager, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16908358, (CharSequence) null), (CharSequence) null, this.mActionPageBackward);
                }
            }
        }

        private void addCollectionInfo(AccessibilityNodeInfo info) {
            int rowCount = 0;
            int colCount = 0;
            if (ViewPager2.this.getAdapter() != null) {
                if (ViewPager2.this.getOrientation() == 1) {
                    rowCount = ViewPager2.this.getAdapter().getItemCount();
                } else {
                    colCount = ViewPager2.this.getAdapter().getItemCount();
                }
            }
            AccessibilityNodeInfoCompat.wrap(info).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(rowCount, colCount, false, 0));
        }

        private void addScrollActions(AccessibilityNodeInfo info) {
            int itemCount;
            RecyclerView.Adapter<?> adapter = ViewPager2.this.getAdapter();
            if (adapter != null && (itemCount = adapter.getItemCount()) != 0 && ViewPager2.this.isUserInputEnabled()) {
                if (ViewPager2.this.mCurrentItem > 0) {
                    info.addAction(8192);
                }
                if (ViewPager2.this.mCurrentItem < itemCount - 1) {
                    info.addAction(4096);
                }
                info.setScrollable(true);
            }
        }
    }

    public static abstract class DataSetChangeObserver extends RecyclerView.AdapterDataObserver {
        public abstract void onChanged();

        private DataSetChangeObserver() {
        }

        public final void onItemRangeChanged(int positionStart, int itemCount) {
            onChanged();
        }

        public final void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            onChanged();
        }

        public final void onItemRangeInserted(int positionStart, int itemCount) {
            onChanged();
        }

        public final void onItemRangeRemoved(int positionStart, int itemCount) {
            onChanged();
        }

        public final void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            onChanged();
        }
    }
}

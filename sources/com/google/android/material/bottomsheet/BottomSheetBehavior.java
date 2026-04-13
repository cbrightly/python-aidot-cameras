package com.google.android.material.bottomsheet;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.VisibleForTesting;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import com.google.android.material.R;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    private static final int CORNER_ANIMATION_DURATION = 500;
    private static final int DEF_STYLE_RES = R.style.Widget_Design_BottomSheet_Modal;
    private static final float HIDE_FRICTION = 0.1f;
    private static final float HIDE_THRESHOLD = 0.5f;
    private static final int NO_WIDTH = -1;
    public static final int PEEK_HEIGHT_AUTO = -1;
    public static final int SAVE_ALL = -1;
    public static final int SAVE_FIT_TO_CONTENTS = 2;
    public static final int SAVE_HIDEABLE = 4;
    public static final int SAVE_NONE = 0;
    public static final int SAVE_PEEK_HEIGHT = 1;
    public static final int SAVE_SKIP_COLLAPSED = 8;
    private static final int SIGNIFICANT_VEL_THRESHOLD = 500;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_HALF_EXPANDED = 6;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "BottomSheetBehavior";
    int activePointerId;
    @NonNull
    private final ArrayList<BottomSheetCallback> callbacks = new ArrayList<>();
    private int childHeight;
    int collapsedOffset;
    private final ViewDragHelper.Callback dragCallback = new ViewDragHelper.Callback() {
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
            int i = bottomSheetBehavior.state;
            if (i == 1 || bottomSheetBehavior.touchingScrollingChild) {
                return false;
            }
            if (i == 3 && bottomSheetBehavior.activePointerId == pointerId) {
                WeakReference<View> weakReference = bottomSheetBehavior.nestedScrollingChildRef;
                View scroll = weakReference != null ? (View) weakReference.get() : null;
                if (scroll != null && scroll.canScrollVertically(-1)) {
                    return false;
                }
            }
            WeakReference<V> weakReference2 = BottomSheetBehavior.this.viewRef;
            if (weakReference2 == null || weakReference2.get() != child) {
                return false;
            }
            return true;
        }

        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            BottomSheetBehavior.this.dispatchOnSlide(top);
        }

        public void onViewDragStateChanged(int state) {
            if (state == 1 && BottomSheetBehavior.this.draggable) {
                BottomSheetBehavior.this.setStateInternal(1);
            }
        }

        private boolean releasedLow(@NonNull View child) {
            int top = child.getTop();
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
            return top > (bottomSheetBehavior.parentHeight + bottomSheetBehavior.getExpandedOffset()) / 2;
        }

        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            int targetState;
            int top;
            if (yvel >= 0.0f) {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                if (!bottomSheetBehavior.hideable || !bottomSheetBehavior.shouldHide(releasedChild, yvel)) {
                    if (yvel == 0.0f || Math.abs(xvel) > Math.abs(yvel)) {
                        int currentTop = releasedChild.getTop();
                        if (!BottomSheetBehavior.this.fitToContents) {
                            BottomSheetBehavior bottomSheetBehavior2 = BottomSheetBehavior.this;
                            int targetState2 = bottomSheetBehavior2.halfExpandedOffset;
                            if (currentTop < targetState2) {
                                if (currentTop < Math.abs(currentTop - bottomSheetBehavior2.collapsedOffset)) {
                                    top = BottomSheetBehavior.this.getExpandedOffset();
                                    targetState = 3;
                                } else {
                                    top = BottomSheetBehavior.this.halfExpandedOffset;
                                    targetState = 6;
                                }
                            } else if (Math.abs(currentTop - targetState2) < Math.abs(currentTop - BottomSheetBehavior.this.collapsedOffset)) {
                                top = BottomSheetBehavior.this.halfExpandedOffset;
                                targetState = 6;
                            } else {
                                top = BottomSheetBehavior.this.collapsedOffset;
                                targetState = 4;
                            }
                        } else if (Math.abs(currentTop - BottomSheetBehavior.this.fitToContentsOffset) < Math.abs(currentTop - BottomSheetBehavior.this.collapsedOffset)) {
                            top = BottomSheetBehavior.this.fitToContentsOffset;
                            targetState = 3;
                        } else {
                            top = BottomSheetBehavior.this.collapsedOffset;
                            targetState = 4;
                        }
                    } else if (BottomSheetBehavior.this.fitToContents) {
                        top = BottomSheetBehavior.this.collapsedOffset;
                        targetState = 4;
                    } else {
                        int currentTop2 = releasedChild.getTop();
                        if (Math.abs(currentTop2 - BottomSheetBehavior.this.halfExpandedOffset) < Math.abs(currentTop2 - BottomSheetBehavior.this.collapsedOffset)) {
                            top = BottomSheetBehavior.this.halfExpandedOffset;
                            targetState = 6;
                        } else {
                            top = BottomSheetBehavior.this.collapsedOffset;
                            targetState = 4;
                        }
                    }
                } else if ((Math.abs(xvel) < Math.abs(yvel) && yvel > 500.0f) || releasedLow(releasedChild)) {
                    top = BottomSheetBehavior.this.parentHeight;
                    targetState = 5;
                } else if (BottomSheetBehavior.this.fitToContents) {
                    top = BottomSheetBehavior.this.fitToContentsOffset;
                    targetState = 3;
                } else if (Math.abs(releasedChild.getTop() - BottomSheetBehavior.this.getExpandedOffset()) < Math.abs(releasedChild.getTop() - BottomSheetBehavior.this.halfExpandedOffset)) {
                    top = BottomSheetBehavior.this.getExpandedOffset();
                    targetState = 3;
                } else {
                    top = BottomSheetBehavior.this.halfExpandedOffset;
                    targetState = 6;
                }
            } else if (BottomSheetBehavior.this.fitToContents) {
                top = BottomSheetBehavior.this.fitToContentsOffset;
                targetState = 3;
            } else {
                int currentTop3 = releasedChild.getTop();
                BottomSheetBehavior bottomSheetBehavior3 = BottomSheetBehavior.this;
                if (currentTop3 > bottomSheetBehavior3.halfExpandedOffset) {
                    top = bottomSheetBehavior3.halfExpandedOffset;
                    targetState = 6;
                } else {
                    top = bottomSheetBehavior3.getExpandedOffset();
                    targetState = 3;
                }
            }
            BottomSheetBehavior.this.startSettlingAnimation(releasedChild, targetState, top, true);
        }

        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            int expandedOffset = BottomSheetBehavior.this.getExpandedOffset();
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
            return MathUtils.clamp(top, expandedOffset, bottomSheetBehavior.hideable ? bottomSheetBehavior.parentHeight : bottomSheetBehavior.collapsedOffset);
        }

        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return child.getLeft();
        }

        public int getViewVerticalDragRange(@NonNull View child) {
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
            if (bottomSheetBehavior.hideable) {
                return bottomSheetBehavior.parentHeight;
            }
            return bottomSheetBehavior.collapsedOffset;
        }
    };
    /* access modifiers changed from: private */
    public boolean draggable = true;
    float elevation = -1.0f;
    private int expandHalfwayActionId = -1;
    int expandedOffset;
    /* access modifiers changed from: private */
    public boolean fitToContents = true;
    int fitToContentsOffset;
    /* access modifiers changed from: private */
    public int gestureInsetBottom;
    private boolean gestureInsetBottomIgnored;
    int halfExpandedOffset;
    float halfExpandedRatio = 0.5f;
    boolean hideable;
    private boolean ignoreEvents;
    @Nullable
    private Map<View, Integer> importantForAccessibilityMap;
    private int initialY;
    /* access modifiers changed from: private */
    public int insetBottom;
    /* access modifiers changed from: private */
    public int insetTop;
    @Nullable
    private ValueAnimator interpolatorAnimator;
    private boolean isShapeExpanded;
    private int lastNestedScrollDy;
    /* access modifiers changed from: private */
    public MaterialShapeDrawable materialShapeDrawable;
    private int maxWidth = -1;
    private float maximumVelocity;
    private boolean nestedScrolled;
    @Nullable
    WeakReference<View> nestedScrollingChildRef;
    /* access modifiers changed from: private */
    public boolean paddingBottomSystemWindowInsets;
    /* access modifiers changed from: private */
    public boolean paddingLeftSystemWindowInsets;
    /* access modifiers changed from: private */
    public boolean paddingRightSystemWindowInsets;
    private boolean paddingTopSystemWindowInsets;
    int parentHeight;
    int parentWidth;
    /* access modifiers changed from: private */
    public int peekHeight;
    private boolean peekHeightAuto;
    private int peekHeightGestureInsetBuffer;
    private int peekHeightMin;
    private int saveFlags = 0;
    private BottomSheetBehavior<V>.SettleRunnable settleRunnable = null;
    private ShapeAppearanceModel shapeAppearanceModelDefault;
    private boolean shapeThemingEnabled;
    /* access modifiers changed from: private */
    public boolean skipCollapsed;
    int state = 4;
    boolean touchingScrollingChild;
    private boolean updateImportantForAccessibilityOnSiblings = false;
    @Nullable
    private VelocityTracker velocityTracker;
    @Nullable
    ViewDragHelper viewDragHelper;
    @Nullable
    WeakReference<V> viewRef;

    public static abstract class BottomSheetCallback {
        public abstract void onSlide(@NonNull View view, float f);

        public abstract void onStateChanged(@NonNull View view, int i);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SaveFlags {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public BottomSheetBehavior() {
    }

    public BottomSheetBehavior(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        int i;
        this.peekHeightGestureInsetBuffer = context.getResources().getDimensionPixelSize(R.dimen.mtrl_min_touch_target_size);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BottomSheetBehavior_Layout);
        this.shapeThemingEnabled = a.hasValue(R.styleable.BottomSheetBehavior_Layout_shapeAppearance);
        int i2 = R.styleable.BottomSheetBehavior_Layout_backgroundTint;
        boolean hasBackgroundTint = a.hasValue(i2);
        if (hasBackgroundTint) {
            createMaterialShapeDrawable(context, attrs, hasBackgroundTint, MaterialResources.getColorStateList(context, a, i2));
        } else {
            createMaterialShapeDrawable(context, attrs, hasBackgroundTint);
        }
        createShapeValueAnimator();
        if (Build.VERSION.SDK_INT >= 21) {
            this.elevation = a.getDimension(R.styleable.BottomSheetBehavior_Layout_android_elevation, -1.0f);
        }
        int i3 = R.styleable.BottomSheetBehavior_Layout_android_maxWidth;
        if (a.hasValue(i3)) {
            setMaxWidth(a.getDimensionPixelSize(i3, -1));
        }
        int i4 = R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight;
        TypedValue value = a.peekValue(i4);
        if (value == null || (i = value.data) != -1) {
            setPeekHeight(a.getDimensionPixelSize(i4, -1));
        } else {
            setPeekHeight(i);
        }
        setHideable(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        setGestureInsetBottomIgnored(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_gestureInsetBottomIgnored, false));
        setFitToContents(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_fitToContents, true));
        setSkipCollapsed(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        setDraggable(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_draggable, true));
        setSaveFlags(a.getInt(R.styleable.BottomSheetBehavior_Layout_behavior_saveFlags, 0));
        setHalfExpandedRatio(a.getFloat(R.styleable.BottomSheetBehavior_Layout_behavior_halfExpandedRatio, 0.5f));
        int i5 = R.styleable.BottomSheetBehavior_Layout_behavior_expandedOffset;
        TypedValue value2 = a.peekValue(i5);
        if (value2 == null || value2.type != 16) {
            setExpandedOffset(a.getDimensionPixelOffset(i5, 0));
        } else {
            setExpandedOffset(value2.data);
        }
        this.paddingBottomSystemWindowInsets = a.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingBottomSystemWindowInsets, false);
        this.paddingLeftSystemWindowInsets = a.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingLeftSystemWindowInsets, false);
        this.paddingRightSystemWindowInsets = a.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingRightSystemWindowInsets, false);
        this.paddingTopSystemWindowInsets = a.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingTopSystemWindowInsets, true);
        a.recycle();
        this.maximumVelocity = (float) ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    @NonNull
    public Parcelable onSaveInstanceState(@NonNull CoordinatorLayout parent, @NonNull V child) {
        return new SavedState(super.onSaveInstanceState(parent, child), (BottomSheetBehavior<?>) this);
    }

    public void onRestoreInstanceState(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull Parcelable state2) {
        SavedState ss = (SavedState) state2;
        super.onRestoreInstanceState(parent, child, ss.getSuperState());
        restoreOptionalState(ss);
        int i = ss.state;
        if (i == 1 || i == 2) {
            this.state = 4;
        } else {
            this.state = i;
        }
    }

    public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams layoutParams) {
        super.onAttachedToLayoutParams(layoutParams);
        this.viewRef = null;
        this.viewDragHelper = null;
    }

    public void onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams();
        this.viewRef = null;
        this.viewDragHelper = null;
    }

    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull final V child, int layoutDirection) {
        MaterialShapeDrawable materialShapeDrawable2;
        if (ViewCompat.getFitsSystemWindows(parent) && !ViewCompat.getFitsSystemWindows(child)) {
            child.setFitsSystemWindows(true);
        }
        if (this.viewRef == null) {
            this.peekHeightMin = parent.getResources().getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min);
            setWindowInsetsListener(child);
            this.viewRef = new WeakReference<>(child);
            if (this.shapeThemingEnabled && (materialShapeDrawable2 = this.materialShapeDrawable) != null) {
                ViewCompat.setBackground(child, materialShapeDrawable2);
            }
            MaterialShapeDrawable materialShapeDrawable3 = this.materialShapeDrawable;
            if (materialShapeDrawable3 != null) {
                float f = this.elevation;
                if (f == -1.0f) {
                    f = ViewCompat.getElevation(child);
                }
                materialShapeDrawable3.setElevation(f);
                boolean z = this.state == 3;
                this.isShapeExpanded = z;
                this.materialShapeDrawable.setInterpolation(z ? 0.0f : 1.0f);
            }
            updateAccessibilityActions();
            if (ViewCompat.getImportantForAccessibility(child) == 0) {
                ViewCompat.setImportantForAccessibility(child, 1);
            }
            int width = child.getMeasuredWidth();
            int i = this.maxWidth;
            if (width > i && i != -1) {
                final ViewGroup.LayoutParams lp = child.getLayoutParams();
                lp.width = this.maxWidth;
                child.post(new Runnable() {
                    public void run() {
                        child.setLayoutParams(lp);
                    }
                });
            }
        }
        if (this.viewDragHelper == null) {
            this.viewDragHelper = ViewDragHelper.create(parent, this.dragCallback);
        }
        int savedTop = child.getTop();
        parent.onLayoutChild(child, layoutDirection);
        this.parentWidth = parent.getWidth();
        this.parentHeight = parent.getHeight();
        int height = child.getHeight();
        this.childHeight = height;
        int i2 = this.parentHeight;
        int i3 = i2 - height;
        int i4 = this.insetTop;
        if (i3 < i4) {
            if (this.paddingTopSystemWindowInsets) {
                this.childHeight = i2;
            } else {
                this.childHeight = i2 - i4;
            }
        }
        this.fitToContentsOffset = Math.max(0, i2 - this.childHeight);
        calculateHalfExpandedOffset();
        calculateCollapsedOffset();
        int i5 = this.state;
        if (i5 == 3) {
            ViewCompat.offsetTopAndBottom(child, getExpandedOffset());
        } else if (i5 == 6) {
            ViewCompat.offsetTopAndBottom(child, this.halfExpandedOffset);
        } else if (this.hideable && i5 == 5) {
            ViewCompat.offsetTopAndBottom(child, this.parentHeight);
        } else if (i5 == 4) {
            ViewCompat.offsetTopAndBottom(child, this.collapsedOffset);
        } else if (i5 == 1 || i5 == 2) {
            ViewCompat.offsetTopAndBottom(child, savedTop - child.getTop());
        }
        this.nestedScrollingChildRef = new WeakReference<>(findScrollingChild(child));
        return true;
    }

    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull MotionEvent event) {
        ViewDragHelper viewDragHelper2;
        if (!child.isShown() || !this.draggable) {
            this.ignoreEvents = true;
            return false;
        }
        int action = event.getActionMasked();
        if (action == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(event);
        View scroll = null;
        switch (action) {
            case 0:
                int initialX = (int) event.getX();
                this.initialY = (int) event.getY();
                if (this.state != 2) {
                    WeakReference<View> weakReference = this.nestedScrollingChildRef;
                    View scroll2 = weakReference != null ? (View) weakReference.get() : null;
                    if (scroll2 != null && parent.isPointInChildBounds(scroll2, initialX, this.initialY)) {
                        this.activePointerId = event.getPointerId(event.getActionIndex());
                        this.touchingScrollingChild = true;
                    }
                }
                this.ignoreEvents = this.activePointerId == -1 && !parent.isPointInChildBounds(child, initialX, this.initialY);
                break;
            case 1:
            case 3:
                this.touchingScrollingChild = false;
                this.activePointerId = -1;
                if (this.ignoreEvents) {
                    this.ignoreEvents = false;
                    return false;
                }
                break;
        }
        if (!this.ignoreEvents && (viewDragHelper2 = this.viewDragHelper) != null && viewDragHelper2.shouldInterceptTouchEvent(event)) {
            return true;
        }
        WeakReference<View> weakReference2 = this.nestedScrollingChildRef;
        if (weakReference2 != null) {
            scroll = (View) weakReference2.get();
        }
        if (action != 2 || scroll == null || this.ignoreEvents || this.state == 1 || parent.isPointInChildBounds(scroll, (int) event.getX(), (int) event.getY()) || this.viewDragHelper == null || Math.abs(((float) this.initialY) - event.getY()) <= ((float) this.viewDragHelper.getTouchSlop())) {
            return false;
        }
        return true;
    }

    public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull MotionEvent event) {
        if (!child.isShown()) {
            return false;
        }
        int action = event.getActionMasked();
        if (this.state == 1 && action == 0) {
            return true;
        }
        ViewDragHelper viewDragHelper2 = this.viewDragHelper;
        if (viewDragHelper2 != null) {
            viewDragHelper2.processTouchEvent(event);
        }
        if (action == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(event);
        if (this.viewDragHelper != null && action == 2 && !this.ignoreEvents && Math.abs(((float) this.initialY) - event.getY()) > ((float) this.viewDragHelper.getTouchSlop())) {
            this.viewDragHelper.captureChildView(child, event.getPointerId(event.getActionIndex()));
        }
        return !this.ignoreEvents;
    }

    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        this.lastNestedScrollDy = 0;
        this.nestedScrolled = false;
        if ((axes & 2) != 0) {
            return true;
        }
        return false;
    }

    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (type != 1) {
            WeakReference<View> weakReference = this.nestedScrollingChildRef;
            if (target == (weakReference != null ? (View) weakReference.get() : null)) {
                int currentTop = child.getTop();
                int newTop = currentTop - dy;
                if (dy > 0) {
                    if (newTop < getExpandedOffset()) {
                        consumed[1] = currentTop - getExpandedOffset();
                        ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                        setStateInternal(3);
                    } else if (this.draggable) {
                        consumed[1] = dy;
                        ViewCompat.offsetTopAndBottom(child, -dy);
                        setStateInternal(1);
                    } else {
                        return;
                    }
                } else if (dy < 0 && !target.canScrollVertically(-1)) {
                    int i = this.collapsedOffset;
                    if (newTop > i && !this.hideable) {
                        consumed[1] = currentTop - i;
                        ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                        setStateInternal(4);
                    } else if (this.draggable) {
                        consumed[1] = dy;
                        ViewCompat.offsetTopAndBottom(child, -dy);
                        setStateInternal(1);
                    } else {
                        return;
                    }
                }
                dispatchOnSlide(child.getTop());
                this.lastNestedScrollDy = dy;
                this.nestedScrolled = true;
            }
        }
    }

    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int type) {
        int top;
        int currentTop;
        if (child.getTop() == getExpandedOffset()) {
            setStateInternal(3);
            return;
        }
        WeakReference<View> weakReference = this.nestedScrollingChildRef;
        if (weakReference != null && target == weakReference.get() && this.nestedScrolled) {
            if (this.lastNestedScrollDy > 0) {
                if (this.fitToContents) {
                    currentTop = this.fitToContentsOffset;
                    top = 3;
                } else if (child.getTop() > this.halfExpandedOffset) {
                    currentTop = this.halfExpandedOffset;
                    top = 6;
                } else {
                    currentTop = getExpandedOffset();
                    top = 3;
                }
            } else if (this.hideable != 0 && shouldHide(child, getYVelocity())) {
                currentTop = this.parentHeight;
                top = 5;
            } else if (this.lastNestedScrollDy == 0) {
                int currentTop2 = child.getTop();
                if (!this.fitToContents) {
                    int top2 = this.halfExpandedOffset;
                    if (currentTop2 < top2) {
                        if (currentTop2 < Math.abs(currentTop2 - this.collapsedOffset)) {
                            currentTop = getExpandedOffset();
                            top = 3;
                        } else {
                            currentTop = this.halfExpandedOffset;
                            top = 6;
                        }
                    } else if (Math.abs(currentTop2 - top2) < Math.abs(currentTop2 - this.collapsedOffset)) {
                        currentTop = this.halfExpandedOffset;
                        top = 6;
                    } else {
                        currentTop = this.collapsedOffset;
                        top = 4;
                    }
                } else if (Math.abs(currentTop2 - this.fitToContentsOffset) < Math.abs(currentTop2 - this.collapsedOffset)) {
                    currentTop = this.fitToContentsOffset;
                    top = 3;
                } else {
                    currentTop = this.collapsedOffset;
                    top = 4;
                }
            } else if (this.fitToContents != 0) {
                currentTop = this.collapsedOffset;
                top = 4;
            } else {
                int currentTop3 = child.getTop();
                if (Math.abs(currentTop3 - this.halfExpandedOffset) < Math.abs(currentTop3 - this.collapsedOffset)) {
                    currentTop = this.halfExpandedOffset;
                    top = 6;
                } else {
                    currentTop = this.collapsedOffset;
                    top = 4;
                }
            }
            startSettlingAnimation(child, top, currentTop, false);
            this.nestedScrolled = false;
        }
    }

    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
    }

    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, float velocityX, float velocityY) {
        WeakReference<View> weakReference = this.nestedScrollingChildRef;
        if (weakReference == null || target != weakReference.get()) {
            return false;
        }
        if (this.state != 3 || super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)) {
            return true;
        }
        return false;
    }

    public boolean isFitToContents() {
        return this.fitToContents;
    }

    public void setFitToContents(boolean fitToContents2) {
        if (this.fitToContents != fitToContents2) {
            this.fitToContents = fitToContents2;
            if (this.viewRef != null) {
                calculateCollapsedOffset();
            }
            setStateInternal((!this.fitToContents || this.state != 6) ? this.state : 3);
            updateAccessibilityActions();
        }
    }

    public void setMaxWidth(@Px int maxWidth2) {
        this.maxWidth = maxWidth2;
    }

    @Px
    public int getMaxWidth() {
        return this.maxWidth;
    }

    public void setPeekHeight(int peekHeight2) {
        setPeekHeight(peekHeight2, false);
    }

    public final void setPeekHeight(int peekHeight2, boolean animate) {
        boolean layout = false;
        if (peekHeight2 == -1) {
            if (!this.peekHeightAuto) {
                this.peekHeightAuto = true;
                layout = true;
            }
        } else if (this.peekHeightAuto || this.peekHeight != peekHeight2) {
            this.peekHeightAuto = false;
            this.peekHeight = Math.max(0, peekHeight2);
            layout = true;
        }
        if (layout) {
            updatePeekHeight(animate);
        }
    }

    /* access modifiers changed from: private */
    public void updatePeekHeight(boolean animate) {
        V view;
        if (this.viewRef != null) {
            calculateCollapsedOffset();
            if (this.state == 4 && (view = (View) this.viewRef.get()) != null) {
                if (animate) {
                    settleToStatePendingLayout(this.state);
                } else {
                    view.requestLayout();
                }
            }
        }
    }

    public int getPeekHeight() {
        if (this.peekHeightAuto) {
            return -1;
        }
        return this.peekHeight;
    }

    public void setHalfExpandedRatio(@FloatRange(from = 0.0d, to = 1.0d) float ratio) {
        if (ratio <= 0.0f || ratio >= 1.0f) {
            throw new IllegalArgumentException("ratio must be a float value between 0 and 1");
        }
        this.halfExpandedRatio = ratio;
        if (this.viewRef != null) {
            calculateHalfExpandedOffset();
        }
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getHalfExpandedRatio() {
        return this.halfExpandedRatio;
    }

    public void setExpandedOffset(int offset) {
        if (offset >= 0) {
            this.expandedOffset = offset;
            return;
        }
        throw new IllegalArgumentException("offset must be greater than or equal to 0");
    }

    public int getExpandedOffset() {
        if (this.fitToContents) {
            return this.fitToContentsOffset;
        }
        return Math.max(this.expandedOffset, this.paddingTopSystemWindowInsets ? 0 : this.insetTop);
    }

    public void setHideable(boolean hideable2) {
        if (this.hideable != hideable2) {
            this.hideable = hideable2;
            if (!hideable2 && this.state == 5) {
                setState(4);
            }
            updateAccessibilityActions();
        }
    }

    public boolean isHideable() {
        return this.hideable;
    }

    public void setSkipCollapsed(boolean skipCollapsed2) {
        this.skipCollapsed = skipCollapsed2;
    }

    public boolean getSkipCollapsed() {
        return this.skipCollapsed;
    }

    public void setDraggable(boolean draggable2) {
        this.draggable = draggable2;
    }

    public boolean isDraggable() {
        return this.draggable;
    }

    public void setSaveFlags(int flags) {
        this.saveFlags = flags;
    }

    public int getSaveFlags() {
        return this.saveFlags;
    }

    @Deprecated
    public void setBottomSheetCallback(BottomSheetCallback callback) {
        Log.w(TAG, "BottomSheetBehavior now supports multiple callbacks. `setBottomSheetCallback()` removes all existing callbacks, including ones set internally by library authors, which may result in unintended behavior. This may change in the future. Please use `addBottomSheetCallback()` and `removeBottomSheetCallback()` instead to set your own callbacks.");
        this.callbacks.clear();
        if (callback != null) {
            this.callbacks.add(callback);
        }
    }

    public void addBottomSheetCallback(@NonNull BottomSheetCallback callback) {
        if (!this.callbacks.contains(callback)) {
            this.callbacks.add(callback);
        }
    }

    public void removeBottomSheetCallback(@NonNull BottomSheetCallback callback) {
        this.callbacks.remove(callback);
    }

    public void setState(int state2) {
        if (state2 != this.state) {
            if (this.viewRef != null) {
                settleToStatePendingLayout(state2);
            } else if (state2 == 4 || state2 == 3 || state2 == 6 || (this.hideable && state2 == 5)) {
                this.state = state2;
            }
        }
    }

    public void setGestureInsetBottomIgnored(boolean gestureInsetBottomIgnored2) {
        this.gestureInsetBottomIgnored = gestureInsetBottomIgnored2;
    }

    public boolean isGestureInsetBottomIgnored() {
        return this.gestureInsetBottomIgnored;
    }

    private void settleToStatePendingLayout(int state2) {
        final V child = (View) this.viewRef.get();
        if (child != null) {
            ViewParent parent = child.getParent();
            if (parent == null || !parent.isLayoutRequested() || !ViewCompat.isAttachedToWindow(child)) {
                settleToState(child, state2);
                return;
            }
            final int finalState = state2;
            child.post(new Runnable() {
                public void run() {
                    BottomSheetBehavior.this.settleToState(child, finalState);
                }
            });
        }
    }

    public int getState() {
        return this.state;
    }

    /* access modifiers changed from: package-private */
    public void setStateInternal(int state2) {
        View bottomSheet;
        if (this.state != state2) {
            this.state = state2;
            WeakReference<V> weakReference = this.viewRef;
            if (weakReference != null && (bottomSheet = (View) weakReference.get()) != null) {
                if (state2 == 3) {
                    updateImportantForAccessibility(true);
                } else if (state2 == 6 || state2 == 5 || state2 == 4) {
                    updateImportantForAccessibility(false);
                }
                updateDrawableForTargetState(state2);
                for (int i = 0; i < this.callbacks.size(); i++) {
                    this.callbacks.get(i).onStateChanged(bottomSheet, state2);
                }
                updateAccessibilityActions();
            }
        }
    }

    private void updateDrawableForTargetState(int state2) {
        ValueAnimator valueAnimator;
        if (state2 != 2) {
            boolean expand = state2 == 3;
            if (this.isShapeExpanded != expand) {
                this.isShapeExpanded = expand;
                if (this.materialShapeDrawable != null && (valueAnimator = this.interpolatorAnimator) != null) {
                    if (valueAnimator.isRunning()) {
                        this.interpolatorAnimator.reverse();
                        return;
                    }
                    float to = expand ? 0.0f : 1.0f;
                    this.interpolatorAnimator.setFloatValues(new float[]{1.0f - to, to});
                    this.interpolatorAnimator.start();
                }
            }
        }
    }

    private int calculatePeekHeight() {
        int i;
        if (this.peekHeightAuto) {
            return Math.min(Math.max(this.peekHeightMin, this.parentHeight - ((this.parentWidth * 9) / 16)), this.childHeight) + this.insetBottom;
        }
        if (this.gestureInsetBottomIgnored != 0 || this.paddingBottomSystemWindowInsets || (i = this.gestureInsetBottom) <= 0) {
            return this.peekHeight + this.insetBottom;
        }
        return Math.max(this.peekHeight, i + this.peekHeightGestureInsetBuffer);
    }

    private void calculateCollapsedOffset() {
        int peek = calculatePeekHeight();
        if (this.fitToContents) {
            this.collapsedOffset = Math.max(this.parentHeight - peek, this.fitToContentsOffset);
        } else {
            this.collapsedOffset = this.parentHeight - peek;
        }
    }

    private void calculateHalfExpandedOffset() {
        this.halfExpandedOffset = (int) (((float) this.parentHeight) * (1.0f - this.halfExpandedRatio));
    }

    private void reset() {
        this.activePointerId = -1;
        VelocityTracker velocityTracker2 = this.velocityTracker;
        if (velocityTracker2 != null) {
            velocityTracker2.recycle();
            this.velocityTracker = null;
        }
    }

    private void restoreOptionalState(@NonNull SavedState ss) {
        int i = this.saveFlags;
        if (i != 0) {
            if (i == -1 || (i & 1) == 1) {
                this.peekHeight = ss.peekHeight;
            }
            if (i == -1 || (i & 2) == 2) {
                this.fitToContents = ss.fitToContents;
            }
            if (i == -1 || (i & 4) == 4) {
                this.hideable = ss.hideable;
            }
            if (i == -1 || (i & 8) == 8) {
                this.skipCollapsed = ss.skipCollapsed;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean shouldHide(@NonNull View child, float yvel) {
        if (this.skipCollapsed) {
            return true;
        }
        if (child.getTop() < this.collapsedOffset) {
            return false;
        }
        if (Math.abs((((float) child.getTop()) + (0.1f * yvel)) - ((float) this.collapsedOffset)) / ((float) calculatePeekHeight()) > 0.5f) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    @Nullable
    public View findScrollingChild(View view) {
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup group = (ViewGroup) view;
        int count = group.getChildCount();
        for (int i = 0; i < count; i++) {
            View scrollingChild = findScrollingChild(group.getChildAt(i));
            if (scrollingChild != null) {
                return scrollingChild;
            }
        }
        return null;
    }

    private void createMaterialShapeDrawable(@NonNull Context context, AttributeSet attrs, boolean hasBackgroundTint) {
        createMaterialShapeDrawable(context, attrs, hasBackgroundTint, (ColorStateList) null);
    }

    private void createMaterialShapeDrawable(@NonNull Context context, AttributeSet attrs, boolean hasBackgroundTint, @Nullable ColorStateList bottomSheetColor) {
        if (this.shapeThemingEnabled) {
            this.shapeAppearanceModelDefault = ShapeAppearanceModel.builder(context, attrs, R.attr.bottomSheetStyle, DEF_STYLE_RES).build();
            MaterialShapeDrawable materialShapeDrawable2 = new MaterialShapeDrawable(this.shapeAppearanceModelDefault);
            this.materialShapeDrawable = materialShapeDrawable2;
            materialShapeDrawable2.initializeElevationOverlay(context);
            if (!hasBackgroundTint || bottomSheetColor == null) {
                TypedValue defaultColor = new TypedValue();
                context.getTheme().resolveAttribute(16842801, defaultColor, true);
                this.materialShapeDrawable.setTint(defaultColor.data);
                return;
            }
            this.materialShapeDrawable.setFillColor(bottomSheetColor);
        }
    }

    /* access modifiers changed from: package-private */
    public MaterialShapeDrawable getMaterialShapeDrawable() {
        return this.materialShapeDrawable;
    }

    private void createShapeValueAnimator() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.interpolatorAnimator = ofFloat;
        ofFloat.setDuration(500);
        this.interpolatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                float value = ((Float) animation.getAnimatedValue()).floatValue();
                if (BottomSheetBehavior.this.materialShapeDrawable != null) {
                    BottomSheetBehavior.this.materialShapeDrawable.setInterpolation(value);
                }
            }
        });
    }

    private void setWindowInsetsListener(@NonNull View child) {
        final boolean shouldHandleGestureInsets = Build.VERSION.SDK_INT >= 29 && !isGestureInsetBottomIgnored() && !this.peekHeightAuto;
        if (this.paddingBottomSystemWindowInsets || this.paddingLeftSystemWindowInsets || this.paddingRightSystemWindowInsets || shouldHandleGestureInsets) {
            ViewUtils.doOnApplyWindowInsets(child, new ViewUtils.OnApplyWindowInsetsListener() {
                public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets, ViewUtils.RelativePadding initialPadding) {
                    int unused = BottomSheetBehavior.this.insetTop = insets.getSystemWindowInsetTop();
                    boolean isRtl = ViewUtils.isLayoutRtl(view);
                    int bottomPadding = view.getPaddingBottom();
                    int leftPadding = view.getPaddingLeft();
                    int rightPadding = view.getPaddingRight();
                    if (BottomSheetBehavior.this.paddingBottomSystemWindowInsets) {
                        int unused2 = BottomSheetBehavior.this.insetBottom = insets.getSystemWindowInsetBottom();
                        bottomPadding = initialPadding.bottom + BottomSheetBehavior.this.insetBottom;
                    }
                    if (BottomSheetBehavior.this.paddingLeftSystemWindowInsets) {
                        leftPadding = (isRtl ? initialPadding.end : initialPadding.start) + insets.getSystemWindowInsetLeft();
                    }
                    if (BottomSheetBehavior.this.paddingRightSystemWindowInsets) {
                        rightPadding = (isRtl ? initialPadding.start : initialPadding.end) + insets.getSystemWindowInsetRight();
                    }
                    view.setPadding(leftPadding, view.getPaddingTop(), rightPadding, bottomPadding);
                    if (shouldHandleGestureInsets) {
                        int unused3 = BottomSheetBehavior.this.gestureInsetBottom = insets.getMandatorySystemGestureInsets().bottom;
                    }
                    if (BottomSheetBehavior.this.paddingBottomSystemWindowInsets || shouldHandleGestureInsets) {
                        BottomSheetBehavior.this.updatePeekHeight(false);
                    }
                    return insets;
                }
            });
        }
    }

    private float getYVelocity() {
        VelocityTracker velocityTracker2 = this.velocityTracker;
        if (velocityTracker2 == null) {
            return 0.0f;
        }
        velocityTracker2.computeCurrentVelocity(1000, this.maximumVelocity);
        return this.velocityTracker.getYVelocity(this.activePointerId);
    }

    /* access modifiers changed from: package-private */
    public void settleToState(@NonNull View child, int state2) {
        int top;
        if (state2 == 4) {
            top = this.collapsedOffset;
        } else if (state2 == 6) {
            top = this.halfExpandedOffset;
            if (this.fitToContents && top <= this.fitToContentsOffset) {
                state2 = 3;
                top = this.fitToContentsOffset;
            }
        } else if (state2 == 3) {
            top = getExpandedOffset();
        } else if (this.hideable == 0 || state2 != 5) {
            throw new IllegalArgumentException("Illegal state argument: " + state2);
        } else {
            top = this.parentHeight;
        }
        startSettlingAnimation(child, state2, top, false);
    }

    /* access modifiers changed from: package-private */
    public void startSettlingAnimation(View child, int state2, int top, boolean settleFromViewDragHelper) {
        ViewDragHelper viewDragHelper2 = this.viewDragHelper;
        if (viewDragHelper2 != null && (!settleFromViewDragHelper ? viewDragHelper2.smoothSlideViewTo(child, child.getLeft(), top) : viewDragHelper2.settleCapturedViewAt(child.getLeft(), top))) {
            setStateInternal(2);
            updateDrawableForTargetState(state2);
            if (this.settleRunnable == null) {
                this.settleRunnable = new SettleRunnable(child, state2);
            }
            if (!this.settleRunnable.isPosted) {
                BottomSheetBehavior<V>.SettleRunnable settleRunnable2 = this.settleRunnable;
                settleRunnable2.targetState = state2;
                ViewCompat.postOnAnimation(child, settleRunnable2);
                boolean unused = this.settleRunnable.isPosted = true;
                return;
            }
            this.settleRunnable.targetState = state2;
            return;
        }
        setStateInternal(state2);
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnSlide(int top) {
        float f;
        View bottomSheet = (View) this.viewRef.get();
        if (bottomSheet != null && !this.callbacks.isEmpty()) {
            int i = this.collapsedOffset;
            if (top > i || i == getExpandedOffset()) {
                int i2 = this.collapsedOffset;
                f = ((float) (i2 - top)) / ((float) (this.parentHeight - i2));
            } else {
                int i3 = this.collapsedOffset;
                f = ((float) (i3 - top)) / ((float) (i3 - getExpandedOffset()));
            }
            float slideOffset = f;
            for (int i4 = 0; i4 < this.callbacks.size(); i4++) {
                this.callbacks.get(i4).onSlide(bottomSheet, slideOffset);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public int getPeekHeightMin() {
        return this.peekHeightMin;
    }

    @VisibleForTesting
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void disableShapeAnimations() {
        this.interpolatorAnimator = null;
    }

    public class SettleRunnable implements Runnable {
        /* access modifiers changed from: private */
        public boolean isPosted;
        int targetState;
        private final View view;

        SettleRunnable(View view2, int targetState2) {
            this.view = view2;
            this.targetState = targetState2;
        }

        public void run() {
            ViewDragHelper viewDragHelper = BottomSheetBehavior.this.viewDragHelper;
            if (viewDragHelper == null || !viewDragHelper.continueSettling(true)) {
                BottomSheetBehavior.this.setStateInternal(this.targetState);
            } else {
                ViewCompat.postOnAnimation(this.view, this);
            }
            this.isPosted = false;
        }
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
            @NonNull
            public SavedState createFromParcel(@NonNull Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            @Nullable
            public SavedState createFromParcel(@NonNull Parcel in) {
                return new SavedState(in, (ClassLoader) null);
            }

            @NonNull
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        boolean fitToContents;
        boolean hideable;
        int peekHeight;
        boolean skipCollapsed;
        final int state;

        public SavedState(@NonNull Parcel source) {
            this(source, (ClassLoader) null);
        }

        public SavedState(@NonNull Parcel source, ClassLoader loader) {
            super(source, loader);
            this.state = source.readInt();
            this.peekHeight = source.readInt();
            boolean z = false;
            this.fitToContents = source.readInt() == 1;
            this.hideable = source.readInt() == 1;
            this.skipCollapsed = source.readInt() == 1 ? true : z;
        }

        public SavedState(Parcelable superState, @NonNull BottomSheetBehavior<?> behavior) {
            super(superState);
            this.state = behavior.state;
            this.peekHeight = behavior.peekHeight;
            this.fitToContents = behavior.fitToContents;
            this.hideable = behavior.hideable;
            this.skipCollapsed = behavior.skipCollapsed;
        }

        @Deprecated
        public SavedState(Parcelable superstate, int state2) {
            super(superstate);
            this.state = state2;
        }

        public void writeToParcel(@NonNull Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.state);
            out.writeInt(this.peekHeight);
            out.writeInt(this.fitToContents ? 1 : 0);
            out.writeInt(this.hideable ? 1 : 0);
            out.writeInt(this.skipCollapsed ? 1 : 0);
        }
    }

    @NonNull
    public static <V extends View> BottomSheetBehavior<V> from(@NonNull V view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof CoordinatorLayout.LayoutParams) {
            CoordinatorLayout.Behavior<?> behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
            if (behavior instanceof BottomSheetBehavior) {
                return (BottomSheetBehavior) behavior;
            }
            throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
        }
        throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
    }

    public void setUpdateImportantForAccessibilityOnSiblings(boolean updateImportantForAccessibilityOnSiblings2) {
        this.updateImportantForAccessibilityOnSiblings = updateImportantForAccessibilityOnSiblings2;
    }

    private void updateImportantForAccessibility(boolean expanded) {
        Map<View, Integer> map;
        WeakReference<V> weakReference = this.viewRef;
        if (weakReference != null) {
            ViewParent viewParent = ((View) weakReference.get()).getParent();
            if (viewParent instanceof CoordinatorLayout) {
                CoordinatorLayout parent = (CoordinatorLayout) viewParent;
                int childCount = parent.getChildCount();
                if (Build.VERSION.SDK_INT >= 16 && expanded) {
                    if (this.importantForAccessibilityMap == null) {
                        this.importantForAccessibilityMap = new HashMap(childCount);
                    } else {
                        return;
                    }
                }
                for (int i = 0; i < childCount; i++) {
                    View child = parent.getChildAt(i);
                    if (child != this.viewRef.get()) {
                        if (expanded) {
                            if (Build.VERSION.SDK_INT >= 16) {
                                this.importantForAccessibilityMap.put(child, Integer.valueOf(child.getImportantForAccessibility()));
                            }
                            if (this.updateImportantForAccessibilityOnSiblings) {
                                ViewCompat.setImportantForAccessibility(child, 4);
                            }
                        } else if (this.updateImportantForAccessibilityOnSiblings && (map = this.importantForAccessibilityMap) != null && map.containsKey(child)) {
                            ViewCompat.setImportantForAccessibility(child, this.importantForAccessibilityMap.get(child).intValue());
                        }
                    }
                }
                if (!expanded) {
                    this.importantForAccessibilityMap = null;
                } else if (this.updateImportantForAccessibilityOnSiblings) {
                    ((View) this.viewRef.get()).sendAccessibilityEvent(8);
                }
            }
        }
    }

    private void updateAccessibilityActions() {
        V child;
        WeakReference<V> weakReference = this.viewRef;
        if (weakReference != null && (child = (View) weakReference.get()) != null) {
            ViewCompat.removeAccessibilityAction(child, 524288);
            ViewCompat.removeAccessibilityAction(child, 262144);
            ViewCompat.removeAccessibilityAction(child, 1048576);
            int i = this.expandHalfwayActionId;
            if (i != -1) {
                ViewCompat.removeAccessibilityAction(child, i);
            }
            int nextState = 6;
            if (!this.fitToContents && this.state != 6) {
                this.expandHalfwayActionId = addAccessibilityActionForState(child, R.string.bottomsheet_action_expand_halfway, 6);
            }
            if (this.hideable && this.state != 5) {
                replaceAccessibilityActionForState(child, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, 5);
            }
            switch (this.state) {
                case 3:
                    if (this.fitToContents != 0) {
                        nextState = 4;
                    }
                    replaceAccessibilityActionForState(child, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, nextState);
                    return;
                case 4:
                    if (this.fitToContents) {
                        nextState = 3;
                    }
                    replaceAccessibilityActionForState(child, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, nextState);
                    return;
                case 6:
                    replaceAccessibilityActionForState(child, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, 4);
                    replaceAccessibilityActionForState(child, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, 3);
                    return;
                default:
                    return;
            }
        }
    }

    private void replaceAccessibilityActionForState(V child, AccessibilityNodeInfoCompat.AccessibilityActionCompat action, int state2) {
        ViewCompat.replaceAccessibilityAction(child, action, (CharSequence) null, createAccessibilityViewCommandForState(state2));
    }

    private int addAccessibilityActionForState(V child, @StringRes int stringResId, int state2) {
        return ViewCompat.addAccessibilityAction(child, child.getResources().getString(stringResId), createAccessibilityViewCommandForState(state2));
    }

    private AccessibilityViewCommand createAccessibilityViewCommandForState(final int state2) {
        return new AccessibilityViewCommand() {
            public boolean perform(@NonNull View view, @Nullable AccessibilityViewCommand.CommandArguments arguments) {
                BottomSheetBehavior.this.setState(state2);
                return true;
            }
        };
    }
}

package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.R;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ItemTouchHelper extends RecyclerView.ItemDecoration implements RecyclerView.OnChildAttachStateChangeListener {
    static final int ACTION_MODE_DRAG_MASK = 16711680;
    private static final int ACTION_MODE_IDLE_MASK = 255;
    static final int ACTION_MODE_SWIPE_MASK = 65280;
    public static final int ACTION_STATE_DRAG = 2;
    public static final int ACTION_STATE_IDLE = 0;
    public static final int ACTION_STATE_SWIPE = 1;
    private static final int ACTIVE_POINTER_ID_NONE = -1;
    public static final int ANIMATION_TYPE_DRAG = 8;
    public static final int ANIMATION_TYPE_SWIPE_CANCEL = 4;
    public static final int ANIMATION_TYPE_SWIPE_SUCCESS = 2;
    private static final boolean DEBUG = false;
    static final int DIRECTION_FLAG_COUNT = 8;
    public static final int DOWN = 2;
    public static final int END = 32;
    public static final int LEFT = 4;
    private static final int PIXELS_PER_SECOND = 1000;
    public static final int RIGHT = 8;
    public static final int START = 16;
    private static final String TAG = "ItemTouchHelper";
    public static final int UP = 1;
    private int mActionState = 0;
    int mActivePointerId = -1;
    @NonNull
    Callback mCallback;
    private RecyclerView.ChildDrawingOrderCallback mChildDrawingOrderCallback = null;
    private List<Integer> mDistances;
    private long mDragScrollStartTimeInMs;
    float mDx;
    float mDy;
    GestureDetectorCompat mGestureDetector;
    float mInitialTouchX;
    float mInitialTouchY;
    private ItemTouchHelperGestureListener mItemTouchHelperGestureListener;
    private float mMaxSwipeVelocity;
    private final RecyclerView.OnItemTouchListener mOnItemTouchListener = new RecyclerView.OnItemTouchListener() {
        public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent event) {
            int index;
            RecoverAnimation animation;
            ItemTouchHelper.this.mGestureDetector.onTouchEvent(event);
            int action = event.getActionMasked();
            if (action == 0) {
                ItemTouchHelper.this.mActivePointerId = event.getPointerId(0);
                ItemTouchHelper.this.mInitialTouchX = event.getX();
                ItemTouchHelper.this.mInitialTouchY = event.getY();
                ItemTouchHelper.this.obtainVelocityTracker();
                ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                if (itemTouchHelper.mSelected == null && (animation = itemTouchHelper.findAnimation(event)) != null) {
                    ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                    itemTouchHelper2.mInitialTouchX -= animation.mX;
                    itemTouchHelper2.mInitialTouchY -= animation.mY;
                    itemTouchHelper2.endRecoverAnimation(animation.mViewHolder, true);
                    if (ItemTouchHelper.this.mPendingCleanup.remove(animation.mViewHolder.itemView)) {
                        ItemTouchHelper itemTouchHelper3 = ItemTouchHelper.this;
                        itemTouchHelper3.mCallback.clearView(itemTouchHelper3.mRecyclerView, animation.mViewHolder);
                    }
                    ItemTouchHelper.this.select(animation.mViewHolder, animation.mActionState);
                    ItemTouchHelper itemTouchHelper4 = ItemTouchHelper.this;
                    itemTouchHelper4.updateDxDy(event, itemTouchHelper4.mSelectedFlags, 0);
                }
            } else if (action == 3 || action == 1) {
                ItemTouchHelper itemTouchHelper5 = ItemTouchHelper.this;
                itemTouchHelper5.mActivePointerId = -1;
                itemTouchHelper5.select((RecyclerView.ViewHolder) null, 0);
            } else {
                int i = ItemTouchHelper.this.mActivePointerId;
                if (i != -1 && (index = event.findPointerIndex(i)) >= 0) {
                    ItemTouchHelper.this.checkSelectForSwipe(action, event, index);
                }
            }
            VelocityTracker velocityTracker = ItemTouchHelper.this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.addMovement(event);
            }
            if (ItemTouchHelper.this.mSelected != null) {
                return true;
            }
            return false;
        }

        public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent event) {
            ItemTouchHelper.this.mGestureDetector.onTouchEvent(event);
            VelocityTracker velocityTracker = ItemTouchHelper.this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.addMovement(event);
            }
            if (ItemTouchHelper.this.mActivePointerId != -1) {
                int action = event.getActionMasked();
                int activePointerIndex = event.findPointerIndex(ItemTouchHelper.this.mActivePointerId);
                if (activePointerIndex >= 0) {
                    ItemTouchHelper.this.checkSelectForSwipe(action, event, activePointerIndex);
                }
                ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                RecyclerView.ViewHolder viewHolder = itemTouchHelper.mSelected;
                if (viewHolder != null) {
                    int newPointerIndex = 0;
                    switch (action) {
                        case 1:
                            break;
                        case 2:
                            if (activePointerIndex >= 0) {
                                itemTouchHelper.updateDxDy(event, itemTouchHelper.mSelectedFlags, activePointerIndex);
                                ItemTouchHelper.this.moveIfNecessary(viewHolder);
                                ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                                itemTouchHelper2.mRecyclerView.removeCallbacks(itemTouchHelper2.mScrollRunnable);
                                ItemTouchHelper.this.mScrollRunnable.run();
                                ItemTouchHelper.this.mRecyclerView.invalidate();
                                return;
                            }
                            return;
                        case 3:
                            VelocityTracker velocityTracker2 = itemTouchHelper.mVelocityTracker;
                            if (velocityTracker2 != null) {
                                velocityTracker2.clear();
                                break;
                            }
                            break;
                        case 6:
                            int pointerIndex = event.getActionIndex();
                            int pointerId = event.getPointerId(pointerIndex);
                            ItemTouchHelper itemTouchHelper3 = ItemTouchHelper.this;
                            if (pointerId == itemTouchHelper3.mActivePointerId) {
                                if (pointerIndex == 0) {
                                    newPointerIndex = 1;
                                }
                                itemTouchHelper3.mActivePointerId = event.getPointerId(newPointerIndex);
                                ItemTouchHelper itemTouchHelper4 = ItemTouchHelper.this;
                                itemTouchHelper4.updateDxDy(event, itemTouchHelper4.mSelectedFlags, pointerIndex);
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                    ItemTouchHelper.this.select((RecyclerView.ViewHolder) null, 0);
                    ItemTouchHelper.this.mActivePointerId = -1;
                }
            }
        }

        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            if (disallowIntercept) {
                ItemTouchHelper.this.select((RecyclerView.ViewHolder) null, 0);
            }
        }
    };
    View mOverdrawChild = null;
    int mOverdrawChildPosition = -1;
    final List<View> mPendingCleanup = new ArrayList();
    @VisibleForTesting
    List<RecoverAnimation> mRecoverAnimations = new ArrayList();
    RecyclerView mRecyclerView;
    final Runnable mScrollRunnable = new Runnable() {
        public void run() {
            ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
            if (itemTouchHelper.mSelected != null && itemTouchHelper.scrollIfNecessary()) {
                ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                RecyclerView.ViewHolder viewHolder = itemTouchHelper2.mSelected;
                if (viewHolder != null) {
                    itemTouchHelper2.moveIfNecessary(viewHolder);
                }
                ItemTouchHelper itemTouchHelper3 = ItemTouchHelper.this;
                itemTouchHelper3.mRecyclerView.removeCallbacks(itemTouchHelper3.mScrollRunnable);
                ViewCompat.postOnAnimation(ItemTouchHelper.this.mRecyclerView, this);
            }
        }
    };
    RecyclerView.ViewHolder mSelected = null;
    int mSelectedFlags;
    private float mSelectedStartX;
    private float mSelectedStartY;
    private int mSlop;
    private List<RecyclerView.ViewHolder> mSwapTargets;
    private float mSwipeEscapeVelocity;
    private final float[] mTmpPosition = new float[2];
    private Rect mTmpRect;
    VelocityTracker mVelocityTracker;

    public interface ViewDropHandler {
        void prepareForDrop(@NonNull View view, @NonNull View view2, int i, int i2);
    }

    public ItemTouchHelper(@NonNull Callback callback) {
        this.mCallback = callback;
    }

    private static boolean hitTest(View child, float x, float y, float left, float top) {
        return x >= left && x <= ((float) child.getWidth()) + left && y >= top && y <= ((float) child.getHeight()) + top;
    }

    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.mRecyclerView;
        if (recyclerView2 != recyclerView) {
            if (recyclerView2 != null) {
                destroyCallbacks();
            }
            this.mRecyclerView = recyclerView;
            if (recyclerView != null) {
                Resources resources = recyclerView.getResources();
                this.mSwipeEscapeVelocity = resources.getDimension(R.dimen.item_touch_helper_swipe_escape_velocity);
                this.mMaxSwipeVelocity = resources.getDimension(R.dimen.item_touch_helper_swipe_escape_max_velocity);
                setupCallbacks();
            }
        }
    }

    private void setupCallbacks() {
        this.mSlop = ViewConfiguration.get(this.mRecyclerView.getContext()).getScaledTouchSlop();
        this.mRecyclerView.addItemDecoration(this);
        this.mRecyclerView.addOnItemTouchListener(this.mOnItemTouchListener);
        this.mRecyclerView.addOnChildAttachStateChangeListener(this);
        startGestureDetection();
    }

    private void destroyCallbacks() {
        this.mRecyclerView.removeItemDecoration(this);
        this.mRecyclerView.removeOnItemTouchListener(this.mOnItemTouchListener);
        this.mRecyclerView.removeOnChildAttachStateChangeListener(this);
        for (int i = this.mRecoverAnimations.size() - 1; i >= 0; i--) {
            RecoverAnimation recoverAnimation = this.mRecoverAnimations.get(0);
            recoverAnimation.cancel();
            this.mCallback.clearView(this.mRecyclerView, recoverAnimation.mViewHolder);
        }
        this.mRecoverAnimations.clear();
        this.mOverdrawChild = null;
        this.mOverdrawChildPosition = -1;
        releaseVelocityTracker();
        stopGestureDetection();
    }

    private void startGestureDetection() {
        this.mItemTouchHelperGestureListener = new ItemTouchHelperGestureListener();
        this.mGestureDetector = new GestureDetectorCompat(this.mRecyclerView.getContext(), this.mItemTouchHelperGestureListener);
    }

    private void stopGestureDetection() {
        ItemTouchHelperGestureListener itemTouchHelperGestureListener = this.mItemTouchHelperGestureListener;
        if (itemTouchHelperGestureListener != null) {
            itemTouchHelperGestureListener.doNotReactToLongPress();
            this.mItemTouchHelperGestureListener = null;
        }
        if (this.mGestureDetector != null) {
            this.mGestureDetector = null;
        }
    }

    private void getSelectedDxDy(float[] outPosition) {
        if ((this.mSelectedFlags & 12) != 0) {
            outPosition[0] = (this.mSelectedStartX + this.mDx) - ((float) this.mSelected.itemView.getLeft());
        } else {
            outPosition[0] = this.mSelected.itemView.getTranslationX();
        }
        if ((this.mSelectedFlags & 3) != 0) {
            outPosition[1] = (this.mSelectedStartY + this.mDy) - ((float) this.mSelected.itemView.getTop());
        } else {
            outPosition[1] = this.mSelected.itemView.getTranslationY();
        }
    }

    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        float dx = 0.0f;
        float dy = 0.0f;
        if (this.mSelected != null) {
            getSelectedDxDy(this.mTmpPosition);
            float[] fArr = this.mTmpPosition;
            dx = fArr[0];
            dy = fArr[1];
        }
        this.mCallback.onDrawOver(c, parent, this.mSelected, this.mRecoverAnimations, this.mActionState, dx, dy);
    }

    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        this.mOverdrawChildPosition = -1;
        float dx = 0.0f;
        float dy = 0.0f;
        if (this.mSelected != null) {
            getSelectedDxDy(this.mTmpPosition);
            float[] fArr = this.mTmpPosition;
            dx = fArr[0];
            dy = fArr[1];
        }
        this.mCallback.onDraw(c, parent, this.mSelected, this.mRecoverAnimations, this.mActionState, dx, dy);
    }

    /* access modifiers changed from: package-private */
    public void select(@Nullable RecyclerView.ViewHolder selected, int actionState) {
        int prevActionState;
        boolean z;
        int i;
        float targetTranslateX;
        float targetTranslateY;
        int animationType;
        RecyclerView.ViewHolder viewHolder = selected;
        int i2 = actionState;
        if (viewHolder != this.mSelected || i2 != this.mActionState) {
            this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
            int prevActionState2 = this.mActionState;
            endRecoverAnimation(viewHolder, true);
            this.mActionState = i2;
            if (i2 == 2) {
                if (viewHolder != null) {
                    this.mOverdrawChild = viewHolder.itemView;
                    addChildDrawingOrderCallback();
                } else {
                    throw new IllegalArgumentException("Must pass a ViewHolder when dragging");
                }
            }
            int actionStateMask = (1 << ((i2 * 8) + 8)) - 1;
            boolean preventLayout = false;
            if (this.mSelected != null) {
                RecyclerView.ViewHolder prevSelected = this.mSelected;
                if (prevSelected.itemView.getParent() != null) {
                    if (prevActionState2 == 2) {
                        i = 0;
                    } else {
                        i = swipeIfNecessary(prevSelected);
                    }
                    int swipeDir = i;
                    releaseVelocityTracker();
                    switch (swipeDir) {
                        case 1:
                        case 2:
                            targetTranslateY = Math.signum(this.mDy) * ((float) this.mRecyclerView.getHeight());
                            targetTranslateX = 0.0f;
                            break;
                        case 4:
                        case 8:
                        case 16:
                        case 32:
                            targetTranslateY = 0.0f;
                            targetTranslateX = Math.signum(this.mDx) * ((float) this.mRecyclerView.getWidth());
                            break;
                        default:
                            targetTranslateY = 0.0f;
                            targetTranslateX = 0.0f;
                            break;
                    }
                    if (prevActionState2 == 2) {
                        animationType = 8;
                    } else if (swipeDir > 0) {
                        animationType = 2;
                    } else {
                        animationType = 4;
                    }
                    getSelectedDxDy(this.mTmpPosition);
                    float[] fArr = this.mTmpPosition;
                    float currentTranslateX = fArr[0];
                    float currentTranslateY = fArr[1];
                    RecyclerView.ViewHolder prevSelected2 = prevSelected;
                    int i3 = prevActionState2;
                    final int i4 = swipeDir;
                    prevActionState = 2;
                    final RecyclerView.ViewHolder viewHolder2 = prevSelected2;
                    RecoverAnimation rv = new RecoverAnimation(this, prevSelected, animationType, prevActionState2, currentTranslateX, currentTranslateY, targetTranslateX, targetTranslateY) {
                        final /* synthetic */ ItemTouchHelper this$0;

                        {
                            this.this$0 = this$0;
                        }

                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            if (!this.mOverridden) {
                                if (i4 <= 0) {
                                    ItemTouchHelper itemTouchHelper = this.this$0;
                                    itemTouchHelper.mCallback.clearView(itemTouchHelper.mRecyclerView, viewHolder2);
                                } else {
                                    this.this$0.mPendingCleanup.add(viewHolder2.itemView);
                                    this.mIsPendingCleanup = true;
                                    int i = i4;
                                    if (i > 0) {
                                        this.this$0.postDispatchSwipe(this, i);
                                    }
                                }
                                ItemTouchHelper itemTouchHelper2 = this.this$0;
                                View view = itemTouchHelper2.mOverdrawChild;
                                View view2 = viewHolder2.itemView;
                                if (view == view2) {
                                    itemTouchHelper2.removeChildDrawingOrderCallbackIfNecessary(view2);
                                }
                            }
                        }
                    };
                    rv.setDuration(this.mCallback.getAnimationDuration(this.mRecyclerView, animationType, targetTranslateX - currentTranslateX, targetTranslateY - currentTranslateY));
                    this.mRecoverAnimations.add(rv);
                    rv.start();
                    preventLayout = true;
                    RecyclerView.ViewHolder viewHolder3 = prevSelected2;
                } else {
                    int i5 = prevActionState2;
                    prevActionState = 2;
                    RecyclerView.ViewHolder prevSelected3 = prevSelected;
                    removeChildDrawingOrderCallbackIfNecessary(prevSelected3.itemView);
                    this.mCallback.clearView(this.mRecyclerView, prevSelected3);
                }
                this.mSelected = null;
            } else {
                prevActionState = 2;
            }
            if (viewHolder != null) {
                this.mSelectedFlags = (this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, viewHolder) & actionStateMask) >> (this.mActionState * 8);
                this.mSelectedStartX = (float) viewHolder.itemView.getLeft();
                this.mSelectedStartY = (float) viewHolder.itemView.getTop();
                this.mSelected = viewHolder;
                if (i2 == prevActionState) {
                    z = false;
                    viewHolder.itemView.performHapticFeedback(0);
                } else {
                    z = false;
                }
            } else {
                z = false;
            }
            ViewParent rvParent = this.mRecyclerView.getParent();
            if (rvParent != null) {
                rvParent.requestDisallowInterceptTouchEvent(this.mSelected != null ? true : z);
            }
            if (!preventLayout) {
                this.mRecyclerView.getLayoutManager().requestSimpleAnimationsInNextLayout();
            }
            this.mCallback.onSelectedChanged(this.mSelected, this.mActionState);
            this.mRecyclerView.invalidate();
        }
    }

    /* access modifiers changed from: package-private */
    public void postDispatchSwipe(final RecoverAnimation anim, final int swipeDir) {
        this.mRecyclerView.post(new Runnable() {
            public void run() {
                RecyclerView recyclerView = ItemTouchHelper.this.mRecyclerView;
                if (recyclerView != null && recyclerView.isAttachedToWindow()) {
                    RecoverAnimation recoverAnimation = anim;
                    if (!recoverAnimation.mOverridden && recoverAnimation.mViewHolder.getAbsoluteAdapterPosition() != -1) {
                        RecyclerView.ItemAnimator animator = ItemTouchHelper.this.mRecyclerView.getItemAnimator();
                        if ((animator == null || !animator.isRunning((RecyclerView.ItemAnimator.ItemAnimatorFinishedListener) null)) && !ItemTouchHelper.this.hasRunningRecoverAnim()) {
                            ItemTouchHelper.this.mCallback.onSwiped(anim.mViewHolder, swipeDir);
                        } else {
                            ItemTouchHelper.this.mRecyclerView.post(this);
                        }
                    }
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public boolean hasRunningRecoverAnim() {
        int size = this.mRecoverAnimations.size();
        for (int i = 0; i < size; i++) {
            if (!this.mRecoverAnimations.get(i).mEnded) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean scrollIfNecessary() {
        int bottomDiff;
        int rightDiff;
        if (this.mSelected == null) {
            this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
            return false;
        }
        long now = System.currentTimeMillis();
        long j = this.mDragScrollStartTimeInMs;
        long scrollDuration = j == Long.MIN_VALUE ? 0 : now - j;
        RecyclerView.LayoutManager lm = this.mRecyclerView.getLayoutManager();
        if (this.mTmpRect == null) {
            this.mTmpRect = new Rect();
        }
        int scrollX = 0;
        int scrollY = 0;
        lm.calculateItemDecorationsForChild(this.mSelected.itemView, this.mTmpRect);
        if (lm.canScrollHorizontally()) {
            int curX = (int) (this.mSelectedStartX + this.mDx);
            int leftDiff = (curX - this.mTmpRect.left) - this.mRecyclerView.getPaddingLeft();
            float f = this.mDx;
            if (f < 0.0f && leftDiff < 0) {
                scrollX = leftDiff;
            } else if (f > 0.0f && (rightDiff = ((this.mSelected.itemView.getWidth() + curX) + this.mTmpRect.right) - (this.mRecyclerView.getWidth() - this.mRecyclerView.getPaddingRight())) > 0) {
                scrollX = rightDiff;
            }
        }
        if (lm.canScrollVertically()) {
            int curY = (int) (this.mSelectedStartY + this.mDy);
            int topDiff = (curY - this.mTmpRect.top) - this.mRecyclerView.getPaddingTop();
            float f2 = this.mDy;
            if (f2 < 0.0f && topDiff < 0) {
                scrollY = topDiff;
            } else if (f2 > 0.0f && (bottomDiff = ((this.mSelected.itemView.getHeight() + curY) + this.mTmpRect.bottom) - (this.mRecyclerView.getHeight() - this.mRecyclerView.getPaddingBottom())) > 0) {
                scrollY = bottomDiff;
            }
        }
        if (scrollX != 0) {
            scrollX = this.mCallback.interpolateOutOfBoundsScroll(this.mRecyclerView, this.mSelected.itemView.getWidth(), scrollX, this.mRecyclerView.getWidth(), scrollDuration);
        }
        if (scrollY != 0) {
            scrollY = this.mCallback.interpolateOutOfBoundsScroll(this.mRecyclerView, this.mSelected.itemView.getHeight(), scrollY, this.mRecyclerView.getHeight(), scrollDuration);
        }
        if (scrollX == 0 && scrollY == 0) {
            this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
            return false;
        }
        if (this.mDragScrollStartTimeInMs == Long.MIN_VALUE) {
            this.mDragScrollStartTimeInMs = now;
        }
        this.mRecyclerView.scrollBy(scrollX, scrollY);
        return true;
    }

    private List<RecyclerView.ViewHolder> findSwapTargets(RecyclerView.ViewHolder viewHolder) {
        int margin;
        int left;
        RecyclerView.ViewHolder viewHolder2 = viewHolder;
        List<RecyclerView.ViewHolder> list = this.mSwapTargets;
        if (list == null) {
            this.mSwapTargets = new ArrayList();
            this.mDistances = new ArrayList();
        } else {
            list.clear();
            this.mDistances.clear();
        }
        int margin2 = this.mCallback.getBoundingBoxMargin();
        int left2 = Math.round(this.mSelectedStartX + this.mDx) - margin2;
        int top = Math.round(this.mSelectedStartY + this.mDy) - margin2;
        int right = viewHolder2.itemView.getWidth() + left2 + (margin2 * 2);
        int bottom = viewHolder2.itemView.getHeight() + top + (margin2 * 2);
        int centerX = (left2 + right) / 2;
        int centerY = (top + bottom) / 2;
        RecyclerView.LayoutManager lm = this.mRecyclerView.getLayoutManager();
        int childCount = lm.getChildCount();
        int i = 0;
        while (i < childCount) {
            View other = lm.getChildAt(i);
            if (other == viewHolder2.itemView) {
                margin = margin2;
                left = left2;
            } else if (other.getBottom() < top || other.getTop() > bottom) {
                margin = margin2;
                left = left2;
            } else if (other.getRight() < left2) {
                margin = margin2;
                left = left2;
            } else if (other.getLeft() > right) {
                margin = margin2;
                left = left2;
            } else {
                RecyclerView.ViewHolder otherVh = this.mRecyclerView.getChildViewHolder(other);
                if (this.mCallback.canDropOver(this.mRecyclerView, this.mSelected, otherVh)) {
                    int dx = Math.abs(centerX - ((other.getLeft() + other.getRight()) / 2));
                    int dy = Math.abs(centerY - ((other.getTop() + other.getBottom()) / 2));
                    int dist = (dx * dx) + (dy * dy);
                    int i2 = dx;
                    int cnt = this.mSwapTargets.size();
                    margin = margin2;
                    int pos = 0;
                    left = left2;
                    int j = 0;
                    while (true) {
                        if (j >= cnt) {
                            break;
                        }
                        int cnt2 = cnt;
                        if (dist <= this.mDistances.get(j).intValue()) {
                            break;
                        }
                        pos++;
                        j++;
                        cnt = cnt2;
                    }
                    this.mSwapTargets.add(pos, otherVh);
                    this.mDistances.add(pos, Integer.valueOf(dist));
                } else {
                    margin = margin2;
                    left = left2;
                }
            }
            i++;
            viewHolder2 = viewHolder;
            left2 = left;
            margin2 = margin;
        }
        return this.mSwapTargets;
    }

    /* access modifiers changed from: package-private */
    public void moveIfNecessary(RecyclerView.ViewHolder viewHolder) {
        RecyclerView.ViewHolder viewHolder2 = viewHolder;
        if (!this.mRecyclerView.isLayoutRequested() && this.mActionState == 2) {
            float threshold = this.mCallback.getMoveThreshold(viewHolder2);
            int x = (int) (this.mSelectedStartX + this.mDx);
            int y = (int) (this.mSelectedStartY + this.mDy);
            if (((float) Math.abs(y - viewHolder2.itemView.getTop())) >= ((float) viewHolder2.itemView.getHeight()) * threshold || ((float) Math.abs(x - viewHolder2.itemView.getLeft())) >= ((float) viewHolder2.itemView.getWidth()) * threshold) {
                List<RecyclerView.ViewHolder> swapTargets = findSwapTargets(viewHolder);
                if (swapTargets.size() != 0) {
                    RecyclerView.ViewHolder target = this.mCallback.chooseDropTarget(viewHolder2, swapTargets, x, y);
                    if (target == null) {
                        this.mSwapTargets.clear();
                        this.mDistances.clear();
                        return;
                    }
                    int toPosition = target.getAbsoluteAdapterPosition();
                    int fromPosition = viewHolder.getAbsoluteAdapterPosition();
                    if (this.mCallback.onMove(this.mRecyclerView, viewHolder2, target)) {
                        this.mCallback.onMoved(this.mRecyclerView, viewHolder, fromPosition, target, toPosition, x, y);
                    }
                }
            }
        }
    }

    public void onChildViewAttachedToWindow(@NonNull View view) {
    }

    public void onChildViewDetachedFromWindow(@NonNull View view) {
        removeChildDrawingOrderCallbackIfNecessary(view);
        RecyclerView.ViewHolder holder = this.mRecyclerView.getChildViewHolder(view);
        if (holder != null) {
            RecyclerView.ViewHolder viewHolder = this.mSelected;
            if (viewHolder == null || holder != viewHolder) {
                endRecoverAnimation(holder, false);
                if (this.mPendingCleanup.remove(holder.itemView)) {
                    this.mCallback.clearView(this.mRecyclerView, holder);
                    return;
                }
                return;
            }
            select((RecyclerView.ViewHolder) null, 0);
        }
    }

    /* access modifiers changed from: package-private */
    public void endRecoverAnimation(RecyclerView.ViewHolder viewHolder, boolean override) {
        for (int i = this.mRecoverAnimations.size() - 1; i >= 0; i--) {
            RecoverAnimation anim = this.mRecoverAnimations.get(i);
            if (anim.mViewHolder == viewHolder) {
                anim.mOverridden |= override;
                if (!anim.mEnded) {
                    anim.cancel();
                }
                this.mRecoverAnimations.remove(i);
                return;
            }
        }
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.setEmpty();
    }

    /* access modifiers changed from: package-private */
    public void obtainVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
        }
        this.mVelocityTracker = VelocityTracker.obtain();
    }

    private void releaseVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private RecyclerView.ViewHolder findSwipedView(MotionEvent motionEvent) {
        View child;
        RecyclerView.LayoutManager lm = this.mRecyclerView.getLayoutManager();
        int i = this.mActivePointerId;
        if (i == -1) {
            return null;
        }
        int pointerIndex = motionEvent.findPointerIndex(i);
        float dx = motionEvent.getX(pointerIndex) - this.mInitialTouchX;
        float dy = motionEvent.getY(pointerIndex) - this.mInitialTouchY;
        float absDx = Math.abs(dx);
        float absDy = Math.abs(dy);
        int i2 = this.mSlop;
        if (absDx < ((float) i2) && absDy < ((float) i2)) {
            return null;
        }
        if (absDx > absDy && lm.canScrollHorizontally()) {
            return null;
        }
        if ((absDy <= absDx || !lm.canScrollVertically()) && (child = findChildView(motionEvent)) != null) {
            return this.mRecyclerView.getChildViewHolder(child);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void checkSelectForSwipe(int action, MotionEvent motionEvent, int pointerIndex) {
        RecyclerView.ViewHolder vh;
        int swipeFlags;
        if (this.mSelected == null && action == 2 && this.mActionState != 2 && this.mCallback.isItemViewSwipeEnabled() && this.mRecyclerView.getScrollState() != 1 && (vh = findSwipedView(motionEvent)) != null && (swipeFlags = (65280 & this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, vh)) >> 8) != 0) {
            float x = motionEvent.getX(pointerIndex);
            float y = motionEvent.getY(pointerIndex);
            float dx = x - this.mInitialTouchX;
            float dy = y - this.mInitialTouchY;
            float absDx = Math.abs(dx);
            float absDy = Math.abs(dy);
            int i = this.mSlop;
            if (absDx >= ((float) i) || absDy >= ((float) i)) {
                if (absDx > absDy) {
                    if (dx < 0.0f && (swipeFlags & 4) == 0) {
                        return;
                    }
                    if (dx > 0.0f && (swipeFlags & 8) == 0) {
                        return;
                    }
                } else if (dy < 0.0f && (swipeFlags & 1) == 0) {
                    return;
                } else {
                    if (dy > 0.0f && (swipeFlags & 2) == 0) {
                        return;
                    }
                }
                this.mDy = 0.0f;
                this.mDx = 0.0f;
                this.mActivePointerId = motionEvent.getPointerId(0);
                select(vh, 1);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public View findChildView(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        RecyclerView.ViewHolder viewHolder = this.mSelected;
        if (viewHolder != null) {
            View selectedView = viewHolder.itemView;
            if (hitTest(selectedView, x, y, this.mSelectedStartX + this.mDx, this.mSelectedStartY + this.mDy)) {
                return selectedView;
            }
        }
        for (int i = this.mRecoverAnimations.size() - 1; i >= 0; i--) {
            RecoverAnimation anim = this.mRecoverAnimations.get(i);
            View view = anim.mViewHolder.itemView;
            if (hitTest(view, x, y, anim.mX, anim.mY)) {
                return view;
            }
        }
        return this.mRecyclerView.findChildViewUnder(x, y);
    }

    public void startDrag(@NonNull RecyclerView.ViewHolder viewHolder) {
        if (!this.mCallback.hasDragFlag(this.mRecyclerView, viewHolder)) {
            Log.e(TAG, "Start drag has been called but dragging is not enabled");
        } else if (viewHolder.itemView.getParent() != this.mRecyclerView) {
            Log.e(TAG, "Start drag has been called with a view holder which is not a child of the RecyclerView which is controlled by this ItemTouchHelper.");
        } else {
            obtainVelocityTracker();
            this.mDy = 0.0f;
            this.mDx = 0.0f;
            select(viewHolder, 2);
        }
    }

    public void startSwipe(@NonNull RecyclerView.ViewHolder viewHolder) {
        if (!this.mCallback.hasSwipeFlag(this.mRecyclerView, viewHolder)) {
            Log.e(TAG, "Start swipe has been called but swiping is not enabled");
        } else if (viewHolder.itemView.getParent() != this.mRecyclerView) {
            Log.e(TAG, "Start swipe has been called with a view holder which is not a child of the RecyclerView controlled by this ItemTouchHelper.");
        } else {
            obtainVelocityTracker();
            this.mDy = 0.0f;
            this.mDx = 0.0f;
            select(viewHolder, 1);
        }
    }

    /* access modifiers changed from: package-private */
    public RecoverAnimation findAnimation(MotionEvent event) {
        if (this.mRecoverAnimations.isEmpty()) {
            return null;
        }
        View target = findChildView(event);
        for (int i = this.mRecoverAnimations.size() - 1; i >= 0; i--) {
            RecoverAnimation anim = this.mRecoverAnimations.get(i);
            if (anim.mViewHolder.itemView == target) {
                return anim;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void updateDxDy(MotionEvent ev, int directionFlags, int pointerIndex) {
        float x = ev.getX(pointerIndex);
        float y = ev.getY(pointerIndex);
        float f = x - this.mInitialTouchX;
        this.mDx = f;
        this.mDy = y - this.mInitialTouchY;
        if ((directionFlags & 4) == 0) {
            this.mDx = Math.max(0.0f, f);
        }
        if ((directionFlags & 8) == 0) {
            this.mDx = Math.min(0.0f, this.mDx);
        }
        if ((directionFlags & 1) == 0) {
            this.mDy = Math.max(0.0f, this.mDy);
        }
        if ((directionFlags & 2) == 0) {
            this.mDy = Math.min(0.0f, this.mDy);
        }
    }

    private int swipeIfNecessary(RecyclerView.ViewHolder viewHolder) {
        if (this.mActionState == 2) {
            return 0;
        }
        int originalMovementFlags = this.mCallback.getMovementFlags(this.mRecyclerView, viewHolder);
        int flags = (this.mCallback.convertToAbsoluteDirection(originalMovementFlags, ViewCompat.getLayoutDirection(this.mRecyclerView)) & 65280) >> 8;
        if (flags == 0) {
            return 0;
        }
        int originalFlags = (65280 & originalMovementFlags) >> 8;
        if (Math.abs(this.mDx) > Math.abs(this.mDy)) {
            int checkHorizontalSwipe = checkHorizontalSwipe(viewHolder, flags);
            int swipeDir = checkHorizontalSwipe;
            if (checkHorizontalSwipe <= 0) {
                int checkVerticalSwipe = checkVerticalSwipe(viewHolder, flags);
                int swipeDir2 = checkVerticalSwipe;
                if (checkVerticalSwipe > 0) {
                    return swipeDir2;
                }
            } else if ((originalFlags & swipeDir) == 0) {
                return Callback.convertToRelativeDirection(swipeDir, ViewCompat.getLayoutDirection(this.mRecyclerView));
            } else {
                return swipeDir;
            }
        } else {
            int checkVerticalSwipe2 = checkVerticalSwipe(viewHolder, flags);
            int swipeDir3 = checkVerticalSwipe2;
            if (checkVerticalSwipe2 > 0) {
                return swipeDir3;
            }
            int checkHorizontalSwipe2 = checkHorizontalSwipe(viewHolder, flags);
            int swipeDir4 = checkHorizontalSwipe2;
            if (checkHorizontalSwipe2 > 0) {
                if ((originalFlags & swipeDir4) == 0) {
                    return Callback.convertToRelativeDirection(swipeDir4, ViewCompat.getLayoutDirection(this.mRecyclerView));
                }
                return swipeDir4;
            }
        }
        return 0;
    }

    private int checkHorizontalSwipe(RecyclerView.ViewHolder viewHolder, int flags) {
        if ((flags & 12) == 0) {
            return 0;
        }
        int i = 8;
        int dirFlag = this.mDx > 0.0f ? 8 : 4;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null && this.mActivePointerId > -1) {
            velocityTracker.computeCurrentVelocity(1000, this.mCallback.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
            float xVelocity = this.mVelocityTracker.getXVelocity(this.mActivePointerId);
            float yVelocity = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
            if (xVelocity <= 0.0f) {
                i = 4;
            }
            int velDirFlag = i;
            float absXVelocity = Math.abs(xVelocity);
            if ((velDirFlag & flags) != 0 && dirFlag == velDirFlag && absXVelocity >= this.mCallback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && absXVelocity > Math.abs(yVelocity)) {
                return velDirFlag;
            }
        }
        float threshold = ((float) this.mRecyclerView.getWidth()) * this.mCallback.getSwipeThreshold(viewHolder);
        if ((flags & dirFlag) == 0 || Math.abs(this.mDx) <= threshold) {
            return 0;
        }
        return dirFlag;
    }

    private int checkVerticalSwipe(RecyclerView.ViewHolder viewHolder, int flags) {
        if ((flags & 3) == 0) {
            return 0;
        }
        int i = 2;
        int dirFlag = this.mDy > 0.0f ? 2 : 1;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null && this.mActivePointerId > -1) {
            velocityTracker.computeCurrentVelocity(1000, this.mCallback.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
            float xVelocity = this.mVelocityTracker.getXVelocity(this.mActivePointerId);
            float yVelocity = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
            if (yVelocity <= 0.0f) {
                i = 1;
            }
            int velDirFlag = i;
            float absYVelocity = Math.abs(yVelocity);
            if ((velDirFlag & flags) != 0 && velDirFlag == dirFlag && absYVelocity >= this.mCallback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && absYVelocity > Math.abs(xVelocity)) {
                return velDirFlag;
            }
        }
        float threshold = ((float) this.mRecyclerView.getHeight()) * this.mCallback.getSwipeThreshold(viewHolder);
        if ((flags & dirFlag) == 0 || Math.abs(this.mDy) <= threshold) {
            return 0;
        }
        return dirFlag;
    }

    private void addChildDrawingOrderCallback() {
        if (Build.VERSION.SDK_INT < 21) {
            if (this.mChildDrawingOrderCallback == null) {
                this.mChildDrawingOrderCallback = new RecyclerView.ChildDrawingOrderCallback() {
                    public int onGetChildDrawingOrder(int childCount, int i) {
                        ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                        View view = itemTouchHelper.mOverdrawChild;
                        if (view == null) {
                            return i;
                        }
                        int childPosition = itemTouchHelper.mOverdrawChildPosition;
                        if (childPosition == -1) {
                            childPosition = itemTouchHelper.mRecyclerView.indexOfChild(view);
                            ItemTouchHelper.this.mOverdrawChildPosition = childPosition;
                        }
                        if (i == childCount - 1) {
                            return childPosition;
                        }
                        return i < childPosition ? i : i + 1;
                    }
                };
            }
            this.mRecyclerView.setChildDrawingOrderCallback(this.mChildDrawingOrderCallback);
        }
    }

    /* access modifiers changed from: package-private */
    public void removeChildDrawingOrderCallbackIfNecessary(View view) {
        if (view == this.mOverdrawChild) {
            this.mOverdrawChild = null;
            if (this.mChildDrawingOrderCallback != null) {
                this.mRecyclerView.setChildDrawingOrderCallback((RecyclerView.ChildDrawingOrderCallback) null);
            }
        }
    }

    public static abstract class Callback {
        private static final int ABS_HORIZONTAL_DIR_FLAGS = 789516;
        public static final int DEFAULT_DRAG_ANIMATION_DURATION = 200;
        public static final int DEFAULT_SWIPE_ANIMATION_DURATION = 250;
        private static final long DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS = 2000;
        static final int RELATIVE_DIR_FLAGS = 3158064;
        private static final Interpolator sDragScrollInterpolator = new Interpolator() {
            public float getInterpolation(float t) {
                return t * t * t * t * t;
            }
        };
        private static final Interpolator sDragViewScrollCapInterpolator = new Interpolator() {
            public float getInterpolation(float t) {
                float t2 = t - 1.0f;
                return (t2 * t2 * t2 * t2 * t2) + 1.0f;
            }
        };
        private int mCachedMaxScrollSpeed = -1;

        public abstract int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder);

        public abstract boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder2);

        public abstract void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i);

        @NonNull
        public static ItemTouchUIUtil getDefaultUIUtil() {
            return ItemTouchUIUtilImpl.INSTANCE;
        }

        public static int convertToRelativeDirection(int flags, int layoutDirection) {
            int masked = flags & ABS_HORIZONTAL_DIR_FLAGS;
            if (masked == 0) {
                return flags;
            }
            int flags2 = flags & (~masked);
            if (layoutDirection == 0) {
                return flags2 | (masked << 2);
            }
            return flags2 | ((masked << 1) & -789517) | ((ABS_HORIZONTAL_DIR_FLAGS & (masked << 1)) << 2);
        }

        public static int makeMovementFlags(int dragFlags, int swipeFlags) {
            return makeFlag(0, swipeFlags | dragFlags) | makeFlag(1, swipeFlags) | makeFlag(2, dragFlags);
        }

        public static int makeFlag(int actionState, int directions) {
            return directions << (actionState * 8);
        }

        public int convertToAbsoluteDirection(int flags, int layoutDirection) {
            int masked = flags & RELATIVE_DIR_FLAGS;
            if (masked == 0) {
                return flags;
            }
            int flags2 = flags & (~masked);
            if (layoutDirection == 0) {
                return flags2 | (masked >> 2);
            }
            return flags2 | ((masked >> 1) & -3158065) | ((RELATIVE_DIR_FLAGS & (masked >> 1)) >> 2);
        }

        /* access modifiers changed from: package-private */
        public final int getAbsoluteMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return convertToAbsoluteDirection(getMovementFlags(recyclerView, viewHolder), ViewCompat.getLayoutDirection(recyclerView));
        }

        /* access modifiers changed from: package-private */
        public boolean hasDragFlag(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return (ItemTouchHelper.ACTION_MODE_DRAG_MASK & getAbsoluteMovementFlags(recyclerView, viewHolder)) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean hasSwipeFlag(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return (65280 & getAbsoluteMovementFlags(recyclerView, viewHolder)) != 0;
        }

        public boolean canDropOver(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder current, @NonNull RecyclerView.ViewHolder target) {
            return true;
        }

        public boolean isLongPressDragEnabled() {
            return true;
        }

        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        public int getBoundingBoxMargin() {
            return 0;
        }

        public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
            return 0.5f;
        }

        public float getMoveThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
            return 0.5f;
        }

        public float getSwipeEscapeVelocity(float defaultValue) {
            return defaultValue;
        }

        public float getSwipeVelocityThreshold(float defaultValue) {
            return defaultValue;
        }

        public RecyclerView.ViewHolder chooseDropTarget(@NonNull RecyclerView.ViewHolder selected, @NonNull List<RecyclerView.ViewHolder> dropTargets, int curX, int curY) {
            int diff;
            int score;
            int diff2;
            int score2;
            int diff3;
            int score3;
            int diff4;
            int score4;
            RecyclerView.ViewHolder viewHolder = selected;
            int right = curX + viewHolder.itemView.getWidth();
            int bottom = curY + viewHolder.itemView.getHeight();
            RecyclerView.ViewHolder winner = null;
            int winnerScore = -1;
            int dx = curX - viewHolder.itemView.getLeft();
            int dy = curY - viewHolder.itemView.getTop();
            int targetsSize = dropTargets.size();
            for (int i = 0; i < targetsSize; i++) {
                RecyclerView.ViewHolder target = dropTargets.get(i);
                if (dx > 0 && (diff4 = target.itemView.getRight() - right) < 0 && target.itemView.getRight() > viewHolder.itemView.getRight() && (score4 = Math.abs(diff4)) > winnerScore) {
                    winnerScore = score4;
                    winner = target;
                }
                if (dx < 0 && (diff3 = target.itemView.getLeft() - curX) > 0 && target.itemView.getLeft() < viewHolder.itemView.getLeft() && (score3 = Math.abs(diff3)) > winnerScore) {
                    winnerScore = score3;
                    winner = target;
                }
                if (dy < 0 && (diff2 = target.itemView.getTop() - curY) > 0 && target.itemView.getTop() < viewHolder.itemView.getTop() && (score2 = Math.abs(diff2)) > winnerScore) {
                    winnerScore = score2;
                    winner = target;
                }
                if (dy > 0 && (diff = target.itemView.getBottom() - bottom) < 0 && target.itemView.getBottom() > viewHolder.itemView.getBottom() && (score = Math.abs(diff)) > winnerScore) {
                    winnerScore = score;
                    winner = target;
                }
            }
            List<RecyclerView.ViewHolder> list = dropTargets;
            return winner;
        }

        public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
            if (viewHolder != null) {
                ItemTouchUIUtilImpl.INSTANCE.onSelected(viewHolder.itemView);
            }
        }

        private int getMaxDragScroll(RecyclerView recyclerView) {
            if (this.mCachedMaxScrollSpeed == -1) {
                this.mCachedMaxScrollSpeed = recyclerView.getResources().getDimensionPixelSize(R.dimen.item_touch_helper_max_drag_scroll_per_frame);
            }
            return this.mCachedMaxScrollSpeed;
        }

        public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof ViewDropHandler) {
                ((ViewDropHandler) layoutManager).prepareForDrop(viewHolder.itemView, target.itemView, x, y);
                return;
            }
            if (layoutManager.canScrollHorizontally()) {
                if (layoutManager.getDecoratedLeft(target.itemView) <= recyclerView.getPaddingLeft()) {
                    recyclerView.scrollToPosition(toPos);
                }
                if (layoutManager.getDecoratedRight(target.itemView) >= recyclerView.getWidth() - recyclerView.getPaddingRight()) {
                    recyclerView.scrollToPosition(toPos);
                }
            }
            if (layoutManager.canScrollVertically() != 0) {
                if (layoutManager.getDecoratedTop(target.itemView) <= recyclerView.getPaddingTop()) {
                    recyclerView.scrollToPosition(toPos);
                }
                if (layoutManager.getDecoratedBottom(target.itemView) >= recyclerView.getHeight() - recyclerView.getPaddingBottom()) {
                    recyclerView.scrollToPosition(toPos);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.ViewHolder selected, List<RecoverAnimation> recoverAnimationList, int actionState, float dX, float dY) {
            Canvas canvas = c;
            int recoverAnimSize = recoverAnimationList.size();
            for (int i = 0; i < recoverAnimSize; i++) {
                RecoverAnimation anim = recoverAnimationList.get(i);
                anim.update();
                int count = c.save();
                onChildDraw(c, parent, anim.mViewHolder, anim.mX, anim.mY, anim.mActionState, false);
                c.restoreToCount(count);
            }
            List<RecoverAnimation> list = recoverAnimationList;
            if (selected != null) {
                int count2 = c.save();
                onChildDraw(c, parent, selected, dX, dY, actionState, true);
                c.restoreToCount(count2);
            }
        }

        /* access modifiers changed from: package-private */
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.ViewHolder selected, List<RecoverAnimation> recoverAnimationList, int actionState, float dX, float dY) {
            Canvas canvas = c;
            List<RecoverAnimation> list = recoverAnimationList;
            int recoverAnimSize = recoverAnimationList.size();
            for (int i = 0; i < recoverAnimSize; i++) {
                RecoverAnimation anim = list.get(i);
                int count = c.save();
                onChildDrawOver(c, parent, anim.mViewHolder, anim.mX, anim.mY, anim.mActionState, false);
                c.restoreToCount(count);
            }
            if (selected != null) {
                int count2 = c.save();
                onChildDrawOver(c, parent, selected, dX, dY, actionState, true);
                c.restoreToCount(count2);
            }
            boolean hasRunningAnimation = false;
            for (int i2 = recoverAnimSize - 1; i2 >= 0; i2--) {
                RecoverAnimation anim2 = list.get(i2);
                boolean z = anim2.mEnded;
                if (z && !anim2.mIsPendingCleanup) {
                    list.remove(i2);
                } else if (!z) {
                    hasRunningAnimation = true;
                }
            }
            if (hasRunningAnimation) {
                parent.invalidate();
            }
        }

        public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            ItemTouchUIUtilImpl.INSTANCE.clearView(viewHolder.itemView);
        }

        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            ItemTouchUIUtilImpl.INSTANCE.onDraw(c, recyclerView, viewHolder.itemView, dX, dY, actionState, isCurrentlyActive);
        }

        public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            ItemTouchUIUtilImpl.INSTANCE.onDrawOver(c, recyclerView, viewHolder.itemView, dX, dY, actionState, isCurrentlyActive);
        }

        public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
            RecyclerView.ItemAnimator itemAnimator = recyclerView.getItemAnimator();
            if (itemAnimator == null) {
                if (animationType == 8) {
                    return 200;
                }
                return 250;
            } else if (animationType == 8) {
                return itemAnimator.getMoveDuration();
            } else {
                return itemAnimator.getRemoveDuration();
            }
        }

        public int interpolateOutOfBoundsScroll(@NonNull RecyclerView recyclerView, int viewSize, int viewSizeOutOfBounds, int totalSize, long msSinceStartScroll) {
            float timeRatio;
            int cappedScroll = (int) (((float) (((int) Math.signum((float) viewSizeOutOfBounds)) * getMaxDragScroll(recyclerView))) * sDragViewScrollCapInterpolator.getInterpolation(Math.min(1.0f, (((float) Math.abs(viewSizeOutOfBounds)) * 1.0f) / ((float) viewSize))));
            if (msSinceStartScroll > 2000) {
                timeRatio = 1.0f;
            } else {
                timeRatio = ((float) msSinceStartScroll) / 2000.0f;
            }
            int value = (int) (((float) cappedScroll) * sDragScrollInterpolator.getInterpolation(timeRatio));
            if (value == 0) {
                return viewSizeOutOfBounds > 0 ? 1 : -1;
            }
            return value;
        }
    }

    public static abstract class SimpleCallback extends Callback {
        private int mDefaultDragDirs;
        private int mDefaultSwipeDirs;

        public SimpleCallback(int dragDirs, int swipeDirs) {
            this.mDefaultSwipeDirs = swipeDirs;
            this.mDefaultDragDirs = dragDirs;
        }

        public void setDefaultSwipeDirs(int defaultSwipeDirs) {
            this.mDefaultSwipeDirs = defaultSwipeDirs;
        }

        public void setDefaultDragDirs(int defaultDragDirs) {
            this.mDefaultDragDirs = defaultDragDirs;
        }

        public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return this.mDefaultSwipeDirs;
        }

        public int getDragDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return this.mDefaultDragDirs;
        }

        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return Callback.makeMovementFlags(getDragDirs(recyclerView, viewHolder), getSwipeDirs(recyclerView, viewHolder));
        }
    }

    public class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        private boolean mShouldReactToLongPress = true;

        ItemTouchHelperGestureListener() {
        }

        /* access modifiers changed from: package-private */
        public void doNotReactToLongPress() {
            this.mShouldReactToLongPress = false;
        }

        public boolean onDown(MotionEvent e) {
            return true;
        }

        public void onLongPress(MotionEvent e) {
            View child;
            RecyclerView.ViewHolder vh;
            int i;
            if (this.mShouldReactToLongPress && (child = ItemTouchHelper.this.findChildView(e)) != null && (vh = ItemTouchHelper.this.mRecyclerView.getChildViewHolder(child)) != null) {
                ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                if (itemTouchHelper.mCallback.hasDragFlag(itemTouchHelper.mRecyclerView, vh) && e.getPointerId(0) == (i = ItemTouchHelper.this.mActivePointerId)) {
                    int index = e.findPointerIndex(i);
                    float x = e.getX(index);
                    float y = e.getY(index);
                    ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                    itemTouchHelper2.mInitialTouchX = x;
                    itemTouchHelper2.mInitialTouchY = y;
                    itemTouchHelper2.mDy = 0.0f;
                    itemTouchHelper2.mDx = 0.0f;
                    if (itemTouchHelper2.mCallback.isLongPressDragEnabled()) {
                        ItemTouchHelper.this.select(vh, 2);
                    }
                }
            }
        }
    }

    @VisibleForTesting
    public static class RecoverAnimation implements Animator.AnimatorListener {
        final int mActionState;
        final int mAnimationType;
        boolean mEnded = false;
        private float mFraction;
        boolean mIsPendingCleanup;
        boolean mOverridden = false;
        final float mStartDx;
        final float mStartDy;
        final float mTargetX;
        final float mTargetY;
        @VisibleForTesting
        final ValueAnimator mValueAnimator;
        final RecyclerView.ViewHolder mViewHolder;
        float mX;
        float mY;

        RecoverAnimation(RecyclerView.ViewHolder viewHolder, int animationType, int actionState, float startDx, float startDy, float targetX, float targetY) {
            this.mActionState = actionState;
            this.mAnimationType = animationType;
            this.mViewHolder = viewHolder;
            this.mStartDx = startDx;
            this.mStartDy = startDy;
            this.mTargetX = targetX;
            this.mTargetY = targetY;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            this.mValueAnimator = ofFloat;
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    RecoverAnimation.this.setFraction(animation.getAnimatedFraction());
                }
            });
            ofFloat.setTarget(viewHolder.itemView);
            ofFloat.addListener(this);
            setFraction(0.0f);
        }

        public void setDuration(long duration) {
            this.mValueAnimator.setDuration(duration);
        }

        public void start() {
            this.mViewHolder.setIsRecyclable(false);
            this.mValueAnimator.start();
        }

        public void cancel() {
            this.mValueAnimator.cancel();
        }

        public void setFraction(float fraction) {
            this.mFraction = fraction;
        }

        public void update() {
            float f = this.mStartDx;
            float f2 = this.mTargetX;
            if (f == f2) {
                this.mX = this.mViewHolder.itemView.getTranslationX();
            } else {
                this.mX = f + (this.mFraction * (f2 - f));
            }
            float f3 = this.mStartDy;
            float f4 = this.mTargetY;
            if (f3 == f4) {
                this.mY = this.mViewHolder.itemView.getTranslationY();
            } else {
                this.mY = f3 + (this.mFraction * (f4 - f3));
            }
        }

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
            if (!this.mEnded) {
                this.mViewHolder.setIsRecyclable(true);
            }
            this.mEnded = true;
        }

        public void onAnimationCancel(Animator animation) {
            setFraction(1.0f);
        }

        public void onAnimationRepeat(Animator animation) {
        }
    }
}

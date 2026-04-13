package com.didichuxing.doraemonkit.widget.tableview.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import com.didichuxing.doraemonkit.widget.tableview.bean.TableInfo;
import com.didichuxing.doraemonkit.widget.tableview.intface.ITouch;
import com.didichuxing.doraemonkit.widget.tableview.listener.Observable;
import com.didichuxing.doraemonkit.widget.tableview.listener.OnTableChangeListener;
import com.didichuxing.doraemonkit.widget.tableview.listener.TableClickObserver;
import java.util.Iterator;
import java.util.List;
import net.sqlcipher.database.SQLiteDatabase;

public class MatrixHelper extends Observable<TableClickObserver> implements ITouch, ScaleGestureDetector.OnScaleGestureListener {
    private AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
        public void onAnimationStart(Animator animation) {
            boolean unused = MatrixHelper.this.isAutoFling = true;
        }

        public void onAnimationCancel(Animator animation) {
            boolean unused = MatrixHelper.this.isAutoFling = false;
        }

        public void onAnimationEnd(Animator animation) {
            boolean unused = MatrixHelper.this.isAutoFling = false;
        }
    };
    private Point endPoint = new Point();
    private TypeEvaluator evaluator = new TypeEvaluator() {
        private Point point = new Point();

        public Object evaluate(float fraction, Object startValue, Object endValue) {
            Point startPoint = (Point) startValue;
            Point endPoint = (Point) endValue;
            int i = startPoint.x;
            int x = (int) (((float) i) + (((float) (endPoint.x - i)) * fraction));
            int i2 = startPoint.y;
            this.point.set(x, (int) (((float) i2) + (((float) (endPoint.y - i2)) * fraction)));
            return this.point;
        }
    };
    private float flingRate = 1.0f;
    private TimeInterpolator interpolator = new DecelerateInterpolator();
    /* access modifiers changed from: private */
    public boolean isAutoFling = false;
    /* access modifiers changed from: private */
    public boolean isCanZoom = false;
    /* access modifiers changed from: private */
    public boolean isFling;
    /* access modifiers changed from: private */
    public boolean isScale;
    private boolean isScaleMax;
    private boolean isScaleMin;
    private boolean isZooming;
    private OnTableChangeListener listener;
    private float mDownX;
    private float mDownY;
    private GestureDetector mGestureDetector;
    /* access modifiers changed from: private */
    public int mMinimumVelocity;
    private ScaleGestureDetector mScaleGestureDetector;
    /* access modifiers changed from: private */
    public float maxZoom = 5.0f;
    /* access modifiers changed from: private */
    public float minZoom = 1.0f;
    /* access modifiers changed from: private */
    public OnInterceptListener onInterceptListener;
    private Rect originalRect;
    private int pointMode;
    private Rect scaleRect = new Rect();
    /* access modifiers changed from: private */
    public Scroller scroller;
    private Point startPoint = new Point(0, 0);
    /* access modifiers changed from: private */
    public int tempTranslateX;
    /* access modifiers changed from: private */
    public int tempTranslateY;
    private float tempZoom = this.minZoom;
    int touchSlop;
    /* access modifiers changed from: private */
    public int translateX;
    /* access modifiers changed from: private */
    public int translateY;
    /* access modifiers changed from: private */
    public float zoom = 1.0f;
    /* access modifiers changed from: private */
    public Rect zoomRect;

    public interface OnInterceptListener {
        boolean isIntercept(MotionEvent motionEvent, float f, float f2);
    }

    public MatrixHelper(Context context) {
        this.mScaleGestureDetector = new ScaleGestureDetector(context, this);
        this.mGestureDetector = new GestureDetector(context, new OnTableGestureListener());
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.touchSlop = configuration.getScaledTouchSlop();
        this.mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        this.scroller = new Scroller(context);
        this.zoomRect = new Rect();
        this.originalRect = new Rect();
    }

    public boolean handlerTouchEvent(MotionEvent event) {
        if (this.isCanZoom) {
            this.mScaleGestureDetector.onTouchEvent(event);
        }
        this.mGestureDetector.onTouchEvent(event);
        return true;
    }

    public void onDisallowInterceptEvent(View view, MotionEvent event) {
        ViewParent parent = view.getParent();
        if (this.zoomRect == null || this.originalRect == null) {
            parent.requestDisallowInterceptTouchEvent(false);
            return;
        }
        switch (event.getAction() & 255) {
            case 0:
                this.pointMode = 1;
                this.mDownX = event.getX();
                float y = event.getY();
                this.mDownY = y;
                if (this.originalRect.contains((int) this.mDownX, (int) y)) {
                    parent.requestDisallowInterceptTouchEvent(true);
                    return;
                } else {
                    parent.requestDisallowInterceptTouchEvent(false);
                    return;
                }
            case 1:
            case 3:
                this.pointMode = 0;
                parent.requestDisallowInterceptTouchEvent(false);
                return;
            case 2:
                if (this.pointMode > 1) {
                    parent.requestDisallowInterceptTouchEvent(true);
                    return;
                }
                float disX = event.getX() - this.mDownX;
                float disY = event.getY() - this.mDownY;
                boolean isDisallowIntercept = true;
                if (Math.abs(disX) > Math.abs(disY)) {
                    if ((disX > 0.0f && toRectLeft()) || (disX < 0.0f && toRectRight())) {
                        isDisallowIntercept = false;
                    }
                } else if ((disY > 0.0f && toRectTop()) || (disY < 0.0f && toRectBottom())) {
                    isDisallowIntercept = false;
                }
                parent.requestDisallowInterceptTouchEvent(isDisallowIntercept);
                return;
            case 5:
                this.pointMode++;
                parent.requestDisallowInterceptTouchEvent(true);
                return;
            case 6:
                this.pointMode--;
                return;
            default:
                return;
        }
    }

    private boolean toRectLeft() {
        return this.translateX <= 0;
    }

    private boolean toRectRight() {
        return this.translateX >= this.zoomRect.width() - this.originalRect.width();
    }

    private boolean toRectBottom() {
        return this.translateY >= this.zoomRect.height() - this.originalRect.height();
    }

    private boolean toRectTop() {
        return this.translateY <= 0;
    }

    /* access modifiers changed from: private */
    public void notifyViewChanged() {
        OnTableChangeListener onTableChangeListener = this.listener;
        if (onTableChangeListener != null) {
            onTableChangeListener.onTableChanged(this.zoom, (float) this.translateX, (float) this.translateY);
        }
    }

    public void notifyObservers(List<TableClickObserver> list) {
    }

    public class OnTableGestureListener extends GestureDetector.SimpleOnGestureListener {
        OnTableGestureListener() {
        }

        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (MatrixHelper.this.onInterceptListener != null && MatrixHelper.this.onInterceptListener.isIntercept(e1, distanceX, distanceY)) {
                return true;
            }
            MatrixHelper matrixHelper = MatrixHelper.this;
            int unused = matrixHelper.translateX = (int) (((float) matrixHelper.translateX) + distanceX);
            MatrixHelper matrixHelper2 = MatrixHelper.this;
            int unused2 = matrixHelper2.translateY = (int) (((float) matrixHelper2.translateY) + distanceY);
            MatrixHelper.this.notifyViewChanged();
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(velocityX) > ((float) MatrixHelper.this.mMinimumVelocity) || Math.abs(velocityY) > ((float) MatrixHelper.this.mMinimumVelocity)) {
                MatrixHelper.this.scroller.setFinalX(0);
                MatrixHelper.this.scroller.setFinalY(0);
                MatrixHelper matrixHelper = MatrixHelper.this;
                int unused = matrixHelper.tempTranslateX = matrixHelper.translateX;
                MatrixHelper matrixHelper2 = MatrixHelper.this;
                int unused2 = matrixHelper2.tempTranslateY = matrixHelper2.translateY;
                MatrixHelper.this.scroller.fling(0, 0, (int) velocityX, (int) velocityY, -50000, SQLiteDatabase.SQLITE_MAX_LIKE_PATTERN_LENGTH, -50000, SQLiteDatabase.SQLITE_MAX_LIKE_PATTERN_LENGTH);
                boolean unused3 = MatrixHelper.this.isFling = true;
                MatrixHelper.this.startFilingAnim(false);
            } else {
                float f = velocityX;
                float f2 = velocityY;
            }
            return true;
        }

        public boolean onDown(MotionEvent e) {
            boolean unused = MatrixHelper.this.isFling = false;
            return true;
        }

        public boolean onDoubleTap(MotionEvent e) {
            if (MatrixHelper.this.isCanZoom) {
                float oldZoom = MatrixHelper.this.zoom;
                if (MatrixHelper.this.isScale) {
                    MatrixHelper matrixHelper = MatrixHelper.this;
                    float unused = matrixHelper.zoom = matrixHelper.zoom / 1.5f;
                    if (MatrixHelper.this.zoom < MatrixHelper.this.minZoom) {
                        MatrixHelper matrixHelper2 = MatrixHelper.this;
                        float unused2 = matrixHelper2.zoom = matrixHelper2.minZoom;
                        boolean unused3 = MatrixHelper.this.isScale = false;
                    }
                } else {
                    MatrixHelper matrixHelper3 = MatrixHelper.this;
                    float unused4 = matrixHelper3.zoom = matrixHelper3.zoom * 1.5f;
                    if (MatrixHelper.this.zoom > MatrixHelper.this.maxZoom) {
                        MatrixHelper matrixHelper4 = MatrixHelper.this;
                        float unused5 = matrixHelper4.zoom = matrixHelper4.maxZoom;
                        boolean unused6 = MatrixHelper.this.isScale = true;
                    }
                }
                MatrixHelper.this.resetTranslate(MatrixHelper.this.zoom / oldZoom);
                MatrixHelper.this.notifyViewChanged();
            }
            return true;
        }

        public boolean onSingleTapUp(MotionEvent e) {
            MatrixHelper.this.notifyViewChanged();
            Iterator<T> it = MatrixHelper.this.observables.iterator();
            while (it.hasNext()) {
                ((TableClickObserver) it.next()).onClick(e.getX(), e.getY());
            }
            return true;
        }
    }

    public boolean onScaleBegin(ScaleGestureDetector detector) {
        this.tempZoom = this.zoom;
        this.isZooming = true;
        return true;
    }

    public boolean onScale(ScaleGestureDetector detector) {
        float oldZoom = this.zoom;
        boolean isScaleEnd = false;
        float scale = detector.getScaleFactor();
        if (scale > 1.0f && this.isScaleMax) {
            this.isScaleMin = false;
            return true;
        } else if (scale >= 1.0f || !this.isScaleMin) {
            float f = this.tempZoom * scale;
            this.zoom = f;
            float f2 = this.maxZoom;
            if (f >= f2) {
                this.isScaleMax = true;
                this.zoom = f2;
                isScaleEnd = true;
            } else {
                float f3 = this.minZoom;
                if (f <= f3) {
                    this.isScaleMin = true;
                    this.zoom = f3;
                    isScaleEnd = true;
                } else {
                    this.isScaleMin = false;
                    this.isScaleMax = false;
                }
            }
            resetTranslate(this.zoom / oldZoom);
            notifyViewChanged();
            return isScaleEnd;
        } else {
            this.isScaleMax = false;
            return true;
        }
    }

    public void onScaleEnd(ScaleGestureDetector detector) {
        this.isZooming = false;
    }

    /* access modifiers changed from: private */
    public void startFilingAnim(boolean doubleWay) {
        int scrollX = Math.abs(this.scroller.getFinalX());
        int scrollY = Math.abs(this.scroller.getFinalY());
        if (doubleWay) {
            this.endPoint.set((int) (((float) this.scroller.getFinalX()) * this.flingRate), (int) (((float) this.scroller.getFinalY()) * this.flingRate));
        } else if (scrollX > scrollY) {
            this.endPoint.set((int) (((float) this.scroller.getFinalX()) * this.flingRate), 0);
        } else {
            this.endPoint.set(0, (int) (((float) this.scroller.getFinalY()) * this.flingRate));
        }
        ValueAnimator valueAnimator = ValueAnimator.ofObject(this.evaluator, new Object[]{this.startPoint, this.endPoint});
        valueAnimator.setInterpolator(this.interpolator);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                if (MatrixHelper.this.isFling) {
                    Point point = (Point) animation.getAnimatedValue();
                    MatrixHelper matrixHelper = MatrixHelper.this;
                    int unused = matrixHelper.translateX = matrixHelper.tempTranslateX - point.x;
                    MatrixHelper matrixHelper2 = MatrixHelper.this;
                    int unused2 = matrixHelper2.translateY = matrixHelper2.tempTranslateY - point.y;
                    MatrixHelper.this.notifyViewChanged();
                    return;
                }
                animation.cancel();
            }
        });
        int duration = ((int) (((float) Math.max(scrollX, scrollY)) * this.flingRate)) / 2;
        valueAnimator.setDuration(duration > 300 ? 300 : (long) duration);
        valueAnimator.start();
    }

    /* access modifiers changed from: private */
    public void resetTranslate(float factor) {
        this.translateX = (int) (((float) this.translateX) * factor);
        this.translateY = (int) (((float) this.translateY) * factor);
    }

    public Rect getZoomProviderRect(Rect showRect, Rect providerRect, TableInfo tableInfo) {
        Rect rect = showRect;
        Rect rect2 = providerRect;
        this.originalRect.set(rect);
        int showWidth = showRect.width();
        int showHeight = showRect.height();
        float f = this.zoom;
        int offsetX = ((int) (((float) showWidth) * (f - 1.0f))) / 2;
        int offsetY = ((int) (((float) showHeight) * (f - 1.0f))) / 2;
        if (!this.isAutoFling) {
            int oldw = providerRect.width();
            int oldh = providerRect.height();
            float f2 = this.zoom;
            int newWidth = (int) (((float) oldw) * f2);
            int newHeight = (int) (((float) oldh) * f2);
            if (f2 > 1.0f) {
                newWidth -= (int) (((float) tableInfo.getyAxisWidth()) * (this.zoom - 1.0f));
                newHeight -= (int) (((float) tableInfo.getTopHeight()) * (this.zoom - 1.0f));
            }
            if (tableInfo.getTitleDirection() == 1 || tableInfo.getTitleDirection() == 3) {
                newHeight -= (int) (((float) tableInfo.getTableTitleSize()) * (this.zoom - 1.0f));
            } else {
                newWidth -= (int) (((float) tableInfo.getTableTitleSize()) * (this.zoom - 1.0f));
            }
            int minTranslateX = -offsetX;
            int maxTranslateX = (newWidth - showWidth) - offsetX;
            int minTranslateY = -offsetY;
            int maxTranslateY = (newHeight - showHeight) - offsetY;
            boolean isFullShowX = false;
            boolean isFullShowY = false;
            if (maxTranslateX > minTranslateX) {
                int i = showWidth;
                int showWidth2 = this.translateX;
                if (showWidth2 < minTranslateX) {
                    this.translateX = minTranslateX;
                } else if (showWidth2 > maxTranslateX) {
                    this.translateX = maxTranslateX;
                }
            } else {
                isFullShowX = true;
            }
            if (maxTranslateY > minTranslateY) {
                int i2 = this.translateY;
                if (i2 < minTranslateY) {
                    this.translateY = minTranslateY;
                } else if (i2 > maxTranslateY) {
                    this.translateY = maxTranslateY;
                }
            } else {
                isFullShowY = true;
            }
            Rect rect3 = this.scaleRect;
            int i3 = showHeight;
            int i4 = oldw;
            int i5 = ((rect2.left - offsetX) - this.translateX) + rect.left;
            rect3.left = i5;
            int i6 = oldh;
            int i7 = ((rect2.top - offsetY) - this.translateY) + rect.top;
            rect3.top = i7;
            if (!isFullShowX) {
            } else if (this.isZooming) {
                int i8 = rect.left;
                if (i5 < i8) {
                    i5 = i8;
                }
                rect3.left = i5;
                int i9 = rect.right;
                int i10 = maxTranslateX;
                if (i5 > i9 - newWidth) {
                    i5 = i9 - newWidth;
                }
                rect3.left = i5;
            } else {
                rect3.left = rect.left;
                this.translateX = minTranslateX;
            }
            if (isFullShowY) {
                if (this.isZooming) {
                    int i11 = rect.top;
                    if (i7 < i11) {
                        i7 = i11;
                    }
                    rect3.top = i7;
                    int i12 = rect.bottom;
                    if (i7 > i12 - newHeight) {
                        i7 = i12 - newHeight;
                    }
                    rect3.top = i7;
                } else {
                    rect3.top = rect.top;
                    this.translateY = minTranslateY;
                }
            }
            rect3.right = rect3.left + newWidth;
            rect3.bottom = rect3.top + newHeight;
            this.zoomRect.set(rect3);
        } else {
            int i13 = showHeight;
            int showWidth3 = rect2.left;
            Rect rect4 = this.zoomRect;
            this.translateX = (showWidth3 - rect4.left) - offsetX;
            this.translateY = (rect2.top - rect4.top) - offsetY;
            this.scaleRect.set(rect4);
        }
        return this.scaleRect;
    }

    public void setZoom(float zoom2) {
        this.zoom = zoom2;
    }

    public Rect getZoomRect() {
        return this.zoomRect;
    }

    public Rect getOriginalRect() {
        return this.originalRect;
    }

    public boolean isCanZoom() {
        this.zoom = 1.0f;
        return this.isCanZoom;
    }

    public OnTableChangeListener getOnTableChangeListener() {
        return this.listener;
    }

    public void setOnTableChangeListener(OnTableChangeListener onTableChangeListener) {
        this.listener = onTableChangeListener;
    }

    public void setCanZoom(boolean canZoom) {
        this.isCanZoom = canZoom;
        if (!canZoom) {
            this.zoom = 1.0f;
        }
    }

    public float getMaxZoom() {
        return this.maxZoom;
    }

    public float getMinZoom() {
        return this.minZoom;
    }

    public void setMinZoom(float minZoom2) {
        if (minZoom2 < 0.0f) {
            minZoom2 = 0.1f;
        }
        this.minZoom = minZoom2;
    }

    public void setMaxZoom(float maxZoom2) {
        if (maxZoom2 < 1.0f) {
            maxZoom2 = 1.0f;
        }
        this.maxZoom = maxZoom2;
    }

    public void reset() {
        this.zoom = 1.0f;
        this.translateX = 0;
        this.translateY = 0;
        notifyViewChanged();
    }

    public void flingLeft(int duration) {
        final int width = this.zoomRect.width();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(new int[]{this.zoomRect.left, 0}).setDuration((long) duration);
        valueAnimator.addListener(this.animatorListenerAdapter);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                MatrixHelper.this.zoomRect.left = ((Integer) animation.getAnimatedValue()).intValue();
                MatrixHelper.this.zoomRect.right = MatrixHelper.this.zoomRect.left + width;
                MatrixHelper.this.notifyViewChanged();
            }
        });
        valueAnimator.start();
    }

    public void flingRight(int duration) {
        final int width = this.zoomRect.width();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(new int[]{this.zoomRect.right, this.originalRect.right}).setDuration((long) duration);
        valueAnimator.addListener(this.animatorListenerAdapter);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                MatrixHelper.this.zoomRect.right = ((Integer) animation.getAnimatedValue()).intValue();
                MatrixHelper.this.zoomRect.left = MatrixHelper.this.zoomRect.right - width;
                MatrixHelper.this.notifyViewChanged();
            }
        });
        valueAnimator.start();
    }

    public void flingTop(int duration) {
        final int height = this.zoomRect.height();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(new int[]{this.zoomRect.top, 0}).setDuration((long) duration);
        valueAnimator.addListener(this.animatorListenerAdapter);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                MatrixHelper.this.zoomRect.top = ((Integer) animation.getAnimatedValue()).intValue();
                MatrixHelper.this.zoomRect.bottom = MatrixHelper.this.zoomRect.top + height;
                MatrixHelper.this.notifyViewChanged();
            }
        });
        valueAnimator.start();
    }

    public void flingBottom(int duration) {
        final int height = this.zoomRect.height();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(new int[]{this.zoomRect.bottom, this.originalRect.bottom}).setDuration((long) duration);
        valueAnimator.addListener(this.animatorListenerAdapter);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                MatrixHelper.this.zoomRect.bottom = ((Integer) animation.getAnimatedValue()).intValue();
                MatrixHelper.this.zoomRect.top = MatrixHelper.this.zoomRect.bottom - height;
                MatrixHelper.this.notifyViewChanged();
            }
        });
        valueAnimator.start();
    }

    public float getZoom() {
        return this.zoom;
    }

    public float getFlingRate() {
        return this.flingRate;
    }

    public void setFlingRate(float flingRate2) {
        this.flingRate = flingRate2;
    }

    public OnInterceptListener getOnInterceptListener() {
        return this.onInterceptListener;
    }

    public void setOnInterceptListener(OnInterceptListener onInterceptListener2) {
        this.onInterceptListener = onInterceptListener2;
    }
}

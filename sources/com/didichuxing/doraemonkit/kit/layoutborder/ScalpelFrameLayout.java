package com.didichuxing.doraemonkit.kit.layoutborder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Deque;

public class ScalpelFrameLayout extends FrameLayout {
    private static final int CHILD_COUNT_ESTIMATION = 25;
    private static final int CHROME_COLOR = -7829368;
    private static final int CHROME_SHADOW_COLOR = -16777216;
    private static final boolean DEBUG = false;
    private static final int ROTATION_DEFAULT_X = -10;
    private static final int ROTATION_DEFAULT_Y = 15;
    private static final int ROTATION_MAX = 60;
    private static final int ROTATION_MIN = -60;
    private static final int SPACING_DEFAULT = 25;
    private static final int SPACING_MAX = 100;
    private static final int SPACING_MIN = 10;
    private static final int TEXT_OFFSET_DP = 2;
    private static final int TEXT_SIZE_DP = 10;
    private static final int TRACKING_HORIZONTALLY = -1;
    private static final int TRACKING_UNKNOWN = 0;
    private static final int TRACKING_VERTICALLY = 1;
    private static final float ZOOM_DEFAULT = 0.6f;
    private static final float ZOOM_MAX = 2.0f;
    private static final float ZOOM_MIN = 0.33f;
    private final Camera camera;
    private int chromeColor;
    private int chromeShadowColor;
    private final float density;
    private boolean drawIds;
    private boolean drawViews;
    private boolean enabled;
    private final SparseArray<String> idNames;
    private float lastOneX;
    private float lastOneY;
    private float lastTwoX;
    private float lastTwoY;
    private final Pool<LayeredView> layeredViewPool;
    private final Deque<LayeredView> layeredViewQueue;
    private final int[] location;
    private final Matrix matrix;
    private int multiTouchTracking;
    private int pointerOne;
    private int pointerTwo;
    private final Resources res;
    private float rotationX;
    private float rotationY;
    private final float slop;
    private float spacing;
    private final float textOffset;
    private final float textSize;
    private final Paint viewBorderPaint;
    private final Rect viewBoundsRect;
    private final BitSet visibilities;
    private float zoom;

    private static void log(String message, Object... args) {
        Log.d("Scalpel", String.format(message, args));
    }

    public static class LayeredView {
        int layer;
        View view;

        private LayeredView() {
        }

        /* access modifiers changed from: package-private */
        public void set(View view2, int layer2) {
            this.view = view2;
            this.layer = layer2;
        }

        /* access modifiers changed from: package-private */
        public void clear() {
            this.view = null;
            this.layer = -1;
        }
    }

    public ScalpelFrameLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public ScalpelFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScalpelFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.viewBoundsRect = new Rect();
        Paint paint = new Paint(1);
        this.viewBorderPaint = paint;
        this.camera = new Camera();
        this.matrix = new Matrix();
        this.location = new int[2];
        this.visibilities = new BitSet(25);
        this.idNames = new SparseArray<>();
        this.layeredViewQueue = new ArrayDeque();
        this.layeredViewPool = new Pool<LayeredView>(25) {
            /* access modifiers changed from: protected */
            public LayeredView newObject() {
                return new LayeredView();
            }
        };
        this.drawViews = true;
        this.pointerOne = -1;
        this.pointerTwo = -1;
        this.multiTouchTracking = 0;
        this.rotationY = 15.0f;
        this.rotationX = -10.0f;
        this.zoom = ZOOM_DEFAULT;
        this.spacing = 25.0f;
        this.res = context.getResources();
        float f = context.getResources().getDisplayMetrics().density;
        this.density = f;
        this.slop = (float) ViewConfiguration.get(context).getScaledTouchSlop();
        float f2 = 10.0f * f;
        this.textSize = f2;
        this.textOffset = f * 2.0f;
        setChromeColor(CHROME_COLOR);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(f2);
        setChromeShadowColor(-16777216);
        if (Build.VERSION.SDK_INT >= 16) {
            paint.setTypeface(Typeface.create("sans-serif-condensed", 0));
        }
    }

    public void setChromeColor(int color) {
        if (this.chromeColor != color) {
            this.viewBorderPaint.setColor(color);
            this.chromeColor = color;
            invalidate();
        }
    }

    public int getChromeColor() {
        return this.chromeColor;
    }

    public void setChromeShadowColor(int color) {
        if (this.chromeShadowColor != color) {
            this.viewBorderPaint.setShadowLayer(1.0f, -1.0f, 1.0f, color);
            this.chromeShadowColor = color;
            invalidate();
        }
    }

    public int getChromeShadowColor() {
        return this.chromeShadowColor;
    }

    public void setLayerInteractionEnabled(boolean enabled2) {
        if (this.enabled != enabled2) {
            this.enabled = enabled2;
            setWillNotDraw(!enabled2);
            invalidate();
        }
    }

    public boolean isLayerInteractionEnabled() {
        return this.enabled;
    }

    public void setDrawViews(boolean drawViews2) {
        if (this.drawViews != drawViews2) {
            this.drawViews = drawViews2;
            invalidate();
        }
    }

    public boolean isDrawingViews() {
        return this.drawViews;
    }

    public void setDrawIds(boolean drawIds2) {
        if (this.drawIds != drawIds2) {
            this.drawIds = drawIds2;
            invalidate();
        }
    }

    public boolean isDrawingIds() {
        return this.drawIds;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.enabled || super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int i;
        int pointerOneIndex;
        MotionEvent motionEvent = event;
        if (!this.enabled) {
            return super.onTouchEvent(event);
        }
        int action = event.getActionMasked();
        int index = 0;
        switch (action) {
            case 0:
            case 5:
                if (action != 0) {
                    index = event.getActionIndex();
                }
                if (this.pointerOne == -1) {
                    this.pointerOne = motionEvent.getPointerId(index);
                    this.lastOneX = motionEvent.getX(index);
                    this.lastOneY = motionEvent.getY(index);
                    return true;
                } else if (this.pointerTwo != -1) {
                    return true;
                } else {
                    this.pointerTwo = motionEvent.getPointerId(index);
                    this.lastTwoX = motionEvent.getX(index);
                    this.lastTwoY = motionEvent.getY(index);
                    return true;
                }
            case 1:
            case 3:
            case 6:
                int pointerId = motionEvent.getPointerId(action != 6 ? 0 : event.getActionIndex());
                if (this.pointerOne == pointerId) {
                    this.pointerOne = this.pointerTwo;
                    this.lastOneX = this.lastTwoX;
                    this.lastOneY = this.lastTwoY;
                    this.pointerTwo = -1;
                    this.multiTouchTracking = 0;
                    return true;
                } else if (this.pointerTwo != pointerId) {
                    return true;
                } else {
                    this.pointerTwo = -1;
                    this.multiTouchTracking = 0;
                    return true;
                }
            case 2:
                if (this.pointerTwo == -1) {
                    int count = event.getPointerCount();
                    for (int i2 = 0; i2 < count; i2++) {
                        if (this.pointerOne == motionEvent.getPointerId(i2)) {
                            float eventX = motionEvent.getX(i2);
                            float eventY = motionEvent.getY(i2);
                            this.rotationY = Math.min(Math.max(this.rotationY + (((eventX - this.lastOneX) / ((float) getWidth())) * 90.0f), -60.0f), 60.0f);
                            this.rotationX = Math.min(Math.max(this.rotationX + (((-(eventY - this.lastOneY)) / ((float) getHeight())) * 90.0f), -60.0f), 60.0f);
                            this.lastOneX = eventX;
                            this.lastOneY = eventY;
                            invalidate();
                        }
                    }
                    return true;
                }
                int pointerOneIndex2 = motionEvent.findPointerIndex(this.pointerOne);
                int pointerTwoIndex = motionEvent.findPointerIndex(this.pointerTwo);
                float xOne = motionEvent.getX(pointerOneIndex2);
                float yOne = motionEvent.getY(pointerOneIndex2);
                float xTwo = motionEvent.getX(pointerTwoIndex);
                float yTwo = motionEvent.getY(pointerTwoIndex);
                float dxOne = xOne - this.lastOneX;
                float dyOne = yOne - this.lastOneY;
                float dxTwo = xTwo - this.lastTwoX;
                float dyTwo = yTwo - this.lastTwoY;
                if (this.multiTouchTracking == 0) {
                    float adx = Math.abs(dxOne) + Math.abs(dxTwo);
                    float ady = Math.abs(dyOne) + Math.abs(dyTwo);
                    float f = this.slop;
                    if (adx <= f * 2.0f && ady <= f * 2.0f) {
                        i = 1;
                    } else if (adx > ady) {
                        this.multiTouchTracking = -1;
                        i = 1;
                    } else {
                        i = 1;
                        this.multiTouchTracking = 1;
                    }
                } else {
                    i = 1;
                }
                int i3 = this.multiTouchTracking;
                if (i3 == i) {
                    if (yOne >= yTwo) {
                        this.zoom += (dyOne / ((float) getHeight())) - (dyTwo / ((float) getHeight()));
                    } else {
                        this.zoom += (dyTwo / ((float) getHeight())) - (dyOne / ((float) getHeight()));
                    }
                    this.zoom = Math.min(Math.max(this.zoom, ZOOM_MIN), 2.0f);
                    invalidate();
                    int i4 = pointerOneIndex2;
                } else if (i3 == -1) {
                    if (xOne >= xTwo) {
                        int i5 = pointerOneIndex2;
                        pointerOneIndex = 1120403456;
                        this.spacing += ((dxOne / ((float) getWidth())) * 100.0f) - ((dxTwo / ((float) getWidth())) * 100.0f);
                    } else {
                        pointerOneIndex = 1120403456;
                        this.spacing += ((dxTwo / ((float) getWidth())) * 100.0f) - ((dxOne / ((float) getWidth())) * 100.0f);
                    }
                    this.spacing = Math.min(Math.max(this.spacing, 10.0f), pointerOneIndex);
                    invalidate();
                }
                if (this.multiTouchTracking == 0) {
                    return true;
                }
                this.lastOneX = xOne;
                this.lastOneY = yOne;
                this.lastTwoX = xTwo;
                this.lastTwoY = yTwo;
                return true;
            default:
                return true;
        }
    }

    public void draw(Canvas canvas) {
        float x;
        ViewGroup viewGroup;
        Canvas canvas2 = canvas;
        if (!this.enabled) {
            super.draw(canvas);
            return;
        }
        getLocationInWindow(this.location);
        int[] iArr = this.location;
        float x2 = (float) iArr[0];
        float y = (float) iArr[1];
        int saveCount = canvas.save();
        float cx = ((float) getWidth()) / 2.0f;
        float cy = ((float) getHeight()) / 2.0f;
        this.camera.save();
        this.camera.rotate(this.rotationX, this.rotationY, 0.0f);
        this.camera.getMatrix(this.matrix);
        this.camera.restore();
        this.matrix.preTranslate(-cx, -cy);
        this.matrix.postTranslate(cx, cy);
        canvas2.concat(this.matrix);
        float f = this.zoom;
        canvas2.scale(f, f, cx, cy);
        if (this.layeredViewQueue.isEmpty()) {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                LayeredView layeredView = this.layeredViewPool.obtain();
                layeredView.set(getChildAt(i), 0);
                this.layeredViewQueue.add(layeredView);
            }
            while (!this.layeredViewQueue.isEmpty()) {
                LayeredView layeredView2 = this.layeredViewQueue.removeFirst();
                View view = layeredView2.view;
                int layer = layeredView2.layer;
                layeredView2.clear();
                this.layeredViewPool.restore(layeredView2);
                if (view instanceof ViewGroup) {
                    ViewGroup viewGroup2 = (ViewGroup) view;
                    this.visibilities.clear();
                    int count2 = viewGroup2.getChildCount();
                    for (int i2 = 0; i2 < count2; i2++) {
                        View child = viewGroup2.getChildAt(i2);
                        if (child.getVisibility() == 0) {
                            this.visibilities.set(i2);
                            child.setVisibility(4);
                        }
                    }
                }
                int viewSaveCount = canvas.save();
                float f2 = this.spacing;
                float f3 = this.density;
                float cx2 = cx;
                float ty = ((float) layer) * f2 * f3 * (this.rotationX / 60.0f);
                canvas2.translate(((float) layer) * f2 * f3 * (this.rotationY / 60.0f), -ty);
                view.getLocationInWindow(this.location);
                int[] iArr2 = this.location;
                float f4 = ty;
                canvas2.translate(((float) iArr2[0]) - x2, ((float) iArr2[1]) - y);
                float y2 = y;
                this.viewBoundsRect.set(0, 0, view.getWidth(), view.getHeight());
                canvas2.drawRect(this.viewBoundsRect, this.viewBorderPaint);
                if (this.drawViews && !(view instanceof SurfaceView)) {
                    view.draw(canvas2);
                }
                if (this.drawIds) {
                    int id = view.getId();
                    if (id != -1) {
                        int i3 = id;
                        canvas2.drawText(nameForId(id), this.textOffset, this.textSize, this.viewBorderPaint);
                    }
                }
                canvas2.restoreToCount(viewSaveCount);
                if (view instanceof ViewGroup) {
                    ViewGroup viewGroup3 = (ViewGroup) view;
                    int i4 = 0;
                    int count3 = viewGroup3.getChildCount();
                    while (i4 < count3) {
                        if (this.visibilities.get(i4)) {
                            View child2 = viewGroup3.getChildAt(i4);
                            viewGroup = viewGroup3;
                            child2.setVisibility(0);
                            LayeredView childLayeredView = this.layeredViewPool.obtain();
                            x = x2;
                            childLayeredView.set(child2, layer + 1);
                            this.layeredViewQueue.add(childLayeredView);
                        } else {
                            viewGroup = viewGroup3;
                            x = x2;
                        }
                        i4++;
                        viewGroup3 = viewGroup;
                        x2 = x;
                    }
                }
                cx = cx2;
                y = y2;
                x2 = x2;
            }
            canvas2.restoreToCount(saveCount);
            return;
        }
        float f5 = y;
        throw new AssertionError("View queue is not empty.");
    }

    private String nameForId(int id) {
        String name = this.idNames.get(id);
        if (name == null) {
            try {
                name = this.res.getResourceEntryName(id);
            } catch (Resources.NotFoundException e) {
                name = String.format("0x%8x", new Object[]{Integer.valueOf(id)});
            }
            this.idNames.put(id, name);
        }
        return name;
    }

    public static abstract class Pool<T> {
        private final Deque<T> pool;

        /* access modifiers changed from: protected */
        public abstract T newObject();

        Pool(int initialSize) {
            this.pool = new ArrayDeque(initialSize);
            for (int i = 0; i < initialSize; i++) {
                this.pool.addLast(newObject());
            }
        }

        /* access modifiers changed from: package-private */
        public T obtain() {
            return this.pool.isEmpty() ? newObject() : this.pool.removeLast();
        }

        /* access modifiers changed from: package-private */
        public void restore(T instance) {
            this.pool.addLast(instance);
        }
    }
}

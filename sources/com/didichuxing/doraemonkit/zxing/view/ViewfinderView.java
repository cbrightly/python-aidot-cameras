package com.didichuxing.doraemonkit.zxing.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.alibaba.fastjson.asm.Opcodes;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.zxing.camera.CameraManager;
import com.google.zxing.ResultPoint;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.util.Collection;
import java.util.HashSet;

public final class ViewfinderView extends View {
    private static final long ANIMATION_DELAY = 10;
    private static final int CORNER_RECT_HEIGHT = 40;
    private static final int CORNER_RECT_WIDTH = 8;
    private static final int OPAQUE = 255;
    private static final int[] SCANNER_ALPHA = {0, 64, 128, Opcodes.CHECKCAST, 255, Opcodes.CHECKCAST, 128, 64};
    private static final int SCANNER_LINE_HEIGHT = 10;
    private static final int SCANNER_LINE_MOVE_DISTANCE = 5;
    public static int scannerEnd = 0;
    public static int scannerStart = 0;
    private final int cornerColor;
    private final int frameColor;
    private final String labelText;
    private final int labelTextColor;
    private final float labelTextSize;
    private final int laserColor;
    private Collection<ResultPoint> lastPossibleResultPoints;
    private final int maskColor;
    private final Paint paint;
    private Collection<ResultPoint> possibleResultPoints;
    private Bitmap resultBitmap;
    private final int resultColor;
    private final int resultPointColor;

    /* JADX INFO: finally extract failed */
    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ViewfinderView);
        try {
            this.laserColor = array.getColor(R.styleable.ViewfinderView_dkLaserColor, MotionEventCompat.ACTION_POINTER_INDEX_MASK);
            this.cornerColor = array.getColor(R.styleable.ViewfinderView_dkCornerColor, MotionEventCompat.ACTION_POINTER_INDEX_MASK);
            this.frameColor = array.getColor(R.styleable.ViewfinderView_dkFrameColor, ViewCompat.MEASURED_SIZE_MASK);
            this.resultPointColor = array.getColor(R.styleable.ViewfinderView_dkResultPointColor, -1056964864);
            this.maskColor = array.getColor(R.styleable.ViewfinderView_dkMaskColor, 1610612736);
            this.resultColor = array.getColor(R.styleable.ViewfinderView_dkResultColor, -1342177280);
            this.labelTextColor = array.getColor(R.styleable.ViewfinderView_dkLabelTextColor, -1862270977);
            this.labelText = array.getString(R.styleable.ViewfinderView_dkLabelText);
            this.labelTextSize = array.getFloat(R.styleable.ViewfinderView_dkLabelTextSize, 36.0f);
            array.recycle();
            Paint paint2 = new Paint();
            this.paint = paint2;
            paint2.setAntiAlias(true);
            this.possibleResultPoints = new HashSet(5);
        } catch (Throwable th) {
            array.recycle();
            throw th;
        }
    }

    public void onDraw(Canvas canvas) {
        Rect frame = CameraManager.get().getFramingRect();
        if (frame != null) {
            if (scannerStart == 0 || scannerEnd == 0) {
                scannerStart = frame.top;
                scannerEnd = frame.bottom;
            }
            drawExterior(canvas, frame, canvas.getWidth(), canvas.getHeight());
            if (this.resultBitmap != null) {
                this.paint.setAlpha(255);
                canvas.drawBitmap(this.resultBitmap, (float) frame.left, (float) frame.top, this.paint);
                return;
            }
            drawFrame(canvas, frame);
            drawCorner(canvas, frame);
            drawTextInfo(canvas, frame);
            drawLaserScanner(canvas, frame);
            Collection<ResultPoint> currentPossible = this.possibleResultPoints;
            Collection<ResultPoint> currentLast = this.lastPossibleResultPoints;
            if (currentPossible.isEmpty()) {
                this.lastPossibleResultPoints = null;
            } else {
                this.possibleResultPoints = new HashSet(5);
                this.lastPossibleResultPoints = currentPossible;
                this.paint.setAlpha(255);
                this.paint.setColor(this.resultPointColor);
                for (ResultPoint point : currentPossible) {
                    canvas.drawCircle(((float) frame.left) + point.getX(), ((float) frame.top) + point.getY(), 6.0f, this.paint);
                }
            }
            if (currentLast != null) {
                this.paint.setAlpha(NeedPermissionEvent.PER_IPC_SPEAK_PERM);
                this.paint.setColor(this.resultPointColor);
                for (ResultPoint point2 : currentLast) {
                    canvas.drawCircle(((float) frame.left) + point2.getX(), ((float) frame.top) + point2.getY(), 3.0f, this.paint);
                }
            }
            postInvalidateDelayed(10, frame.left, frame.top, frame.right, frame.bottom);
        }
    }

    private void drawTextInfo(Canvas canvas, Rect frame) {
        this.paint.setColor(this.labelTextColor);
        this.paint.setTextSize(this.labelTextSize);
        this.paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(this.labelText, (float) (frame.left + (frame.width() / 2)), (float) (frame.top - 40), this.paint);
    }

    private void drawCorner(Canvas canvas, Rect frame) {
        this.paint.setColor(this.cornerColor);
        int i = frame.left;
        int i2 = frame.top;
        canvas.drawRect((float) i, (float) i2, (float) (i + 8), (float) (i2 + 40), this.paint);
        int i3 = frame.left;
        int i4 = frame.top;
        canvas.drawRect((float) i3, (float) i4, (float) (i3 + 40), (float) (i4 + 8), this.paint);
        int i5 = frame.right;
        int i6 = frame.top;
        Canvas canvas2 = canvas;
        canvas2.drawRect((float) (i5 - 8), (float) i6, (float) i5, (float) (i6 + 40), this.paint);
        int i7 = frame.right;
        int i8 = frame.top;
        canvas2.drawRect((float) (i7 - 40), (float) i8, (float) i7, (float) (i8 + 8), this.paint);
        int i9 = frame.left;
        int i10 = frame.bottom;
        canvas.drawRect((float) i9, (float) (i10 - 8), (float) (i9 + 40), (float) i10, this.paint);
        int i11 = frame.left;
        int i12 = frame.bottom;
        canvas.drawRect((float) i11, (float) (i12 - 40), (float) (i11 + 8), (float) i12, this.paint);
        int i13 = frame.right;
        int i14 = frame.bottom;
        Canvas canvas3 = canvas;
        canvas3.drawRect((float) (i13 - 8), (float) (i14 - 40), (float) i13, (float) i14, this.paint);
        int i15 = frame.right;
        int i16 = frame.bottom;
        Canvas canvas4 = canvas;
        canvas4.drawRect((float) (i15 - 40), (float) (i16 - 8), (float) i15, (float) i16, this.paint);
    }

    private void drawLaserScanner(Canvas canvas, Rect frame) {
        this.paint.setColor(this.laserColor);
        int i = frame.left;
        int i2 = scannerStart;
        LinearGradient linearGradient = new LinearGradient((float) i, (float) i2, (float) i, (float) (i2 + 10), shadeColor(this.laserColor), this.laserColor, Shader.TileMode.MIRROR);
        int i3 = this.laserColor;
        RadialGradient radialGradient = new RadialGradient((float) (frame.left + (frame.width() / 2)), (float) (scannerStart + 5), 360.0f, i3, shadeColor(i3), Shader.TileMode.MIRROR);
        new SweepGradient((float) (frame.left + (frame.width() / 2)), (float) (scannerStart + 10), shadeColor(this.laserColor), this.laserColor);
        new ComposeShader(radialGradient, linearGradient, PorterDuff.Mode.ADD);
        this.paint.setShader(radialGradient);
        if (scannerStart <= scannerEnd) {
            int i4 = scannerStart;
            canvas.drawOval(new RectF((float) (frame.left + 20), (float) i4, (float) (frame.right - 20), (float) (i4 + 10)), this.paint);
            scannerStart += 5;
        } else {
            scannerStart = frame.top;
        }
        this.paint.setShader((Shader) null);
    }

    public int shadeColor(int color) {
        String hax = Integer.toHexString(color);
        return Integer.valueOf("20" + hax.substring(2), 16).intValue();
    }

    private void drawFrame(Canvas canvas, Rect frame) {
        this.paint.setColor(this.frameColor);
        float f = (float) frame.left;
        int i = frame.top;
        canvas.drawRect(f, (float) i, (float) (frame.right + 1), (float) (i + 2), this.paint);
        int i2 = frame.left;
        canvas.drawRect((float) i2, (float) (frame.top + 2), (float) (i2 + 2), (float) (frame.bottom - 1), this.paint);
        int i3 = frame.right;
        canvas.drawRect((float) (i3 - 1), (float) frame.top, (float) (i3 + 1), (float) (frame.bottom - 1), this.paint);
        float f2 = (float) frame.left;
        int i4 = frame.bottom;
        canvas.drawRect(f2, (float) (i4 - 1), (float) (frame.right + 1), (float) (i4 + 1), this.paint);
    }

    private void drawExterior(Canvas canvas, Rect frame, int width, int height) {
        Rect rect = frame;
        int i = width;
        this.paint.setColor(this.resultBitmap != null ? this.resultColor : this.maskColor);
        canvas.drawRect(0.0f, 0.0f, (float) i, (float) rect.top, this.paint);
        canvas.drawRect(0.0f, (float) rect.top, (float) rect.left, (float) (rect.bottom + 1), this.paint);
        canvas.drawRect((float) (rect.right + 1), (float) rect.top, (float) i, (float) (rect.bottom + 1), this.paint);
        canvas.drawRect(0.0f, (float) (rect.bottom + 1), (float) i, (float) height, this.paint);
    }

    public void drawViewfinder() {
        this.resultBitmap = null;
        invalidate();
    }

    public void drawResultBitmap(Bitmap barcode) {
        this.resultBitmap = barcode;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
        this.possibleResultPoints.add(point);
    }
}

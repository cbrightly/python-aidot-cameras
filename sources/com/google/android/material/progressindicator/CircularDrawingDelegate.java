package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import com.google.android.material.color.MaterialColors;

public final class CircularDrawingDelegate extends DrawingDelegate<CircularProgressIndicatorSpec> {
    private float adjustedRadius;
    private int arcDirectionFactor = 1;
    private float displayedCornerRadius;
    private float displayedTrackThickness;

    public CircularDrawingDelegate(@NonNull CircularProgressIndicatorSpec spec) {
        super(spec);
    }

    public int getPreferredWidth() {
        return getSize();
    }

    public int getPreferredHeight() {
        return getSize();
    }

    public void adjustCanvas(@NonNull Canvas canvas, @FloatRange(from = 0.0d, to = 1.0d) float trackThicknessFraction) {
        S s = this.spec;
        float outerRadiusWithInset = (((float) ((CircularProgressIndicatorSpec) s).indicatorSize) / 2.0f) + ((float) ((CircularProgressIndicatorSpec) s).indicatorInset);
        canvas.translate(outerRadiusWithInset, outerRadiusWithInset);
        canvas.rotate(-90.0f);
        canvas.clipRect(-outerRadiusWithInset, -outerRadiusWithInset, outerRadiusWithInset, outerRadiusWithInset);
        S s2 = this.spec;
        this.arcDirectionFactor = ((CircularProgressIndicatorSpec) s2).indicatorDirection == 0 ? 1 : -1;
        this.displayedTrackThickness = ((float) ((CircularProgressIndicatorSpec) s2).trackThickness) * trackThicknessFraction;
        this.displayedCornerRadius = ((float) ((CircularProgressIndicatorSpec) s2).trackCornerRadius) * trackThicknessFraction;
        this.adjustedRadius = ((float) (((CircularProgressIndicatorSpec) s2).indicatorSize - ((CircularProgressIndicatorSpec) s2).trackThickness)) / 2.0f;
        if ((this.drawable.isShowing() && ((CircularProgressIndicatorSpec) this.spec).showAnimationBehavior == 2) || (this.drawable.isHiding() && ((CircularProgressIndicatorSpec) this.spec).hideAnimationBehavior == 1)) {
            this.adjustedRadius += ((1.0f - trackThicknessFraction) * ((float) ((CircularProgressIndicatorSpec) this.spec).trackThickness)) / 2.0f;
        } else if ((this.drawable.isShowing() && ((CircularProgressIndicatorSpec) this.spec).showAnimationBehavior == 1) || (this.drawable.isHiding() && ((CircularProgressIndicatorSpec) this.spec).hideAnimationBehavior == 2)) {
            this.adjustedRadius -= ((1.0f - trackThicknessFraction) * ((float) ((CircularProgressIndicatorSpec) this.spec).trackThickness)) / 2.0f;
        }
    }

    /* access modifiers changed from: package-private */
    public void fillIndicator(@NonNull Canvas canvas, @NonNull Paint paint, @FloatRange(from = 0.0d, to = 1.0d) float startFraction, @FloatRange(from = 0.0d, to = 1.0d) float endFraction, @ColorInt int color) {
        Paint paint2 = paint;
        if (startFraction != endFraction) {
            paint2.setStyle(Paint.Style.STROKE);
            paint2.setStrokeCap(Paint.Cap.BUTT);
            paint2.setAntiAlias(true);
            paint2.setColor(color);
            paint2.setStrokeWidth(this.displayedTrackThickness);
            int i = this.arcDirectionFactor;
            float startDegree = startFraction * 360.0f * ((float) i);
            float arcDegree = (endFraction >= startFraction ? endFraction - startFraction : (endFraction + 1.0f) - startFraction) * 360.0f * ((float) i);
            float f = this.adjustedRadius;
            canvas.drawArc(new RectF(-f, -f, f, f), startDegree, arcDegree, false, paint);
            if (this.displayedCornerRadius > 0.0f && Math.abs(arcDegree) < 360.0f) {
                paint2.setStyle(Paint.Style.FILL);
                float f2 = this.displayedCornerRadius;
                RectF cornerPatternRectBound = new RectF(-f2, -f2, f2, f2);
                drawRoundedEnd(canvas, paint, this.displayedTrackThickness, this.displayedCornerRadius, startDegree, true, cornerPatternRectBound);
                drawRoundedEnd(canvas, paint, this.displayedTrackThickness, this.displayedCornerRadius, startDegree + arcDegree, false, cornerPatternRectBound);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void fillTrack(@NonNull Canvas canvas, @NonNull Paint paint) {
        int trackColor = MaterialColors.compositeARGBWithAlpha(((CircularProgressIndicatorSpec) this.spec).trackColor, this.drawable.getAlpha());
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setAntiAlias(true);
        paint.setColor(trackColor);
        paint.setStrokeWidth(this.displayedTrackThickness);
        float f = this.adjustedRadius;
        canvas.drawArc(new RectF(-f, -f, f, f), 0.0f, 360.0f, false, paint);
    }

    private int getSize() {
        S s = this.spec;
        return ((CircularProgressIndicatorSpec) s).indicatorSize + (((CircularProgressIndicatorSpec) s).indicatorInset * 2);
    }

    private void drawRoundedEnd(Canvas canvas, Paint paint, float trackSize, float cornerRadius, float positionInDeg, boolean isStartPosition, RectF cornerPatternRectBound) {
        Canvas canvas2 = canvas;
        float startOrEndFactor = isStartPosition ? -1.0f : 1.0f;
        canvas.save();
        canvas.rotate(positionInDeg);
        Paint paint2 = paint;
        canvas.drawRect((this.adjustedRadius - (trackSize / 2.0f)) + cornerRadius, Math.min(0.0f, startOrEndFactor * cornerRadius * ((float) this.arcDirectionFactor)), (this.adjustedRadius + (trackSize / 2.0f)) - cornerRadius, Math.max(0.0f, startOrEndFactor * cornerRadius * ((float) this.arcDirectionFactor)), paint2);
        canvas.translate((this.adjustedRadius - (trackSize / 2.0f)) + cornerRadius, 0.0f);
        RectF rectF = cornerPatternRectBound;
        canvas.drawArc(rectF, 180.0f, (-startOrEndFactor) * 90.0f * ((float) this.arcDirectionFactor), true, paint2);
        canvas.translate(trackSize - (cornerRadius * 2.0f), 0.0f);
        canvas.drawArc(rectF, 0.0f, 90.0f * startOrEndFactor * ((float) this.arcDirectionFactor), true, paint2);
        canvas.restore();
    }
}

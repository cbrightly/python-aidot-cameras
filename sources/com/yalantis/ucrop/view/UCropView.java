package com.yalantis.ucrop.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import com.luck.picture.lib.R;
import com.yalantis.ucrop.callback.CropBoundsChangeListener;
import com.yalantis.ucrop.callback.OverlayViewChangeListener;

public class UCropView extends FrameLayout {
    /* access modifiers changed from: private */
    public GestureCropImageView mGestureCropImageView;
    /* access modifiers changed from: private */
    public final OverlayView mViewOverlay;

    public UCropView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UCropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.ucrop_view, this, true);
        this.mGestureCropImageView = (GestureCropImageView) findViewById(R.id.image_view_crop);
        OverlayView overlayView = (OverlayView) findViewById(R.id.view_overlay);
        this.mViewOverlay = overlayView;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ucrop_UCropView);
        overlayView.processStyledAttributes(a);
        this.mGestureCropImageView.processStyledAttributes(a);
        a.recycle();
        setListenersToViews();
    }

    private void setListenersToViews() {
        this.mGestureCropImageView.setCropBoundsChangeListener(new CropBoundsChangeListener() {
            public void onCropAspectRatioChanged(float cropRatio) {
                UCropView.this.mViewOverlay.setTargetAspectRatio(cropRatio);
            }
        });
        this.mViewOverlay.setOverlayViewChangeListener(new OverlayViewChangeListener() {
            public void onCropRectUpdated(RectF cropRect) {
                UCropView.this.mGestureCropImageView.setCropRect(cropRect);
            }

            public void postTranslate(float deltaX, float deltaY) {
                UCropView.this.mGestureCropImageView.postTranslate(deltaX, deltaY);
            }
        });
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @NonNull
    public GestureCropImageView getCropImageView() {
        return this.mGestureCropImageView;
    }

    @NonNull
    public OverlayView getOverlayView() {
        return this.mViewOverlay;
    }

    public void resetCropImageView() {
        removeView(this.mGestureCropImageView);
        this.mGestureCropImageView = new GestureCropImageView(getContext());
        setListenersToViews();
        this.mGestureCropImageView.setCropRect(getOverlayView().getCropViewRect());
        addView(this.mGestureCropImageView, 0);
    }
}

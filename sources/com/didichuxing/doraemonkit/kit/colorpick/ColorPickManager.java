package com.didichuxing.doraemonkit.kit.colorpick;

import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;

public class ColorPickManager {
    private ColorPickerDokitView mColorPickerDokitView;
    private MediaProjection mMediaProjection;
    private MediaProjectionManager mMediaProjectionManager;

    public static class Holder {
        /* access modifiers changed from: private */
        public static ColorPickManager INSTANCE = new ColorPickManager();

        private Holder() {
        }
    }

    public static ColorPickManager getInstance() {
        return Holder.INSTANCE;
    }

    /* access modifiers changed from: package-private */
    public MediaProjection getMediaProjection() {
        return this.mMediaProjection;
    }

    /* access modifiers changed from: package-private */
    public void setMediaProjection(MediaProjection mMediaProjection2) {
        this.mMediaProjection = mMediaProjection2;
    }

    public ColorPickerDokitView getColorPickerDokitView() {
        return this.mColorPickerDokitView;
    }

    public void setColorPickerDokitView(ColorPickerDokitView mColorPickerDokitView2) {
        this.mColorPickerDokitView = mColorPickerDokitView2;
    }
}

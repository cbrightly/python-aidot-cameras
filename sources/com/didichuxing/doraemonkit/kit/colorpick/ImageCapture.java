package com.didichuxing.doraemonkit.kit.colorpick;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.RequiresApi;
import com.blankj.utilcode.util.d;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.util.LogHelper;
import com.didichuxing.doraemonkit.util.UIUtils;
import java.nio.ByteBuffer;

@RequiresApi(api = 21)
public class ImageCapture {
    private static final String TAG = "ImageCapture";
    private boolean isCapturing;
    private Bitmap mBitmap;
    private ColorPickerDokitView mColorPickerDokitView;
    private ImageReader mImageReader;
    private MediaProjection mMediaProjection;
    private MediaProjectionManager mMediaProjectionManager;

    public void init(Context context, Bundle bundle, ColorPickerDokitView colorPickerDokitView) {
        this.mColorPickerDokitView = colorPickerDokitView;
        if (DoraemonKit.APPLICATION.getPackageManager().getApplicationInfo(d.c(), 0).targetSdkVersion < 29) {
            MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) context.getSystemService("media_projection");
            this.mMediaProjectionManager = mediaProjectionManager;
            if (mediaProjectionManager != null) {
                MediaProjection mediaProjection = mediaProjectionManager.getMediaProjection(-1, (Intent) bundle.getParcelable("data"));
                this.mMediaProjection = mediaProjection;
                initImageRead(mediaProjection);
            }
        } else if (ColorPickManager.getInstance().getMediaProjection() != null) {
            colorPickerDokitView.onScreenServiceReady();
        } else {
            try {
                if (Build.VERSION.SDK_INT >= 26) {
                    Intent intent = new Intent(context, ScreenRecorderService.class);
                    intent.putExtra("data", bundle.getParcelable("data"));
                    context.startForegroundService(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void initImageRead(MediaProjection mediaProjection) {
        if (mediaProjection == null) {
            LogHelper.e(TAG, "mediaProjection == null");
            return;
        }
        int width = UIUtils.getWidthPixels();
        int height = UIUtils.getRealHeightPixels();
        int dpi = UIUtils.getDensityDpi();
        ImageReader newInstance = ImageReader.newInstance(width, height, 1, 2);
        this.mImageReader = newInstance;
        mediaProjection.createVirtualDisplay("ScreenCapture", width, height, dpi, 16, newInstance.getSurface(), (VirtualDisplay.Callback) null, (Handler) null);
    }

    /* access modifiers changed from: package-private */
    public void capture() {
        ImageReader imageReader;
        if (!this.isCapturing && (imageReader = this.mImageReader) != null) {
            this.isCapturing = true;
            Image image = imageReader.acquireLatestImage();
            if (image != null) {
                int width = image.getWidth();
                int height = image.getHeight();
                Image.Plane[] planes = image.getPlanes();
                ByteBuffer buffer = planes[0].getBuffer();
                int pixelStride = planes[0].getPixelStride();
                Bitmap recordBitmap = Bitmap.createBitmap(width + ((planes[0].getRowStride() - (pixelStride * width)) / pixelStride), height, Bitmap.Config.ARGB_8888);
                recordBitmap.copyPixelsFromBuffer(buffer);
                this.mBitmap = Bitmap.createBitmap(recordBitmap, 0, 0, width, height);
                image.close();
                this.isCapturing = false;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Bitmap getPartBitmap(int x, int y, int width, int height) {
        Bitmap bitmap = this.mBitmap;
        if (bitmap == null) {
            return null;
        }
        if (x < 0) {
            x = 0;
        }
        if (x + width > bitmap.getWidth()) {
            x = this.mBitmap.getWidth() - width;
        }
        if (y < 0) {
            y = 0;
        }
        if (y + height > this.mBitmap.getHeight()) {
            y = this.mBitmap.getHeight() - height;
        }
        return Bitmap.createBitmap(this.mBitmap, x, y, width, height);
    }

    /* access modifiers changed from: package-private */
    public void destroy() {
        ImageReader imageReader = this.mImageReader;
        if (imageReader != null) {
            imageReader.close();
            this.mImageReader = null;
        }
        MediaProjection mediaProjection = this.mMediaProjection;
        if (mediaProjection != null) {
            mediaProjection.stop();
            this.mMediaProjection = null;
        }
        this.mMediaProjectionManager = null;
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.mBitmap = null;
        }
    }
}

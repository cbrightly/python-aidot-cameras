package com.yalantis.ucrop.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.tools.SdkVersionUtils;
import com.yalantis.ucrop.callback.BitmapCropCallback;
import com.yalantis.ucrop.model.CropParameters;
import com.yalantis.ucrop.model.ImageState;
import com.yalantis.ucrop.util.BitmapLoadUtils;
import com.yalantis.ucrop.util.FileUtils;
import com.yalantis.ucrop.util.ImageHeaderParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

public class BitmapCropTask extends AsyncTask<Void, Void, Throwable> {
    private static final String TAG = "BitmapCropTask";
    private int cropOffsetX;
    private int cropOffsetY;
    private final Bitmap.CompressFormat mCompressFormat;
    private final int mCompressQuality;
    private final WeakReference<Context> mContextWeakReference;
    private final BitmapCropCallback mCropCallback;
    private final RectF mCropRect;
    private int mCroppedImageHeight;
    private int mCroppedImageWidth;
    private float mCurrentAngle;
    private final RectF mCurrentImageRect;
    private float mCurrentScale;
    private final String mImageInputPath;
    private final String mImageOutputPath;
    private final int mMaxResultImageSizeX;
    private final int mMaxResultImageSizeY;
    private Bitmap mViewBitmap;

    public BitmapCropTask(@NonNull Context context, @Nullable Bitmap viewBitmap, @NonNull ImageState imageState, @NonNull CropParameters cropParameters, @Nullable BitmapCropCallback cropCallback) {
        this.mContextWeakReference = new WeakReference<>(context);
        this.mViewBitmap = viewBitmap;
        this.mCropRect = imageState.getCropRect();
        this.mCurrentImageRect = imageState.getCurrentImageRect();
        this.mCurrentScale = imageState.getCurrentScale();
        this.mCurrentAngle = imageState.getCurrentAngle();
        this.mMaxResultImageSizeX = cropParameters.getMaxResultImageSizeX();
        this.mMaxResultImageSizeY = cropParameters.getMaxResultImageSizeY();
        this.mCompressFormat = cropParameters.getCompressFormat();
        this.mCompressQuality = cropParameters.getCompressQuality();
        this.mImageInputPath = cropParameters.getImageInputPath();
        this.mImageOutputPath = cropParameters.getImageOutputPath();
        this.mCropCallback = cropCallback;
    }

    private Context getContext() {
        return (Context) this.mContextWeakReference.get();
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Throwable doInBackground(Void... params) {
        Bitmap bitmap = this.mViewBitmap;
        if (bitmap == null) {
            return new NullPointerException("ViewBitmap is null");
        }
        if (bitmap.isRecycled()) {
            return new NullPointerException("ViewBitmap is recycled");
        }
        if (this.mCurrentImageRect.isEmpty()) {
            return new NullPointerException("CurrentImageRect is empty");
        }
        try {
            crop();
            this.mViewBitmap = null;
            return null;
        } catch (Throwable throwable) {
            return throwable;
        }
    }

    private boolean crop() {
        ExifInterface originalExif;
        if (this.mMaxResultImageSizeX > 0 && this.mMaxResultImageSizeY > 0) {
            float cropWidth = this.mCropRect.width() / this.mCurrentScale;
            float cropHeight = this.mCropRect.height() / this.mCurrentScale;
            int i = this.mMaxResultImageSizeX;
            if (cropWidth > ((float) i) || cropHeight > ((float) this.mMaxResultImageSizeY)) {
                float resizeScale = Math.min(((float) i) / cropWidth, ((float) this.mMaxResultImageSizeY) / cropHeight);
                Bitmap bitmap = this.mViewBitmap;
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, Math.round(((float) bitmap.getWidth()) * resizeScale), Math.round(((float) this.mViewBitmap.getHeight()) * resizeScale), false);
                Bitmap bitmap2 = this.mViewBitmap;
                if (bitmap2 != resizedBitmap) {
                    bitmap2.recycle();
                }
                this.mViewBitmap = resizedBitmap;
                this.mCurrentScale /= resizeScale;
            }
        }
        if (this.mCurrentAngle != 0.0f) {
            Matrix tempMatrix = new Matrix();
            tempMatrix.setRotate(this.mCurrentAngle, (float) (this.mViewBitmap.getWidth() / 2), (float) (this.mViewBitmap.getHeight() / 2));
            Bitmap bitmap3 = this.mViewBitmap;
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap3, 0, 0, bitmap3.getWidth(), this.mViewBitmap.getHeight(), tempMatrix, true);
            Bitmap bitmap4 = this.mViewBitmap;
            if (bitmap4 != rotatedBitmap) {
                bitmap4.recycle();
            }
            this.mViewBitmap = rotatedBitmap;
        }
        this.cropOffsetX = Math.round((this.mCropRect.left - this.mCurrentImageRect.left) / this.mCurrentScale);
        this.cropOffsetY = Math.round((this.mCropRect.top - this.mCurrentImageRect.top) / this.mCurrentScale);
        this.mCroppedImageWidth = Math.round(this.mCropRect.width() / this.mCurrentScale);
        int round = Math.round(this.mCropRect.height() / this.mCurrentScale);
        this.mCroppedImageHeight = round;
        boolean shouldCrop = shouldCrop(this.mCroppedImageWidth, round);
        Log.i(TAG, "Should crop: " + shouldCrop);
        if (shouldCrop) {
            ParcelFileDescriptor parcelFileDescriptor = null;
            if (!SdkVersionUtils.checkedAndroid_Q() || !PictureMimeType.isContent(this.mImageInputPath)) {
                originalExif = new ExifInterface(this.mImageInputPath);
            } else {
                parcelFileDescriptor = getContext().getContentResolver().openFileDescriptor(Uri.parse(this.mImageInputPath), "r");
                originalExif = new ExifInterface((InputStream) new FileInputStream(parcelFileDescriptor.getFileDescriptor()));
            }
            saveImage(Bitmap.createBitmap(this.mViewBitmap, this.cropOffsetX, this.cropOffsetY, this.mCroppedImageWidth, this.mCroppedImageHeight));
            if (this.mCompressFormat.equals(Bitmap.CompressFormat.JPEG)) {
                ImageHeaderParser.copyExif(originalExif, this.mCroppedImageWidth, this.mCroppedImageHeight, this.mImageOutputPath);
            }
            if (parcelFileDescriptor == null) {
                return true;
            }
            BitmapLoadUtils.close(parcelFileDescriptor);
            return true;
        }
        if (!SdkVersionUtils.checkedAndroid_Q() || !PictureMimeType.isContent(this.mImageInputPath)) {
            FileUtils.copyFile(this.mImageInputPath, this.mImageOutputPath);
        } else {
            ParcelFileDescriptor parcelFileDescriptor2 = getContext().getContentResolver().openFileDescriptor(Uri.parse(this.mImageInputPath), "r");
            FileUtils.copyFile(new FileInputStream(parcelFileDescriptor2.getFileDescriptor()), this.mImageOutputPath);
            BitmapLoadUtils.close(parcelFileDescriptor2);
        }
        return false;
    }

    private void saveImage(@NonNull Bitmap croppedBitmap) {
        Context context = getContext();
        if (context != null) {
            OutputStream outputStream = null;
            try {
                outputStream = context.getContentResolver().openOutputStream(Uri.fromFile(new File(this.mImageOutputPath)));
                croppedBitmap.compress(this.mCompressFormat, this.mCompressQuality, outputStream);
                croppedBitmap.recycle();
            } finally {
                BitmapLoadUtils.close(outputStream);
            }
        }
    }

    private boolean shouldCrop(int width, int height) {
        int pixelError = 1 + Math.round(((float) Math.max(width, height)) / 1000.0f);
        return (this.mMaxResultImageSizeX > 0 && this.mMaxResultImageSizeY > 0) || Math.abs(this.mCropRect.left - this.mCurrentImageRect.left) > ((float) pixelError) || Math.abs(this.mCropRect.top - this.mCurrentImageRect.top) > ((float) pixelError) || Math.abs(this.mCropRect.bottom - this.mCurrentImageRect.bottom) > ((float) pixelError) || Math.abs(this.mCropRect.right - this.mCurrentImageRect.right) > ((float) pixelError) || this.mCurrentAngle != 0.0f;
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(@Nullable Throwable t) {
        BitmapCropCallback bitmapCropCallback = this.mCropCallback;
        if (bitmapCropCallback == null) {
            return;
        }
        if (t == null) {
            this.mCropCallback.onBitmapCropped(Uri.fromFile(new File(this.mImageOutputPath)), this.cropOffsetX, this.cropOffsetY, this.mCroppedImageWidth, this.mCroppedImageHeight);
            return;
        }
        bitmapCropCallback.onCropFailure(t);
    }
}

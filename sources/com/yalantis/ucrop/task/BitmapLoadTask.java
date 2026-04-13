package com.yalantis.ucrop.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.tools.SdkVersionUtils;
import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;
import com.yalantis.ucrop.util.FileUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import org.apache.http.l;

public class BitmapLoadTask extends AsyncTask<Void, Void, BitmapWorkerResult> {
    private static final int MAX_BITMAP_SIZE = 104857600;
    private static final String TAG = "BitmapWorkerTask";
    private final BitmapLoadCallback mBitmapLoadCallback;
    private final Context mContext;
    private Uri mInputUri;
    private final Uri mOutputUri;
    private final int mRequiredHeight;
    private final int mRequiredWidth;

    public static class BitmapWorkerResult {
        Bitmap mBitmapResult;
        Exception mBitmapWorkerException;
        ExifInfo mExifInfo;

        public BitmapWorkerResult(@NonNull Bitmap bitmapResult, @NonNull ExifInfo exifInfo) {
            this.mBitmapResult = bitmapResult;
            this.mExifInfo = exifInfo;
        }

        public BitmapWorkerResult(@NonNull Exception bitmapWorkerException) {
            this.mBitmapWorkerException = bitmapWorkerException;
        }
    }

    public BitmapLoadTask(@NonNull Context context, @NonNull Uri inputUri, @Nullable Uri outputUri, int requiredWidth, int requiredHeight, BitmapLoadCallback loadCallback) {
        this.mContext = context;
        this.mInputUri = inputUri;
        this.mOutputUri = outputUri;
        this.mRequiredWidth = requiredWidth;
        this.mRequiredHeight = requiredHeight;
        this.mBitmapLoadCallback = loadCallback;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public BitmapWorkerResult doInBackground(Void... params) {
        InputStream stream;
        if (this.mInputUri == null) {
            return new BitmapWorkerResult(new NullPointerException("Input Uri cannot be null"));
        }
        try {
            processInputUri();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = BitmapLoadUtils.calculateInSampleSize(options, this.mRequiredWidth, this.mRequiredHeight);
            options.inJustDecodeBounds = false;
            Bitmap decodeSampledBitmap = null;
            boolean decodeAttemptSuccess = false;
            while (!decodeAttemptSuccess) {
                try {
                    stream = this.mContext.getContentResolver().openInputStream(this.mInputUri);
                    decodeSampledBitmap = BitmapFactory.decodeStream(stream, (Rect) null, options);
                    if (options.outWidth == -1 || options.outHeight == -1) {
                        BitmapWorkerResult bitmapWorkerResult = new BitmapWorkerResult(new IllegalArgumentException("Bounds for bitmap could not be retrieved from the Uri: [" + this.mInputUri + "]"));
                        BitmapLoadUtils.close(stream);
                        return bitmapWorkerResult;
                    }
                    BitmapLoadUtils.close(stream);
                    if (!checkSize(decodeSampledBitmap, options)) {
                        decodeAttemptSuccess = true;
                    }
                } catch (OutOfMemoryError error) {
                    Log.e(TAG, "doInBackground: BitmapFactory.decodeFileDescriptor: ", error);
                    options.inSampleSize *= 2;
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: ImageDecoder.createSource: ", e);
                    return new BitmapWorkerResult(new IllegalArgumentException("Bitmap could not be decoded from the Uri: [" + this.mInputUri + "]", e));
                } catch (Throwable th) {
                    BitmapLoadUtils.close(stream);
                    throw th;
                }
            }
            if (decodeSampledBitmap == null) {
                return new BitmapWorkerResult(new IllegalArgumentException("Bitmap could not be decoded from the Uri: [" + this.mInputUri + "]"));
            }
            int exifOrientation = BitmapLoadUtils.getExifOrientation(this.mContext, this.mInputUri);
            int exifDegrees = BitmapLoadUtils.exifToDegrees(exifOrientation);
            int exifTranslation = BitmapLoadUtils.exifToTranslation(exifOrientation);
            ExifInfo exifInfo = new ExifInfo(exifOrientation, exifDegrees, exifTranslation);
            Matrix matrix = new Matrix();
            if (exifDegrees != 0) {
                matrix.preRotate((float) exifDegrees);
            }
            if (exifTranslation != 1) {
                matrix.postScale((float) exifTranslation, 1.0f);
            }
            if (!matrix.isIdentity()) {
                return new BitmapWorkerResult(BitmapLoadUtils.transformBitmap(decodeSampledBitmap, matrix), exifInfo);
            }
            return new BitmapWorkerResult(decodeSampledBitmap, exifInfo);
        } catch (IOException | NullPointerException e2) {
            return new BitmapWorkerResult(e2);
        }
    }

    private void processInputUri() {
        String inputUriScheme = this.mInputUri.getScheme();
        Log.d(TAG, "Uri scheme: " + inputUriScheme);
        if (l.DEFAULT_SCHEME_NAME.equals(inputUriScheme) || "https".equals(inputUriScheme)) {
            try {
                downloadFile(this.mInputUri, this.mOutputUri);
            } catch (IOException | NullPointerException e) {
                Log.e(TAG, "Downloading failed", e);
                throw e;
            }
        } else if (FirebaseAnalytics.Param.CONTENT.equals(inputUriScheme)) {
            String path = getFilePath();
            if (TextUtils.isEmpty(path) || !new File(path).exists()) {
                try {
                    copyFile(this.mInputUri, this.mOutputUri);
                } catch (IOException | NullPointerException e2) {
                    Log.e(TAG, "Copying failed", e2);
                    throw e2;
                }
            } else {
                this.mInputUri = SdkVersionUtils.checkedAndroid_Q() ? this.mInputUri : Uri.fromFile(new File(path));
            }
        } else if (!"file".equals(inputUriScheme)) {
            Log.e(TAG, "Invalid Uri scheme " + inputUriScheme);
            throw new IllegalArgumentException("Invalid Uri scheme" + inputUriScheme);
        }
    }

    private String getFilePath() {
        if (ContextCompat.checkSelfPermission(this.mContext, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
            return FileUtils.getPath(this.mContext, this.mInputUri);
        }
        return "";
    }

    private void copyFile(@NonNull Uri inputUri, @Nullable Uri outputUri) {
        Log.d(TAG, "copyFile");
        if (outputUri != null) {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = this.mContext.getContentResolver().openInputStream(inputUri);
                outputStream = new FileOutputStream(new File(outputUri.getPath()));
                if (inputStream != null) {
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int read = inputStream.read(buffer);
                        int length = read;
                        if (read > 0) {
                            outputStream.write(buffer, 0, length);
                        } else {
                            return;
                        }
                    }
                } else {
                    throw new NullPointerException("InputStream for given input Uri is null");
                }
            } finally {
                BitmapLoadUtils.close(outputStream);
                BitmapLoadUtils.close(inputStream);
                this.mInputUri = this.mOutputUri;
            }
        } else {
            throw new NullPointerException("Output Uri is null - cannot copy image");
        }
    }

    private void downloadFile(@NonNull Uri inputUri, @Nullable Uri outputUri) {
        Log.d(TAG, "downloadFile");
        if (outputUri != null) {
            OutputStream outputStream = null;
            BufferedInputStream bin = null;
            BufferedOutputStream bout = null;
            try {
                byte[] buffer = new byte[1024];
                bin = new BufferedInputStream(new URL(inputUri.toString()).openStream());
                outputStream = this.mContext.getContentResolver().openOutputStream(outputUri);
                if (outputStream != null) {
                    bout = new BufferedOutputStream(outputStream);
                    while (true) {
                        int read = bin.read(buffer);
                        int read2 = read;
                        if (read <= -1) {
                            break;
                        }
                        bout.write(buffer, 0, read2);
                    }
                    bout.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable th) {
                this.mInputUri = this.mOutputUri;
                BitmapLoadUtils.close((Closeable) null);
                BitmapLoadUtils.close((Closeable) null);
                BitmapLoadUtils.close((Closeable) null);
                throw th;
            }
            this.mInputUri = this.mOutputUri;
            BitmapLoadUtils.close(bout);
            BitmapLoadUtils.close(bin);
            BitmapLoadUtils.close(outputStream);
            return;
        }
        throw new NullPointerException("Output Uri is null - cannot download image");
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(@NonNull BitmapWorkerResult result) {
        Exception exc = result.mBitmapWorkerException;
        if (exc == null) {
            String inputUriString = this.mInputUri.toString();
            BitmapLoadCallback bitmapLoadCallback = this.mBitmapLoadCallback;
            Bitmap bitmap = result.mBitmapResult;
            ExifInfo exifInfo = result.mExifInfo;
            String path = PictureMimeType.isContent(inputUriString) ? inputUriString : this.mInputUri.getPath();
            Uri uri = this.mOutputUri;
            bitmapLoadCallback.onBitmapLoaded(bitmap, exifInfo, path, uri == null ? null : uri.getPath());
            return;
        }
        this.mBitmapLoadCallback.onFailure(exc);
    }

    private boolean checkSize(Bitmap bitmap, BitmapFactory.Options options) {
        if ((bitmap != null ? bitmap.getByteCount() : 0) <= MAX_BITMAP_SIZE) {
            return false;
        }
        options.inSampleSize *= 2;
        return true;
    }
}

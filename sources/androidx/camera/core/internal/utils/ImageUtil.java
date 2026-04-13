package androidx.camera.core.internal.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.Rational;
import android.util.Size;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Logger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public final class ImageUtil {
    private static final String TAG = "ImageUtil";

    private ImageUtil() {
    }

    @NonNull
    public static float[] sizeToVertexes(@NonNull Size size) {
        return new float[]{0.0f, 0.0f, (float) size.getWidth(), 0.0f, (float) size.getWidth(), (float) size.getHeight(), 0.0f, (float) size.getHeight()};
    }

    public static float min(float value1, float value2, float value3, float value4) {
        return Math.min(Math.min(value1, value2), Math.min(value3, value4));
    }

    @NonNull
    public static Rational getRotatedAspectRatio(@IntRange(from = 0, to = 359) int rotationDegrees, @NonNull Rational aspectRatio) {
        if (rotationDegrees == 90 || rotationDegrees == 270) {
            return inverseRational(aspectRatio);
        }
        return new Rational(aspectRatio.getNumerator(), aspectRatio.getDenominator());
    }

    @Nullable
    public static byte[] imageToJpegByteArray(@NonNull ImageProxy image) {
        if (image.getFormat() == 256) {
            return jpegImageToJpegByteArray(image);
        }
        if (image.getFormat() == 35) {
            return yuvImageToJpegByteArray(image);
        }
        Logger.w(TAG, "Unrecognized image format: " + image.getFormat());
        return null;
    }

    @NonNull
    public static byte[] yuv_420_888toNv21(@NonNull ImageProxy image) {
        ImageProxy.PlaneProxy yPlane = image.getPlanes()[0];
        ImageProxy.PlaneProxy uPlane = image.getPlanes()[1];
        ImageProxy.PlaneProxy vPlane = image.getPlanes()[2];
        ByteBuffer yBuffer = yPlane.getBuffer();
        ByteBuffer uBuffer = uPlane.getBuffer();
        ByteBuffer vBuffer = vPlane.getBuffer();
        yBuffer.rewind();
        uBuffer.rewind();
        vBuffer.rewind();
        int ySize = yBuffer.remaining();
        int position = 0;
        byte[] nv21 = new byte[(((image.getWidth() * image.getHeight()) / 2) + ySize)];
        for (int row = 0; row < image.getHeight(); row++) {
            yBuffer.get(nv21, position, image.getWidth());
            position += image.getWidth();
            yBuffer.position(Math.min(ySize, (yBuffer.position() - image.getWidth()) + yPlane.getRowStride()));
        }
        int chromaHeight = image.getHeight() / 2;
        int chromaWidth = image.getWidth() / 2;
        int vRowStride = vPlane.getRowStride();
        int uRowStride = uPlane.getRowStride();
        int vPixelStride = vPlane.getPixelStride();
        int uPixelStride = uPlane.getPixelStride();
        byte[] vLineBuffer = new byte[vRowStride];
        ImageProxy.PlaneProxy planeProxy = yPlane;
        byte[] uLineBuffer = new byte[uRowStride];
        ImageProxy.PlaneProxy planeProxy2 = uPlane;
        int row2 = 0;
        while (row2 < chromaHeight) {
            ImageProxy.PlaneProxy vPlane2 = vPlane;
            int vRowStride2 = vRowStride;
            vBuffer.get(vLineBuffer, 0, Math.min(vRowStride, vBuffer.remaining()));
            uBuffer.get(uLineBuffer, 0, Math.min(uRowStride, uBuffer.remaining()));
            int vLineBufferPosition = 0;
            int uLineBufferPosition = 0;
            for (int col = 0; col < chromaWidth; col++) {
                int position2 = position + 1;
                nv21[position] = vLineBuffer[vLineBufferPosition];
                position = position2 + 1;
                nv21[position2] = uLineBuffer[uLineBufferPosition];
                vLineBufferPosition += vPixelStride;
                uLineBufferPosition += uPixelStride;
            }
            row2++;
            vPlane = vPlane2;
            vRowStride = vRowStride2;
        }
        return nv21;
    }

    @NonNull
    public static byte[] cropByteArray(@NonNull byte[] data, @Nullable Rect cropRect) {
        if (cropRect == null) {
            return data;
        }
        try {
            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(data, 0, data.length, false);
            Bitmap bitmap = decoder.decodeRegion(cropRect, new BitmapFactory.Options());
            decoder.recycle();
            if (bitmap != null) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                    bitmap.recycle();
                    return out.toByteArray();
                }
                throw new CodecFailedException("Encode bitmap failed.", CodecFailedException.FailureType.ENCODE_FAILED);
            }
            throw new CodecFailedException("Decode byte array failed.", CodecFailedException.FailureType.DECODE_FAILED);
        } catch (IllegalArgumentException e) {
            throw new CodecFailedException("Decode byte array failed with illegal argument." + e, CodecFailedException.FailureType.DECODE_FAILED);
        } catch (IOException e2) {
            throw new CodecFailedException("Decode byte array failed.", CodecFailedException.FailureType.DECODE_FAILED);
        }
    }

    public static boolean isAspectRatioValid(@Nullable Rational aspectRatio) {
        return aspectRatio != null && aspectRatio.floatValue() > 0.0f && !aspectRatio.isNaN();
    }

    public static boolean isAspectRatioValid(@NonNull Size sourceSize, @Nullable Rational aspectRatio) {
        return aspectRatio != null && aspectRatio.floatValue() > 0.0f && isCropAspectRatioHasEffect(sourceSize, aspectRatio) && !aspectRatio.isNaN();
    }

    @Nullable
    public static Rect computeCropRectFromAspectRatio(@NonNull Size sourceSize, @NonNull Rational aspectRatio) {
        if (!isAspectRatioValid(aspectRatio)) {
            Logger.w(TAG, "Invalid view ratio.");
            return null;
        }
        int sourceWidth = sourceSize.getWidth();
        int sourceHeight = sourceSize.getHeight();
        float srcRatio = ((float) sourceWidth) / ((float) sourceHeight);
        int cropLeft = 0;
        int cropTop = 0;
        int outputWidth = sourceWidth;
        int outputHeight = sourceHeight;
        int numerator = aspectRatio.getNumerator();
        int denominator = aspectRatio.getDenominator();
        if (aspectRatio.floatValue() > srcRatio) {
            outputHeight = Math.round((((float) sourceWidth) / ((float) numerator)) * ((float) denominator));
            cropTop = (sourceHeight - outputHeight) / 2;
        } else {
            outputWidth = Math.round((((float) sourceHeight) / ((float) denominator)) * ((float) numerator));
            cropLeft = (sourceWidth - outputWidth) / 2;
        }
        return new Rect(cropLeft, cropTop, cropLeft + outputWidth, cropTop + outputHeight);
    }

    private static byte[] nv21ToJpeg(byte[] nv21, int width, int height, @Nullable Rect cropRect) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if (new YuvImage(nv21, 17, width, height, (int[]) null).compressToJpeg(cropRect == null ? new Rect(0, 0, width, height) : cropRect, 100, out)) {
            return out.toByteArray();
        }
        throw new CodecFailedException("YuvImage failed to encode jpeg.", CodecFailedException.FailureType.ENCODE_FAILED);
    }

    private static boolean isCropAspectRatioHasEffect(Size sourceSize, Rational aspectRatio) {
        int sourceWidth = sourceSize.getWidth();
        int sourceHeight = sourceSize.getHeight();
        int numerator = aspectRatio.getNumerator();
        int denominator = aspectRatio.getDenominator();
        return (sourceHeight == Math.round((((float) sourceWidth) / ((float) numerator)) * ((float) denominator)) && sourceWidth == Math.round((((float) sourceHeight) / ((float) denominator)) * ((float) numerator))) ? false : true;
    }

    private static Rational inverseRational(Rational rational) {
        if (rational == null) {
            return rational;
        }
        return new Rational(rational.getDenominator(), rational.getNumerator());
    }

    private static boolean shouldCropImage(ImageProxy image) {
        return !new Size(image.getCropRect().width(), image.getCropRect().height()).equals(new Size(image.getWidth(), image.getHeight()));
    }

    private static byte[] jpegImageToJpegByteArray(ImageProxy image) {
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] data = new byte[buffer.capacity()];
        buffer.rewind();
        buffer.get(data);
        if (shouldCropImage(image)) {
            return cropByteArray(data, image.getCropRect());
        }
        return data;
    }

    private static byte[] yuvImageToJpegByteArray(ImageProxy image) {
        return nv21ToJpeg(yuv_420_888toNv21(image), image.getWidth(), image.getHeight(), shouldCropImage(image) ? image.getCropRect() : null);
    }

    public static final class CodecFailedException extends Exception {
        private FailureType mFailureType;

        public enum FailureType {
            ENCODE_FAILED,
            DECODE_FAILED,
            UNKNOWN
        }

        CodecFailedException(String message) {
            super(message);
            this.mFailureType = FailureType.UNKNOWN;
        }

        CodecFailedException(String message, FailureType failureType) {
            super(message);
            this.mFailureType = failureType;
        }

        @NonNull
        public FailureType getFailureType() {
            return this.mFailureType;
        }
    }
}

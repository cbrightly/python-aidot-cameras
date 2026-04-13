package androidx.camera.core;

import android.util.Size;
import androidx.annotation.NonNull;
import androidx.camera.core.ImageProxy;
import java.nio.ByteBuffer;

public final class ImageProxyDownsampler {

    public enum DownsamplingMethod {
        NEAREST_NEIGHBOR,
        AVERAGING
    }

    private ImageProxyDownsampler() {
    }

    static ForwardingImageProxy downsample(ImageProxy image, int downsampledWidth, int downsampledHeight, DownsamplingMethod downsamplingMethod) {
        byte[] output;
        ImageProxy imageProxy = image;
        int i = downsampledWidth;
        int i2 = downsampledHeight;
        if (image.getFormat() != 35) {
            throw new UnsupportedOperationException("Only YUV_420_888 format is currently supported.");
        } else if (image.getWidth() < i || image.getHeight() < i2) {
            throw new IllegalArgumentException("Downsampled dimension " + new Size(i, i2) + " is not <= original dimension " + new Size(image.getWidth(), image.getHeight()) + ".");
        } else if (image.getWidth() == i && image.getHeight() == i2) {
            return new ForwardingImageProxyImpl(imageProxy, image.getPlanes(), i, i2);
        } else {
            int[] inputWidths = {image.getWidth(), image.getWidth() / 2, image.getWidth() / 2};
            int[] inputHeights = {image.getHeight(), image.getHeight() / 2, image.getHeight() / 2};
            int[] outputWidths = {i, i / 2, i / 2};
            int[] outputHeights = {i2, i2 / 2, i2 / 2};
            ImageProxy.PlaneProxy[] outputPlanes = new ImageProxy.PlaneProxy[3];
            for (int i3 = 0; i3 < 3; i3++) {
                ImageProxy.PlaneProxy inputPlane = image.getPlanes()[i3];
                ByteBuffer inputBuffer = inputPlane.getBuffer();
                byte[] output2 = new byte[(outputWidths[i3] * outputHeights[i3])];
                switch (AnonymousClass2.$SwitchMap$androidx$camera$core$ImageProxyDownsampler$DownsamplingMethod[downsamplingMethod.ordinal()]) {
                    case 1:
                        output = output2;
                        resizeNearestNeighbor(inputBuffer, inputWidths[i3], inputPlane.getPixelStride(), inputPlane.getRowStride(), inputHeights[i3], output, outputWidths[i3], outputHeights[i3]);
                        break;
                    case 2:
                        int i4 = inputWidths[i3];
                        int pixelStride = inputPlane.getPixelStride();
                        int rowStride = inputPlane.getRowStride();
                        output = output2;
                        resizeAveraging(inputBuffer, i4, pixelStride, rowStride, inputHeights[i3], output, outputWidths[i3], outputHeights[i3]);
                        break;
                    default:
                        output = output2;
                        break;
                }
                outputPlanes[i3] = createPlaneProxy(outputWidths[i3], 1, output);
            }
            return new ForwardingImageProxyImpl(imageProxy, outputPlanes, i, i2);
        }
    }

    /* renamed from: androidx.camera.core.ImageProxyDownsampler$2  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$core$ImageProxyDownsampler$DownsamplingMethod;

        static {
            int[] iArr = new int[DownsamplingMethod.values().length];
            $SwitchMap$androidx$camera$core$ImageProxyDownsampler$DownsamplingMethod = iArr;
            try {
                iArr[DownsamplingMethod.NEAREST_NEIGHBOR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$camera$core$ImageProxyDownsampler$DownsamplingMethod[DownsamplingMethod.AVERAGING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private static void resizeNearestNeighbor(ByteBuffer input, int inputWidth, int inputPixelStride, int inputRowStride, int inputHeight, byte[] output, int outputWidth, int outputHeight) {
        ByteBuffer byteBuffer = input;
        int i = inputRowStride;
        int i2 = inputHeight;
        int i3 = outputWidth;
        int i4 = outputHeight;
        float scaleX = ((float) inputWidth) / ((float) i3);
        float scaleY = ((float) i2) / ((float) i4);
        int outputRowStride = outputWidth;
        byte[] row = new byte[i];
        int[] sourceIndices = new int[i3];
        for (int ix = 0; ix < i3; ix++) {
            sourceIndices[ix] = ((int) (((float) ix) * scaleX)) * inputPixelStride;
        }
        synchronized (input) {
            input.rewind();
            int iy = 0;
            while (iy < i4) {
                int rowOffsetTarget = iy * outputRowStride;
                byteBuffer.position(Math.min((int) (((float) iy) * scaleY), i2 - 1) * i);
                byteBuffer.get(row, 0, Math.min(i, input.remaining()));
                for (int ix2 = 0; ix2 < i3; ix2++) {
                    output[rowOffsetTarget + ix2] = row[sourceIndices[ix2]];
                }
                iy++;
                i = inputRowStride;
                i2 = inputHeight;
            }
        }
    }

    private static void resizeAveraging(ByteBuffer input, int inputWidth, int inputPixelStride, int inputRowStride, int inputHeight, byte[] output, int outputWidth, int outputHeight) {
        ByteBuffer byteBuffer = input;
        int i = inputRowStride;
        int i2 = inputHeight;
        int i3 = outputWidth;
        int i4 = outputHeight;
        float scaleX = ((float) inputWidth) / ((float) i3);
        float scaleY = ((float) i2) / ((float) i4);
        int outputRowStride = outputWidth;
        byte[] row0 = new byte[i];
        byte[] row1 = new byte[i];
        int[] sourceIndices = new int[i3];
        for (int ix = 0; ix < i3; ix++) {
            sourceIndices[ix] = ((int) (((float) ix) * scaleX)) * inputPixelStride;
        }
        synchronized (input) {
            try {
                input.rewind();
                int iy = 0;
                while (iy < i4) {
                    int floorSourceY = (int) (((float) iy) * scaleY);
                    int rowOffsetSource1 = Math.min(floorSourceY + 1, i2 - 1) * i;
                    int rowOffsetTarget = iy * outputRowStride;
                    byteBuffer.position(Math.min(floorSourceY, i2 - 1) * i);
                    float scaleX2 = scaleX;
                    byteBuffer.get(row0, 0, Math.min(i, input.remaining()));
                    byteBuffer.position(rowOffsetSource1);
                    byteBuffer.get(row1, 0, Math.min(i, input.remaining()));
                    int ix2 = 0;
                    while (ix2 < i3) {
                        int sampleB = row0[sourceIndices[ix2] + inputPixelStride] & 255;
                        int i5 = row1[sourceIndices[ix2]] & 255;
                        int i6 = sampleB;
                        int sampleB2 = ((((row0[sourceIndices[ix2]] & 255) + sampleB) + i5) + (row1[sourceIndices[ix2] + inputPixelStride] & 255)) / 4;
                        int mixed = i5;
                        output[rowOffsetTarget + ix2] = (byte) (sampleB2 & 255);
                        ix2++;
                        int i7 = inputRowStride;
                        i3 = outputWidth;
                        rowOffsetSource1 = rowOffsetSource1;
                    }
                    iy++;
                    int i8 = inputWidth;
                    i = inputRowStride;
                    i2 = inputHeight;
                    i3 = outputWidth;
                    i4 = outputHeight;
                    scaleX = scaleX2;
                }
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        }
    }

    private static ImageProxy.PlaneProxy createPlaneProxy(int rowStride, int pixelStride, byte[] data) {
        return new ImageProxy.PlaneProxy(data, rowStride, pixelStride) {
            final ByteBuffer mBuffer;
            final /* synthetic */ byte[] val$data;
            final /* synthetic */ int val$pixelStride;
            final /* synthetic */ int val$rowStride;

            {
                this.val$data = r1;
                this.val$rowStride = r2;
                this.val$pixelStride = r3;
                this.mBuffer = ByteBuffer.wrap(r1);
            }

            public int getRowStride() {
                return this.val$rowStride;
            }

            public int getPixelStride() {
                return this.val$pixelStride;
            }

            @NonNull
            public ByteBuffer getBuffer() {
                return this.mBuffer;
            }
        };
    }

    public static final class ForwardingImageProxyImpl extends ForwardingImageProxy {
        private final int mDownsampledHeight;
        private final ImageProxy.PlaneProxy[] mDownsampledPlanes;
        private final int mDownsampledWidth;

        ForwardingImageProxyImpl(ImageProxy originalImage, ImageProxy.PlaneProxy[] downsampledPlanes, int downsampledWidth, int downsampledHeight) {
            super(originalImage);
            this.mDownsampledPlanes = downsampledPlanes;
            this.mDownsampledWidth = downsampledWidth;
            this.mDownsampledHeight = downsampledHeight;
        }

        public synchronized int getWidth() {
            return this.mDownsampledWidth;
        }

        public synchronized int getHeight() {
            return this.mDownsampledHeight;
        }

        @NonNull
        public synchronized ImageProxy.PlaneProxy[] getPlanes() {
            return this.mDownsampledPlanes;
        }
    }
}

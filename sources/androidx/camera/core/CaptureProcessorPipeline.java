package androidx.camera.core;

import android.media.ImageReader;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.camera.core.impl.CaptureProcessor;
import androidx.camera.core.impl.ImageProxyBundle;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class CaptureProcessorPipeline implements CaptureProcessor {
    private final Executor mExecutor;
    private ImageReaderProxy mIntermediateImageReader = null;
    private final int mMaxImages;
    private final CaptureProcessor mPostCaptureProcessor;
    private final CaptureProcessor mPreCaptureProcessor;
    private ImageInfo mSourceImageInfo = null;

    CaptureProcessorPipeline(@NonNull CaptureProcessor preCaptureProcessor, int maxImages, @NonNull CaptureProcessor postCaptureProcessor, @NonNull Executor executor) {
        this.mPreCaptureProcessor = preCaptureProcessor;
        this.mPostCaptureProcessor = postCaptureProcessor;
        this.mExecutor = executor;
        this.mMaxImages = maxImages;
    }

    public void onOutputSurface(@NonNull Surface surface, int imageFormat) {
        this.mPostCaptureProcessor.onOutputSurface(surface, imageFormat);
    }

    public void process(@NonNull ImageProxyBundle bundle) {
        ListenableFuture<ImageProxy> imageProxyListenableFuture = bundle.getImageProxy(bundle.getCaptureIds().get(0).intValue());
        Preconditions.checkArgument(imageProxyListenableFuture.isDone());
        try {
            this.mSourceImageInfo = imageProxyListenableFuture.get().getImageInfo();
            this.mPreCaptureProcessor.process(bundle);
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalArgumentException("Can not successfully extract ImageProxy from the ImageProxyBundle.");
        }
    }

    public void onResolutionUpdate(@NonNull Size size) {
        AndroidImageReaderProxy androidImageReaderProxy = new AndroidImageReaderProxy(ImageReader.newInstance(size.getWidth(), size.getHeight(), 35, this.mMaxImages));
        this.mIntermediateImageReader = androidImageReaderProxy;
        this.mPreCaptureProcessor.onOutputSurface(androidImageReaderProxy.getSurface(), 35);
        this.mPreCaptureProcessor.onResolutionUpdate(size);
        this.mPostCaptureProcessor.onResolutionUpdate(size);
        this.mIntermediateImageReader.setOnImageAvailableListener(new ImageReaderProxy.OnImageAvailableListener() {
            public void onImageAvailable(@NonNull ImageReaderProxy imageReader) {
                CaptureProcessorPipeline.this.postProcess(imageReader.acquireNextImage());
            }
        }, this.mExecutor);
    }

    /* access modifiers changed from: package-private */
    public void postProcess(ImageProxy imageProxy) {
        Size resolution = new Size(imageProxy.getWidth(), imageProxy.getHeight());
        Preconditions.checkNotNull(this.mSourceImageInfo);
        String tagBundleKey = this.mSourceImageInfo.getTagBundle().listKeys().iterator().next();
        int captureId = this.mSourceImageInfo.getTagBundle().getTag(tagBundleKey).intValue();
        SettableImageProxy settableImageProxy = new SettableImageProxy(imageProxy, resolution, this.mSourceImageInfo);
        this.mSourceImageInfo = null;
        SettableImageProxyBundle settableImageProxyBundle = new SettableImageProxyBundle(Collections.singletonList(Integer.valueOf(captureId)), tagBundleKey);
        settableImageProxyBundle.addImageProxy(settableImageProxy);
        this.mPostCaptureProcessor.process(settableImageProxyBundle);
    }

    /* access modifiers changed from: package-private */
    public void close() {
        ImageReaderProxy imageReaderProxy = this.mIntermediateImageReader;
        if (imageReaderProxy != null) {
            imageReaderProxy.clearOnImageAvailableListener();
            this.mIntermediateImageReader.close();
        }
    }
}

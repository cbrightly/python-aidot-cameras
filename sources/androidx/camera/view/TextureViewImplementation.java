package androidx.camera.view;

import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Logger;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.view.PreviewViewImplementation;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.content.ContextCompat;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

public final class TextureViewImplementation extends PreviewViewImplementation {
    private static final String TAG = "TextureViewImpl";
    SurfaceTexture mDetachedSurfaceTexture;
    boolean mIsSurfaceTextureDetachedFromView = false;
    AtomicReference<CallbackToFutureAdapter.Completer<Void>> mNextFrameCompleter = new AtomicReference<>();
    @Nullable
    PreviewViewImplementation.OnSurfaceNotInUseListener mOnSurfaceNotInUseListener;
    ListenableFuture<SurfaceRequest.Result> mSurfaceReleaseFuture;
    SurfaceRequest mSurfaceRequest;
    SurfaceTexture mSurfaceTexture;
    TextureView mTextureView;

    TextureViewImplementation(@NonNull FrameLayout parent, @NonNull PreviewTransformation previewTransform) {
        super(parent, previewTransform);
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public View getPreview() {
        return this.mTextureView;
    }

    /* access modifiers changed from: package-private */
    public void onAttachedToWindow() {
        reattachSurfaceTexture();
    }

    /* access modifiers changed from: package-private */
    public void onDetachedFromWindow() {
        this.mIsSurfaceTextureDetachedFromView = true;
    }

    /* access modifiers changed from: package-private */
    public void onSurfaceRequested(@NonNull SurfaceRequest surfaceRequest, @Nullable PreviewViewImplementation.OnSurfaceNotInUseListener onSurfaceNotInUseListener) {
        this.mResolution = surfaceRequest.getResolution();
        this.mOnSurfaceNotInUseListener = onSurfaceNotInUseListener;
        initializePreview();
        SurfaceRequest surfaceRequest2 = this.mSurfaceRequest;
        if (surfaceRequest2 != null) {
            surfaceRequest2.willNotProvideSurface();
        }
        this.mSurfaceRequest = surfaceRequest;
        surfaceRequest.addRequestCancellationListener(ContextCompat.getMainExecutor(this.mTextureView.getContext()), new p(this, surfaceRequest));
        tryToProvidePreviewSurface();
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$onSurfaceRequested$0 */
    public /* synthetic */ void a(SurfaceRequest surfaceRequest) {
        SurfaceRequest surfaceRequest2 = this.mSurfaceRequest;
        if (surfaceRequest2 != null && surfaceRequest2 == surfaceRequest) {
            this.mSurfaceRequest = null;
            this.mSurfaceReleaseFuture = null;
        }
        notifySurfaceNotInUse();
    }

    private void notifySurfaceNotInUse() {
        PreviewViewImplementation.OnSurfaceNotInUseListener onSurfaceNotInUseListener = this.mOnSurfaceNotInUseListener;
        if (onSurfaceNotInUseListener != null) {
            onSurfaceNotInUseListener.onSurfaceNotInUse();
            this.mOnSurfaceNotInUseListener = null;
        }
    }

    public void initializePreview() {
        Preconditions.checkNotNull(this.mParent);
        Preconditions.checkNotNull(this.mResolution);
        TextureView textureView = new TextureView(this.mParent.getContext());
        this.mTextureView = textureView;
        textureView.setLayoutParams(new FrameLayout.LayoutParams(this.mResolution.getWidth(), this.mResolution.getHeight()));
        this.mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surfaceTexture, int width, int height) {
                Logger.d(TextureViewImplementation.TAG, "SurfaceTexture available. Size: " + width + "x" + height);
                TextureViewImplementation textureViewImplementation = TextureViewImplementation.this;
                textureViewImplementation.mSurfaceTexture = surfaceTexture;
                if (textureViewImplementation.mSurfaceReleaseFuture != null) {
                    Preconditions.checkNotNull(textureViewImplementation.mSurfaceRequest);
                    Logger.d(TextureViewImplementation.TAG, "Surface invalidated " + TextureViewImplementation.this.mSurfaceRequest);
                    TextureViewImplementation.this.mSurfaceRequest.getDeferrableSurface().close();
                    return;
                }
                textureViewImplementation.tryToProvidePreviewSurface();
            }

            public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int width, int height) {
                Logger.d(TextureViewImplementation.TAG, "SurfaceTexture size changed: " + width + "x" + height);
            }

            public boolean onSurfaceTextureDestroyed(@NonNull final SurfaceTexture surfaceTexture) {
                TextureViewImplementation textureViewImplementation = TextureViewImplementation.this;
                textureViewImplementation.mSurfaceTexture = null;
                ListenableFuture<SurfaceRequest.Result> listenableFuture = textureViewImplementation.mSurfaceReleaseFuture;
                if (listenableFuture != null) {
                    Futures.addCallback(listenableFuture, new FutureCallback<SurfaceRequest.Result>() {
                        public void onSuccess(SurfaceRequest.Result result) {
                            Preconditions.checkState(result.getResultCode() != 3, "Unexpected result from SurfaceRequest. Surface was provided twice.");
                            Logger.d(TextureViewImplementation.TAG, "SurfaceTexture about to manually be destroyed");
                            surfaceTexture.release();
                            TextureViewImplementation textureViewImplementation = TextureViewImplementation.this;
                            if (textureViewImplementation.mDetachedSurfaceTexture != null) {
                                textureViewImplementation.mDetachedSurfaceTexture = null;
                            }
                        }

                        public void onFailure(Throwable t) {
                            throw new IllegalStateException("SurfaceReleaseFuture did not complete nicely.", t);
                        }
                    }, ContextCompat.getMainExecutor(TextureViewImplementation.this.mTextureView.getContext()));
                    TextureViewImplementation.this.mDetachedSurfaceTexture = surfaceTexture;
                    return false;
                }
                Logger.d(TextureViewImplementation.TAG, "SurfaceTexture about to be destroyed");
                return true;
            }

            public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) {
                CallbackToFutureAdapter.Completer<Void> completer = TextureViewImplementation.this.mNextFrameCompleter.getAndSet((Object) null);
                if (completer != null) {
                    completer.set(null);
                }
            }
        });
        this.mParent.removeAllViews();
        this.mParent.addView(this.mTextureView);
    }

    /* access modifiers changed from: package-private */
    public void tryToProvidePreviewSurface() {
        SurfaceTexture surfaceTexture;
        Size size = this.mResolution;
        if (size != null && (surfaceTexture = this.mSurfaceTexture) != null && this.mSurfaceRequest != null) {
            surfaceTexture.setDefaultBufferSize(size.getWidth(), this.mResolution.getHeight());
            Surface surface = new Surface(this.mSurfaceTexture);
            SurfaceRequest surfaceRequest = this.mSurfaceRequest;
            ListenableFuture<SurfaceRequest.Result> surfaceReleaseFuture = CallbackToFutureAdapter.getFuture(new s(this, surface));
            this.mSurfaceReleaseFuture = surfaceReleaseFuture;
            surfaceReleaseFuture.addListener(new q(this, surface, surfaceReleaseFuture, surfaceRequest), ContextCompat.getMainExecutor(this.mTextureView.getContext()));
            onSurfaceProvided();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$tryToProvidePreviewSurface$1 */
    public /* synthetic */ Object b(Surface surface, CallbackToFutureAdapter.Completer completer) {
        Logger.d(TAG, "Surface set on Preview.");
        SurfaceRequest surfaceRequest = this.mSurfaceRequest;
        Executor directExecutor = CameraXExecutors.directExecutor();
        Objects.requireNonNull(completer);
        surfaceRequest.provideSurface(surface, directExecutor, new u(completer));
        return "provideSurface[request=" + this.mSurfaceRequest + " surface=" + surface + "]";
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$tryToProvidePreviewSurface$2 */
    public /* synthetic */ void c(Surface surface, ListenableFuture surfaceReleaseFuture, SurfaceRequest surfaceRequest) {
        Logger.d(TAG, "Safe to release surface.");
        notifySurfaceNotInUse();
        surface.release();
        if (this.mSurfaceReleaseFuture == surfaceReleaseFuture) {
            this.mSurfaceReleaseFuture = null;
        }
        if (this.mSurfaceRequest == surfaceRequest) {
            this.mSurfaceRequest = null;
        }
    }

    private void reattachSurfaceTexture() {
        SurfaceTexture surfaceTexture;
        if (this.mIsSurfaceTextureDetachedFromView && this.mDetachedSurfaceTexture != null && this.mTextureView.getSurfaceTexture() != (surfaceTexture = this.mDetachedSurfaceTexture)) {
            this.mTextureView.setSurfaceTexture(surfaceTexture);
            this.mDetachedSurfaceTexture = null;
            this.mIsSurfaceTextureDetachedFromView = false;
        }
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public ListenableFuture<Void> waitForNextFrame() {
        return CallbackToFutureAdapter.getFuture(new o(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$waitForNextFrame$3 */
    public /* synthetic */ Object d(CallbackToFutureAdapter.Completer completer) {
        this.mNextFrameCompleter.set(completer);
        return "textureViewImpl_waitForNextFrame";
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Bitmap getPreviewBitmap() {
        TextureView textureView = this.mTextureView;
        if (textureView == null || !textureView.isAvailable()) {
            return null;
        }
        return this.mTextureView.getBitmap();
    }
}

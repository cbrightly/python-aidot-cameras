package androidx.camera.view;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.util.Size;
import android.view.PixelCopy;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.camera.core.Logger;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.view.PreviewViewImplementation;
import androidx.core.content.ContextCompat;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;

public final class SurfaceViewImplementation extends PreviewViewImplementation {
    private static final String TAG = "SurfaceViewImpl";
    @Nullable
    private PreviewViewImplementation.OnSurfaceNotInUseListener mOnSurfaceNotInUseListener;
    final SurfaceRequestCallback mSurfaceRequestCallback = new SurfaceRequestCallback();
    SurfaceView mSurfaceView;

    SurfaceViewImplementation(@NonNull FrameLayout parent, @NonNull PreviewTransformation previewTransform) {
        super(parent, previewTransform);
    }

    /* access modifiers changed from: package-private */
    public void onSurfaceRequested(@NonNull SurfaceRequest surfaceRequest, @Nullable PreviewViewImplementation.OnSurfaceNotInUseListener onSurfaceNotInUseListener) {
        this.mResolution = surfaceRequest.getResolution();
        this.mOnSurfaceNotInUseListener = onSurfaceNotInUseListener;
        initializePreview();
        surfaceRequest.addRequestCancellationListener(ContextCompat.getMainExecutor(this.mSurfaceView.getContext()), new t(this));
        this.mSurfaceView.post(new l(this, surfaceRequest));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$onSurfaceRequested$0 */
    public /* synthetic */ void a(SurfaceRequest surfaceRequest) {
        this.mSurfaceRequestCallback.setSurfaceRequest(surfaceRequest);
    }

    /* access modifiers changed from: package-private */
    public void initializePreview() {
        Preconditions.checkNotNull(this.mParent);
        Preconditions.checkNotNull(this.mResolution);
        SurfaceView surfaceView = new SurfaceView(this.mParent.getContext());
        this.mSurfaceView = surfaceView;
        surfaceView.setLayoutParams(new FrameLayout.LayoutParams(this.mResolution.getWidth(), this.mResolution.getHeight()));
        this.mParent.removeAllViews();
        this.mParent.addView(this.mSurfaceView);
        this.mSurfaceView.getHolder().addCallback(this.mSurfaceRequestCallback);
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public View getPreview() {
        return this.mSurfaceView;
    }

    /* access modifiers changed from: package-private */
    public void onAttachedToWindow() {
    }

    /* access modifiers changed from: package-private */
    public void onDetachedFromWindow() {
    }

    /* access modifiers changed from: package-private */
    public void notifySurfaceNotInUse() {
        PreviewViewImplementation.OnSurfaceNotInUseListener onSurfaceNotInUseListener = this.mOnSurfaceNotInUseListener;
        if (onSurfaceNotInUseListener != null) {
            onSurfaceNotInUseListener.onSurfaceNotInUse();
            this.mOnSurfaceNotInUseListener = null;
        }
    }

    /* access modifiers changed from: package-private */
    @TargetApi(24)
    @Nullable
    public Bitmap getPreviewBitmap() {
        SurfaceView surfaceView = this.mSurfaceView;
        if (surfaceView == null || surfaceView.getHolder().getSurface() == null || !this.mSurfaceView.getHolder().getSurface().isValid()) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(this.mSurfaceView.getWidth(), this.mSurfaceView.getHeight(), Bitmap.Config.ARGB_8888);
        SurfaceView surfaceView2 = this.mSurfaceView;
        PixelCopy.request(surfaceView2, bitmap, m.a, surfaceView2.getHandler());
        return bitmap;
    }

    static /* synthetic */ void lambda$getPreviewBitmap$1(int copyResult) {
        if (copyResult == 0) {
            Logger.d(TAG, "PreviewView.SurfaceViewImplementation.getBitmap() succeeded");
            return;
        }
        Logger.e(TAG, "PreviewView.SurfaceViewImplementation.getBitmap() failed with error " + copyResult);
    }

    public class SurfaceRequestCallback implements SurfaceHolder.Callback {
        @Nullable
        private Size mCurrentSurfaceSize;
        @Nullable
        private SurfaceRequest mSurfaceRequest;
        @Nullable
        private Size mTargetSize;
        private boolean mWasSurfaceProvided = false;

        SurfaceRequestCallback() {
        }

        /* access modifiers changed from: package-private */
        @UiThread
        public void setSurfaceRequest(@NonNull SurfaceRequest surfaceRequest) {
            cancelPreviousRequest();
            this.mSurfaceRequest = surfaceRequest;
            Size targetSize = surfaceRequest.getResolution();
            this.mTargetSize = targetSize;
            this.mWasSurfaceProvided = false;
            if (!tryToComplete()) {
                Logger.d(SurfaceViewImplementation.TAG, "Wait for new Surface creation.");
                SurfaceViewImplementation.this.mSurfaceView.getHolder().setFixedSize(targetSize.getWidth(), targetSize.getHeight());
            }
        }

        @UiThread
        private boolean tryToComplete() {
            Surface surface = SurfaceViewImplementation.this.mSurfaceView.getHolder().getSurface();
            if (!canProvideSurface()) {
                return false;
            }
            Logger.d(SurfaceViewImplementation.TAG, "Surface set on Preview.");
            this.mSurfaceRequest.provideSurface(surface, ContextCompat.getMainExecutor(SurfaceViewImplementation.this.mSurfaceView.getContext()), new n(this));
            this.mWasSurfaceProvided = true;
            SurfaceViewImplementation.this.onSurfaceProvided();
            return true;
        }

        /* access modifiers changed from: private */
        /* renamed from: lambda$tryToComplete$0 */
        public /* synthetic */ void a(SurfaceRequest.Result result) {
            Logger.d(SurfaceViewImplementation.TAG, "Safe to release surface.");
            SurfaceViewImplementation.this.notifySurfaceNotInUse();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:4:0x0008, code lost:
            r0 = r2.mTargetSize;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean canProvideSurface() {
            /*
                r2 = this;
                boolean r0 = r2.mWasSurfaceProvided
                if (r0 != 0) goto L_0x0016
                androidx.camera.core.SurfaceRequest r0 = r2.mSurfaceRequest
                if (r0 == 0) goto L_0x0016
                android.util.Size r0 = r2.mTargetSize
                if (r0 == 0) goto L_0x0016
                android.util.Size r1 = r2.mCurrentSurfaceSize
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0016
                r0 = 1
                goto L_0x0017
            L_0x0016:
                r0 = 0
            L_0x0017:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.camera.view.SurfaceViewImplementation.SurfaceRequestCallback.canProvideSurface():boolean");
        }

        @UiThread
        private void cancelPreviousRequest() {
            if (this.mSurfaceRequest != null) {
                Logger.d(SurfaceViewImplementation.TAG, "Request canceled: " + this.mSurfaceRequest);
                this.mSurfaceRequest.willNotProvideSurface();
            }
        }

        @UiThread
        private void invalidateSurface() {
            if (this.mSurfaceRequest != null) {
                Logger.d(SurfaceViewImplementation.TAG, "Surface invalidated " + this.mSurfaceRequest);
                this.mSurfaceRequest.getDeferrableSurface().close();
            }
        }

        public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
            Logger.d(SurfaceViewImplementation.TAG, "Surface created.");
        }

        public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {
            Logger.d(SurfaceViewImplementation.TAG, "Surface changed. Size: " + width + "x" + height);
            this.mCurrentSurfaceSize = new Size(width, height);
            tryToComplete();
        }

        public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
            Logger.d(SurfaceViewImplementation.TAG, "Surface destroyed.");
            if (this.mWasSurfaceProvided) {
                invalidateSurface();
            } else {
                cancelPreviousRequest();
            }
            this.mWasSurfaceProvided = false;
            this.mSurfaceRequest = null;
            this.mCurrentSurfaceSize = null;
            this.mTargetSize = null;
        }
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public ListenableFuture<Void> waitForNextFrame() {
        return Futures.immediateFuture(null);
    }
}

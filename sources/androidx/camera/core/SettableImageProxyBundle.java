package androidx.camera.core;

import android.util.SparseArray;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.camera.core.impl.ImageProxyBundle;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SettableImageProxyBundle implements ImageProxyBundle {
    private final List<Integer> mCaptureIdList;
    @GuardedBy("mLock")
    private boolean mClosed = false;
    @GuardedBy("mLock")
    final SparseArray<CallbackToFutureAdapter.Completer<ImageProxy>> mCompleters = new SparseArray<>();
    @GuardedBy("mLock")
    private final SparseArray<ListenableFuture<ImageProxy>> mFutureResults = new SparseArray<>();
    final Object mLock = new Object();
    @GuardedBy("mLock")
    private final List<ImageProxy> mOwnedImageProxies = new ArrayList();
    private String mTagBundleKey = null;

    SettableImageProxyBundle(List<Integer> captureIds, String tagBundleKey) {
        this.mCaptureIdList = captureIds;
        this.mTagBundleKey = tagBundleKey;
        setup();
    }

    @NonNull
    public ListenableFuture<ImageProxy> getImageProxy(int captureId) {
        ListenableFuture<ImageProxy> result;
        synchronized (this.mLock) {
            if (!this.mClosed) {
                result = this.mFutureResults.get(captureId);
                if (result == null) {
                    throw new IllegalArgumentException("ImageProxyBundle does not contain this id: " + captureId);
                }
            } else {
                throw new IllegalStateException("ImageProxyBundle already closed.");
            }
        }
        return result;
    }

    @NonNull
    public List<Integer> getCaptureIds() {
        return Collections.unmodifiableList(this.mCaptureIdList);
    }

    /* access modifiers changed from: package-private */
    public void addImageProxy(ImageProxy imageProxy) {
        synchronized (this.mLock) {
            if (!this.mClosed) {
                Integer captureId = imageProxy.getImageInfo().getTagBundle().getTag(this.mTagBundleKey);
                if (captureId != null) {
                    CallbackToFutureAdapter.Completer<ImageProxy> completer = this.mCompleters.get(captureId.intValue());
                    if (completer != null) {
                        this.mOwnedImageProxies.add(imageProxy);
                        completer.set(imageProxy);
                        return;
                    }
                    throw new IllegalArgumentException("ImageProxyBundle does not contain this id: " + captureId);
                }
                throw new IllegalArgumentException("CaptureId is null.");
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void close() {
        synchronized (this.mLock) {
            if (!this.mClosed) {
                for (ImageProxy imageProxy : this.mOwnedImageProxies) {
                    imageProxy.close();
                }
                this.mOwnedImageProxies.clear();
                this.mFutureResults.clear();
                this.mCompleters.clear();
                this.mClosed = true;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void reset() {
        synchronized (this.mLock) {
            if (!this.mClosed) {
                for (ImageProxy imageProxy : this.mOwnedImageProxies) {
                    imageProxy.close();
                }
                this.mOwnedImageProxies.clear();
                this.mFutureResults.clear();
                this.mCompleters.clear();
                setup();
            }
        }
    }

    private void setup() {
        synchronized (this.mLock) {
            for (Integer intValue : this.mCaptureIdList) {
                final int captureId = intValue.intValue();
                this.mFutureResults.put(captureId, CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver<ImageProxy>() {
                    public Object attachCompleter(@NonNull CallbackToFutureAdapter.Completer<ImageProxy> completer) {
                        synchronized (SettableImageProxyBundle.this.mLock) {
                            SettableImageProxyBundle.this.mCompleters.put(captureId, completer);
                        }
                        return "getImageProxy(id: " + captureId + ")";
                    }
                }));
            }
        }
    }
}

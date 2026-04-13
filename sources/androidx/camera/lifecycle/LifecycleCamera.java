package androidx.camera.lifecycle;

import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CameraConfig;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.internal.CameraUseCaseAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public final class LifecycleCamera implements LifecycleObserver, Camera {
    private final CameraUseCaseAdapter mCameraUseCaseAdapter;
    @GuardedBy("mLock")
    private volatile boolean mIsActive = false;
    @GuardedBy("mLock")
    private final LifecycleOwner mLifecycleOwner;
    private final Object mLock = new Object();
    @GuardedBy("mLock")
    private boolean mReleased = false;
    @GuardedBy("mLock")
    private boolean mSuspended = false;

    LifecycleCamera(LifecycleOwner lifecycleOwner, CameraUseCaseAdapter cameraUseCaseAdaptor) {
        this.mLifecycleOwner = lifecycleOwner;
        this.mCameraUseCaseAdapter = cameraUseCaseAdaptor;
        if (lifecycleOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            cameraUseCaseAdaptor.attachUseCases();
        } else {
            cameraUseCaseAdaptor.detachUseCases();
        }
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart(LifecycleOwner lifecycleOwner) {
        synchronized (this.mLock) {
            if (!this.mSuspended && !this.mReleased) {
                this.mCameraUseCaseAdapter.attachUseCases();
                this.mIsActive = true;
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(LifecycleOwner lifecycleOwner) {
        synchronized (this.mLock) {
            if (!this.mSuspended && !this.mReleased) {
                this.mCameraUseCaseAdapter.detachUseCases();
                this.mIsActive = false;
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(LifecycleOwner lifecycleOwner) {
        synchronized (this.mLock) {
            CameraUseCaseAdapter cameraUseCaseAdapter = this.mCameraUseCaseAdapter;
            cameraUseCaseAdapter.removeUseCases(cameraUseCaseAdapter.getUseCases());
        }
    }

    public void suspend() {
        synchronized (this.mLock) {
            if (!this.mSuspended) {
                onStop(this.mLifecycleOwner);
                this.mSuspended = true;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0024, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void unsuspend() {
        /*
            r3 = this;
            java.lang.Object r0 = r3.mLock
            monitor-enter(r0)
            boolean r1 = r3.mSuspended     // Catch:{ all -> 0x0025 }
            if (r1 != 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0025 }
            return
        L_0x0009:
            r1 = 0
            r3.mSuspended = r1     // Catch:{ all -> 0x0025 }
            androidx.lifecycle.LifecycleOwner r1 = r3.mLifecycleOwner     // Catch:{ all -> 0x0025 }
            androidx.lifecycle.Lifecycle r1 = r1.getLifecycle()     // Catch:{ all -> 0x0025 }
            androidx.lifecycle.Lifecycle$State r1 = r1.getCurrentState()     // Catch:{ all -> 0x0025 }
            androidx.lifecycle.Lifecycle$State r2 = androidx.lifecycle.Lifecycle.State.STARTED     // Catch:{ all -> 0x0025 }
            boolean r1 = r1.isAtLeast(r2)     // Catch:{ all -> 0x0025 }
            if (r1 == 0) goto L_0x0023
            androidx.lifecycle.LifecycleOwner r1 = r3.mLifecycleOwner     // Catch:{ all -> 0x0025 }
            r3.onStart(r1)     // Catch:{ all -> 0x0025 }
        L_0x0023:
            monitor-exit(r0)     // Catch:{ all -> 0x0025 }
            return
        L_0x0025:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0025 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.lifecycle.LifecycleCamera.unsuspend():void");
    }

    public boolean isActive() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsActive;
        }
        return z;
    }

    public boolean isBound(@NonNull UseCase useCase) {
        boolean contains;
        synchronized (this.mLock) {
            contains = this.mCameraUseCaseAdapter.getUseCases().contains(useCase);
        }
        return contains;
    }

    @NonNull
    public List<UseCase> getUseCases() {
        List<UseCase> unmodifiableList;
        synchronized (this.mLock) {
            unmodifiableList = Collections.unmodifiableList(this.mCameraUseCaseAdapter.getUseCases());
        }
        return unmodifiableList;
    }

    public LifecycleOwner getLifecycleOwner() {
        LifecycleOwner lifecycleOwner;
        synchronized (this.mLock) {
            lifecycleOwner = this.mLifecycleOwner;
        }
        return lifecycleOwner;
    }

    public CameraUseCaseAdapter getCameraUseCaseAdapter() {
        return this.mCameraUseCaseAdapter;
    }

    /* access modifiers changed from: package-private */
    public void bind(Collection<UseCase> useCases) {
        synchronized (this.mLock) {
            this.mCameraUseCaseAdapter.addUseCases(useCases);
        }
    }

    /* access modifiers changed from: package-private */
    public void unbind(Collection<UseCase> useCases) {
        synchronized (this.mLock) {
            List<UseCase> useCasesToRemove = new ArrayList<>(useCases);
            useCasesToRemove.retainAll(this.mCameraUseCaseAdapter.getUseCases());
            this.mCameraUseCaseAdapter.removeUseCases(useCasesToRemove);
        }
    }

    /* access modifiers changed from: package-private */
    public void unbindAll() {
        synchronized (this.mLock) {
            CameraUseCaseAdapter cameraUseCaseAdapter = this.mCameraUseCaseAdapter;
            cameraUseCaseAdapter.removeUseCases(cameraUseCaseAdapter.getUseCases());
        }
    }

    /* access modifiers changed from: package-private */
    public void release() {
        synchronized (this.mLock) {
            this.mReleased = true;
            this.mIsActive = false;
            this.mLifecycleOwner.getLifecycle().removeObserver(this);
        }
    }

    @NonNull
    public CameraControl getCameraControl() {
        return this.mCameraUseCaseAdapter.getCameraControl();
    }

    @NonNull
    public CameraInfo getCameraInfo() {
        return this.mCameraUseCaseAdapter.getCameraInfo();
    }

    @NonNull
    public LinkedHashSet<CameraInternal> getCameraInternals() {
        return this.mCameraUseCaseAdapter.getCameraInternals();
    }

    @NonNull
    public CameraConfig getExtendedConfig() {
        return this.mCameraUseCaseAdapter.getExtendedConfig();
    }

    public void setExtendedConfig(@Nullable CameraConfig cameraConfig) {
        this.mCameraUseCaseAdapter.setExtendedConfig(cameraConfig);
    }
}

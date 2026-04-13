package androidx.camera.lifecycle;

import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.UseCase;
import androidx.camera.core.ViewPort;
import androidx.camera.core.internal.CameraUseCaseAdapter;
import androidx.core.util.Preconditions;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import com.google.auto.value.AutoValue;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class LifecycleCameraRepository {
    @GuardedBy("mLock")
    private final ArrayDeque<LifecycleOwner> mActiveLifecycleOwners = new ArrayDeque<>();
    @GuardedBy("mLock")
    private final Map<Key, LifecycleCamera> mCameraMap = new HashMap();
    @GuardedBy("mLock")
    private final Map<LifecycleCameraRepositoryObserver, Set<Key>> mLifecycleObserverMap = new HashMap();
    private final Object mLock = new Object();

    LifecycleCameraRepository() {
    }

    /* access modifiers changed from: package-private */
    public LifecycleCamera createLifecycleCamera(@NonNull LifecycleOwner lifecycleOwner, @NonNull CameraUseCaseAdapter cameraUseCaseAdaptor) {
        LifecycleCamera lifecycleCamera;
        synchronized (this.mLock) {
            Preconditions.checkArgument(this.mCameraMap.get(Key.create(lifecycleOwner, cameraUseCaseAdaptor.getCameraId())) == null, "LifecycleCamera already exists for the given LifecycleOwner and set of cameras");
            if (lifecycleOwner.getLifecycle().getCurrentState() != Lifecycle.State.DESTROYED) {
                lifecycleCamera = new LifecycleCamera(lifecycleOwner, cameraUseCaseAdaptor);
                if (cameraUseCaseAdaptor.getUseCases().isEmpty()) {
                    lifecycleCamera.suspend();
                }
                registerCamera(lifecycleCamera);
            } else {
                throw new IllegalArgumentException("Trying to create LifecycleCamera with destroyed lifecycle.");
            }
        }
        return lifecycleCamera;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public LifecycleCamera getLifecycleCamera(LifecycleOwner lifecycleOwner, CameraUseCaseAdapter.CameraId cameraId) {
        LifecycleCamera lifecycleCamera;
        synchronized (this.mLock) {
            lifecycleCamera = this.mCameraMap.get(Key.create(lifecycleOwner, cameraId));
        }
        return lifecycleCamera;
    }

    /* access modifiers changed from: package-private */
    public Collection<LifecycleCamera> getLifecycleCameras() {
        Collection<LifecycleCamera> unmodifiableCollection;
        synchronized (this.mLock) {
            unmodifiableCollection = Collections.unmodifiableCollection(this.mCameraMap.values());
        }
        return unmodifiableCollection;
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        synchronized (this.mLock) {
            for (LifecycleCameraRepositoryObserver observer : new HashSet<>(this.mLifecycleObserverMap.keySet())) {
                unregisterLifecycle(observer.getLifecycleOwner());
            }
        }
    }

    private void registerCamera(LifecycleCamera lifecycleCamera) {
        Set<Key> lifecycleCameraKeySet;
        synchronized (this.mLock) {
            LifecycleOwner lifecycleOwner = lifecycleCamera.getLifecycleOwner();
            Key key = Key.create(lifecycleOwner, lifecycleCamera.getCameraUseCaseAdapter().getCameraId());
            LifecycleCameraRepositoryObserver observer = getLifecycleCameraRepositoryObserver(lifecycleOwner);
            if (observer != null) {
                lifecycleCameraKeySet = this.mLifecycleObserverMap.get(observer);
            } else {
                lifecycleCameraKeySet = new HashSet<>();
            }
            lifecycleCameraKeySet.add(key);
            this.mCameraMap.put(key, lifecycleCamera);
            if (observer == null) {
                LifecycleCameraRepositoryObserver observer2 = new LifecycleCameraRepositoryObserver(lifecycleOwner, this);
                this.mLifecycleObserverMap.put(observer2, lifecycleCameraKeySet);
                lifecycleOwner.getLifecycle().addObserver(observer2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void unregisterLifecycle(LifecycleOwner lifecycleOwner) {
        synchronized (this.mLock) {
            LifecycleCameraRepositoryObserver observer = getLifecycleCameraRepositoryObserver(lifecycleOwner);
            if (observer != null) {
                setInactive(lifecycleOwner);
                for (Key key : this.mLifecycleObserverMap.get(observer)) {
                    this.mCameraMap.remove(key);
                }
                this.mLifecycleObserverMap.remove(observer);
                observer.getLifecycleOwner().getLifecycle().removeObserver(observer);
            }
        }
    }

    private LifecycleCameraRepositoryObserver getLifecycleCameraRepositoryObserver(LifecycleOwner lifecycleOwner) {
        synchronized (this.mLock) {
            for (LifecycleCameraRepositoryObserver observer : this.mLifecycleObserverMap.keySet()) {
                if (lifecycleOwner.equals(observer.getLifecycleOwner())) {
                    return observer;
                }
            }
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public void bindToLifecycleCamera(@NonNull LifecycleCamera lifecycleCamera, @Nullable ViewPort viewPort, @NonNull Collection<UseCase> useCases) {
        synchronized (this.mLock) {
            Preconditions.checkArgument(!useCases.isEmpty());
            LifecycleOwner lifecycleOwner = lifecycleCamera.getLifecycleOwner();
            for (Key key : this.mLifecycleObserverMap.get(getLifecycleCameraRepositoryObserver(lifecycleOwner))) {
                LifecycleCamera camera = (LifecycleCamera) Preconditions.checkNotNull(this.mCameraMap.get(key));
                if (!camera.equals(lifecycleCamera)) {
                    if (!camera.getUseCases().isEmpty()) {
                        throw new IllegalArgumentException("Multiple LifecycleCameras with use cases are registered to the same LifecycleOwner.");
                    }
                }
            }
            try {
                lifecycleCamera.getCameraUseCaseAdapter().setViewPort(viewPort);
                lifecycleCamera.bind(useCases);
                if (lifecycleOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    setActive(lifecycleOwner);
                }
            } catch (CameraUseCaseAdapter.CameraException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void unbind(@NonNull Collection<UseCase> useCases) {
        synchronized (this.mLock) {
            for (Key key : this.mCameraMap.keySet()) {
                LifecycleCamera lifecycleCamera = this.mCameraMap.get(key);
                boolean hasUseCase = !lifecycleCamera.getUseCases().isEmpty();
                lifecycleCamera.unbind(useCases);
                if (hasUseCase && lifecycleCamera.getUseCases().isEmpty()) {
                    setInactive(lifecycleCamera.getLifecycleOwner());
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void unbindAll() {
        synchronized (this.mLock) {
            for (Key key : this.mCameraMap.keySet()) {
                LifecycleCamera lifecycleCamera = this.mCameraMap.get(key);
                lifecycleCamera.unbindAll();
                setInactive(lifecycleCamera.getLifecycleOwner());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setActive(LifecycleOwner lifecycleOwner) {
        synchronized (this.mLock) {
            if (hasUseCaseBound(lifecycleOwner)) {
                if (this.mActiveLifecycleOwners.isEmpty()) {
                    this.mActiveLifecycleOwners.push(lifecycleOwner);
                } else {
                    LifecycleOwner currentActiveLifecycleOwner = this.mActiveLifecycleOwners.peek();
                    if (!lifecycleOwner.equals(currentActiveLifecycleOwner)) {
                        suspendUseCases(currentActiveLifecycleOwner);
                        this.mActiveLifecycleOwners.remove(lifecycleOwner);
                        this.mActiveLifecycleOwners.push(lifecycleOwner);
                    }
                }
                unsuspendUseCases(lifecycleOwner);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setInactive(LifecycleOwner lifecycleOwner) {
        synchronized (this.mLock) {
            this.mActiveLifecycleOwners.remove(lifecycleOwner);
            suspendUseCases(lifecycleOwner);
            if (!this.mActiveLifecycleOwners.isEmpty()) {
                unsuspendUseCases(this.mActiveLifecycleOwners.peek());
            }
        }
    }

    private boolean hasUseCaseBound(LifecycleOwner lifecycleOwner) {
        synchronized (this.mLock) {
            LifecycleCameraRepositoryObserver observer = getLifecycleCameraRepositoryObserver(lifecycleOwner);
            if (observer == null) {
                return false;
            }
            for (Key key : this.mLifecycleObserverMap.get(observer)) {
                if (!((LifecycleCamera) Preconditions.checkNotNull(this.mCameraMap.get(key))).getUseCases().isEmpty()) {
                    return true;
                }
            }
            return false;
        }
    }

    private void suspendUseCases(LifecycleOwner lifecycleOwner) {
        synchronized (this.mLock) {
            for (Key key : this.mLifecycleObserverMap.get(getLifecycleCameraRepositoryObserver(lifecycleOwner))) {
                ((LifecycleCamera) Preconditions.checkNotNull(this.mCameraMap.get(key))).suspend();
            }
        }
    }

    private void unsuspendUseCases(LifecycleOwner lifecycleOwner) {
        synchronized (this.mLock) {
            for (Key key : this.mLifecycleObserverMap.get(getLifecycleCameraRepositoryObserver(lifecycleOwner))) {
                LifecycleCamera lifecycleCamera = this.mCameraMap.get(key);
                if (!((LifecycleCamera) Preconditions.checkNotNull(lifecycleCamera)).getUseCases().isEmpty()) {
                    lifecycleCamera.unsuspend();
                }
            }
        }
    }

    @AutoValue
    public static abstract class Key {
        @NonNull
        public abstract CameraUseCaseAdapter.CameraId getCameraId();

        @NonNull
        public abstract LifecycleOwner getLifecycleOwner();

        Key() {
        }

        static Key create(@NonNull LifecycleOwner lifecycleOwner, @NonNull CameraUseCaseAdapter.CameraId cameraId) {
            return new AutoValue_LifecycleCameraRepository_Key(lifecycleOwner, cameraId);
        }
    }

    public static class LifecycleCameraRepositoryObserver implements LifecycleObserver {
        private final LifecycleCameraRepository mLifecycleCameraRepository;
        private final LifecycleOwner mLifecycleOwner;

        LifecycleCameraRepositoryObserver(LifecycleOwner lifecycleOwner, LifecycleCameraRepository lifecycleCameraRepository) {
            this.mLifecycleOwner = lifecycleOwner;
            this.mLifecycleCameraRepository = lifecycleCameraRepository;
        }

        /* access modifiers changed from: package-private */
        public LifecycleOwner getLifecycleOwner() {
            return this.mLifecycleOwner;
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void onStart(LifecycleOwner lifecycleOwner) {
            this.mLifecycleCameraRepository.setActive(lifecycleOwner);
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        public void onStop(LifecycleOwner lifecycleOwner) {
            this.mLifecycleCameraRepository.setInactive(lifecycleOwner);
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void onDestroy(LifecycleOwner lifecycleOwner) {
            this.mLifecycleCameraRepository.unregisterLifecycle(lifecycleOwner);
        }
    }
}

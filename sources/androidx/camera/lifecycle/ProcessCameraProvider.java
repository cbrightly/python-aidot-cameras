package androidx.camera.lifecycle;

import android.content.Context;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.annotation.RestrictTo;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraFilter;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraX;
import androidx.camera.core.CameraXConfig;
import androidx.camera.core.ExperimentalCameraFilter;
import androidx.camera.core.ExperimentalUseCaseGroup;
import androidx.camera.core.UseCase;
import androidx.camera.core.UseCaseGroup;
import androidx.camera.core.ViewPort;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.core.internal.CameraUseCaseAdapter;
import androidx.core.util.Preconditions;
import androidx.lifecycle.LifecycleOwner;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public final class ProcessCameraProvider implements LifecycleCameraProvider {
    private static final ProcessCameraProvider sAppInstance = new ProcessCameraProvider();
    private CameraX mCameraX;
    private final LifecycleCameraRepository mLifecycleCameraRepository = new LifecycleCameraRepository();

    @NonNull
    public static ListenableFuture<ProcessCameraProvider> getInstance(@NonNull Context context) {
        Preconditions.checkNotNull(context);
        return Futures.transform(CameraX.getOrCreateInstance(context), a.a, CameraXExecutors.directExecutor());
    }

    static /* synthetic */ ProcessCameraProvider lambda$getInstance$0(CameraX cameraX) {
        ProcessCameraProvider processCameraProvider = sAppInstance;
        processCameraProvider.setCameraX(cameraX);
        return processCameraProvider;
    }

    @ExperimentalCameraProviderConfiguration
    public static void configureInstance(@NonNull CameraXConfig cameraXConfig) {
        CameraX.configureInstance(cameraXConfig);
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.TESTS})
    public ListenableFuture<Void> shutdown() {
        this.mLifecycleCameraRepository.clear();
        return CameraX.shutdown();
    }

    private void setCameraX(CameraX cameraX) {
        this.mCameraX = cameraX;
    }

    @MainThread
    @NonNull
    @OptIn(markerClass = {ExperimentalUseCaseGroup.class})
    public Camera bindToLifecycle(@NonNull LifecycleOwner lifecycleOwner, @NonNull CameraSelector cameraSelector, @NonNull UseCase... useCases) {
        return bindToLifecycle(lifecycleOwner, cameraSelector, (ViewPort) null, useCases);
    }

    @MainThread
    @OptIn(markerClass = {ExperimentalUseCaseGroup.class})
    @ExperimentalUseCaseGroupLifecycle
    @NonNull
    public Camera bindToLifecycle(@NonNull LifecycleOwner lifecycleOwner, @NonNull CameraSelector cameraSelector, @NonNull UseCaseGroup useCaseGroup) {
        return bindToLifecycle(lifecycleOwner, cameraSelector, useCaseGroup.getViewPort(), (UseCase[]) useCaseGroup.getUseCases().toArray(new UseCase[0]));
    }

    @OptIn(markerClass = {ExperimentalCameraFilter.class})
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @NonNull
    @ExperimentalUseCaseGroup
    public Camera bindToLifecycle(@NonNull LifecycleOwner lifecycleOwner, @NonNull CameraSelector cameraSelector, @Nullable ViewPort viewPort, @NonNull UseCase... useCases) {
        LifecycleOwner lifecycleOwner2 = lifecycleOwner;
        UseCase[] useCaseArr = useCases;
        Threads.checkMainThread();
        CameraSelector.Builder selectorBuilder = CameraSelector.Builder.fromSelector(cameraSelector);
        for (UseCase useCase : useCaseArr) {
            CameraSelector selector = useCase.getCurrentConfig().getCameraSelector((CameraSelector) null);
            if (selector != null) {
                Iterator it = selector.getCameraFilterSet().iterator();
                while (it.hasNext()) {
                    selectorBuilder.addCameraFilter((CameraFilter) it.next());
                }
            }
        }
        LinkedHashSet<CameraInternal> cameraInternals = selectorBuilder.build().filter(this.mCameraX.getCameraRepository().getCameras());
        LifecycleCamera lifecycleCameraToBind = this.mLifecycleCameraRepository.getLifecycleCamera(lifecycleOwner2, CameraUseCaseAdapter.generateCameraId(cameraInternals));
        Collection<LifecycleCamera> lifecycleCameras = this.mLifecycleCameraRepository.getLifecycleCameras();
        for (UseCase useCase2 : useCaseArr) {
            for (LifecycleCamera lifecycleCamera : lifecycleCameras) {
                if (lifecycleCamera.isBound(useCase2) && lifecycleCamera != lifecycleCameraToBind) {
                    throw new IllegalStateException(String.format("Use case %s already bound to a different lifecycle.", new Object[]{useCase2}));
                }
            }
        }
        if (lifecycleCameraToBind == null) {
            lifecycleCameraToBind = this.mLifecycleCameraRepository.createLifecycleCamera(lifecycleOwner2, new CameraUseCaseAdapter(cameraInternals, this.mCameraX.getCameraDeviceSurfaceManager(), this.mCameraX.getDefaultConfigFactory()));
        }
        if (useCaseArr.length == 0) {
            return lifecycleCameraToBind;
        }
        this.mLifecycleCameraRepository.bindToLifecycleCamera(lifecycleCameraToBind, viewPort, Arrays.asList(useCases));
        return lifecycleCameraToBind;
    }

    public boolean isBound(@NonNull UseCase useCase) {
        for (LifecycleCamera lifecycleCamera : this.mLifecycleCameraRepository.getLifecycleCameras()) {
            if (lifecycleCamera.isBound(useCase)) {
                return true;
            }
        }
        return false;
    }

    @MainThread
    public void unbind(@NonNull UseCase... useCases) {
        Threads.checkMainThread();
        this.mLifecycleCameraRepository.unbind(Arrays.asList(useCases));
    }

    @MainThread
    public void unbindAll() {
        Threads.checkMainThread();
        this.mLifecycleCameraRepository.unbindAll();
    }

    public boolean hasCamera(@NonNull CameraSelector cameraSelector) {
        try {
            cameraSelector.select(this.mCameraX.getCameraRepository().getCameras());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @NonNull
    public List<CameraInfo> getAvailableCameraInfos() {
        List<CameraInfo> availableCameraInfos = new ArrayList<>();
        for (CameraInternal camera : this.mCameraX.getCameraRepository().getCameras()) {
            availableCameraInfos.add(camera.getCameraInfo());
        }
        return availableCameraInfos;
    }

    private ProcessCameraProvider() {
    }
}

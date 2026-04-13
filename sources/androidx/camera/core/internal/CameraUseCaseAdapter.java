package androidx.camera.core.internal;

import android.graphics.Rect;
import android.util.Size;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalCameraFilter;
import androidx.camera.core.ExperimentalUseCaseGroup;
import androidx.camera.core.Logger;
import androidx.camera.core.UseCase;
import androidx.camera.core.ViewPort;
import androidx.camera.core.impl.CameraConfig;
import androidx.camera.core.impl.CameraConfigs;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.CameraDeviceSurfaceManager;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.SurfaceConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public final class CameraUseCaseAdapter implements Camera {
    private static final String TAG = "CameraUseCaseAdapter";
    @GuardedBy("mLock")
    private boolean mAttached = true;
    @GuardedBy("mLock")
    @NonNull
    private CameraConfig mCameraConfig = CameraConfigs.emptyConfig();
    private final CameraDeviceSurfaceManager mCameraDeviceSurfaceManager;
    @NonNull
    private CameraInternal mCameraInternal;
    private final LinkedHashSet<CameraInternal> mCameraInternals;
    private final CameraId mId;
    @GuardedBy("mLock")
    private Config mInteropConfig = null;
    private final Object mLock = new Object();
    private final UseCaseConfigFactory mUseCaseConfigFactory;
    @GuardedBy("mLock")
    private final List<UseCase> mUseCases = new ArrayList();
    @GuardedBy("mLock")
    @Nullable
    private ViewPort mViewPort;

    public CameraUseCaseAdapter(@NonNull LinkedHashSet<CameraInternal> cameras, @NonNull CameraDeviceSurfaceManager cameraDeviceSurfaceManager, @NonNull UseCaseConfigFactory useCaseConfigFactory) {
        this.mCameraInternal = (CameraInternal) cameras.iterator().next();
        LinkedHashSet<CameraInternal> linkedHashSet = new LinkedHashSet<>(cameras);
        this.mCameraInternals = linkedHashSet;
        this.mId = new CameraId(linkedHashSet);
        this.mCameraDeviceSurfaceManager = cameraDeviceSurfaceManager;
        this.mUseCaseConfigFactory = useCaseConfigFactory;
    }

    @NonNull
    public static CameraId generateCameraId(@NonNull LinkedHashSet<CameraInternal> cameras) {
        return new CameraId(cameras);
    }

    @NonNull
    public CameraId getCameraId() {
        return this.mId;
    }

    public boolean isEquivalent(@NonNull CameraUseCaseAdapter cameraUseCaseAdapter) {
        return this.mId.equals(cameraUseCaseAdapter.getCameraId());
    }

    public void setViewPort(@Nullable ViewPort viewPort) {
        synchronized (this.mLock) {
            this.mViewPort = viewPort;
        }
    }

    public void checkAttachUseCases(@NonNull List<UseCase> useCases) {
        synchronized (this.mLock) {
            try {
                calculateSuggestedResolutions(this.mCameraInternal.getCameraInfoInternal(), useCases, Collections.emptyList(), getConfigs(useCases, this.mCameraConfig.getUseCaseConfigFactory(), this.mUseCaseConfigFactory));
            } catch (IllegalArgumentException e) {
                throw new CameraException(e.getMessage());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @OptIn(markerClass = {ExperimentalUseCaseGroup.class})
    public void addUseCases(@NonNull Collection<UseCase> useCases) {
        synchronized (this.mLock) {
            List<UseCase> newUseCases = new ArrayList<>();
            for (UseCase useCase : useCases) {
                if (this.mUseCases.contains(useCase)) {
                    Logger.d(TAG, "Attempting to attach already attached UseCase");
                } else {
                    newUseCases.add(useCase);
                }
            }
            Map<UseCase, ConfigPair> configs = getConfigs(newUseCases, this.mCameraConfig.getUseCaseConfigFactory(), this.mUseCaseConfigFactory);
            try {
                Map<UseCase, Size> suggestedResolutionsMap = calculateSuggestedResolutions(this.mCameraInternal.getCameraInfoInternal(), newUseCases, this.mUseCases, configs);
                updateViewPort(suggestedResolutionsMap, useCases);
                for (UseCase useCase2 : newUseCases) {
                    ConfigPair configPair = configs.get(useCase2);
                    useCase2.onAttach(this.mCameraInternal, configPair.mExtendedConfig, configPair.mCameraConfig);
                    useCase2.updateSuggestedResolution((Size) Preconditions.checkNotNull(suggestedResolutionsMap.get(useCase2)));
                }
                this.mUseCases.addAll(newUseCases);
                if (this.mAttached) {
                    notifyAttachedUseCasesChange(this.mUseCases);
                    this.mCameraInternal.attachUseCases(newUseCases);
                }
                for (UseCase useCase3 : newUseCases) {
                    useCase3.notifyState();
                }
            } catch (IllegalArgumentException e) {
                throw new CameraException(e.getMessage());
            }
        }
    }

    public void removeUseCases(@NonNull Collection<UseCase> useCases) {
        synchronized (this.mLock) {
            this.mCameraInternal.detachUseCases(useCases);
            for (UseCase useCase : useCases) {
                if (this.mUseCases.contains(useCase)) {
                    useCase.onDetach(this.mCameraInternal);
                } else {
                    Logger.e(TAG, "Attempting to detach non-attached UseCase: " + useCase);
                }
            }
            this.mUseCases.removeAll(useCases);
        }
    }

    @NonNull
    public List<UseCase> getUseCases() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mUseCases);
        }
        return arrayList;
    }

    public void attachUseCases() {
        synchronized (this.mLock) {
            if (!this.mAttached) {
                this.mCameraInternal.attachUseCases(this.mUseCases);
                notifyAttachedUseCasesChange(this.mUseCases);
                restoreInteropConfig();
                for (UseCase useCase : this.mUseCases) {
                    useCase.notifyState();
                }
                this.mAttached = true;
            }
        }
    }

    public void detachUseCases() {
        synchronized (this.mLock) {
            if (this.mAttached) {
                this.mCameraInternal.detachUseCases(new ArrayList(this.mUseCases));
                cacheInteropConfig();
                this.mAttached = false;
            }
        }
    }

    private void restoreInteropConfig() {
        synchronized (this.mLock) {
            if (this.mInteropConfig != null) {
                this.mCameraInternal.getCameraControlInternal().addInteropConfig(this.mInteropConfig);
            }
        }
    }

    private void cacheInteropConfig() {
        synchronized (this.mLock) {
            CameraControlInternal cameraControlInternal = this.mCameraInternal.getCameraControlInternal();
            this.mInteropConfig = cameraControlInternal.getInteropConfig();
            cameraControlInternal.clearInteropConfig();
        }
    }

    private Map<UseCase, Size> calculateSuggestedResolutions(@NonNull CameraInfoInternal cameraInfoInternal, @NonNull List<UseCase> newUseCases, @NonNull List<UseCase> currentUseCases, @NonNull Map<UseCase, ConfigPair> configPairMap) {
        List<SurfaceConfig> existingSurfaces = new ArrayList<>();
        String cameraId = cameraInfoInternal.getCameraId();
        Map<UseCase, Size> suggestedResolutions = new HashMap<>();
        for (UseCase useCase : currentUseCases) {
            existingSurfaces.add(this.mCameraDeviceSurfaceManager.transformSurfaceConfig(cameraId, useCase.getImageFormat(), useCase.getAttachedSurfaceResolution()));
            suggestedResolutions.put(useCase, useCase.getAttachedSurfaceResolution());
        }
        if (!newUseCases.isEmpty()) {
            Map<UseCaseConfig<?>, UseCase> configToUseCaseMap = new HashMap<>();
            for (UseCase useCase2 : newUseCases) {
                ConfigPair configPair = configPairMap.get(useCase2);
                configToUseCaseMap.put(useCase2.mergeConfigs(cameraInfoInternal, configPair.mExtendedConfig, configPair.mCameraConfig), useCase2);
            }
            Map<UseCaseConfig<?>, Size> useCaseConfigSizeMap = this.mCameraDeviceSurfaceManager.getSuggestedResolutions(cameraId, existingSurfaces, new ArrayList(configToUseCaseMap.keySet()));
            for (Map.Entry<UseCaseConfig<?>, UseCase> entry : configToUseCaseMap.entrySet()) {
                suggestedResolutions.put(entry.getValue(), useCaseConfigSizeMap.get(entry.getKey()));
            }
        }
        return suggestedResolutions;
    }

    @OptIn(markerClass = {ExperimentalUseCaseGroup.class})
    private void updateViewPort(@NonNull Map<UseCase, Size> suggestedResolutionsMap, @NonNull Collection<UseCase> useCases) {
        synchronized (this.mLock) {
            if (this.mViewPort != null) {
                Map<UseCase, Rect> cropRectMap = ViewPorts.calculateViewPortRects(this.mCameraInternal.getCameraControlInternal().getSensorRect(), this.mCameraInternal.getCameraInfoInternal().getLensFacing().intValue() == 0, this.mViewPort.getAspectRatio(), this.mCameraInternal.getCameraInfoInternal().getSensorRotationDegrees(this.mViewPort.getRotation()), this.mViewPort.getScaleType(), this.mViewPort.getLayoutDirection(), suggestedResolutionsMap);
                for (UseCase useCase : useCases) {
                    useCase.setViewPortCropRect((Rect) Preconditions.checkNotNull(cropRectMap.get(useCase)));
                }
            }
        }
    }

    public static class ConfigPair {
        UseCaseConfig<?> mCameraConfig;
        UseCaseConfig<?> mExtendedConfig;

        ConfigPair(UseCaseConfig<?> extendedConfig, UseCaseConfig<?> cameraConfig) {
            this.mExtendedConfig = extendedConfig;
            this.mCameraConfig = cameraConfig;
        }
    }

    private Map<UseCase, ConfigPair> getConfigs(List<UseCase> useCases, UseCaseConfigFactory extendedFactory, UseCaseConfigFactory cameraFactory) {
        Map<UseCase, ConfigPair> configs = new HashMap<>();
        for (UseCase useCase : useCases) {
            configs.put(useCase, new ConfigPair(useCase.getDefaultConfig(false, extendedFactory), useCase.getDefaultConfig(true, cameraFactory)));
        }
        return configs;
    }

    public static final class CameraId {
        private final List<String> mIds = new ArrayList();

        CameraId(LinkedHashSet<CameraInternal> cameraInternals) {
            Iterator it = cameraInternals.iterator();
            while (it.hasNext()) {
                this.mIds.add(((CameraInternal) it.next()).getCameraInfoInternal().getCameraId());
            }
        }

        public boolean equals(Object cameraId) {
            if (cameraId instanceof CameraId) {
                return this.mIds.equals(((CameraId) cameraId).mIds);
            }
            return false;
        }

        public int hashCode() {
            return this.mIds.hashCode() * 53;
        }
    }

    public static final class CameraException extends Exception {
        public CameraException() {
        }

        public CameraException(@NonNull String message) {
            super(message);
        }

        public CameraException(@NonNull Throwable cause) {
            super(cause);
        }
    }

    @NonNull
    public CameraControl getCameraControl() {
        return this.mCameraInternal.getCameraControlInternal();
    }

    @NonNull
    public CameraInfo getCameraInfo() {
        return this.mCameraInternal.getCameraInfoInternal();
    }

    @NonNull
    public LinkedHashSet<CameraInternal> getCameraInternals() {
        return this.mCameraInternals;
    }

    @NonNull
    public CameraConfig getExtendedConfig() {
        CameraConfig cameraConfig;
        synchronized (this.mLock) {
            cameraConfig = this.mCameraConfig;
        }
        return cameraConfig;
    }

    @OptIn(markerClass = {ExperimentalCameraFilter.class})
    public void setExtendedConfig(@Nullable CameraConfig cameraConfig) {
        CameraConfig newCameraConfig;
        synchronized (this.mLock) {
            if (cameraConfig == null) {
                try {
                    newCameraConfig = CameraConfigs.emptyConfig();
                } catch (IllegalArgumentException e) {
                    throw new CameraException(e.getMessage());
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                newCameraConfig = cameraConfig;
            }
            CameraInternal cameraInternal = new CameraSelector.Builder().addCameraFilter(newCameraConfig.getCameraFilter()).build().select(this.mCameraInternals);
            Map<UseCase, ConfigPair> configs = getConfigs(this.mUseCases, newCameraConfig.getUseCaseConfigFactory(), this.mUseCaseConfigFactory);
            Map<UseCase, Size> suggestedResolutionsMap = calculateSuggestedResolutions(cameraInternal.getCameraInfoInternal(), this.mUseCases, Collections.emptyList(), configs);
            updateViewPort(suggestedResolutionsMap, this.mUseCases);
            if (this.mAttached) {
                this.mCameraInternal.detachUseCases(this.mUseCases);
            }
            for (UseCase useCase : this.mUseCases) {
                useCase.onDetach(this.mCameraInternal);
            }
            for (UseCase useCase2 : this.mUseCases) {
                ConfigPair configPair = configs.get(useCase2);
                useCase2.onAttach(cameraInternal, configPair.mExtendedConfig, configPair.mCameraConfig);
                useCase2.updateSuggestedResolution((Size) Preconditions.checkNotNull(suggestedResolutionsMap.get(useCase2)));
            }
            if (this.mAttached) {
                notifyAttachedUseCasesChange(this.mUseCases);
                cameraInternal.attachUseCases(this.mUseCases);
            }
            for (UseCase useCase3 : this.mUseCases) {
                useCase3.notifyState();
            }
            this.mCameraInternal = cameraInternal;
            this.mCameraConfig = newCameraConfig;
        }
    }

    private void notifyAttachedUseCasesChange(@NonNull List<UseCase> useCases) {
        CameraXExecutors.mainThreadExecutor().execute(new a(useCases));
    }

    static /* synthetic */ void lambda$notifyAttachedUseCasesChange$0(List useCases) {
        Iterator it = useCases.iterator();
        while (it.hasNext()) {
            Consumer<Collection<UseCase>> attachedUseCasesUpdateListener = ((UseCase) it.next()).getCurrentConfig().getAttachedUseCasesUpdateListener((Consumer<Collection<UseCase>>) null);
            if (attachedUseCasesUpdateListener != null) {
                attachedUseCasesUpdateListener.accept(Collections.unmodifiableList(useCases));
            }
        }
    }
}

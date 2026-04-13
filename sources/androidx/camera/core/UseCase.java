package androidx.camera.core;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.util.Size;
import androidx.annotation.CallSuper;
import androidx.annotation.GuardedBy;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.utils.UseCaseConfigUtil;
import androidx.core.util.Preconditions;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class UseCase {
    private Size mAttachedResolution;
    private SessionConfig mAttachedSessionConfig = SessionConfig.defaultEmptySessionConfig();
    @GuardedBy("mCameraLock")
    private CameraInternal mCamera;
    @Nullable
    private UseCaseConfig<?> mCameraConfig;
    private final Object mCameraLock = new Object();
    @NonNull
    private UseCaseConfig<?> mCurrentConfig;
    @Nullable
    private UseCaseConfig<?> mExtendedConfig;
    private State mState = State.INACTIVE;
    private final Set<StateChangeCallback> mStateChangeCallbacks = new HashSet();
    @NonNull
    private UseCaseConfig<?> mUseCaseConfig;
    @Nullable
    private Rect mViewPortCropRect;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface EventCallback {
        void onAttach(@NonNull CameraInfo cameraInfo);

        void onDetach();
    }

    public enum State {
        ACTIVE,
        INACTIVE
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface StateChangeCallback {
        void onUseCaseActive(@NonNull UseCase useCase);

        void onUseCaseInactive(@NonNull UseCase useCase);

        void onUseCaseReset(@NonNull UseCase useCase);

        void onUseCaseUpdated(@NonNull UseCase useCase);
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public abstract UseCaseConfig<?> getDefaultConfig(boolean z, @NonNull UseCaseConfigFactory useCaseConfigFactory);

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public abstract UseCaseConfig.Builder<?, ?, ?> getUseCaseConfigBuilder(@NonNull Config config);

    /* access modifiers changed from: protected */
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public abstract Size onSuggestedResolutionUpdated(@NonNull Size size);

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected UseCase(@NonNull UseCaseConfig<?> currentConfig) {
        this.mUseCaseConfig = currentConfig;
        this.mCurrentConfig = currentConfig;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseConfig<?> mergeConfigs(@NonNull CameraInfoInternal cameraInfo, @Nullable UseCaseConfig<?> extendedConfig, @Nullable UseCaseConfig<?> cameraDefaultConfig) {
        MutableOptionsBundle mergedConfig;
        if (cameraDefaultConfig != null) {
            mergedConfig = MutableOptionsBundle.from(cameraDefaultConfig);
            mergedConfig.removeOption(TargetConfig.OPTION_TARGET_NAME);
        } else {
            mergedConfig = MutableOptionsBundle.create();
        }
        for (Config.Option<?> opt : this.mUseCaseConfig.listOptions()) {
            Config.Option<?> option = opt;
            mergedConfig.insertOption(option, this.mUseCaseConfig.getOptionPriority(opt), this.mUseCaseConfig.retrieveOption(option));
        }
        if (extendedConfig != null) {
            for (Config.Option<?> opt2 : extendedConfig.listOptions()) {
                Config.Option<?> option2 = opt2;
                if (!option2.getId().equals(TargetConfig.OPTION_TARGET_NAME.getId())) {
                    mergedConfig.insertOption(option2, extendedConfig.getOptionPriority(opt2), extendedConfig.retrieveOption(option2));
                }
            }
        }
        if (mergedConfig.containsOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION)) {
            Config.Option<Integer> option3 = ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO;
            if (mergedConfig.containsOption(option3)) {
                mergedConfig.removeOption(option3);
            }
        }
        return onMergeConfig(cameraInfo, getUseCaseConfigBuilder(mergedConfig));
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [androidx.camera.core.impl.UseCaseConfig$Builder, androidx.camera.core.impl.UseCaseConfig$Builder<?, ?, ?>] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Unknown variable types count: 1 */
    @androidx.annotation.NonNull
    @androidx.annotation.RestrictTo({androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public androidx.camera.core.impl.UseCaseConfig<?> onMergeConfig(@androidx.annotation.NonNull androidx.camera.core.impl.CameraInfoInternal r2, @androidx.annotation.NonNull androidx.camera.core.impl.UseCaseConfig.Builder<?, ?, ?> r3) {
        /*
            r1 = this;
            androidx.camera.core.impl.UseCaseConfig r0 = r3.getUseCaseConfig()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.UseCase.onMergeConfig(androidx.camera.core.impl.CameraInfoInternal, androidx.camera.core.impl.UseCaseConfig$Builder):androidx.camera.core.impl.UseCaseConfig");
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean setTargetRotationInternal(int targetRotation) {
        int oldRotation = ((ImageOutputConfig) getCurrentConfig()).getTargetRotation(-1);
        if (oldRotation != -1 && oldRotation == targetRotation) {
            return false;
        }
        UseCaseConfig.Builder useCaseConfigBuilder = getUseCaseConfigBuilder(this.mUseCaseConfig);
        UseCaseConfigUtil.updateTargetRotationAndRelatedConfigs(useCaseConfigBuilder, targetRotation);
        this.mUseCaseConfig = useCaseConfigBuilder.getUseCaseConfig();
        CameraInternal camera = getCamera();
        if (camera == null) {
            this.mCurrentConfig = this.mUseCaseConfig;
            return true;
        }
        this.mCurrentConfig = mergeConfigs(camera.getCameraInfoInternal(), this.mExtendedConfig, this.mCameraConfig);
        return true;
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"WrongConstant"})
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getTargetRotationInternal() {
        return ((ImageOutputConfig) this.mCurrentConfig).getTargetRotation(0);
    }

    /* access modifiers changed from: protected */
    @IntRange(from = 0, to = 359)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getRelativeRotation(@NonNull CameraInternal cameraInternal) {
        return cameraInternal.getCameraInfoInternal().getSensorRotationDegrees(getTargetRotationInternal());
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void updateSessionConfig(@NonNull SessionConfig sessionConfig) {
        this.mAttachedSessionConfig = sessionConfig;
    }

    private void addStateChangeCallback(@NonNull StateChangeCallback callback) {
        this.mStateChangeCallbacks.add(callback);
    }

    private void removeStateChangeCallback(@NonNull StateChangeCallback callback) {
        this.mStateChangeCallbacks.remove(callback);
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public SessionConfig getSessionConfig() {
        return this.mAttachedSessionConfig;
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final void notifyActive() {
        this.mState = State.ACTIVE;
        notifyState();
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final void notifyInactive() {
        this.mState = State.INACTIVE;
        notifyState();
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final void notifyUpdated() {
        for (StateChangeCallback stateChangeCallback : this.mStateChangeCallbacks) {
            stateChangeCallback.onUseCaseUpdated(this);
        }
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final void notifyReset() {
        for (StateChangeCallback stateChangeCallback : this.mStateChangeCallbacks) {
            stateChangeCallback.onUseCaseReset(this);
        }
    }

    /* renamed from: androidx.camera.core.UseCase$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$core$UseCase$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$androidx$camera$core$UseCase$State = iArr;
            try {
                iArr[State.INACTIVE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$camera$core$UseCase$State[State.ACTIVE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final void notifyState() {
        switch (AnonymousClass1.$SwitchMap$androidx$camera$core$UseCase$State[this.mState.ordinal()]) {
            case 1:
                for (StateChangeCallback stateChangeCallback : this.mStateChangeCallbacks) {
                    stateChangeCallback.onUseCaseInactive(this);
                }
                return;
            case 2:
                for (StateChangeCallback stateChangeCallback2 : this.mStateChangeCallbacks) {
                    stateChangeCallback2.onUseCaseActive(this);
                }
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public String getCameraId() {
        CameraInternal camera = getCamera();
        return ((CameraInternal) Preconditions.checkNotNull(camera, "No camera attached to use case: " + this)).getCameraInfoInternal().getCameraId();
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isCurrentCamera(@NonNull String cameraId) {
        if (getCamera() == null) {
            return false;
        }
        return Objects.equals(cameraId, getCameraId());
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public String getName() {
        UseCaseConfig<?> useCaseConfig = this.mCurrentConfig;
        return useCaseConfig.getTargetName("<UnknownUseCase-" + hashCode() + ">");
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseConfig<?> getCurrentConfig() {
        return this.mCurrentConfig;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public CameraInternal getCamera() {
        CameraInternal cameraInternal;
        synchronized (this.mCameraLock) {
            cameraInternal = this.mCamera;
        }
        return cameraInternal;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Size getAttachedSurfaceResolution() {
        return this.mAttachedResolution;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void updateSuggestedResolution(@NonNull Size suggestedResolution) {
        this.mAttachedResolution = onSuggestedResolutionUpdated(suggestedResolution);
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onCameraControlReady() {
    }

    @SuppressLint({"WrongConstant"})
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onAttach(@NonNull CameraInternal camera, @Nullable UseCaseConfig<?> extendedConfig, @Nullable UseCaseConfig<?> cameraConfig) {
        synchronized (this.mCameraLock) {
            this.mCamera = camera;
            addStateChangeCallback(camera);
        }
        this.mExtendedConfig = extendedConfig;
        this.mCameraConfig = cameraConfig;
        UseCaseConfig<?> mergeConfigs = mergeConfigs(camera.getCameraInfoInternal(), this.mExtendedConfig, this.mCameraConfig);
        this.mCurrentConfig = mergeConfigs;
        EventCallback eventCallback = mergeConfigs.getUseCaseEventCallback((EventCallback) null);
        if (eventCallback != null) {
            eventCallback.onAttach(camera.getCameraInfoInternal());
        }
        onAttached();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onAttached() {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void onDetach(@NonNull CameraInternal camera) {
        onDetached();
        EventCallback eventCallback = this.mCurrentConfig.getUseCaseEventCallback((EventCallback) null);
        if (eventCallback != null) {
            eventCallback.onDetach();
        }
        synchronized (this.mCameraLock) {
            Preconditions.checkArgument(camera == this.mCamera);
            removeStateChangeCallback(this.mCamera);
            this.mCamera = null;
        }
        this.mAttachedResolution = null;
        this.mViewPortCropRect = null;
        this.mCurrentConfig = this.mUseCaseConfig;
        this.mExtendedConfig = null;
        this.mCameraConfig = null;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onDetached() {
    }

    @CallSuper
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onStateAttached() {
        onCameraControlReady();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onStateDetached() {
    }

    /* access modifiers changed from: protected */
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public CameraControlInternal getCameraControl() {
        synchronized (this.mCameraLock) {
            CameraInternal cameraInternal = this.mCamera;
            if (cameraInternal == null) {
                CameraControlInternal cameraControlInternal = CameraControlInternal.DEFAULT_EMPTY_INSTANCE;
                return cameraControlInternal;
            }
            CameraControlInternal cameraControlInternal2 = cameraInternal.getCameraControlInternal();
            return cameraControlInternal2;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setViewPortCropRect(@NonNull Rect viewPortCropRect) {
        this.mViewPortCropRect = viewPortCropRect;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Rect getViewPortCropRect() {
        return this.mViewPortCropRect;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getImageFormat() {
        return this.mCurrentConfig.getInputFormat();
    }
}

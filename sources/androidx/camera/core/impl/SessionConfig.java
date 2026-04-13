package androidx.camera.core.impl;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import androidx.annotation.NonNull;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.CaptureConfig;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SessionConfig {
    private final List<CameraDevice.StateCallback> mDeviceStateCallbacks;
    private final List<ErrorListener> mErrorListeners;
    private final CaptureConfig mRepeatingCaptureConfig;
    private final List<CameraCaptureSession.StateCallback> mSessionStateCallbacks;
    private final List<CameraCaptureCallback> mSingleCameraCaptureCallbacks;
    private final List<DeferrableSurface> mSurfaces;

    public interface ErrorListener {
        void onError(@NonNull SessionConfig sessionConfig, @NonNull SessionError sessionError);
    }

    public interface OptionUnpacker {
        void unpack(@NonNull UseCaseConfig<?> useCaseConfig, @NonNull Builder builder);
    }

    public enum SessionError {
        SESSION_ERROR_SURFACE_NEEDS_RESET,
        SESSION_ERROR_UNKNOWN
    }

    SessionConfig(List<DeferrableSurface> surfaces, List<CameraDevice.StateCallback> deviceStateCallbacks, List<CameraCaptureSession.StateCallback> sessionStateCallbacks, List<CameraCaptureCallback> singleCameraCaptureCallbacks, List<ErrorListener> errorListeners, CaptureConfig repeatingCaptureConfig) {
        this.mSurfaces = surfaces;
        this.mDeviceStateCallbacks = Collections.unmodifiableList(deviceStateCallbacks);
        this.mSessionStateCallbacks = Collections.unmodifiableList(sessionStateCallbacks);
        this.mSingleCameraCaptureCallbacks = Collections.unmodifiableList(singleCameraCaptureCallbacks);
        this.mErrorListeners = Collections.unmodifiableList(errorListeners);
        this.mRepeatingCaptureConfig = repeatingCaptureConfig;
    }

    @NonNull
    public static SessionConfig defaultEmptySessionConfig() {
        return new SessionConfig(new ArrayList(), new ArrayList(0), new ArrayList(0), new ArrayList(0), new ArrayList(0), new CaptureConfig.Builder().build());
    }

    @NonNull
    public List<DeferrableSurface> getSurfaces() {
        return Collections.unmodifiableList(this.mSurfaces);
    }

    @NonNull
    public Config getImplementationOptions() {
        return this.mRepeatingCaptureConfig.getImplementationOptions();
    }

    public int getTemplateType() {
        return this.mRepeatingCaptureConfig.getTemplateType();
    }

    @NonNull
    public List<CameraDevice.StateCallback> getDeviceStateCallbacks() {
        return this.mDeviceStateCallbacks;
    }

    @NonNull
    public List<CameraCaptureSession.StateCallback> getSessionStateCallbacks() {
        return this.mSessionStateCallbacks;
    }

    @NonNull
    public List<CameraCaptureCallback> getRepeatingCameraCaptureCallbacks() {
        return this.mRepeatingCaptureConfig.getCameraCaptureCallbacks();
    }

    @NonNull
    public List<ErrorListener> getErrorListeners() {
        return this.mErrorListeners;
    }

    @NonNull
    public List<CameraCaptureCallback> getSingleCameraCaptureCallbacks() {
        return this.mSingleCameraCaptureCallbacks;
    }

    @NonNull
    public CaptureConfig getRepeatingCaptureConfig() {
        return this.mRepeatingCaptureConfig;
    }

    public static class BaseBuilder {
        final CaptureConfig.Builder mCaptureConfigBuilder = new CaptureConfig.Builder();
        final List<CameraDevice.StateCallback> mDeviceStateCallbacks = new ArrayList();
        final List<ErrorListener> mErrorListeners = new ArrayList();
        final List<CameraCaptureSession.StateCallback> mSessionStateCallbacks = new ArrayList();
        final List<CameraCaptureCallback> mSingleCameraCaptureCallbacks = new ArrayList();
        final Set<DeferrableSurface> mSurfaces = new HashSet();

        BaseBuilder() {
        }
    }

    public static class Builder extends BaseBuilder {
        @NonNull
        public static Builder createFrom(@NonNull UseCaseConfig<?> config) {
            OptionUnpacker unpacker = config.getSessionOptionUnpacker((OptionUnpacker) null);
            if (unpacker != null) {
                Builder builder = new Builder();
                unpacker.unpack(config, builder);
                return builder;
            }
            throw new IllegalStateException("Implementation is missing option unpacker for " + config.getTargetName(config.toString()));
        }

        public void setTemplateType(int templateType) {
            this.mCaptureConfigBuilder.setTemplateType(templateType);
        }

        public void addTag(@NonNull String key, @NonNull Integer tag) {
            this.mCaptureConfigBuilder.addTag(key, tag);
        }

        public void addDeviceStateCallback(@NonNull CameraDevice.StateCallback deviceStateCallback) {
            if (!this.mDeviceStateCallbacks.contains(deviceStateCallback)) {
                this.mDeviceStateCallbacks.add(deviceStateCallback);
                return;
            }
            throw new IllegalArgumentException("Duplicate device state callback.");
        }

        public void addAllDeviceStateCallbacks(@NonNull Collection<CameraDevice.StateCallback> deviceStateCallbacks) {
            for (CameraDevice.StateCallback callback : deviceStateCallbacks) {
                addDeviceStateCallback(callback);
            }
        }

        public void addSessionStateCallback(@NonNull CameraCaptureSession.StateCallback sessionStateCallback) {
            if (!this.mSessionStateCallbacks.contains(sessionStateCallback)) {
                this.mSessionStateCallbacks.add(sessionStateCallback);
                return;
            }
            throw new IllegalArgumentException("Duplicate session state callback.");
        }

        public void addAllSessionStateCallbacks(@NonNull List<CameraCaptureSession.StateCallback> sessionStateCallbacks) {
            for (CameraCaptureSession.StateCallback callback : sessionStateCallbacks) {
                addSessionStateCallback(callback);
            }
        }

        public void addRepeatingCameraCaptureCallback(@NonNull CameraCaptureCallback cameraCaptureCallback) {
            this.mCaptureConfigBuilder.addCameraCaptureCallback(cameraCaptureCallback);
        }

        public void addAllRepeatingCameraCaptureCallbacks(@NonNull Collection<CameraCaptureCallback> cameraCaptureCallbacks) {
            this.mCaptureConfigBuilder.addAllCameraCaptureCallbacks(cameraCaptureCallbacks);
        }

        public void addCameraCaptureCallback(@NonNull CameraCaptureCallback cameraCaptureCallback) {
            this.mCaptureConfigBuilder.addCameraCaptureCallback(cameraCaptureCallback);
            this.mSingleCameraCaptureCallbacks.add(cameraCaptureCallback);
        }

        public void addAllCameraCaptureCallbacks(@NonNull Collection<CameraCaptureCallback> cameraCaptureCallbacks) {
            this.mCaptureConfigBuilder.addAllCameraCaptureCallbacks(cameraCaptureCallbacks);
            this.mSingleCameraCaptureCallbacks.addAll(cameraCaptureCallbacks);
        }

        @NonNull
        public List<CameraCaptureCallback> getSingleCameraCaptureCallbacks() {
            return Collections.unmodifiableList(this.mSingleCameraCaptureCallbacks);
        }

        public void addErrorListener(@NonNull ErrorListener errorListener) {
            this.mErrorListeners.add(errorListener);
        }

        public void addSurface(@NonNull DeferrableSurface surface) {
            this.mSurfaces.add(surface);
            this.mCaptureConfigBuilder.addSurface(surface);
        }

        public void addNonRepeatingSurface(@NonNull DeferrableSurface surface) {
            this.mSurfaces.add(surface);
        }

        public void removeSurface(@NonNull DeferrableSurface surface) {
            this.mSurfaces.remove(surface);
            this.mCaptureConfigBuilder.removeSurface(surface);
        }

        public void clearSurfaces() {
            this.mSurfaces.clear();
            this.mCaptureConfigBuilder.clearSurfaces();
        }

        public void setImplementationOptions(@NonNull Config config) {
            this.mCaptureConfigBuilder.setImplementationOptions(config);
        }

        public void addImplementationOptions(@NonNull Config config) {
            this.mCaptureConfigBuilder.addImplementationOptions(config);
        }

        @NonNull
        public SessionConfig build() {
            return new SessionConfig(new ArrayList(this.mSurfaces), this.mDeviceStateCallbacks, this.mSessionStateCallbacks, this.mSingleCameraCaptureCallbacks, this.mErrorListeners, this.mCaptureConfigBuilder.build());
        }
    }

    public static final class ValidatingBuilder extends BaseBuilder {
        private static final String TAG = "ValidatingBuilder";
        private boolean mTemplateSet = false;
        private boolean mValid = true;

        public void add(@NonNull SessionConfig sessionConfig) {
            CaptureConfig captureConfig = sessionConfig.getRepeatingCaptureConfig();
            if (captureConfig.getTemplateType() != -1) {
                if (!this.mTemplateSet) {
                    this.mCaptureConfigBuilder.setTemplateType(captureConfig.getTemplateType());
                    this.mTemplateSet = true;
                } else if (this.mCaptureConfigBuilder.getTemplateType() != captureConfig.getTemplateType()) {
                    Logger.d(TAG, "Invalid configuration due to template type: " + this.mCaptureConfigBuilder.getTemplateType() + " != " + captureConfig.getTemplateType());
                    this.mValid = false;
                }
            }
            this.mCaptureConfigBuilder.addAllTags(sessionConfig.getRepeatingCaptureConfig().getTagBundle());
            this.mDeviceStateCallbacks.addAll(sessionConfig.getDeviceStateCallbacks());
            this.mSessionStateCallbacks.addAll(sessionConfig.getSessionStateCallbacks());
            this.mCaptureConfigBuilder.addAllCameraCaptureCallbacks(sessionConfig.getRepeatingCameraCaptureCallbacks());
            this.mSingleCameraCaptureCallbacks.addAll(sessionConfig.getSingleCameraCaptureCallbacks());
            this.mErrorListeners.addAll(sessionConfig.getErrorListeners());
            this.mSurfaces.addAll(sessionConfig.getSurfaces());
            this.mCaptureConfigBuilder.getSurfaces().addAll(captureConfig.getSurfaces());
            if (!this.mSurfaces.containsAll(this.mCaptureConfigBuilder.getSurfaces())) {
                Logger.d(TAG, "Invalid configuration due to capture request surfaces are not a subset of surfaces");
                this.mValid = false;
            }
            this.mCaptureConfigBuilder.addImplementationOptions(captureConfig.getImplementationOptions());
        }

        public boolean isValid() {
            return this.mTemplateSet && this.mValid;
        }

        @NonNull
        public SessionConfig build() {
            if (this.mValid) {
                return new SessionConfig(new ArrayList(this.mSurfaces), this.mDeviceStateCallbacks, this.mSessionStateCallbacks, this.mSingleCameraCaptureCallbacks, this.mErrorListeners, this.mCaptureConfigBuilder.build());
            }
            throw new IllegalArgumentException("Unsupported session configuration combination");
        }
    }
}

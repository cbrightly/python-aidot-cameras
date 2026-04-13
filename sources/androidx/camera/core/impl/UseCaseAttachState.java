package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.SessionConfig;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class UseCaseAttachState {
    private static final String TAG = "UseCaseAttachState";
    private final Map<String, UseCaseAttachInfo> mAttachedUseCasesToInfoMap = new HashMap();
    private final String mCameraId;

    public interface AttachStateFilter {
        boolean filter(UseCaseAttachInfo useCaseAttachInfo);
    }

    public UseCaseAttachState(@NonNull String cameraId) {
        this.mCameraId = cameraId;
    }

    public void setUseCaseActive(@NonNull String useCaseName, @NonNull SessionConfig sessionConfig) {
        getOrCreateUseCaseAttachInfo(useCaseName, sessionConfig).setActive(true);
    }

    public void setUseCaseInactive(@NonNull String useCaseName) {
        if (this.mAttachedUseCasesToInfoMap.containsKey(useCaseName)) {
            UseCaseAttachInfo useCaseAttachInfo = this.mAttachedUseCasesToInfoMap.get(useCaseName);
            useCaseAttachInfo.setActive(false);
            if (!useCaseAttachInfo.getAttached()) {
                this.mAttachedUseCasesToInfoMap.remove(useCaseName);
            }
        }
    }

    public void setUseCaseAttached(@NonNull String useCaseName, @NonNull SessionConfig sessionConfig) {
        getOrCreateUseCaseAttachInfo(useCaseName, sessionConfig).setAttached(true);
    }

    public void setUseCaseDetached(@NonNull String useCaseName) {
        if (this.mAttachedUseCasesToInfoMap.containsKey(useCaseName)) {
            UseCaseAttachInfo useCaseAttachInfo = this.mAttachedUseCasesToInfoMap.get(useCaseName);
            useCaseAttachInfo.setAttached(false);
            if (!useCaseAttachInfo.getActive()) {
                this.mAttachedUseCasesToInfoMap.remove(useCaseName);
            }
        }
    }

    public boolean isUseCaseAttached(@NonNull String useCaseName) {
        if (!this.mAttachedUseCasesToInfoMap.containsKey(useCaseName)) {
            return false;
        }
        return this.mAttachedUseCasesToInfoMap.get(useCaseName).getAttached();
    }

    @NonNull
    public Collection<SessionConfig> getAttachedSessionConfigs() {
        return Collections.unmodifiableCollection(getSessionConfigs(r.a));
    }

    @NonNull
    public Collection<SessionConfig> getActiveAndAttachedSessionConfigs() {
        return Collections.unmodifiableCollection(getSessionConfigs(s.a));
    }

    static /* synthetic */ boolean lambda$getActiveAndAttachedSessionConfigs$1(UseCaseAttachInfo useCaseAttachInfo) {
        return useCaseAttachInfo.getActive() && useCaseAttachInfo.getAttached();
    }

    public void updateUseCase(@NonNull String useCaseName, @NonNull SessionConfig sessionConfig) {
        if (this.mAttachedUseCasesToInfoMap.containsKey(useCaseName)) {
            UseCaseAttachInfo newUseCaseAttachInfo = new UseCaseAttachInfo(sessionConfig);
            UseCaseAttachInfo oldUseCaseAttachInfo = this.mAttachedUseCasesToInfoMap.get(useCaseName);
            newUseCaseAttachInfo.setAttached(oldUseCaseAttachInfo.getAttached());
            newUseCaseAttachInfo.setActive(oldUseCaseAttachInfo.getActive());
            this.mAttachedUseCasesToInfoMap.put(useCaseName, newUseCaseAttachInfo);
        }
    }

    public void removeUseCase(@NonNull String useCaseName) {
        this.mAttachedUseCasesToInfoMap.remove(useCaseName);
    }

    @NonNull
    public SessionConfig.ValidatingBuilder getActiveAndAttachedBuilder() {
        SessionConfig.ValidatingBuilder validatingBuilder = new SessionConfig.ValidatingBuilder();
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, UseCaseAttachInfo> attachedUseCase : this.mAttachedUseCasesToInfoMap.entrySet()) {
            UseCaseAttachInfo useCaseAttachInfo = attachedUseCase.getValue();
            if (useCaseAttachInfo.getActive() && useCaseAttachInfo.getAttached()) {
                validatingBuilder.add(useCaseAttachInfo.getSessionConfig());
                list.add(attachedUseCase.getKey());
            }
        }
        Logger.d(TAG, "Active and attached use case: " + list + " for camera: " + this.mCameraId);
        return validatingBuilder;
    }

    @NonNull
    public SessionConfig.ValidatingBuilder getAttachedBuilder() {
        SessionConfig.ValidatingBuilder validatingBuilder = new SessionConfig.ValidatingBuilder();
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, UseCaseAttachInfo> attachedUseCase : this.mAttachedUseCasesToInfoMap.entrySet()) {
            UseCaseAttachInfo useCaseAttachInfo = attachedUseCase.getValue();
            if (useCaseAttachInfo.getAttached()) {
                validatingBuilder.add(useCaseAttachInfo.getSessionConfig());
                list.add(attachedUseCase.getKey());
            }
        }
        Logger.d(TAG, "All use case: " + list + " for camera: " + this.mCameraId);
        return validatingBuilder;
    }

    private UseCaseAttachInfo getOrCreateUseCaseAttachInfo(@NonNull String useCaseName, @NonNull SessionConfig sessionConfig) {
        UseCaseAttachInfo useCaseAttachInfo = this.mAttachedUseCasesToInfoMap.get(useCaseName);
        if (useCaseAttachInfo != null) {
            return useCaseAttachInfo;
        }
        UseCaseAttachInfo useCaseAttachInfo2 = new UseCaseAttachInfo(sessionConfig);
        this.mAttachedUseCasesToInfoMap.put(useCaseName, useCaseAttachInfo2);
        return useCaseAttachInfo2;
    }

    private Collection<SessionConfig> getSessionConfigs(AttachStateFilter attachStateFilter) {
        List<SessionConfig> sessionConfigs = new ArrayList<>();
        for (Map.Entry<String, UseCaseAttachInfo> attachedUseCase : this.mAttachedUseCasesToInfoMap.entrySet()) {
            if (attachStateFilter == null || attachStateFilter.filter(attachedUseCase.getValue())) {
                sessionConfigs.add(attachedUseCase.getValue().getSessionConfig());
            }
        }
        return sessionConfigs;
    }

    public static final class UseCaseAttachInfo {
        private boolean mActive = false;
        private boolean mAttached = false;
        @NonNull
        private final SessionConfig mSessionConfig;

        UseCaseAttachInfo(@NonNull SessionConfig sessionConfig) {
            this.mSessionConfig = sessionConfig;
        }

        /* access modifiers changed from: package-private */
        @NonNull
        public SessionConfig getSessionConfig() {
            return this.mSessionConfig;
        }

        /* access modifiers changed from: package-private */
        public boolean getAttached() {
            return this.mAttached;
        }

        /* access modifiers changed from: package-private */
        public void setAttached(boolean attached) {
            this.mAttached = attached;
        }

        /* access modifiers changed from: package-private */
        public boolean getActive() {
            return this.mActive;
        }

        /* access modifiers changed from: package-private */
        public void setActive(boolean active) {
            this.mActive = active;
        }
    }
}

package androidx.camera.core.impl;

import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.camera.core.Camera;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.CameraInternal;
import androidx.core.util.Preconditions;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

public final class CameraStateRegistry {
    private static final String TAG = "CameraStateRegistry";
    @GuardedBy("mLock")
    private int mAvailableCameras;
    @GuardedBy("mLock")
    private final Map<Camera, CameraRegistration> mCameraStates = new HashMap();
    private final StringBuilder mDebugString = new StringBuilder();
    private final Object mLock = new Object();
    private final int mMaxAllowedOpenedCameras;

    public interface OnOpenAvailableListener {
        void onOpenAvailable();
    }

    public CameraStateRegistry(int maxAllowedOpenedCameras) {
        this.mMaxAllowedOpenedCameras = maxAllowedOpenedCameras;
        synchronized ("mLock") {
            this.mAvailableCameras = maxAllowedOpenedCameras;
        }
    }

    public void registerCamera(@NonNull Camera camera, @NonNull Executor notifyExecutor, @NonNull OnOpenAvailableListener cameraAvailableListener) {
        synchronized (this.mLock) {
            boolean z = !this.mCameraStates.containsKey(camera);
            Preconditions.checkState(z, "Camera is already registered: " + camera);
            this.mCameraStates.put(camera, new CameraRegistration((CameraInternal.State) null, notifyExecutor, cameraAvailableListener));
        }
    }

    public boolean tryOpenCamera(@NonNull Camera camera) {
        boolean success;
        synchronized (this.mLock) {
            CameraRegistration registration = (CameraRegistration) Preconditions.checkNotNull(this.mCameraStates.get(camera), "Camera must first be registered with registerCamera()");
            success = false;
            if (Logger.isDebugEnabled(TAG)) {
                this.mDebugString.setLength(0);
                this.mDebugString.append(String.format(Locale.US, "tryOpenCamera(%s) [Available Cameras: %d, Already Open: %b (Previous state: %s)]", new Object[]{camera, Integer.valueOf(this.mAvailableCameras), Boolean.valueOf(isOpen(registration.getState())), registration.getState()}));
            }
            if (this.mAvailableCameras > 0 || isOpen(registration.getState())) {
                registration.setState(CameraInternal.State.OPENING);
                success = true;
            }
            if (Logger.isDebugEnabled(TAG)) {
                StringBuilder sb = this.mDebugString;
                Locale locale = Locale.US;
                Object[] objArr = new Object[1];
                objArr[0] = success ? "SUCCESS" : "FAIL";
                sb.append(String.format(locale, " --> %s", objArr));
                Logger.d(TAG, this.mDebugString.toString());
            }
            if (success) {
                recalculateAvailableCameras();
            }
        }
        return success;
    }

    public void markCameraState(@NonNull Camera camera, @NonNull CameraInternal.State state) {
        markCameraState(camera, state, true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x007d, code lost:
        if (r0 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x007f, code lost:
        r1 = r0.values().iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x008b, code lost:
        if (r1.hasNext() == false) goto L_0x0097;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x008d, code lost:
        r1.next().notifyListener();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void markCameraState(@androidx.annotation.NonNull androidx.camera.core.Camera r9, @androidx.annotation.NonNull androidx.camera.core.impl.CameraInternal.State r10, boolean r11) {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object r1 = r8.mLock
            monitor-enter(r1)
            r2 = 0
            int r3 = r8.mAvailableCameras     // Catch:{ all -> 0x0098 }
            androidx.camera.core.impl.CameraInternal$State r4 = androidx.camera.core.impl.CameraInternal.State.RELEASED     // Catch:{ all -> 0x0098 }
            if (r10 != r4) goto L_0x0011
            androidx.camera.core.impl.CameraInternal$State r4 = r8.unregisterCamera(r9)     // Catch:{ all -> 0x0098 }
            r2 = r4
            goto L_0x0016
        L_0x0011:
            androidx.camera.core.impl.CameraInternal$State r4 = r8.updateAndVerifyState(r9, r10)     // Catch:{ all -> 0x0098 }
            r2 = r4
        L_0x0016:
            if (r2 != r10) goto L_0x001a
            monitor-exit(r1)     // Catch:{ all -> 0x0098 }
            return
        L_0x001a:
            r4 = 1
            if (r3 >= r4) goto L_0x005c
            int r4 = r8.mAvailableCameras     // Catch:{ all -> 0x0098 }
            if (r4 <= 0) goto L_0x005c
            java.util.HashMap r4 = new java.util.HashMap     // Catch:{ all -> 0x0098 }
            r4.<init>()     // Catch:{ all -> 0x0098 }
            r0 = r4
            java.util.Map<androidx.camera.core.Camera, androidx.camera.core.impl.CameraStateRegistry$CameraRegistration> r4 = r8.mCameraStates     // Catch:{ all -> 0x0098 }
            java.util.Set r4 = r4.entrySet()     // Catch:{ all -> 0x0098 }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ all -> 0x0098 }
        L_0x0031:
            boolean r5 = r4.hasNext()     // Catch:{ all -> 0x0098 }
            if (r5 == 0) goto L_0x005b
            java.lang.Object r5 = r4.next()     // Catch:{ all -> 0x0098 }
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5     // Catch:{ all -> 0x0098 }
            java.lang.Object r6 = r5.getValue()     // Catch:{ all -> 0x0098 }
            androidx.camera.core.impl.CameraStateRegistry$CameraRegistration r6 = (androidx.camera.core.impl.CameraStateRegistry.CameraRegistration) r6     // Catch:{ all -> 0x0098 }
            androidx.camera.core.impl.CameraInternal$State r6 = r6.getState()     // Catch:{ all -> 0x0098 }
            androidx.camera.core.impl.CameraInternal$State r7 = androidx.camera.core.impl.CameraInternal.State.PENDING_OPEN     // Catch:{ all -> 0x0098 }
            if (r6 != r7) goto L_0x005a
            java.lang.Object r6 = r5.getKey()     // Catch:{ all -> 0x0098 }
            androidx.camera.core.Camera r6 = (androidx.camera.core.Camera) r6     // Catch:{ all -> 0x0098 }
            java.lang.Object r7 = r5.getValue()     // Catch:{ all -> 0x0098 }
            androidx.camera.core.impl.CameraStateRegistry$CameraRegistration r7 = (androidx.camera.core.impl.CameraStateRegistry.CameraRegistration) r7     // Catch:{ all -> 0x0098 }
            r0.put(r6, r7)     // Catch:{ all -> 0x0098 }
        L_0x005a:
            goto L_0x0031
        L_0x005b:
            goto L_0x0075
        L_0x005c:
            androidx.camera.core.impl.CameraInternal$State r4 = androidx.camera.core.impl.CameraInternal.State.PENDING_OPEN     // Catch:{ all -> 0x0098 }
            if (r10 != r4) goto L_0x0075
            int r4 = r8.mAvailableCameras     // Catch:{ all -> 0x0098 }
            if (r4 <= 0) goto L_0x0075
            java.util.HashMap r4 = new java.util.HashMap     // Catch:{ all -> 0x0098 }
            r4.<init>()     // Catch:{ all -> 0x0098 }
            r0 = r4
            java.util.Map<androidx.camera.core.Camera, androidx.camera.core.impl.CameraStateRegistry$CameraRegistration> r4 = r8.mCameraStates     // Catch:{ all -> 0x0098 }
            java.lang.Object r4 = r4.get(r9)     // Catch:{ all -> 0x0098 }
            androidx.camera.core.impl.CameraStateRegistry$CameraRegistration r4 = (androidx.camera.core.impl.CameraStateRegistry.CameraRegistration) r4     // Catch:{ all -> 0x0098 }
            r0.put(r9, r4)     // Catch:{ all -> 0x0098 }
        L_0x0075:
            if (r0 == 0) goto L_0x007c
            if (r11 != 0) goto L_0x007c
            r0.remove(r9)     // Catch:{ all -> 0x0098 }
        L_0x007c:
            monitor-exit(r1)     // Catch:{ all -> 0x0098 }
            if (r0 == 0) goto L_0x0097
            java.util.Collection r1 = r0.values()
            java.util.Iterator r1 = r1.iterator()
        L_0x0087:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0097
            java.lang.Object r2 = r1.next()
            androidx.camera.core.impl.CameraStateRegistry$CameraRegistration r2 = (androidx.camera.core.impl.CameraStateRegistry.CameraRegistration) r2
            r2.notifyListener()
            goto L_0x0087
        L_0x0097:
            return
        L_0x0098:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0098 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.impl.CameraStateRegistry.markCameraState(androidx.camera.core.Camera, androidx.camera.core.impl.CameraInternal$State, boolean):void");
    }

    @GuardedBy("mLock")
    @Nullable
    private CameraInternal.State unregisterCamera(Camera camera) {
        CameraRegistration registration = this.mCameraStates.remove(camera);
        if (registration == null) {
            return null;
        }
        recalculateAvailableCameras();
        return registration.getState();
    }

    @GuardedBy("mLock")
    @Nullable
    private CameraInternal.State updateAndVerifyState(@NonNull Camera camera, @NonNull CameraInternal.State state) {
        CameraInternal.State previousState = ((CameraRegistration) Preconditions.checkNotNull(this.mCameraStates.get(camera), "Cannot update state of camera which has not yet been registered. Register with CameraStateRegistry.registerCamera()")).setState(state);
        CameraInternal.State state2 = CameraInternal.State.OPENING;
        if (state == state2) {
            Preconditions.checkState(isOpen(state) || previousState == state2, "Cannot mark camera as opening until camera was successful at calling CameraStateRegistry.tryOpenCamera()");
        }
        if (previousState != state) {
            recalculateAvailableCameras();
        }
        return previousState;
    }

    private static boolean isOpen(@Nullable CameraInternal.State state) {
        return state != null && state.holdsCameraSlot();
    }

    @WorkerThread
    @GuardedBy("mLock")
    private void recalculateAvailableCameras() {
        String stateString;
        if (Logger.isDebugEnabled(TAG)) {
            this.mDebugString.setLength(0);
            this.mDebugString.append("Recalculating open cameras:\n");
            this.mDebugString.append(String.format(Locale.US, "%-45s%-22s\n", new Object[]{"Camera", "State"}));
            this.mDebugString.append("-------------------------------------------------------------------\n");
        }
        int openCount = 0;
        for (Map.Entry<Camera, CameraRegistration> entry : this.mCameraStates.entrySet()) {
            if (Logger.isDebugEnabled(TAG)) {
                if (entry.getValue().getState() != null) {
                    stateString = entry.getValue().getState().toString();
                } else {
                    stateString = LDNetUtil.NETWORKTYPE_INVALID;
                }
                this.mDebugString.append(String.format(Locale.US, "%-45s%-22s\n", new Object[]{entry.getKey().toString(), stateString}));
            }
            if (isOpen(entry.getValue().getState())) {
                openCount++;
            }
        }
        if (Logger.isDebugEnabled(TAG)) {
            this.mDebugString.append("-------------------------------------------------------------------\n");
            this.mDebugString.append(String.format(Locale.US, "Open count: %d (Max allowed: %d)", new Object[]{Integer.valueOf(openCount), Integer.valueOf(this.mMaxAllowedOpenedCameras)}));
            Logger.d(TAG, this.mDebugString.toString());
        }
        this.mAvailableCameras = Math.max(this.mMaxAllowedOpenedCameras - openCount, 0);
    }

    public boolean isCameraClosing() {
        synchronized (this.mLock) {
            for (Map.Entry<Camera, CameraRegistration> entry : this.mCameraStates.entrySet()) {
                if (entry.getValue().getState() == CameraInternal.State.CLOSING) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class CameraRegistration {
        private final OnOpenAvailableListener mCameraAvailableListener;
        private final Executor mNotifyExecutor;
        private CameraInternal.State mState;

        CameraRegistration(@Nullable CameraInternal.State initialState, @NonNull Executor notifyExecutor, @NonNull OnOpenAvailableListener cameraAvailableListener) {
            this.mState = initialState;
            this.mNotifyExecutor = notifyExecutor;
            this.mCameraAvailableListener = cameraAvailableListener;
        }

        /* access modifiers changed from: package-private */
        public CameraInternal.State setState(@Nullable CameraInternal.State state) {
            CameraInternal.State previousState = this.mState;
            this.mState = state;
            return previousState;
        }

        /* access modifiers changed from: package-private */
        public CameraInternal.State getState() {
            return this.mState;
        }

        /* access modifiers changed from: package-private */
        public void notifyListener() {
            try {
                Executor executor = this.mNotifyExecutor;
                OnOpenAvailableListener onOpenAvailableListener = this.mCameraAvailableListener;
                Objects.requireNonNull(onOpenAvailableListener);
                executor.execute(new t(onOpenAvailableListener));
            } catch (RejectedExecutionException e) {
                Logger.e(CameraStateRegistry.TAG, "Unable to notify camera.", e);
            }
        }
    }
}

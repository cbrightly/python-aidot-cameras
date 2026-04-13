package androidx.camera.core;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import androidx.annotation.GuardedBy;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.annotation.RestrictTo;
import androidx.camera.core.CameraXConfig;
import androidx.camera.core.impl.CameraDeviceSurfaceManager;
import androidx.camera.core.impl.CameraFactory;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CameraRepository;
import androidx.camera.core.impl.CameraThreadConfig;
import androidx.camera.core.impl.CameraValidator;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.FutureChain;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.core.internal.compat.quirk.DeviceQuirks;
import androidx.camera.core.internal.compat.quirk.IncompleteCameraListQuirk;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.os.HandlerCompat;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@MainThread
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class CameraX {
    static final Object INSTANCE_LOCK = new Object();
    private static final long RETRY_SLEEP_MILLIS = 500;
    private static final String RETRY_TOKEN = "retry_token";
    private static final String TAG = "CameraX";
    private static final long WAIT_INITIALIZED_TIMEOUT_MILLIS = 3000;
    @GuardedBy("INSTANCE_LOCK")
    private static CameraXConfig.Provider sConfigProvider = null;
    @GuardedBy("INSTANCE_LOCK")
    private static ListenableFuture<Void> sInitializeFuture = Futures.immediateFailedFuture(new IllegalStateException("CameraX is not initialized."));
    @GuardedBy("INSTANCE_LOCK")
    static CameraX sInstance = null;
    @GuardedBy("INSTANCE_LOCK")
    private static ListenableFuture<Void> sShutdownFuture = Futures.immediateFuture(null);
    private Context mAppContext;
    private final Executor mCameraExecutor;
    private CameraFactory mCameraFactory;
    final CameraRepository mCameraRepository = new CameraRepository();
    private final CameraXConfig mCameraXConfig;
    private UseCaseConfigFactory mDefaultConfigFactory;
    @GuardedBy("mInitializeLock")
    private InternalInitState mInitState = InternalInitState.UNINITIALIZED;
    private final Object mInitializeLock = new Object();
    private final Handler mSchedulerHandler;
    @Nullable
    private final HandlerThread mSchedulerThread;
    @GuardedBy("mInitializeLock")
    private ListenableFuture<Void> mShutdownInternalFuture = Futures.immediateFuture(null);
    private CameraDeviceSurfaceManager mSurfaceManager;

    public enum InternalInitState {
        UNINITIALIZED,
        INITIALIZING,
        INITIALIZED,
        SHUTDOWN
    }

    CameraX(@NonNull CameraXConfig cameraXConfig) {
        this.mCameraXConfig = (CameraXConfig) Preconditions.checkNotNull(cameraXConfig);
        Executor executor = cameraXConfig.getCameraExecutor((Executor) null);
        Handler schedulerHandler = cameraXConfig.getSchedulerHandler((Handler) null);
        this.mCameraExecutor = executor == null ? new CameraExecutor() : executor;
        if (schedulerHandler == null) {
            HandlerThread handlerThread = new HandlerThread("CameraX-scheduler", 10);
            this.mSchedulerThread = handlerThread;
            handlerThread.start();
            this.mSchedulerHandler = HandlerCompat.createAsync(handlerThread.getLooper());
            return;
        }
        this.mSchedulerThread = null;
        this.mSchedulerHandler = schedulerHandler;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static CameraInternal getCameraWithCameraSelector(@NonNull CameraSelector cameraSelector) {
        return cameraSelector.select(checkInitialized().getCameraRepository().getCameras());
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.TESTS})
    public static ListenableFuture<Void> initialize(@NonNull Context context, @NonNull CameraXConfig cameraXConfig) {
        ListenableFuture<Void> listenableFuture;
        synchronized (INSTANCE_LOCK) {
            Preconditions.checkNotNull(context);
            configureInstanceLocked(new i(cameraXConfig));
            initializeInstanceLocked(context);
            listenableFuture = sInitializeFuture;
        }
        return listenableFuture;
    }

    static /* synthetic */ CameraXConfig lambda$initialize$0(CameraXConfig cameraXConfig) {
        return cameraXConfig;
    }

    public static void configureInstance(@NonNull CameraXConfig cameraXConfig) {
        synchronized (INSTANCE_LOCK) {
            configureInstanceLocked(new h(cameraXConfig));
        }
    }

    static /* synthetic */ CameraXConfig lambda$configureInstance$1(CameraXConfig cameraXConfig) {
        return cameraXConfig;
    }

    @GuardedBy("INSTANCE_LOCK")
    private static void configureInstanceLocked(@NonNull CameraXConfig.Provider configProvider) {
        Preconditions.checkNotNull(configProvider);
        Preconditions.checkState(sConfigProvider == null, "CameraX has already been configured. To use a different configuration, shutdown() must be called.");
        sConfigProvider = configProvider;
        Integer minLogLevel = (Integer) configProvider.getCameraXConfig().retrieveOption(CameraXConfig.OPTION_MIN_LOGGING_LEVEL, null);
        if (minLogLevel != null) {
            Logger.setMinLogLevel(minLogLevel.intValue());
        }
    }

    @GuardedBy("INSTANCE_LOCK")
    private static void initializeInstanceLocked(@NonNull Context context) {
        Preconditions.checkNotNull(context);
        Preconditions.checkState(sInstance == null, "CameraX already initialized.");
        Preconditions.checkNotNull(sConfigProvider);
        CameraX cameraX = new CameraX(sConfigProvider.getCameraXConfig());
        sInstance = cameraX;
        sInitializeFuture = CallbackToFutureAdapter.getFuture(new f(cameraX, context));
    }

    static /* synthetic */ Object lambda$initializeInstanceLocked$3(final CameraX cameraX, Context context, final CallbackToFutureAdapter.Completer completer) {
        synchronized (INSTANCE_LOCK) {
            Futures.addCallback(FutureChain.from(sShutdownFuture).transformAsync(new j(cameraX, context), CameraXExecutors.directExecutor()), new FutureCallback<Void>() {
                public void onSuccess(@Nullable Void result) {
                    CallbackToFutureAdapter.Completer.this.set(null);
                }

                public void onFailure(Throwable t) {
                    Logger.w(CameraX.TAG, "CameraX initialize() failed", t);
                    synchronized (CameraX.INSTANCE_LOCK) {
                        if (CameraX.sInstance == cameraX) {
                            CameraX.shutdownLocked();
                        }
                    }
                    CallbackToFutureAdapter.Completer.this.setException(t);
                }
            }, CameraXExecutors.directExecutor());
        }
        return "CameraX-initialize";
    }

    @NonNull
    public static ListenableFuture<Void> shutdown() {
        ListenableFuture<Void> shutdownLocked;
        synchronized (INSTANCE_LOCK) {
            sConfigProvider = null;
            Logger.resetMinLogLevel();
            shutdownLocked = shutdownLocked();
        }
        return shutdownLocked;
    }

    @GuardedBy("INSTANCE_LOCK")
    @NonNull
    static ListenableFuture<Void> shutdownLocked() {
        if (sInstance == null) {
            return sShutdownFuture;
        }
        CameraX cameraX = sInstance;
        sInstance = null;
        ListenableFuture<Void> nonCancellationPropagating = Futures.nonCancellationPropagating(CallbackToFutureAdapter.getFuture(new n(cameraX)));
        sShutdownFuture = nonCancellationPropagating;
        return nonCancellationPropagating;
    }

    static /* synthetic */ Object lambda$shutdownLocked$5(CameraX cameraX, CallbackToFutureAdapter.Completer completer) {
        synchronized (INSTANCE_LOCK) {
            sInitializeFuture.addListener(new m(cameraX, completer), CameraXExecutors.directExecutor());
        }
        return "CameraX shutdown";
    }

    @NonNull
    @Deprecated
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Context getContext() {
        return checkInitialized().mAppContext;
    }

    @RestrictTo({RestrictTo.Scope.TESTS})
    public static boolean isInitialized() {
        boolean z;
        synchronized (INSTANCE_LOCK) {
            CameraX cameraX = sInstance;
            z = cameraX != null && cameraX.isInitializedInternal();
        }
        return z;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public CameraFactory getCameraFactory() {
        CameraFactory cameraFactory = this.mCameraFactory;
        if (cameraFactory != null) {
            return cameraFactory;
        }
        throw new IllegalStateException("CameraX not initialized yet.");
    }

    @NonNull
    private static CameraX checkInitialized() {
        CameraX cameraX = waitInitialized();
        Preconditions.checkState(cameraX.isInitializedInternal(), "Must call CameraX.initialize() first");
        return cameraX;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static ListenableFuture<CameraX> getOrCreateInstance(@NonNull Context context) {
        ListenableFuture<CameraX> instanceFuture;
        Preconditions.checkNotNull(context, "Context must not be null.");
        synchronized (INSTANCE_LOCK) {
            boolean isConfigured = sConfigProvider != null;
            instanceFuture = getInstanceLocked();
            if (instanceFuture.isDone()) {
                try {
                    instanceFuture.get();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Unexpected thread interrupt. Should not be possible since future is already complete.", e);
                } catch (ExecutionException e2) {
                    shutdownLocked();
                    instanceFuture = null;
                }
            }
            if (instanceFuture == null) {
                if (!isConfigured) {
                    CameraXConfig.Provider configProvider = getConfigProvider(context);
                    if (configProvider != null) {
                        configureInstanceLocked(configProvider);
                    } else {
                        throw new IllegalStateException("CameraX is not configured properly. The most likely cause is you did not include a default implementation in your build such as 'camera-camera2'.");
                    }
                }
                initializeInstanceLocked(context);
                instanceFuture = getInstanceLocked();
            }
        }
        return instanceFuture;
    }

    @Nullable
    private static CameraXConfig.Provider getConfigProvider(@NonNull Context context) {
        Application application = getApplicationFromContext(context);
        if (application instanceof CameraXConfig.Provider) {
            return (CameraXConfig.Provider) application;
        }
        try {
            return (CameraXConfig.Provider) Class.forName(context.getApplicationContext().getResources().getString(R.string.androidx_camera_default_config_provider)).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Resources.NotFoundException | ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | NullPointerException | InvocationTargetException e) {
            Logger.e(TAG, "Failed to retrieve default CameraXConfig.Provider from resources", e);
            return null;
        }
    }

    @Nullable
    private static Application getApplicationFromContext(@NonNull Context context) {
        for (Context appContext = context.getApplicationContext(); appContext instanceof ContextWrapper; appContext = ((ContextWrapper) appContext).getBaseContext()) {
            if (appContext instanceof Application) {
                return (Application) appContext;
            }
        }
        return null;
    }

    @NonNull
    private static ListenableFuture<CameraX> getInstance() {
        ListenableFuture<CameraX> instanceLocked;
        synchronized (INSTANCE_LOCK) {
            instanceLocked = getInstanceLocked();
        }
        return instanceLocked;
    }

    @GuardedBy("INSTANCE_LOCK")
    @NonNull
    private static ListenableFuture<CameraX> getInstanceLocked() {
        CameraX cameraX = sInstance;
        if (cameraX == null) {
            return Futures.immediateFailedFuture(new IllegalStateException("Must call CameraX.initialize() first"));
        }
        return Futures.transform(sInitializeFuture, new e(cameraX), CameraXExecutors.directExecutor());
    }

    static /* synthetic */ CameraX lambda$getInstanceLocked$6(CameraX cameraX, Void nullVoid) {
        return cameraX;
    }

    @NonNull
    private static CameraX waitInitialized() {
        try {
            return getInstance().get(3000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new IllegalStateException(e);
        }
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public CameraDeviceSurfaceManager getCameraDeviceSurfaceManager() {
        CameraDeviceSurfaceManager cameraDeviceSurfaceManager = this.mSurfaceManager;
        if (cameraDeviceSurfaceManager != null) {
            return cameraDeviceSurfaceManager;
        }
        throw new IllegalStateException("CameraX not initialized yet.");
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public CameraRepository getCameraRepository() {
        return this.mCameraRepository;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseConfigFactory getDefaultConfigFactory() {
        UseCaseConfigFactory useCaseConfigFactory = this.mDefaultConfigFactory;
        if (useCaseConfigFactory != null) {
            return useCaseConfigFactory;
        }
        throw new IllegalStateException("CameraX not initialized yet.");
    }

    /* access modifiers changed from: private */
    public ListenableFuture<Void> initInternal(@NonNull Context context) {
        ListenableFuture<Void> future;
        synchronized (this.mInitializeLock) {
            Preconditions.checkState(this.mInitState == InternalInitState.UNINITIALIZED, "CameraX.initInternal() should only be called once per instance");
            this.mInitState = InternalInitState.INITIALIZING;
            future = CallbackToFutureAdapter.getFuture(new d(this, context));
        }
        return future;
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$initInternal$7 */
    public /* synthetic */ Object c(Context context, CallbackToFutureAdapter.Completer completer) {
        initAndRetryRecursively(this.mCameraExecutor, SystemClock.elapsedRealtime(), context, completer);
        return "CameraX initInternal";
    }

    @OptIn(markerClass = {ExperimentalAvailableCamerasLimiter.class})
    private void initAndRetryRecursively(@NonNull Executor cameraExecutor, long startMs, @NonNull Context context, @NonNull CallbackToFutureAdapter.Completer<Void> completer) {
        cameraExecutor.execute(new l(this, context, cameraExecutor, completer, startMs));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$initAndRetryRecursively$9 */
    public /* synthetic */ void b(Context context, Executor cameraExecutor, CallbackToFutureAdapter.Completer completer, long startMs) {
        try {
            Application applicationFromContext = getApplicationFromContext(context);
            this.mAppContext = applicationFromContext;
            if (applicationFromContext == null) {
                this.mAppContext = context.getApplicationContext();
            }
            CameraFactory.Provider cameraFactoryProvider = this.mCameraXConfig.getCameraFactoryProvider((CameraFactory.Provider) null);
            if (cameraFactoryProvider != null) {
                CameraThreadConfig cameraThreadConfig = CameraThreadConfig.create(this.mCameraExecutor, this.mSchedulerHandler);
                CameraSelector availableCamerasLimiter = this.mCameraXConfig.getAvailableCamerasLimiter((CameraSelector) null);
                this.mCameraFactory = cameraFactoryProvider.newInstance(this.mAppContext, cameraThreadConfig, availableCamerasLimiter);
                CameraDeviceSurfaceManager.Provider surfaceManagerProvider = this.mCameraXConfig.getDeviceSurfaceManagerProvider((CameraDeviceSurfaceManager.Provider) null);
                if (surfaceManagerProvider != null) {
                    this.mSurfaceManager = surfaceManagerProvider.newInstance(this.mAppContext, this.mCameraFactory.getCameraManager(), this.mCameraFactory.getAvailableCameraIds());
                    UseCaseConfigFactory.Provider configFactoryProvider = this.mCameraXConfig.getUseCaseConfigFactoryProvider((UseCaseConfigFactory.Provider) null);
                    if (configFactoryProvider != null) {
                        this.mDefaultConfigFactory = configFactoryProvider.newInstance(this.mAppContext);
                        if (cameraExecutor instanceof CameraExecutor) {
                            ((CameraExecutor) cameraExecutor).init(this.mCameraFactory);
                        }
                        this.mCameraRepository.init(this.mCameraFactory);
                        if (DeviceQuirks.get(IncompleteCameraListQuirk.class) != null) {
                            CameraValidator.validateCameras(this.mAppContext, this.mCameraRepository, availableCamerasLimiter);
                        }
                        setStateToInitialized();
                        completer.set(null);
                        return;
                    }
                    throw new InitializationException((Throwable) new IllegalArgumentException("Invalid app configuration provided. Missing UseCaseConfigFactory."));
                }
                throw new InitializationException((Throwable) new IllegalArgumentException("Invalid app configuration provided. Missing CameraDeviceSurfaceManager."));
            }
            throw new InitializationException((Throwable) new IllegalArgumentException("Invalid app configuration provided. Missing CameraFactory."));
        } catch (InitializationException | CameraValidator.CameraIdListIncorrectException | RuntimeException e) {
            if (SystemClock.elapsedRealtime() - startMs < 2500) {
                Logger.w(TAG, "Retry init. Start time " + startMs + " current time " + SystemClock.elapsedRealtime(), e);
                HandlerCompat.postDelayed(this.mSchedulerHandler, new g(this, cameraExecutor, startMs, completer), RETRY_TOKEN, RETRY_SLEEP_MILLIS);
                return;
            }
            setStateToInitialized();
            if (e instanceof CameraValidator.CameraIdListIncorrectException) {
                Logger.e(TAG, "The device might underreport the amount of the cameras. Finish the initialize task since we are already reaching the maximum number of retries.");
                completer.set(null);
            } else if (e instanceof InitializationException) {
                completer.setException(e);
            } else {
                completer.setException(new InitializationException((Throwable) e));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$initAndRetryRecursively$8 */
    public /* synthetic */ void a(Executor cameraExecutor, long startMs, CallbackToFutureAdapter.Completer completer) {
        initAndRetryRecursively(cameraExecutor, startMs, this.mAppContext, completer);
    }

    private void setStateToInitialized() {
        synchronized (this.mInitializeLock) {
            this.mInitState = InternalInitState.INITIALIZED;
        }
    }

    @NonNull
    private ListenableFuture<Void> shutdownInternal() {
        synchronized (this.mInitializeLock) {
            this.mSchedulerHandler.removeCallbacksAndMessages(RETRY_TOKEN);
            switch (AnonymousClass2.$SwitchMap$androidx$camera$core$CameraX$InternalInitState[this.mInitState.ordinal()]) {
                case 1:
                    this.mInitState = InternalInitState.SHUTDOWN;
                    ListenableFuture<Void> immediateFuture = Futures.immediateFuture(null);
                    return immediateFuture;
                case 2:
                    throw new IllegalStateException("CameraX could not be shutdown when it is initializing.");
                case 3:
                    this.mInitState = InternalInitState.SHUTDOWN;
                    this.mShutdownInternalFuture = CallbackToFutureAdapter.getFuture(new o(this));
                    break;
            }
            ListenableFuture<Void> listenableFuture = this.mShutdownInternalFuture;
            return listenableFuture;
        }
    }

    /* renamed from: androidx.camera.core.CameraX$2  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$core$CameraX$InternalInitState;

        static {
            int[] iArr = new int[InternalInitState.values().length];
            $SwitchMap$androidx$camera$core$CameraX$InternalInitState = iArr;
            try {
                iArr[InternalInitState.UNINITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$camera$core$CameraX$InternalInitState[InternalInitState.INITIALIZING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$camera$core$CameraX$InternalInitState[InternalInitState.INITIALIZED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$androidx$camera$core$CameraX$InternalInitState[InternalInitState.SHUTDOWN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$shutdownInternal$11 */
    public /* synthetic */ Object e(CallbackToFutureAdapter.Completer completer) {
        this.mCameraRepository.deinit().addListener(new k(this, completer), this.mCameraExecutor);
        return "CameraX shutdownInternal";
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$shutdownInternal$10 */
    public /* synthetic */ void d(CallbackToFutureAdapter.Completer completer) {
        if (this.mSchedulerThread != null) {
            Executor executor = this.mCameraExecutor;
            if (executor instanceof CameraExecutor) {
                ((CameraExecutor) executor).deinit();
            }
            this.mSchedulerThread.quit();
            completer.set(null);
        }
    }

    private boolean isInitializedInternal() {
        boolean z;
        synchronized (this.mInitializeLock) {
            z = this.mInitState == InternalInitState.INITIALIZED;
        }
        return z;
    }
}

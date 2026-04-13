package com.google.firebase.installations;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.components.Lazy;
import com.google.firebase.heartbeatinfo.HeartBeatController;
import com.google.firebase.inject.Provider;
import com.google.firebase.installations.FirebaseInstallationsException;
import com.google.firebase.installations.internal.FidListener;
import com.google.firebase.installations.internal.FidListenerHandle;
import com.google.firebase.installations.local.IidStore;
import com.google.firebase.installations.local.PersistedInstallation;
import com.google.firebase.installations.local.PersistedInstallationEntry;
import com.google.firebase.installations.remote.FirebaseInstallationServiceClient;
import com.google.firebase.installations.remote.InstallationResponse;
import com.google.firebase.installations.remote.TokenResult;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class FirebaseInstallations implements FirebaseInstallationsApi {
    private static final String API_KEY_VALIDATION_MSG = "Please set a valid API key. A Firebase API key is required to communicate with Firebase server APIs: It authenticates your project with Google.Please refer to https://firebase.google.com/support/privacy/init-options.";
    private static final String APP_ID_VALIDATION_MSG = "Please set your Application ID. A valid Firebase App ID is required to communicate with Firebase server APIs: It identifies your application with Firebase.Please refer to https://firebase.google.com/support/privacy/init-options.";
    private static final String AUTH_ERROR_MSG = "Installation ID could not be validated with the Firebase servers (maybe it was deleted). Firebase Installations will need to create a new Installation ID and auth token. Please retry your last request.";
    private static final String CHIME_FIREBASE_APP_NAME = "CHIME_ANDROID_SDK";
    private static final int CORE_POOL_SIZE = 0;
    private static final long KEEP_ALIVE_TIME_IN_SECONDS = 30;
    private static final String LOCKFILE_NAME_GENERATE_FID = "generatefid.lock";
    private static final int MAXIMUM_POOL_SIZE = 1;
    private static final String PROJECT_ID_VALIDATION_MSG = "Please set your Project ID. A valid Firebase Project ID is required to communicate with Firebase server APIs: It identifies your application with Firebase.Please refer to https://firebase.google.com/support/privacy/init-options.";
    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @SuppressLint({"ThreadPoolCreation"})
        public Thread newThread(Runnable r) {
            return new Thread(r, String.format("firebase-installations-executor-%d", new Object[]{Integer.valueOf(this.mCount.getAndIncrement())}));
        }
    };
    private static final Object lockGenerateFid = new Object();
    private final ExecutorService backgroundExecutor;
    @GuardedBy("this")
    private String cachedFid;
    private final RandomFidGenerator fidGenerator;
    /* access modifiers changed from: private */
    @GuardedBy("FirebaseInstallations.this")
    public Set<FidListener> fidListeners;
    private final FirebaseApp firebaseApp;
    private final Lazy<IidStore> iidStore;
    @GuardedBy("lock")
    private final List<StateListener> listeners;
    private final Object lock;
    private final Executor networkExecutor;
    private final PersistedInstallation persistedInstallation;
    private final FirebaseInstallationServiceClient serviceClient;
    private final Utils utils;

    @SuppressLint({"ThreadPoolCreation"})
    FirebaseInstallations(FirebaseApp firebaseApp2, @NonNull Provider<HeartBeatController> heartBeatProvider, @NonNull ExecutorService backgroundExecutor2, @NonNull Executor networkExecutor2) {
        this(backgroundExecutor2, networkExecutor2, firebaseApp2, new FirebaseInstallationServiceClient(firebaseApp2.getApplicationContext(), heartBeatProvider), new PersistedInstallation(firebaseApp2), Utils.getInstance(), new Lazy(new a(firebaseApp2)), new RandomFidGenerator());
    }

    static /* synthetic */ IidStore lambda$new$0(FirebaseApp firebaseApp2) {
        return new IidStore(firebaseApp2);
    }

    @SuppressLint({"ThreadPoolCreation"})
    FirebaseInstallations(ExecutorService backgroundExecutor2, Executor networkExecutor2, FirebaseApp firebaseApp2, FirebaseInstallationServiceClient serviceClient2, PersistedInstallation persistedInstallation2, Utils utils2, Lazy<IidStore> iidStore2, RandomFidGenerator fidGenerator2) {
        this.lock = new Object();
        this.fidListeners = new HashSet();
        this.listeners = new ArrayList();
        this.firebaseApp = firebaseApp2;
        this.serviceClient = serviceClient2;
        this.persistedInstallation = persistedInstallation2;
        this.utils = utils2;
        this.iidStore = iidStore2;
        this.fidGenerator = fidGenerator2;
        this.backgroundExecutor = backgroundExecutor2;
        this.networkExecutor = networkExecutor2;
    }

    private void preConditionChecks() {
        Preconditions.checkNotEmpty(getApplicationId(), APP_ID_VALIDATION_MSG);
        Preconditions.checkNotEmpty(getProjectIdentifier(), PROJECT_ID_VALIDATION_MSG);
        Preconditions.checkNotEmpty(getApiKey(), API_KEY_VALIDATION_MSG);
        Preconditions.checkArgument(Utils.isValidAppIdFormat(getApplicationId()), APP_ID_VALIDATION_MSG);
        Preconditions.checkArgument(Utils.isValidApiKeyFormat(getApiKey()), API_KEY_VALIDATION_MSG);
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public String getProjectIdentifier() {
        return this.firebaseApp.getOptions().getProjectId();
    }

    @NonNull
    public static FirebaseInstallations getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    @NonNull
    public static FirebaseInstallations getInstance(@NonNull FirebaseApp app) {
        Preconditions.checkArgument(app != null, "Null is not a valid value of FirebaseApp.");
        return (FirebaseInstallations) app.get(FirebaseInstallationsApi.class);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public String getApplicationId() {
        return this.firebaseApp.getOptions().getApplicationId();
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public String getApiKey() {
        return this.firebaseApp.getOptions().getApiKey();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public String getName() {
        return this.firebaseApp.getName();
    }

    @NonNull
    public Task<String> getId() {
        preConditionChecks();
        String fid = getCacheFid();
        if (fid != null) {
            return Tasks.forResult(fid);
        }
        Task<String> task = addGetIdListener();
        this.backgroundExecutor.execute(new b(this));
        return task;
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$getId$1 */
    public /* synthetic */ void b() {
        lambda$getToken$2(false);
    }

    @NonNull
    public Task<InstallationTokenResult> getToken(boolean forceRefresh) {
        preConditionChecks();
        Task<InstallationTokenResult> task = addGetAuthTokenListener();
        this.backgroundExecutor.execute(new d(this, forceRefresh));
        return task;
    }

    @NonNull
    public Task<Void> delete() {
        return Tasks.call(this.backgroundExecutor, new e(this));
    }

    @NonNull
    public synchronized FidListenerHandle registerFidListener(@NonNull final FidListener listener) {
        this.fidListeners.add(listener);
        return new FidListenerHandle() {
            public void unregister() {
                synchronized (FirebaseInstallations.this) {
                    FirebaseInstallations.this.fidListeners.remove(listener);
                }
            }
        };
    }

    private Task<String> addGetIdListener() {
        TaskCompletionSource<String> taskCompletionSource = new TaskCompletionSource<>();
        addStateListeners(new GetIdListener(taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    private Task<InstallationTokenResult> addGetAuthTokenListener() {
        TaskCompletionSource<InstallationTokenResult> taskCompletionSource = new TaskCompletionSource<>();
        addStateListeners(new GetAuthTokenListener(this.utils, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    private void addStateListeners(StateListener l) {
        synchronized (this.lock) {
            this.listeners.add(l);
        }
    }

    private void triggerOnStateReached(PersistedInstallationEntry persistedInstallationEntry) {
        synchronized (this.lock) {
            Iterator<StateListener> it = this.listeners.iterator();
            while (it.hasNext()) {
                if (it.next().onStateReached(persistedInstallationEntry)) {
                    it.remove();
                }
            }
        }
    }

    private void triggerOnException(Exception exception) {
        synchronized (this.lock) {
            Iterator<StateListener> it = this.listeners.iterator();
            while (it.hasNext()) {
                if (it.next().onException(exception)) {
                    it.remove();
                }
            }
        }
    }

    private synchronized void updateCacheFid(String cachedFid2) {
        this.cachedFid = cachedFid2;
    }

    private synchronized String getCacheFid() {
        return this.cachedFid;
    }

    /* access modifiers changed from: private */
    /* renamed from: doRegistrationOrRefresh */
    public final void lambda$getToken$2(boolean forceRefresh) {
        PersistedInstallationEntry prefs = getPrefsWithGeneratedIdMultiProcessSafe();
        if (forceRefresh) {
            prefs = prefs.withClearedAuthToken();
        }
        triggerOnStateReached(prefs);
        this.networkExecutor.execute(new c(this, forceRefresh));
    }

    private IidStore getIidStore() {
        return this.iidStore.get();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x004c  */
    /* renamed from: doNetworkCallIfNecessary */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void lambda$doRegistrationOrRefresh$3(boolean r5) {
        /*
            r4 = this;
            com.google.firebase.installations.local.PersistedInstallationEntry r0 = r4.getMultiProcessSafePrefs()
            boolean r1 = r0.isErrored()     // Catch:{ FirebaseInstallationsException -> 0x0061 }
            if (r1 != 0) goto L_0x0022
            boolean r1 = r0.isUnregistered()     // Catch:{ FirebaseInstallationsException -> 0x0061 }
            if (r1 == 0) goto L_0x0011
            goto L_0x0022
        L_0x0011:
            if (r5 != 0) goto L_0x001d
            com.google.firebase.installations.Utils r1 = r4.utils     // Catch:{ FirebaseInstallationsException -> 0x0061 }
            boolean r1 = r1.isAuthTokenExpired(r0)     // Catch:{ FirebaseInstallationsException -> 0x0061 }
            if (r1 == 0) goto L_0x001c
            goto L_0x001d
        L_0x001c:
            return
        L_0x001d:
            com.google.firebase.installations.local.PersistedInstallationEntry r1 = r4.fetchAuthTokenFromServer(r0)     // Catch:{ FirebaseInstallationsException -> 0x0061 }
            goto L_0x0026
        L_0x0022:
            com.google.firebase.installations.local.PersistedInstallationEntry r1 = r4.registerFidWithServer(r0)     // Catch:{ FirebaseInstallationsException -> 0x0061 }
        L_0x0026:
            r4.insertOrUpdatePrefs(r1)
            r4.updateFidListener(r0, r1)
            r0 = r1
            boolean r2 = r0.isRegistered()
            if (r2 == 0) goto L_0x003b
            java.lang.String r2 = r0.getFirebaseInstallationId()
            r4.updateCacheFid(r2)
        L_0x003b:
            boolean r2 = r0.isErrored()
            if (r2 == 0) goto L_0x004c
            com.google.firebase.installations.FirebaseInstallationsException r2 = new com.google.firebase.installations.FirebaseInstallationsException
            com.google.firebase.installations.FirebaseInstallationsException$Status r3 = com.google.firebase.installations.FirebaseInstallationsException.Status.BAD_CONFIG
            r2.<init>(r3)
            r4.triggerOnException(r2)
            goto L_0x0060
        L_0x004c:
            boolean r2 = r0.isNotGenerated()
            if (r2 == 0) goto L_0x005d
            java.io.IOException r2 = new java.io.IOException
            java.lang.String r3 = "Installation ID could not be validated with the Firebase servers (maybe it was deleted). Firebase Installations will need to create a new Installation ID and auth token. Please retry your last request."
            r2.<init>(r3)
            r4.triggerOnException(r2)
            goto L_0x0060
        L_0x005d:
            r4.triggerOnStateReached(r0)
        L_0x0060:
            return
        L_0x0061:
            r1 = move-exception
            r4.triggerOnException(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.installations.FirebaseInstallations.lambda$doRegistrationOrRefresh$3(boolean):void");
    }

    private synchronized void updateFidListener(PersistedInstallationEntry prefs, PersistedInstallationEntry updatedPrefs) {
        if (this.fidListeners.size() != 0 && !TextUtils.equals(prefs.getFirebaseInstallationId(), updatedPrefs.getFirebaseInstallationId())) {
            for (FidListener listener : this.fidListeners) {
                listener.onFidChanged(updatedPrefs.getFirebaseInstallationId());
            }
        }
    }

    private void insertOrUpdatePrefs(PersistedInstallationEntry prefs) {
        synchronized (lockGenerateFid) {
            CrossProcessLock lock2 = CrossProcessLock.acquire(this.firebaseApp.getApplicationContext(), LOCKFILE_NAME_GENERATE_FID);
            try {
                this.persistedInstallation.insertOrUpdatePersistedInstallationEntry(prefs);
                if (lock2 != null) {
                    lock2.releaseAndClose();
                }
            } catch (Throwable th) {
                if (lock2 != null) {
                    lock2.releaseAndClose();
                }
                throw th;
            }
        }
    }

    private PersistedInstallationEntry getPrefsWithGeneratedIdMultiProcessSafe() {
        PersistedInstallationEntry prefs;
        synchronized (lockGenerateFid) {
            CrossProcessLock lock2 = CrossProcessLock.acquire(this.firebaseApp.getApplicationContext(), LOCKFILE_NAME_GENERATE_FID);
            try {
                prefs = this.persistedInstallation.readPersistedInstallationEntryValue();
                if (prefs.isNotGenerated()) {
                    prefs = this.persistedInstallation.insertOrUpdatePersistedInstallationEntry(prefs.withUnregisteredFid(readExistingIidOrCreateFid(prefs)));
                }
                if (lock2 != null) {
                    lock2.releaseAndClose();
                }
            } catch (Throwable th) {
                if (lock2 != null) {
                    lock2.releaseAndClose();
                }
                throw th;
            }
        }
        return prefs;
    }

    private String readExistingIidOrCreateFid(PersistedInstallationEntry prefs) {
        if ((!this.firebaseApp.getName().equals(CHIME_FIREBASE_APP_NAME) && !this.firebaseApp.isDefaultApp()) || !prefs.shouldAttemptMigration()) {
            return this.fidGenerator.createRandomFid();
        }
        String fid = getIidStore().readIid();
        if (TextUtils.isEmpty(fid)) {
            return this.fidGenerator.createRandomFid();
        }
        return fid;
    }

    private PersistedInstallationEntry registerFidWithServer(PersistedInstallationEntry prefs) {
        String iidToken = null;
        if (prefs.getFirebaseInstallationId() != null && prefs.getFirebaseInstallationId().length() == 11) {
            iidToken = getIidStore().readToken();
        }
        InstallationResponse response = this.serviceClient.createFirebaseInstallation(getApiKey(), prefs.getFirebaseInstallationId(), getProjectIdentifier(), getApplicationId(), iidToken);
        switch (AnonymousClass3.$SwitchMap$com$google$firebase$installations$remote$InstallationResponse$ResponseCode[response.getResponseCode().ordinal()]) {
            case 1:
                return prefs.withRegisteredFid(response.getFid(), response.getRefreshToken(), this.utils.currentTimeInSecs(), response.getAuthToken().getToken(), response.getAuthToken().getTokenExpirationTimestamp());
            case 2:
                return prefs.withFisError("BAD CONFIG");
            default:
                throw new FirebaseInstallationsException("Firebase Installations Service is unavailable. Please try again later.", FirebaseInstallationsException.Status.UNAVAILABLE);
        }
    }

    private PersistedInstallationEntry fetchAuthTokenFromServer(@NonNull PersistedInstallationEntry prefs) {
        TokenResult tokenResult = this.serviceClient.generateAuthToken(getApiKey(), prefs.getFirebaseInstallationId(), getProjectIdentifier(), prefs.getRefreshToken());
        switch (AnonymousClass3.$SwitchMap$com$google$firebase$installations$remote$TokenResult$ResponseCode[tokenResult.getResponseCode().ordinal()]) {
            case 1:
                return prefs.withAuthToken(tokenResult.getToken(), tokenResult.getTokenExpirationTimestamp(), this.utils.currentTimeInSecs());
            case 2:
                return prefs.withFisError("BAD CONFIG");
            case 3:
                updateCacheFid((String) null);
                return prefs.withNoGeneratedFid();
            default:
                throw new FirebaseInstallationsException("Firebase Installations Service is unavailable. Please try again later.", FirebaseInstallationsException.Status.UNAVAILABLE);
        }
    }

    /* renamed from: com.google.firebase.installations.FirebaseInstallations$3  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$installations$remote$InstallationResponse$ResponseCode;
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$installations$remote$TokenResult$ResponseCode;

        static {
            int[] iArr = new int[TokenResult.ResponseCode.values().length];
            $SwitchMap$com$google$firebase$installations$remote$TokenResult$ResponseCode = iArr;
            try {
                iArr[TokenResult.ResponseCode.OK.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$firebase$installations$remote$TokenResult$ResponseCode[TokenResult.ResponseCode.BAD_CONFIG.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$firebase$installations$remote$TokenResult$ResponseCode[TokenResult.ResponseCode.AUTH_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            int[] iArr2 = new int[InstallationResponse.ResponseCode.values().length];
            $SwitchMap$com$google$firebase$installations$remote$InstallationResponse$ResponseCode = iArr2;
            try {
                iArr2[InstallationResponse.ResponseCode.OK.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$firebase$installations$remote$InstallationResponse$ResponseCode[InstallationResponse.ResponseCode.BAD_CONFIG.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    /* access modifiers changed from: private */
    public Void deleteFirebaseInstallationId() {
        updateCacheFid((String) null);
        PersistedInstallationEntry entry = getMultiProcessSafePrefs();
        if (entry.isRegistered()) {
            this.serviceClient.deleteFirebaseInstallation(getApiKey(), entry.getFirebaseInstallationId(), getProjectIdentifier(), entry.getRefreshToken());
        }
        insertOrUpdatePrefs(entry.withNoGeneratedFid());
        return null;
    }

    private PersistedInstallationEntry getMultiProcessSafePrefs() {
        PersistedInstallationEntry prefs;
        synchronized (lockGenerateFid) {
            CrossProcessLock lock2 = CrossProcessLock.acquire(this.firebaseApp.getApplicationContext(), LOCKFILE_NAME_GENERATE_FID);
            try {
                prefs = this.persistedInstallation.readPersistedInstallationEntryValue();
                if (lock2 != null) {
                    lock2.releaseAndClose();
                }
            } catch (Throwable th) {
                if (lock2 != null) {
                    lock2.releaseAndClose();
                }
                throw th;
            }
        }
        return prefs;
    }
}

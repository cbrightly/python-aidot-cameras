package com.google.firebase;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.collection.ArrayMap;
import androidx.core.os.UserManagerCompat;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.BackgroundDetector;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.ProcessUtils;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentDiscovery;
import com.google.firebase.components.ComponentDiscoveryService;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.ComponentRuntime;
import com.google.firebase.components.Lazy;
import com.google.firebase.concurrent.ExecutorsRegistrar;
import com.google.firebase.concurrent.UiExecutor;
import com.google.firebase.events.Publisher;
import com.google.firebase.heartbeatinfo.DefaultHeartBeatController;
import com.google.firebase.inject.Provider;
import com.google.firebase.internal.DataCollectionConfigStorage;
import com.google.firebase.provider.FirebaseInitProvider;
import com.google.firebase.tracing.ComponentMonitor;
import com.google.firebase.tracing.FirebaseTrace;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.slf4j.e;

public class FirebaseApp {
    @NonNull
    public static final String DEFAULT_APP_NAME = "[DEFAULT]";
    @GuardedBy("LOCK")
    static final Map<String, FirebaseApp> INSTANCES = new ArrayMap();
    /* access modifiers changed from: private */
    public static final Object LOCK = new Object();
    private static final String LOG_TAG = "FirebaseApp";
    private final Context applicationContext;
    /* access modifiers changed from: private */
    public final AtomicBoolean automaticResourceManagementEnabled = new AtomicBoolean(false);
    private final List<BackgroundStateChangeListener> backgroundStateChangeListeners = new CopyOnWriteArrayList();
    private final ComponentRuntime componentRuntime;
    private final Lazy<DataCollectionConfigStorage> dataCollectionConfigStorage;
    private final Provider<DefaultHeartBeatController> defaultHeartBeatController;
    private final AtomicBoolean deleted = new AtomicBoolean();
    private final List<FirebaseAppLifecycleListener> lifecycleListeners = new CopyOnWriteArrayList();
    private final String name;
    private final FirebaseOptions options;

    @KeepForSdk
    public interface BackgroundStateChangeListener {
        @KeepForSdk
        void onBackgroundStateChanged(boolean z);
    }

    @NonNull
    public Context getApplicationContext() {
        checkNotDeleted();
        return this.applicationContext;
    }

    @NonNull
    public String getName() {
        checkNotDeleted();
        return this.name;
    }

    @NonNull
    public FirebaseOptions getOptions() {
        checkNotDeleted();
        return this.options;
    }

    public boolean equals(Object o) {
        if (!(o instanceof FirebaseApp)) {
            return false;
        }
        return this.name.equals(((FirebaseApp) o).getName());
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        return Objects.toStringHelper(this).add("name", this.name).add("options", this.options).toString();
    }

    @NonNull
    public static List<FirebaseApp> getApps(@NonNull Context context) {
        ArrayList arrayList;
        synchronized (LOCK) {
            arrayList = new ArrayList(INSTANCES.values());
        }
        return arrayList;
    }

    @NonNull
    public static FirebaseApp getInstance() {
        FirebaseApp defaultApp;
        synchronized (LOCK) {
            defaultApp = INSTANCES.get(DEFAULT_APP_NAME);
            if (defaultApp == null) {
                throw new IllegalStateException("Default FirebaseApp is not initialized in this process " + ProcessUtils.getMyProcessName() + ". Make sure to call FirebaseApp.initializeApp(Context) first.");
            }
        }
        return defaultApp;
    }

    @NonNull
    public static FirebaseApp getInstance(@NonNull String name2) {
        FirebaseApp firebaseApp;
        String availableAppNamesMessage;
        synchronized (LOCK) {
            firebaseApp = INSTANCES.get(normalize(name2));
            if (firebaseApp != null) {
                firebaseApp.defaultHeartBeatController.get().registerHeartBeat();
            } else {
                List<String> availableAppNames = getAllAppNames();
                if (availableAppNames.isEmpty()) {
                    availableAppNamesMessage = "";
                } else {
                    availableAppNamesMessage = "Available app names: " + TextUtils.join(", ", availableAppNames);
                }
                throw new IllegalStateException(String.format("FirebaseApp with name %s doesn't exist. %s", new Object[]{name2, availableAppNamesMessage}));
            }
        }
        return firebaseApp;
    }

    @Nullable
    public static FirebaseApp initializeApp(@NonNull Context context) {
        synchronized (LOCK) {
            if (INSTANCES.containsKey(DEFAULT_APP_NAME)) {
                FirebaseApp instance = getInstance();
                return instance;
            }
            FirebaseOptions firebaseOptions = FirebaseOptions.fromResource(context);
            if (firebaseOptions == null) {
                Log.w(LOG_TAG, "Default FirebaseApp failed to initialize because no default options were found. This usually means that com.google.gms:google-services was not applied to your gradle project.");
                return null;
            }
            FirebaseApp initializeApp = initializeApp(context, firebaseOptions);
            return initializeApp;
        }
    }

    @NonNull
    public static FirebaseApp initializeApp(@NonNull Context context, @NonNull FirebaseOptions options2) {
        return initializeApp(context, options2, DEFAULT_APP_NAME);
    }

    @NonNull
    public static FirebaseApp initializeApp(@NonNull Context context, @NonNull FirebaseOptions options2, @NonNull String name2) {
        Context applicationContext2;
        FirebaseApp firebaseApp;
        GlobalBackgroundStateListener.ensureBackgroundStateListenerRegistered(context);
        String normalizedName = normalize(name2);
        if (context.getApplicationContext() == null) {
            applicationContext2 = context;
        } else {
            applicationContext2 = context.getApplicationContext();
        }
        synchronized (LOCK) {
            Map<String, FirebaseApp> map = INSTANCES;
            boolean z = !map.containsKey(normalizedName);
            Preconditions.checkState(z, "FirebaseApp name " + normalizedName + " already exists!");
            Preconditions.checkNotNull(applicationContext2, "Application context cannot be null.");
            firebaseApp = new FirebaseApp(applicationContext2, normalizedName, options2);
            map.put(normalizedName, firebaseApp);
        }
        firebaseApp.initializeAllApis();
        return firebaseApp;
    }

    public void delete() {
        if (this.deleted.compareAndSet(false, true)) {
            synchronized (LOCK) {
                INSTANCES.remove(this.name);
            }
            notifyOnAppDeleted();
        }
    }

    @KeepForSdk
    public <T> T get(Class<T> anInterface) {
        checkNotDeleted();
        return this.componentRuntime.get(anInterface);
    }

    public void setAutomaticResourceManagementEnabled(boolean enabled) {
        checkNotDeleted();
        if (this.automaticResourceManagementEnabled.compareAndSet(!enabled, enabled)) {
            boolean inBackground = BackgroundDetector.getInstance().isInBackground();
            if (enabled && inBackground) {
                notifyBackgroundStateChangeListeners(true);
            } else if (!enabled && inBackground) {
                notifyBackgroundStateChangeListeners(false);
            }
        }
    }

    @KeepForSdk
    public boolean isDataCollectionDefaultEnabled() {
        checkNotDeleted();
        return this.dataCollectionConfigStorage.get().isEnabled();
    }

    @KeepForSdk
    public void setDataCollectionDefaultEnabled(Boolean enabled) {
        checkNotDeleted();
        this.dataCollectionConfigStorage.get().setEnabled(enabled);
    }

    @KeepForSdk
    @Deprecated
    public void setDataCollectionDefaultEnabled(boolean enabled) {
        setDataCollectionDefaultEnabled(Boolean.valueOf(enabled));
    }

    protected FirebaseApp(Context applicationContext2, String name2, FirebaseOptions options2) {
        this.applicationContext = (Context) Preconditions.checkNotNull(applicationContext2);
        this.name = Preconditions.checkNotEmpty(name2);
        this.options = (FirebaseOptions) Preconditions.checkNotNull(options2);
        StartupTime startupTime = FirebaseInitProvider.getStartupTime();
        FirebaseTrace.pushTrace("Firebase");
        FirebaseTrace.pushTrace("ComponentDiscovery");
        List<Provider<ComponentRegistrar>> registrars = ComponentDiscovery.forContext(applicationContext2, ComponentDiscoveryService.class).discoverLazy();
        FirebaseTrace.popTrace();
        FirebaseTrace.pushTrace("Runtime");
        ComponentRuntime.Builder builder = ComponentRuntime.builder(UiExecutor.INSTANCE).addLazyComponentRegistrars(registrars).addComponentRegistrar(new FirebaseCommonRegistrar()).addComponentRegistrar(new ExecutorsRegistrar()).addComponent(Component.of(applicationContext2, Context.class, (Class<? super T>[]) new Class[0])).addComponent(Component.of(this, FirebaseApp.class, (Class<? super T>[]) new Class[0])).addComponent(Component.of(options2, FirebaseOptions.class, (Class<? super T>[]) new Class[0])).setProcessor(new ComponentMonitor());
        if (UserManagerCompat.isUserUnlocked(applicationContext2) && FirebaseInitProvider.isCurrentlyInitializing()) {
            builder.addComponent(Component.of(startupTime, StartupTime.class, (Class<? super T>[]) new Class[0]));
        }
        ComponentRuntime build = builder.build();
        this.componentRuntime = build;
        FirebaseTrace.popTrace();
        this.dataCollectionConfigStorage = new Lazy<>(new b(this, applicationContext2));
        this.defaultHeartBeatController = build.getProvider(DefaultHeartBeatController.class);
        addBackgroundStateChangeListener(new a(this));
        FirebaseTrace.popTrace();
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$new$0 */
    public /* synthetic */ DataCollectionConfigStorage a(Context applicationContext2) {
        return new DataCollectionConfigStorage(applicationContext2, getPersistenceKey(), (Publisher) this.componentRuntime.get(Publisher.class));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$new$1 */
    public /* synthetic */ void b(boolean background) {
        if (!background) {
            this.defaultHeartBeatController.get().registerHeartBeat();
        }
    }

    private void checkNotDeleted() {
        Preconditions.checkState(!this.deleted.get(), "FirebaseApp was deleted");
    }

    @VisibleForTesting
    @KeepForSdk
    public boolean isDefaultApp() {
        return DEFAULT_APP_NAME.equals(getName());
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    @RestrictTo({RestrictTo.Scope.TESTS})
    public void initializeAllComponents() {
        this.componentRuntime.initializeAllComponentsForTests();
    }

    /* access modifiers changed from: private */
    public void notifyBackgroundStateChangeListeners(boolean background) {
        Log.d(LOG_TAG, "Notifying background state change listeners.");
        for (BackgroundStateChangeListener listener : this.backgroundStateChangeListeners) {
            listener.onBackgroundStateChanged(background);
        }
    }

    @KeepForSdk
    public void addBackgroundStateChangeListener(BackgroundStateChangeListener listener) {
        checkNotDeleted();
        if (this.automaticResourceManagementEnabled.get() && BackgroundDetector.getInstance().isInBackground()) {
            listener.onBackgroundStateChanged(true);
        }
        this.backgroundStateChangeListeners.add(listener);
    }

    @KeepForSdk
    public void removeBackgroundStateChangeListener(BackgroundStateChangeListener listener) {
        checkNotDeleted();
        this.backgroundStateChangeListeners.remove(listener);
    }

    @KeepForSdk
    public String getPersistenceKey() {
        return Base64Utils.encodeUrlSafeNoPadding(getName().getBytes(Charset.defaultCharset())) + e.ANY_NON_NULL_MARKER + Base64Utils.encodeUrlSafeNoPadding(getOptions().getApplicationId().getBytes(Charset.defaultCharset()));
    }

    @KeepForSdk
    public void addLifecycleEventListener(@NonNull FirebaseAppLifecycleListener listener) {
        checkNotDeleted();
        Preconditions.checkNotNull(listener);
        this.lifecycleListeners.add(listener);
    }

    @KeepForSdk
    public void removeLifecycleEventListener(@NonNull FirebaseAppLifecycleListener listener) {
        checkNotDeleted();
        Preconditions.checkNotNull(listener);
        this.lifecycleListeners.remove(listener);
    }

    private void notifyOnAppDeleted() {
        for (FirebaseAppLifecycleListener listener : this.lifecycleListeners) {
            listener.onDeleted(this.name, this.options);
        }
    }

    @VisibleForTesting
    public static void clearInstancesForTest() {
        synchronized (LOCK) {
            INSTANCES.clear();
        }
    }

    @KeepForSdk
    public static String getPersistenceKey(String name2, FirebaseOptions options2) {
        return Base64Utils.encodeUrlSafeNoPadding(name2.getBytes(Charset.defaultCharset())) + e.ANY_NON_NULL_MARKER + Base64Utils.encodeUrlSafeNoPadding(options2.getApplicationId().getBytes(Charset.defaultCharset()));
    }

    private static List<String> getAllAppNames() {
        List<String> allAppNames = new ArrayList<>();
        synchronized (LOCK) {
            for (FirebaseApp app : INSTANCES.values()) {
                allAppNames.add(app.getName());
            }
        }
        Collections.sort(allAppNames);
        return allAppNames;
    }

    /* access modifiers changed from: private */
    public void initializeAllApis() {
        if (!UserManagerCompat.isUserUnlocked(this.applicationContext)) {
            Log.i(LOG_TAG, "Device in Direct Boot Mode: postponing initialization of Firebase APIs for app " + getName());
            UserUnlockReceiver.ensureReceiverRegistered(this.applicationContext);
            return;
        }
        Log.i(LOG_TAG, "Device unlocked: initializing all Firebase APIs for app " + getName());
        this.componentRuntime.initializeEagerComponents(isDefaultApp());
        this.defaultHeartBeatController.get().registerHeartBeat();
    }

    private static String normalize(@NonNull String name2) {
        return name2.trim();
    }

    @TargetApi(24)
    public static class UserUnlockReceiver extends BroadcastReceiver {
        private static AtomicReference<UserUnlockReceiver> INSTANCE = new AtomicReference<>();
        private final Context applicationContext;

        public UserUnlockReceiver(Context applicationContext2) {
            this.applicationContext = applicationContext2;
        }

        /* access modifiers changed from: private */
        public static void ensureReceiverRegistered(Context applicationContext2) {
            if (INSTANCE.get() == null) {
                UserUnlockReceiver receiver = new UserUnlockReceiver(applicationContext2);
                if (INSTANCE.compareAndSet((Object) null, receiver)) {
                    applicationContext2.registerReceiver(receiver, new IntentFilter("android.intent.action.USER_UNLOCKED"));
                }
            }
        }

        public void onReceive(Context context, Intent intent) {
            PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
            synchronized (FirebaseApp.LOCK) {
                for (FirebaseApp app : FirebaseApp.INSTANCES.values()) {
                    app.initializeAllApis();
                }
            }
            unregister();
        }

        public void unregister() {
            this.applicationContext.unregisterReceiver(this);
        }
    }

    @TargetApi(14)
    public static class GlobalBackgroundStateListener implements BackgroundDetector.BackgroundStateChangeListener {
        private static AtomicReference<GlobalBackgroundStateListener> INSTANCE = new AtomicReference<>();

        private GlobalBackgroundStateListener() {
        }

        /* access modifiers changed from: private */
        public static void ensureBackgroundStateListenerRegistered(Context context) {
            if (PlatformVersion.isAtLeastIceCreamSandwich() && (context.getApplicationContext() instanceof Application)) {
                Application application = (Application) context.getApplicationContext();
                if (INSTANCE.get() == null) {
                    GlobalBackgroundStateListener listener = new GlobalBackgroundStateListener();
                    if (INSTANCE.compareAndSet((Object) null, listener)) {
                        BackgroundDetector.initialize(application);
                        BackgroundDetector.getInstance().addListener(listener);
                    }
                }
            }
        }

        public void onBackgroundStateChanged(boolean background) {
            synchronized (FirebaseApp.LOCK) {
                Iterator it = new ArrayList(FirebaseApp.INSTANCES.values()).iterator();
                while (it.hasNext()) {
                    FirebaseApp app = (FirebaseApp) it.next();
                    if (app.automaticResourceManagementEnabled.get()) {
                        app.notifyBackgroundStateChangeListeners(background);
                    }
                }
            }
        }
    }
}

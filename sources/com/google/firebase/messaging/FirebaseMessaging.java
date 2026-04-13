package com.google.firebase.messaging;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.datatransport.TransportFactory;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.concurrent.NamedThreadFactory;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.DataCollectionDefaultChange;
import com.google.firebase.FirebaseApp;
import com.google.firebase.events.Event;
import com.google.firebase.events.EventHandler;
import com.google.firebase.events.Subscriber;
import com.google.firebase.heartbeatinfo.HeartBeatInfo;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.inject.Provider;
import com.google.firebase.installations.FirebaseInstallationsApi;
import com.google.firebase.messaging.Store;
import com.google.firebase.platforminfo.UserAgentPublisher;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FirebaseMessaging {
    private static final String EXTRA_DUMMY_P_INTENT = "app";
    static final String GMS_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final String INSTANCE_ID_SCOPE = "FCM";
    private static final long MAX_DELAY_SEC = TimeUnit.HOURS.toSeconds(8);
    private static final long MIN_DELAY_SEC = 30;
    private static final String SEND_INTENT_ACTION = "com.google.android.gcm.intent.SEND";
    private static final String SUBTYPE_DEFAULT = "";
    static final String TAG = "FirebaseMessaging";
    @GuardedBy("FirebaseMessaging.class")
    private static Store store;
    @VisibleForTesting
    @GuardedBy("FirebaseMessaging.class")
    static ScheduledExecutorService syncExecutor;
    @VisibleForTesting
    @SuppressLint({"FirebaseUnknownNullness"})
    @Nullable
    static TransportFactory transportFactory;
    private final AutoInit autoInit;
    private final Context context;
    private final Executor fileExecutor;
    /* access modifiers changed from: private */
    public final FirebaseApp firebaseApp;
    private final FirebaseInstallationsApi fis;
    private final GmsRpc gmsRpc;
    @Nullable
    private final FirebaseInstanceIdInternal iid;
    private final Executor initExecutor;
    private final Application.ActivityLifecycleCallbacks lifecycleCallbacks;
    private final Metadata metadata;
    private final RequestDeduplicator requestDeduplicator;
    @GuardedBy("this")
    private boolean syncScheduledOrRunning;
    private final Executor taskExecutor;
    private final Task<TopicsSubscriber> topicsSubscriberTask;

    @NonNull
    public static synchronized FirebaseMessaging getInstance() {
        FirebaseMessaging instance;
        synchronized (FirebaseMessaging.class) {
            instance = getInstance(FirebaseApp.getInstance());
        }
        return instance;
    }

    @NonNull
    private static synchronized Store getStore(Context context2) {
        Store store2;
        synchronized (FirebaseMessaging.class) {
            if (store == null) {
                store = new Store(context2);
            }
            store2 = store;
        }
        return store2;
    }

    @VisibleForTesting
    static synchronized void clearStoreForTest() {
        synchronized (FirebaseMessaging.class) {
            store = null;
        }
    }

    @NonNull
    @Keep
    static synchronized FirebaseMessaging getInstance(@NonNull FirebaseApp firebaseApp2) {
        FirebaseMessaging firebaseMessaging;
        Class cls = FirebaseMessaging.class;
        synchronized (cls) {
            firebaseMessaging = (FirebaseMessaging) firebaseApp2.get(cls);
            Preconditions.checkNotNull(firebaseMessaging, "Firebase Messaging component is not present");
        }
        return firebaseMessaging;
    }

    FirebaseMessaging(FirebaseApp firebaseApp2, @Nullable FirebaseInstanceIdInternal iid2, Provider<UserAgentPublisher> userAgentPublisher, Provider<HeartBeatInfo> heartBeatInfo, FirebaseInstallationsApi firebaseInstallationsApi, @Nullable TransportFactory transportFactory2, Subscriber subscriber) {
        this(firebaseApp2, iid2, userAgentPublisher, heartBeatInfo, firebaseInstallationsApi, transportFactory2, subscriber, new Metadata(firebaseApp2.getApplicationContext()));
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    FirebaseMessaging(com.google.firebase.FirebaseApp r12, @androidx.annotation.Nullable com.google.firebase.iid.internal.FirebaseInstanceIdInternal r13, com.google.firebase.inject.Provider<com.google.firebase.platforminfo.UserAgentPublisher> r14, com.google.firebase.inject.Provider<com.google.firebase.heartbeatinfo.HeartBeatInfo> r15, com.google.firebase.installations.FirebaseInstallationsApi r16, @androidx.annotation.Nullable com.google.android.datatransport.TransportFactory r17, com.google.firebase.events.Subscriber r18, com.google.firebase.messaging.Metadata r19) {
        /*
            r11 = this;
            com.google.firebase.messaging.GmsRpc r7 = new com.google.firebase.messaging.GmsRpc
            r0 = r7
            r1 = r12
            r2 = r19
            r3 = r14
            r4 = r15
            r5 = r16
            r0.<init>(r1, r2, r3, r4, r5)
            java.util.concurrent.ExecutorService r8 = com.google.firebase.messaging.FcmExecutors.newTaskExecutor()
            java.util.concurrent.ScheduledExecutorService r9 = com.google.firebase.messaging.FcmExecutors.newInitExecutor()
            java.util.concurrent.Executor r10 = com.google.firebase.messaging.FcmExecutors.newFileIOExecutor()
            r0 = r11
            r2 = r13
            r3 = r16
            r4 = r17
            r5 = r18
            r6 = r19
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.FirebaseMessaging.<init>(com.google.firebase.FirebaseApp, com.google.firebase.iid.internal.FirebaseInstanceIdInternal, com.google.firebase.inject.Provider, com.google.firebase.inject.Provider, com.google.firebase.installations.FirebaseInstallationsApi, com.google.android.datatransport.TransportFactory, com.google.firebase.events.Subscriber, com.google.firebase.messaging.Metadata):void");
    }

    FirebaseMessaging(FirebaseApp firebaseApp2, @Nullable FirebaseInstanceIdInternal iid2, FirebaseInstallationsApi firebaseInstallationsApi, @Nullable TransportFactory transportFactory2, Subscriber subscriber, Metadata metadata2, GmsRpc gmsRpc2, Executor taskExecutor2, Executor initExecutor2, Executor fileExecutor2) {
        this.syncScheduledOrRunning = false;
        transportFactory = transportFactory2;
        this.firebaseApp = firebaseApp2;
        this.iid = iid2;
        this.fis = firebaseInstallationsApi;
        this.autoInit = new AutoInit(subscriber);
        Context applicationContext = firebaseApp2.getApplicationContext();
        this.context = applicationContext;
        FcmLifecycleCallbacks fcmLifecycleCallbacks = new FcmLifecycleCallbacks();
        this.lifecycleCallbacks = fcmLifecycleCallbacks;
        this.metadata = metadata2;
        this.taskExecutor = taskExecutor2;
        this.gmsRpc = gmsRpc2;
        this.requestDeduplicator = new RequestDeduplicator(taskExecutor2);
        this.initExecutor = initExecutor2;
        this.fileExecutor = fileExecutor2;
        Context appContext = firebaseApp2.getApplicationContext();
        if (appContext instanceof Application) {
            ((Application) appContext).registerActivityLifecycleCallbacks(fcmLifecycleCallbacks);
        } else {
            Log.w("FirebaseMessaging", "Context " + appContext + " was not an application, can't register for lifecycle callbacks. Some notification events may be dropped as a result.");
        }
        if (iid2 != null) {
            iid2.addNewTokenListener(new n(this));
        }
        initExecutor2.execute(new p(this));
        Task<TopicsSubscriber> createInstance = TopicsSubscriber.createInstance(this, metadata2, gmsRpc2, applicationContext, FcmExecutors.newTopicsSyncExecutor());
        this.topicsSubscriberTask = createInstance;
        createInstance.addOnSuccessListener(initExecutor2, (OnSuccessListener<? super TopicsSubscriber>) new o(this));
        initExecutor2.execute(new j(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$new$1 */
    public /* synthetic */ void g() {
        if (isAutoInitEnabled()) {
            startSyncIfNecessary();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$new$2 */
    public /* synthetic */ void h(TopicsSubscriber topicsSubscriber) {
        if (isAutoInitEnabled()) {
            topicsSubscriber.startTopicsSyncIfNecessary();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$new$3 */
    public /* synthetic */ void i() {
        ProxyNotificationInitializer.initialize(this.context);
    }

    public boolean isAutoInitEnabled() {
        return this.autoInit.isEnabled();
    }

    public void setAutoInitEnabled(boolean enable) {
        this.autoInit.setEnabled(enable);
    }

    @NonNull
    public boolean deliveryMetricsExportToBigQueryEnabled() {
        return MessagingAnalytics.deliveryMetricsExportToBigQueryEnabled();
    }

    public void setDeliveryMetricsExportToBigQuery(boolean enable) {
        MessagingAnalytics.setDeliveryMetricsExportToBigQuery(enable);
    }

    public boolean isNotificationDelegationEnabled() {
        return ProxyNotificationInitializer.isProxyNotificationEnabled(this.context);
    }

    @NonNull
    public Task<Void> setNotificationDelegationEnabled(boolean enable) {
        return ProxyNotificationInitializer.setEnableProxyNotification(this.initExecutor, this.context, enable);
    }

    @NonNull
    public Task<String> getToken() {
        FirebaseInstanceIdInternal firebaseInstanceIdInternal = this.iid;
        if (firebaseInstanceIdInternal != null) {
            return firebaseInstanceIdInternal.getTokenTask();
        }
        TaskCompletionSource<String> taskCompletionSource = new TaskCompletionSource<>();
        this.initExecutor.execute(new q(this, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$getToken$4 */
    public /* synthetic */ void e(TaskCompletionSource taskCompletionSource) {
        try {
            taskCompletionSource.setResult(blockingGetToken());
        } catch (Exception e) {
            taskCompletionSource.setException(e);
        }
    }

    @NonNull
    public Task<Void> deleteToken() {
        if (this.iid != null) {
            TaskCompletionSource<Void> taskCompletionSource = new TaskCompletionSource<>();
            this.initExecutor.execute(new m(this, taskCompletionSource));
            return taskCompletionSource.getTask();
        } else if (getTokenWithoutTriggeringSync() == null) {
            return Tasks.forResult(null);
        } else {
            TaskCompletionSource<Void> taskCompletionSource2 = new TaskCompletionSource<>();
            FcmExecutors.newNetworkIOExecutor().execute(new s(this, taskCompletionSource2));
            return taskCompletionSource2.getTask();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$deleteToken$5 */
    public /* synthetic */ void c(TaskCompletionSource taskCompletionSource) {
        try {
            this.iid.deleteToken(Metadata.getDefaultSenderId(this.firebaseApp), INSTANCE_ID_SCOPE);
            taskCompletionSource.setResult(null);
        } catch (Exception e) {
            taskCompletionSource.setException(e);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$deleteToken$6 */
    public /* synthetic */ void d(TaskCompletionSource taskCompletionSource) {
        try {
            Tasks.await(this.gmsRpc.deleteToken());
            getStore(this.context).deleteToken(getSubtype(), Metadata.getDefaultSenderId(this.firebaseApp));
            taskCompletionSource.setResult(null);
        } catch (Exception e) {
            taskCompletionSource.setException(e);
        }
    }

    @SuppressLint({"TaskMainThread"})
    @NonNull
    public Task<Void> subscribeToTopic(@NonNull String topic) {
        return this.topicsSubscriberTask.onSuccessTask(new t(topic));
    }

    @SuppressLint({"TaskMainThread"})
    @NonNull
    public Task<Void> unsubscribeFromTopic(@NonNull String topic) {
        return this.topicsSubscriberTask.onSuccessTask(new l(topic));
    }

    public void send(@NonNull RemoteMessage message) {
        if (!TextUtils.isEmpty(message.getTo())) {
            Intent intent = new Intent(SEND_INTENT_ACTION);
            Intent dummyIntent = new Intent();
            dummyIntent.setPackage("com.google.example.invalidpackage");
            Context context2 = this.context;
            int i = Build.VERSION.SDK_INT >= 23 ? 67108864 : 0;
            PushAutoTrackHelper.hookIntentGetBroadcast(context2, 0, dummyIntent, i);
            PendingIntent broadcast = PendingIntent.getBroadcast(context2, 0, dummyIntent, i);
            PushAutoTrackHelper.hookPendingIntentGetBroadcast(broadcast, context2, 0, dummyIntent, i);
            intent.putExtra(EXTRA_DUMMY_P_INTENT, broadcast);
            intent.setPackage("com.google.android.gms");
            message.populateSendMessageIntent(intent);
            this.context.sendOrderedBroadcast(intent, "com.google.android.gtalkservice.permission.GTALK_SERVICE");
            return;
        }
        throw new IllegalArgumentException("Missing 'to'");
    }

    /* access modifiers changed from: package-private */
    public Task<TopicsSubscriber> getTopicsSubscriberTask() {
        return this.topicsSubscriberTask;
    }

    @Nullable
    public static TransportFactory getTransportFactory() {
        return transportFactory;
    }

    static void clearTransportFactoryForTest() {
        transportFactory = null;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean isGmsCorePresent() {
        return this.metadata.isGmscorePresent();
    }

    /* access modifiers changed from: package-private */
    public Context getApplicationContext() {
        return this.context;
    }

    /* access modifiers changed from: package-private */
    public synchronized void setSyncScheduledOrRunning(boolean value) {
        this.syncScheduledOrRunning = value;
    }

    /* access modifiers changed from: package-private */
    public synchronized void syncWithDelaySecondsInternal(long delaySeconds) {
        enqueueTaskWithDelaySeconds(new SyncTask(this, Math.min(Math.max(30, 2 * delaySeconds), MAX_DELAY_SEC)), delaySeconds);
        this.syncScheduledOrRunning = true;
    }

    /* access modifiers changed from: package-private */
    @SuppressLint({"ThreadPoolCreation"})
    public void enqueueTaskWithDelaySeconds(Runnable task, long delaySeconds) {
        synchronized (FirebaseMessaging.class) {
            if (syncExecutor == null) {
                syncExecutor = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("TAG"));
            }
            syncExecutor.schedule(task, delaySeconds, TimeUnit.SECONDS);
        }
    }

    /* access modifiers changed from: private */
    public void startSyncIfNecessary() {
        FirebaseInstanceIdInternal firebaseInstanceIdInternal = this.iid;
        if (firebaseInstanceIdInternal != null) {
            firebaseInstanceIdInternal.getToken();
        } else if (tokenNeedsRefresh(getTokenWithoutTriggeringSync())) {
            startSync();
        }
    }

    private synchronized void startSync() {
        if (!this.syncScheduledOrRunning) {
            syncWithDelaySecondsInternal(0);
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    @Nullable
    public Store.Token getTokenWithoutTriggeringSync() {
        return getStore(this.context).getToken(getSubtype(), Metadata.getDefaultSenderId(this.firebaseApp));
    }

    /* access modifiers changed from: package-private */
    public String blockingGetToken() {
        FirebaseInstanceIdInternal firebaseInstanceIdInternal = this.iid;
        if (firebaseInstanceIdInternal != null) {
            try {
                return (String) Tasks.await(firebaseInstanceIdInternal.getTokenTask());
            } catch (InterruptedException | ExecutionException e) {
                throw new IOException(e);
            }
        } else {
            Store.Token cachedToken = getTokenWithoutTriggeringSync();
            if (!tokenNeedsRefresh(cachedToken)) {
                return cachedToken.token;
            }
            String senderId = Metadata.getDefaultSenderId(this.firebaseApp);
            try {
                return (String) Tasks.await(this.requestDeduplicator.getOrStartGetTokenRequest(senderId, new h(this, senderId, cachedToken)));
            } catch (InterruptedException | ExecutionException e2) {
                throw new IOException(e2);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$blockingGetToken$10 */
    public /* synthetic */ Task a(String senderId, Store.Token cachedToken) {
        return this.gmsRpc.getToken().onSuccessTask(this.fileExecutor, new i(this, senderId, cachedToken));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$blockingGetToken$9 */
    public /* synthetic */ Task b(String senderId, Store.Token cachedToken, String token) {
        getStore(this.context).saveToken(getSubtype(), senderId, token, this.metadata.getAppVersionCode());
        if (cachedToken == null || !token.equals(cachedToken.token)) {
            lambda$new$0(token);
        }
        return Tasks.forResult(token);
    }

    private String getSubtype() {
        if (FirebaseApp.DEFAULT_APP_NAME.equals(this.firebaseApp.getName())) {
            return "";
        }
        return this.firebaseApp.getPersistenceKey();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean tokenNeedsRefresh(@Nullable Store.Token token) {
        return token == null || token.needsRefresh(this.metadata.getAppVersionCode());
    }

    /* access modifiers changed from: private */
    /* renamed from: invokeOnTokenRefresh */
    public void lambda$new$0(String token) {
        if (FirebaseApp.DEFAULT_APP_NAME.equals(this.firebaseApp.getName())) {
            if (Log.isLoggable("FirebaseMessaging", 3)) {
                Log.d("FirebaseMessaging", "Invoking onNewToken for app: " + this.firebaseApp.getName());
            }
            Intent messagingIntent = new Intent("com.google.firebase.messaging.NEW_TOKEN");
            messagingIntent.putExtra("token", token);
            new FcmBroadcastProcessor(this.context).process(messagingIntent);
        }
    }

    public class AutoInit {
        private static final String AUTO_INIT_PREF = "auto_init";
        private static final String FCM_PREFERENCES = "com.google.firebase.messaging";
        private static final String MANIFEST_METADATA_AUTO_INIT_ENABLED = "firebase_messaging_auto_init_enabled";
        @GuardedBy("this")
        @Nullable
        private Boolean autoInitEnabled;
        @GuardedBy("this")
        @Nullable
        private EventHandler<DataCollectionDefaultChange> dataCollectionDefaultChangeEventHandler;
        @GuardedBy("this")
        private boolean initialized;
        private final Subscriber subscriber;

        AutoInit(Subscriber subscriber2) {
            this.subscriber = subscriber2;
        }

        /* access modifiers changed from: package-private */
        public synchronized void initialize() {
            if (!this.initialized) {
                Boolean readEnabled = readEnabled();
                this.autoInitEnabled = readEnabled;
                if (readEnabled == null) {
                    k kVar = new k(this);
                    this.dataCollectionDefaultChangeEventHandler = kVar;
                    this.subscriber.subscribe(DataCollectionDefaultChange.class, kVar);
                }
                this.initialized = true;
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: lambda$initialize$0 */
        public /* synthetic */ void a(Event event) {
            if (isEnabled()) {
                FirebaseMessaging.this.startSyncIfNecessary();
            }
        }

        /* access modifiers changed from: package-private */
        public synchronized boolean isEnabled() {
            boolean z;
            initialize();
            Boolean bool = this.autoInitEnabled;
            if (bool != null) {
                z = bool.booleanValue();
            } else {
                z = FirebaseMessaging.this.firebaseApp.isDataCollectionDefaultEnabled();
            }
            return z;
        }

        /* access modifiers changed from: package-private */
        public synchronized void setEnabled(boolean enable) {
            initialize();
            EventHandler<DataCollectionDefaultChange> eventHandler = this.dataCollectionDefaultChangeEventHandler;
            if (eventHandler != null) {
                this.subscriber.unsubscribe(DataCollectionDefaultChange.class, eventHandler);
                this.dataCollectionDefaultChangeEventHandler = null;
            }
            SharedPreferences.Editor preferencesEditor = FirebaseMessaging.this.firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.messaging", 0).edit();
            preferencesEditor.putBoolean(AUTO_INIT_PREF, enable);
            preferencesEditor.apply();
            if (enable) {
                FirebaseMessaging.this.startSyncIfNecessary();
            }
            this.autoInitEnabled = Boolean.valueOf(enable);
        }

        @Nullable
        private Boolean readEnabled() {
            ApplicationInfo applicationInfo;
            Bundle bundle;
            Context applicationContext = FirebaseMessaging.this.firebaseApp.getApplicationContext();
            SharedPreferences preferences = applicationContext.getSharedPreferences("com.google.firebase.messaging", 0);
            if (preferences.contains(AUTO_INIT_PREF)) {
                return Boolean.valueOf(preferences.getBoolean(AUTO_INIT_PREF, false));
            }
            try {
                PackageManager packageManager = applicationContext.getPackageManager();
                if (packageManager == null || (applicationInfo = packageManager.getApplicationInfo(applicationContext.getPackageName(), 128)) == null || (bundle = applicationInfo.metaData) == null || !bundle.containsKey(MANIFEST_METADATA_AUTO_INIT_ENABLED)) {
                    return null;
                }
                return Boolean.valueOf(applicationInfo.metaData.getBoolean(MANIFEST_METADATA_AUTO_INIT_ENABLED));
            } catch (PackageManager.NameNotFoundException e) {
                return null;
            }
        }
    }
}

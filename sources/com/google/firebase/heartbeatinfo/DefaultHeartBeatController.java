package com.google.firebase.heartbeatinfo;

import android.content.Context;
import android.util.Base64OutputStream;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.core.os.UserManagerCompat;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.annotations.concurrent.Background;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.Dependency;
import com.google.firebase.components.Qualified;
import com.google.firebase.heartbeatinfo.HeartBeatInfo;
import com.google.firebase.inject.Provider;
import com.google.firebase.platforminfo.UserAgentPublisher;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.zip.GZIPOutputStream;
import meshsdk.ConfigUtil;
import org.json.JSONArray;
import org.json.JSONObject;

public class DefaultHeartBeatController implements HeartBeatController, HeartBeatInfo {
    private final Context applicationContext;
    private final Executor backgroundExecutor;
    private final Set<HeartBeatConsumer> consumers;
    private final Provider<HeartBeatInfoStorage> storageProvider;
    private final Provider<UserAgentPublisher> userAgentProvider;

    public /* synthetic */ Void b() {
        lambda$registerHeartBeat$0();
        return null;
    }

    public Task<Void> registerHeartBeat() {
        if (this.consumers.size() <= 0) {
            return Tasks.forResult(null);
        }
        if (!UserManagerCompat.isUserUnlocked(this.applicationContext)) {
            return Tasks.forResult(null);
        }
        return Tasks.call(this.backgroundExecutor, new a(this));
    }

    private /* synthetic */ Void lambda$registerHeartBeat$0() {
        synchronized (this) {
            this.storageProvider.get().storeHeartBeat(System.currentTimeMillis(), this.userAgentProvider.get().getUserAgent());
        }
        return null;
    }

    public Task<String> getHeartBeatsHeader() {
        if (!UserManagerCompat.isUserUnlocked(this.applicationContext)) {
            return Tasks.forResult("");
        }
        return Tasks.call(this.backgroundExecutor, new b(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$getHeartBeatsHeader$1 */
    public /* synthetic */ String a() {
        GZIPOutputStream gzip;
        String byteArrayOutputStream;
        synchronized (this) {
            HeartBeatInfoStorage storage = this.storageProvider.get();
            List<HeartBeatResult> allHeartBeats = storage.getAllHeartBeats();
            storage.deleteAllHeartBeats();
            JSONArray array = new JSONArray();
            for (int i = 0; i < allHeartBeats.size(); i++) {
                HeartBeatResult result = allHeartBeats.get(i);
                JSONObject obj = new JSONObject();
                obj.put("agent", (Object) result.getUserAgent());
                obj.put("dates", (Object) new JSONArray((Collection<?>) result.getUsedDates()));
                array.put((Object) obj);
            }
            JSONObject output = new JSONObject();
            output.put("heartbeats", (Object) array);
            output.put(ConfigUtil.VERSION_FILE, (Object) ExifInterface.GPS_MEASUREMENT_2D);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Base64OutputStream b64os = new Base64OutputStream(out, 11);
            try {
                gzip = new GZIPOutputStream(b64os);
                gzip.write(output.toString().getBytes("UTF-8"));
                gzip.close();
                b64os.close();
                byteArrayOutputStream = out.toString("UTF-8");
            } catch (Throwable th) {
                try {
                    b64os.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        return byteArrayOutputStream;
        throw th;
    }

    private DefaultHeartBeatController(Context context, String persistenceKey, Set<HeartBeatConsumer> consumers2, Provider<UserAgentPublisher> userAgentProvider2, Executor backgroundExecutor2) {
        this((Provider<HeartBeatInfoStorage>) new c(context, persistenceKey), consumers2, backgroundExecutor2, userAgentProvider2, context);
    }

    static /* synthetic */ HeartBeatInfoStorage lambda$new$2(Context context, String persistenceKey) {
        return new HeartBeatInfoStorage(context, persistenceKey);
    }

    @VisibleForTesting
    DefaultHeartBeatController(Provider<HeartBeatInfoStorage> testStorage, Set<HeartBeatConsumer> consumers2, Executor executor, Provider<UserAgentPublisher> userAgentProvider2, Context context) {
        this.storageProvider = testStorage;
        this.consumers = consumers2;
        this.backgroundExecutor = executor;
        this.userAgentProvider = userAgentProvider2;
        this.applicationContext = context;
    }

    @NonNull
    public static Component<DefaultHeartBeatController> component() {
        Qualified<Executor> backgroundExecutor2 = Qualified.qualified(Background.class, Executor.class);
        return Component.builder(DefaultHeartBeatController.class, (Class<? super T>[]) new Class[]{HeartBeatController.class, HeartBeatInfo.class}).add(Dependency.required((Class<?>) Context.class)).add(Dependency.required((Class<?>) FirebaseApp.class)).add(Dependency.setOf((Class<?>) HeartBeatConsumer.class)).add(Dependency.requiredProvider((Class<?>) UserAgentPublisher.class)).add(Dependency.required((Qualified<?>) backgroundExecutor2)).factory(new d(backgroundExecutor2)).build();
    }

    static /* synthetic */ DefaultHeartBeatController lambda$component$3(Qualified backgroundExecutor2, ComponentContainer c) {
        return new DefaultHeartBeatController((Context) c.get(Context.class), ((FirebaseApp) c.get(FirebaseApp.class)).getPersistenceKey(), c.setOf(HeartBeatConsumer.class), c.getProvider(UserAgentPublisher.class), (Executor) c.get(backgroundExecutor2));
    }

    @NonNull
    public synchronized HeartBeatInfo.HeartBeat getHeartBeatCode(@NonNull String heartBeatTag) {
        long presentTime = System.currentTimeMillis();
        HeartBeatInfoStorage storage = this.storageProvider.get();
        if (storage.shouldSendGlobalHeartBeat(presentTime)) {
            storage.postHeartBeatCleanUp();
            return HeartBeatInfo.HeartBeat.GLOBAL;
        }
        return HeartBeatInfo.HeartBeat.NONE;
    }
}

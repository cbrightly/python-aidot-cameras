package androidx.startup;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.tracing.Trace;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class AppInitializer {
    private static final String SECTION_NAME = "Startup";
    private static volatile AppInitializer sInstance;
    private static final Object sLock = new Object();
    @NonNull
    final Context mContext;
    @NonNull
    final Set<Class<? extends Initializer<?>>> mDiscovered = new HashSet();
    @NonNull
    final Map<Class<?>, Object> mInitialized = new HashMap();

    AppInitializer(@NonNull Context context) {
        this.mContext = context.getApplicationContext();
    }

    @NonNull
    public static AppInitializer getInstance(@NonNull Context context) {
        if (sInstance == null) {
            synchronized (sLock) {
                if (sInstance == null) {
                    sInstance = new AppInitializer(context);
                }
            }
        }
        return sInstance;
    }

    @NonNull
    public <T> T initializeComponent(@NonNull Class<? extends Initializer<T>> component) {
        return doInitialize(component, new HashSet());
    }

    public boolean isEagerlyInitialized(@NonNull Class<? extends Initializer<?>> component) {
        return this.mDiscovered.contains(component);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public <T> T doInitialize(@NonNull Class<? extends Initializer<?>> component, @NonNull Set<Class<?>> initializing) {
        Object result;
        synchronized (sLock) {
            if (Trace.isEnabled()) {
                try {
                    Trace.beginSection(component.getSimpleName());
                } catch (Throwable th) {
                    Trace.endSection();
                    throw th;
                }
            }
            if (!initializing.contains(component)) {
                if (!this.mInitialized.containsKey(component)) {
                    initializing.add(component);
                    Initializer<?> initializer = (Initializer) component.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    List<Class<? extends Initializer<?>>> dependencies = initializer.dependencies();
                    if (!dependencies.isEmpty()) {
                        for (Class<? extends Initializer<?>> clazz : dependencies) {
                            if (!this.mInitialized.containsKey(clazz)) {
                                doInitialize(clazz, initializing);
                            }
                        }
                    }
                    result = initializer.create(this.mContext);
                    initializing.remove(component);
                    this.mInitialized.put(component, result);
                } else {
                    result = this.mInitialized.get(component);
                }
                Trace.endSection();
            } else {
                throw new IllegalStateException(String.format("Cannot initialize %s. Cycle detected.", new Object[]{component.getName()}));
            }
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public void discoverAndInitialize() {
        try {
            Trace.beginSection(SECTION_NAME);
            Bundle metadata = this.mContext.getPackageManager().getProviderInfo(new ComponentName(this.mContext.getPackageName(), InitializationProvider.class.getName()), 128).metaData;
            String startup = this.mContext.getString(R.string.androidx_startup);
            if (metadata != null) {
                Set<Class<?>> initializing = new HashSet<>();
                for (String key : metadata.keySet()) {
                    if (startup.equals(metadata.getString(key, (String) null))) {
                        Class<?> clazz = Class.forName(key);
                        if (Initializer.class.isAssignableFrom(clazz)) {
                            Class<?> cls = clazz;
                            this.mDiscovered.add(cls);
                            doInitialize(cls, initializing);
                        }
                    }
                }
            }
            Trace.endSection();
        } catch (PackageManager.NameNotFoundException | ClassNotFoundException exception) {
            throw new StartupException((Throwable) exception);
        } catch (Throwable th) {
            Trace.endSection();
            throw th;
        }
    }
}

package com.google.firebase.installations;

import androidx.annotation.Keep;
import com.google.firebase.FirebaseApp;
import com.google.firebase.annotations.concurrent.Background;
import com.google.firebase.annotations.concurrent.Blocking;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.components.Qualified;
import com.google.firebase.concurrent.FirebaseExecutors;
import com.google.firebase.heartbeatinfo.HeartBeatConsumerComponent;
import com.google.firebase.heartbeatinfo.HeartBeatController;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

@Keep
public class FirebaseInstallationsRegistrar implements ComponentRegistrar {
    private static final String LIBRARY_NAME = "fire-installations";

    public List<Component<?>> getComponents() {
        return Arrays.asList(new Component[]{Component.builder(FirebaseInstallationsApi.class).name(LIBRARY_NAME).add(Dependency.required((Class<?>) FirebaseApp.class)).add(Dependency.optionalProvider((Class<?>) HeartBeatController.class)).add(Dependency.required((Qualified<?>) Qualified.qualified(Background.class, ExecutorService.class))).add(Dependency.required((Qualified<?>) Qualified.qualified(Blocking.class, Executor.class))).factory(f.a).build(), HeartBeatConsumerComponent.create(), LibraryVersionComponent.create(LIBRARY_NAME, BuildConfig.VERSION_NAME)});
    }

    static /* synthetic */ FirebaseInstallationsApi lambda$getComponents$0(ComponentContainer c) {
        return new FirebaseInstallations((FirebaseApp) c.get(FirebaseApp.class), c.getProvider(HeartBeatController.class), (ExecutorService) c.get(Qualified.qualified(Background.class, ExecutorService.class)), FirebaseExecutors.newSequentialExecutor((Executor) c.get(Qualified.qualified(Blocking.class, Executor.class))));
    }
}

package com.google.firebase.messaging;

import androidx.annotation.Keep;
import com.google.android.datatransport.TransportFactory;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.firebase.FirebaseApp;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.events.Subscriber;
import com.google.firebase.heartbeatinfo.HeartBeatInfo;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.installations.FirebaseInstallationsApi;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import com.google.firebase.platforminfo.UserAgentPublisher;
import java.util.Arrays;
import java.util.List;

@KeepForSdk
@Keep
public class FirebaseMessagingRegistrar implements ComponentRegistrar {
    private static final String LIBRARY_NAME = "fire-fcm";

    @Keep
    public List<Component<?>> getComponents() {
        return Arrays.asList(new Component[]{Component.builder(FirebaseMessaging.class).name(LIBRARY_NAME).add(Dependency.required((Class<?>) FirebaseApp.class)).add(Dependency.optional(FirebaseInstanceIdInternal.class)).add(Dependency.optionalProvider((Class<?>) UserAgentPublisher.class)).add(Dependency.optionalProvider((Class<?>) HeartBeatInfo.class)).add(Dependency.optional(TransportFactory.class)).add(Dependency.required((Class<?>) FirebaseInstallationsApi.class)).add(Dependency.required((Class<?>) Subscriber.class)).factory(u.a).alwaysEager().build(), LibraryVersionComponent.create(LIBRARY_NAME, BuildConfig.VERSION_NAME)});
    }

    static /* synthetic */ FirebaseMessaging lambda$getComponents$0(ComponentContainer container) {
        return new FirebaseMessaging((FirebaseApp) container.get(FirebaseApp.class), (FirebaseInstanceIdInternal) container.get(FirebaseInstanceIdInternal.class), container.getProvider(UserAgentPublisher.class), container.getProvider(HeartBeatInfo.class), (FirebaseInstallationsApi) container.get(FirebaseInstallationsApi.class), (TransportFactory) container.get(TransportFactory.class), (Subscriber) container.get(Subscriber.class));
    }
}

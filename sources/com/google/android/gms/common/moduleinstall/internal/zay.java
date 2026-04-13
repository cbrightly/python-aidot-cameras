package com.google.android.gms.common.moduleinstall.internal;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.OptionalModuleApi;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.api.internal.RegistrationMethods;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.moduleinstall.InstallStatusListener;
import com.google.android.gms.common.moduleinstall.ModuleAvailabilityResponse;
import com.google.android.gms.common.moduleinstall.ModuleInstallClient;
import com.google.android.gms.common.moduleinstall.ModuleInstallIntentResponse;
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest;
import com.google.android.gms.common.moduleinstall.ModuleInstallResponse;
import com.google.android.gms.internal.base.zav;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zay extends GoogleApi implements ModuleInstallClient {
    public static final /* synthetic */ int zab = 0;
    private static final Api.ClientKey zac;
    private static final Api.AbstractClientBuilder zad;
    private static final Api zae;

    static {
        Api.ClientKey clientKey = new Api.ClientKey();
        zac = clientKey;
        zaq zaq = new zaq();
        zad = zaq;
        zae = new Api("ModuleInstall.API", zaq, clientKey);
    }

    public zay(Activity activity) {
        super(activity, zae, Api.ApiOptions.NO_OPTIONS, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    static final ApiFeatureRequest zad(boolean z, OptionalModuleApi... optionalModuleApiArr) {
        boolean z2;
        Preconditions.checkNotNull(optionalModuleApiArr, "Requested APIs must not be null.");
        if (r0 > 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        Preconditions.checkArgument(z2, "Please provide at least one OptionalModuleApi.");
        for (OptionalModuleApi checkNotNull : optionalModuleApiArr) {
            Preconditions.checkNotNull(checkNotNull, "Requested API must not be null.");
        }
        return ApiFeatureRequest.zaa(Arrays.asList(optionalModuleApiArr), z);
    }

    public final Task<ModuleAvailabilityResponse> areModulesAvailable(OptionalModuleApi... optionalModuleApiArr) {
        ApiFeatureRequest zad2 = zad(false, optionalModuleApiArr);
        if (zad2.getApiFeatures().isEmpty()) {
            return Tasks.forResult(new ModuleAvailabilityResponse(true, 0));
        }
        TaskApiCall.Builder builder = TaskApiCall.builder();
        builder.setFeatures(zav.zaa);
        builder.setMethodKey(27301);
        builder.setAutoResolveMissingFeatures(false);
        builder.run(new zap(this, zad2));
        return doRead(builder.build());
    }

    public final Task<Void> deferredInstall(OptionalModuleApi... optionalModuleApiArr) {
        ApiFeatureRequest zad2 = zad(false, optionalModuleApiArr);
        if (zad2.getApiFeatures().isEmpty()) {
            return Tasks.forResult(null);
        }
        TaskApiCall.Builder builder = TaskApiCall.builder();
        builder.setFeatures(zav.zaa);
        builder.setMethodKey(27302);
        builder.setAutoResolveMissingFeatures(false);
        builder.run(new zan(this, zad2));
        return doRead(builder.build());
    }

    public final Task<ModuleInstallIntentResponse> getInstallModulesIntent(OptionalModuleApi... optionalModuleApiArr) {
        ApiFeatureRequest zad2 = zad(true, optionalModuleApiArr);
        if (zad2.getApiFeatures().isEmpty()) {
            return Tasks.forResult(new ModuleInstallIntentResponse((PendingIntent) null));
        }
        TaskApiCall.Builder builder = TaskApiCall.builder();
        builder.setFeatures(zav.zaa);
        builder.setMethodKey(27307);
        builder.run(new zam(this, zad2));
        return doRead(builder.build());
    }

    public final Task<ModuleInstallResponse> installModules(ModuleInstallRequest moduleInstallRequest) {
        ListenerHolder listenerHolder;
        Class<InstallStatusListener> cls = InstallStatusListener.class;
        ApiFeatureRequest fromModuleInstallRequest = ApiFeatureRequest.fromModuleInstallRequest(moduleInstallRequest);
        InstallStatusListener listener = moduleInstallRequest.getListener();
        Executor listenerExecutor = moduleInstallRequest.getListenerExecutor();
        if (fromModuleInstallRequest.getApiFeatures().isEmpty()) {
            return Tasks.forResult(new ModuleInstallResponse(0));
        }
        if (listener == null) {
            TaskApiCall.Builder builder = TaskApiCall.builder();
            builder.setFeatures(zav.zaa);
            builder.setAutoResolveMissingFeatures(true);
            builder.setMethodKey(27304);
            builder.run(new zao(this, fromModuleInstallRequest));
            return doRead(builder.build());
        }
        Preconditions.checkNotNull(listener);
        if (listenerExecutor == null) {
            listenerHolder = registerListener(listener, cls.getSimpleName());
        } else {
            listenerHolder = ListenerHolders.createListenerHolder(listener, listenerExecutor, cls.getSimpleName());
        }
        zaab zaab = new zaab(listenerHolder);
        AtomicReference atomicReference = new AtomicReference();
        zai zai = new zai(this, atomicReference, listener, fromModuleInstallRequest, zaab);
        zaj zaj = new zaj(this, zaab);
        RegistrationMethods.Builder builder2 = RegistrationMethods.builder();
        builder2.withHolder(listenerHolder);
        builder2.setFeatures(zav.zaa);
        builder2.setAutoResolveMissingFeatures(true);
        builder2.register(zai);
        builder2.unregister(zaj);
        builder2.setMethodKey(27305);
        return doRegisterEventListener(builder2.build()).onSuccessTask(new zak(atomicReference));
    }

    public final Task<Void> releaseModules(OptionalModuleApi... optionalModuleApiArr) {
        ApiFeatureRequest zad2 = zad(false, optionalModuleApiArr);
        if (zad2.getApiFeatures().isEmpty()) {
            return Tasks.forResult(null);
        }
        TaskApiCall.Builder builder = TaskApiCall.builder();
        builder.setFeatures(zav.zaa);
        builder.setMethodKey(27303);
        builder.setAutoResolveMissingFeatures(false);
        builder.run(new zal(this, zad2));
        return doRead(builder.build());
    }

    @ResultIgnorabilityUnspecified
    public final Task<Boolean> unregisterListener(InstallStatusListener installStatusListener) {
        return doUnregisterEventListener(ListenerHolders.createListenerKey(installStatusListener, InstallStatusListener.class.getSimpleName()), 27306);
    }

    public zay(Context context) {
        super(context, zae, Api.ApiOptions.NO_OPTIONS, GoogleApi.Settings.DEFAULT_SETTINGS);
    }
}

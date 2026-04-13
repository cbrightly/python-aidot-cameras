package com.google.firebase.installations;

import java.util.concurrent.Callable;

/* compiled from: lambda */
public final /* synthetic */ class e implements Callable {
    public final /* synthetic */ FirebaseInstallations c;

    public /* synthetic */ e(FirebaseInstallations firebaseInstallations) {
        this.c = firebaseInstallations;
    }

    public final Object call() {
        return this.c.deleteFirebaseInstallationId();
    }
}

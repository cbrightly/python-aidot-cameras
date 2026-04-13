package com.google.firebase.installations;

import com.google.firebase.FirebaseApp;
import com.google.firebase.inject.Provider;

/* compiled from: lambda */
public final /* synthetic */ class a implements Provider {
    public final /* synthetic */ FirebaseApp a;

    public /* synthetic */ a(FirebaseApp firebaseApp) {
        this.a = firebaseApp;
    }

    public final Object get() {
        return FirebaseInstallations.lambda$new$0(this.a);
    }
}

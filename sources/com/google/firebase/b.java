package com.google.firebase;

import android.content.Context;
import com.google.firebase.inject.Provider;

/* compiled from: lambda */
public final /* synthetic */ class b implements Provider {
    public final /* synthetic */ FirebaseApp a;
    public final /* synthetic */ Context b;

    public /* synthetic */ b(FirebaseApp firebaseApp, Context context) {
        this.a = firebaseApp;
        this.b = context;
    }

    public final Object get() {
        return this.a.a(this.b);
    }
}

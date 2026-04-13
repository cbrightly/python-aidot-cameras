package com.google.firebase;

import com.google.firebase.FirebaseApp;

/* compiled from: lambda */
public final /* synthetic */ class a implements FirebaseApp.BackgroundStateChangeListener {
    public final /* synthetic */ FirebaseApp a;

    public /* synthetic */ a(FirebaseApp firebaseApp) {
        this.a = firebaseApp;
    }

    public final void onBackgroundStateChanged(boolean z) {
        this.a.b(z);
    }
}

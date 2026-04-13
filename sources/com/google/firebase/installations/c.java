package com.google.firebase.installations;

/* compiled from: lambda */
public final /* synthetic */ class c implements Runnable {
    public final /* synthetic */ FirebaseInstallations c;
    public final /* synthetic */ boolean d;

    public /* synthetic */ c(FirebaseInstallations firebaseInstallations, boolean z) {
        this.c = firebaseInstallations;
        this.d = z;
    }

    public final void run() {
        this.c.a(this.d);
    }
}

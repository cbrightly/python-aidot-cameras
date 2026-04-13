package com.google.firebase.installations;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    public final /* synthetic */ FirebaseInstallations c;
    public final /* synthetic */ boolean d;

    public /* synthetic */ d(FirebaseInstallations firebaseInstallations, boolean z) {
        this.c = firebaseInstallations;
        this.d = z;
    }

    public final void run() {
        this.c.c(this.d);
    }
}

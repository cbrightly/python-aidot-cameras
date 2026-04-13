package com.google.firebase.messaging;

/* compiled from: lambda */
public final /* synthetic */ class a0 implements Runnable {
    public final /* synthetic */ SharedPreferencesQueue c;

    public /* synthetic */ a0(SharedPreferencesQueue sharedPreferencesQueue) {
        this.c = sharedPreferencesQueue;
    }

    public final void run() {
        this.c.syncState();
    }
}

package com.google.firebase.messaging;

import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;

/* compiled from: lambda */
public final /* synthetic */ class n implements FirebaseInstanceIdInternal.NewTokenListener {
    public final /* synthetic */ FirebaseMessaging a;

    public /* synthetic */ n(FirebaseMessaging firebaseMessaging) {
        this.a = firebaseMessaging;
    }

    public final void onNewToken(String str) {
        this.a.f(str);
    }
}

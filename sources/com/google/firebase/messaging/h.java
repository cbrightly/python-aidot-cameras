package com.google.firebase.messaging;

import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.RequestDeduplicator;
import com.google.firebase.messaging.Store;

/* compiled from: lambda */
public final /* synthetic */ class h implements RequestDeduplicator.GetTokenRequest {
    public final /* synthetic */ FirebaseMessaging a;
    public final /* synthetic */ String b;
    public final /* synthetic */ Store.Token c;

    public /* synthetic */ h(FirebaseMessaging firebaseMessaging, String str, Store.Token token) {
        this.a = firebaseMessaging;
        this.b = str;
        this.c = token;
    }

    public final Task start() {
        return this.a.a(this.b, this.c);
    }
}

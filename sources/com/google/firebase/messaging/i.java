package com.google.firebase.messaging;

import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.Store;

/* compiled from: lambda */
public final /* synthetic */ class i implements SuccessContinuation {
    public final /* synthetic */ FirebaseMessaging a;
    public final /* synthetic */ String b;
    public final /* synthetic */ Store.Token c;

    public /* synthetic */ i(FirebaseMessaging firebaseMessaging, String str, Store.Token token) {
        this.a = firebaseMessaging;
        this.b = str;
        this.c = token;
    }

    public final Task then(Object obj) {
        return this.a.b(this.b, this.c, (String) obj);
    }
}

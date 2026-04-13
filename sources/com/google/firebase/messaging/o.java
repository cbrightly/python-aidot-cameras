package com.google.firebase.messaging;

import com.google.android.gms.tasks.OnSuccessListener;

/* compiled from: lambda */
public final /* synthetic */ class o implements OnSuccessListener {
    public final /* synthetic */ FirebaseMessaging a;

    public /* synthetic */ o(FirebaseMessaging firebaseMessaging) {
        this.a = firebaseMessaging;
    }

    public final void onSuccess(Object obj) {
        this.a.h((TopicsSubscriber) obj);
    }
}

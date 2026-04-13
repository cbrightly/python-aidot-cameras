package com.google.firebase.messaging;

import com.google.android.datatransport.Transformer;
import com.google.firebase.messaging.reporting.MessagingClientEventExtension;

/* compiled from: lambda */
public final /* synthetic */ class a implements Transformer {
    public static final /* synthetic */ a a = new a();

    private /* synthetic */ a() {
    }

    public final Object apply(Object obj) {
        return ((MessagingClientEventExtension) obj).toByteArray();
    }
}

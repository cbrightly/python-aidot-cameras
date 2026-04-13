package com.google.firebase;

import android.content.Context;
import com.google.firebase.platforminfo.LibraryVersionComponent;

/* compiled from: lambda */
public final /* synthetic */ class f implements LibraryVersionComponent.VersionExtractor {
    public static final /* synthetic */ f a = new f();

    private /* synthetic */ f() {
    }

    public final String extract(Object obj) {
        return FirebaseCommonRegistrar.lambda$getComponents$2((Context) obj);
    }
}

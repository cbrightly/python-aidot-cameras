package com.google.firebase;

import android.content.Context;
import com.google.firebase.platforminfo.LibraryVersionComponent;

/* compiled from: lambda */
public final /* synthetic */ class c implements LibraryVersionComponent.VersionExtractor {
    public static final /* synthetic */ c a = new c();

    private /* synthetic */ c() {
    }

    public final String extract(Object obj) {
        return FirebaseCommonRegistrar.lambda$getComponents$3((Context) obj);
    }
}

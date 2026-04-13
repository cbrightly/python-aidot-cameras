package com.google.firebase;

import android.content.Context;
import com.google.firebase.platforminfo.LibraryVersionComponent;

/* compiled from: lambda */
public final /* synthetic */ class d implements LibraryVersionComponent.VersionExtractor {
    public static final /* synthetic */ d a = new d();

    private /* synthetic */ d() {
    }

    public final String extract(Object obj) {
        return FirebaseCommonRegistrar.lambda$getComponents$0((Context) obj);
    }
}

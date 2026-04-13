package com.google.firebase;

import android.content.Context;
import com.google.firebase.platforminfo.LibraryVersionComponent;

/* compiled from: lambda */
public final /* synthetic */ class e implements LibraryVersionComponent.VersionExtractor {
    public static final /* synthetic */ e a = new e();

    private /* synthetic */ e() {
    }

    public final String extract(Object obj) {
        return FirebaseCommonRegistrar.lambda$getComponents$1((Context) obj);
    }
}

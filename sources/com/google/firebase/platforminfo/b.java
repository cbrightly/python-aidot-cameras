package com.google.firebase.platforminfo;

import android.content.Context;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;
import com.google.firebase.platforminfo.LibraryVersionComponent;

/* compiled from: lambda */
public final /* synthetic */ class b implements ComponentFactory {
    public final /* synthetic */ String a;
    public final /* synthetic */ LibraryVersionComponent.VersionExtractor b;

    public /* synthetic */ b(String str, LibraryVersionComponent.VersionExtractor versionExtractor) {
        this.a = str;
        this.b = versionExtractor;
    }

    public final Object create(ComponentContainer componentContainer) {
        return LibraryVersion.create(this.a, this.b.extract((Context) componentContainer.get(Context.class)));
    }
}

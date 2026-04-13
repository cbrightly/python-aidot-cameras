package com.google.firebase.platforminfo;

import android.content.Context;
import com.google.firebase.components.Component;
import com.google.firebase.components.Dependency;

public class LibraryVersionComponent {

    public interface VersionExtractor<T> {
        String extract(T t);
    }

    private LibraryVersionComponent() {
    }

    public static Component<?> create(String sdkName, String version) {
        return Component.intoSet(LibraryVersion.create(sdkName, version), LibraryVersion.class);
    }

    public static Component<?> fromContext(String sdkName, VersionExtractor<Context> extractor) {
        return Component.intoSetBuilder(LibraryVersion.class).add(Dependency.required((Class<?>) Context.class)).factory(new b(sdkName, extractor)).build();
    }
}

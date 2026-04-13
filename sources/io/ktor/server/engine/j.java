package io.ktor.server.engine;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ClassLoaders.kt */
public final class j extends ClassLoader {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public j(@NotNull ClassLoader delegate) {
        super(delegate);
        k.f(delegate, "delegate");
    }

    @NotNull
    public final List<String> a() {
        Package[] packages = getPackages();
        k.b(packages, "getPackages()");
        ArrayList arrayList = new ArrayList(packages.length);
        for (Package it : packages) {
            k.b(it, "it");
            arrayList.add(it.getName());
        }
        return arrayList;
    }
}

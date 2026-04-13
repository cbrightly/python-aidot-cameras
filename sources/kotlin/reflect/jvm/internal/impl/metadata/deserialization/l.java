package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import kotlin.jvm.internal.k;
import meshsdk.ConfigUtil;
import org.jetbrains.annotations.NotNull;

/* compiled from: versionSpecificBehavior.kt */
public final class l {
    public static final boolean b(@NotNull a version) {
        k.f(version, ConfigUtil.VERSION_FILE);
        return a(version);
    }

    public static final boolean a(@NotNull a version) {
        k.f(version, ConfigUtil.VERSION_FILE);
        return version.a() == 1 && version.b() >= 4;
    }
}

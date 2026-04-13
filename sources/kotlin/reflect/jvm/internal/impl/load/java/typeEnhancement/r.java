package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: predefinedEnhancementInfo.kt */
public final class r {
    @NotNull
    private final Map<Integer, d> a;

    public r(@NotNull Map<Integer, d> map) {
        k.f(map, "map");
        this.a = map;
    }

    @NotNull
    public final Map<Integer, d> a() {
        return this.a;
    }
}

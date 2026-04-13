package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.c;
import kotlin.reflect.jvm.internal.impl.name.a;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: NameResolverUtil.kt */
public final class y {
    @NotNull
    public static final a a(@NotNull c $this$getClassId, int index) {
        k.f($this$getClassId, "$this$getClassId");
        a f = a.f($this$getClassId.b(index), $this$getClassId.a(index));
        k.b(f, "ClassId.fromString(getQu… isLocalClassName(index))");
        return f;
    }

    @NotNull
    public static final f b(@NotNull c $this$getName, int index) {
        k.f($this$getName, "$this$getName");
        f e = f.e($this$getName.getString(index));
        k.b(e, "Name.guessByFirstCharacter(getString(index))");
        return e;
    }
}

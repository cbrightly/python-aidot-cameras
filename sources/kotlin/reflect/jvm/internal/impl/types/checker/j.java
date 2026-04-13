package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.r;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;

/* compiled from: KotlinTypeRefiner.kt */
public final class j {
    @NotNull
    private static final y.a<q<i>> a = new y.a<>("KotlinTypeRefiner");

    @NotNull
    public static final List<b0> b(@NotNull i $this$refineTypes, @NotNull Iterable<? extends b0> types) {
        k.f($this$refineTypes, "$this$refineTypes");
        k.f(types, "types");
        Iterable<b0> $this$mapTo$iv$iv = types;
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (b0 it : $this$mapTo$iv$iv) {
            arrayList.add($this$refineTypes.g(it));
        }
        return arrayList;
    }

    @NotNull
    public static final y.a<q<i>> a() {
        return a;
    }
}

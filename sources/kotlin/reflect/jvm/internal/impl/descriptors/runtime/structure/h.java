package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.d;
import kotlin.reflect.jvm.internal.impl.load.java.structure.e;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaAnnotationArguments.kt */
public final class h extends d implements e {
    private final Object[] c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public h(@Nullable f name, @NotNull Object[] values) {
        super(name);
        k.f(values, "values");
        this.c = values;
    }

    @NotNull
    public List<d> getElements() {
        Object[] $this$map$iv = this.c;
        ArrayList arrayList = new ArrayList($this$map$iv.length);
        for (Object it : $this$map$iv) {
            d.a aVar = d.a;
            if (it == null) {
                k.n();
            }
            arrayList.add(aVar.a(it, (f) null));
        }
        return arrayList;
    }
}

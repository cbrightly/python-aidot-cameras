package kotlin.reflect.full;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import kotlin.reflect.c;
import kotlin.reflect.f;
import kotlin.reflect.jvm.internal.g;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KClasses.kt */
public final class b {
    @Nullable
    public static final c<?> a(@NotNull c<?> $this$companionObject) {
        T t;
        k.f($this$companionObject, "$this$companionObject");
        Iterator<T> it = $this$companionObject.i().iterator();
        while (true) {
            if (!it.hasNext()) {
                t = null;
                break;
            }
            t = it.next();
            c it2 = (c) t;
            if (it2 != null) {
                if (((g) it2).c().V()) {
                    break;
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.reflect.jvm.internal.KClassImpl<*>");
            }
        }
        return (c) t;
    }

    @NotNull
    public static final Collection<f<?>> b(@NotNull c<?> $this$functions) {
        k.f($this$functions, "$this$functions");
        Iterable $this$filterIsInstanceTo$iv$iv = $this$functions.g();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (element$iv$iv instanceof f) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        return destination$iv$iv;
    }
}

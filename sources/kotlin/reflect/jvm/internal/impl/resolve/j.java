package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.Collection;
import java.util.LinkedList;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: overridingUtils.kt */
public final class j {
    @NotNull
    public static final <H> Collection<H> b(@NotNull Collection<? extends H> $this$selectMostSpecificInEachOverridableGroup, @NotNull l<? super H, ? extends kotlin.reflect.jvm.internal.impl.descriptors.a> descriptorByHandle) {
        Collection<? extends H> collection = $this$selectMostSpecificInEachOverridableGroup;
        l<? super H, ? extends kotlin.reflect.jvm.internal.impl.descriptors.a> lVar = descriptorByHandle;
        k.f(collection, "$this$selectMostSpecificInEachOverridableGroup");
        k.f(lVar, "descriptorByHandle");
        boolean z = true;
        if ($this$selectMostSpecificInEachOverridableGroup.size() <= 1) {
            return collection;
        }
        LinkedList queue = new LinkedList(collection);
        kotlin.reflect.jvm.internal.impl.utils.j result = kotlin.reflect.jvm.internal.impl.utils.j.c.a();
        while (queue.isEmpty() ^ z) {
            Object nextHandle = y.S(queue);
            kotlin.reflect.jvm.internal.impl.utils.j conflictedHandles = kotlin.reflect.jvm.internal.impl.utils.j.c.a();
            Collection<H> $this$filterNotTo$iv = i.q(nextHandle, queue, lVar, new b(conflictedHandles));
            k.b($this$filterNotTo$iv, "OverridingUtil.extractMe…nflictedHandles.add(it) }");
            if ($this$filterNotTo$iv.size() != z || !conflictedHandles.isEmpty()) {
                Object mostSpecific = i.M($this$filterNotTo$iv, lVar);
                k.b(mostSpecific, "OverridingUtil.selectMos…roup, descriptorByHandle)");
                kotlin.reflect.jvm.internal.impl.descriptors.a mostSpecificDescriptor = (kotlin.reflect.jvm.internal.impl.descriptors.a) lVar.invoke(mostSpecific);
                for (Object next : $this$filterNotTo$iv) {
                    Object it = next;
                    k.b(it, "it");
                    if (!i.C(mostSpecificDescriptor, (kotlin.reflect.jvm.internal.impl.descriptors.a) lVar.invoke(it))) {
                        conflictedHandles.add(next);
                    }
                }
                if (!conflictedHandles.isEmpty()) {
                    result.addAll(conflictedHandles);
                }
                result.add(mostSpecific);
                z = true;
            } else {
                Object p0 = y.p0($this$filterNotTo$iv);
                k.b(p0, "overridableGroup.single()");
                result.add(p0);
            }
        }
        return result;
    }

    /* compiled from: overridingUtils.kt */
    public static final class b extends kotlin.jvm.internal.l implements l<H, x> {
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.utils.j $conflictedHandles;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(kotlin.reflect.jvm.internal.impl.utils.j jVar) {
            super(1);
            this.$conflictedHandles = jVar;
        }

        public final void invoke(H it) {
            kotlin.reflect.jvm.internal.impl.utils.j jVar = this.$conflictedHandles;
            k.b(it, "it");
            jVar.add(it);
        }
    }

    /* compiled from: overridingUtils.kt */
    public static final class a extends kotlin.jvm.internal.l implements l<D, D> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final D invoke(@NotNull D $this$selectMostSpecificInEachOverridableGroup) {
            k.f($this$selectMostSpecificInEachOverridableGroup, "$receiver");
            return $this$selectMostSpecificInEachOverridableGroup;
        }
    }

    public static final <D extends kotlin.reflect.jvm.internal.impl.descriptors.a> void a(@NotNull Collection<D> $this$retainMostSpecificInEachOverridableGroup) {
        k.f($this$retainMostSpecificInEachOverridableGroup, "$this$retainMostSpecificInEachOverridableGroup");
        Collection newResult = b($this$retainMostSpecificInEachOverridableGroup, a.INSTANCE);
        if ($this$retainMostSpecificInEachOverridableGroup.size() != newResult.size()) {
            $this$retainMostSpecificInEachOverridableGroup.retainAll(newResult);
        }
    }
}

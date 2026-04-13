package io.ktor.util.internal;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: LockFreeLinkedList.kt */
public final class b {
    @NotNull
    private static final Object a = new f("CONDITION_FALSE");
    @NotNull
    private static final Object b = new f("ALREADY_REMOVED");
    @NotNull
    private static final Object c = new f("LIST_EMPTY");
    private static final Object d = new f("REMOVE_PREPARED");
    private static final Object e = new f("NO_DECISION");

    @NotNull
    public static final c a(@NotNull Object $this$unwrap) {
        c cVar;
        k.f($this$unwrap, "$this$unwrap");
        e eVar = (e) (!($this$unwrap instanceof e) ? null : $this$unwrap);
        return (eVar == null || (cVar = eVar.a) == null) ? (c) $this$unwrap : cVar;
    }
}

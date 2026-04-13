package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.z0;
import org.jetbrains.annotations.NotNull;

/* compiled from: ModuleAwareClassDescriptor.kt */
public final class u {
    @NotNull
    public static final h b(@NotNull e $this$getRefinedUnsubstitutedMemberScopeIfPossible, @NotNull i kotlinTypeRefiner) {
        k.f($this$getRefinedUnsubstitutedMemberScopeIfPossible, "$this$getRefinedUnsubstitutedMemberScopeIfPossible");
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        return t.c.b($this$getRefinedUnsubstitutedMemberScopeIfPossible, kotlinTypeRefiner);
    }

    @NotNull
    public static final h a(@NotNull e $this$getRefinedMemberScopeIfPossible, @NotNull z0 typeSubstitution, @NotNull i kotlinTypeRefiner) {
        k.f($this$getRefinedMemberScopeIfPossible, "$this$getRefinedMemberScopeIfPossible");
        k.f(typeSubstitution, "typeSubstitution");
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        return t.c.a($this$getRefinedMemberScopeIfPossible, typeSubstitution, kotlinTypeRefiner);
    }
}

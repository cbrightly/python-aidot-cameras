package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.z0;
import org.jetbrains.annotations.NotNull;

/* compiled from: ModuleAwareClassDescriptor.kt */
public abstract class t implements e {
    public static final a c = new a((DefaultConstructorMarker) null);

    /* access modifiers changed from: protected */
    @NotNull
    public abstract h a0(@NotNull i iVar);

    /* access modifiers changed from: protected */
    @NotNull
    public abstract h u(@NotNull z0 z0Var, @NotNull i iVar);

    /* compiled from: ModuleAwareClassDescriptor.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final h b(@NotNull e $this$getRefinedUnsubstitutedMemberScopeIfPossible, @NotNull i kotlinTypeRefiner) {
            h a0;
            k.f($this$getRefinedUnsubstitutedMemberScopeIfPossible, "$this$getRefinedUnsubstitutedMemberScopeIfPossible");
            k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
            t tVar = (t) (!($this$getRefinedUnsubstitutedMemberScopeIfPossible instanceof t) ? null : $this$getRefinedUnsubstitutedMemberScopeIfPossible);
            if (tVar != null && (a0 = tVar.a0(kotlinTypeRefiner)) != null) {
                return a0;
            }
            h R = $this$getRefinedUnsubstitutedMemberScopeIfPossible.R();
            k.b(R, "this.unsubstitutedMemberScope");
            return R;
        }

        @NotNull
        public final h a(@NotNull e $this$getRefinedMemberScopeIfPossible, @NotNull z0 typeSubstitution, @NotNull i kotlinTypeRefiner) {
            h u;
            k.f($this$getRefinedMemberScopeIfPossible, "$this$getRefinedMemberScopeIfPossible");
            k.f(typeSubstitution, "typeSubstitution");
            k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
            t tVar = (t) (!($this$getRefinedMemberScopeIfPossible instanceof t) ? null : $this$getRefinedMemberScopeIfPossible);
            if (tVar != null && (u = tVar.u(typeSubstitution, kotlinTypeRefiner)) != null) {
                return u;
            }
            h m0 = $this$getRefinedMemberScopeIfPossible.m0(typeSubstitution);
            k.b(m0, "this.getMemberScope(\n   …ubstitution\n            )");
            return m0;
        }
    }
}

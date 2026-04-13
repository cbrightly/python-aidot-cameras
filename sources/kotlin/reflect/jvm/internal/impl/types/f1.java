package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import org.jetbrains.annotations.NotNull;

/* compiled from: ErrorType.kt */
public final class f1 extends t {
    @NotNull
    private final String z;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public f1(@NotNull String presentableName, @NotNull u0 constructor, @NotNull h memberScope, @NotNull List<? extends w0> arguments, boolean isMarkedNullable) {
        super(constructor, memberScope, arguments, isMarkedNullable, (String) null, 16, (DefaultConstructorMarker) null);
        k.f(presentableName, "presentableName");
        k.f(constructor, "constructor");
        k.f(memberScope, "memberScope");
        k.f(arguments, "arguments");
        this.z = presentableName;
    }

    @NotNull
    public String R0() {
        return this.z;
    }

    @NotNull
    /* renamed from: P0 */
    public i0 M0(boolean newNullability) {
        return new f1(R0(), I0(), l(), H0(), newNullability);
    }

    @NotNull
    /* renamed from: T0 */
    public f1 S0(@NotNull i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }
}

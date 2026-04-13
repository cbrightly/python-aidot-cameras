package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.c;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TypeAliasExpansionReportStrategy.kt */
public interface s0 {
    void a(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.s0 s0Var);

    void b(@NotNull b0 b0Var, @NotNull b0 b0Var2, @NotNull b0 b0Var3, @NotNull t0 t0Var);

    void c(@NotNull c cVar);

    void d(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.s0 s0Var, @Nullable t0 t0Var, @NotNull b0 b0Var);

    /* compiled from: TypeAliasExpansionReportStrategy.kt */
    public static final class a implements s0 {
        public static final a a = new a();

        private a() {
        }

        public void d(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.s0 typeAlias, @Nullable t0 typeParameter, @NotNull b0 substitutedArgument) {
            k.f(typeAlias, "typeAlias");
            k.f(substitutedArgument, "substitutedArgument");
        }

        public void a(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.s0 typeAlias) {
            k.f(typeAlias, "typeAlias");
        }

        public void b(@NotNull b0 bound, @NotNull b0 unsubstitutedArgument, @NotNull b0 argument, @NotNull t0 typeParameter) {
            k.f(bound, "bound");
            k.f(unsubstitutedArgument, "unsubstitutedArgument");
            k.f(argument, "argument");
            k.f(typeParameter, "typeParameter");
        }

        public void c(@NotNull c annotation) {
            k.f(annotation, "annotation");
        }
    }
}

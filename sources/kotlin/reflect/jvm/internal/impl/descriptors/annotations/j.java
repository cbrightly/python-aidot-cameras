package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Map;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.l;
import kotlin.k;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.i0;
import org.jetbrains.annotations.NotNull;

/* compiled from: BuiltInAnnotationDescriptor.kt */
public final class j implements c {
    @NotNull
    private final g a = i.a(k.PUBLICATION, new a(this));
    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.builtins.g b;
    @NotNull
    private final b c;
    @NotNull
    private final Map<f, kotlin.reflect.jvm.internal.impl.resolve.constants.g<?>> d;

    @NotNull
    public b0 getType() {
        return (b0) this.a.getValue();
    }

    public j(@NotNull kotlin.reflect.jvm.internal.impl.builtins.g builtIns, @NotNull b fqName, @NotNull Map<f, ? extends kotlin.reflect.jvm.internal.impl.resolve.constants.g<?>> allValueArguments) {
        kotlin.jvm.internal.k.f(builtIns, "builtIns");
        kotlin.jvm.internal.k.f(fqName, "fqName");
        kotlin.jvm.internal.k.f(allValueArguments, "allValueArguments");
        this.b = builtIns;
        this.c = fqName;
        this.d = allValueArguments;
    }

    @NotNull
    public b e() {
        return this.c;
    }

    @NotNull
    public Map<f, kotlin.reflect.jvm.internal.impl.resolve.constants.g<?>> a() {
        return this.d;
    }

    /* compiled from: BuiltInAnnotationDescriptor.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<i0> {
        final /* synthetic */ j this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(j jVar) {
            super(0);
            this.this$0 = jVar;
        }

        @NotNull
        public final i0 invoke() {
            e o = this.this$0.b.o(this.this$0.e());
            kotlin.jvm.internal.k.b(o, "builtIns.getBuiltInClassByFqName(fqName)");
            return o.m();
        }
    }

    @NotNull
    public o0 n() {
        o0 o0Var = o0.a;
        kotlin.jvm.internal.k.b(o0Var, "SourceElement.NO_SOURCE");
        return o0Var;
    }
}

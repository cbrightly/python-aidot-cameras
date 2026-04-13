package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.Map;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.n;
import kotlin.reflect.jvm.internal.impl.load.java.structure.w;
import kotlin.reflect.jvm.internal.impl.load.java.structure.x;
import kotlin.reflect.jvm.internal.impl.storage.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: resolvers.kt */
public final class i implements m {
    /* access modifiers changed from: private */
    public final Map<w, Integer> a;
    private final d<w, n> b;
    /* access modifiers changed from: private */
    public final h c;
    /* access modifiers changed from: private */
    public final m d;
    /* access modifiers changed from: private */
    public final int e;

    public i(@NotNull h c2, @NotNull m containingDeclaration, @NotNull x typeParameterOwner, int typeParametersIndexOffset) {
        k.f(c2, "c");
        k.f(containingDeclaration, "containingDeclaration");
        k.f(typeParameterOwner, "typeParameterOwner");
        this.c = c2;
        this.d = containingDeclaration;
        this.e = typeParametersIndexOffset;
        this.a = kotlin.reflect.jvm.internal.impl.utils.a.d(typeParameterOwner.getTypeParameters());
        this.b = c2.e().g(new a(this));
    }

    /* compiled from: resolvers.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<w, n> {
        final /* synthetic */ i this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(i iVar) {
            super(1);
            this.this$0 = iVar;
        }

        @Nullable
        public final n invoke(@NotNull w typeParameter) {
            k.f(typeParameter, "typeParameter");
            Integer num = (Integer) this.this$0.a.get(typeParameter);
            if (num == null) {
                return null;
            }
            return new n(a.b(this.this$0.c, this.this$0), typeParameter, this.this$0.e + num.intValue(), this.this$0.d);
        }
    }

    @Nullable
    public t0 a(@NotNull w javaTypeParameter) {
        k.f(javaTypeParameter, "javaTypeParameter");
        n invoke = this.b.invoke(javaTypeParameter);
        return invoke != null ? invoke : this.c.f().a(javaTypeParameter);
    }
}

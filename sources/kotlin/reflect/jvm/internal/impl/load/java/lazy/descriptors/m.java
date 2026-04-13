package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.k;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.h;
import kotlin.reflect.jvm.internal.impl.load.java.structure.q;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LazyJavaStaticScope.kt */
public abstract class m extends k {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public m(@NotNull h c) {
        super(c, (k) null, 2, (DefaultConstructorMarker) null);
        k.f(c, "c");
    }

    /* access modifiers changed from: protected */
    @Nullable
    public l0 v() {
        return null;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public k.a D(@NotNull q method, @NotNull List<? extends t0> methodTypeParameters, @NotNull b0 returnType, @NotNull List<? extends w0> valueParameters) {
        kotlin.jvm.internal.k.f(method, FirebaseAnalytics.Param.METHOD);
        kotlin.jvm.internal.k.f(methodTypeParameters, "methodTypeParameters");
        kotlin.jvm.internal.k.f(returnType, "returnType");
        kotlin.jvm.internal.k.f(valueParameters, "valueParameters");
        return new k.a(returnType, (b0) null, valueParameters, methodTypeParameters, false, kotlin.collections.q.g());
    }

    /* access modifiers changed from: protected */
    public void p(@NotNull f name, @NotNull Collection<i0> result) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(result, "result");
    }
}
